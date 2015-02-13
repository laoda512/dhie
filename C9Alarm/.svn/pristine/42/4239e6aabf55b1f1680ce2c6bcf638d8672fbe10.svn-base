package holidaynotifaction;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.R;
import com.tavx.C9Alarm.Symbol;
import com.tavx.C9Alarm.boxAlarmActivity;

public class HolidayReceiver extends BroadcastReceiver {
	Context c;

	NotificationManager nfManger;
	Notification mNotification;
	private DBHelper helper = null;
	List<Day> holidayDate;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.c = context;
		helper = new DBHelper(context);
		Day d = geTomorrow();
		if (d != null) {
			Intent intent_ma = new Intent(c, boxAlarmActivity.class);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(c, 0,
					intent_ma, 0);

			NotificationManager nfManger = (NotificationManager) c
					.getSystemService(Activity.NOTIFICATION_SERVICE);

			Notification mNotification = new Notification();
			mNotification.icon = R.drawable.ic_clock_add_alarm;
			mNotification.tickerText = "接下来将是" + d.notification
					+ "请关注您的闹钟是否设置正确";
			mNotification.flags |= mNotification.FLAG_AUTO_CANCEL; // 将此通知放到通知栏的"Ongoing"即"正在运行"组中
			mNotification.setLatestEventInfo(c, d.month + "月" + d.day + "日",
					d.notification, pendingIntent);

			nfManger.notify(10, mNotification);
		}
		setAlarm(context);
	}

	private Day geTomorrow() {

		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(System.currentTimeMillis() + 24 * 3600 * 1000);
		int day = cl.get(Calendar.DAY_OF_MONTH);
		int month = cl.get(Calendar.MONTH)+1;
		MyLogger.e("day"+day, "month"+month)
		;
		Cursor c = helper.query();
		if (c.moveToFirst()) {
			do {
				MyLogger.e("day"+c.getInt(0), "month"+c.getInt(1))
				;
				if (c.getInt(0) == day && c.getInt(1) == month) {
					return new Day(day, month, c.getString(2));
				}

			} while (c.moveToNext());

		}
		return null;
	}

	

	private class Day {
		public Day(int day, int month, String notification) {
			super();
			this.day = day;
			this.month = month;
			this.notification = notification;
		}

		int day;
		int month;
		String notification;

	}

	/**
	 * 假日
	 * 
	 * */
	public static void setAlarm(Context c) {
		AlarmManager am = (AlarmManager) c
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent =new Intent();
		intent.setClass(c, HolidayReceiver.class);
		intent.setAction(Symbol.ACTION_RECEIVE_ALARM);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(c, 0, intent,
				0);

		Calendar cl = Calendar.getInstance();
		 setNextHourOfDay(cl,19);
		
		am.set(AlarmManager.RTC_WAKEUP, cl.getTimeInMillis(), pendingIntent);
	}
	private static void setNextHourOfDay(Calendar cl,int hour){
		cl.setTimeInMillis(System.currentTimeMillis());
		if(cl.get(Calendar.HOUR_OF_DAY)<hour){
			cl.set(Calendar.HOUR_OF_DAY, hour);
		}
		cl.set(Calendar.HOUR_OF_DAY, hour);
		cl.set(Calendar.MINUTE,0);
		cl.set(Calendar.SECOND,0);
		if(cl.getTimeInMillis()<=System.currentTimeMillis())
			cl.setTimeInMillis(cl.getTimeInMillis()+3600*24*1000);
		
	}

}