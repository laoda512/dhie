package util;

import java.util.Set;

import android.util.SparseArray;

public class ThemeRes {
	//Map<Integer,String> resMap ;
	SparseArray<Object> resName;
	int keyValue;
	String themeName ;
	
	String rootPath;//文件夹名字
	String path;//全路径 
	String resPath ;//次级目录开始的路径
	public String getRootPath() {
		return rootPath;
	}
	public String getResPath() {
		return resPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public void setResPath(String resPath) {
		this.resPath = resPath;
	}

	public ThemeRes(String themeName, String rootPath,String resPath) {
		super();
		this.themeName = themeName;
		this.path = path;
		keyValue = 0;
		resName = new SparseArray<Object>();
		this.rootPath = rootPath;
		this.resPath = resPath;
		path=rootPath+"/"+resPath;
	}

	
	public String getThemeName() {
		return themeName;
	}
	public String getPath() {
		return path;
	}
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public void deleteSelf(){
		
	}
	
/**
 * @param 
 * name maybe int ,string, int[] or anything
 * and means color ,path,name ,or anything ,should judged by user
 * */
	public int addRes(Object name){
		if(name==null) return -1;
		resName.append(keyValue, name);
		return keyValue++;
	}
	
	public Object getRes(int key){
		return resName.get(key);
	}
}
