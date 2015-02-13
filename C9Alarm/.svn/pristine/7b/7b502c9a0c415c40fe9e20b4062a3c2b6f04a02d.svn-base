package weibo;


import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tavx.C9Alarm.R;

public abstract class WeiboOAuthActivity extends Activity{
	public WebView webview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sina_oauth_webview);
		
		if(!isHigherToSDKVersion(14)){
			CookieManager.getInstance().setAcceptCookie(false);
		}
		
		webview = (WebView) this.findViewById(R.id.oauth_webview);
		webview.getSettings().setJavaScriptEnabled(true);
		 
		webview.setFocusable(true);
		
		
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				
				super.onPageFinished(view, url); 
			}
			
			@Override
			 public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
				if(isHigherToSDKVersion(8)){
					 handler.proceed() ;
				}else{
					super.onReceivedSslError(view, handler, error);
				}
				
				 }
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				webview.loadUrl(url);
				return super.shouldOverrideUrlLoading(view,url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				WeiboOAuthActivity.this.pageStarted( view,  url,  favicon);
				super.onPageStarted(view, url, favicon);
			}
	
		});
	}
	public static boolean isHigherToSDKVersion(int ver){
		if(Integer.parseInt(android.os.Build.VERSION.SDK)<ver){
			return true;
		}
		return false;
	}
	
	
	 protected String getParam(String name,String content,String end){
		 int  i = content.indexOf(name)+name.length();
		 int j = i+content.substring(i).indexOf(end);
		 return content.substring(i, j);
	 }
	
	public abstract void pageStarted(WebView view, String url, Bitmap favicon);
	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sina_oauth_webview);

		webview = (WebView) this.findViewById(R.id.oauth_webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setFocusable(true);
		webview.loadUrl(WeiboConstant.TX_OAUTH);

		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				Logger.i("onPageFinished", url + "网页加载完毕");
				super.onPageFinished(view, url);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Logger.i("shouldOverrideUrlLoading", url);
				webview.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override 
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Logger.e("onPageStarted", url + "开始加载界面。。。。。");
				if (url.startsWith("http://www.baidu.com")) {
					// 取消授权后的界面
					view.cancelLongPress();
					view.stopLoading();

					// 获取Code
					Uri uri = Uri.parse(url);
					String code = uri.getQueryParameter("code");
					Logger.e("code", WeiboConstant.TX_ACCESS_TOKEN + code);

					String result = "";
					if (code != null) {
						result = HttpsUtil.HttpsPost(
								WeiboConstant.TX_ACCESS_TOKEN + code, "");
						Logger.e("Https地址", WeiboConstant.TX_ACCESS_TOKEN + code);
						Logger.e("登录请求结果", result);
					}
					if (result.startsWith("access_token=")) {
						int i = result.indexOf("access_token=");
						result= result.substring(i);
						i = result.indexOf("&");
						WeiboConstant.ACCESS_TOKEN  = result.substring(0,i);
						
						i = result.indexOf("refresh_token=");
						result= result.substring(i);
						i = result.indexOf("&");
						WeiboConstant.Refresh_TOKEN  = result.substring(0,i);
						
						Logger.e("ACCESS_TOKEN的值", WeiboConstant.ACCESS_TOKEN);
						Logger.e("Refresh_TOKEN的值", WeiboConstant.Refresh_TOKEN);
						finish();
						Toast.makeText(SinaOAuthActivity.this, "登录成功",
								Toast.LENGTH_LONG).show();
					}
				}
				super.onPageStarted(view, url, favicon);
			}
		});
	}*/
}
