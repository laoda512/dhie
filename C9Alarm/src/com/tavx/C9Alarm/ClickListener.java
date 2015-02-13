package com.tavx.C9Alarm;
/**
 * 处理分享页面的按键消息
 * 
 * */
public interface ClickListener {

	/**
	 * 确认发送消息
	 * @param 发送的内容
	 * */
	void onUpdateStatusClick(String status);
}
