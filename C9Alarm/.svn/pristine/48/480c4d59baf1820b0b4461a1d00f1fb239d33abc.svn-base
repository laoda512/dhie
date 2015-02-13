package com.tavx.C9Alarm;

import java.util.Calendar;

import util.AlarmAlertWakeLock;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.Service.LifeService;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmSet {
	static Intent intent;
	static PendingIntent pendingIntent;

	public static void setAlarm(Context c, Boolean needNoti) {

		// 设定闹钟
		AlarmBean ab = AlarmProvider.geInstance(c).getNextAlarm();
		AlarmManager am = (AlarmManager) c
				.getSystemService(Context.ALARM_SERVICE);
		
		if (ab != null) {
			MyLogger.e("AlarmReceiver", "seralarm");
			intent = new Intent(c, AlarmReceiver.class);
			intent.setAction(Symbol.ACTION_PREPARE_TO_AWAKE);
			intent.putExtra("alarm_index", ab.getIndex());
			pendingIntent = PendingIntent.getBroadcast(c, 0, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmReceiver.saveTime(c,ab.getTime(),ab.getIndex());
			
			if(ab.getTime()-System.currentTimeMillis()<5*60*1000){
				MyLogger.e("AlarmReceiver", "setAlarm time too Short");
				AlarmAlertWakeLock.acquireCpu(AlarmApplication.getApp());
			}else{
				MyLogger.e("AlarmReceiver", "setAlarm time too long,set alarm");
				am.set(AlarmManager.RTC_WAKEUP, ab.getTime()-5*60*1000, pendingIntent);
			}
			AlarmApplication.getApp().getUser().setLastFormTag(ab.getFormIndex());
			
			
		} else {// 取消闹钟
			MyLogger.e("AlarmReceiver", "awake");
			intent = new Intent(c, AlarmReceiver.class);
			intent = new Intent();
			intent.setAction(Symbol.ACTION_PREPARE_TO_AWAKE);
			pendingIntent = PendingIntent.getBroadcast(c, 0, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			am.cancel(pendingIntent);
			AlarmReceiver.cancelTime(c);
			needNoti = true;
		}

		LifeService.startMe(c);
		
		Intent intentNotification;

		PendingIntent pINotificiation;
		NotificationManager nfManger = (NotificationManager) c
				.getSystemService(Activity.NOTIFICATION_SERVICE);
		Notification mNotification = new Notification();

		// 设定消息
		if (ab != null) {
			intentNotification = new Intent(c, SplashScreen.class);
		//	intentNotification.setAction(Symbol.ACTION_START_MAIN);
			intentNotification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			pINotificiation = PendingIntent.getActivity(c, 0,
					intentNotification, PendingIntent.FLAG_UPDATE_CURRENT);

			Long nextTime = ab.getTime();
			Calendar cl = Calendar.getInstance();
			cl.setTimeInMillis(nextTime);
			String showText = Utils.getFormatedDayString(cl);

			mNotification.icon = R.drawable.logo;
			mNotification.tickerText = "闹钟将在"
					+ Utils.getFormatTime((int) ((nextTime - System
							.currentTimeMillis()) / 1000)) + "后再次启动";
			mNotification.flags |= mNotification.FLAG_ONGOING_EVENT; // 将此通知放到通知栏的"Ongoing"即"正在运行"组中
			mNotification.setLatestEventInfo(c, "下次闹钟时间", showText, 
					pINotificiation);

			nfManger.notify(Symbol.NOTIFICATION_RINGING, mNotification);
		} else {
			if (needNoti) {
				intentNotification = new Intent(c, AlarmReceiver.class);
				intentNotification.setAction(Symbol.ACTION_DONOTHING);
				pINotificiation = PendingIntent.getBroadcast(c, 0,
						intentNotification, PendingIntent.FLAG_UPDATE_CURRENT);

				mNotification.icon = R.drawable.logo;
				mNotification.tickerText = "目前没有启用闹钟";
				mNotification.flags |= mNotification.FLAG_AUTO_CANCEL; // 将此通知放到通知栏的"Ongoing"即"正在运行"组中
				mNotification.setLatestEventInfo(c, "没有设定闹钟", "点击关闭提示",
						pINotificiation);
				nfManger.notify(Symbol.NOTIFICATION_RINGING, mNotification);
			}
		}

	}
	
	public static void noticeMiss(Context c,Long time){
		PendingIntent pINotificiation;
		NotificationManager nfManger = (NotificationManager) c
				.getSystemService(Activity.NOTIFICATION_SERVICE);
		Notification mNotification = new Notification();
		Intent intentNotification;
		// 设定消息
			intentNotification = new Intent(c, AlarmReceiver.class);
			pINotificiation = PendingIntent.getBroadcast(c, 1,
					intentNotification, PendingIntent.FLAG_UPDATE_CURRENT);

			Calendar cl = Calendar.getInstance();
			cl.setTimeInMillis(time);
			String showText = Utils.getFormatedDayString(cl);

			mNotification.icon = R.drawable.logo;
			mNotification.tickerText = "原定于"+showText+"的闹钟似乎没有正常启动T_T";
			mNotification.flags |= mNotification.FLAG_AUTO_CANCEL; // 将此通知放到通知栏的"Ongoing"即"正在运行"组中
			mNotification.setLatestEventInfo(c, "闹钟没有正常启动", showText,
					pINotificiation);

			nfManger.notify(Symbol.NOTIFICATION_MESSAGE, mNotification);
		
	}
	public static void setAlarm(Context c) {
		setAlarm(c, false);
	}
	
	

}
