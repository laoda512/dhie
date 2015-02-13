/**
 * 
 */
package com.tavx.C9Alarm.cloud.Bean.Bmob;


import java.lang.reflect.InvocationTargetException;

import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.cloud.Interface.Saver;

import android.content.Context;
import util.readWriteAbleBean;
import cn.bmob.v3.BmobObject;

/**
 * @author Administrator
 *
 */
public class form_pointSaver extends readWriteAbleBean  implements Saver{

	/**
	 * @param c
	 * @param _tag
	 */
	public form_pointSaver(Context c, String _tag) {
		super(c, _tag);
	}
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private int point;
	private String name;
	private RemoteFormData jifen;
	public int getPoint() {
		return point;
	}
	public String getName() {
		return name;
	}
	public RemoteFormData getJifen() {
		return jifen;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setJifen(RemoteFormData jifen) {
		this.jifen = jifen;
	}
	/* (non-Javadoc)
	 * @see util.readWriteAbleBean#iniParam()
	 */
	@Override
	public void iniParam() {
		// TODO Auto-generated method stub
		
	}
	
	public void save(Object f){
		form_point fm = (form_point) f;
		this.setName(fm.getName());
		this.setPoint(fm.getPoint());
			try {
				writeAllData(AlarmApplication.getApp());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
