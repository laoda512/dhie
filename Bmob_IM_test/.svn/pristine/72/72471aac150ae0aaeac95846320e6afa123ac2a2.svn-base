package com.bmob.im.demo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.Log;
import cn.bmob.im.BmobChat;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.db.BmobDB;

import com.baidu.mapapi.SDKInitializer;
import com.bmob.im.demo.util.CollectionUtils;
import com.bmob.im.demo.util.SharePreferenceUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tavx.C9Alam.connector.AlarmConnector;

/**
 * 自定义全局Applcation类
 * 
 * @ClassName: CustomApplcation
 * @Description: TODO
 * @author smile
 * @date 2014-5-19 下午3:25:00
 */
public class CustomApplcation extends ContextWrapper {

	public CustomApplcation(Context base) {
		super(base);
		BmobChat.DEBUG_MODE = true;
		init();
	}

	private static CustomApplcation mInstance;

//	@Override
//	public void onCreate() {
//		// TODO Auto-generated method stub
//		super.onCreate();
//		// 是否开启debug模式--默认开启状态
//		
//	}

	private void init() {
		mMediaPlayer = MediaPlayer.create(this, R.raw.notify);
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		initImageLoader(getApplicationContext());
		// 若用户登陆过，则先从数据库中取出来存入内存中
		if (BmobUserManager.getInstance(getApplicationContext())
				.getCurrentUser() != null) {
			// 获取本地好友user list到内存,方便以后获取好友list
			contactList = CollectionUtils.list2map(BmobDB.create(
					getApplicationContext()).getContactList());
		}
		
	//	initBaidumapSdk();
	}

	/**初始化百度地图sdk
	  * initBaidumap
	  * @Title: initBaidumap
	  * @Description: TODO
	  * @param  
	  * @return void
	  * @throws
	  */
	private void initBaidumapSdk(){
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
	}
	/** 初始化ImageLoader */
	public static void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"bmobim/Cache");// 获取到缓存的目录地址
		Log.d("cacheDir", cacheDir.getPath());
		// 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		// 线程池内加载的数量
				.threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
				// .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);// 全局初始化此配置
	}

	public static CustomApplcation getInstance() {
		if(mInstance==null) mInstance = new CustomApplcation(AlarmConnector.getApplication());
		return mInstance;
	}

	// 单例模式，才能及时返回数据
	SharePreferenceUtil mSpUtil;
	public static final String PREFERENCE_NAME = "_sharedinfo";

	public synchronized SharePreferenceUtil getSpUtil() {
		if (mSpUtil == null) {
			String currentId = BmobUserManager.getInstance(getApplicationContext()).getCurrentUserObjectId();
			String sharedName = currentId + PREFERENCE_NAME;
			Log.i("BmobChat", "首选项文件名：" + sharedName);
			mSpUtil = new SharePreferenceUtil(this, sharedName);
		}
		return mSpUtil;
	}

	NotificationManager mNotificationManager;

	public NotificationManager getNotificationManager() {
		if (mNotificationManager == null)
			mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		return mNotificationManager;
	}

	MediaPlayer mMediaPlayer;

	public synchronized MediaPlayer getMediaPlayer() {
		if (mMediaPlayer == null)
			mMediaPlayer = MediaPlayer.create(this, R.raw.notify);
		return mMediaPlayer;
	}

	// login password
	private static final String PREF_NICK = "nick";
	private String nick = null;

	private static final String PREF_SEX = "sex";
	private boolean sex;

	// login user avatar
	public final String PREF_USERAVATAR = "avatar";
	private String avatar = null;

	/**
	 * 获取用户头像
	 * 
	 * @return
	 */
	public String getUserAvatar() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		avatar = preferences.getString(PREF_USERAVATAR, null);
		return avatar;
	}

	/**
	 * 设置用户头像
	 * 
	 * @param user
	 */
	public void setUserAvatar(String head) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		if (editor.putString(PREF_USERAVATAR, head).commit()) {
			avatar = head;
		}
	}

	private Map<String, BmobChatUser> contactList = new HashMap<String, BmobChatUser>();

	/**
	 * 获取内存中好友user list
	 * 
	 * @return
	 */
	public Map<String, BmobChatUser> getContactList() {
		return contactList;
	}

	/**
	 * 设置好友user list到内存中
	 * 
	 * @param contactList
	 */
	public void setContactList(Map<String, BmobChatUser> contactList) {
		if (this.contactList != null) {
			this.contactList.clear();
		}
		this.contactList = contactList;
	}

	/**
	 * 获取昵称
	 * 
	 * @return
	 */
	public boolean getSex() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		sex = preferences.getBoolean(PREF_SEX, true);
		return sex;
	}

	/**
	 * 
	 * @param pwd
	 */
	public void setSex(boolean gender) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		if (editor.putBoolean(PREF_SEX, true).commit()) {
			sex = gender;
		}
	}

	/**
	 * 获取昵称
	 * 
	 * @return
	 */
	public String getNick() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		nick = preferences.getString(PREF_NICK, null);
		return nick;
	}

	/**
	 * @param pwd
	 */
	public void setNick(String pwd) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		if (editor.putString(PREF_NICK, pwd).commit()) {
			nick = pwd;
		}
	}

	/**
	 * 退出登录,清空缓存数据
	 */
	public void logout() {
		BmobUserManager.getInstance(getApplicationContext()).logout();
		setNick(null);
		setUserAvatar(null);
		setContactList(null);
	}

}
