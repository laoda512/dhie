package com.tavx.C9Alarm;

//import java.util.HashMap;
//import java.util.Map;

import util.MyToast;
import util.PhoneInfo;
import util.md5;
import weibo.WeiboConstant;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import cn.bmob.v3.BmobUser;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.util.CacheManager;

import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.ui.LoginActivity;
import com.tavx.C9Alam.connector.AlarmConnector;
import com.tavx.C9Alam.connector.Connector;
import com.tavx.C9Alam.connector.LoginManager;
import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.Service.LifeService;
import com.tavx.C9Alarm.bd.AdView;
import com.tavx.C9Alarm.bw.OffersManager;
import com.tavx.C9Alarm.cloud.CloudManager;
import com.tavx.C9Alarm.cloud.Bean.Bmob.functionLock;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.message.PushAgent;
import com.umeng.socialize.controller.UMSocialService;

public class AlarmApplication extends Application {
	static final String gameName = "闹钟娘";
	public WeiboConstant weiboUser;
	public static AlarmApplication myApp;
	private UMSocialService mController;

	public static final String path = "/tavx/alarm/";
	public AlarmUser user;

	public int deviceWidth, deviceHeight;

	private static ImageCache IMAGE_CACHE;

	public static functionLock mFunctionLock;

	public static synchronized ImageCache getImageCache() {
		if (IMAGE_CACHE == null)
			IMAGE_CACHE = CacheManager.getImageCache();
		return IMAGE_CACHE;
	}

	public static functionLock getFLock() {
		if (mFunctionLock == null) {

			mFunctionLock = new functionLock();
			CloudManager.queryLock(AlarmApplication.getApp());
		}
		return mFunctionLock;
	}

	public static synchronized AlarmApplication getApp() {
		if (myApp == null)
			myApp = new AlarmApplication();

		return myApp;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		LifeService.startMe(this);
		myApp = this;
		weiboUser = new WeiboConstant(this);
		// mController = UMServiceFactory.getUMSocialService("com.umeng.share",
		// RequestType.SOCIAL);
		// mController.set setChannel(String channel)
		// mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// com.umeng.socom.Log.LOG=true;
		user = AlarmUser.getUser(this);

		MobclickAgent.updateOnlineConfig(this);
		// Form.inialSound(this);
		getScreenInfo((WindowManager) getSystemService(WINDOW_SERVICE));

		AdView.setAppSid(this, "d1f6a1a9");// 其中的debug需改为您的APPSID
		AdView.setAppSec(this, "d1f6a1a9");// 其中的debug需改为您的计费名
		OffersManager.setAppSid("d1f6a1a9");// 其中的debug需改为您的APPSID
		OffersManager.setAppSec("d1f6a1a9");// 其中的debug需改为您的计费名

		FeedbackAgent agent = new FeedbackAgent(this);
		PushAgent mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable();
		agent.sync();

		if (user.getFirstLogin() == true) {
			MyLogger.e("first_Logiin", "aaaa");
			user.setSleepTime(10);
			user.setFirstLogin(false);
			// PointManager.addPoint("200");
		}
	}

	private float fitRateX;
	private float fitRateY;

	public float getFitRateX() {
		if (Math.abs(fitRateX - 0.000001) < 0) {
			WindowManager mWm = (WindowManager) getSystemService(WINDOW_SERVICE);
			getScreenInfo(mWm);
		}

		return fitRateX;
	}

	public float getFitRateY() {
		if (Math.abs(fitRateY - 0.000001) < 0) {
			WindowManager mWm = (WindowManager) getSystemService(WINDOW_SERVICE);
			getScreenInfo(mWm);
		}
		return fitRateY;
	}

	private void getScreenInfo(WindowManager mWindowManager) {
		DisplayMetrics dm = new DisplayMetrics();
		mWindowManager.getDefaultDisplay().getMetrics(dm);
		int screen_width = dm.widthPixels;// 宽度
		int screen_height = dm.heightPixels;// 高度
		fitRateX = screen_width / 1080f;
		fitRateY = screen_height / 1920f;
	}

	public void showToast(String content) {
		MyLogger.e("toast", "" + content);
		MyToast.getInatance().ShowToast(content);
	}

	public void showError(String error) {
		MyToast.getInatance().ShowErrorToast(error);
	}

	public UMSocialService getCommentController() {
		return mController;
	}

	public String getUserNickName() {
//		
//		User u = AlarmApplication.getApp().getBmobUser();
//		String nickName = null;
//		if(u!=null){
//			nickName = u.getNick();
//		}
//		
//		return nickName;
		return getUser().getUserNickName();
	}

	public void setUserNickName(String name) {
		
//		User u = AlarmApplication.getApp().getBmobUser();
//		if(u!=null){
//			u.setNick(name);
//		}else{
//			
//		}
		 getUser().setUserNickName(name);
	}

	public int getSleepTime() {
		return user.getSleepTime();
	}

	public void setSleepTime(int i) {
		user.setSleepTime(i);
	}

	public AlarmUser getUser() {
		return user;
	}

	public boolean getIsVibrate() {
		return (user.getAlarmRingMode() & Symbol.RINGMODE_VIBRATE) == Symbol.RINGMODE_VIBRATE;
	}

	public boolean getIsSlowLoud() {
		return (user.getAlarmRingMode() & Symbol.RINGMODE_SLOWLOUD) == Symbol.RINGMODE_SLOWLOUD;
	}

	public boolean getIsMusic() {
		return (user.getAlarmRingMode() & Symbol.RINGMODE_MUSIC) == Symbol.RINGMODE_MUSIC;
	}

	public boolean checkNetWork(boolean showToast) {
		boolean isAv = PhoneInfo.checkNetWork(AlarmApplication.getApp());
		if (isAv == true) {
			return true;
		}
		if (showToast) {
			showError("网络似乎不太正常，连接的异常吃力啊！");
		}
		return false;
	}

	public void onDestroy() {

	}

	public String getVersion() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "1.2";
		}
	}
	
	
	public User getBmobUser(){
		return LoginManager.getInstance().getUser(this);
	}
	
	public  void setConnector(){
		AlarmConnector.setConnector(new Connector() {
			
			@Override
			public void Register() {
				 
			}

			@Override
			public void afterRegister(Activity a) {
				a.finish();
			}

			@Override
			public void afterLogin(Activity a) {
				//Intent i = new Intent();
				//i.setClass(a, )
				a.finish();
			}
			public Application getApplication(){
				return getApp();
			}

			@Override
			public void showToast(String content) {
				AlarmApplication.getApp().showToast(content);
			}

			@Override
			public String getUserDefaultNumber() {
				return "LabMan"+PhoneInfo.getIMEI(getApplication()).substring(0, 4).replace("null", "1415");
			}

			@Override
			public String getDeviceId() {
				// TODO Auto-generated method stub
				return PhoneInfo.getIMEI(getApplication());
			}

			@Override
			public String getDeviceKey() {
				// TODO Auto-generated method stub
				return md5.md5s(PhoneInfo.getIMEI(getApplication())+"@_tavx");
			}
		});
	}
	
	public void loginForeGround(Activity mActivity){
		LoginManager.getInstance().LoginByActivity(mActivity);
	}
	
}
