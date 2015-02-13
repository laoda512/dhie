package com.tavx.C9Alarm.View;

import java.text.DecimalFormat;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.R;
import com.tavx.C9Alarm.bean.HotInfo;

@SuppressLint("DrawAllocation")
public class HotInfoPad extends View{
	Paint MiddleTextPaint ;
	Paint LargeTextPaint ;
	Paint SmallTextPaint ;
	Paint CirclePaint;
	Paint GraPaint;
	
	float LargeSizeRate =2.7f;
	float MiddleSizeRate =7;
	float SmallSizeRate =11;
	
	float fixRate=0.9f;
	
	float fillRate = 0.7f;//中心渐变度0~1；
	LinearGradient gradient ;
	
	int mWidth = 0;
	
	HotInfo mHotInfo;
	boolean hasChanged =true;
	
	public HotInfoPad(Context context) {
		super(context);
	
		inil();
	}
	
	public HotInfoPad(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		inil();
	}

	public HotInfoPad(Context context, AttributeSet attrs) {
		super(context, attrs);
		inil();
	}
	
	public void inil(){
		this.setDrawingCacheEnabled(true);
		MiddleTextPaint = new Paint();
		MiddleTextPaint.setColor(Color.BLACK);
		MiddleTextPaint.setTextAlign(Paint.Align.LEFT);
		MiddleTextPaint.setAntiAlias(true);
		
		LargeTextPaint = new Paint();
		LargeTextPaint.setColor(Color.BLACK);
		LargeTextPaint.setTextAlign(Paint.Align.RIGHT);
		LargeTextPaint.setAntiAlias(true);
		
		SmallTextPaint = new Paint();
		SmallTextPaint.setColor(Color.BLACK);
		SmallTextPaint.setTextAlign(Paint.Align.CENTER);
		SmallTextPaint.setAntiAlias(true);
		
		CirclePaint = new Paint();
		CirclePaint.setStyle(Paint.Style.STROKE);
		CirclePaint.setColor(Color.WHITE);
		CirclePaint.setAntiAlias(true);
		
		GraPaint = new Paint();
		GraPaint.setStyle(Style.FILL);
		
		
		
	}
	
	public void setParam(HotInfo mHotInfo){
		this.mHotInfo = mHotInfo;
		
		setfillrate(mHotInfo.getLastTimeCount()/7f);
	}
	
	public void setfillrate(float fillRate){
		if(this.fillRate==fillRate) return;
		if(fillRate>1) fillRate =1;
		this.fillRate=fillRate;	
	}
	

	private void resetPaint(int width){
		MiddleTextPaint.setTextSize(width/MiddleSizeRate*fixRate);
		SmallTextPaint.setTextSize(width/SmallSizeRate*fixRate);
		LargeTextPaint.setTextSize(width/LargeSizeRate*fixRate);
		
		CirclePaint.setStrokeWidth(width/100f);
		
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		
		if(changed==true){
			
			int width = right-left;
			
			resetPaint(width);
		//	GraPaint.setColor(Color.TRANSPARENT);
		
			
		}
	}
	float rotateAngle;
	public void setRoatation(float angle){
		if(cache==null){
			cache=getDrawingCache();
		}
		
		if(angle<0.01) rotateAngle=0;
		else
		rotateAngle = angle*360;
		
		postInvalidate();
	}
	Bitmap cache;
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int width = 100;
//	    width = MeasureSpec.getSize(widthMeasureSpec);
//	    setMeasuredDimension(width|MeasureSpec.EXACTLY, width|MeasureSpec.EXACTLY);
	    super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		if(getWidth()==0) return ;
		int width = getWidth();
		int height = getHeight();
		
		canvas.save();
		canvas.rotate(rotateAngle,width/2f, height/2f);
		if(rotateAngle>0.01&&cache!=null){
			MyLogger.e("aaa", "cache");
			canvas.drawBitmap(cache, new Matrix(), new Paint());
		}else{
			MyLogger.e("aaa", "no cache");
		if(cache!=null){
			cache=null;
		this.destroyDrawingCache();
		}
		canvas.drawColor(Color.TRANSPARENT);
		canvas.drawCircle(width/2, height/2, fixRate*(width/2-1),CirclePaint);
		
		gradient = new LinearGradient(width/2, width -width/2*(fillRate), width/2, width/2 -width/2*(fillRate), new int[]{0xffF7D94C,0x00ffffff}, new float[]{0,1}, TileMode.CLAMP);
		GraPaint.setShader(gradient);
		canvas.drawCircle(width/2, height/2, fixRate*(width/2-1-width/100f),GraPaint);
		GraPaint.setShader(null);
		gradient = null;
		if(mHotInfo!=null){
			float useCountOffsetX = (width/LargeSizeRate/2)*fixRate;
			float useCountOffsetY = (width/LargeSizeRate/4)*fixRate;
		if(mHotInfo.sumCount<10){
			 useCountOffsetX = (width/LargeSizeRate/4)*fixRate;
		}
		canvas.drawText(""+mHotInfo.sumCount, width/2+useCountOffsetX, height/2+useCountOffsetY, LargeTextPaint);
		canvas.drawText("次",  width/2+width/LargeSizeRate/2*fixRate, height/2-width/LargeSizeRate/2*fixRate, MiddleTextPaint);
		canvas.drawText("累计准时率"+(int)(mHotInfo.intTimeCount*100f/mHotInfo.sumCount)+"%", width/2, height/2+(width/LargeSizeRate/4+width/SmallSizeRate*2)*fixRate, SmallTextPaint);
		}
		}
		canvas.restore();
	}
	
	
	
	

}
