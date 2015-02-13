/**
 * 
 */
package com.tavx.C9Alarm.cloud.Bean.Bmob;

import cn.bmob.v3.BmobObject;

/**
 * @author Administrator
 *
 */
public class functionLock extends BmobObject{
	
	private boolean  isBaiduBannerOn;
	private int pointRate;
	private int pointMax;
	
	public functionLock() {
		super();
		this.isBaiduBannerOn = true;
		pointRate = 100;
		pointMax = 200;
	}
	
	public int getPointRate() {
		return pointRate;
	}

	public int getPointMax() {
		return pointMax;
	}

	public void setPointRate(int pointRate) {
		this.pointRate = pointRate;
	}

	public void setPointMax(int pointMax) {
		this.pointMax = pointMax;
	}

	

	

	public boolean isBaiduBannerOn() {
		return isBaiduBannerOn;
	}

	public void setBaiduBannerOn(boolean isBaiduBannerOn) {
		this.isBaiduBannerOn = isBaiduBannerOn;
	}
	

}
