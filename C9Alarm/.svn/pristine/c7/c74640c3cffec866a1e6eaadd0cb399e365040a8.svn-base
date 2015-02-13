/**
 * 
 */
package com.tavx.C9Alarm.bean;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.Symbol;
import com.tavx.C9Alarm.Manager.NumberManager;

import android.content.Context;
import util.readWriteAbleBean;

/**
 * @author Administrator
 *
 */
public class PointCountBean extends readWriteAbleBean{

	
	private String count;
	private Long date;
	private String MAX_COUNT ;
	/**
	 * @param c
	 * @param _tag
	 */
	public PointCountBean(Context c, String _tag) {
		super(c, _tag);
		MyLogger.e("aaa", _tag+" "+toString());
	}

	@Override
	public String toString() {
		return "ClickBean [count=" + count + ", date=" + date + ", MAX_COUNT="
				+ MAX_COUNT + "]";
	}

	/* (non-Javadoc)
	 * @see util.readWriteAbleBean#iniParam()
	 */
	@Override
	public void iniParam() {
		count = "0";
		date =0l;
		MAX_COUNT = "200";
	}
	
	public boolean hasOverMax(){
		NumberManager nm = NumberManager.getInstance();
		refixTime(nm);
		if(nm.isLargerEqual(getCount(), MAX_COUNT)) return true;
		return false;
	}
	
	public String getCount() {
		return count;
	}

	public Long getDate() {
		return date;
	}

	public void setCount(String count) {
		this.count = count;
		writeData(AlarmApplication.getApp(), "count");
	}

	public void setDate(Long date) {
		this.date = date;
		writeData(AlarmApplication.getApp(), "date");
	}
	
	public boolean addCount(String howMuch){
		NumberManager nm = NumberManager.getInstance();
		refixTime(nm);
		
		
		if(nm.isLargerEqual(getCount(), MAX_COUNT)) return false;
		setCount(nm.add(getCount(), howMuch));
		return true;
		//setCount(getCount()+1);
	}
	
	private void refixTime(NumberManager nm ){
		Long time = System.currentTimeMillis();
		if(checkIsTimeOverMax()){
			setDate(time);
			setCount(nm.getMixedString(0));
		};
	}
	
	private boolean checkIsTimeOverMax(){
		Long time = System.currentTimeMillis();
		if(time>getDate()+getTimeInterval()){
			return true;
		};
		return false;
	}
	
	private long getTimeInterval(){
		return Symbol.INTERVAL_DAY/24*20;
	}
	

}
