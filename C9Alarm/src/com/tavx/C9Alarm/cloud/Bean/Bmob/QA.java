package com.tavx.C9Alarm.cloud.Bean.Bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

	public  class QA extends BmobObject {
		String title;
		String question;
		String answer;
		String id;
		String author;
		BmobFileX pic;
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		
		public String getTitle() {
			return title;
		}
		public String getQuestion() {
			return question;
		}
		public String getAnswer() {
			return answer;
		}
		public String getId() {
			return getTitle()+"@"+getObjectId();
		}
		public BmobFileX getPic() {
			return pic;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public void setQuestion(String question) {
			this.question = question;
		}
		public void setAnswer(String answer) {
			this.answer = answer;
		}
		public void setId(String id) {
			this.id = id;
		}
		public void setPic(BmobFileX pic) {
			this.pic = pic;
		}
		
		
	}