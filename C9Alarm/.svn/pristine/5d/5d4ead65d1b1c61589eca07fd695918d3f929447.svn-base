package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import util.zip.Decompress;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.util.Log;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.R;
import com.tavx.C9Alarm.Form.Form;

public class SDResReadManager {
	static SDResReadManager mSDResReadManager;
	public static final String path_root =Environment.getExternalStorageDirectory().getPath()+"/C9Alarm/";
	public static final String path_theme =path_root+"/theme/";
	public static final String path_download =path_root+"/download/";

	public static SDResReadManager getInstance() {
		if (mSDResReadManager == null)
			mSDResReadManager = new SDResReadManager();
		return mSDResReadManager;

	}

	Map<String, ThemeRes> resMap;

	public SDResReadManager() {
		super();
		this.resMap = new HashMap<String, ThemeRes>();
		// resMap.put("pikaqiu", "");

	}

	public ThemeRes getResource(String name) {
		return resMap.get(name);
	}

	public void appendRes(String name, ThemeRes mThemeRes) {
		resMap.put(name, mThemeRes);
	}
	public void removeRes(String name) {
		resMap.remove(name) ;
	}
	

	private XmlPullParser readXml(InputStream mIn) 
			throws XmlPullParserException {
		if(mIn==null) return null;
		XmlPullParser xmp = XmlPullParserFactory.newInstance().newPullParser();
		xmp.setInput(mIn, "utf-8");
		return xmp;
	}

	public Bitmap getResBitmap(int key, String themeName, int width, int height) {

		Bitmap mBitmap;
		if(isNativeResource(key, themeName)){
			return BitmapWorker.getBitmap(AlarmApplication.getApp(), key, width, height);
		}
		
		try {
			InputStream in = getResInputStream(key, themeName);
			BitmapFactory.Options op = BitmapWorker.getSampleSizeOption(in, width, height);
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			in = getResInputStream(key, themeName);
			mBitmap = BitmapWorker.getBitmap(in,op);
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return mBitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return Bitmap.createBitmap(1, 1, Config.RGB_565);
		}

	}

	public Bitmap getResBitmap(int key, String themeName,
			Options mBitmapOptions) {
		Bitmap mBitmap;
		if(isNativeResource(key, themeName)){
			 mBitmap = BitmapWorker.getBitmap(AlarmApplication.getApp(),key, mBitmapOptions);
		}else{
		InputStream in = getResInputStream(key, themeName);
		 mBitmap = BitmapWorker.getBitmap(in, mBitmapOptions);
		}
		return mBitmap;
	}
	
	public XmlPullParser getResXml(int key, String themeName) {
		InputStream in = getResInputStream(key, themeName);
		try {
			return readXml(in);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return null;
	}

	public FileInputStream getResInputStream(int key, String themeName) {
		MyLogger.e("anim", ""+key+" "+themeName);
		if (resMap.containsKey(themeName)) {
			ThemeRes mThemeRes = resMap.get(themeName);
			String path = getResPath(themeName);
			String name = (String) mThemeRes.getRes(key);

			if (path != null && name != null) {
				FileInputStream in = FileManager.getInstance().read(path, name);
				return in;
			}
		}
		return null;
	}
	
	
	public <T> T getResObject(int key, String themeName) {
		if(isNativeResource(key, themeName)) return (T)(new Integer(key));
		if (resMap.containsKey(themeName)) {
			ThemeRes mThemeRes = resMap.get(themeName);
			Object o = mThemeRes.getRes(key);
			
			return (T)o;
		}
		return null;
	}
	
	public int getResInt(int key, String themeName) {
		if(isNativeResource(key, themeName)) return key;
		if (resMap.containsKey(themeName)) {
			ThemeRes mThemeRes = resMap.get(themeName);
			int name = (Integer) mThemeRes.getRes(key);
			return name;
		}
		return -1;
	}

	public String getResPath(String themeName) {
		if (resMap.containsKey(themeName)) {
			ThemeRes mThemeRes = resMap.get(themeName);
			String path = mThemeRes.getPath();
			return this.path_theme+path;
		}
		return null;
	}
	
	public <T> T getResPathWithName(int key,String themeName){
		if(isNativeResource(key, themeName)) return (T) new Integer(key); 
		if (resMap.containsKey(themeName)) {
			ThemeRes mThemeRes = resMap.get(themeName);
			String path = mThemeRes.getPath();
			path =  this.path_theme+path;
			String name = (String) mThemeRes.getRes(key);
			File f = FileManager.getInstance().generateFile(path, name);
			return (T) f.getPath();
		}
		return (T) new Integer(key); 
	}

	public static void test() throws XmlPullParserException {
		if(!hasSDCard()){
			AlarmApplication.getApp().showError("当前SD卡不可用,加载失败");
			return ;
		}
		
		if(true||AlarmApplication.getApp().getUser().getIsFirstInstall()){
			copyResToSdcard(AlarmApplication.getApp(),path_download);
			AlarmApplication.getApp().getUser().setIsFirstInstall(false);
		} 
		
		parseDownloadDataList(true,getDownloadDataList());
		
		
		File[] mFile=FileManager.getInstance().getFileList(path_theme);
		
		
		for(int i=0;i<mFile.length;i++){
					if(mFile[i].isDirectory()){
						File fdat = new File(mFile[i].getPath()+"/dat");
						if(fdat.exists()&&fdat.isFile()){
							loadOneTheme(fdat);
						}
				//}
			}
		}
	}
	
	
	public synchronized void  installOne(String name,LoadOverCallBack mLoadOverCallBack)  {
		
		File f = new File(path_download+name);
		if(f.exists()){
			parseDownloadDataList(true,f);
		}
	//	parseDownloadDataList(getDownloadDataList());
		
		
		File[] mFile=FileManager.getInstance().getFileList(path_theme);
		 
		for(int i=0;i<mFile.length;i++){
					if(mFile[i].isDirectory()){
						File fdat = new File(mFile[i].getPath()+"/dat");
						if(fdat.exists()&&fdat.isFile()){
							boolean isSuccess=loadOneTheme(fdat);
							if(mLoadOverCallBack!=null){
								mLoadOverCallBack.LoadOver(isSuccess, name);
							}
						}
			}
		}
	}
	
	public void removeOneData(String name)  {
		
		File f = new File(path_download+name);
		if(f.exists()){
			f.delete();
		}
	
	}
	
	public void removeOneInstall(String name)  {
		if(Form.hasForm(name)){
			Form.removeForm(name);
		}
		if(resMap.containsKey(name)){
			ThemeRes mThemeRes = this.resMap.get(name);
			mThemeRes.deleteSelf();
			File f = new File(path_theme+mThemeRes.getRootPath());
			FileManager.getInstance().DeleteRecursive(f);
			
			resMap.remove(name);
		}
		
		
		File f = new File(path_theme+name);
		if(f.exists()){
			f.delete();
		}
	
	}
	
	public static boolean loadOneTheme(File dat){
		FileInputStream inXml = FileManager.getInstance().read(dat);
		if(inXml!=null)
			try {
				ResXmlReader.parseResXml(getInstance().readXml(inXml),dat.getParentFile().getName());
				
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
//		return false;
		
		
		FileInputStream f = FileManager.getInstance().read(dat);
		 
		 InputStreamReader inputreader = new InputStreamReader(f);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            //分行读取
            try {
				while (( line = buffreader.readLine()) != null) {
					MyLogger.e("aaaa", line+"\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}                
            try {
				f.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return false;
	}
	
	
	public static File[] getDownloadDataList(){
		File f = new File(path_download);
		f.mkdirs();
		FileFilter mFilter=new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				if(pathname.getName().endsWith(".prx")){ 
					return true;
				}
				return false;
			}
		};
		File []mf=f.listFiles(mFilter);
		return mf;
		
	}
	
	public static void parseDownloadDataList(boolean successDelete,File... mf){
		if(mf==null) return ;
		for(int i =0;i<mf.length;i++){
			if(mf[i].getName().endsWith(".prx")){
				
				String zipFile = mf[i].getPath(); 
				String unzipLocation = path_theme;
				 
				Decompress d = new Decompress(zipFile, unzipLocation); 
				try {
					
					d.upZipFile(zipFile, unzipLocation);
					if(successDelete){
						mf[i].delete();
					}
				//	
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					
				}
				
			}
		}
	}
	
	public boolean isNativeResource(int key,String themeName){
		if(key<0||themeName==null||resMap.containsKey(themeName)==false)
			return true;
		return false;
	}
	
	
	 public static interface LoadOverCallBack{
		 public void LoadOver(Boolean isSuccess,String name);
		 
	 }
	 
	 public static void copyResToSdcard(Context c,String name){//name为sd卡下制定的路径 
	     Field[] raw = R.raw.class.getFields(); 
	    for (Field r : raw) { 
	         try { 
	        //     System.out.println("R.raw." + r.getName()); 
	             int id=c.getResources().getIdentifier(r.getName(), "raw", c.getPackageName()); 
	               if(r.getName().endsWith("stall")){ 
	                     String path=name+"/"+r.getName()+".prx"; 
	                    File dir = new File( name);
	             		dir.mkdirs();
	                 BufferedOutputStream bufEcrivain = new BufferedOutputStream((new FileOutputStream(new File(path)))); 
	                 BufferedInputStream VideoReader = new BufferedInputStream(c.getResources().openRawResource(id)); 
	                 byte[] buff = new byte[20*1024]; 
	                 int len; 
	                 while( (len = VideoReader.read(buff)) > 0 ){ 
	                     bufEcrivain.write(buff,0,len); 
	                 } 
	                 bufEcrivain.flush(); 
	                 bufEcrivain.close(); 
	                 VideoReader.close(); 
	               } 
	         } catch (Exception e) { 
	             e.printStackTrace(); 
	         } 
	     } 
	     
	} 
	 
	 /**
		* 判断手机是否有SD卡。
		* 
		* @return 有SD卡返回true，没有返回false。
		*/
		public static boolean hasSDCard() {
			return Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState());
		}
	
}
