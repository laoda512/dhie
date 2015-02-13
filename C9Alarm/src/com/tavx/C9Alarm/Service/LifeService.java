/**
 * 
 */
package com.tavx.C9Alarm.Service;

import java.util.Timer;
import java.util.TimerTask;

import util.AlarmAlertWakeLock;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.nineoldandroids.animation.ValueAnimator;
import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.AlarmReceiver;
import com.tavx.C9Alarm.Symbol;

/**
 * @author Administrator
 *
 */
public class LifeService extends Service{
	public final String  TAG= getClass().getName(); 
	public static void startMe(Context c){
		Intent  i =new Intent();
		i.setClass(c, LifeService.class);
		//i.putExtra("do", "start_scan");
		c.startService(i);
		MyLogger.e("LifeService", "startMe");
	}
	AlarmReceiver mAlarmReceiver;
	private ValueAnimator timeAnimator;
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		runCounter();
        return START_STICKY;
    }
	/* (non-Javadoc)
	 * @see android.app.Service#onStart(android.content.Intent, int)
	 */
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		
		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		IntentFilter i = new IntentFilter();
		i.addAction(Symbol.ACTION_CANCEL);
		i.addAction(Symbol.ACTION_GIVEUP);
		i.addAction(Symbol.ACTION_WIN);
		i.addAction(Symbol.ACTION_LOSE);
		i.addAction(Symbol.ACTION_CANCEL);
		
		i.addAction(Symbol.ACTION_START_MAIN);
//		i.addAction(Symbol.ACTION_RECEIVE_ALARM);
//		i.addAction(Symbol.ACTION_PREPARE_TO_AWAKE);
		i.addAction(Intent.ACTION_BOOT_COMPLETED);
		
		
		mAlarmReceiver = new AlarmReceiver();
//		registerReceiver(mAlarmReceiver, i);
		
	}
	Timer t;
	public synchronized void  runCounter(){
		
		
		if(t!=null){
			t.cancel();
		}
		
		t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				MyLogger.e("timeAnimator", "run");
				Long Time =AlarmReceiver.getTime(getApplicationContext())-System.currentTimeMillis() ;
				if(Time>0&&Time<=5*60*1000){
					addScan(Time);
				}else if(Time==0){
					MyLogger.e("timeAnimator", "time on with no timer? ,start check");
					checkAlarm();
				}else{
					AlarmAlertWakeLock.release();
				}
			
				//runCounter();
			}
		},1000,30000);
	}
	public void addScan(Long Time){
		MyLogger.e("timeAnimator", "find alarm after "+Time);
		
		if(buff!=null){
			MyLogger.e("timeAnimator", "already have timer,return");
			return ;
		}
		AlarmAlertWakeLock.acquireCpu(AlarmApplication.getApp());
		MyLogger.e("timeAnimator", "add timer for alarm comming soon");
		buff =new Timer();
		buff.schedule(new TimerTask() {
			@Override
			public void run() {
				MyLogger.e("timeAnimator", "time now ,start check");
				checkAlarm();
				buff = null;
			}
		}, Time);
	}
	Timer buff;
	/* (non-Javadoc)
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		//if(mAlarmReceiver!=null)
		//unregisterReceiver(mAlarmReceiver);
		super.onDestroy();
	}
	/**
	 * @param time
	 */
	public static void setTime(Long time) {
	}
	
	class AnimHolder {
		public AnimHolder(int weight) {
			super();
			this.weight = weight;
		}

		int weight;

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}
	}
	Handler mHandler = new Handler(){
		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			AlarmReceiver.checkAlarm(getApplicationContext());
			super.handleMessage(msg);
		}
	};
	public void checkAlarm(){
		mHandler.sendEmptyMessage(0);
	}
	
	public class MsgBinder extends Binder{  
        public void addScan(){
        	Long Time =AlarmReceiver.getTime(getApplicationContext())-System.currentTimeMillis() ;
        	if(Time>0)
        	LifeService.this.addScan(Time);
        }
    }  
}
