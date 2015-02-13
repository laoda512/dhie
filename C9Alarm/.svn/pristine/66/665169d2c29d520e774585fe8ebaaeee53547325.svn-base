/**
 * 
 */
package com.tavx.C9Alarm.Manager;

import com.tavx.C9Alarm.AlarmApplication;

/**
 * @author Administrator
 * 
 */
public class ToastTontentManager {

	public static final int type_ad = 0;
	public static final int type_btn_click = 1;
	public static final int type_Alarm = 2;
	public static final int type_AddPoint = 3;
	public static final int type_newerror = 4;
	
	
	public static final int tag_ad_baidubanner=0;
	public static final int tag_btn_click_normal=1;
	public static final int tag_Alarm_awake=2;
	public static final int tag_AddPoint_Over = 3;
	public static final int tag_neterror_vote=4;
	public static final int tag_neterror_vote_download=5;
	private static ToastTontentManager instance;
	public final String mix = "@";

	public static ToastTontentManager getInstance() {
		if (instance == null)
			instance = new ToastTontentManager();
		return instance;
	}

	public void toast(int type , int tag, String... parm) {
		switch ( type) {
		case type_ad: {
			switch (tag) {
			case tag_ad_baidubanner:
				if(parm==null||parm.length==0) return;
				int howMuch = NumberManager.getInstance().parseInt(parm[0]);
			//	AlarmApplication.getApp().showToast("点击广告,"+(getText(howMuch).replace("[count]", ""+howMuch)));
				break;

			default:
				break;
			}
		}
		
		case type_btn_click: {
			switch (tag) {
			case tag_btn_click_normal:
				if(parm==null||parm.length==0) return;
				int howMuch = NumberManager.getInstance().parseInt(parm[0]);
			//	AlarmApplication.getApp().showToast("积极使用软件，"+getText(howMuch).replace("[count]", ""+howMuch));
				break;

			default:
				break;
			}
		}
		case type_Alarm: {
			switch (tag) {
			case tag_Alarm_awake:
				if(parm==null||parm.length==0) return;
				int howMuch = NumberManager.getInstance().parseInt(parm[0]);
				AlarmApplication.getApp().showToast("闹钟响了"+getText(howMuch).replace("[count]", ""+howMuch));
				break;

			default:
				break;
			}
		}
		case type_AddPoint: {
			switch (tag) {
			case tag_AddPoint_Over:
				AlarmApplication.getApp().showToast("24小时内已经使用超过200点元气了，精力再足也要注意身体啊少年！");
				break;

			default:
				break;
			}
		}
		case type_newerror: {
			switch (tag) {
			case tag_neterror_vote:
				if(parm==null||parm.length==0) return;
				AlarmApplication.getApp().showToast(parm[0]);
				break;

			case tag_neterror_vote_download:
				if(parm==null||parm.length==0) return;
				AlarmApplication.getApp().showToast(parm[0]);
				break;

			default:
				break;
			}
		}
		

		}

	}
	
	
	public String getText(int howMuch){
		if(howMuch>0&&howMuch<11){
			return "恭喜你，获得了[count]点元气";
		}else if(howMuch>10&&howMuch<21){
			return "运气真好，你获得了[count]点元气";
		}else if(howMuch>20&&howMuch<31){
			return "鸿运当头，你获得了[count]点元气";
		}else if(howMuch>30&&howMuch<61){
			return "你获得了[count]点元气，顿时元气满满";
		}else if(howMuch>60&&howMuch<200){
			return "啊,一发元气蛋，你获得了[count]点元气";
		}else if(howMuch>200){
			//TODO:
			return "作弊是可耻的！";
		}
		return "";
 			
	}
}
