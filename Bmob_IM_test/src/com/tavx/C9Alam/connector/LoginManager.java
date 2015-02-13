package com.tavx.C9Alam.connector;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.ui.LoginActivity;
import com.bmob.im.demo.util.CollectionUtils;

public class LoginManager {
	public final static int SUCCESS = 0;
	public final static int FAILED = 1;
   public  void login(String name ,String password,final Context c,final Handler callBack,final ProgressDialog progress){
	   User bmobchatuser = new User();
       bmobchatuser.setUsername(name);
       bmobchatuser.setPassword(password);
       bmobchatuser.login(c,new  SaveListener(){

		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub
		 
			User currentUser = (User)  BmobUser.getCurrentUser(c, User.class);
			// 存入内存中
			BmobLog.i("当前用户的头像："+currentUser.getAvatar());
			MyLogger.e("login", ""+currentUser.getNick());
			CustomApplcation.getInstance().setNick(currentUser.getNick());
			CustomApplcation.getInstance().setUserAvatar(currentUser.getAvatar());
			//查询该用户的好友列表
			
			if(progress!=null)
			((Activity)c).runOnUiThread(new Runnable() {

				@Override
				public void run() {
					progress.setMessage("正在获取好友列表...");
				}
			});
			//这里默认采取的是登陆成功之后即将好于列表存储到数据库中，并更新到当前内存中,
			 BmobUserManager.getInstance(c).queryCurrentContactList(new FindListener<BmobChatUser>() {

						@Override
						public void onError(int arg0, String arg1) {
							// TODO Auto-generated method stub
							if(arg0==BmobConfig.CODE_COMMON_NONE){
								
							}else{
								
							}
						}

						@Override
						public void onSuccess(List<BmobChatUser> arg0) {
							// TODO Auto-generated method stub
							// 保存到application中方便比较
							CustomApplcation.getInstance().setContactList(CollectionUtils.list2map(arg0));
						}
					});
			 
			 if(progress!=null){
			progress.dismiss();
			 }
			
			 if(callBack!=null){
				 callBack.sendEmptyMessage(SUCCESS);
			 }
		}

		@Override
		public void onFailure(int errorcode, String arg0) {
			// TODO Auto-generated method stub
			 if(progress!=null){
					progress.dismiss();
					 }
			 if(callBack!=null){
				 Message m=new Message();
				 m.obj = arg0;
				 m.arg1 = errorcode;
				 m.what = FAILED;
				 callBack.sendMessage(m);
			 }
		}
	});
   }
   
   public void login(String name,String password,Activity a,Handler callback){
		final ProgressDialog progress = new ProgressDialog(
				a);
		progress.setMessage("正在登陆...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		
		login(name, password, a, callback, progress);
   }
   
   public void login(String name,String password,Handler callback,Context c){
		login(name, password, c, callback, null);
  }
  
   private static LoginManager mLoginManager;
   
   public static LoginManager getInstance(){
	   if(mLoginManager==null) mLoginManager =new LoginManager();
	   return mLoginManager;
   }
   
   public User getUser(Context c){
	   return  BmobUserManager.getInstance(c).getCurrentUser(User.class);
   } 
   
   public void LoginByActivity(Activity mActivity){
	   Intent i = new Intent();
	   i.setClass(mActivity, LoginActivity.class);
	   mActivity.startActivity(i);
			   
   }
   
   public void register(String name,String password,String nickname,String installId,CloudCodeListener mCloudCodeListener){
			try {
				String Did = AlarmConnector.getConnector().getDeviceId();
				String Dkey = AlarmConnector.getConnector().getDeviceKey();
				JSONObject mj = new JSONObject();
				mj.put("username", name);
				mj.put("password", password);
				mj.put("nickname", nickname);
				mj.put("deviceId", Did);
				mj.put("deviceKey",Dkey);
				mj.put("installId", installId);
				AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
				ace.callEndpoint(CustomApplcation.getInstance(), "register", mj, mCloudCodeListener);
			} catch (JSONException e) {
				e.printStackTrace();
			}
   }
}
