package weibo;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.tavx.C9Alarm.AlarmApplication;

public  class SinaWeiboOAuthActivity extends WeiboOAuthActivity {
	//public WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//setContentView(R.layout.sina_oauth_webview);

		super.onCreate(savedInstanceState);
		webview.loadUrl(WeiboConstant.SINA_OAUTH);
	}
	
	
	 public  void pageStarted(WebView view, String url, Bitmap favicon){
//		 Logger.e("onPageStarted", url + "开始加载界面。。。。。");
		if (url.startsWith("http://www.caifutong.com.cn/phone")) {
			// 取消授权后的界面
			view.cancelLongPress();
			view.stopLoading();

			// 获取Code
			Uri uri = Uri.parse(url);
			String code = uri.getQueryParameter("code");
//			Logger.e("code", WeiboConstant.SINA_ACCESS_TOKEN + code);

			String result = "";
			if (code != null) {
				result = HttpsUtil.HttpsPost(
						WeiboConstant.SINA_ACCESS_TOKEN + code, "");
//				Logger.e("Https地址", WeiboConstant.SINA_ACCESS_TOKEN + code);
//				Logger.e("登录请求结果", result);
				System.out.println("result--------->" + result);
			}
			if(result!=null){
				if (result.startsWith("{\"access_token\":")) {
					int i = result.indexOf(":");
					int j = result.indexOf(",");
					String token = result.substring(i + 2,
							j - 1);
					
					String uid= "\"uid\":\"";
					String nameResult = HttpsUtil.HttpsPost( WeiboConstant.getWeiboNameSina(getParam(uid,result,"\""),token),null);
					String name = getParam("\"screen_name\":\"",nameResult,"\"");	
//				Logger.e("name+token", name+" + "+token);
					
					AlarmApplication.getApp().weiboUser.bindSinaWeibo(this,token, 0,name); 
					
					finish();
					Toast.makeText(SinaWeiboOAuthActivity.this, "绑定成功",
							Toast.LENGTH_LONG).show();
				}
			}else{
				Toast.makeText(SinaWeiboOAuthActivity.this, "绑定失败请再试一次",
						Toast.LENGTH_LONG).show();
			}
		}
		}
	
}
