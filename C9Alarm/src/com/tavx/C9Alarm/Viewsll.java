package com.tavx.C9Alarm;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tavx.C9Alam.connector.MyLogger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class Viewsll extends View{

	
	
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//		
//		myButton mb;
//		   Iterator<myButton> i= buttonList.iterator();
//		   while(i.hasNext())
//		   {
//			   mb = i.next();
//			   if(mb.r.contains((int)event.getX(),(int) event.getY())){
//				   mb.c.onClick();
//				   return true;
//			   }
//			   
//		   }
//		
//		
//		return super.onTouchEvent(event);
//	}

	int time=-1;
	Bitmap mBitmap;
	Canvas mCanvas;
	Paint bp;
	Paint fp;
	List<myButton> buttonList;
	
	public Viewsll(Context context,int time) {
		super(context);
		
		
		inial(context);
		
		setData(time);
		
		doDraw();
		
		//this.invalidate();
		// TODO Auto-generated constructor stub
	}
	private void inial(Context c ){
		mBitmap = Bitmap.createBitmap((int) (480 ),
				(int) (550 ), Config.ARGB_8888);
		
		mCanvas = new Canvas(mBitmap);
	//	this.setBackgroundResource(R.drawable.bg_jimi);
		
		Bitmap backgroundBitmap = BitmapFactory.decodeResource(getResources(), 0);
		bp=new Paint();
		bp.setStyle(Paint.Style.FILL);
		mCanvas.drawBitmap(backgroundBitmap, 0, 0, bp);
		
		fp =new Paint();
		fp.setAntiAlias(true);
		fp.setColor(Color.YELLOW);
		fp.setTextSize(24f*Scale.getXScale());
		
		
		
		
		
	}
	private void DrawMainText(){
		mCanvas.drawText("你今天起床时间为："+time+"秒", 10, 24,fp);
	}
	private void DrawButton(){
		myButton mb;
	   Iterator<myButton> i= buttonList.iterator();
	   while(i.hasNext())
	   {
		   mb = i.next();
		   if(mb.back!=null){
		   mCanvas.drawBitmap(mb.back,mb.r.left,mb.r.top, bp);
		   }else{
			   MyLogger.e("null", "null");
		   }
		   
	   }
	   this.invalidate();
	}
	
	public void setData(int time){
		this.time=time;
		buttonList = new ArrayList<myButton>();
		
	}
	public void addButton(int x,int y,int w,int l,Bitmap b,ClickListener c){
		
		Rect r =new Rect();
		r.set(x,y,x+w, y+l);
		
		buttonList.add(new myButton(r,c,b,b));
		
		
	}
	public void doDraw(){
		DrawMainText();
		DrawButton();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (mBitmap != null) {

		
			canvas.drawBitmap(mBitmap, 0, 0, bp);
		}
	}

	private class myButton{
		Rect r;
		ClickListener c;
		public myButton(Rect r, ClickListener c, Bitmap back, Bitmap onclick) {
			super();
			this.r = r;
			this.c = c;
			this.back = back;
			this.onclick = onclick;
		}
		Bitmap back;
		Bitmap onclick;
	}
	

}
