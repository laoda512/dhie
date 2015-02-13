/**
 * 
 */
package com.tavx.C9Alarm.cloud.Bean.Bmob;


import java.sql.Date;
import java.util.Calendar;

import org.apache.commons.httpclient.util.DateUtil;

import util.PhoneInfo;

import com.google.gson.annotations.Expose;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.Symbol;
import com.tavx.C9Alarm.Manager.NumberManager;

import cn.bmob.v3.BmobObject;

/**
 * @author Administrator
 *
 */
public class pointUse extends BmobObject{

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	@Expose
	private int point;
	@Expose
	private String deviceId;
	
	
	
	public pointUse() {
		deviceId  = PhoneInfo.getIMEI(AlarmApplication.getApp());
		point = 0;
	}
	public int getPoint() {
		return point;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	
	
	
	
	
	
}
