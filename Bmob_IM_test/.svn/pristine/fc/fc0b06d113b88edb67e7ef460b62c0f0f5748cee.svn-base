package com.tavx.C9Alam.connector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.bmob.im.demo.CustomApplcation;

import android.app.Application;

public class AlarmConnector {
	static Connector mConnector;
	public static void setConnector(Connector c){
		mConnector = c;
	}
	
	public static Connector getConnector(){
		if(mConnector==null)generateConnector();
		return mConnector;
	}
	public static Application getApplication(){
		return  getConnector().getApplication();
	}
	
	private static void generateConnector(){
		try {
			Class c = Class.forName("com.tavx.C9Alarm.AlarmApplication");
			Object o = c.getMethod("getApp").invoke(null) ;
			c.getMethod("setConnector").invoke(o);
			
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
