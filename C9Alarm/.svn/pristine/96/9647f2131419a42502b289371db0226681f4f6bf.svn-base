/**
 * 
 */
package com.tavx.C9Alarm.Manager;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.offers.OffersManager;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.bd.AdView;
import com.tavx.C9Alarm.bd.AdViewListener;
import com.umeng.newxp.common.ExchangeConstants;
import com.umeng.newxp.controller.ExchangeDataService;
import com.umeng.newxp.view.ExchangeViewManager;


/**
 * @author Administrator
 *
 */
public class ADManager {
	
	public static void clickBaiduBanner(){
		int howMuch = PointManager.PointChange(PointManager.type_ad, PointManager.tag_ad_baidubanner);
		if(howMuch==-1)
		ToastTontentManager.getInstance().toast(ToastTontentManager.type_ad, ToastTontentManager.tag_ad_baidubanner);
		else
			ToastTontentManager.getInstance().toast(ToastTontentManager.type_ad, ToastTontentManager.tag_ad_baidubanner,""+howMuch);
	}
	
	public static void normalClick(){
		int howMuch = PointManager.PointChange(PointManager.type_btn_click, PointManager.tag_btn_click_normal);
		if(howMuch==-1)
		ToastTontentManager.getInstance().toast(ToastTontentManager.type_btn_click, ToastTontentManager.tag_btn_click_normal);
		else
			ToastTontentManager.getInstance().toast(ToastTontentManager.type_btn_click, ToastTontentManager.tag_btn_click_normal,""+howMuch);
	}
	
	public static void AlarmAwake(){
		int howMuch = PointManager.PointChange(PointManager.type_Alarm, PointManager.tag_Alarm_awake);
		if(howMuch==-1)
		ToastTontentManager.getInstance().toast(ToastTontentManager.type_Alarm, ToastTontentManager.tag_Alarm_awake);
		else
			ToastTontentManager.getInstance().toast(ToastTontentManager.type_Alarm, ToastTontentManager.tag_Alarm_awake,""+howMuch);
	}
	
	
	
	
	public static AdView getBanner(){
	return null;	
	}
	
	public static void addBanner(RelativeLayout rlMain,Context c,final Handler onClickhandler){
		if(AlarmApplication.getApp().getFLock().isBaiduBannerOn()==false) return;
		
		AdView adView = new AdView(c);
		 
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
					Log.w("", "onAdClick " );
					ADManager.clickBaiduBanner();
					if(onClickhandler!=null)
					onClickhandler.sendEmptyMessage(0);
					
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
		

		adView.setVisibility(View.VISIBLE);
//		if(adView.getParent()!=null)
//	(	(RelativeLayout)(adView.getParent())).removeView(adView);
	//	rlMain.setVisibility(View.INVISIBLE);
		rlMain.addView(adView);
		
	
	}
	
	public static void addYoumiBanner(Context c,ViewGroup l){
		net.youmi.android.banner.AdView adView = new net.youmi.android.banner.AdView(c, AdSize.FIT_SCREEN);
		// 获取要嵌入广告条的布局
	//	LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
		// 将广告条加入到布局中
		l.addView(adView);
		adView.setAdListener(new net.youmi.android.banner.AdViewListener() {
			
			@Override
			public void onSwitchedAd(net.youmi.android.banner.AdView adview) {
				
				MyLogger.e("adview", "onswitch");
			}
			
			@Override
			public void onReceivedAd(net.youmi.android.banner.AdView adview) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailedToReceivedAd(net.youmi.android.banner.AdView adview) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	public static void addYouSmallBanner(Context c,ViewGroup l){
		//DiyBanner banner = new DiyBanner(c, DiyAdSize.SIZE_MATCH_SCREENx32);
		// Demo 2 迷你Banner : 宽320dp，高32dp
		// 传入高度为32dp 的 AdSize 来定义迷你 Banner
		
		// 将积分 Banner 加入到布局中
		//l.addView(banner);
	}
	
	public static void iniYoumi(){
		AdManager.getInstance(AlarmApplication.getApp()).init("a2977ba40642bdb9", "9ea7cccf1c889ec8", false);
		OffersManager.getInstance(AlarmApplication.getApp()).onAppLaunch();
	}
	
	
	public static void addHandlerAli(ImageView v,Activity a){
	        new ExchangeViewManager(a, new ExchangeDataService("60509"))
	        .addView(ExchangeConstants.type_list_curtain, v);
	}
}
