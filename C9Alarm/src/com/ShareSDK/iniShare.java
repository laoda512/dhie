package com.ShareSDK;

import java.util.HashMap;

import android.content.Context;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class iniShare {
	static boolean hasIni=false;
	public static void iniShare(Context c){
		ShareSDK.initSDK(c,"1ed37a7b3d2e");
		//if(!hasIni){
			hasIni = true;
		 HashMap<String,Object> hashMap = new HashMap<String, Object>();
		    hashMap.put("Id","1");
		    hashMap.put("SortId","1");
		     hashMap.put("AppKey","1043366677");
		    hashMap.put("AppSecret","643a5cf19ab123e8a5013987dcb970c0");
		    hashMap.put("RedirectUrl","www.projectalarmx.com");
		    hashMap.put("ShareByAppClient","false");
		    hashMap.put("Enable","true");
		    ShareSDK.setPlatformDevInfo(SinaWeibo.NAME,hashMap);
		    
			 hashMap = new HashMap<String, Object>();
			    hashMap.put("Id","2");
			    hashMap.put("SortId","2");
			     hashMap.put("AppKey","1101372141");
			    hashMap.put("AppSecret","KQYGC9aAKJ7KyeeW");
			    hashMap.put("RedirectUrl","www.projectalarmx.com");
			    hashMap.put("ShareByAppClient","false");
			    hashMap.put("Enable","true");
			    ShareSDK.setPlatformDevInfo(TencentWeibo.NAME,hashMap);
			    
			    hashMap = new HashMap<String, Object>();
			    hashMap.put("Id","3");
			    hashMap.put("SortId","3");
			     hashMap.put("AppKey","101107983");
			    hashMap.put("AppSecret","124e183eeb5c3cd1d21befdac9591fb1");
			    hashMap.put("RedirectUrl","www.projectalarmx.com");
			    hashMap.put("ShareByAppClient","false");
			    hashMap.put("Enable","true");
			    ShareSDK.setPlatformDevInfo(QZone.NAME,hashMap);
			    
		
			    
			    hashMap = new HashMap<String, Object>();
			    hashMap.put("Id","4");
			    hashMap.put("SortId","4");
			     hashMap.put("AppId","wxb3239812c10799c5");
			    hashMap.put("BypassApproval","false");
			    hashMap.put("Enable","true");
			    ShareSDK.setPlatformDevInfo(Wechat.NAME,hashMap);
			    
			    hashMap = new HashMap<String, Object>();
			    hashMap.put("Id","5");
			    hashMap.put("SortId","5");
			     hashMap.put("AppId","wxb3239812c10799c5");
			    hashMap.put("BypassApproval","false");
			    hashMap.put("Enable","true");
			    ShareSDK.setPlatformDevInfo(WechatMoments.NAME,hashMap);
			    
			    hashMap = new HashMap<String, Object>();
			    hashMap.put("Id","6");
			    hashMap.put("SortId","6");
			     hashMap.put("AppId","wxb3239812c10799c5");
			    hashMap.put("BypassApproval","false");
			    hashMap.put("Enable","true");
			    ShareSDK.setPlatformDevInfo(WechatFavorite.NAME,hashMap);
		    
		//}
	}

}
