package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.tavx.C9Alarm.AlarmApplication;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileManager {
	private static FileManager mFileManager;
	
	
	private FileManager(){}
	public static FileManager getInstance(){
		if(mFileManager==null){
			mFileManager=new FileManager();
		}
		return mFileManager;
	}
	
	public File generateFile(String path,String fileName){
		//File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File( path);
		dir.mkdirs();
		File file = new File(dir, fileName);
		return file;
	}
	
	public File[] getFileList(String path){
		File dir = new File(path);
		dir.mkdirs();
		return dir.listFiles();
	}
	
	
	private File generateFile(String fileName){
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File(sdCard.getAbsolutePath() + AlarmApplication.path);
		dir.mkdirs();
		File file = new File(dir, fileName);
		return file;
	}
	private FileOutputStream getFileOut(File file) {
		
		FileOutputStream f = null;
		try {
			f = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}

private FileInputStream getFileIn(File file) {
	File bufFile =file;
		if(bufFile.exists()==false){
			if(bufFile.getName().indexOf(".")==-1){
				bufFile =new File(file.getPath()+".sav");
				if(!bufFile.exists()){
					bufFile =new File(file.getPath()+".jpeg");
					 if(!bufFile.exists()){
						 bufFile =new File(file.getPath()+".png");
						 if(!bufFile.exists()){
							 bufFile =new File(file.getPath()+".xml");
							 if(!bufFile.exists()){
								 bufFile =new File(file.getPath()+".9.png");
									 if(!bufFile.exists()){
								          return null;
									 }
						}
					}
				}
			}
			}
			
		}
		FileInputStream f = null;
		try {
			f = new FileInputStream(bufFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return f;
	}

	
	
	
	private void saveFile(FileOutputStream fout, InputStream fin) {
		try {
			byte[] buffer = new byte[1024];
			int len1 = 0;
			while ((len1 = fin.read(buffer)) > 0) {
				fout.write(buffer, 0, len1);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public File saveResToFile(Context c,int res,String fileName){
		
		InputStream in = c.getResources().openRawResource(res);
		File f = generateFile(fileName);
		FileOutputStream out =getFileOut(f);
		saveFile(out, in);
		return f;
	}

	public void DownloadFile(String fileURL, String fileName) {
		try {
			File root = Environment.getExternalStorageDirectory();
			URL u = new URL(fileURL);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setDoOutput(true);
			c.connect();
			FileOutputStream f = new FileOutputStream(new File(root, fileName));

			InputStream in = c.getInputStream();

			byte[] buffer = new byte[1024];
			int len1 = 0;
			while ((len1 = in.read(buffer)) > 0) {
				f.write(buffer, 0, len1);
			}
			f.close();
		} catch (Exception e) {
			Log.d("Downloader", e.getMessage());
		}

	}
	
	public FileInputStream read(String path,String name){
		return  getFileIn(generateFile(path, name));
	} 
	
	public FileInputStream read(File f){
		return  getFileIn(f);
	} 
	
	
	public void DeleteRecursive(File fileOrDirectory) {
	    if (fileOrDirectory.isDirectory())
	        for (File child : fileOrDirectory.listFiles())
	            DeleteRecursive(child);

	    fileOrDirectory.delete();
	}
	
}
