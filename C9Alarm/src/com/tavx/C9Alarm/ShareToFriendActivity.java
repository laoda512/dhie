package com.tavx.C9Alarm;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import util.SDResReadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.socialization.CommentFilter;
import cn.sharesdk.socialization.CommentFilter.FilterItem;
import cn.sharesdk.socialization.QuickCommentBar;
import cn.sharesdk.socialization.Socialization;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.Form.Form;
import com.tavx.C9Alarm.Manager.CommentManager;
import com.tavx.C9Alarm.Manager.HotInfoManager;
import com.tavx.C9Alarm.cloud.CloudManager;
import com.tavx.C9Alarm.cloud.Bean.Bmob.form_point;
import com.umeng.socialize.controller.UMSocialService;

public class ShareToFriendActivity extends Fragment implements Callback {
	Uri uri;
	String access = null;
	Gallery g;
	private Gallery mGallery;
	int alarm_count;

	boolean weiboBtEnable = true;
	boolean the9BtEnable = true;
	boolean isLaichuang = true;

	private static int awakeTime = 0;
	// ImageButton sinawWeiboIBt;
	// ImageButton openfeintIBt;
	// ImageView titleIm;
	String form;
	Form mForm;
	SleepText mSleepText;
	View tvCancel;
	form_point mform_point;
	String[] shareContent;

	private Handler mDataHandler = new Handler() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {

			if (msg.what == CloudManager.success) {
				List<form_point> mlist = (List<form_point>) msg.obj;
				for (int i = 0; i < mlist.size(); i++) {
					MyLogger.e("aaaa", "xxx" + i + " " + (mlist.get(i) == null));
					if (mlist.get(i).getName().equals(mForm.getTitleName())) {
						mform_point = mlist.get(i);

						break;
					}
				}
			}
			if (getActivity() != null) {
				initQuickCommentBar();
			}
			setChatText();
			super.handleMessage(msg);

		}
	};

	public static ShareToFriendActivity newInstance(Bundle args) {
		ShareToFriendActivity details = new ShareToFriendActivity();
		// Bundle args = new Bundle();
		// args.putString("formId", formId);
		details.setArguments(args);
		return details;
	}

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		MyLogger.e("ShareToFriendActivity", "oncreate");
		setContentView(R.layout.layout_gallery);

	}

	/**
	 * @wangcheng
	 * @myfitcode start
	 * */

	int MainViewId;
	private View MainView;
	ViewHolder holder;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		MainView = inflater.inflate(getContentView(), container, false);

		Bundle extra = getArguments();
		form = extra.getString("alarm_form");
		mForm = Form.getForm(form);
		mSleepText = mForm.getmSleepText();

		awakeTime = Utils.getLaichuangTime();
		Utils.setLaichuangTime(0);

		if (awakeTime > 60) {
			isLaichuang = true;
		} else {
			isLaichuang = false;
		}

		HotInfoManager mHotInfoManager = HotInfoManager.getInstance();
		mHotInfoManager.alarmOver(form, isLaichuang);

		shareContent = mSleepText.getText(awakeTime, isLaichuang,
				AlarmApplication.getApp().getUserNickName())
				.split(Symbol.SPLIT);

		// ————————————————————————
		qcBar = (QuickCommentBar) findViewById(R.id.qcBar);

		ShareSDK.registerService(Socialization.class);
		new Thread() {
			public void run() {
				UIHandler.sendEmptyMessageDelayed(1, 100,
						ShareToFriendActivity.this);

				Class<QuickCommentBar> cls = QuickCommentBar.class;
				try {
					// qcBar.
					qcBar.removeViewAt(0);
					qcBar.removeViewAt(0);
					qcBar.removeViewAt(0);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();

		// _____________

		holder = new ViewHolder();
		holder.sharePad = MainView.findViewById(R.id.sharePad);
		holder.iButton = (Button) MainView.findViewById(R.id.imageButton1);
		holder.tv = (TextView) MainView.findViewById(R.id.textView1);
		// holder.tv_face = (TextView) convertView
		// .findViewById(R.id.textView2);
		holder.imHead = (ImageView) MainView.findViewById(R.id.head);
		MainView.setTag(holder);
		holder.imHead.setImageBitmap(SDResReadManager.getInstance()
				.getResBitmap(mForm.getHeadIcon(), mForm.getTitleName(),
						(int) (100 * AlarmApplication.getApp().getFitRateX()),
						(int) (100 * AlarmApplication.getApp().getFitRateY())));
		setChatText();
		CloudManager.queryFromPoint(AlarmApplication.getApp(), mDataHandler,
				"jifen","jifen.discount");

		// holder.tv_face.setText(buff[1]);
		MyLogger.e("aaaa", " " + form + " ");
		// holder.sharePad.setBackgroundResource(Form.getForm(form).getSettingPageBg());
		// mController.getConfig().removePlatform( SHARE_MEDIA.RENREN,
		// SHARE_MEDIA.DOUBAN);

		holder.iButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyLogger.e("aaaa", mController.getConfig() == null ? "null"
						: "not null");

				mController.openShare(getActivity(), false);
			}

		});

		SinaWeibo qzone = new SinaWeibo(AlarmApplication.getApp());
		// qzone.setPlatformActionListener(this);
		qzone.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				MyLogger.e("aaaa", "" + arg2);
			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				MyLogger.e("aaaa", "success");
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {

			}
		});

		Platform p = ShareSDK.getPlatform(SinaWeibo.NAME);
		if (p.isValid()) {
			String url = "https://api.weibo.com/2/friendships/create.json";
			String method = "POST";
			short customerAction = 0;
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("screen_name", "未来闹钟");
			qzone.customerProtocol(url, method, customerAction, values, null);
		}
		initOnekeyShare();
		return MainView;
	}

	/**
	 * 
	 */
	private void setChatText() {
		holder.tv.setText(generateAdString(shareContent[0] + shareContent[1]));
		holder.tv.setMovementMethod(LinkMovementMethod.getInstance()); 
	}

	private int getContentView() {
		return MainViewId;
	}

	private void setContentView(int guba_stockhome) {
		MainViewId = guba_stockhome;
	}

	private View getMainView() {
		return MainView;
	}

	private View findViewById(int tvArtiTitle) {
		return getMainView().findViewById(tvArtiTitle);
	}

	/**
	 * @wangcheng
	 * @myfitcode end
	 * */

	Handler mHandler = new Handler();

	private void iniUmengShare() {/*
								 * mController =
								 * AlarmApplication.getApp().getCommentController
								 * (); // 设置分享内容
								 * mController.setShareContent(shareContent[1] +
								 * shareContent[0]); UMImage mUMImage = new
								 * UMImage(getActivity(), mForm.getHeadIcon());
								 * mController.setShareMedia(mUMImage);
								 * 
								 * // wx967daebe835fbeac是你在微信开发平台注册应用的AppID,
								 * 这里需要替换成你注册的AppID String appID =
								 * "wxb3239812c10799c5"; // 微信图文分享必须设置一个url
								 * String nick = null; try { nick = new
								 * String(AlarmApplication
								 * .getApp().getUserNickName() .getBytes(),
								 * "utf-8"); } catch
								 * (UnsupportedEncodingException e) { // TODO
								 * Auto-generated catch block
								 * e.printStackTrace(); } String contentUrl =
								 * "http://www.projectalarmx.com/weixinShare.php"
								 * + "?f=" + form + "&t=" + awakeTime + "&n=" +
								 * nick; // 添加微信平台，参数1为当前Activity,
								 * 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
								 * UMWXHandler wxHandler =
								 * mController.getConfig().supportWXPlatform(
								 * getActivity(), appID, contentUrl); // 设置分享标题
								 * String title = "起床啦~";
								 * wxHandler.setWXTitle(title); // 支持微信朋友圈
								 * UMWXHandler circleHandler =
								 * mController.getConfig()
								 * .supportWXCirclePlatform(getActivity(),
								 * appID, contentUrl);
								 * circleHandler.setCircleTitle(title);
								 * 
								 * SinaShareContent sinaContent = new
								 * SinaShareContent(); sinaContent
								 * .setShareContent(shareContent[1] +
								 * shareContent[0] + "@未来闹钟");
								 * sinaContent.setShareImage(mUMImage);
								 * mController.setShareMedia(sinaContent);
								 * 
								 * mController.setAppWebSite(SHARE_MEDIA.QZONE,
								 * "http://www.projectalarmx.com/");
								 * mController.
								 * getConfig().supportQQPlatform(getActivity(),
								 * "101107983",
								 * "124e183eeb5c3cd1d21befdac9591fb1",
								 * "http://www.projectalarmx.com/");
								 * mController.
								 * getConfig().setPlatformOrder(SHARE_MEDIA
								 * .WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
								 * SHARE_MEDIA.QZONE, SHARE_MEDIA.QQ,
								 * SHARE_MEDIA.SINA); // 设置分享图片，参数2为本地图片的资源引用 //
								 * mController.setShareMedia(new
								 * UMImage(getActivity(), // R.drawable.icon));
								 * // 设置分享图片，参数2为本地图片的路径(绝对路径) //
								 * mController.setShareMedia(new
								 * UMImage(getActivity(), //
								 * BitmapFactory.decodeFile
								 * ("/mnt/sdcard/icon.png")));
								 * 
								 * // 设置分享音乐 // UMusic uMusic = new //
								 * UMusic("http://sns.whalecloud.com/test_music.mp3"
								 * ); // uMusic.setAuthor("GuGu"); //
								 * uMusic.setTitle("天籁之音"); // 设置音乐缩略图 //
								 * uMusic.setThumb(
								 * "http://www.umeng.com/images/pic/banner_module_social.png"
								 * ); // mController.setShareMedia(uMusic);
								 * 
								 * // 设置分享视频 // UMVideo umVedio = new UMVideo(
								 * //
								 * "http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023"
								 * ); // 设置视频缩略图 // umVedio.setThumb(
								 * "http://www.umeng.com/images/pic/banner_module_social.png"
								 * ); // umVedio.setTitle("友盟社会化分享!"); //
								 * mController.setShareMedia(umVedio);
								 */
	}

	UMSocialService mController;
	/*
	 * ImageView BTsinaWeibo; ImageView BTtxWeibo;
	 */
	private QuickCommentBar cBar;

	@Override
	public void onResume() {
		super.onResume();
		// refreshBindState();
	}

	/**************************************************************/

	private String testImage;
	// 模拟的主题id
	private String topicId;
	// 模拟的主题标题
	private String topicTitle;
	// 模拟的主题发布时间
	private String topicPublishTime;
	// 模拟的主题作者
	private String topicAuthor;
	private OnekeyShare oks;
	private QuickCommentBar qcBar;
	private CommentFilter filter;

	public boolean handleMessage(Message msg) {

		// Socialization service = ShareSDK.getService(Socialization.class);
		// service.setCustomPlatform(new MyPlatform(this));
		initOnekeyShare();
		initQuickCommentBar();
		return false;
	}

	// Socialization服务依赖OnekeyShare组件，此方法初始化一个OnekeyShare对象
	// 此方法的代码从DemoPage中复制而来
	private void initOnekeyShare() {
		oks = new OnekeyShare();

		oks.setNotification(R.drawable.logo, getString(R.string.app_name));
		oks.setTitle(mForm.getTitleName() + "叫我起床啦");
		oks.setTitleUrl("http://www.projectalarmx.com");
		oks.setText(shareContent[1] + shareContent[0]);
		try {
			oks.setImagePath((String) SDResReadManager.getInstance()
					.getResPathWithName(mForm.getHeadIcon(),
							mForm.getTitleName()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// oks.setImageUrl("http://img.appgo.cn/imgs/sharesdk/content/2013/07/25/1374723172663.jpg");

		String nick = "";
		try {
			nick = new String(AlarmApplication.getApp().getUserNickName()
					.getBytes(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String contentUrl;
		try {
			contentUrl = "http://www.projectalarmx.com/weixinShare.php" + "?f="
					+ form + "&t=" + awakeTime + "&n="
					+ URLEncoder.encode(nick, "utf-8");
			oks.setUrl(contentUrl);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// oks.setFilePath(testImage);
		// oks.setComment(getString(R.string.share));
		oks.setSite("未来闹钟");
		oks.setSiteUrl("http://www.projectalarmx.com");
		// oks.setVenueName("ShareSDK");
		// oks.setVenueDescription("This is a beautiful place!");
		// oks.setLatitude(23.056081f);
		// oks.setLongitude(113.385708f);
		// oks.disableSSOWhenAuthorize();
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
			public void onShare(Platform platform, ShareParams paramsToShare) {
				// 改写twitter分享内容中的text字段，否则会超长，
				// 因为twitter会将图片地址当作文本的一部分去计算长度

			}
		});
	}

	private void initQuickCommentBar() {
		try {
			String id, name, point, author;
			if (mform_point == null) {
				id = mForm.getTitleName();
				name = mForm.getTitleName();
				point = "人气： - ";
				author = "来源： -";
			} else {
				id = mForm.getTitleName();
				name = mForm.getTitleName();
				point = "人气： " + mform_point.getPoint();
				author = "来源： " + mform_point.getJifen().getAuthor();
			}

			qcBar.setTopic(CommentManager.getCommentId(id,
					CommentManager.TYPE_AWAKE_COMMENT), name, point, author);
			qcBar.setTextToShare(shareContent[1] + shareContent[0]);
			qcBar.setAuthedAccountChangeable(false);
			CommentFilter.Builder builder = new CommentFilter.Builder();
			// 非空过滤器
			builder.append(new FilterItem() {
				// 返回true表示是垃圾评论
				public boolean onFilter(String comment) {
					if (TextUtils.isEmpty(comment)) {
						return true;
					} else if (comment.trim().length() <= 0) {
						return true;
					} else if (comment.trim().toLowerCase().equals("null")) {
						return true;
					}
					return false;
				}

				@Override
				public int getFilterCode() {
					return 0;
				}
			});
			// 字数上限过滤器
			builder.append(new FilterItem() {
				// 返回true表示是垃圾评论
				public boolean onFilter(String comment) {
					if (comment != null) {
						String pureComment = comment.trim();
						String wordText = cn.sharesdk.framework.utils.R
								.toWordText(pureComment, 140);
						if (wordText.length() != pureComment.length()) {
							return true;
						}
					}
					return false;
				}

				@Override
				public int getFilterCode() {
					return 0;
				}
			});
			filter = builder.build();
			qcBar.setCommentFilter(filter);
			qcBar.setOnekeyShare(oks);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
//	public String[] getRandomStringKey(){
//		String [] key =new String[]{"手办","抱枕","玩偶","T恤","公仔","卫衣","马克杯","贴纸"};
//		return 
//	}
	
	public Spanned generateAdString(String fistPart) {

		String source = fistPart
				+ "\n\n"
				+ "</font><br><br><a href='http://projectalarmx.sinaapp.com/baidu.php?key="
				+ mForm.getTitleName()
				+ "'>百度百科</a>   "
				+ "</font><a href='http://tieba.baidu.com/f?kw="
				+ mForm.getTitleName()
				+ "'>百度贴吧</a>"
				+ "</font><br><br><a href='http://r.m.taobao.com/s?p=mm_56673559_6858157_23330854&q="
				+ mForm.getTitleName()
				+ " "
				+ "看看抱枕"
				+ "'>想要个抱枕</a>    "
				+ "</font><a href='http://r.m.taobao.com/s?p=mm_56673559_6858157_23330854&q="
				+ mForm.getTitleName()
				+ " "
				+ "手机壳"
				+ "'>Ta的手机壳</a>"
				;
				
				
		if (mform_point != null
				&& mform_point.getJifen() != null
				&& mform_point.getJifen().getForm_discount() != null
				&& mform_point.getJifen().getForm_discount().getAdUrl() != null
				&& mform_point.getJifen().getForm_discount().getAdUrl().size() != 0) {
			String discountUrl=mform_point
					.getJifen()
					.getForm_discount()
					.getAdUrl()
					.get(Utils.random(0, mform_point.getJifen()
							.getForm_discount().getAdUrl().size() - 1));
			
			
			source=source+"</font><br><br><a href='"+discountUrl+"'>打折周边</a>";;
		}
		return Html.fromHtml(source);

	}

	/**************************************************************/

	@Override
	public void onDestroy() {
		// ShareSDK.stopSDK(getActivity());
		super.onDestroy();
	}

	ViewFlipper mFlipper;
	GestureDetector _GestureDetector;
	boolean isWeiboAccessStart = false;
	Object obj_lock = new Object();

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// ShareSDK.stopSDK(this);
	}

	private class ImageAdapter extends BaseAdapter {
		private Context mContext;

		int time;
		int size = 2;

		public ImageAdapter(Context c, int time) {
			mContext = c;
			this.time = time;
			this.mInflater = LayoutInflater.from(c);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub

			return size;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub

			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			switch (position) {
			case 0:
				return getTestPicView(position, convertView, parent);
			case 1:
				return getComView(position, convertView, parent);
			default:
				return null;
			}

		}

		Viewsll vs;
		ArrayList<View> vlist;

		private View getTestPicView(int position, View convertView,
				ViewGroup parent) {/*
									 * 
									 * if (convertView == null) { vs = new
									 * Viewsll(mContext, time); vs.addButton(30,
									 * 500, 500, 50,
									 * BitmapFactory.decodeResource(
									 * mContext.getResources(),
									 * R.drawable.logo_sina),
									 * ShareToFriendActivity.this); vs.doDraw();
									 * 
									 * vs.postInvalidate(); return vs; } else {
									 * convertView.postInvalidate(); return
									 * convertView; }
									 */
			return null;
		}

		ViewHolder holder;
		private LayoutInflater mInflater;

		public View getComView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.layout_gallery, null);
				holder.sharePad = convertView.findViewById(R.id.sharePad);
				holder.iButton = (Button) convertView
						.findViewById(R.id.imageButton1);
				holder.tv = (TextView) convertView.findViewById(R.id.textView1);
				// holder.tv_face = (TextView) convertView
				// .findViewById(R.id.textView2);
				holder.imHead = (ImageView) convertView.findViewById(R.id.head);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.imHead.setImageResource(mForm.getHeadIcon());
			holder.tv.setText(shareContent[0] + shareContent[1]);
			// holder.tv_face.setText(buff[1]);
			MyLogger.e("aaaa", " " + form + " ");
			// holder.sharePad.setBackgroundResource(Form.getForm(form).getSettingPageBg());
			// mController.getConfig().removePlatform( SHARE_MEDIA.RENREN,
			// SHARE_MEDIA.DOUBAN);

			holder.iButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					MyLogger.e("aaaa", mController.getConfig() == null ? "null"
							: "not null");

					mController.openShare(getActivity(), false);

					/*
					 * // TODO Auto-generated method stub if
					 * (Utils.checkNetWork(ShareToFriendActivity.this) == true)
					 * {
					 * 
					 * cls.onUpdateStatusClick(holder.tv.getText().toString() +
					 * holder.tv_face.getText().toString() + "  " +
					 * holder.editText.getText().toString()); } else {
					 * Toast.makeText(ShareToFriendActivity.this, "无网络链接",
					 * 1000); }
					 */}

			});
			return convertView;
		}

		public final class ViewHolder {

			public TextView tv;
			// public TextView tv_face;
			public Button iButton;
			public View sharePad;
			public ImageView imHead;
		}

	}

	boolean isWeiboSendStart = false;
	Object send_lock = new Object();

	// TODO：
	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// // TODO Auto-generated method stub
	// //������ �¼�����ʱ���Լ������?�������_GestureDetector������
	// return _GestureDetector.onTouchEvent(event);
	// }
	public final class ViewHolder {

		public TextView tv;
		// public TextView tv_face;
		public Button iButton;
		public View sharePad;
		public ImageView imHead;
	}
}