package com.tavx.C9Alarm.cloud.Bean.Bmob;

import java.lang.reflect.InvocationTargetException;

import util.readWriteAbleBean;
import android.content.Context;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.cloud.Interface.Saver;

	public  class QASaver extends readWriteAbleBean implements Saver{
		/**
		 * @param c
		 * @param _tag
		 */
		public QASaver(Context c, String _tag) {
			super(c, _tag);
			// TODO Auto-generated constructor stub
		}
		String title;
		String question;
		String answer;
		String id;
		String author;
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		BmobFile pic;
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
			return id;
		}
		public BmobFile getPic() {
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
		public void setPic(BmobFile pic) {
			this.pic = pic;
		}
		
		public void save(Object f){
			QA qa = (QA) f;
			
			this.setAnswer(qa.getAnswer());
			this.setAuthor(qa.getAuthor());
			this.setId(qa.getId());
			this.setPic(qa.getPic());
			this.setQuestion(qa.getQuestion());
			this.setTitle(qa.getTitle());
		//	this.setPoint(fm.getPoint());
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
		/* (non-Javadoc)
		 * @see util.readWriteAbleBean#iniParam()
		 */
		@Override
		public void iniParam() {
			// TODO Auto-generated method stub
			
		}
		
	}