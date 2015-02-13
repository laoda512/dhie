package com.tavx.C9Alarm.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("Point")
public class Point extends AVObject {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return getString("displayName");
	}

	public void setDisplayName(String value) {
		put("displayName", value);
	}

}
