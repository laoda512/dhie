package com.tavx.C9Alarm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import com.tavx.C9Alam.connector.MyLogger;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;

public class Utils implements Symbol {
    public static Calendar setCalendarTime( int hour, int min ) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(System.currentTimeMillis());
        cl.set(Calendar.HOUR_OF_DAY, hour);
        cl.set(Calendar.MINUTE, min);
        cl.set(Calendar.SECOND, 0);
        MyLogger.e("aaaa", "hour"+hour+"min"+min+" "+getCalendarTime(cl.getTimeInMillis()));
        return cl;
    }
    public static String getNextTimeString( int hour, int min){
    	String date="";
    	Calendar cl = setCalendarTime(hour, min);
    	if(System.currentTimeMillis()>cl.getTimeInMillis()){
    		date="明天";
    	}else{
    		date="今天";
    	}
    	
    	return date;
	 }
    public static String getTimeLocationString( int hour, int min){
    	String date="";
    	
    	if(hour<5){
    		date="凌晨";
    	}else if(hour<10){
    		date="上午";
    	}else if(hour<14){
    		date="中午";
    	}else if(hour<18){
    		date="下午";
    	}else if(hour<24){
    		date="晚上";
    	}else{
    		date="??";
    	}
    	return date;
	 }
    
    public static String getCalendarTime( Long milliseconds ) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(milliseconds);
         
        
        int hour = ( cl.get(Calendar.HOUR_OF_DAY) );
        int min = ( cl.get(Calendar.MINUTE) );
        return (hour<10?"0"+hour:hour) +":" + (min<10?"0"+min:min);
        
    }
    /**
     * @param 七位十六进制，每一位代表一天，0x1000 0110 返回 周四,周五
     * */
    public static String FormatDay(int day){
        switch (day) {
            case DAY_ALLDAY:
                return STR_DAY_ALLDAY;
            case DAY_WEEKDAY:
                return STR_DAY_WEEKDAY;
            case DAY_WEEKEND:
                return STR_DAY_WEEKEND;
            case DAY_ONCE:
                return STR_DAY_ONCE;
            default: {
                return "自定义";//getNumWeekDaytoWord( day);
            }
        }
    }
    
    public static boolean isDayAlarmOn(int day,int whichDay){
    	if(day==Symbol.DAY_ONCE) return false;
    	if((day&whichDay)==whichDay) return true;
    	else return false;
    }
   
    public static String getNumWeekDaytoWord(int day){
    	if(day==Symbol.DAY_ONCE) return "";
    	StringBuffer sb = new StringBuffer();
        sb.append("周");
        for (int i = 6; i >= 0; i--) {
            if ((0x1 << i & day) != 0) {
                sb.append(getNumToWord((i == 6 ? i + 1 : 6 - i)));
                sb.append(", ");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
    /**
     * convert Long day to String as ：1990年5月1号星期一 
     * 
     * */
    public static String getFormatedDayString(Calendar cl){
    	return  cl.get(Calendar.YEAR)+"年"+(cl.get(Calendar.MONTH)+1)+"月"+cl.get(Calendar.DAY_OF_MONTH)+"号"+"星期"+AlarmBean.translateNumToDayText((cl.get(Calendar.DAY_OF_WEEK)))+cl.get(Calendar.HOUR_OF_DAY)+"点"+cl.get(Calendar.MINUTE)+"分";
		
    }
  
    
    public static String getNumToWord(int i){
    	switch(i){
    	case 0 :return "零";
    	case 1 :return "一";
    	case 2 :return "二";
    	case 3 :return "三";
    	case 4 :return "四";
    	case 5 :return "五";
    	case 6 :return "六";
    	case 7 :return "日";
    	}
    	return "";
    }
    
    public static String formatRingMode( int mode ) {
        switch (mode) {
            case RING_SLIENT:
                return STR_RING_SLIENT;
            case RING_SYSTEM:
                return STR_RING_SYSTEM;
            case RING_OPTIONAL:
                return STR_RING_OPTIONAL;
            case RING_VIBRATE:
                return STR_RING_VIBRATE;
            default:
                return "";
        }
    }
    //50 :少于一分钟
    public static String getFormatTime(int second){
    	MyLogger.e("second","s"+second);
    	//if(second<0) return "没有闹钟";
    	return (second>3600*24? second/(24*3600)+"天":"")+(second>3600? ((second%(24*3600)))/3600+"小时":"")+(second>60? (((second%(24*3600))%3600)/60+"分钟"+((second%(24*3600))%3600)%60+"秒"):"不到一分钟");
    }
    //50:50秒
    public static String getFormatTimeWithSeconds(int second){
    	MyLogger.e("second","s"+second);
    	return (second>3600*24? second/(24*3600)+"天":"")+(second>3600? ((second%(24*3600)))/3600+"小时":"")+(second>60? (((second%(24*3600))%3600)/60+"分钟"+((second%(24*3600))%3600)%60+"秒"):second+"秒");
    }
    
    
    private static boolean isStart = false;
    private static long startTime = -1l;
    private static int awakeCount = 0;
    private static int time = 0;
    
    private static String I_awakeCount= "awakeCount";
    private static String L_startTime = "startTime";
    private static String B_isStart = "isStart";
    private static String I_LastTime = "I_LastTime";
	/**
     * 
     * 
     * */
	public static void addorStartSleepTime(Context c) {
		loadData(c);
		addTime(c);
		saveData(c);
	}

	private static void addTime(Context c) {
		
		if (isStart == false) {
			resetTime(c);
			isStart = true;
			startTime = System.currentTimeMillis();
		}
		awakeCount++;
		
	}

	public static int finishSleepTime(Context c) {
		loadData(c);
		if(isStart==true){
		time = (int) ((System.currentTimeMillis() - startTime) / 1000);
		//MyLogger.e("time:"+time, ""+System.currentTimeMillis()+"start"+startTime);
		isStart=false;
		
		resetTime(c);
		saveData(c);
		
		}
		else{
			time = 0;
		}
		setLaichuangTime( (int) (time-(((GameOverTime<=-1l?0:GameOverTime))/1000)));
		return getLaichuangTime();
	}

	private static void resetTime(Context c) {
		// TODO Auto-generated method stub
		isStart = false;
		startTime = -1l;
		awakeCount = 0;
	//	saveData(c);
	}

	private static void saveData(Context c) {
		// TODO Auto-generated method stub
		SharedPreferences.Editor sp = c.getSharedPreferences("timeCount",
				Context.MODE_PRIVATE).edit();
		sp.putBoolean(B_isStart, isStart);
		sp.putLong(L_startTime, startTime);
		sp.putInt(I_awakeCount, awakeCount);
		sp.putInt(I_LastTime, time);
		sp.commit();
	}

	private static void loadData(Context c) {
		// TODO Auto-generated method stub

		SharedPreferences sp = c.getSharedPreferences("timeCount",
				Context.MODE_PRIVATE);
		isStart = sp.getBoolean(B_isStart, false);
		startTime = sp.getLong(L_startTime, -1l);
		awakeCount = sp.getInt(I_awakeCount, 0);
		time = sp.getInt(I_LastTime, 0);

	}	
//	public static int getLastAwakeTime(){
//		MyLogger.e("time", ""+time);
//		return time;
//	}
	
	public static void forceStopSleepAlarm(Context c){
		Intent intent = new Intent(ACTION_GIVEUP);    
        c.sendBroadcast(intent);
	}
	private static long GameStartTime =-1l;
	private static long GameOverTime = -1l;
	public static void GameStart(){/*
		if(GameStartTime!=-1l){
			MyLogger.e("GameStartTimeError", ""+GameStartTime);
			return ;
		}
		GameStartTime=System.currentTimeMillis();
		GameOverTime=-1l;
	*/}
	public static void GameOver(){/*
		if(GameOverTime!=-1l){
			MyLogger.e("GameOverTimeError", ""+GameStartTime);
			GameOverTime=0l;
			return ;
		}
		GameOverTime=System.currentTimeMillis()-GameStartTime;
		GameStartTime=-1l;
	*/}
	public static void GameReset(){
		GameStartTime =-1l;
		GameOverTime = -1l;
	}
	
	 /**
     * check is network avilable
     */
    public static  boolean checkNetWork(Context c) {
        ConnectivityManager cm = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for (int i = 0; i<networkInfos.length; i++) {
            if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
            	return true;
            }
        }
        return false;
    }
	
	/**
	 * 
	 * 
	 * */
	public static void submitScore( int nScore, String Desc)
	{
		
//		Score score = new Score(nScore, (Desc.length() > 0 ? Desc : null));
//		Leaderboard lb = new Leaderboard(Symbol.LeadboardID_1);
//		score.submitTo(lb, null);

		
		/*//如果想处理返回结果，可以传入回调类，下面是代码演示
		Score.SubmitToCB cb = new Score.SubmitToCB()
		{
			@Override
			public void onSuccess(boolean newHighScore)
			{
				if (newHighScore)
				{
					Toast.makeText(Tetris.this, "成功上传了新的得分",
							Toast.LENGTH_LONG).show();
				} else
				{
					Toast.makeText(Tetris.this, "上传的分数不是更高的分数",
							Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(String exceptionMessage)
			{
				Tetris.this.nSubmitScoreResult = SubmitScoreResult_Success_newHighScore;
				mExceptionMessage = exceptionMessage;
				mExceptionMessageTitle = "上传分数失败";
				Tetris.this.showDialog(SHOW_NotifyMes);
			}
		};
		Score score = new Score(nScore, (Desc.length() > 0 ? Desc : null));
		Leaderboard lb = new Leaderboard(LeaderboardID);
		score.submitTo(lb, null);
		*/
	}
	
	private static int laichuangTime;
	public static int getLaichuangTime() {
		
		return laichuangTime;
	}
	

	public static void setLaichuangTime(int laichuangTime) {
		
		Utils.laichuangTime = laichuangTime;
	}
	
	 public ArrayList findSDCardMediaFileList(Context c)
	    {
	      ArrayList localArrayList = new ArrayList();
	      ContentResolver localContentResolver = c.getContentResolver();
	      Uri localUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	      Cursor localCursor = localContentResolver.query(localUri, null, null, null, "title_key");
	      if ((localCursor == null) || (!localCursor.moveToFirst()))  
	          do
	          {
	            int i = localCursor.getColumnIndex("title");
	            String str1 = localCursor.getString(i);
	            int j = localCursor.getColumnIndex("artist");
	            String str2 = localCursor.getString(j);
	            int k = localCursor.getColumnIndex("_size");
	            long l = localCursor.getLong(k);
	            int m = localCursor.getColumnIndex("_data");
	            String str3 = localCursor.getString(m);
	           
	            localArrayList.add(str3);
	          }
	          while (localCursor.moveToNext());
	       //   sendRingtoneMsg(paramHandler, 1, localArrayList);
	         
	        
	          localCursor.close();
	          return localArrayList;
	    }
	 
	 public static Bitmap getBitmapByStream(Context c,int resId){
		
	     return getBitmapByStream(c,resId,false);
	 }
	 
	 public static Bitmap getBitmapByStream(Context c,int resId,Boolean isLowQuality){
	     BitmapFactory.Options options=new BitmapFactory.Options();
	     options.inJustDecodeBounds = false;
	     if(isLowQuality==true)
	     options.inPreferredConfig=Bitmap.Config.ARGB_4444;	    	 
	     return getBitmapByStream(c,resId,options);
	 }
	 
	 public static Bitmap getBitmapByStream(Context c,int resId,BitmapFactory.Options options){
		 InputStream is = c.getResources().openRawResource(resId);
	     return BitmapFactory.decodeStream(is,null,options);
	 }
	 
	 
	public static int random(int start,int end){
		return (int) (Math.random()*(end-start+1)+start);
	}
	static float density = -1;
	public static int dipToPx(Context context, int dip) {
		
		if (density <= 0.0F)
			density = context.getResources().getDisplayMetrics().density;
		return (int) ((float) dip * density + 0.5F);
	}
	
}
