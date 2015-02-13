/**
 * 
 */
package com.tavx.C9Alarm.Manager;

import util.AlarmAlertWakeLock;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.AlarmBean;
import com.tavx.C9Alarm.AlarmProvider;
import com.tavx.C9Alarm.Symbol;
import com.tavx.C9Alarm.Utils;
import com.tavx.C9Alarm.Form.Form;

/**
 * @author Administrator
 * 
 */
public class SleepManager {
	public static final String TAG = "SleepManager";
	public static final int AWAKE = 0;
	public static final int SNOOZE = 1;
	public static final int INTERRUPTED = 2;
	public static final int NEW_ALAEM = 3;
	private static SleepManager mSleepManager;
	AlarmShower mAlarmShower;
	Thread autoOff;
	KeyguardLock mKeyguardLock;
	KeyguardManager mKeyguardManager;

	public SleepManager() {
		super();
		mKeyguardManager = (KeyguardManager) AlarmApplication.getApp()
				.getSystemService(Context.KEYGUARD_SERVICE);
	}

	public static SleepManager getInstance() {
		MyLogger.e(TAG, "getInstance");
		if (mSleepManager == null)
			mSleepManager = new SleepManager();
		return mSleepManager;
	}

	public void startNewAlarm(AlarmShower mAlarmShower) {
		if (this.mAlarmShower != null) {
			endAwake(NEW_ALAEM);
		}
		this.mAlarmShower = mAlarmShower;
		startAwake();
	}

	public void startAwake() {

		AlarmAlertWakeLock.acquire(AlarmApplication.getApp());
		disableKeyguard();
		
		if (mAlarmShower.isTest()==false&&AlarmApplication.getApp().getUser().getSleepTime()>0) {
			startAutoOff();
		}
		
		if (mAlarmShower.isShowShare()) {

			Utils.addorStartSleepTime(AlarmApplication.getApp());
			AlarmProvider.geInstance(AlarmApplication.getApp())
					.disableSleepAlarm(0);

			if (mAlarmShower.getAlarmBean().getDay() == Symbol.DAY_ONCE) {
				AlarmProvider.geInstance(AlarmApplication.getApp())
						.setAlarmEnable(mAlarmShower.getAlarmBean().getIndex(),
								false);
			}
		}
	}

	public synchronized void endAwake(int type) {
		MyLogger.e(TAG, "endAwake"+" "+type);
		if (mAlarmShower == null)
			return;

		switch (type) {
		case AWAKE: {
			if (mAlarmShower.isShowShare()) {
				tryToAwake();
			}
			closeAutoOff();
		}
			break;
		case SNOOZE: {
			onCancelWakeUp();
		}
			break;
		case NEW_ALAEM: {
			closeAutoOff();
		}
		
			break;
			
		case INTERRUPTED: {
			closeAutoOff();
			if (mAlarmShower.isShowShare()) {
				tryToAwake();
			}
			
		}

		default:
			break;
		}
		mAlarmShower.closeAlarm();
		enableKeyguard();
		AlarmAlertWakeLock.release();
		mAlarmShower = null;
	}

	private void closeAutoOff() {
		if (autoOff != null && autoOff.isAlive()) {
			autoOff.interrupt();
		}
	}

	private void startAutoOff() {
		closeAutoOff();
		autoOff = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(60000);
					endAwake(SNOOZE);
				} catch (InterruptedException e) {
					e.printStackTrace();
					// endAwake(INTERRUPTED);
				}
			}

		});
		autoOff.start();
	}

	private synchronized void tryToAwake() {
		// isOver = true;
		Utils.GameStart();
		Intent intent = new Intent(Symbol.ACTION_WIN);
		intent.putExtra("alarm_form", mAlarmShower.getAlarmBean()
				.getFormIndex());
		AlarmApplication.getApp().sendBroadcast(intent);
		ADManager.AlarmAwake();
	}

	private void onCancelWakeUp() {
		if (mAlarmShower.isTest() == true) {
//			AlarmApplication.getApp().showToast(
//					"点击动画后者按下两次返回键，闹钟会在"
//							+ mAlarmShower.getAlarmBean().getSleepDelay()
//							+ "分钟后再响，可以在主界面中取消");

		} else {
			if (AlarmApplication.getApp().getUser().getSleepTime()> 0) {
				AlarmApplication.getApp().showToast(
						"闹钟将会在" + AlarmApplication.getApp().getUser().getSleepTime()
								+ "分钟后再响，可以在主界面中取消");
				stillSleep();
			} else {
				tryToAwake();
			}
		}
	}

	private synchronized void stillSleep() {
		Intent intent = new Intent(Symbol.ACTION_LOSE);
		intent.putExtra("alarm_form", mAlarmShower.getAlarmBean()
				.getFormIndex());
		AlarmApplication.getApp().sendBroadcast(intent);

		//
		//
		// AlarmBean ab =
		// AlarmProvider.geInstance(AlarmApplication.getApp()).getAlarm(mAlarmShower.getAlarmBean().getIndex());
		// ab.setDay(Symbol.DAY_ONCE);// sym
		// ab.setInterval(mAlarmShower.getAlarmBean().getSleepDelay() * 1000 *
		// 60l);// sym
		// ab.setTime(System.currentTimeMillis() + ab.getInterval());
		// ab.setIndex(0);// sleepalarm ,start from 0
		// ab.setEnabled(true);
		//
		// AlarmProvider.geInstance(AlarmApplication.getApp()).setSleepAlarm(ab);
		//
		// Utils.GameReset();
		// AlarmSet.setAlarm(AlarmApplication.getApp());

	}
	
	private synchronized void cancel() {
		Intent intent = new Intent(Symbol.ACTION_LOSE);
		intent.putExtra("alarm_form", mAlarmShower.getAlarmBean()
				.getFormIndex());
		AlarmApplication.getApp().sendBroadcast(intent);


	}

	private synchronized void enableKeyguard() {
		if (mKeyguardLock != null) {
			mKeyguardLock.reenableKeyguard();
			mKeyguardLock = null;
		}
	}

	private synchronized void disableKeyguard() {
		if (mKeyguardLock == null) {
			mKeyguardLock = mKeyguardManager.newKeyguardLock("tavx");
			mKeyguardLock.disableKeyguard();
		}
	}

	// private CloseSystemDialogsReceiver mCloseSystemDialogsReceiver;
	// private class CloseSystemDialogsReceiver extends BroadcastReceiver {
	// final String SYSTEM_DIALOG_REASON_KEY = "reason";
	// final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
	//
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
	// String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
	// if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
	// onCancelWakeUp();
	// try {
	// AlarmApplication.getApp().unregisterReceiver(mCloseSystemDialogsReceiver);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }
	//
	// }
	// }

	public interface AlarmShower {

		public boolean isTest();

		public boolean isShowShare();

		public AlarmBean getAlarmBean();

		public Form getForm();

		public void closeAlarm();

	}

}
