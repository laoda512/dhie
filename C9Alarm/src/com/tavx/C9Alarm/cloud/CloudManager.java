/**
 * 
 */
package com.tavx.C9Alarm.cloud;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.trinea.android.common.entity.FailedReason;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.service.impl.ImageMemoryCache.OnImageCallbackListener;
import cn.trinea.android.common.util.ObjectUtils;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tavx.C9Alam.connector.responseBean;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.Manager.CacheManager;
import com.tavx.C9Alarm.cloud.Bean.Bmob.QA;
import com.tavx.C9Alarm.cloud.Bean.Bmob.Vote_form_point;
import com.tavx.C9Alarm.cloud.Bean.Bmob.form_point;
import com.tavx.C9Alarm.cloud.Bean.Bmob.functionLock;
import com.tavx.C9Alarm.cloud.Bean.Bmob.title_hint;

/**
 * @author Administrator
 * 
 */
public class CloudManager {
	public static final int success = 0;
	public static final int failed = 1;
	
	
	public static void setBitmap(Object file, ImageView mView) {
		checkNetWork();

		if (file instanceof BmobFile) {
			BmobFile pic = (BmobFile) file;
			if (pic != null) {
				
				mView.setTag(pic.getFileUrl());
				if (!AlarmApplication.getImageCache().get(pic.getFileUrl(), mView)) {
					mView.setImageDrawable(null);
			    }
				
				AlarmApplication.getImageCache().get(pic.getFileUrl(), mView);
			

			}
		} else if (file instanceof AVFile) {
			AVFile pic = (AVFile) file;
			if (pic != null) {
				AlarmApplication.getImageCache().get(pic.getThumbnailUrl(true,
						(int) (120 * AlarmApplication.getApp().getFitRateX()),
						(int) (120 * AlarmApplication.getApp().getFitRateX())),
						mView);
			}
		}

	}
	public static void recyleImageCache(){
		AlarmApplication.getImageCache().saveDataToDb(AlarmApplication.getApp(),
				"tavx");
	}
	
	public static void iniImageCache(){
		AlarmApplication.getImageCache().initData(AlarmApplication.getApp(), "tavx");
	}
	public static void removemageCacheListener(){
		AlarmApplication.getImageCache().setOnImageCallbackListener(null);
	}
	public static void IniImageCacheListener(){
		AlarmApplication.getImageCache().setOnImageCallbackListener(new OnImageCallbackListener() {
			
			@Override
			public void onPreGet(String imageUrl, View view) {
				
			}
			
			@Override
			public void onGetSuccess(String imageUrl, Bitmap loadedImage, View view,
					boolean isInCache) {
				   if (view != null && loadedImage != null) {
				        ImageView imageView = (ImageView)view;
				        String imageUrlTag = (String)imageView.getTag();
				        if (ObjectUtils.isEquals(imageUrlTag, imageUrl)) {
				            imageView.setImageBitmap(loadedImage);
				        }
				    }
				
			}
			
			@Override
			public void onGetNotInCache(String imageUrl, View view) {
				
			}
			
			@Override
			public void onGetFailed(String imageUrl, Bitmap loadedImage, View view,
					FailedReason failedReason) {
				
			}
		});
	}

	public static String getBitmapPath(Object file, ImageView mView,
			Handler imageGetHandler) {
		checkNetWork();

		if (file instanceof BmobFile) {
			BmobFile pic = (BmobFile) file;
			if (pic != null) {

				String path = AlarmApplication.getImageCache().getImagePath(pic
						.getFileUrl());
				return path;

			}
		} else if (file instanceof AVFile) {
			AVFile pic = (AVFile) file;
			if (pic != null) {
				AlarmApplication.getImageCache().get(pic.getThumbnailUrl(true,
						(int) (120 * AlarmApplication.getApp().getFitRateX()),
						(int) (120 * AlarmApplication.getApp().getFitRateX())),
						mView);
			}

		}
		return null;
	}

	/**
	 * 
	 */
	private static boolean checkNetWork() {
		return AlarmApplication.getApp().checkNetWork(true);

	}

	public static String getFileUrl(Object o) {
		if (o instanceof BmobFile) {
			BmobFile b = (BmobFile) o;
			return b.getFileUrl();
		} else if (o instanceof AVFile) {
			AVFile av = (AVFile) o;
			return av.getUrl();
		}
		return "";
	}

	public static <T> void queryFromPoint(Context c, final Handler mHandler,
			String... parm) {
		Message msg = new Message();
		msg.what = success;
		List l = CacheManager.get("queryFromPoint", new form_point());
		final boolean needRefresh;
		if (l.size() == 0)
			needRefresh = true;
		else {
			needRefresh = false;
		}
		msg.obj = l;
		mHandler.dispatchMessage(msg);

		if (checkNetWork() == false) {
			return;
		}

		BmobQuery<form_point> mBmobQuery = new BmobQuery<form_point>();
		mBmobQuery.setCachePolicy(CachePolicy.NETWORK_ONLY);
		for (String item : parm) {
			mBmobQuery.include(item);
		}
		mBmobQuery.setLimit(999);
		mBmobQuery.order("-point");

		mBmobQuery.findObjects(c, new FindListener<form_point>() {
			@Override
			public void onSuccess(List<form_point> arg0) {
				if (needRefresh) {
					Message msg = new Message();
					msg.what = success;
					msg.obj = arg0;
					mHandler.dispatchMessage(msg);
				}
				CacheManager.save(arg0, "queryFromPoint");
			}

			@Override
			public void onError(int arg0, String arg1) {
				Message msg = new Message();
				msg.what = failed;
				msg.obj = arg1;
				 mHandler.dispatchMessage(msg);

			}
		});

		/*
		 * AVQuery<AVObject> query = AVQuery.getQuery("form_point");
		 * query.setCachePolicy(AVQuery.CachePolicy.CACHE_THEN_NETWORK);
		 * query.include("jifen"); query.orderByDescending("point");
		 * 
		 * query.findInBackground(new FindCallback<AVObject>() {
		 * 
		 * @Override public void done(List<AVObject> arg0, AVException arg1) {
		 * List<RemoteFormData> buff_list = new ArrayList<RemoteFormData>(); if
		 * (arg0 == null) return;
		 * 
		 * for (AVObject item : arg0) { RemoteFormData mRemoteFormData =
		 * item.getAVObject("jifen"); buff_list.add(mRemoteFormData); //
		 * mRemoteFormData.point=""+item.getInt("point");
		 * mRemoteFormData.form_point = item; // new
		 * PointManager().getPoint(item); }
		 * 
		 * mDownloadListAdapter = new DownloadListAdapter(buff_list,
		 * getActivity(), DownLoadsFragment2.this);
		 * 
		 * mlView.setAdapter(mDownloadListAdapter);
		 * //handleDownloadsChanged(buff_list); } });
		 */
	}

	public static void queryQAList(Context c, final Handler mHandler,
			String... parm) {
		Message msg = new Message();
		msg.what = success;
		List l = CacheManager.get("queryQAList", new QA());
		final boolean needRefresh;
		if (l.size() == 0)
			needRefresh = true;
		else {
			needRefresh = false;
		}
		msg.obj = l;
		mHandler.dispatchMessage(msg);

		if (checkNetWork() == false) {
			return;
		}

		BmobQuery<QA> mBmobQuery = new BmobQuery<QA>();
		mBmobQuery.setCachePolicy(CachePolicy.NETWORK_ONLY);
		for (String item : parm) {
			mBmobQuery.include(item);
		}
		mBmobQuery.setLimit(999);
		mBmobQuery.order("-level");
		mBmobQuery.addWhereEqualTo("isShow", true);
		mBmobQuery.findObjects(c, new FindListener<QA>() {
			@Override
			public void onSuccess(List<QA> arg0) {
				if (needRefresh) {
					Message msg = new Message();
					msg.what = success;
					msg.obj = arg0;
					mHandler.dispatchMessage(msg);
				}
				CacheManager.save(arg0, "queryQAList");
			}

			@Override
			public void onError(int arg0, String arg1) {
				Message msg = new Message();
				msg.what = failed;
				msg.obj = arg1;
				mHandler.dispatchMessage(msg);
			}
		});

	}

	public static void queryVotePoint(Context c, final Handler mHandler,
			String... parm) {
		Message msg = new Message();
		msg.what = success;
		List l = CacheManager.get("queryVotePoint", new Vote_form_point());
		final boolean needRefresh;
		if (l.size() == 0)
			needRefresh = true;
		else {
			needRefresh = false;
		}
		msg.obj = l;
		mHandler.dispatchMessage(msg);

		if (checkNetWork() == false) {
			return;
		}

		BmobQuery<Vote_form_point> mBmobQuery = new BmobQuery<Vote_form_point>();
		mBmobQuery.setCachePolicy(CachePolicy.NETWORK_ONLY);
		for (String item : parm) {
			mBmobQuery.include(item);
		}
		mBmobQuery.setLimit(999);
		mBmobQuery.order("-point");
		mBmobQuery.addWhereEqualTo("isShow", true);
		mBmobQuery.findObjects(c, new FindListener<Vote_form_point>() {
			@Override
			public void onSuccess(List<Vote_form_point> arg0) {
				if (needRefresh) {
					Message msg = new Message();
					msg.what = success;
					msg.obj = arg0;
					mHandler.dispatchMessage(msg);
				}
				CacheManager.save(arg0, "queryVotePoint");
			}

			@Override
			public void onError(int arg0, String arg1) {
				Message msg = new Message();
				msg.what = failed;
				msg.obj = arg1;
				 mHandler.dispatchMessage(msg);

			}
		});

		/*
		 * AVQuery<AVObject> query = AVQuery.getQuery("form_point");
		 * query.setCachePolicy(AVQuery.CachePolicy.CACHE_THEN_NETWORK);
		 * query.include("jifen"); query.orderByDescending("point");
		 * 
		 * query.findInBackground(new FindCallback<AVObject>() {
		 * 
		 * @Override public void done(List<AVObject> arg0, AVException arg1) {
		 * List<RemoteFormData> buff_list = new ArrayList<RemoteFormData>(); if
		 * (arg0 == null) return;
		 * 
		 * for (AVObject item : arg0) { RemoteFormData mRemoteFormData =
		 * item.getAVObject("jifen"); buff_list.add(mRemoteFormData); //
		 * mRemoteFormData.point=""+item.getInt("point");
		 * mRemoteFormData.form_point = item; // new
		 * PointManager().getPoint(item); }
		 * 
		 * mDownloadListAdapter = new DownloadListAdapter(buff_list,
		 * getActivity(), DownLoadsFragment2.this);
		 * 
		 * mlView.setAdapter(mDownloadListAdapter);
		 * //handleDownloadsChanged(buff_list); } });
		 */
	}
	
	
	public static void login(String username,String password,String nickName){
		
	}
	
	public static void queryLock(Context c) {
		
		
		
		List l = CacheManager.get("queryLock", new functionLock());
		if(l!=null&&l.size()!=0){
			setLock((functionLock) l.get(0));
		}
		
		
	//	mHandler.dispatchMessage(msg);

		if (checkNetWork() == false) {
			return;
		}

		BmobQuery<functionLock> mBmobQuery = new BmobQuery<functionLock>();
		mBmobQuery.setCachePolicy(CachePolicy.NETWORK_ONLY);
		mBmobQuery.addWhereContains("platform", "default");
		mBmobQuery.setLimit(999);
		mBmobQuery.findObjects(c, new FindListener<functionLock>() {
			@Override
			public void onSuccess(List<functionLock> arg0) {
//				if (needRefresh) {
//					Message msg = new Message();
//					msg.what = success;
//					msg.obj = arg0;
//					mHandler.dispatchMessage(msg);
//				}
				if(arg0!=null&&arg0.size()!=0){
					setLock(arg0.get(0));
				}
				
				CacheManager.save(arg0, "queryLock");
			}

			@Override
			public void onError(int arg0, String arg1) {
//				Message msg = new Message();
//				msg.what = failed;
//				msg.obj = arg1;
//				mHandler.dispatchMessage(msg);

			}
		});
	}
	
	private static void setLock(functionLock f){
		AlarmApplication.getApp().mFunctionLock = f;
	}

	public static void queryHint(Context c, final Handler mHandler) {
		Message msg = new Message();
		msg.what = success;
		List l = CacheManager.get("queryHint", new title_hint());
		final boolean needRefresh;
		if (l.size() == 0)
			needRefresh = true;
		else {
			needRefresh = false;
		}
		msg.obj = l;
		mHandler.dispatchMessage(msg);

		if (checkNetWork() == false) {
			return;
		}

		BmobQuery<title_hint> mBmobQuery = new BmobQuery<title_hint>();
		mBmobQuery.setCachePolicy(CachePolicy.NETWORK_ONLY);

		mBmobQuery.setLimit(999);
		mBmobQuery.addWhereEqualTo("isShow", true);
		mBmobQuery.addWhereLessThanOrEqualTo("up_to_ver", 2);
		mBmobQuery.findObjects(c, new FindListener<title_hint>() {
			@Override
			public void onSuccess(List<title_hint> arg0) {
				if (needRefresh) {
					Message msg = new Message();
					msg.what = success;
					msg.obj = arg0;
					mHandler.dispatchMessage(msg);
				}
				CacheManager.save(arg0, "queryHint");
			}

			@Override
			public void onError(int arg0, String arg1) {
				Message msg = new Message();
				msg.what = failed;
				msg.obj = arg1;
				mHandler.dispatchMessage(msg);

			}
		});
	}

	public static void reset(Context c) {
		BmobQuery.clearAllCachedResults(c);
	}

	public static void copy(AVQuery<AVObject> av, BmobFile bm) {

	}
	
	public static responseBean parseResponseMsg(String msg){
		Gson g= new GsonBuilder().create();
		responseBean r = g.fromJson(msg,new TypeToken<responseBean>(){}.getType());
		return r;
	}

}
