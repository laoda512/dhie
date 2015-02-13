/**
 * 
 */
package com.tavx.C9Alarm.cloud.Bean.Bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author Administrator
 *
 */
public class Vote_who_add_point extends BmobObject{

	private String deviceid;

	public String getDeviceid() {
		return deviceid;
	}

	public String getPoint() {
		return point;
	}

	public String getName() {
		return name;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String point;
	
	private String name;

}
