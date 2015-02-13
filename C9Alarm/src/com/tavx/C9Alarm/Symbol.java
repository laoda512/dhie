package com.tavx.C9Alarm;

public interface Symbol {
	public static final int    MAX_ALARM_COUNT     = 4;
	public static final int    MAX_SLEEP_COUNT     = 1;
	
	public static long         INTERVAL_DAY        = 24 * 3600 * 1000l;
	public static int          INTERVAL_DELAY      = 10;
	
	//Alarm Types
	public static final int    TYPE_XIAONAO        = 1;
	public static final int    TYPE_XIAOLONGMAO    = 2;
	public static final int    TYPE_XIAOMEIQIU     = 3;
	public static final int    TYPE_MANULINPUT     = 4;
	
	/**
	 * the 7th bit: Sunday
	 * the 1st bit: Sunday
	 */
	public static final int    DAY_ALLDAY          = 0x7f;// 01111111;
	public static final int    DAY_WEEKDAY         = 0x3e;// 00111110;
	public static final int    DAY_WEEKEND         = 0x41;// 01000001;
	public static final int    DAY_ONCE            = 0xff;// 11111111;
	
	public static final int    BIT_ONCE            = 0x80;// 10000000;
	
	public static final int    DAY_SUN             = 0x40;// 01000000;
	public static final int    DAY_MON             = 0x20;// 00100000;
	public static final int    DAY_TUE             = 0x10;// 00010000;
	public static final int    DAY_WED             = 0x08;// 00001000;
	public static final int    DAY_THU             = 0x04;// 00000100;
	public static final int    DAY_FRI             = 0x02;// 00000010;
	public static final int    DAY_SAT             = 0x01;// 00000001;
	
	public static final String STR_DAY_ALLDAY      = "每天";
    public static final String STR_DAY_WEEKDAY     = "工作日";
    public static final String STR_DAY_WEEKEND     = "双休日";
    public static final String STR_DAY_ONCE        = "仅单次";
	
	public static final String DEFAULT_NAME        = "pikaqiu";
	public static final String DEFAULT_MUSIC_PATH  = "/sdcard/music/";
	
	public static final int    RING_SLIENT         = 0x01;
	public static final int    RING_SYSTEM         = 0x02;
	public static final int    RING_OPTIONAL       = 0x03;
	public static final int    RING_VIBRATE        = 0x04;
	
	public static final int    RINGMODE_VIBRATE         = 0x01;//震动0001
	public static final int    RINGMODE_MUSIC         = 0x2;//铃声0010
	public static final int    RINGMODE_SLOWLOUD         = 0x4;//渐响0100
	
	public static final String DEFAULT_RING        = "静音";
	
	public static final String STR_RING_SLIENT     = "静音";
	public static final String STR_RING_SYSTEM     = "系统铃声";
	public static final String STR_RING_OPTIONAL   = "自选铃声";
	public static final String STR_RING_VIBRATE    = "仅震动";
	
	public static final String ACTION_LOSE = "ACTION_LOSE_the9";
	public static final String ACTION_WIN = "ACTION_WIN_the9";
	public static final String ACTION_GIVEUP = "ACTION_GIVEUP_the9";
	public static final String ACTION_START = "ACTION_START_the9";
	public static final String ACTION_CANCEL = "ACTION_cancel_the9";//因为意外消失，重置一切计时
	
	public static final String ACTION_PREPARE_TO_AWAKE = "ACTION_PREPARE_TO_AWAKE";
	public static final String ACTION_DONOTHING = "ACTION_DONOTHING_the9";
	public static final String ACTION_START_MAIN = "ACTION_START_MAIN_the9";
	public static final String ACTION_RECEIVE_ALARM = "ACTION_RECEIVE_ALARM_the9";
	
	public static final int NOTIFICATION_RINGING = 0;
	public static final int NOTIFICATION_MESSAGE = 1;
	
	public static final String SPLIT = "###";
	
	public static final String LeadboardID_1 = "916959182";
}
