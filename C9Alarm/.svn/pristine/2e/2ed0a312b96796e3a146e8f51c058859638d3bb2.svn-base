package com.tavx.C9Alarm.View;

import m.framework.utils.Utils;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.view.View;

import com.tavx.C9Alarm.AlarmApplication;

abstract class Shape {
		boolean visible = false;
		boolean isPressed = false;
		int locX;
		int locY;
		int maxXOffset;
		int maxYOffset;
		View.OnClickListener mListener ;
		
		Paint MiddleTextPaint;
		Paint LargeTextPaint;
		Paint SmallTextPaint;
		Paint BorderPaint;
		Paint GraPaint;

		public Shape() {
			ini();
			iniPaint();
		}
		
		public boolean getVisiblity(){
			return visible;
		}
		public void setVisiblity(boolean b){
			 visible = b;
		}
		
		public abstract void ini();

		public abstract void draw(Canvas canvas);
		
		public abstract boolean checkClick(float x,float y);
		
		public void pressDown(){
			isPressed = true;
		}
		
		public void pressUp(){
			isPressed = false;
		}
		
		public void setOnclickListener(View.OnClickListener l){
			mListener = l;
		}
		
		public void onClick(){
			if(mListener!=null) {
				mListener.onClick(null);
			}
		}
		
		public void iniPaint(){
			MiddleTextPaint = new Paint();
			MiddleTextPaint.setColor(Color.BLACK);
			MiddleTextPaint.setTextAlign(Paint.Align.CENTER);
			MiddleTextPaint.setAntiAlias(true);

			LargeTextPaint = new Paint();
			LargeTextPaint.setColor(Color.BLACK);
			LargeTextPaint.setTextAlign(Paint.Align.CENTER);
			LargeTextPaint.setAntiAlias(true);

			SmallTextPaint = new Paint();
			SmallTextPaint.setColor(Color.BLACK);
			SmallTextPaint.setTextAlign(Paint.Align.CENTER);
			SmallTextPaint.setAntiAlias(true);

			BorderPaint = new Paint();
			BorderPaint.setStyle(Paint.Style.STROKE);
			BorderPaint.setColor(Color.WHITE);
			BorderPaint.setAntiAlias(true);
			Xfermode xfermode = new PorterDuffXfermode(Mode.DST_OVER);
			BorderPaint.setXfermode(xfermode);

			GraPaint = new Paint();
			GraPaint.setStyle(Style.FILL);
			
			MiddleTextPaint.setTextSize(Utils.dipToPx(AlarmApplication.getApp(), 20));
			SmallTextPaint.setTextSize(Utils.dipToPx(AlarmApplication.getApp(), 15));
			LargeTextPaint.setTextSize(Utils.dipToPx(AlarmApplication.getApp(), 30));

			BorderPaint.setStrokeWidth(Utils.dipToPx(AlarmApplication.getApp(), 3));
		}
		
		public int getX(){
			return locX;
		}

		public int getY(){
			return locY;
		}
		
		public void setX(int x){
			locX=x;
		}
		public void setY(int y){
			locY=y;
		}

	}