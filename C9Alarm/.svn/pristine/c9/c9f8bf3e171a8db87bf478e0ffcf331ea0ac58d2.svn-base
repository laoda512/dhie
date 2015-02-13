
package com.tavx.C9Alarm;

import java.io.Serializable;
import java.util.Calendar;

public class AlarmBean implements Serializable {
    /** alarm type*/
    private int type                            = 0;
    /** alarm name */
    private String name                         = null;

    private Long interval                       = Long.MAX_VALUE;

    /** the day which alarm occurs on */
    private int day                             = 0;
    
    /** 再响时间，以分钟为单位*/    
    private int sleepDelay;

    private Boolean enableGame; 

	public Boolean getEnableGame() {
		return enableGame;
	}

	public void setEnableGame(Boolean enableGame) {
		this.enableGame = enableGame;
	}

	/** index of alarm */
    private int index                           = 0;

    private Long time;
    
    /** alarm ring mode */
    private int ringMode                        = 0;

    private boolean enabled;// = true;

    /** alarm ring uri */
    private String uri                          = null;
    
    private boolean bVibrate                    = false;
    
    private String form ;
    /**
     * 获取form的id值
     * 
     * */
    public String getFormIndex() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	/**
	 * 
	 */
    private static final long serialVersionUID  = -2518061880923546881L;

    public AlarmBean(int type, String name, Long interval, int day, int index, Long time, boolean enabled,
            int mode, String uri, int sleepDelay, boolean vibrate,Boolean enableGame,String form) {
        super();
        this.type = type;
        this.name = name;
        this.interval = interval;
        this.day = day;
        this.index = index;
        this.time = time;
        this.enabled = enabled;
        this.ringMode = mode;
        this.uri = uri;
        this.sleepDelay = sleepDelay;
        this.bVibrate = vibrate;//震动
        this.enableGame=enableGame;
        this.form = form;
    }
    
    public AlarmBean(){
    	 this.type = type;
         this.name = name;
         this.interval = interval;
         this.day = day;
         this.index = index;
         this.time = time;
         this.enabled = enabled;
         this.ringMode = 0;
         this.uri = uri;
         this.sleepDelay = sleepDelay;
         this.bVibrate = false;//震动
         this.enableGame=false;
         this.form = form;
    }
    /**
     * 获取闹钟是第几个（首页）
     * 
     * */
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Long getTime() {
        //MyLogger.e("alarm", "time: " + time);
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getName() {
      //  Log.d("alarm", "name: " + name);
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getType() {
          return type;
      }

      public void setType(int type) {
          this.type = type;
      }

    public Long getInterval() {
      //  Log.d("alarm", "interval: " + interval);
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public int getDay() {
      //  Log.d("alarm", "day: " + day);
        return day;
    }
    
    public String getDayText( int day ) {
    	String strWeeklyMode = null;
        switch (day) {
            case Symbol.DAY_ALLDAY:
                strWeeklyMode = Symbol.STR_DAY_ALLDAY;
                break;
            
            case Symbol.DAY_WEEKDAY:
                strWeeklyMode = Symbol.STR_DAY_WEEKDAY;
                break;
                
            case Symbol.DAY_WEEKEND:
                strWeeklyMode = Symbol.STR_DAY_WEEKEND;
                break;
                
            case Symbol.DAY_ONCE:
                strWeeklyMode = Symbol.STR_DAY_ONCE;
                break;
        }
        return strWeeklyMode;
    }

    public void setDay(int day) {
        this.day = day;
    }
    
    public int getRingMode() {
      //  Log.d("alarm", "get ring: " + ringMode);
        return ringMode;
    }

    public void setRingMode(int mode) {
        this.ringMode = mode;
    }
    
    public String getUri() {
      //  Log.d("alarm", "uri: " + uri);
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean getVibrate() {
        return bVibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.bVibrate = vibrate;
    }
    
    public int getSleepDelay() {
    	return sleepDelay;
    }

    public void setSleepDelay(int sleepDelay) {
    	this.sleepDelay = sleepDelay;
    }
    /**
     * 系统的日期常量数值可能会变,直接用数字会有问题
     * 
     * */
    public static int translateDay(int systemDay){
    	switch (systemDay){
    	case Calendar.SUNDAY : return Symbol.DAY_SUN;
    	case Calendar.MONDAY : return Symbol.DAY_MON;
    	case Calendar.TUESDAY : return Symbol.DAY_TUE;
    	case Calendar.WEDNESDAY : return Symbol.DAY_WED;
    	case Calendar.THURSDAY : return Symbol.DAY_THU;
    	case Calendar.FRIDAY : return Symbol.DAY_FRI;
    	case Calendar.SATURDAY : return Symbol.DAY_SAT;
    	}
    	return -1;
    }
    /**
     * 接受Calendar.SUNDAY之类的日期参数
     * 返回中文的一~日
     * */
    public static String translateNumToDayText(int systemDay){
    	switch (systemDay){
    	case Calendar.SUNDAY : return "日";
    	case Calendar.MONDAY : return "一";
    	case Calendar.TUESDAY :  return "二";
    	case Calendar.WEDNESDAY :  return "三";
    	case Calendar.THURSDAY :  return "四";
    	case Calendar.FRIDAY :  return "五";
    	case Calendar.SATURDAY :  return "六";
    	}
    	return "";
    }
}
