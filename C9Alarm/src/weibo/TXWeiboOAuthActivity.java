package weibo;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.tavx.C9Alarm.AlarmApplication;


public  class TXWeiboOAuthActivity extends WeiboOAuthActivity {
	//public WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		webview.loadUrl(WeiboConstant.TX_OAUTH);
	}
	

	
	 public  void pageStarted(WebView view, String url, Bitmap favicon){
//			Logger.e("onPageStarted", url + "开始加载界面。。。。。");
			if (url.startsWith("http://www.caifutong.com.cn/phone")) {
				// 取消授权后的界面
				view.cancelLongPress();
				view.stopLoading();
				
				// 获取Code
				Uri uri = Uri.parse(url);
				String code = uri.getQueryParameter("code");
				String openid = uri.getQueryParameter("openid");
				String openkey = uri.getQueryParameter("openkey");
				
//				Logger.e("code", WeiboConstant.TX_ACCESS_TOKEN + code);

				String result = "";
				if (code != null) {
					result = HttpsUtil.HttpsPost(
							WeiboConstant.TX_ACCESS_TOKEN + code, "");
//					Logger.e("Https地址", WeiboConstant.TX_ACCESS_TOKEN + code);
//					Logger.e("登录请求结果", result);
				}
				if(result!=null){
				if (result.startsWith("access_token=")) {
					int i = result.indexOf("access_token=");
					result= result.substring(i+"access_token=".length());
					i = result.indexOf("&");
					String token  = result.substring(0,i);
					
					
					i = result.indexOf("refresh_token=");
					result= result.substring(i);
					i = result.indexOf("&");
					String retoken  = result.substring(0,i);
					
					String nameResult = HttpsUtil.HttpsPost(
							WeiboConstant.getWeiboNameTx(token, openid, "111.111.111.111"), null);
					
//					Logger.e("nameResult",nameResult);
					//MyApp.getMyApp().weiboUser.bindTXWeibo(token, retoken,openid,openkey, 0);
					String name = getParam("nick\":\"", nameResult, "\"");
					
//					Logger.e("name的值", name);
//					Logger.e("ACCESS_TOKEN的值", token);
//					Logger.e("Refresh_TOKEN的值", retoken);
					AlarmApplication.getApp().weiboUser.bindTXWeibo(this,token, retoken,openid,openkey, 0,name);
					finish();
					Toast.makeText(TXWeiboOAuthActivity.this, "绑定成功",
							Toast.LENGTH_LONG).show();
				}
				}else{
					Toast.makeText(TXWeiboOAuthActivity.this, "绑定失败请再试一次",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	
}
