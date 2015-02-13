
package com.tavx.C9Alarm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.Form.Form;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class AlarmProvider implements Symbol {

    private String B_isEnable = "B_isEnable";

    private String S_uri = "S_uri";

    private String I_type = "I_type";
    
    private String S_name = "S_name";

    private String L_interval = "L_interval";

    private String I_data = "I_data";

    private String L_time = "L_time";

    private String B_isFrist = "B_isFrist";

    private String I_index = "I_index";
    
    private String I_ring = "I_ring";

    private String I_Sleep_delay = "I_Sleep_delay";
    
    private String B_vibrate = "B_vibrate";
    
    private String S_ALarmForm = "S_ALarmForm";//风格
    
    private String B_gameEnable = "B_gameEnable";
    
    private Context mContext;

    private static AlarmProvider ap = null;

    private AlarmProvider(Context c) {

        initialData();
    }

    public static AlarmProvider geInstance(Context c) {
        if ( null == ap ) {
            ap = new AlarmProvider(c);
        }
        return ap;
    }

    private void initialData() {
        SharedPreferences preference = AlarmApplication.getApp().getSharedPreferences("alarm", Context.MODE_PRIVATE);
        if (preference.getBoolean(B_isFrist, true)) {
            SharedPreferences.Editor edit = AlarmApplication.getApp().getSharedPreferences("alarm", Context.MODE_PRIVATE)
                    .edit();
            edit.putBoolean("B_isFrist", false).commit();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.set(Calendar.HOUR_OF_DAY, 8);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            AlarmBean ab = new AlarmBean(
                    Symbol.TYPE_XIAONAO,
                    DEFAULT_NAME , INTERVAL_DAY, DAY_ALLDAY, 0,
                    c.getTimeInMillis(), false,
                    RING_SLIENT, DEFAULT_MUSIC_PATH + "01.mp3",
                    INTERVAL_DELAY, false,false,"" );
            ab.setEnabled(false);
            for (int i = 0; i < MAX_ALARM_COUNT; i++) {
                ab.setIndex(i);
                ab.setForm(Form.InialFormCount[i]);
                this.setAlarm(ab);
            }
//            ab.setForm(Form.FORM_FIRST);
//            ab.setIndex(0);
//            this.setAlarm(ab);
        }
    }

    public List<AlarmBean> getEnabledAlarm() {
        SharedPreferences preference = AlarmApplication.getApp().getSharedPreferences("alarm", Context.MODE_PRIVATE);

        ArrayList<AlarmBean> al = new ArrayList<AlarmBean>();
        for (int i = 0; i < MAX_ALARM_COUNT; i++) {
            if (preference.getBoolean(B_isEnable + i, false)) {
                al.add(getAlarm(i));
            }
        }
        return al;

    }

    public List<AlarmBean> getAlarms() {
//        SharedPreferences preference = AlarmApplication.getApp().getSharedPreferences("alarm", Context.MODE_PRIVATE);
        ArrayList<AlarmBean> al = new ArrayList<AlarmBean>();
//        for (int i = 0; i < MAX_ALARM_COUNT + MAX_SLEEP_COUNT; i++) {
        for (int i = 0; i < MAX_ALARM_COUNT ; i++) {
            al.add(getAlarm(i));
        }

        return al;
    }

    public AlarmBean getAlarm(int index) {
        SharedPreferences preference = AlarmApplication.getApp().getSharedPreferences("alarm", Context.MODE_PRIVATE);
        int i = index;
      //  MyLogger.e("aaaa", "L_time"+L_time+" "+Utils.getCalendarTime( preference.getLong(L_time + i, -1l)));
        return new AlarmBean(
                preference.getInt(I_type + i, -1),
                preference.getString(S_name + i, ""),
                preference.getLong(L_interval + i, Long.MAX_VALUE),
                preference.getInt(I_data + i, -1),
                preference.getInt(I_index + i, -1),
                preference.getLong(L_time + i, -1l),
                preference.getBoolean(B_isEnable + i, false),
                preference.getInt(I_ring + i, -1),
                preference.getString(S_uri + i, null),
                preference.getInt(I_Sleep_delay + i, Symbol.INTERVAL_DELAY),
                preference.getBoolean(B_vibrate + i, false),
                preference.getBoolean(B_gameEnable + i, false),
                preference.getString(S_ALarmForm+i,  "pikaqiu")
                
                // TODO: add extra here
        );
    }

    /**
     * @param index use 0
     */
    public AlarmBean getSleepAlarm(int index) {
        if (index >= MAX_SLEEP_COUNT )
            return null;
        index += MAX_ALARM_COUNT;

        SharedPreferences preference = AlarmApplication.getApp().getSharedPreferences("alarm", Context.MODE_PRIVATE);
        int i = index;
        if (preference.getBoolean(B_isEnable + i, false)) {
            return getAlarm(i);
        }

        return null;

    }

    /**
     * 将传入的alarm写入文件，如果时间早于当前时间，会自动计算下一次时间，但是不保证结果晚于当前时间
     */
    public boolean setAlarm(AlarmBean ab) {
    	
        int index = ab.getIndex();

        SharedPreferences.Editor edit = AlarmApplication.getApp().getSharedPreferences("alarm", Context.MODE_PRIVATE)
                .edit();
        edit.putInt(I_type + index, ab.getType());
        edit.putString(S_name + index, ab.getName());
        edit.putLong(L_interval + index, ab.getInterval());
        edit.putInt(I_data + index, ab.getDay());
        edit.putBoolean(B_isEnable + index, ab.getEnabled());
        edit.putInt(I_ring + index, ab.getRingMode());
        edit.putString(S_uri + index, ab.getUri());
        edit.putInt(I_index + index, ab.getIndex());
        if (ab.getTime() < System.currentTimeMillis())
            edit.putLong(L_time + index, calNextTime(ab));
        else
            edit.putLong(L_time + index, ab.getTime());
        edit.putInt(this.I_Sleep_delay + index, ab.getSleepDelay());
        edit.putBoolean(this.B_vibrate + index, ab.getVibrate());
        edit.putBoolean(this.B_gameEnable + index, ab.getEnableGame());
        edit.putString(this.S_ALarmForm + index, ab.getFormIndex());
        edit.commit();

        return true;
    }

    public boolean setAlarmEnable(int index, boolean isEnable) {
        SharedPreferences.Editor edit = AlarmApplication.getApp().getSharedPreferences("alarm", Context.MODE_PRIVATE)
                .edit();
        edit.putBoolean(B_isEnable + index, isEnable);
        edit.commit();
        return true;
    }

    /**
     * @param index use 0
     */
    public boolean disableSleepAlarm(int index) {
        index += MAX_ALARM_COUNT;

        SharedPreferences.Editor edit = AlarmApplication.getApp().getSharedPreferences("alarm", Context.MODE_PRIVATE)
                .edit();
        edit.putBoolean(B_isEnable + index, false);
        edit.commit();
        return true;
    }
    
    /**
     * @param index use 0
     */
    public boolean isSleepAlarmEnable(int index) {
        index += MAX_ALARM_COUNT;
        return getAlarm(index).getEnabled();
    }

    /**
     * 将传入的alarm写入文件，如果时间早于当前时间，会自动计算下一次时间，但是不保证结果晚于当前时间
     * 
     * @param ab.index =0;
     */
    public boolean setSleepAlarm(AlarmBean ab) {
        int index = ab.getIndex();
        if (ab == null || index >= MAX_SLEEP_COUNT) {
            return false;
        }
        index += MAX_ALARM_COUNT;
        ab.setIndex(index);
        setAlarm(ab);

        return true;
    }

    /**
     * 计算下一次时间
     * 
     * @param Overtime 必须大于的时间
     */
    private Long calNextTime(AlarmBean ab) {
        if (ab.getDay() != -1) {
            Calendar cl = Calendar.getInstance();
            Long current=System.currentTimeMillis();
            do {
                ab.setTime(ab.getTime() + ab.getInterval());
                cl.setTimeInMillis(ab.getTime());
               // MyLogger.e("aaaa", ""+cl.get(Calendar.DAY_OF_WEEK));
                System.out.println(cl.get(Calendar.DAY_OF_WEEK));
                
                
                
            } while (isAlarmActive(cl.get(Calendar.DAY_OF_WEEK), ab.getDay()) == false || ab.getTime()<current);

        } else {
            ab.setTime(ab.getTime() + ab.getInterval());
        }

        return ab.getTime();

    }

    private boolean isAlarmActive(int day_of_week, int dataMask) {
        if ((AlarmBean.translateDay(day_of_week) & dataMask) == 0) {
             System.out.println("alarm day: "+ day_of_week );
            return false;
        } else
            return true;
    }

    /**
     * 获取下一个闹钟，如果有过期的时间会自动运算至合法的时间(错误的数据可能导致死锁)
     */
    public AlarmBean getNextAlarm() {
        SharedPreferences preference = AlarmApplication.getApp().getSharedPreferences("alarm", Context.MODE_PRIVATE);
        Long buffer = Long.MAX_VALUE;
        Long currentTime = System.currentTimeMillis();
        int index = -1;
        for (int i = 0; i < MAX_ALARM_COUNT + MAX_SLEEP_COUNT; i++) {
            if (preference.getBoolean(B_isEnable + i, false)) {

                // 校准时间正确性,10分钟后再响如果过期却任然有效将报错
                Long time = preference.getLong(L_time + i, 0);
                if (time <= currentTime) {
                    AlarmBean abx = getAlarm(i);
                    calNextTime(abx);
                    setAlarm(abx);
                    if (i == MAX_ALARM_COUNT + MAX_SLEEP_COUNT - 1) {
                        MyLogger.e("Time_Error", "error_state");
                    }
                }
                // 获取距离现在最短的闹铃
                if (buffer > preference.getLong(L_time + i, 0)) {
                    buffer = preference.getLong(L_time + i, 0);
                    index = i;
                    MyLogger.e("nextAlarm_1", "index="+index);
                } else if (buffer == preference.getLong(L_time + i, 0)) {
                    AlarmBean abx = getAlarm(i);
                    calNextTime(abx);
                    setAlarm(abx);
                }

            }

        }

        if (index == -1){
        	setStatusBarIcon( AlarmApplication.getApp(),false);
            return null;
        }
        
        AlarmBean abx = getAlarm(index);
        // calNextTime(abx);
        // setAlarm(abx);

        abx.setTime(buffer);
        saveCurrendIndex(index);
        MyLogger.e("nextAlarm", "index="+index);
        MyLogger.e("nextAlarm3", "index="+abx.getIndex());
        setStatusBarIcon( AlarmApplication.getApp(),true);
        return abx;

    }
    
    
    /**
     * Tells the StatusBar whether the alarm is enabled or disabled
     */
    private static void setStatusBarIcon(Context context, boolean enabled) {
        Intent alarmChanged = new Intent("android.intent.action.ALARM_CHANGED");
        alarmChanged.putExtra("alarmSet", enabled);
        context.sendBroadcast(alarmChanged);
    }
    
    private void saveCurrendIndex(int index) {
		// TODO Auto-generated method stub
    	currentIndex=index;
    	 SharedPreferences.Editor edit = AlarmApplication.getApp().getSharedPreferences("alarm", Context.MODE_PRIVATE)
                 .edit();
         edit.putInt("CurrentIndex" , currentIndex);
         edit.commit();
	}
	private int currentIndex;

    public int getCurrendIndex() {
		// TODO Auto-generated method stub
    	if(currentIndex!=-1){
    	 SharedPreferences c = AlarmApplication.getApp().getSharedPreferences("alarm", Context.MODE_PRIVATE);
    	 currentIndex=c.getInt("CurrentIndex", 0);
    	}
    	return currentIndex;
	}
}
