
package com.tavx.C9Alarm;

import util.AnimationsContainer;
import util.AnimationsContainer.FramesSequenceAnimation;
import util.BitmapWorker;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.Form.Form;
import com.tavx.C9Alarm.Manager.HotInfoManager;

public class SplashScreen extends BaseActivity {
	protected boolean _active = true;
	protected int _splashTime = 2000;
	boolean ismm = false;
	View background;
	Bitmap b;
	BitmapDrawable mBitmapDrawable;
	ImageView loading;
	private FramesSequenceAnimation anim;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
        setContentView(R.layout.splash9c);
        background = findViewById(R.id.relativeLayout1);
        loading = (ImageView) findViewById(R.id.loading);
     

        
        int mWidth = 1080;//宽度
        int mHeight = 1920 ;//高度
        int realWidth = (int) (mWidth*AlarmApplication.getApp().getFitRateX());
        int realHeight = (int) (mHeight*AlarmApplication.getApp().getFitRateY());
        
     
        MyLogger.e("bitmap", "splash" ,true);
        b = BitmapWorker.getBitmap(this, R.drawable.slash,(int)(1080*AlarmApplication.getApp().getFitRateX()),(int)(1920*AlarmApplication.getApp().getFitRateY()));
        mBitmapDrawable = new BitmapDrawable(b);
        background.setBackgroundDrawable(mBitmapDrawable);
        
       // AdManager.getInstance(this).init("a2977ba40642bdb9", "9ea7cccf1c889ec8", false);
        
       
        anim = AnimationsContainer.getInstance().createSplashAnim(loading,
				R.anim.loading, (int)(700*AlarmApplication.getApp().getFitRateX()), (int)(264*AlarmApplication.getApp().getFitRateY()));
        anim.setOverRun(true);
        anim.start();
        
    
       // String device_token = UmengRegistrar.getRegistrationId(this);
      //  MyLogger.e("aaaa", device_token);
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                	  iniRes();
                } catch(Exception e) {
                    // do nothing
                	e.printStackTrace();
                } finally {
                	anim.stop();
                	anim.recyle();
                	startActivity(new Intent(SplashScreen.this,boxAlarmActivity.class));

                    finish();
                    // 启动主应用
                   
                    {
                    	                    }
                    _active=false;
                }
            }
        }; 
        splashTread.start();
        
     
      
        
       
    }
	
	Handler mHandler;

	static final String gameID = "1060318632";
	static final String gameKey = "QcnONvB0fTZhfO71khUuA";
	static final String gameSecret = "p2gLs0kFMvF6xYLHSisFzU58izi7rxAf";

	@Override
	protected void onDestroy() {
		background.setBackgroundDrawable(null);
		mBitmapDrawable = null;
		BitmapWorker.recyleBitmap(b);
		b=null;
		super.onDestroy();

	}

	public void iniRes() {

		/* 
		 * //�������Ҫ�Զ���һЩOpenFeintSettings�Ĳ��� Map<String, Object> options =
		 * new HashMap<String, Object>();
		 * options.put(OpenFeintSettings.SettingCloudStorageCompressionStrategy,
		 * OpenFeintSettings.CloudStorageCompressionStrategyDefault);
		 * OpenFeintSettings settings = new OpenFeintSettings(AppName,
		 * Product_Key, Product_Secret, Client_Application_ID, options);
		 */
		MyLogger.e("aaa",getDeviceInfo(this));
		Form.inialize(this);
		
		
		
		_active = false;
		Media.getInstance(this);
		checkSleepTime();
	}

	
	public static String getDeviceInfo(Context context) {
	    try{
	      org.json.JSONObject json = new org.json.JSONObject();
	      android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
	          .getSystemService(Context.TELEPHONY_SERVICE);
	  
	      String device_id = tm.getDeviceId();
	      
	      android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	          
	      String mac = wifi.getConnectionInfo().getMacAddress();
	      json.put("mac", mac);
	      
	      if( TextUtils.isEmpty(device_id) ){
	        device_id = mac;
	      }
	      
	      if( TextUtils.isEmpty(device_id) ){
	        device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
	      }
	      
	      json.put("device_id", device_id);
	      
	      return json.toString();
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	  return null;
	}
	
	
	private void checkSleepTime(){
		HotInfoManager.getInstance().checkSleepTime();
	}
	
	



}