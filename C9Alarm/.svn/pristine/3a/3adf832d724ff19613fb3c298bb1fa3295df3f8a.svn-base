/**
 * 
 */
package com.tavx.C9Alarm.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.cloud.MyExclusionStrategy;

/**
 * @author Administrator
 *
 */
public class CacheManager {

	private static CacheManager mCacheManager;
	private static CacheManager getInstance(){
		if(mCacheManager==null) mCacheManager = new CacheManager();
		return mCacheManager;
	}
	HashMap<String,String>  mCache = new HashMap<String,String>();
	
	public static <T>void save(List<T> obj,String key){

	
		Gson g=  new GsonBuilder().setExclusionStrategies(new MyExclusionStrategy()).serializeNulls().create();
		String t=g.toJson(obj,new TypeToken<List<T>>(){}.getType());
		
		SharedPreferences.Editor e = AlarmApplication.getApp().getSharedPreferences("Tavx_chache", 0).edit();
		e.putString(key, t).commit();
		getInstance().mCache.put(key, t);
	}
	

	
	
	public static  List<Object> get(String key,Object o){
		if(getInstance().mCache.containsKey(key)){
			//MyLogger.e("aaa", "gggg"+getInstance().mCache.get(key));
			String t=getInstance().mCache.get(key);
			if(t==null) return new ArrayList<Object>();
			
			List<Object> l = new ArrayList<Object>();
			Gson g= new GsonBuilder().setExclusionStrategies(new MyExclusionStrategy()).serializeNulls().create();
			
			JsonParser parser = new JsonParser();
			 JsonArray array = parser.parse(t).getAsJsonArray();
			 for(int i=0;i<array.size();i++){
				 l.add(g.fromJson(array.get(i), o.getClass()));
			 }

			return l;
		}else{
			SharedPreferences e = AlarmApplication.getApp().getSharedPreferences("Tavx_chache", 0);
			String t=e.getString(key, null);
			if(t==null) return new ArrayList<Object>();
			
			List<Object> l = new ArrayList<Object>();
			Gson g= new GsonBuilder().setExclusionStrategies(new MyExclusionStrategy()).serializeNulls().create();
			
			JsonParser parser = new JsonParser();
			 JsonArray array = parser.parse(t).getAsJsonArray();
			 for(int i=0;i<array.size();i++){
				 l.add(g.fromJson(array.get(i), o.getClass()));
			 }
		//	l=g.fromJson(t,new TypeToken<List<Object>>(){}.getType());
//			

			return l;
		}
		
	}

}
