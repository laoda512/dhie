package com.tavx.C9Alarm;

import util.AlarmAlertWakeLock;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.Form.Form;

import holidaynotifaction.HolidayReceiver;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class AlarmReceiver extends BroadcastReceiver {
	Context c;
	Intent mIntent;

	NotificationManager nfManger;
	int index;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.c = context;
		this.mIntent = intent;
		nextIntent = null;
		MyLogger.e("AlarmReceiver", "receive_action"+intent.getAction());
		nfManger = (NotificationManager) context
				.getSystemService(Activity.NOTIFICATION_SERVICE);
		// Toast.makeText(context, "This the time", Toast.LENGTH_LONG).show();
		if (intent.getAction().equals(Symbol.ACTION_DONOTHING)) {
			// do nothing now
			((NotificationManager) context
					.getSystemService(Activity.NOTIFICATION_SERVICE)).cancel(0);

		} else if (intent.getAction().equals(Symbol.ACTION_RECEIVE_ALARM)
				|| intent.getAction().equals(Intent.ACTION_TIME_TICK)) {

			checkAlarm(context);

		} else if (intent.getAction().equals(Symbol.ACTION_PREPARE_TO_AWAKE)
				) {
			MyLogger.e("AlarmReceiver", "ACTION_PREPARE_TO_AWAKE");
			AlarmAlertWakeLock.acquireCpu(AlarmApplication.getApp());
		//	checkAlarm(context);

		} else if (intent.getAction().equals(Symbol.ACTION_LOSE)) {
			MyLogger.e("AlarmReceiver", "ACTION_LOSE");
			nfManger = (NotificationManager) c
					.getSystemService(Activity.NOTIFICATION_SERVICE);
			stillSleep();

		} else if (intent.getAction().equals(Symbol.ACTION_WIN)) {
			MyLogger.e("AlarmReceiver", "ACTION_WIN");
			nfManger = (NotificationManager) c
					.getSystemService(Activity.NOTIFICATION_SERVICE);
			awake();
		} else if (intent.getAction().equals(Symbol.ACTION_GIVEUP)) {
			nfManger = (NotificationManager) c
					.getSystemService(Activity.NOTIFICATION_SERVICE);
			giveup();
		} else if (intent.getAction().equals(Symbol.ACTION_START_MAIN)) {
			Intent i = new Intent(context, SplashScreen.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
			MyLogger.e("AlarmReceiver", "start_main_action");
		} else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			AlarmSet.setAlarm(c);
			HolidayReceiver.setAlarm(c);
			MyLogger.e("AlarmReceiver", "ACTION_BOOT_COMPLETED");
		} else if (intent.getAction().equals(Symbol.ACTION_START)) {
			Media.getInstance(context).Stop();
			MyLogger.e("AlarmReceiver", "ACTION_START");
		} else {
			MyLogger.e("AlarmReceiver", "unknow Action");
		}
	}

	/**
	 * @param context
	 */
	public static synchronized void checkAlarm(Context context) {

		Long time = getTime(context);
		if (time != -1) {
			// Media.getInstance(context).Stop();
			MyLogger.e(
					"receive",
					"ACTION_TIME_TICK" + System.currentTimeMillis() + " "
							+ getTime(context) + " "
							+ (System.currentTimeMillis() - getTime(context)));
			if (Math.abs(System.currentTimeMillis() - getTime(context)) < 3000) {

				int index = getIndex(context);
				AlarmReceiveActivityVideo.startAlarm(context, true, false,
						index);
				((NotificationManager) context
						.getSystemService(Activity.NOTIFICATION_SERVICE))
						.cancel(-1);
			}
			if (System.currentTimeMillis() - getTime(context) > 3000) {
				// nfManger.cancel(Symbol.NOTIFICATION_MESSAGE);
				AlarmSet.noticeMiss(context, getTime(context));
				cancelTime(context);
				AlarmSet.setAlarm(context, true);

			}

		}

	}

	Intent nextIntent;

	private synchronized void awake() {
		Utils.GameOver();
		MyLogger.e("AlarmReceiver", "awake");
		int time = Utils.finishSleepTime(c);

		boolean isLaiChuang = true;
		if (time == 0) {
			isLaiChuang = false;
		}

		Bundle bd = new Bundle();
		bd.putBoolean(c.getString(R.string.isLaiChuang), isLaiChuang);
		bd.putInt(c.getString(R.string.awakeTime), time);
		bd.putInt("alarm_index", index);
		nextIntent = new Intent(c, ShareToFriendActivity.class);

		nextIntent.putExtra(c.getString(R.string.sleepState), bd);
		nextIntent.putExtra("extra", mIntent.getExtras());
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		AlarmProvider.geInstance(c).disableSleepAlarm(0);

		nameIsToHard(mIntent.getExtras());

	}

	private synchronized void nameIsToHard(Bundle bd) {
		Utils.GameReset();
		// isOver = true;
		nfManger.cancel(Symbol.NOTIFICATION_RINGING);
		AlarmSet.setAlarm(c);
		String form = bd.getString("alarm_form");
		AlarmApplication.getApp().getUser().setLastFormTag(form);
		
		// Media.getInstance(c).Stop();
		// Intent i = new Intent();
		// i.setAction(Symbol.ACTION_DONOTHING);
		// i.setClass(c, AlarmReceiver.class);

		if (nextIntent != null) {
			DownlistActivity.startActivity(null, DownlistActivity.TYPE_SHARE,
					bd, "extra");
			// c.startActivity(nextIntent);

		} else {

		}

	}

	private synchronized void stillSleep() {
		AlarmProvider ap = AlarmProvider.geInstance(c);
		AlarmBean ab = ap.getAlarm(ap.getCurrendIndex());
		ab.setDay(Symbol.DAY_ONCE);// sym
		ab.setInterval(ab.getSleepDelay() * 1000 * 60l);// sym
		ab.setTime(System.currentTimeMillis() + ab.getInterval());
		ab.setIndex(0);// sleepalarm ,start from 0
		// ab.setName("10fenyan");
		ab.setEnabled(true);

		AlarmProvider.geInstance(c).setSleepAlarm(ab);
		nameIsToHard(null);
	}

	private synchronized void giveup() {
		Utils.finishSleepTime(c);
		// int time = Integer.MAX_VALUE;
		// Utils.setLaichuangTime(time);
		Utils.GameReset();

		nextIntent = new Intent(c, ShareToFriendActivity.class);
		// nextIntent.putExtra(c.getString(R.string.sleepState), bd);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Bundle bd = new Bundle();
		bd.putString("alarm_form", AlarmProvider.geInstance(c).getSleepAlarm(0)
				.getFormIndex());
		nextIntent.putExtra("extra", bd);
		AlarmProvider.geInstance(c).disableSleepAlarm(0);

		nameIsToHard(bd);
		// nfManger.cancel(Symbol.NOTIFICATION_RINGING);
	}

	public static void cancelTime(Context c) {
		SharedPreferences.Editor e = c.getSharedPreferences("tavx", 0).edit();
		e.putLong("t1", -1);
		e.putInt("i1", -1);
		e.commit();
	}

	public static void saveTime(Context c, long time, int index) {
		SharedPreferences.Editor e = c.getSharedPreferences("tavx", 0).edit();
		e.putLong("t1", time);
		e.putInt("i1", index);
		e.commit();
	}

	public static long getTime(Context c) {
		SharedPreferences e = c.getSharedPreferences("tavx", 0);
		return e.getLong("t1", -1);// e.putLong("t1", time);
	}

	public static int getIndex(Context c) {
		SharedPreferences e = c.getSharedPreferences("tavx", 0);
		return e.getInt("i1", -1);// ("t1", -1);//e.putLong("t1", time);
	}
}