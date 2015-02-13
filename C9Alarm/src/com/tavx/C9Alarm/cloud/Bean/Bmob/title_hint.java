/**
 * 
 */
package com.tavx.C9Alarm.cloud.Bean.Bmob;

import com.google.gson.annotations.Expose;

import cn.bmob.v3.BmobObject;

/**
 * @author Administrator
 *
 */
public class title_hint extends BmobObject{
	@Expose
	private String hint_download;
	@Expose
	private String hint_qa;
	@Expose
	private String hint_setting;
	@Expose
	private String hint_vote;
	@Expose
	private String hint_Ad;
	@Expose
	private String hint_Share;
	
	public String getHint_Ad() {
		return hint_Ad;
	}
	public void setHint_Ad(String hint_Ad) {
		this.hint_Ad = hint_Ad;
	}
	private String hint_Square;
	
	public String getHint_Square() {
		return hint_Square;
	}
	public void setHint_Square(String hint_Square) {
		this.hint_Square = hint_Square;
	}
	public title_hint() {
		super();
		this.hint_download = "这里可以下载主题~还可以用元气支持你喜欢的角色哟~";
		this.hint_qa = "使用上的困惑或者bug反馈都在这里，留言的话苦逼作者就会看到哟~";
		this.hint_setting = "这里可以修改各种设置哟~";
		this.hint_vote = "下一个制作的主题会根据投票结果来，以后会开放发起投票功能，敬请期待哟~";
		hint_Ad = "点击广告支持软件发展,还会有元气作奖励哦~有大家的支持我才能做得更好";
		hint_Square = "广场是个自由的地方，以后会开放发帖功能哟~哟~";
		hint_Share = "^_^用元气给喜爱的角色加分吧~";
	}
	public String getHint_download() {
		return hint_download;
	}
	public String getHint_qa() {
		return hint_qa;
	}
	public String getHint_setting() {
		return hint_setting;
	}
	public String getHint_vote() {
		return hint_vote;
	}
	public void setHint_download(String hint_download) {
		this.hint_download = hint_download;
	}
	public void setHint_qa(String hint_qa) {
		this.hint_qa = hint_qa;
	}
	public void setHint_setting(String hint_setting) {
		this.hint_setting = hint_setting;
	}
	public void setHint_vote(String hint_vote) {
		this.hint_vote = hint_vote;
	}
	/**
	 * @return the hint_Share
	 */
	public String getHint_Share() {
		return hint_Share;
	}
	/**
	 * @param hint_Share the hint_Share to set
	 */
	public void setHint_Share(String hint_Share) {
		this.hint_Share = hint_Share;
	}
	

}
