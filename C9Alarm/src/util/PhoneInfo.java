package util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

public class PhoneInfo {
	private static final String NETWORK_CDMA = "2G";
	private static final String NETWORK_EDGE = "2G";
	private static final String NETWORK_GPRS = "2G";
	private static final String NETWORK_UMTS = "3G";
	private static final String NETWORK_EVDO_0 = "3G";
	private static final String NETWORK_EVDO_A = "3G";
	private static final String NETWORK_EVDO_B = "3G";
	private static final String NETWORK_1X_RTT = "2G";
	private static final String NETWORK_HSDPA = "3G";
	private static final String NETWORK_HSUPA = "3G";
	private static final String NETWORK_HSPA = "3G";
	private static final String NETWORK_IDEN = "2G";
	private static final String NETWORK_UNKOWN = "Unknown";
	private static final String PRODUCT_NAME = "东方财富通";
	private static String Ip_client;
	public static void setIP(String ip){
		Ip_client  = ip;
	}
	
	public static String getInformationForNetInfo(Context c) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(getIMEI(c)).append("|");//
		sb.append(getPhoneNumber(c)).append("|");//
		sb.append(getManufacturer()).append("|");
		sb.append(getPhoneVer()).append("|");
		sb.append(getSystemVer()).append("|");
		sb.append(getClientName()).append("|");
		sb.append(getVerCode(c)).append("|");
		sb.append(getGpsInf(c)).append("|");
		sb.append(getIP()).append("|");
		sb.append(getNetWorkProvider(c)).append("|");//
		sb.append(getNetWorkType(c)).append("|");

		return sb.toString();
	}
	public static String getInformationForCutstonExcption(Context c){
		StringBuffer sb = new StringBuffer();
		sb.append(getIMEI(c)).append("|");
		sb.append(getPhoneNumber(c)).append("|");//
		sb.append(getManufacturer()).append("|");
		sb.append(getPhoneVer()).append("|");
		sb.append(getSystemVer()).append("|");
		sb.append(getClientName()).append("|");
		sb.append(getVerCode(c)).append("|");
		
		return sb.toString();
	}
	
	private static String getClientName() {
		// TODO Auto-generated method stub
		return PRODUCT_NAME;
	}

	public static String getVerCode(Context c){
		String versionname;//版本号
		try {
		PackageManager pm = c.getPackageManager();
		PackageInfo pi = pm.getPackageInfo(c.getPackageName(), 0);
		versionname = pi.versionName;//获取在AndroidManifest.xml中配置的版本号
		} catch (PackageManager.NameNotFoundException e) {
		versionname = "3";
		}
		return versionname;
	}

	public static String getNetWorkType(Context c) {
		if (isWifi(c) == true) {
			return "wifi";
		} else {
			TelephonyManager tm = (TelephonyManager) c
					.getSystemService(Context.TELEPHONY_SERVICE);
			return mapNetworkTypeToName(tm.getNetworkType());
		}
	}

	public static String getIMEI(Context c) {
		
			TelephonyManager tm = (TelephonyManager) c
					.getSystemService(Context.TELEPHONY_SERVICE);
			return tm.getDeviceId()+getSerId();
		
	}
	
	public static String getSerId(){
		String serialnum = null;                                                                                                                                        
		try {                                                           
		 Class<?> c = Class.forName("android.os.SystemProperties"); 
		 Method get = c.getMethod("get", String.class, String.class );     
		 serialnum = (String)(   get.invoke(c, "ro.serialno", "unknown" )  );   
		}                                                                                
		catch (Exception ignored)                                                        
		{                              
		}
		
		return serialnum;
	}
	
	private static String getNetWorkProvider(Context c) {
		return getProvidersName(c);
	}

	public static String getIP() {
		if(getIpByClient()!=null){
			return getIpByClient();
		}
		return getLocalIPAddress();
	}

	private static String getIpByClient() {
		return Ip_client;
	}

	
	private static String getGpsInf(Context c) {
		String locationXY = "|";//这个字段不需要传了，但是需要保留位置
		return locationXY;
	}

	private static String getSystemVer() {
		return getSdkVersion()+"";
	}

	public static String getPhoneVer() {
		return Build.DEVICE;
	}

	public static String getManufacturer() {
		String  manufacturer = "unknown";
		try {
			Class<?> c = Class.forName("android.os.Build");
			Field field = c.getField("MANUFACTURER");
			manufacturer = (String) field.get(c);
		} catch (Exception e) {
			
		}
		return manufacturer;
	}

	public static String getPhoneNumber(Context c) {
		return getNativePhoneNumber(c);
	}

	public static String getLocalIPAddress() {
		try {
			for (Enumeration<NetworkInterface> mEnumeration = NetworkInterface
					.getNetworkInterfaces(); mEnumeration.hasMoreElements();) {
				NetworkInterface intf = mEnumeration.nextElement();
				for (Enumeration<InetAddress> enumIPAddr = intf
						.getInetAddresses(); enumIPAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIPAddr.nextElement();
					// 如果不是回环地址
					if (!inetAddress.isLoopbackAddress()) {
						// 直接返回本地IP地址
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
		}
		return null;
	}

	/**
	 * 将网络类型值以字符串形式返回
	 * 
	 * @param networkType
	 * @return
	 */
	private static String mapNetworkTypeToName(int networkType) {
		switch (networkType) {
		case TelephonyManager.NETWORK_TYPE_CDMA:
			return NETWORK_CDMA;
		case TelephonyManager.NETWORK_TYPE_EDGE:
			return NETWORK_EDGE;
		case TelephonyManager.NETWORK_TYPE_GPRS:
			return NETWORK_GPRS;
		case TelephonyManager.NETWORK_TYPE_UMTS:
			return NETWORK_UMTS;
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			return NETWORK_EVDO_0;
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			return NETWORK_EVDO_A;
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
			return NETWORK_EVDO_B;
		case TelephonyManager.NETWORK_TYPE_1xRTT:
			return NETWORK_1X_RTT;
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			return NETWORK_HSDPA;
		case TelephonyManager.NETWORK_TYPE_HSPA:
			return NETWORK_HSPA;
		case TelephonyManager.NETWORK_TYPE_HSUPA:
			return NETWORK_HSUPA;
		case TelephonyManager.NETWORK_TYPE_IDEN:
			return NETWORK_IDEN;
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
		default:
			return NETWORK_UNKOWN;
		}
	}

	
	/**
	 * wifi是否开启
	 * 
	 * @param iContext
	 * @return
	 */
	public static boolean isWifi(Context c) {
		boolean WifiOn = false;

		WifiManager wm = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
		if (wm.isWifiEnabled()
				&& wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
			WifiOn = true;
		}

		return WifiOn;
	}
    /**
     * check is network avilable
     */
    public static  boolean checkNetWork(Context c) {
        ConnectivityManager cm = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for (int i = 0; i<networkInfos.length; i++) {
            if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
            	return true;
            }
        }
        return false;
    }
		/**
		 * Role:获取当前设置的电话号码 <BR>
		 * Date:2012-3-12 <BR>
		 */
		public static String getNativePhoneNumber(Context c) {
			String NativePhoneNumber = null;
			NativePhoneNumber =( (TelephonyManager) c
			.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
			if(NativePhoneNumber==null||NativePhoneNumber.equals("")||NativePhoneNumber.equals("null")){
				NativePhoneNumber = "";
			}
			
			return NativePhoneNumber;
		}

		/**
		 * Role:Telecom service providers获取手机服务商信息 <BR>
		 * 需要加入权限<uses-permission
		 * android:name="android.permission.READ_PHONE_STATE"/> <BR>
		 * Date:2012-3-12 <BR>
		 * 
		 */
		public static String getProvidersName(Context c) {
			String ProvidersName = null;
			// 返回唯一的用户ID;就是这张卡的编号神马的
			String IMSI = ( (TelephonyManager) c
					.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
			// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
			if(IMSI==null) return "-1";
			if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
				ProvidersName = "中国移动";
			} else if (IMSI.startsWith("46001")) {
				ProvidersName = "中国联通";
			} else if (IMSI.startsWith("46003")) {
				ProvidersName = "中国电信";
			}
			return ProvidersName;
	}
		
		@SuppressWarnings("deprecation")
		public static int getSdkVersion() {
			int sdk = 1;
			try {
				Class<?> c = Class.forName("android.os.Build$VERSION");
				Field field = c.getField("SDK_INT");
				sdk = (Integer) field.get(c);
			} catch (Exception e) {
				sdk = Integer.parseInt(android.os.Build.VERSION.SDK);
			}
			return sdk;
		}
}
