package weibo;

import java.net.URLEncoder;

import android.content.Context;
import android.content.SharedPreferences;

import com.tavx.C9Alarm.AlarmApplication;


public class WeiboConstant {
	
	public static String SINA_CONSUMER_KEY = "803249671";
	public static String SINA_CONSUMER_SECRET = "23078e4c2bb6d2b0eb411478d7f1b5f5";

	public static String SINA_OAUTH = "https://api.weibo.com/oauth2/authorize?client_id="
			+ SINA_CONSUMER_KEY
			+ "&response_type=code&redirect_uri=http://www.caifutong.com.cn/phone"
			+ "&display=mobile";
 
	public static String SINA_ACCESS_TOKEN = "https://api.weibo.com/oauth2/access_token?client_id="
			+ SINA_CONSUMER_KEY
			+ "&client_secret="
			+ SINA_CONSUMER_SECRET
			+ "&grant_type=authorization_code&redirect_uri=http://www.caifutong.com.cn/phone&code=";
	
	public static String ACCESS_TOKEN = "";
	public static String Refresh_TOKEN = "";
	
	private static final String TX_URL = "http://www.caifutong.com.cn/phone";
	public static String TX_CONSUMER_KEY = "801204574";
	public static String TX_CONSUMER_SECRET = "82ccdafeb809a1832a56eb10fa703030";
	public static String TX_OAUTH="https://open.t.qq.com/cgi-bin/oauth2/authorize?client_id="+TX_CONSUMER_KEY+"&response_type=code&redirect_uri="+TX_URL+"&wap=2";
	public static String TX_ACCESS_TOKEN="https://open.t.qq.com/cgi-bin/oauth2/access_token?client_id="+TX_CONSUMER_KEY+"&client_secret="+TX_CONSUMER_SECRET+"&redirect_uri="+TX_URL+"&grant_type=authorization_code&code=";
	
	public static String getWeiboNameSina(String uid,String token){
		return  "https://api.weibo.com/2/users/show.json?access_token="+ token + "&uid="+ uid;
	}
	
	public static String getWeiboNameTx(String access_token,String openid,String ip){
		return "http://open.t.qq.com/api/user/info?format=json&oauth_consumer_key="+TX_CONSUMER_KEY+"&access_token="+access_token+"&openid="+openid+"&clientip="+ip+"&oauth_version=2.a&scope=all";
	}
	
	public static String sendWeiboDataTx(String content){
		return  "format=json&content="+URLEncoder.encode(content.trim())+"&clientip=0&jing=0&wei=0&syncflag=0";
	}

	public static String sendWeiboUriTx(String access_token,String openid,String ip){
		return "https://open.t.qq.com/api/t/add?oauth_consumer_key="+TX_CONSUMER_KEY+"&access_token="+access_token+"&openid="+openid+"&clientip="+ip+"&oauth_version=2.a&scope=all";
	}
	
	public static String sendWeiboDataSina(String access_token,String content){
		return "format=json&access_token="
		+ access_token + "&status="
		+ URLEncoder.encode(content.trim()); 
	}
	
	public static String sendWeiboUriSina(){
		return "https://api.weibo.com/2/statuses/update.json?";
	}
	
	private  static final String data_sinaweibo = "sina_weibo_data";
	private  static final String data_txweibo = "tx_weibo_data";
	
	public String sina_token="null";
	public String sina_name="null";
	public String tx_token="null";
	public String tx_name="null";
	public String tx_refresh_token="null";
	public String tx_openid="null";
	public String tx_openkey="null";
	
	public WeiboConstant(Context c){
		 getSinaBind(c);
		 getTxBind(c);
	}
	
	public  void bindSinaWeibo(Context c,String token,int time,String name){
		sina_token = token;
		sina_name = name;
		SharedPreferences.Editor sharedata = c.getSharedPreferences(data_sinaweibo, 0).edit();  
		sharedata.putString("token",sina_token);  
		sharedata.putString("name",sina_name);  
		sharedata.commit();
//		MyApp.getMyApp().weiboUser.sina_name=sina_name;
//		MyApp.getMyApp().weiboUser.sina_token=sina_token;
	}
	
	public  void bindTXWeibo(Context c,String token,int time,String name){
		tx_token = token;
		tx_name = name;
		SharedPreferences.Editor sharedata = c.getSharedPreferences(data_txweibo, 0).edit();  
		sharedata.putString("token",tx_token);  
		sharedata.putString("name",tx_name);  
		sharedata.commit();
//		MyApp.getMyApp().weiboUser.tx_name=tx_name;
//		MyApp.getMyApp().weiboUser.tx_token=tx_token;
	}
	
	public  void bindTXWeibo(Context c,String token,String refreshToken,String openid,String openkey,int time,String name){
		tx_token = token;
		tx_refresh_token = refreshToken;
		tx_openid=openid;
		tx_openkey=openkey;
		tx_name = name;
		SharedPreferences.Editor sharedata = c.getSharedPreferences(data_txweibo, 0).edit();  
		sharedata.putString("token",tx_token);  
		sharedata.putString("refreshtoken",tx_refresh_token); 
		sharedata.putString("openid",tx_openid);  
		sharedata.putString("openkey",tx_openkey); 
		sharedata.putString("name",tx_name);  
		sharedata.commit();
	}
	
	public  void getSinaBind(Context c){
		SharedPreferences sharedata = c.getSharedPreferences(data_sinaweibo, 0);  
		sina_token = sharedata.getString("token","null");
		sina_name = sharedata.getString("name","null");
	}
	
	public  void getTxBind(Context c){
		SharedPreferences sharedata = c.getSharedPreferences(data_txweibo, 0);  
		tx_token=sharedata.getString("token","null");
		tx_refresh_token=sharedata.getString("refreshtoken","null");
		tx_openid= sharedata.getString("openid","null");
		tx_openkey= sharedata.getString("openkey","null");
		tx_name = sharedata.getString("name","null");
		
	}
	
	public boolean isSinaBind(){
		if(sina_token.equals("null")){
			return false;
		}
		return true;
	}
	
	public boolean isTxBind(){
		if(tx_token.equals("null")){
			return false;
		}
		return true;
	}
	
	public void removeBindSina(Context c){
		bindSinaWeibo(c,"null",0,"null");
	}
	
	public void removeBindTX(Context c){
		bindTXWeibo(c,"null",0,"null");
	}
	
	public void sendSinaWeibo(Context c,String content){
		String data = WeiboConstant.sendWeiboDataSina(AlarmApplication.getApp().weiboUser.sina_token, content);//"format=json&content="+URLEncoder.encode(value.trim())+"&clientip=0&jing=0&wei=0&syncflag=0";
		String url=WeiboConstant.sendWeiboUriSina();
		HttpsUtil.HttpsPost(url, data);
	}
	
	public void sendTXWeibo(Context c,String content){
		String data = WeiboConstant.sendWeiboDataTx(content);//"format=json&content="+URLEncoder.encode(value.trim())+"&clientip=0&jing=0&wei=0&syncflag=0";
		String url=WeiboConstant.sendWeiboUriTx(AlarmApplication.getApp().weiboUser.tx_token,AlarmApplication.getApp().weiboUser.tx_openid,"133.1.1.1");
		HttpsUtil.HttpsPost(url, data);
	}
	
}
