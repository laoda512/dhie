package com.tavx.C9Alarm.adapter;

import java.util.List;

import org.json.JSONObject;

import util.BitmapWorker;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.R;
import com.tavx.C9Alarm.Form.Form;
import com.tavx.C9Alarm.bd.AdView;
import com.tavx.C9Alarm.bd.AdViewListener;
import com.tavx.C9Alarm.bean.TagHolder;
import com.tavx.C9Alarm.listener.OnThemeItemCheckedListener;

public class themeAdapter extends BaseAdapter {

	List<TagHolder> mList;
	LayoutInflater mLayoutInflater;
	Context c;
	int checkPos = 0;
	OnThemeItemCheckedListener mOnThemeItemCheckedListener;
	int height = 0;

	public themeAdapter(List<TagHolder> mList, Context c,
			OnThemeItemCheckedListener l, int checkedPositon) {
		super();
		this.mList = mList;
		mLayoutInflater = LayoutInflater.from(c);
		this.c = c;
		this.mOnThemeItemCheckedListener = l;
		checkPos = checkedPositon;
		 adView = new AdView(c);
		 
		 adView.setListener(new AdViewListener() {
				public void onAdSwitch() {
					Log.w("", "onAdSwitch");
				}
				public void onAdShow(JSONObject info) {
					Log.w("", "onAdShow " + info.toString());
				}
				public void onAdReady(AdView adView) {
					Log.w("", "onAdReady " + adView);
				}
				public void onAdFailed(String reason) {
					Log.w("", "onAdFailed " + reason);
				}
				public void onAdClick(JSONObject info) {
					Log.w("", "onAdClick " + info.toString());
				}
				public void onVideoStart() {
					Log.w("", "onVideoStart");
				}
				public void onVideoFinish() {
					Log.w("", "onVideoFinish");
				}
				@Override
				public void onVideoClickAd() {
					Log.w("", "onVideoFinish");
				}
				@Override
				public void onVideoClickClose() {
					Log.w("", "onVideoFinish");
				}
				@Override
				public void onVideoClickReplay() {
					Log.w("", "onVideoFinish");
				}
				@Override
				public void onVideoError() {
					Log.w("", "onVideoFinish");
				}
			});
	}

	@Override
	public int getCount() {
		if(height==0) return 0;
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		if(position<mList.size())
		return mList.get(position);
		else return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	public void setItemHeight(int height){
		this.height = height;	
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		MyLogger.e("aaaa", " " + position);
		
		
		
		ViewHolder mViewHolder;
		TagHolder mTagHolder = (TagHolder) getItem(position); ;
		
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.items_themechoose,
					null);
			mViewHolder = new ViewHolder();
			mViewHolder.tv = (CheckedTextView) convertView
					.findViewById(R.id.tvTaq);
			mViewHolder.layout = (RelativeLayout) convertView
					.findViewById(R.id.list_item);
			convertView.setTag(mViewHolder); 
			
			if(mTagHolder!=null)
			mViewHolder.tv.setBackgroundDrawable(mTagHolder.mForm
					.getSettingPageTagDrawable(mViewHolder.tv,
							android.R.attr.state_checked));
			
			mViewHolder.tv.getLayoutParams().height=height;
			
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		
		
//		if(position==getCount()-1){
//			
//			addBaidu(mViewHolder.layout);
//			mViewHolder.tv.setVisibility(View.INVISIBLE);
//			return convertView;
//			}else{
//				mViewHolder.tv.setVisibility(View.VISIBLE);
//				adView.setVisibility(View.GONE);
//			}
		
		Drawable d = mViewHolder.tv.getBackground();
		Form  buf_form= mTagHolder.mForm;
		int color = buf_form.getSettingPageTagColorFilter();
		d.setColorFilter(color, Mode.MULTIPLY);
		
		if(height!=0){
		int pad = (int) (height * 0.05f);
		int tagIconHeight = pad * 16;
		int tagIconWidth = pad * 11;

		recyleBitmap(mViewHolder.tv, false);
		
		MyLogger.e("bitmap", "themeadapter" ,true);
		Bitmap btpbuff = Form.getBitmapByRes(mTagHolder.mForm.getSettingPageBg(),mTagHolder.mForm.getTitleName(),tagIconWidth + pad * 4,tagIconHeight);

//				BitmapWorker.getBitmap(
//				mTagHolder.mFrom.getSettingPageBgStream(), tagIconWidth + pad * 4,
//				tagIconHeight);
		BitmapDrawable mBitmapDrawable = new BitmapDrawable(btpbuff);
		mBitmapDrawable.setBounds(pad * 4, -pad, tagIconWidth + pad * 4,
				tagIconHeight);
		mViewHolder.tv.setCompoundDrawablePadding(0);
		mViewHolder.tv.setCompoundDrawables(mBitmapDrawable, null, null, null);
		}

		if (checkPos == position) {// alpha is 40
			mViewHolder.tv.setChecked(true);
			if (mViewHolder.tv.getCompoundDrawables()[0] != null) {
				mViewHolder.tv.getCompoundDrawables()[0].setColorFilter(null);
			}
		} else {
			mViewHolder.tv.setChecked(false);
			if (mViewHolder.tv.getCompoundDrawables()[0] != null) {
				mViewHolder.tv.getCompoundDrawables()[0].setColorFilter(
						Color.GRAY, Mode.MULTIPLY);
			}
		}
		mViewHolder.tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mOnThemeItemCheckedListener.onItemChecked(position);
				checkPos = position;
				notifyDataSetChanged();
			}
		});
		mViewHolder.tv.setText(mTagHolder.name);

		return convertView;
	}
	
	private void recyleBitmap(TextView tv,boolean isAll){
		Drawable[] md =tv.getCompoundDrawables();

		tv.setCompoundDrawablesWithIntrinsicBounds(null, null,
				null, null);
		if (md != null && md.length > 0) {
			for (int md_i = 0; md_i < md.length; md_i++) {
				if (md[md_i] != null) {
					if (md[md_i] instanceof BitmapDrawable) {
						BitmapWorker
								.recyleBitmap(((BitmapDrawable) md[md_i])
										.getBitmap());
						md[md_i] = null;
						// ((BitmapDrawable)md[md_i]).getBitmap().recycle();
					}
				}
			}
		}
		if (isAll) {
			Drawable mb = tv.getBackground();
			tv.setBackgroundDrawable(null);
			if (mb != null) {

				if (mb instanceof BitmapDrawable) {
					Bitmap b = ((BitmapDrawable) mb).getBitmap();
					if (b != null) {
						BitmapWorker.recyleBitmap(b);
						mb = null;
					}

				}
			}
		}
	}
	AdView adView;
	private void addBaidu(RelativeLayout rlMain ){

		adView.setVisibility(View.VISIBLE);
		if(adView.getParent()!=null)
	(	(RelativeLayout)(adView.getParent())).removeView(adView);
		 
		rlMain.addView(adView);
		adView.setListener(new AdViewListener() {
			
			@Override
			public void onVideoStart() {
				// TODO Auto-generated method stub
				MyLogger.e("aaa", "onAdClick");
			}
			
			@Override
			public void onVideoFinish() {
				// TODO Auto-generated method stub
				MyLogger.e("aaa", "onVideoFinish");
			}
			
			@Override
			public void onVideoError() {
				// TODO Auto-generated method stub
				MyLogger.e("aaa", "onVideoError");
			}
			
			@Override
			public void onVideoClickReplay() {
				// TODO Auto-generated method stub
				MyLogger.e("aaa", "onVideoClickReplay");
			}
			
			@Override
			public void onVideoClickClose() {
				// TODO Auto-generated method stub
				MyLogger.e("aaa", "onVideoClickClose");
			}
			
			@Override
			public void onVideoClickAd() {
				// TODO Auto-generated method stub
				MyLogger.e("aaa", "onVideoClickAd");
			}
			
			@Override
			public void onAdSwitch() {
				MyLogger.e("aaa", "onAdSwitch");
				
			}
			
			@Override
			public void onAdShow(JSONObject arg0) {
				MyLogger.e("aaa", "onAdShow");
				
			}
			
			@Override
			public void onAdReady(AdView arg0) {
				MyLogger.e("aaa", "onAdReady");
				
			}
			
			@Override
			public void onAdFailed(String arg0) {
				MyLogger.e("aaa", "onAdFailed");
				
			}
			
			@Override
			public void onAdClick(JSONObject arg0) {
				MyLogger.e("aaa", "onAdClick");
			}
		});
	
	}
	
	class ViewHolder {
		public RelativeLayout layout;
		CheckedTextView tv;
	}
}
