package com.bmob.im.demo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.config.BmobConfig;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.base.BaseListAdapter;
import com.bmob.im.demo.adapter.base.ViewHolder;
import com.bmob.im.demo.ui.ImageBrowserActivity;
import com.bmob.im.demo.ui.LocationActivity;
import com.bmob.im.demo.ui.SetMyInfoActivity;
import com.bmob.im.demo.util.FaceTextUtils;
import com.bmob.im.demo.util.ImageLoadOptions;
import com.bmob.im.demo.util.TimeUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


/** 聊天适配器
  * @ClassName: MessageChatAdapter
  * @Description: TODO
  * @author smile
  * @date 2014-5-28 下午5:34:07
  */
public class MessageChatAdapter extends BaseListAdapter<BmobMsg> {

	private final int TYPE_RECEIVER_TXT = 0;
	private final int TYPE_SEND_TXT = 1;
	private final int TYPE_SEND_IMAGE = 2;
	private final int TYPE_RECEIVER_IMAGE = 3;
	
	private final int TYPE_SEND_LOCATION = 4;
	private final int TYPE_RECEIVER_LOCATION = 5;

	String currentObjectId = "";
	
	public MessageChatAdapter(Context context,List<BmobMsg> msgList) {
		// TODO Auto-generated constructor stub
		super(context, msgList);
		currentObjectId = BmobUserManager.getInstance(context).getCurrentUserObjectId();
	}

	@Override
	public int getItemViewType(int position) {
		BmobMsg msg = list.get(position);
		if(msg.getMsgType()==BmobConfig.TYPE_TEXT){
			return msg.getBelongId().equals(currentObjectId) ? TYPE_SEND_TXT: TYPE_RECEIVER_TXT;
		}else if(msg.getMsgType()==BmobConfig.TYPE_IMAGE){
			return msg.getBelongId().equals(currentObjectId) ? TYPE_SEND_IMAGE: TYPE_RECEIVER_IMAGE;
		}else if(msg.getMsgType()==BmobConfig.TYPE_LOCATION){
			return msg.getBelongId().equals(currentObjectId) ? TYPE_SEND_LOCATION: TYPE_RECEIVER_LOCATION;
		}
		return -1;
	}

	@Override
	public int getViewTypeCount() {
		return 6;
	}
	
	private View createViewByType(BmobMsg message, int position) {
		int type = message.getMsgType();
		if(type==BmobConfig.TYPE_TEXT){
			return getItemViewType(position) == TYPE_RECEIVER_TXT ? 
					mInflater.inflate(R.layout.item_chat_received_message, null) 
					:
					mInflater.inflate(R.layout.item_chat_sent_message, null);
		}else if(type==BmobConfig.TYPE_IMAGE){//图片类型
			return getItemViewType(position) == TYPE_RECEIVER_IMAGE ? 
					mInflater.inflate(R.layout.item_chat_received_image, null) 
					:
						mInflater.inflate(R.layout.item_chat_sent_image, null);
		}else if(type==BmobConfig.TYPE_LOCATION){//位置类型
			return getItemViewType(position) == TYPE_RECEIVER_LOCATION ? 
					mInflater.inflate(R.layout.item_chat_received_location, null) 
					:
						mInflater.inflate(R.layout.item_chat_sent_location, null);
		}else{
			return getItemViewType(position) == TYPE_RECEIVER_TXT ? 
					mInflater.inflate(R.layout.item_chat_received_message, null) 
					:
						mInflater.inflate(R.layout.item_chat_sent_message, null);
		}
	}

	
	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final BmobMsg item = list.get(position);
		if (convertView == null) {
			convertView = createViewByType(item, position);
		}
		//文本类型
		ImageView iv_avatar = ViewHolder.get(convertView, R.id.iv_avatar);
		final ImageView iv_fail_resend = ViewHolder.get(convertView, R.id.iv_fail_resend);//失败重发
		final TextView tv_send_status = ViewHolder.get(convertView, R.id.tv_send_status);//发送状态
		TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
		TextView tv_message = ViewHolder.get(convertView, R.id.tv_message);
		//图片
		ImageView iv_picture = ViewHolder.get(convertView, R.id.iv_picture);
		final ProgressBar progress_load = ViewHolder.get(convertView, R.id.progress_load);//进度条
		//位置
		TextView tv_location = ViewHolder.get(convertView, R.id.tv_location);
		
		//点击头像进入个人资料
		String avatar = item.getBelongAvatar();
		if(avatar!=null && !avatar.equals("")){
			ImageLoader.getInstance().displayImage(avatar, iv_avatar, ImageLoadOptions.getOptions());
		}else{
			iv_avatar.setImageResource(R.drawable.head);
		}
		
		iv_avatar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(mContext,SetMyInfoActivity.class);
				if(getItemViewType(position) == TYPE_RECEIVER_TXT ||getItemViewType(position) == TYPE_RECEIVER_IMAGE||getItemViewType(position)==TYPE_RECEIVER_LOCATION){
					intent.putExtra("from", "other");
					intent.putExtra("username", item.getBelongUsername());
				}else{
					intent.putExtra("from", "me");
				}
				mContext.startActivity(intent);
			}
		});
		
		tv_time.setText(TimeUtil.getChatTime(Long.parseLong(item.getMsgTime())));
		
		if(getItemViewType(position)==TYPE_SEND_TXT||getItemViewType(position)==TYPE_SEND_IMAGE||getItemViewType(position)==TYPE_SEND_LOCATION){//只有自己发送的消息才有重发机制
			//状态描述
			if(item.getStatus()==BmobConfig.STATUS_SEND_SUCCESS){//发送成功
				progress_load.setVisibility(View.INVISIBLE);
				iv_fail_resend.setVisibility(View.INVISIBLE);
				tv_send_status.setVisibility(View.VISIBLE);
				tv_send_status.setText("已发送");
			}else if(item.getStatus()==BmobConfig.STATUS_SEND_FAIL){//服务器无响应或者查询失败等原因造成的发送失败，均需要重发
				progress_load.setVisibility(View.INVISIBLE);
				iv_fail_resend.setVisibility(View.VISIBLE);
				tv_send_status.setVisibility(View.INVISIBLE);
			}else if(item.getStatus()==BmobConfig.STATUS_SEND_RECEIVERED){//对方已接收到
				progress_load.setVisibility(View.INVISIBLE);
				iv_fail_resend.setVisibility(View.INVISIBLE);
				tv_send_status.setVisibility(View.VISIBLE);
				tv_send_status.setText("已阅读");
			}else if(item.getStatus()==BmobConfig.STATUS_SEND_START){//开始上传
				progress_load.setVisibility(View.VISIBLE);
				iv_fail_resend.setVisibility(View.INVISIBLE);
				tv_send_status.setVisibility(View.INVISIBLE);
			}
		}
		
		//根据类型显示内容
		final String text = item.getContent();
		switch (item.getMsgType()) {
		case BmobConfig.TYPE_TEXT:
			try {
				SpannableString spannableString = FaceTextUtils
						.toSpannableString(mContext, text);
				tv_message.setText(spannableString);
			} catch (Exception e) {
			}
			break;

		case BmobConfig.TYPE_IMAGE://图片类
			try {
				if (text != null && !text.equals("")) {
					ImageLoader.getInstance().displayImage(text, iv_picture,ImageLoadOptions.getOptions(),new ImageLoadingListener() {
								
								@Override
								public void onLoadingStarted(String imageUri, View view) {
									// TODO Auto-generated method stub
									progress_load.setVisibility(View.VISIBLE);
									if(getItemViewType(position)==TYPE_SEND_IMAGE){//若为发送的图片
										iv_fail_resend.setVisibility(View.INVISIBLE);
										tv_send_status.setVisibility(View.INVISIBLE);
									}
								}
								
								@Override
								public void onLoadingFailed(String imageUri, View view,
										FailReason failReason) {
									// TODO Auto-generated method stub
									progress_load.setVisibility(View.INVISIBLE);
								}
								
								@Override
								public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
									// TODO Auto-generated method stub
									progress_load.setVisibility(View.INVISIBLE);
									if(getItemViewType(position)==TYPE_SEND_IMAGE){//若为发送的图片
										iv_fail_resend.setVisibility(View.INVISIBLE);
										tv_send_status.setVisibility(View.VISIBLE);
									}
								}
								
								@Override
								public void onLoadingCancelled(String imageUri, View view) {
									// TODO Auto-generated method stub
									progress_load.setVisibility(View.INVISIBLE);
									
								}
							});
				}
				iv_picture.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent =new Intent(mContext,ImageBrowserActivity.class);
						ArrayList<String> photos = new ArrayList<String>();
						photos.add(text);
						intent.putStringArrayListExtra("photos", photos);
						intent.putExtra("position", 0);
						mContext.startActivity(intent);
					}
				});
			} catch (Exception e) {
			}
			break;
			
		case BmobConfig.TYPE_LOCATION://位置信息
			try {
				if (text != null && !text.equals("")) {
					String address  = text.split("&")[0];
					final String latitude = text.split("&")[1];//维度
					final String longtitude = text.split("&")[2];//经度
					tv_location.setText(address);
					tv_location.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(mContext, LocationActivity.class);
							intent.putExtra("type", "scan");
							intent.putExtra("latitude", Double.parseDouble(latitude));//维度
							intent.putExtra("longtitude", Double.parseDouble(longtitude));//经度
							mContext.startActivity(intent);
						}
					});
				}
			} catch (Exception e) {
				
			}
			break;
		default:
			break;
		}
		
		
		return convertView;
	}
	
}
