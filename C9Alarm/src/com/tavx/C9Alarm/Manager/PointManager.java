/**
 * 
 */
package com.tavx.C9Alarm.Manager;

import java.util.List;


import net.youmi.android.offers.PointsChangeNotify;
import net.youmi.android.offers.PointsManager;

import org.json.JSONException;
import org.json.JSONObject;

import util.PhoneInfo;
import util.md5;
import android.os.Handler;
import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.FindListener;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alam.connector.responseBean;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.Utils;
import com.tavx.C9Alarm.bean.ADBean;
import com.tavx.C9Alarm.bean.AlarmAwakeBean;
import com.tavx.C9Alarm.bean.ClickBean;
import com.tavx.C9Alarm.bean.PointCountBean;
import com.tavx.C9Alarm.bw.OffersManager;
import com.tavx.C9Alarm.bw.PointsChangeListener;
import com.tavx.C9Alarm.cloud.Bean.Bmob.Vote_form_point;
import com.tavx.C9Alarm.cloud.Bean.Bmob.Vote_who_add_point;
import com.tavx.C9Alarm.cloud.Bean.Bmob.form_point;
import com.tavx.C9Alarm.cloud.Bean.Bmob.pointUse;
import com.tavx.C9Alarm.cloud.Bean.Bmob.who_add_point;
import com.tavx.C9Alarm.cloud.Interface._RemoteFormData;
import com.tavx.C9Alarm.cloud.Interface._VoteFormData;
import com.umeng.socom.Log;

/**
 * @author Administrator
 * 
 */
public class PointManager {
	
	public static final int type_ad = 0;
	public static final int type_btn_click = 1;
	public static final int type_Alarm = 2;
	public static final int tag_ad_baidubanner=0;
	public static final int tag_btn_click_normal=1;
	public static final int tag_Alarm_awake=2;
	
	private static final int rate_point = 1;
	
	private static PointManager pm;
	
	public static PointManager getInstance(){
		if(pm==null) pm=new PointManager();
		return pm;
			
	}
	
	private  PointManager(){
		if(checkNetWork()){
			//queryPointUSeByNet();
		}
	}
	
	public static int PointChange(int type,int tag){
		switch (type) {
		case type_ad:{
			
			switch (tag) {
			case tag_ad_baidubanner:
				ADBean adb=new ADBean(AlarmApplication.getApp(), type+"@"+tag);
				return  addByBaiduBanner(adb);

			default:
				break;
			}
		}
			
			
			break;
		case type_btn_click:{
			
			switch (tag) {
			case tag_btn_click_normal:
				ClickBean clb=new ClickBean(AlarmApplication.getApp(), type+"@"+tag);
				return  addByButtonClickNormal(clb);

			default:
				break;
			}
		}
		case type_Alarm:{
			
			switch (tag) {
			case tag_Alarm_awake:
				AlarmAwakeBean alb=new AlarmAwakeBean(AlarmApplication.getApp(), type+"@"+tag);
				return  addByAlarmAwake(alb);

			default:
				break;
			}
		}
		default:
			break;
		}
		return -1;
		
		
	}
	
	/**
	 * @param alb
	 * @return
	 */
	private static int addByAlarmAwake(AlarmAwakeBean alb) {
		if(alb.hasOverMax()) return -1;
		//boolean b = alb.addCount();
			int rate = NumberManager.getInstance().parseInt(alb.getCount());
			if(Utils.random(0, rate)<1){
				String res= getRandomPoint(10*rate_point, 50*rate_point/(rate+1));
				int point = NumberManager.getInstance().parseInt(res);
				addPoint(res);
				alb.addCount();
				return point;
			}
		return -1;
	}
	
	private static float getRate(){
		return AlarmApplication.getFLock().getPointRate()/100f;
	}
	
	/**
	 * @param clb
	 * @return
	 */
	private static int addByButtonClickNormal(ClickBean clb) {
		if(clb.hasOverMax()) return -1;
	
			int rate = NumberManager.getInstance().parseInt(clb.getCount());
			if(Utils.random(0, rate*10+1)<5){
				String res= getRandomPoint(1*rate_point, 5*rate_point);
				int point = NumberManager.getInstance().parseInt(res);
				addPoint(res);
				clb.addCount();
				return point;
			}
		
		return -1;
	}

	private static int addByBaiduBanner(ADBean adb){
		
		boolean b = adb.addCount();
		if(b==false){
			return -1;
		}
		else{
			String res= getRandomPoint(15*rate_point, 35*rate_point);
			int point = NumberManager.getInstance().parseInt(res);
			addPoint(res);
			return point;
		} 
	}
	
	public static String  getRandomPoint(float start,float end){
		return NumberManager.getInstance().getMixedString(Utils.random((int)start, (int)end));
	}
	
	public static void minusPoint(String howMuch){
		//PointsManager.getInstance(AlarmApplication.getApp()).spendPoints(NumberManager.getInstance().parseInt(howMuch));
		OffersManager.subPoints(AlarmApplication.getApp(), NumberManager.getInstance().parseInt(howMuch));
	}
	
	public static void addPoint(String howMuch){
	//	 PointsManager.getInstance(AlarmApplication.getApp()).awardPoints(NumberManager.getInstance().parseInt(howMuch));
		OffersManager.addPoints(AlarmApplication.getApp(), NumberManager.getInstance().parseInt(howMuch));
	}
	
	public static String getPoint(){
		
		String paoint =""+ OffersManager.getPoints(AlarmApplication.getApp());
	//	String paoint =""+PointsManager.getInstance(AlarmApplication.getApp()).queryPoints();
		return paoint;
	}
	
//	public static String setPoint(){
//		
//		OffersManager.setPointsChangeListener(new PointsChangeListener() {
//			
//			@Override
//			public void onPointsChanged(int i) {
//				
//			}
//		});
//		//String paoint =""+ OffersManager.(AlarmApplication.getApp());
//	//	String paoint =""+PointsManager.getInstance(AlarmApplication.getApp()).queryPoints();
//		return paoint;
//	}
//	

  
  public static void addPointCallBack(final Handler h){
		if(h==null){
			OffersManager.setPointsChangeListener(null);
		}
		OffersManager.setPointsChangeListener(new PointsChangeListener() {
			
			@Override
			public void onPointsChanged(int arg0) {
			h.sendEmptyMessage(0);
			}
		});
		
	}
	
	//youmi
//	private static PointsChangeNotify p;
//	
//	public static synchronized void addPointCallBack(final Handler h){
//
//		if(p!=null){
//			removePointCallBack(p);
//		}
//			
//		p = new PointsChangeNotify() {
//			
//			@Override
//			public void onPointBalanceChange(int i) {
//				Log.e("aaaa", "addpoint"+i);
//				h.sendEmptyMessage(0);
//			}
//		};
//		PointsManager.getInstance(AlarmApplication.getApp()).registerNotify(p);
//		
//	}
//	public static void removePointCallBack(){
//		if(p!=null){
//			removePointCallBack(p);
//		}
//	}
	
	private static void removePointCallBack(PointsChangeNotify obj){
		PointsManager.getInstance(AlarmApplication.getApp()).unRegisterNotify(obj);
	}
	
	public static boolean  addPointToFormLock = false;
	public static long  addPointToFormLockTime = 0l;

	public static synchronized boolean  addPointToForm(final _RemoteFormData mRemoteFormData,final String howMuch,final Handler mHandler) {
		if(checkNetWork()==false) return false;
		if(checkPointIsOverToday(howMuch)==true) return false;
		
		if(Integer.parseInt(getPoint())<Integer.parseInt(howMuch)) {
			AlarmApplication.getApp().showError("啊，讨厌！元气不足！");
			return false;
		}
		if(System.currentTimeMillis()-addPointToFormLockTime>30*1000){
			addPointToFormLock=false;
		}
		
		if(addPointToFormLock==true) {
			AlarmApplication.getApp().showError("你操作太快了，服务器娘晕了啦！");
			return false;
		}
		addPointToFormLock = true;
		addPointToFormLockTime = System.currentTimeMillis();
		
		if(mRemoteFormData instanceof com.tavx.C9Alarm.cloud.Bean.Bmob.RemoteFormData){
			com.tavx.C9Alarm.cloud.Bean.Bmob.RemoteFormData m = (com.tavx.C9Alarm.cloud.Bean.Bmob.RemoteFormData) mRemoteFormData;
			
			who_add_point wa =new who_add_point();
			wa.setName(mRemoteFormData.getName());
			wa.setPoint(howMuch);
			wa.setDeviceid(PhoneInfo.getIMEI(AlarmApplication.getApp()));
			wa.save(AlarmApplication.getApp());
			
			
			final form_point mf = m.getPointForSelf();
			addPointByCustomCode(howMuch, mf.getObjectId(), "form_point", new CloudCodeListener() {
				
				@Override
				public void onSuccess(Object obj) {
					addPointToFormLock = false;
					MyLogger.e("aaaaxxxx", ""+obj);
				   responseBean re = responseBean.parseBean( (String) obj );
				
					if(re.getSuccess()==0){
						minusPoint(howMuch);
						mf.setPoint(mf.getPoint()+ Integer.parseInt(howMuch));
						PointCountBean pcb = new PointCountBean(AlarmApplication.getApp(), "PointCountBean_yuanqi");
						pcb.addCount(howMuch);
						mHandler.sendEmptyMessage(0);
					}else{
						ToastTontentManager.getInstance().toast(ToastTontentManager.type_newerror, ToastTontentManager.tag_neterror_vote, re.getErrorContent());
					}
					
				
				}
				
				@Override
				public void onFailure(int i, String s) {
					addPointToFormLock = false;
					mHandler.sendEmptyMessage(0);
				}
			});
			
			/*mf.setJifen(null);
			
			mf.increment("point",  Integer.parseInt(howMuch));
			mf.update(AlarmApplication.getApp(), new UpdateListener()  {
				
				@Override
				public void onSuccess() {
					minusPoint(howMuch);
					mf.setPoint(mf.getPoint()+ Integer.parseInt(howMuch));
					PointCountBean pcb = new PointCountBean(AlarmApplication.getApp(), "PointCountBean_yuanqi");
					pcb.addCount(howMuch);
					mHandler.sendEmptyMessage(0);
				}
				@Override
				public void onFailure(int i, String s) {
					
					mHandler.sendEmptyMessage(0);
				}
			});*/
			return true;
		}else{
			
			
			AVObject myComment = new AVObject("who_add_point");
			myComment.put("name", mRemoteFormData.getName());
			myComment.put("point", "");
			myComment.put("deviceid", PhoneInfo.getIMEI(AlarmApplication.getApp()));
			myComment.saveInBackground();
			
			((AVObject)mRemoteFormData.getPointForSelf()).increment("point", Integer.parseInt(howMuch));
			((AVObject)mRemoteFormData.getPointForSelf()).saveInBackground(new SaveCallback() {
				
				@Override
				public void done(AVException avexception) {
					
					mHandler.sendEmptyMessage(0);
				}
			});
			return true;
		}
		
		
	}

	
	
	
	
	public static boolean  addPointToVoteFormLock = false;
	public static long  addPointToVoteFormLockTime = 0l;
	public static  boolean  addPointToVoteForm(final _VoteFormData mVoteFormData,final String howMuch,final Handler mHandler) {
		if(checkNetWork()==false) return false;
		if(checkPointIsOverToday(howMuch)==true) return false;
		
		if(Integer.parseInt(getPoint())<Integer.parseInt(howMuch)) {
			AlarmApplication.getApp().showError("啊，讨厌！元气不足！");
			return false;
		}
		//	if(Integer.parseInt(getPoint())<Integer.parseInt(howMuch)) return false;
		
		
		
		if(System.currentTimeMillis()-addPointToVoteFormLockTime>30*1000){
			addPointToVoteFormLock=false;
		}
		
		if(addPointToVoteFormLock==true) {
			AlarmApplication.getApp().showError("你操作太快了，服务器娘晕了啦！");
			return false;
		}
		addPointToVoteFormLock = true;
		addPointToVoteFormLockTime = System.currentTimeMillis();
		
		
			
			if(mVoteFormData instanceof com.tavx.C9Alarm.cloud.Bean.Bmob.VoteFormData){
				com.tavx.C9Alarm.cloud.Bean.Bmob.VoteFormData m = (com.tavx.C9Alarm.cloud.Bean.Bmob.VoteFormData) mVoteFormData;
				
				Vote_who_add_point wa =new Vote_who_add_point();
				wa.setName(mVoteFormData.getName());
				wa.setPoint(howMuch);
				wa.setDeviceid(PhoneInfo.getIMEI(AlarmApplication.getApp()));
				wa.save(AlarmApplication.getApp());
				
				
				final Vote_form_point mf = m.getPointForSelf();
				addPointByCustomCode(howMuch, mf.getObjectId(), "Vote_form_point", new CloudCodeListener() {
					
					@Override
					public void onSuccess(Object obj) {
						addPointToVoteFormLock = false;
						MyLogger.e("aaaaxxxx", ""+obj);
						Gson g =new GsonBuilder().create();
						responseBean re = g.fromJson(((String) obj).replace("\\", ""), new TypeToken<responseBean>(){}.getType());
						if(re.getSuccess()==0){
							minusPoint(howMuch);
							mf.setPoint(mf.getPoint()+ Integer.parseInt(howMuch));
							PointCountBean pcb = new PointCountBean(AlarmApplication.getApp(), "PointCountBean_yuanqi");
							pcb.addCount(howMuch);
							mHandler.sendEmptyMessage(0);
						}else{
							ToastTontentManager.getInstance().toast(ToastTontentManager.type_newerror, ToastTontentManager.tag_neterror_vote, re.getErrorContent());
						}
						
					
					}
					
					@Override
					public void onFailure(int i, String s) {
						addPointToVoteFormLock = false;
						mHandler.sendEmptyMessage(0);
					}
				});
				
//				mf.setJifen(null);
//				
//				mf.increment("point",  Integer.parseInt(howMuch));
//				mf.update(AlarmApplication.getApp(), new UpdateListener()  {
//					
//					@Override
//					public void onSuccess() {
//						minusPoint(howMuch);
//						mf.setPoint(mf.getPoint()+ Integer.parseInt(howMuch));
//						PointCountBean pcb = new PointCountBean(AlarmApplication.getApp(), "PointCountBean_yuanqi");
//						pcb.addCount(howMuch);
//						mHandler.sendEmptyMessage(0);
//					}
//					@Override
//					public void onFailure(int i, String s) {
//						mHandler.sendEmptyMessage(0);
//					}
//				});
				return true;
			}else{
//				
//				
//				AVObject myComment = new AVObject("who_add_point");
//				myComment.put("name", mRemoteFormData.getName());
//				myComment.put("point", "");
//				myComment.put("deviceid", PhoneInfo.getIMEI(AlarmApplication.getApp()));
//				myComment.saveInBackground();
//				
//				((AVObject)mRemoteFormData.getPointForSelf()).increment("point", Integer.parseInt(howMuch));
//				((AVObject)mRemoteFormData.getPointForSelf()).saveInBackground(new SaveCallback() {
//					
//					@Override
//					public void done(AVException avexception) {
//						
//						mHandler.sendEmptyMessage(0);
//					}
//				});
				return true;
			}
			
			
		}

	private static void addPointByCustomCode(String howMuch,String objid,String tableName,CloudCodeListener mCloudCodeListener){
		try {
			String imei = PhoneInfo.getIMEI(AlarmApplication.getApp());
			JSONObject mj = new JSONObject();
			mj.put("howMuch", howMuch);
			mj.put("objectId", objid);
			mj.put("table", tableName);
			mj.put("deviceId", imei);
			mj.put("deviceKey",md5.md5s(imei+"@_tavx"));
			AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
			ace.callEndpoint(AlarmApplication.getApp(), "addPoint", mj, mCloudCodeListener);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 
	 */
	private static boolean checkPointIsOverToday(String howMuch) {
//		if(getInstance().pu==null){
//			getInstance().queryPointUSeByNet();
//		}else{
//			if(getInstance().pu.getPoint()>200) return true;
//			
//		}
		
		
		PointCountBean pcb = new PointCountBean(AlarmApplication.getApp(), "PointCountBean_yuanqi");
		boolean hasOver= pcb.hasOverMax();
		if(hasOver){
			ToastTontentManager.getInstance().toast(ToastTontentManager.type_AddPoint, ToastTontentManager.tag_AddPoint_Over, null);
		}
		return hasOver;
		
	}
		
	
	public  void queryPointUSeByNet(){
		queryPointUSeByNet(3);
	}
		
		private  void queryPointUSeByNet(final int retryCount){
			
			BmobQuery<pointUse> query = new BmobQuery<pointUse>();
			query.addWhereNotEqualTo("deviceId", PhoneInfo.getIMEI(AlarmApplication.getApp()));
			query.findObjects(AlarmApplication.getApp(), new FindListener<pointUse>() {
			        @Override
			        public void onSuccess(List<pointUse> object) {
			        	if(object==null||object.size()==0){
			        		pu = new pointUse();
			        		pu.update(AlarmApplication.getApp());
			        	}else{
			        		pu = object.get(0);
			        	}
			        }
			        @Override
			        public void onError(int code, String msg) {
			        	if(checkNetWork())
			        	if(retryCount>0){
			        		queryPointUSeByNet(retryCount-1);
			        	}
			        }
			});
		
	}
	
	public pointUse pu;
		
		/**
		 * `
		 */
		private static boolean checkNetWork() {
			return AlarmApplication.getApp().checkNetWork(true);
			
		}
}
