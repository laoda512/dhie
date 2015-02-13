package com.tavx.C9Alarm.Manager;

import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.Form.Form;
import com.tavx.C9Alarm.bean.HotInfo;

public class HotInfoManager {
	private static final int delta = 10;//12*3600*1000;
	private static final int Day = 24*3600*1000;
	private static final int punishTime = 3;
	private HotInfoManager(){
		
	}
	private static HotInfoManager mHotInfoManager;
	public static HotInfoManager getInstance() {
		if(mHotInfoManager==null){
			mHotInfoManager = new HotInfoManager();
		}
		return mHotInfoManager;
	}
	
	public void checkSleepTime(){
		Long last = AlarmApplication.getApp().getUser().getLastTime();
		int  punishCount = AlarmApplication.getApp().getUser().getLastPunishCount();
		Long noAlarmtime = System.currentTimeMillis()-punishTime*Day+punishCount*Day-last;
		if(noAlarmtime>0){
			punishCount++;
			reduceAllHot((int) (noAlarmtime/(Day)+1));
			AlarmApplication.getApp().getUser().setLastPunishCount(punishCount);
			 
		}
		
	}
	
	public void reduceAllHot(int count){
		Object[] mForm = Form.getAllForm(); 
		for(int i=0;i<mForm.length;i++){
			reduceHot(((Form)mForm[i]).getTitleName(),count);
		}
	}
	
	private void reduceHot(String name,int count){
		HotInfo mHotInfo=new  HotInfo( AlarmApplication.getApp(), ""+name);
		mHotInfo.setLastTimeCount(mHotInfo.getLastTimeCount()-count);
	}
	
	private void increaseHot(int index){
		HotInfo mHotInfo=new  HotInfo( AlarmApplication.getApp(), ""+index);
		mHotInfo.setLastTimeCount(mHotInfo.getLastTimeCount()+1);
	}

	public void alarmOver(String form,boolean isLaichuang){
		addOneTime( form, isLaichuang);
		resetLastRingTime();
		
	}
	
	private void addOneTime(String form,boolean isLaichuang){
		HotInfo mHotInfo=new HotInfo(AlarmApplication.getApp(), ""+form);
		mHotInfo.setSumCount(mHotInfo.getSumCount()+1);
		if(System.currentTimeMillis()-mHotInfo.getLastTime()>delta){
			mHotInfo.setLastTimeCount(mHotInfo.getLastTimeCount()+1);
		}
		mHotInfo.setLastTime(System.currentTimeMillis());
		if(!isLaichuang){
			mHotInfo.setIntTimeCount(mHotInfo.getIntTimeCount()+1);
		}
		
	}
	/**
	 * 
	 * 重设 最近一次响铃 时间 
	 * */
	private void resetLastRingTime(){
		AlarmApplication.getApp().getUser().setLastPunishCount(0);
		AlarmApplication.getApp().getUser().setLastTime(System.currentTimeMillis());
	}

}
