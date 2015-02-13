package com.tavx.C9Alarm.View;

import java.util.ArrayList;
import java.util.List;

import m.framework.utils.Utils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Xfermode;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.bean.HotInfo;

@SuppressLint("DrawAllocation")
public class AlarmButtonPad extends View {
//	Paint MiddleTextPaint;
//	Paint LargeTextPaint;
//	Paint SmallTextPaint;
//	Paint CirclePaint;
//	Paint GraPaint;

	float LargeSizeRate = 2.7f;
	float MiddleSizeRate = 7;
	float SmallSizeRate = 11;

	float fixRate = 0.9f;

	float fillRate = 0.7f;// 中心渐变度0~1；
	LinearGradient gradient;

	int mWidth = 0;

	HotInfo mHotInfo;
	boolean hasChanged = true;

	public static final int STATE_NORMAL = 1;
	public static final int STATE_CLICKED = 2;

	private int current_state = STATE_NORMAL;

	boolean isOnAnim = false;
	boolean isOnTimeChange = false;
	Ball mainBall, sleepBall, awakeBall;
	int timeCounter;
	List<Ball> mBallList;

	public AlarmButtonPad(Context context) {
		super(context);

		inil();
	}

	public AlarmButtonPad(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		inil();
	}

	public AlarmButtonPad(Context context, AttributeSet attrs) {
		super(context, attrs);
		inil();
	}

	public void inil() {
		this.setDrawingCacheEnabled(true);
//		MiddleTextPaint = new Paint();
//		MiddleTextPaint.setColor(Color.BLACK);
//		MiddleTextPaint.setTextAlign(Paint.Align.CENTER);
//		MiddleTextPaint.setAntiAlias(true);
//
//		LargeTextPaint = new Paint();
//		LargeTextPaint.setColor(Color.BLACK);
//		LargeTextPaint.setTextAlign(Paint.Align.CENTER);
//		LargeTextPaint.setAntiAlias(true);
//
//		SmallTextPaint = new Paint();
//		SmallTextPaint.setColor(Color.BLACK);
//		SmallTextPaint.setTextAlign(Paint.Align.CENTER);
//		SmallTextPaint.setAntiAlias(true);
//
//		CirclePaint = new Paint();
//		CirclePaint.setStyle(Paint.Style.STROKE);
//		CirclePaint.setColor(Color.WHITE);
//		CirclePaint.setAntiAlias(true);
//
//		GraPaint = new Paint();
//		GraPaint.setStyle(Style.FILL);

		mainBall = new Ball() {

			@Override
			public void draw(Canvas canvas) {
				if(currentStateRate==0)
				GraPaint.setColor(0x77ffffff);
				else
				GraPaint.setColor(0x77585858);
				canvas.drawCircle(locX, locY, r, GraPaint);
				
				if(isPressed){
					CirclePaint.setColor(0xff65a9ee);
				}else{
					CirclePaint.setColor(0xffffffff);
				}
				canvas.drawCircle(locX, locY, r, CirclePaint);
				canvas.drawText("" + timeCounter, locX, locY+LargeTextPaint.getTextSize()/3, LargeTextPaint);
				canvas.drawText("S" , locX+LargeTextPaint.getTextSize()/2+SmallTextPaint.getTextSize()/2, locY-LargeTextPaint.getTextSize()/2+SmallTextPaint.getTextSize()/2, SmallTextPaint);
			}

			@Override
			public void ini() {
				int margin = 10;
				r = Utils.dipToPx(AlarmApplication.getApp(), 40);
				locX = getWidth()-r-10;
				locY = r+10;

			}
			@Override
			public void iniPaint() {
				super.iniPaint();
				Xfermode lighten = new PorterDuffXfermode(Mode.LIGHTEN);
				GraPaint.setXfermode(lighten);
			}
		};

		sleepBall = new Ball() {

			@Override
			public void draw(Canvas canvas) {
				if(currentStateRate==0)return ;
				
				canvas.drawCircle(locX, locY, r, GraPaint);
				
				if(isPressed){
					CirclePaint.setColor(0xff65a9ee);
				}else{
					CirclePaint.setColor(0xffffffff);
				}
				canvas.drawCircle(locX, locY, r, CirclePaint);
				canvas.drawText("起床", locX, locY, SmallTextPaint);
			}

			@Override
			public void ini() {
				if(currentStateRate == 0) visible=false;
				else visible = true;
				int margin = 10;
				r = Utils.dipToPx(AlarmApplication.getApp(), 40);
				maxXOffset = r*2+10;
				maxYOffset = r*2+10;
				cal(currentStateRate, 100, 0);
			}

			private void cal(int currentStateRate, int max, int min) {
				float rate = currentStateRate * 1.0f / (max - min);
				float xoffset = rate * maxXOffset * 0;
				float yoffset = rate * maxYOffset;

				locX = (int) (mainBall.locX - xoffset);
				locY = (int) (mainBall.locY + yoffset);
			}
			
			@Override
			public void iniPaint() {
				super.iniPaint();
				GraPaint.setColor(0x77ffffff);
				Xfermode lighten = new PorterDuffXfermode(Mode.LIGHTEN);
				Xfermode dst_over = new PorterDuffXfermode(Mode.DST_OVER);
				GraPaint.setXfermode(lighten);
				CirclePaint.setXfermode(dst_over);
			}
		};

		awakeBall = new Ball() {

			@Override
			public void draw(Canvas canvas) {
				if(currentStateRate==0)return ;
			
				canvas.drawCircle(locX, locY, r, GraPaint);
				
			
				if(isPressed){
					CirclePaint.setColor(0xff65a9ee);
				}else{
					CirclePaint.setColor(0xffffffff);
				}
				canvas.drawCircle(locX, locY, r, CirclePaint);
				canvas.drawText("再睡一会", locX, locY, SmallTextPaint);
			}

			@Override
			public void ini() {
				if(currentStateRate == 0) visible=false;
				else visible = true;
				
				int margin = 10;
				r = Utils.dipToPx(AlarmApplication.getApp(), 40);
				maxXOffset = r*2+10;
				maxYOffset = r*2+10;
				cal(currentStateRate, 100, 0);
			}

			private void cal(int currentStateRate, int max, int min) {
				float rate = currentStateRate * 1.0f / (max - min);
				float xoffset = rate * maxXOffset;
				float yoffset = rate * maxYOffset * 0;

				locX = (int) (mainBall.locX - xoffset);
				locY = (int) (mainBall.locY + yoffset);
			}
			
			@Override
			public void iniPaint() {
				super.iniPaint();
				GraPaint.setColor(0x77ffffff);
				Xfermode lighten = new PorterDuffXfermode(Mode.LIGHTEN);
				Xfermode dst_over = new PorterDuffXfermode(Mode.DST_OVER);
				GraPaint.setXfermode(lighten);
				CirclePaint.setXfermode(dst_over);
				
			}
		};
		mBallList = new ArrayList<AlarmButtonPad.Ball>();
		mBallList.add(mainBall);
		mBallList.add(sleepBall);
		mBallList.add(awakeBall);
		
		mainBall.setOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				toggleState();
			}
		});
		
		awakeBall.setOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if(mAlarmAwakeInterface!=null)mAlarmAwakeInterface.onAwake();
			}
		});
		sleepBall.setOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if(mAlarmAwakeInterface!=null)mAlarmAwakeInterface.onSnooze();
			}
		});
		
		timeCount();
		
	}

	public void setParam(HotInfo mHotInfo) {
		this.mHotInfo = mHotInfo;

		setfillrate(mHotInfo.getLastTimeCount() / 7f);
	}

	public void setfillrate(float fillRate) {
		if (this.fillRate == fillRate)
			return;
		if (fillRate > 1)
			fillRate = 1;
		this.fillRate = fillRate;
	}

	private void resetPaint(int width) {
//		MiddleTextPaint.setTextSize(Utils.dipToPx(AlarmApplication.getApp(), 20));
//		SmallTextPaint.setTextSize(Utils.dipToPx(AlarmApplication.getApp(), 15));
//		LargeTextPaint.setTextSize(Utils.dipToPx(AlarmApplication.getApp(), 30));
//
//		CirclePaint.setStrokeWidth(width / 100f);
	}
	Thread refreshThread = new Thread(new Runnable() {

		@Override
		public void run() {
			while (isOnAnim == true || isOnTimeChange == true) {
				synchronized (refreshThread) {
					if (true) {
						MyLogger.e(TAG, "refrsh",true );
						postInvalidate();
						try {
							if (isOnAnim == true) {
								Thread.sleep(50);
							} else {
								Thread.sleep(300);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			}
		}
	});

	void startRefresh() {
		if(refreshThread.isAlive()==false)
		refreshThread.start();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (changed == true) {

			int width = right - left;

			resetPaint(width);
			// GraPaint.setColor(Color.TRANSPARENT);

		}
	}

	float rotateAngle;

	public void setRoatation(float angle) {
		if (cache == null) {
			cache = getDrawingCache();
		}

		if (angle < 0.01)
			rotateAngle = 0;
		else
			rotateAngle = angle * 360;

		postInvalidate();
	}

	Bitmap cache;
	private ValueAnimator moveAnimator;
	private ValueAnimator timeAnimator;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// int width = 100;
		// width = MeasureSpec.getSize(widthMeasureSpec);
		// setMeasuredDimension(width|MeasureSpec.EXACTLY,
		// width|MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		if (getWidth() == 0)
			return;
		int width = getWidth();
		int height = getHeight();

		/*
		 * canvas.save(); canvas.rotate(rotateAngle,width/2f, height/2f);
		 * if(rotateAngle>0.01&&cache!=null){ MyLogger.e("aaa", "cache");
		 * canvas.drawBitmap(cache, new Matrix(), new Paint()); }else{
		 * MyLogger.e("aaa", "no cache"); if(cache!=null){ cache=null;
		 * this.destroyDrawingCache(); } canvas.drawColor(Color.TRANSPARENT);
		 * canvas.drawCircle(width/2, height/2,
		 * fixRate*(width/2-1),CirclePaint);
		 * 
		 * gradient = new LinearGradient(width/2, width -width/2*(fillRate),
		 * width/2, width/2 -width/2*(fillRate), new
		 * int[]{0xffF7D94C,0x00ffffff}, new float[]{0,1}, TileMode.CLAMP);
		 * GraPaint.setShader(gradient); canvas.drawCircle(width/2, height/2,
		 * fixRate*(width/2-1-width/100f),GraPaint); GraPaint.setShader(null);
		 * gradient = null; if(mHotInfo!=null){ float useCountOffsetX =
		 * (width/LargeSizeRate/2)*fixRate; float useCountOffsetY =
		 * (width/LargeSizeRate/4)*fixRate; if(mHotInfo.sumCount<10){
		 * useCountOffsetX = (width/LargeSizeRate/4)*fixRate; }
		 * canvas.drawText(""+mHotInfo.sumCount, width/2+useCountOffsetX,
		 * height/2+useCountOffsetY, LargeTextPaint); canvas.drawText("次",
		 * width/2+width/LargeSizeRate/2*fixRate,
		 * height/2-width/LargeSizeRate/2*fixRate, MiddleTextPaint);
		 * canvas.drawText
		 * ("累计准时率"+(int)(mHotInfo.intTimeCount*100f/mHotInfo.sumCount)+"%",
		 * width/2,
		 * height/2+(width/LargeSizeRate/4+width/SmallSizeRate*2)*fixRate,
		 * SmallTextPaint); } } canvas.restore();
		 */
		MyLogger.e(TAG,""+ getWidth()+" "+getHeight() ,true);
		mainBall.setVisiblity(true);
		mainBall.ini();
		sleepBall.ini();
		awakeBall.ini();
		
		sleepBall.draw(canvas);
		awakeBall.draw(canvas);
		mainBall.draw(canvas);
	}

	

	public void toggleState(){
		changeState(current_state ==STATE_NORMAL?STATE_CLICKED:STATE_NORMAL);
	}
	public void changeState(int state) {
		this.current_state = state;
		dropUpAnim(current_state==STATE_CLICKED);
	}

	void timeCount() {

		if (timeAnimator != null && timeAnimator.isRunning()) {
			timeAnimator.cancel();
		}
		timeAnimator = ObjectAnimator.ofInt(new AnimHolder(0), "weight", 0, 60);

		timeAnimator.setDuration(60 * 1000);
		timeAnimator.setInterpolator(new LinearInterpolator());
		timeAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				MyLogger.e(VIEW_LOG_TAG,
						"" + (Integer) animation.getAnimatedValue(), true);
				timeCounter = (Integer) animation.getAnimatedValue();
			}
		});
		
		
		timeAnimator.start();
		isOnTimeChange = true;
		startRefresh();

	}

	public synchronized void dropUpAnim(boolean isOut) {

		if (moveAnimator != null && moveAnimator.isRunning()) {
			moveAnimator.cancel();
		}
		
		final int dist;
		Interpolator interplolator; 
		if (isOut) {
			moveAnimator = ObjectAnimator.ofInt(new AnimHolder(0), "weight",
					currentStateRate, 100);
			dist = 100;
			interplolator = new OvershootInterpolator();
		} else {
			moveAnimator = ObjectAnimator.ofInt(new AnimHolder(0), "weight",
					currentStateRate, 0);
			dist = 0; 
			interplolator =  new  AnticipateInterpolator();
		}

		moveAnimator
				.setDuration((long) (600 * (Math.abs(dist - currentStateRate) / 100f)));
		moveAnimator.setInterpolator(interplolator);
		moveAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				currentStateRate = (Integer) animation.getAnimatedValue();
			}
		});
		moveAnimator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				currentStateRate = dist;
				isOnAnim = false;
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});
		moveAnimator.start();
		isOnAnim = true;
		startRefresh();

	}

	int currentStateRate = 0;

	class AnimHolder {
		public AnimHolder(int weight) {
			super();
			this.weight = weight;
		}

		int weight;

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}
	}

	AnimHolder mAnimHolder = new AnimHolder(0);
	String TAG = getClass().getName();
	
	/* (non-Javadoc)
	 * @see android.view.View#onDetachedFromWindow()
	 */
	@Override
	protected void onDetachedFromWindow() {
		try {
			if(moveAnimator!=null&&moveAnimator.isRunning()){
			moveAnimator.cancel();
			}
			
			if(timeAnimator!=null&&timeAnimator.isRunning()){
			timeAnimator.cancel();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			moveAnimator = null;
			timeAnimator = null;
		}
		
		isOnAnim = false;
		isOnTimeChange = false;
		
		super.onDetachedFromWindow();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			
			

		case MotionEvent.ACTION_MOVE:{
			float x = event.getX();
			float y = event.getY();
			
			Ball b= checkHasOnclick(x, y);
			if(b!=null){
			b.pressDown();
			}
			break;
		}
		case MotionEvent.ACTION_UP:{
			float x = event.getX();
			float y = event.getY();
			
			Ball b= checkHasOnclick(x, y);
			if(b!=null){
			b.pressUp();
			b.onClick();
			}
			break;
		}
		default:
			break;
		}

		return true;
	}
	
	Ball checkHasOnclick(float x,float y){
		for(int i = 0;i<mBallList.size();i++){
			for(Ball b:mBallList){
				if(b.getVisiblity()==false){
					continue;
				}
				boolean hasOnCLick = b.checkClick(x, y);
				if(hasOnCLick==true) return b;
				else b.pressUp();
			}
		}
		return null;
	}
	
	AlarmAwakeInterface mAlarmAwakeInterface;
	public void setAlarmAwakeInterface(AlarmAwakeInterface mAlarmAwakeInterface){
		this.mAlarmAwakeInterface = mAlarmAwakeInterface;
	}
	
	abstract class Ball {
		boolean visible = false;
		boolean isPressed = false;
		int locX;
		int locY;
		int r;
		int maxXOffset;
		int maxYOffset;
		View.OnClickListener mListener ;
		
		Paint MiddleTextPaint;
		Paint LargeTextPaint;
		Paint SmallTextPaint;
		Paint CirclePaint;
		Paint GraPaint;

		public Ball() {
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
		
		public boolean checkClick(float x,float y){
			Rect rect = new Rect(locX - r, locY - r, locX + r, locY + r);
			boolean isContain=rect.contains((int)x, (int)y);
			
			return isContain;
		}
		
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

			CirclePaint = new Paint();
			CirclePaint.setStyle(Paint.Style.STROKE);
			CirclePaint.setColor(Color.WHITE);
			CirclePaint.setAntiAlias(true);

			GraPaint = new Paint();
			GraPaint.setStyle(Style.FILL);
			
			MiddleTextPaint.setTextSize(Utils.dipToPx(AlarmApplication.getApp(), 20));
			SmallTextPaint.setTextSize(Utils.dipToPx(AlarmApplication.getApp(), 15));
			LargeTextPaint.setTextSize(Utils.dipToPx(AlarmApplication.getApp(), 30));

			CirclePaint.setStrokeWidth(Utils.dipToPx(AlarmApplication.getApp(), 3));
		}

	}
}
