package com.tavx.C9Alarm;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import cn.bmob.v3.Bmob;

import com.ShareSDK.iniShare;
import com.tavx.C9Alarm.cloud.CloudManager;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends FragmentActivity{
	protected String TAG = getClass().getName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	ShareSDK.initSDK(this);
		iniShare.iniShare(this);
		  Bmob.initialize(this,"f5fc740f8d9c681b345d883b33c9b124");
		
		 Thread.setDefaultUncaughtExceptionHandler(getErrorhandler());
			  
	}

	
	static UncaughtExceptionHandler Errorhandler;
	
	private synchronized UncaughtExceptionHandler getErrorhandler(){
		if(Errorhandler==null){
			Errorhandler =  new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				ex.printStackTrace();
				MobclickAgent.reportError(AlarmApplication.getApp(), ex);
				AlarmApplication.getApp().showError("程序似乎傲娇了,但是你的元气增加了！");
				CloudManager.reset(AlarmApplication.getApp());
				  // 退出程序,注释下面的重启启动程序代码
	            android.os.Process.killProcess(android.os.Process.myPid());  
	            System.exit(1); 
	            
//	           Intent intent = new Intent();
//	           intent.setClass(mContext,MainActivity.class);
//	           intent.addFlag(Intent.FLAG_ACTIVITY_NEW_TASK);
//	           mContext.startActivity(intent);
//	           android.os.Process.killProcess(android.os.Process.myPid());
			}
		};
		}
		
		return Errorhandler;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
	
		MobclickAgent.onPause(this);
	}
/* (non-Javadoc)
 * @see android.support.v4.app.FragmentActivity#onStop()
 */
@Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// this.save2();
			//timer.cancel();
			this.finish();
			// System.gc();
			// return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	
	public void setAlpha(View view,float alpha){
		if (Build.VERSION.SDK_INT < 11) {
//	        final AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
//	        animation.setDuration(0);
//	        animation.setFillAfter(true);
//	        view.startAnimation(animation);
	    } else
	        view.setAlpha(alpha);
	}
	
 

	protected boolean CheckIsCurrentTask(Context context) {
//		if (isScreenLocked(context)) {
//			return false;
//		}
		ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> a = mActivityManager.getRunningTasks(1);
		  Log.e("第一个", ""+a.get(0).baseActivity.getPackageName());
		if (!context.getPackageName().equals(a.get(0).baseActivity.getPackageName())) {
			return false;
		}
		return true;
	}

}
