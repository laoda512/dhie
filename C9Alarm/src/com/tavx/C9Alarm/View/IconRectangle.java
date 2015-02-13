/**
 * 
 */
package com.tavx.C9Alarm.View;

import com.avos.avoscloud.GetHttpResponseHandler;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * @author Administrator
 *
 */
public abstract class IconRectangle extends Rectangle{
	
	int textLocX,textLocY;
	int iconLocX,iconLocY;
	
	public IconRectangle(){
		super();
	}
	public IconRectangle(Drawable d){
		super();
		setIcon(d);
	}

	Drawable icon;
	public void setIcon(Drawable d){
		this.icon=d;
	}
	
	public Drawable getIcon(){
		return icon;
	}
	
	@Override
	public void iniPaint() {
		super.iniPaint();
		GraPaint.setColor(0x99ffffff);
		
		LargeTextPaint.setTextAlign(Paint.Align.LEFT);
		MiddleTextPaint.setTextAlign(Paint.Align.LEFT);
		SmallTextPaint.setTextAlign(Paint.Align.LEFT);
	}
	
	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.View.Rectangle#ini()
	 */
	@Override
	public void ini() {
		Rect iconBd = getIconBounds();
		if(getIcon()!=null)
		getIcon().setBounds(iconBd);
		
		textLocX =  iconBd.right+width/12;
		textLocY = iconBd.bottom;
	}
	
	public Rect getIconBounds(){
		return new Rect(getLeft() + width/13, getTop() + height*2/10, getLeft() + width/13+ height*6/10, getTop()+ height*4/5);
	}
	
	public void calTextLoc(){
		
	}
	
	public void drawIcon(Canvas c){
		if(getIcon()!=null)
			getIcon().draw(c);
	}
	
	public void drawBound(Canvas canvas){
		Rect rect = getBounds();
		canvas.drawRect(rect, GraPaint);//(locX, locY, r, GraPaint);
		
		if(isPressed){
			BorderPaint.setColor(0xff65a9ee);
		}else{
			BorderPaint.setColor(0x77fcfde9);
		}
		canvas.drawRect(rect, BorderPaint);
	}
	
	public void drawText(Canvas canvas,String text){
		
		canvas.drawText(text, locX, locY+MiddleTextPaint.getTextSize()/3, SmallTextPaint);

		drawIcon(canvas);
	}

	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.View.Rectangle#draw(android.graphics.Canvas)
	 */
	@Override
	public abstract void draw(Canvas canvas) ;

}
