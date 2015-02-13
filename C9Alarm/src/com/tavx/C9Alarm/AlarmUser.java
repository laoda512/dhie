package com.tavx.C9Alarm;

import java.lang.reflect.InvocationTargetException;

import util.readWriteAbleBean;
import android.content.Context;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.Manager.PointManager;

public class AlarmUser extends readWriteAbleBean {
	private String SharedName = "me";
	
	private static AlarmUser user;
	private String userNickName;
	private Integer sleepTime;
	private String snsBindName;
	private Boolean firstLogin  ;
	private Boolean hasAlarmSetHelp  ;
	
	private Integer alarmRingMode;//响铃模式， see Symbol.RINGMODE_VIBRATE..
	
	private Integer lazyButtonOffClickCount; 
	
	public Long lastTime;//上一次时间
	public Integer lastPunishCount;//累计连续惩罚次数,每次响铃后重置为0
	
	private Boolean isLowMemory;
	
	private String point;
	
	private Boolean isFirstInstall;
	
	public String lastFormTag;
	
	public String getLastFormTag() {
		return lastFormTag;
	}

	public void setLastFormTag(String lastFormTag) {
		this.lastFormTag = lastFormTag;
		writeData(AlarmApplication.getApp(), "lastFormTag");
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
		writeData(AlarmApplication.getApp(), "point");
	}

	public Boolean getIsLowMemory() {
		return isLowMemory;
	}

	public void setIsLowMemory(Boolean isLowMemory) {
		this.isLowMemory = isLowMemory;
		writeData(AlarmApplication.getApp(), "isLowMemory");
	}

	public Integer getLastPunishCount() {
		return lastPunishCount;
	}
	
	public void setLastPunishCount(Integer lastPunishCount) {
		this.lastPunishCount = lastPunishCount;
		writeData(AlarmApplication.getApp(), "lastPunishCount");
	}

	public Long getLastTime() {
		return lastTime;
	}

	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
		writeData(AlarmApplication.getApp(), "lastTime");
	}

	public Integer getLazyButtonOffClickCount() {
		return lazyButtonOffClickCount;
	}

	public void setLazyButtonOffClickCount(Integer lazyButtonOffClickCount) {
		this.lazyButtonOffClickCount = lazyButtonOffClickCount;
	}

	@Override
	public void iniParam() {
		alarmRingMode = Symbol.RINGMODE_MUSIC|Symbol.RINGMODE_SLOWLOUD;
		hasAlarmSetHelp = false;
		firstLogin = true;
		userNickName = "主人";
		sleepTime = 10;
		snsBindName = "";
		lazyButtonOffClickCount = 0;
		lastTime = System.currentTimeMillis();
		lastPunishCount = 0;
		isLowMemory = false;
		point = "0";
		isFirstInstall =true;
		lastFormTag = "default";
	}
	
	public int getAlarmRingMode() {
		return alarmRingMode;
	}

	public void setAlarmRingMode(Integer alarmRingMode) {
		this.alarmRingMode = alarmRingMode;
		writeData(AlarmApplication.getApp(), "alarmRingMode");
	}

	public Boolean getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
		writeData(AlarmApplication.getApp(), "firstLogin");
	}

	public String getSnsBindName() {
		return snsBindName;
	}

	public void setSnsBindName(String snsBindName) {
		this.snsBindName = snsBindName;
		writeData(AlarmApplication.getApp(), "snsBindName");
	}

	private AlarmUser(Context c){
		super(c);
		
	}
	public void save(Context c){
		try {
			writeAllData(c);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
		writeData(AlarmApplication.getApp(), "userNickName");
	}
	/**
	 * 
	 * -1=关闭
	 * 分钟为单位
	 * */
	public int getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(Integer sleepTime) {
		this.sleepTime = sleepTime;
		MyLogger.e("aaaa", "sleepTime"+sleepTime);
		writeData(AlarmApplication.getApp(), "sleepTime");
	}
	
	
	public Boolean getHasAlarmSetHelp() {
		return hasAlarmSetHelp;
	}

	public void setHasAlarmSetHelp(Boolean hasAlarmSetHelp) {
		this.hasAlarmSetHelp = hasAlarmSetHelp;
		writeData(AlarmApplication.getApp(), "hasAlarmSetHelp");
	}

	public static AlarmUser getUser(Context c) {
		if(user==null){
			user=new AlarmUser( c);
			user.setPoint(PointManager.getPoint());
		}
		return user;
	}

	@Override
	public String toString() {
		return "AlarmUser [SharedName=" + SharedName + ", userNickName="
				+ userNickName + ", sleepTime=" + sleepTime + ", snsBindName="
				+ snsBindName + ", firstLogin=" + firstLogin
				+ ", hasAlarmSetHelp=" + hasAlarmSetHelp + ", alarmRingMode="
				+ alarmRingMode + ", lazyButtonOffClickCount="
				+ lazyButtonOffClickCount + ", lastTime=" + lastTime
				+ ", lastPunishCount=" + lastPunishCount + "]";
	}

	/**
	 * @return the isFirstInstall
	 */
	public Boolean getIsFirstInstall() {
		return isFirstInstall;
	}

	/**
	 * @param isFirstInstall the isFirstInstall to set
	 */
	public void setIsFirstInstall(Boolean isFirstInstall) {
		this.isFirstInstall = isFirstInstall;
		writeData(AlarmApplication.getApp(), "isFirstInstall");
	}

	

	


	
	
	
	

	
	
}
