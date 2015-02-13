package com.bmob.im.demo.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.bmob.im.BmobChat;

import com.bmob.im.demo.R;
import com.bmob.im.demo.config.Config;

/** 引导页
  * @ClassName: SplashActivity
  * @Description: TODO
  * @author smile
  * @date 2014-6-4 上午9:45:43
  */
public class SplashActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		// 初始化
		BmobChat.getInstance().init(this, Config.applicationId);
		
		if (userManager.getCurrentUser() != null) {
			mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
		}
	}
	
	private static final int GO_HOME = 100;
	private static final int GO_LOGIN =200;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				startAnimActivity(MainActivity.class);
				finish();
				break;
			case GO_LOGIN:
				startAnimActivity(LoginActivity.class);
				finish();
				break;
			}
		}
	};

}
