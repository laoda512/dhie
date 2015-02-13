/**
 * 
 */
package com.tavx.C9Alarm.cloud.Bean.Bmob;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * @author Administrator
 *
 */
public class form_discount {
	List<String> adUrl;
	public List<String> getAdUrl() {
		return adUrl;
	}
	public void setAdUrl(List<String> adUrl) {
		this.adUrl = adUrl;
	}
	String form_name;
	boolean isShow;
	public String getForm_name() {
		return form_name;
	}
	public boolean isShow() {
		return isShow;
	}
	public void setForm_name(String form_name) {
		this.form_name = form_name;
	}
	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}
}
