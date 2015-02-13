/**
 * 
 */
package com.tavx.C9Alarm.Form;

import org.xmlpull.v1.XmlPullParser;

import util.ResXmlReader;
import util.SDResReadManager;
import util.ThemeRes;
import util.AnimationsContainer.AnimContainer;

/**
 * @author Administrator
 *
 */
public class AlarmTypeMovie extends AlarmType{
	public static final int Orientation_horizonal=0;
	public static final int Orientation_verctal =1;;
	
	int alarm_mad;
	
	public int getAlarm_mad() {
		return alarm_mad;
	}

	public int getAlarm_orientation() {
		return alarm_orientation;
	}

	public void setAlarm_mad(int alarm_mad) {
		this.alarm_mad = alarm_mad;
	}

	public void setAlarm_orientation(int alarm_orientation) {
		this.alarm_orientation = alarm_orientation;
	}

	
	int alarm_orientation;
	public static AlarmType parse(XmlPullParser anmiParser,ThemeRes mThemeRes){

		AlarmTypeMovie mAlarmTypeAnim = new AlarmTypeMovie();
		String alarm_mad  =anmiParser.getAttributeValue(null, "alarm_mad");
		String alarm_orientation  =anmiParser.getAttributeValue(null, "alarm_orientation");

		
		alarm_mad = checkIsNull(alarm_mad);
		alarm_orientation = checkIsNull(alarm_orientation);
	

		mAlarmTypeAnim.setAlarm_mad(mThemeRes.addRes(alarm_mad));
		
		int orienation = Orientation_verctal;
		if(alarm_orientation!=null&&alarm_orientation.startsWith("h")){
			orienation = Orientation_horizonal;
		}
		
		mAlarmTypeAnim.setAlarm_orientation(orienation);
		mAlarmTypeAnim.setType(TYPE_MOVIE);
		
		return mAlarmTypeAnim;
	
	}
	
	public  static String checkIsNull(String res){
		if(res==null||res.equals("")||res.toLowerCase().equals("null")||res.equals("-1")){
			return null;
		}
		return res;
	}
	
	
}
