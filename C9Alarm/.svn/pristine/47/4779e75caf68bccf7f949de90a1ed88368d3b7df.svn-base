package com.tavx.C9Alarm.bean;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.AlarmApplication;

import android.content.Context;
import android.util.Log;
import util.readWriteAbleBean;

public class HotInfo extends readWriteAbleBean{
	
	public HotInfo(Context c,String tag) {
		super(c,tag);
		MyLogger.e("aaa", tag+" "+toString());
	}

	@Override
	public void iniParam() {
		sumCount = 0;
		intTimeCount = 0;
		lastTimeCount = 0;
		formIndex = -1;
		lastTime = System.currentTimeMillis();
	};
	
	public Integer formIndex;
	public Integer sumCount;
	public Integer intTimeCount;
	public Integer lastTimeCount;//持续多少天
	public Long lastTime;//上一次时间
	
	public Long getLastTime() {
		return lastTime;
	}

	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
		writeData(AlarmApplication.getApp(), "lastTime");
	}

	public Integer getFormIndex() {
		return formIndex;
	}

	public Integer getSumCount() {
		return sumCount;
	}

	public Integer getIntTimeCount() {
		return intTimeCount;
	}

	public Integer getLastTimeCount() {
		return lastTimeCount;
	}

	public void setFormIndex(Integer formIndex) {
		this.formIndex = formIndex;
		writeData(AlarmApplication.getApp(), "formIndex");
	}

	public void setSumCount(Integer sumCount) {
		this.sumCount = sumCount;
		writeData(AlarmApplication.getApp(), "sumCount");
	}

	public void setIntTimeCount(Integer intTimeCount) {
		this.intTimeCount = intTimeCount;
		writeData(AlarmApplication.getApp(), "intTimeCount");
	}

	public void setLastTimeCount(Integer lastTimeCount) {
		if(lastTimeCount<0){
			lastTimeCount = 0;
		}
		this.lastTimeCount = lastTimeCount;
		writeData(AlarmApplication.getApp(), "lastTimeCount");
	}

	@Override
	public String toString() {
		return "HotInfo [formIndex=" + formIndex + ", sumCount=" + sumCount
				+ ", intTimeCount=" + intTimeCount + ", lastTimeCount="
				+ lastTimeCount + ", lastTime=" + lastTime + "]";
	}
	
	
}
