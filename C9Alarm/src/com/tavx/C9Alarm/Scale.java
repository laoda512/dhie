package com.tavx.C9Alarm;

import android.graphics.Matrix;
import android.util.DisplayMetrics;

public class Scale {
	static DisplayMetrics dm ;
	static int default_width= 480;
	static int default_height = 800;
	
	public static void setDm(DisplayMetrics dm){
		Scale.dm = dm;
       

	}
	public static float getXScale(){
		return ((float)dm.heightPixels)/default_height;
	}
	public static float getYScale(){
		return ((float)dm.heightPixels)/default_height;
	}
	public static int getXPixels(){
		return dm.widthPixels;
	}
public static int getYPixels(){
		return dm.heightPixels;
	}
	public static Matrix getSacleMatrixByXY(int x,int y){
		float scx=((float)dm.widthPixels)/x;
		float scy=((float)dm.heightPixels)/y;
		scx=scx>scy?scy:scx;
		Matrix mx= new Matrix();
	mx.setScale(scx, scx);
	return mx;
	}

}
