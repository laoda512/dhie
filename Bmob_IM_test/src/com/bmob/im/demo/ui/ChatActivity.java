package com.bmob.im.demo.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobNotifyManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.bean.BmobInvitation;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.im.db.BmobDB;
import cn.bmob.im.inteface.EventListener;
import cn.bmob.im.inteface.UploadListener;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.listener.PushListener;

import com.bmob.im.demo.MyMessageReceiver;
import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.EmoViewPagerAdapter;
import com.bmob.im.demo.adapter.EmoteAdapter;
import com.bmob.im.demo.adapter.MessageChatAdapter;
import com.bmob.im.demo.bean.FaceText;
import com.bmob.im.demo.config.BmobConstants;
import com.bmob.im.demo.util.FaceTextUtils;
import com.bmob.im.demo.util.NetUtil;
import com.bmob.im.demo.view.EmoticonsEditText;
import com.bmob.im.demo.view.HeaderLayout;
import com.bmob.im.demo.view.dialog.DialogTips;
import com.bmob.im.demo.view.xlist.XListView;
import com.bmob.im.demo.view.xlist.XListView.IXListViewListener;

/**
 * �������
 * 
 * @ClassName: ChatActivity
 * @Description: TODO
 * @author smile
 * @date 2014-6-3 ����4:33:11
 */
/**
  * @ClassName: ChatActivity
  * @Description: TODO
  * @author smile
  * @date 2014-6-23 ����3:28:49
  */
public class ChatActivity extends ActivityBase implements OnClickListener,
		IXListViewListener, EventListener {

	private Button btn_chat_emo, btn_chat_send, btn_chat_add;
//	btn_chat_keyboard, btn_speak ,btn_chat_voice;
	
	XListView mListView;
	
	EmoticonsEditText edit_user_comment;

	String targetId = "";
	BmobChatUser targetUser;

	private static int MsgPagerNum;

	private LinearLayout layout_more, layout_emo, layout_add;

	private ViewPager pager_emo;

	private TextView tv_picture, tv_camera, tv_location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		manager = BmobChatManager.getInstance(this);
		MsgPagerNum = 0;
		// ��װ�������
		targetUser = (BmobChatUser) getIntent().getSerializableExtra("user");
		targetId = targetUser.getObjectId();
		BmobLog.i("�������" + targetUser.getUsername() + ",targetId = "
				+ targetId);
		initView();
	}


	private void initView() {
		mHeaderLayout = (HeaderLayout) findViewById(R.id.common_actionbar);
		mListView = (XListView) findViewById(R.id.mListView);
		initTopBarForLeft("��" + targetUser.getUsername() + "�Ի�");
		initBottomView();
		initXListView();
	}

	MessageChatAdapter mAdapter;

	private void initXListView() {
		// ���Ȳ�������ظ���
		mListView.setPullLoadEnable(false);
		// ��������
		mListView.setPullRefreshEnable(true);
		// ���ü�����
		mListView.setXListViewListener(this);
		mListView.pullRefreshing();
		// ��������
		mAdapter = new MessageChatAdapter(this, initMsgData());
		mListView.setAdapter(mAdapter);
		// ��ʼ����λ�����һ��
		mListView.setSelection(mAdapter.getCount() - 1);
		mListView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				hideSoftInputView();
				layout_more.setVisibility(View.GONE);
				layout_add.setVisibility(View.GONE);
//				btn_chat_voice.setVisibility(View.VISIBLE);
//				btn_chat_keyboard.setVisibility(View.GONE);
//				btn_chat_send.setVisibility(View.GONE);
				btn_chat_send.setVisibility(View.VISIBLE);
				return false;
			}
		});
		
		//�ط���ť�ĵ���¼�
		mAdapter.setOnInViewClickListener(R.id.iv_fail_resend, new MessageChatAdapter.onInternalClickListener() {

			@Override
			public void OnClickListener(View parentV, View v,Integer position, Object values) {
				//�ط���Ϣ
				ShowLog("����¼�");
				showResendDialog(parentV, v, values);
			}
		});
	}

	/** ��ʾ�ط���ť
	  * showResendDialog
	  * @Title: showResendDialog
	  * @Description: TODO
	  * @param @param recent 
	  * @return void
	  * @throws
	  */
	public void showResendDialog(final View parentV, View v,final Object values) {
		DialogTips dialog = new DialogTips(this, "ȷ���ط�����Ϣ", "ȷ��","ȡ��", "��ʾ", true);
		// ���óɹ��¼�
		dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int userId) {
				if(((BmobMsg)values).getMsgType()==BmobConfig.TYPE_IMAGE){
					resendImageMsg(parentV, values);
				}else{
					resendTextMsg(parentV, values);
				}
				dialogInterface.dismiss();
			}
		});
		// ��ʾȷ�϶Ի���
		dialog.show();
		dialog = null;
	}
	
	/**
	 * �ط��ı���Ϣ
	 */
	private void resendTextMsg(final View parentV,final Object values) {
		BmobChatManager.getInstance(ChatActivity.this).resendMessage(targetUser,(BmobMsg)values,new PushListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ShowLog("���ͳɹ�");
				((BmobMsg)values).setStatus(BmobConfig.STATUS_SEND_SUCCESS);
				parentV.findViewById(R.id.progress_load).setVisibility(View.INVISIBLE);
				parentV.findViewById(R.id.iv_fail_resend).setVisibility(View.INVISIBLE);
				parentV.findViewById(R.id.tv_send_status).setVisibility(View.VISIBLE);
				((TextView)parentV.findViewById(R.id.tv_send_status)).setText("�ѷ���");
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowLog("����ʧ��:"+arg1);
				((BmobMsg)values).setStatus(BmobConfig.STATUS_SEND_FAIL);
				parentV.findViewById(R.id.progress_load).setVisibility(View.INVISIBLE);
				parentV.findViewById(R.id.iv_fail_resend).setVisibility(View.VISIBLE);
				parentV.findViewById(R.id.tv_send_status).setVisibility(View.INVISIBLE);
			}
		});
		mAdapter.notifyDataSetChanged();
	}
	
	
	/** �ط�ͼƬ��Ϣ
	  * @Title: resendImageMsg
	  * @Description: TODO
	  * @param @param parentV
	  * @param @param values 
	  * @return void
	  * @throws
	  */
	private void resendImageMsg(final View parentV,final Object values) {
		BmobChatManager.getInstance(ChatActivity.this).resendImageMessage(targetUser,(BmobMsg)values,new UploadListener() {
			
			@Override
			public void onStart(BmobMsg msg) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				((BmobMsg)values).setStatus(BmobConfig.STATUS_SEND_SUCCESS);
				parentV.findViewById(R.id.progress_load).setVisibility(View.INVISIBLE);
				parentV.findViewById(R.id.iv_fail_resend).setVisibility(View.INVISIBLE);
				parentV.findViewById(R.id.tv_send_status).setVisibility(View.VISIBLE);
				((TextView)parentV.findViewById(R.id.tv_send_status)).setText("�ѷ���");
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				((BmobMsg)values).setStatus(BmobConfig.STATUS_SEND_FAIL);
				parentV.findViewById(R.id.progress_load).setVisibility(View.INVISIBLE);
				parentV.findViewById(R.id.iv_fail_resend).setVisibility(View.VISIBLE);
				parentV.findViewById(R.id.tv_send_status).setVisibility(View.INVISIBLE);
			}
		});
		mAdapter.notifyDataSetChanged();
	}	
	
	/**
	 * ������Ϣ��ʷ�������ݿ��ж���
	 */
	private List<BmobMsg> initMsgData() {
		List<BmobMsg> list = BmobDB.create(this).queryMessages(targetId,MsgPagerNum);
		return list;
	}
	
	private void initAddView() {
		tv_picture = (TextView) findViewById(R.id.tv_picture);
		tv_camera = (TextView) findViewById(R.id.tv_camera);
		tv_location = (TextView) findViewById(R.id.tv_location);
		tv_picture.setOnClickListener(this);
		tv_location.setOnClickListener(this);
		tv_camera.setOnClickListener(this);
	}

	private void initBottomView() {
		// �����
		btn_chat_add = (Button) findViewById(R.id.btn_chat_add);
		btn_chat_emo = (Button) findViewById(R.id.btn_chat_emo);
		btn_chat_add.setOnClickListener(this);
		btn_chat_emo.setOnClickListener(this);
		// ���ұ�
//		btn_chat_keyboard = (Button) findViewById(R.id.btn_chat_keyboard);
//		btn_chat_voice = (Button) findViewById(R.id.btn_chat_voice);
//		btn_chat_voice.setOnClickListener(this);
//		btn_chat_keyboard.setOnClickListener(this);
		btn_chat_send = (Button) findViewById(R.id.btn_chat_send);
		btn_chat_send.setOnClickListener(this);
		// ������
		layout_more = (LinearLayout) findViewById(R.id.layout_more);
		layout_emo = (LinearLayout) findViewById(R.id.layout_emo);
		layout_add = (LinearLayout) findViewById(R.id.layout_add);
		initAddView();
		initEmoView();

		// ���м�
		// ������
//		btn_speak = (Button) findViewById(R.id.btn_speak);
		// �����
		edit_user_comment = (EmoticonsEditText) findViewById(R.id.edit_user_comment);

		edit_user_comment.setOnClickListener(this);
		edit_user_comment.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(s)) {
					btn_chat_send.setVisibility(View.VISIBLE);
//					btn_chat_keyboard.setVisibility(View.GONE);
//					btn_chat_voice.setVisibility(View.GONE);
//				} else {
//					if (btn_chat_voice.getVisibility() != View.VISIBLE) {
//						btn_chat_voice.setVisibility(View.VISIBLE);
//						btn_chat_send.setVisibility(View.GONE);
//						btn_chat_keyboard.setVisibility(View.GONE);
//					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});

	}

	List<FaceText> emos;

	/**
	 * ��ʼ�����鲼��
	 * @Title: initEmoView
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void initEmoView() {
		pager_emo = (ViewPager) findViewById(R.id.pager_emo);
		emos = FaceTextUtils.faceTexts;

		List<View> views = new ArrayList<View>();
		for (int i = 0; i < 2; ++i) {
			views.add(getGridView(i));
		}
		pager_emo.setAdapter(new EmoViewPagerAdapter(views));
	}

	private View getGridView(final int i) {
		View view = View.inflate(this, R.layout.include_emo_gridview, null);
		GridView gridview = (GridView) view.findViewById(R.id.gridview);
		List<FaceText> list = new ArrayList<FaceText>();
		if (i == 0) {
			list.addAll(emos.subList(0, 21));
		} else if (i == 1) {
			list.addAll(emos.subList(21, emos.size()));
		}
		final EmoteAdapter gridAdapter = new EmoteAdapter(ChatActivity.this,
				list);
		gridview.setAdapter(gridAdapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				FaceText name = (FaceText) gridAdapter.getItem(position);
				String key = name.text.toString();
				try {
					if (edit_user_comment != null
							&& !TextUtils.isEmpty(key)) {
						int start = edit_user_comment.getSelectionStart();
						CharSequence content = edit_user_comment.getText()
								.insert(start, key);
						edit_user_comment.setText(content);
						// ��λ���λ��
						CharSequence info = edit_user_comment.getText();
						if (info instanceof Spannable) {
							Spannable spanText = (Spannable) info;
							Selection.setSelection(spanText,
									start + key.length());
						}
					}
					// }
				} catch (Exception e) {

				}

			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.edit_user_comment) {
			mListView.setSelection(mListView.getCount() - 1);
			if (layout_more.getVisibility() == View.VISIBLE) {
				layout_add.setVisibility(View.GONE);
				layout_emo.setVisibility(View.GONE);
				layout_more.setVisibility(View.GONE);
			}
		} else if (id == R.id.btn_chat_emo) {
			if (layout_more.getVisibility() == View.GONE) {
				showEditState(true);
			} else {
				if (layout_add.getVisibility() == View.VISIBLE) {
					layout_add.setVisibility(View.GONE);
					layout_emo.setVisibility(View.VISIBLE);
				} else {
					layout_more.setVisibility(View.GONE);
				}
			}
		} else if (id == R.id.btn_chat_add) {
			if (layout_more.getVisibility() == View.GONE) {
				layout_more.setVisibility(View.VISIBLE);
				layout_add.setVisibility(View.VISIBLE);
				layout_emo.setVisibility(View.GONE);
				hideSoftInputView();
			} else {
				if (layout_emo.getVisibility() == View.VISIBLE) {
					layout_emo.setVisibility(View.GONE);
					layout_add.setVisibility(View.VISIBLE);
				} else {
					layout_more.setVisibility(View.GONE);
				}
			}
		} else if (id == R.id.btn_chat_send) {
			final String msg = edit_user_comment.getText().toString();
			if (msg.equals("")) {
				ShowToast("�����뷢����Ϣ!");
				return;
			}
			boolean isNetConnected = NetUtil.isNetworkAvailable(this);
			if (!isNetConnected) {
				ShowToast(R.string.network_tips);
				// return;
			}
			// ��װBmobMessage����
			BmobMsg message = BmobMsg.createTextSendMsg(this,targetId, msg);
			// Ĭ�Ϸ�����ɣ������ݱ��浽������Ϣ�������Ự����
			manager.sendMessage(targetUser, message);
			//ˢ�½���
			refreshMessage(message);
		} else if (id == R.id.tv_camera) {
			selectImageFromCamera();
		} else if (id == R.id.tv_picture) {
			selectImageFromLocal();
		} else if (id == R.id.tv_location) {
			selectLocationFromMap();
		} else {
		}
	}
	
	/** ������ͼ
	  * @Title: selectLocationFromMap
	  * @Description: TODO
	  * @param  
	  * @return void
	  * @throws
	  */
	private void selectLocationFromMap(){
		Intent intent = new Intent(this, LocationActivity.class);
		intent.putExtra("type", "select");
		startActivityForResult(intent, BmobConstants.REQUESTCODE_TAKE_LOCATION);
	}

	private String localCameraPath = "";//���պ�õ���ͼƬ��ַ

	/**
	 * ����������� startCamera
	 * @Title: startCamera
	 * @throws
	 */
	public void selectImageFromCamera() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File dir = new File(BmobConstants.BMOB_PICTURE_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, String.valueOf(System.currentTimeMillis())+ ".jpg");
		localCameraPath = file.getPath();
		Uri imageUri = Uri.fromFile(file);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(openCameraIntent,BmobConstants.REQUESTCODE_TAKE_CAMERA);
	}

	/** ѡ��ͼƬ
	  * @Title: selectImage
	  * @Description: TODO
	  * @param  
	  * @return void
	  * @throws
	  */
	public void selectImageFromLocal() {
		Intent intent;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
		} else {
			intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		startActivityForResult(intent, BmobConstants.REQUESTCODE_TAKE_LOCAL);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			switch (requestCode) {
			case BmobConstants.REQUESTCODE_TAKE_CAMERA://��ȡ��ֵ��ʱ����ϴ�path·���µ�ͼƬ��������
				ShowLog("����ͼƬ�ĵ�ַ��"+localCameraPath);
				sendImageMessage(localCameraPath);
				break;
			case BmobConstants.REQUESTCODE_TAKE_LOCAL:
				if (data != null) {
					Uri selectedImage = data.getData();
					if (selectedImage != null) {
						Cursor cursor = getContentResolver().query(
								selectedImage, null, null, null, null);
						cursor.moveToFirst();
						int columnIndex = cursor.getColumnIndex("_data");
						String localSelectPath = cursor.getString(columnIndex);
						cursor.close();
						if (localSelectPath == null|| localSelectPath.equals("null")) {
							ShowToast("�Ҳ�������Ҫ��ͼƬ");
							return;
						}
						sendImageMessage(localSelectPath);
					}
				}
				break;
			case BmobConstants.REQUESTCODE_TAKE_LOCATION://����λ��
				double latitude = data.getDoubleExtra("x", 0);//ά��
				double longtitude = data.getDoubleExtra("y", 0);//����
				String address = data.getStringExtra("address");
				if (address != null && !address.equals("")) {
					BmobLog.i("latitude = "+latitude+".longtitude = "+longtitude+",address = "+address);
					sendLocationMessage(address, latitude, longtitude);
				} else {
					ShowToast("�޷���ȡ������λ����Ϣ!");
				}
				
				break;
			}
		}
	}
	
	
	/** ����λ����Ϣ
	  * @Title: sendLocationMessage
	  * @Description: TODO
	  * @param @param address
	  * @param @param latitude
	  * @param @param longtitude 
	  * @return void
	  * @throws
	  */
	private void sendLocationMessage(String address,double latitude,double longtitude){
		if (layout_more.getVisibility() == View.VISIBLE) {
			layout_more.setVisibility(View.GONE);
			layout_add.setVisibility(View.GONE);
			layout_emo.setVisibility(View.GONE);
		}
		// ��װBmobMessage����
		BmobMsg message = BmobMsg.createLocationSendMsg(this, targetId, address, latitude, longtitude);
		// Ĭ�Ϸ�����ɣ������ݱ��浽������Ϣ�������Ự����
		manager.sendMessage(targetUser, message);
		//ˢ�½���
		refreshMessage(message);
	}
	
	/** Ĭ�����ϴ�����ͼƬ��֮�����ʾ����
	  * sendImageMessage
	  * @Title: sendImageMessage
	  * @Description: TODO
	  * @param @param localPath 
	  * @return void
	  * @throws
	  */
	private void sendImageMessage(String local){
		if (layout_more.getVisibility() == View.VISIBLE) {
			layout_more.setVisibility(View.GONE);
			layout_add.setVisibility(View.GONE);
			layout_emo.setVisibility(View.GONE);
		}
		manager.sendImageMessage(targetUser, local,new UploadListener() {
			
			@Override
			public void onStart(BmobMsg msg) {
				// TODO Auto-generated method stub
				ShowLog("��ʼ�ϴ�onStart��"+msg.getContent()+",״̬��"+msg.getStatus());
				refreshMessage(msg);
			}
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				mAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onFailure(int error, String arg1) {
				// TODO Auto-generated method stub
				ShowLog("�ϴ�ʧ�� -->arg1��"+arg1);
				mAdapter.notifyDataSetChanged();
			}
		});
	}
	
	/** ˢ�½���
	  * refreshMessage
	  * @Title: refreshMessage
	  * @Description: TODO
	  * @param @param message 
	  * @return void
	  * @throws
	  */
	private void refreshMessage(BmobMsg msg){
		// ���½���
		mAdapter.add(msg);
		mListView.setSelection(mAdapter.getCount() - 1);
		edit_user_comment.setText("");
	}
	
	/**�����Ƿ���Ц������ʾ�ı�������״̬
	 * @Title: showEditState
	 * @Description: TODO
	 * @param @param isEmo: �����������ֺͱ���
	 * @return void
	 * @throws
	 */
	private void showEditState(boolean isEmo) {
		edit_user_comment.setVisibility(View.VISIBLE);
//		btn_chat_keyboard.setVisibility(View.GONE);
//		btn_chat_voice.setVisibility(View.VISIBLE);
//		btn_speak.setVisibility(View.GONE);
		edit_user_comment.requestFocus();
		if (isEmo) {
			layout_more.setVisibility(View.VISIBLE);
			layout_more.setVisibility(View.VISIBLE);
			layout_emo.setVisibility(View.VISIBLE);
			layout_add.setVisibility(View.GONE);
			hideSoftInputView();
		} else {
			layout_more.setVisibility(View.GONE);
			showSoftInputView();
		}
	}

	// ��ʾ�����
	public void showSoftInputView() {
		if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.showSoftInput(edit_user_comment, 0);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MyMessageReceiver.ehList.add(this);// �������͵���Ϣ
		// �п��������ڼ䣬������������֪ͨ������ʱ����Ҫ���֪ͨ�����δ����Ϣ��
		BmobNotifyManager.getInstance(this).cancelNotify();
		BmobDB.create(this).resetUnread(targetId);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MyMessageReceiver.ehList.remove(this);// �������͵���Ϣ
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == NEW_MESSAGE) {
				BmobMsg message = (BmobMsg) msg.obj;
				String uid = message.getBelongId();
				if (!uid.equals(targetId))// ������ǵ�ǰ��������������Ϣ��������
					return;
				mAdapter.add(message);
				// ������ҳ���յ�����Ϣ�����Ѷ�״̬
				message.setIsReaded(BmobConfig.STATE_READED);
				// ������յ�����Ϣ-�������Ѷ���ִ���Է�
				BmobChatManager.getInstance(ChatActivity.this).saveReceiveMessage(true,message);
				// ��λ
				mListView.setSelection(mAdapter.getCount() - 1);
			}
		}
	};

	public static final int NEW_MESSAGE = 0x001;// �յ���Ϣ

	@Override
	public void onMessage(BmobMsg message) {
		// TODO Auto-generated method stub
		Message handlerMsg = handler.obtainMessage(NEW_MESSAGE);
		handlerMsg.obj = message;
		handler.sendMessage(handlerMsg);
	}

	@Override
	public void onNetChange(boolean isNetConnected) {
		// TODO Auto-generated method stub
		if (!isNetConnected) {
			ShowToast(R.string.network_tips);
		}
	}

	@Override
	public void onAddUser(BmobInvitation invite) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onOffline() {
		// TODO Auto-generated method stub
		showOfflineDialog(this);
	}

	@Override
	public void onReaded(String conversionId, String msgTime) {
		// TODO Auto-generated method stub
		//�޸Ľ�����ָ����Ϣ���Ķ�״̬
		for (BmobMsg msg : mAdapter.getList()) {
			if (msg.getConversationId().equals(conversionId) && msg.getMsgTime().equals(msgTime)) {
				msg.setStatus(BmobConfig.STATUS_SEND_RECEIVERED);
			}
			mAdapter.notifyDataSetChanged();
		}
		
	}
	
	public void onRefresh() {
		// TODO Auto-generated method stub
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				MsgPagerNum++;
				int total = BmobDB.create(ChatActivity.this)
						.queryChatTotalCount(targetId);
				BmobLog.i("��¼������" + total);
				int currents = mAdapter.getCount();
				if (total <= currents) {
					ShowToast("�������!");
				} else {
					List<BmobMsg> msgList = initMsgData();
					mAdapter.setList(msgList);
					mListView.setSelection(mAdapter.getCount() - currents - 1);
				}
				mListView.stopRefresh();
			}
		}, 1000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (layout_more.getVisibility() == 0) {
				layout_more.setVisibility(View.GONE);
				return false;
			} else {
				return super.onKeyDown(keyCode, event);
			}
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		hideSoftInputView();
	}
	
}
