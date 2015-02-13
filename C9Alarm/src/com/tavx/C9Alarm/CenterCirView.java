package com.tavx.C9Alarm;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mainView.Graphics;
import util.BitmapWorker;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.tavx.C9Alam.connector.MyLogger;

public class CenterCirView extends SurfaceView implements
		SurfaceHolder.Callback, Runnable, OnChangeAlarmListener {

	private int color_alarm_off = Color.GRAY;
	private int color_alarm_on = Color.RED | Color.GRAY;
	private int color_alarm_special = Color.RED;

	private Bitmap Bitmap_time = null;
	private int I_cirAniCount = 0;
	private Bitmap[] Bitmap_cirAnim = null;
	private Bitmap[] Bitmap_cirButton = new Bitmap[4];
	private Bitmap[]  Bitmap_cirButtonLight =  new Bitmap[4];
	private static AnimHolder[] mButtonHolder = new AnimHolder[4];
	private float buttonMoveStart, buttonMoveOver;
	ValueAnimator openBtnAnimator[];
	ValueAnimator lightBtnAnimator[];
	AnimatorSet btnAnimSet[];

	private Bitmap Bitmap_cirArrow = null, Bitmap_cirBg = null;
	private int cirAnimFps;
	private int cirAnimSpeed;
	private int SLEEPTIME = 0;
	int width, height;
	SurfaceHolder surfaceHolder;
	Boolean mLoop;
	Paint arcPaint, textPaintMiddle, textPaintMini, circlePaint;// 空心黑色;
	private Graphics mG ;
	PaintFlagsDrawFilter mSetfil;
	Timer DayChangeTimer;
	private Object inialOver;
	private float pspeed_abs = 0;
	private int vibarate_count = 0;
	private int vibarate_Max = 1;
	int lastCircleAnimIndex;
	boolean efficentMode = false;

	public CenterCirView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		MyLogger.e("boxGameView", "boxGameView");
		mG = new Graphics();
		surfaceHolder = this.getHolder();

		surfaceHolder.addCallback(this);

		if (!isInEditMode()) {
			this.setZOrderOnTop(true);
		}
		// this.setEGLConfigChooser(8, 8, 8, 8, 16, 0); 0Wm-` ZA
		this.getHolder().setFormat(PixelFormat.TRANSPARENT);
		mSetfil = new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG);
		currentAngle = 0;
		alarmState = ALARMSTATE_NOALARM;
		
		setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
				float x=event.getX();
				float y=event.getY();
				
				if(x>0&&x<width/2){
					if(y>0&&y<height/2){
						onButtonClick(0);
					}else if(y>=height/2&&y<height){
						onButtonClick(2);
					}
				}else if(x>=width/2&&x<width){
					if(y>0&&y<height/2){
						onButtonClick(1);
					}else if(y>=height/2&&y<height){
						onButtonClick(3);
					}
				}
				
				}
				return true;
			}
		});
	}

	@Override
	public void run() {
		MyLogger.e("boxGameView", "thread start");
		Canvas c = null;

		int dayFromZero = (int) (System.currentTimeMillis() / Symbol.INTERVAL_DAY);
		// DayChangeTimer.schedule(task, new
		// Date((dayFromZero+1)*Symbol.INTERVAL_DAY),Symbol.INTERVAL_DAY);

		while (mLoop) {
			synchronized (surfaceHolder) {

				updateLogic();
				c = surfaceHolder.lockCanvas();
				try {
					mG.setCanvas(c);
					if (true||!efficentMode || state != STATE_CHANGEING) {
						if(mG!=null)
						drawNewPage(mG);
					}
					switch (state) {
					case STATE_CHANGEING:
						// drawChanging(mG);
						if(mG!=null)
						drawDone(mG);

						break;
					case STATE_DONE:
						// drawChanging(mG);
						MyLogger.e("boxGameView", "thread drawDone");
						if(mG!=null)
						drawDone(mG);
						if(!isButtonAnimOver()){
							MyLogger.e("boxGameView", "thread over");
							state =STATE_CHANGEING ;
						}
						
						break;
					}
					surfaceHolder.unlockCanvasAndPost(c);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

				}

				if (state == STATE_DONE) {
					try {
						do {
							MyLogger.e("boxGameView", "thread wait");
							surfaceHolder.wait();
						} while (inialOver == null);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
			try {
				Thread.sleep(SLEEPTIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		MyLogger.e("boxGameView", "thread over");
	}

	private boolean isButtonAnimOver() {
		for(int i=0;i<4;i++){
			if (btnAnimSet[i] != null
					&& btnAnimSet[i].isRunning()) {
				return false;
			}
		}
		return true;
	} 

	private String translateTimeToString(Long time) {
		Calendar cltime = Calendar.getInstance();
		cltime.setTimeInMillis(time);

		time += (long) cltime.get(Calendar.ZONE_OFFSET);//
		Long currtentTime = System.currentTimeMillis()
				+ (long) cltime.get(Calendar.ZONE_OFFSET);
		int dayFromZero = (int) (currtentTime / Symbol.INTERVAL_DAY);
		MyLogger.e("aaaaaa", "time:" + time + ":" + time / Symbol.INTERVAL_DAY
				+ " current:" + currtentTime + ":" + currtentTime
				/ Symbol.INTERVAL_DAY);
		int day = (int) (time / Symbol.INTERVAL_DAY - dayFromZero);
		// if(day>0){
		String whatday = day == 0 ? "今天" : day == 1 ? "明天" : day == 2 ? "后天"
				: day + "天后";
		int hour = cltime.get(Calendar.HOUR_OF_DAY);
		int minute = cltime.get(Calendar.MINUTE);
		return whatday + " " + (hour > 9 ? hour : "0" + hour) + ":"
				+ (minute > 9 ? minute : "0" + minute);
		// }

	}

	private void drawTime(Graphics g) {
		// TODO Auto-generated method stub
		clipScreen();
		if (alarmState != ALARMSTATE_NOALARM) {
			Paint p = new Paint(mG.getmPaint());
			p.setTextSize(fontSizeLarge);
			mG.drawTextWithBorder("NEXT", p, width / 2, (int) (height * 0.45),
					0xff26a6d0, 0xff525151);
			p.setTextSize(fontSizeNormal);
			mG.drawSystemString(translateTimeToString(nextTime), 
					width / 2, (int) (height * 0.6), 0xffffffff);
			// mG.drawSystemString("NEXT", width / 2, (int) (height * 0.5),p);
			// mG.drawSystemString(translateTimeToString(nextTime), width / 2,
			// (int) (height * 0.6));
		} else {
			Paint p = new Paint(mG.getmPaint());
			p.setTextSize(fontSizeLarge);
			mG.drawTextWithBorder("没有闹钟",p, width / 2, (int) (height * 0.45),0xff26a6d0, 0xff525151);
			p.setTextSize(fontSizeNormal);
			mG.drawSystemString("点击圆环开启", 
					width / 2, (int) (height * 0.6), 0xffffffff);
		}
		// mG.drawBitmap(Bitmap_time, (int) (width / 2), (int) (height / 2),
		// mG.HCENTER | mG.VCENTER);

	}

	private void drawCircleAnim(Graphics mG2) {
		// TODO Auto-generated method stub
		int buf = stateLoop / cirAnimSpeed % I_cirAniCount;
		lastCircleAnimIndex = buf;
		if (!efficentMode || lastCircleAnimIndex != buf) {
			drawNewPage(mG);
			lastCircleAnimIndex = buf;
			clipScreen(ftoi(circleSize));
			mG.drawBitmap(Bitmap_cirAnim[stateLoop / cirAnimSpeed
					% I_cirAniCount], (width / 2), height / 2, mG.HCENTER
					| mG.VCENTER);

		}
		timeLoopPlus();
	}

	private void drawText(Graphics g) {
		// TODO Auto-generated method stub

		Path a = new Path();

		RectF ef = new RectF(width / 2 - wholeSize / 2, height / 2 - wholeSize
				/ 2, width / 2 + wholeSize / 2, height / 2 + wholeSize / 2);

		a.addArc(ef, ftoi((currentAngle - 90 - arcAngle / 2)), arcAngle);
		g.drawArcText("▲", a, 0, ftoi(width * SIZE_BORDER_CIRCILE / 2),
				textPaintMini);
	}

	private void updateLogic() {
		// TODO Auto-generated method stub
		// MyLogger.e("boxGameView", "updateLogic start");

		float angle;
		targetAngle = changeTOVailid(targetAngle);
		currentAngle = changeTOVailid(currentAngle);
		angle = targetAngle - currentAngle;
		// if(currentAngle<targetAngle)
		// if (angle < 5 && angle > -5) {
		// currentAngle = targetAngle;
		// state = STATE_DONE;
		// return;
		// }
		currentAngle += pspeed;
		if (angle <= pspeed_abs && angle > -pspeed_abs) {
			// currentAngle += pspeed;
			pspeed *= -0.6;
			vibarate_count++;
			// return;
			if (vibarate_count == vibarate_Max) {
				currentAngle = targetAngle;
				state = STATE_DONE;
				vibarate_count = 0;
				return;
			}
		} else {
			vibarate_count = 0;
		}

		// MyLogger.e("Current:" + currentAngle, "target:" + targetAngle);

	}

	private void drawNewPage(Graphics g) {
		// TODO Auto-generated method stub
		clipScreen(width);
		g.Clear();
		 g.drawBitmap2(Bitmap_cirBg, (width / 2), height / 2,width,height,mG.HCENTER
		 | mG.VCENTER);
	} 

	private void drawRotate() {
		// clipScreen();
		mG.drawRotate(pspeed, width / 2, height / 2, arcPaint);
	}

	private void drawDone(Graphics g) {
		drawTime(g);
		drawGame3(g);
		drawButton(g);
	}

	private void drawButton(Graphics g) {
		boolean isOver = true;
		for (int i = 0; i < 4; i++) {
			if (mButtonHolder[i] != null) {
				drawButtonBitmap(g, i, mButtonHolder[i].loc,mButtonHolder[i].light);
//				if((Math.abs(mButtonHolder[i].light-1)<0.000001||Math.abs(mButtonHolder[i].light-0)<0.000001)&&(Math.abs(mButtonHolder[i].loc-buttonMoveStart)<0.000001||Math.abs(mButtonHolder[i].loc-buttonMoveOver)<0.000001)){
//
//				}else{
//					isOver = false;
//				}
				
			} else {
				if(m_listBean.get(i).isEnabled()){
					drawButtonBitmap(g, i, buttonMoveOver ,1);
				}else{
					drawButtonBitmap(g, i,buttonMoveStart,0 );
				}
			}
		}
	}

	private void drawButtonBitmap(Graphics g, int index, float loc,float alpha) {
		int archor = 0;
		float xoffset = 0;
		float yoffset = 0;
		switch (index) {
		case 0: {
			archor = mG.RIGHT | mG.BOTTOM;
			xoffset = loc * -1;
			yoffset = loc * -1;
			break;
		}
		case 1: {
			archor = mG.LEFT | mG.BOTTOM;
			xoffset = loc;
			yoffset = loc * -1;
			break;

		}
		case 2: {
			archor = mG.RIGHT | mG.TOP;
			xoffset = loc * -1;
			yoffset = loc;
			break;
		}
		case 3: {
			archor = mG.LEFT | mG.TOP;
			xoffset = loc;
			yoffset = loc;
			break;
		}
		}
		if(Bitmap_cirButton!=null)
		g.drawBitmap2(Bitmap_cirButton[index], ftoi((width / 2f) + xoffset),
				ftoi(height / 2f + yoffset), ftoi(width / 2),
				ftoi( height / 2), archor);
		Paint p = new Paint(mG.getmPaint());
		p.setAlpha((int)(alpha*255));
		if(Bitmap_cirButtonLight!=null)
		g.drawBitmap2(Bitmap_cirButtonLight[index], ftoi((width / 2f) + xoffset),
				ftoi(height / 2f + yoffset), ftoi(width / 2),
				ftoi( height / 2), archor,p);
	}

	private void drawChanging(Graphics g) {
		// clipScreen();

		// drawRotate();
		// MyLogger.e("boxGameView", "drawChangeing");

		// drawCircleAnim(g);
		// drawText(g);
		// drawGame2(g);

		Path a = new Path();

		RectF ef = new RectF(width / 2 - circleSize / 2, height / 2
				- circleSize / 2, width / 2 + circleSize / 2, height / 2
				+ circleSize / 2);

		a.addArc(ef, 0, 360);
		g.drawArcText(
				"Εκδοχή που σερβίρεται στο άθροισμα του 6000 ηλεκτρονικό κωδικό επαναφόρτιση χρήματα, μπορείτε να επαναφορτίσετε την προ-πώληση την ημέρα του παιχνιδιού. Χτισμένο πάνω από 2GB ",
				a, (int) changeTOVailid(360 - currentAngle),
				ftoi(circleSize * 0.22f / 2), textPaintMini);
		g.drawCircle(width / 2, height / 2, circleSize / 2 * 0.75f, circlePaint);
		// g.drawBitmap2(Bitmap_cirBg, (width / 2), height / 2,ftoi(
		// circleSize),ftoi(circleSize),mG.HCENTER
		// | mG.VCENTER);

	}

	Xfermode mXfermode, mXfermode_buf;

	private void drawGame(Graphics g) {
		// TODO Auto-generated method stub

		clipScreen();
		arcPaint.setXfermode(mXfermode);
		mG.drawArcBySweep(width / 2, height / 2, ftoi(wholeSize - 1),
				ftoi(wholeSize - 1), 0, 360, arcPaint);

		clipScreen();
		int startAngle = ftoi((currentAngle - 90 - arcAngle / 2));
		if (state == STATE_DONE) {
			switch (alarmState) {
			case ALARMSTATE_NOALARM:
				arcPaint.setColor(Color.GRAY);
				break;
			case ALARMSTATE_SLEEPALARM:
				arcPaint.setColor(Color.RED);
				break;
			case ALARMSTATE_NORMAL:
				arcPaint.setColor(Color.RED | Color.GRAY);
				break;

			}
			// arcPaint.setColor(Color.GRAY);
			// //} else {
			// arcPaint.setColor(Color.RED | Color.GRAY);
		}
		arcPaint.setXfermode(mXfermode_buf);
		mG.drawArcBySweep(width / 2, height / 2, ftoi(wholeSize - 1),
				ftoi(wholeSize - 1), startAngle, arcAngle, arcPaint);

	}

	private void drawGame3(Graphics g) {
		// TODO Auto-generated method stub

		clipScreen();

		// arcPaint.setXfermode(mXfermode);
		// mG.drawArcBySweep(width / 2, height / 2, ftoi (wholeSize - 1),
		// ftoi (wholeSize - 1), 0, 360, arcPaint);
		//
		// clipScreen();
		// int startAngle = ftoi ((currentAngle - 90 - arcAngle / 2));
		// if (state == STATE_DONE) {
		// switch (alarmState) {
		// case ALARMSTATE_NOALARM:
		// arcPaint.setColor(Color.GRAY);
		// break;
		// case ALARMSTATE_SLEEPALARM:
		// arcPaint.setColor(Color.RED);
		// break;
		// case ALARMSTATE_NORMAL:
		// arcPaint.setColor(Color.RED | Color.GRAY);
		// break;
		//
		// }
		// // arcPaint.setColor(Color.GRAY);
		// // //} else {
		// // arcPaint.setColor(Color.RED | Color.GRAY);
		// }
		// arcPaint.setXfermode(mXfermode_buf);
		// mG.drawArcBySweep(width / 2, height / 2, (int) (wholeSize - 1),
		// (int) (wholeSize - 1), startAngle, arcAngle, arcPaint);
		
		/*			mG.drawRotate(currentAngle, width / 2, height / 2, arcPaint);
	mG.drawBitmap2(Bitmap_cirArrow, ftoi(width / 2), ftoi(height / 2
				- SIZE_INNER_CIRCLE * height / 2), ftoi(SIZE_INNER_CIRCLE
				* width / 8), ftoi(SIZE_INNER_CIRCLE * height / 5),
				mG.HCENTER | mG.VCENTER);*/

	}

	private void drawGame2(Graphics g) {
		// TODO Auto-generated method stub

		clipScreen();

		arcPaint.setXfermode(mXfermode);
		mG.drawArcBySweep(width / 2, height / 2, ftoi(wholeSize - 1),
				ftoi(wholeSize - 1), 0, 360, arcPaint);

		clipScreen();
		int startAngle = ftoi((currentAngle - 90 - arcAngle / 2));
		if (state == STATE_DONE) {
			switch (alarmState) {
			case ALARMSTATE_NOALARM:
				arcPaint.setColor(Color.GRAY);
				break;
			case ALARMSTATE_SLEEPALARM:
				arcPaint.setColor(Color.RED);
				break;
			case ALARMSTATE_NORMAL:
				arcPaint.setColor(Color.RED | Color.GRAY);
				break;

			}
			// arcPaint.setColor(Color.GRAY);
			// //} else {
			// arcPaint.setColor(Color.RED | Color.GRAY);
		}
		arcPaint.setXfermode(mXfermode_buf);
		mG.drawArcBySweep(width / 2, height / 2, (int) (wholeSize - 1),
				(int) (wholeSize - 1), startAngle, arcAngle, arcPaint);
		mG.drawRotate(currentAngle, width / 2, height / 2, arcPaint);
		mG.drawBitmap2(Bitmap_cirArrow, (width / 2), height / 2,
				ftoi(wholeSize), ftoi(wholeSize), mG.HCENTER | mG.VCENTER);

	}

	private float changeTOVailid(float targetAngle2) {
		while (targetAngle2 < 0) {
			targetAngle2 += 360;
		}
		while (targetAngle2 >= 360) {
			targetAngle2 -= 360;
		}
		return targetAngle2;

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		MyLogger.e("boxGameView", "surfaceChanged");
		this.width = width;
		this.height = height;
		
		

	}

	private int ftoi(float f) {
		return (int) (f + 0.5f);
	}

	Object Lock;
	private TimerTask task;
	protected int currentIndex = -2;
	protected Long nextTime;

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		MyLogger.e("boxGameView", "surfaceCreated");

		this.width = getWidth();
		this.height = getHeight();

		arcPaint = new Paint();
		arcPaint.setColor(color_alarm_special);
		arcPaint.setStyle(Paint.Style.STROKE);
		arcPaint.setStrokeWidth(width * SIZE_BORDER_CIRCILE);
		arcPaint.setAntiAlias(true);

		textPaintMiddle = new Paint();
		textPaintMiddle.setTextSize(width * SIZE_BORDER_CIRCILE / 2);
		textPaintMiddle.setTextAlign(Paint.Align.CENTER);
		textPaintMiddle.setColor(Color.WHITE);
		textPaintMiddle.setAntiAlias(true);

		textPaintMini = new Paint();
		textPaintMini.setTextSize(width * SIZE_BORDER_CIRCILE / 4);
		textPaintMini.setTextAlign(Paint.Align.CENTER);
		textPaintMini.setTypeface(Typeface.DEFAULT_BOLD);
		textPaintMini.setColor(Color.WHITE);
		textPaintMini.setAntiAlias(true);

		circlePaint = new Paint();
		circlePaint.setColor(Color.WHITE);
		circlePaint.setStyle(Paint.Style.STROKE);
		circlePaint.setStrokeWidth(2f);
		textPaintMini.setAntiAlias(true);

		mXfermode_buf = arcPaint.getXfermode();
		circleSize = (float) (height * SIZE_INNER_CIRCLE);
		innercircleSize = circleSize * 0.75f;
		fontSizeNormal = (float) (width * SIZE_INNER_CIRCLE / 8);
		fontSizeLarge = (fontSizeNormal * 1.2f);
		wholeSize = (width * (SIZE_INNER_CIRCLE + SIZE_BORDER_CIRCILE) * 1.00f);

		mG.setFont(Typeface.DEFAULT_BOLD, Style.FILL_AND_STROKE, Color.WHITE,
				fontSizeNormal);
		mXfermode = new PorterDuffXfermode(
				android.graphics.PorterDuff.Mode.CLEAR);

		DayChangeTimer = new Timer();
		startView();
		DayChangeTimer.schedule(task, (System.currentTimeMillis()
				/ Symbol.INTERVAL_DAY + 1)
				* Symbol.INTERVAL_DAY, Symbol.INTERVAL_DAY);
		
		
//		buttonMoveStart = 0;
//		buttonMoveOver=width/2*0.1f;
		

		iniData();
		loadResource();
		inialOver = new Object();
		startGame();
		
		buttonMoveStart = 0;
		buttonMoveOver=width/2*0.1f;
		getHandler().post(new Runnable() {
			
			@Override
			public void run() {
				if(m_listBean!=null){
					synchronized(surfaceHolder){
					surfaceHolder.notify();
					state = STATE_CHANGEING;
					setButtonState(-1);			
					}
				}
				
			}
		});

		
	}
	
	public void startView(){
		task = new TimerTask() {
			public void run() {
				onAlarmChange(currentIndex, nextTime,-1,m_listBean);
			}
		};
		
	}

	private void startGame() {
		if (t == null || t.getState() == Thread.State.TERMINATED) {
			t = new Thread(this);
			t.start();
		} else
			synchronized (surfaceHolder) {
				if (t.getState() == Thread.State.WAITING) {

					surfaceHolder.notify();
				}
			}

	}

	Thread t;

	/**
	 * 初始化一些固定的参数
	 * 
	 * */
	private void iniData() {
		// currentIndex = -1;
		mLoop = true;
		stateLoop = 0;
		failCount = 0;
		playCount = 0;
		SLEEPTIME = ftoi(200 * 5 / FPS);
		lastCircleAnimIndex = 0;
		arcAngle = 60;

	}

	private void loadResource() {
		/*
		 * b_right = Bitmap.createScaledBitmap(
		 * BitmapFactory.decodeResource(getResources(), R.drawable.lg), width,
		 * height, false); b_wrong = Bitmap.createScaledBitmap(
		 * BitmapFactory.decodeResource(getResources(), R.drawable.nr), width,
		 * height, false); b_win =
		 * Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
		 * getResources(), R.drawable.success), width, height, false); b_lose =
		 * Bitmap.createScaledBitmap(
		 * BitmapFactory.decodeResource(getResources(), R.drawable.fail), width,
		 * height, false); b_wait = Bitmap.createScaledBitmap(
		 * BitmapFactory.decodeResource(getResources(), R.drawable.fail), width,
		 * height, false);
		 */

		// Bitmap_cirAnim = new Bitmap[2];
		// I_cirAniCount = Bitmap_cirAnim.length;
		// cirAnimFps = 5;
		// cirAnimSpeed = FPS / cirAnimFps;

		/*
		 * Bitmap_cirAnim[0] = Bitmap.createScaledBitmap(BitmapFactory
		 * .decodeResource(getResources(), R.drawable.ciranim1), ftoi
		 * (circleSize), ftoi (circleSize), false); Bitmap_cirAnim[1] =
		 * Bitmap.createScaledBitmap(BitmapFactory
		 * .decodeResource(getResources(), R.drawable.ciranim2), ftoi
		 * (circleSize), ftoi (circleSize), false);
		 */
		// Bitmap_cirBg= Bitmap.createBitmap(BitmapFactory
		// .decodeResource(getResources(), R.drawable.innercircle_h));

		// 2014.4.29 去除魔法阵
		/*
		 * Bitmap pm = Bitmap.createBitmap(BitmapFactory
		 * .decodeResource(getResources(), R.drawable.circlearrow));
		 * Bitmap_cirArrow=Bitmap.createBitmap(pm.getWidth(), pm.getHeight(),
		 * Bitmap.Config.ARGB_4444); int[] colors=new
		 * int[pm.getWidth()];//取一行图片的像素点 for (int i = 0; i < pm.getHeight();
		 * i++) { // 将位图bm的第i行的像素放入到数组 colors中去 pm.getPixels(colors, 0,
		 * pm.getWidth(), 0, i, pm.getWidth(), 1); for (int j = 0; j <
		 * pm.getHeight(); j++) { // 将颜色数组中的RGB值取反，255减去当前颜色值就获得当前颜色的反色
		 * colors[j] = Color.argb(Color.alpha(colors[j]),255 -
		 * Color.red(colors[j]), 255 - Color.green(colors[j]), 255 -
		 * Color.blue(colors[j])); } Bitmap_cirArrow.setPixels(colors, 0,
		 * pm.getWidth(), 0, i, pm.getWidth(), 1);// 颜色取反后，将像素加入到pm的第i行中去 }
		 * pm.recycle();
		 */
		openBtnAnimator = new ValueAnimator[4]; 
		lightBtnAnimator = new ValueAnimator[4];
		btnAnimSet =  new AnimatorSet[4];
		for (int i = 0; i < 4; i++) {
			if(mButtonHolder[i]==null)
			mButtonHolder[i] = new AnimHolder(0f,0f);
		}
		Bitmap_cirArrow = Bitmap.createBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.arrow));
		
		Bitmap_cirBg = Bitmap.createBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.bg_center));

		
		int w = ftoi(width / 2);
		int h = ftoi(height / 2);
		Bitmap_cirButton = new Bitmap[4];
		MyLogger.e("bitmap", "center" ,true);
		Bitmap_cirButton[0] = BitmapWorker.getBitmap(getContext(), R.drawable.pgcirtl,w,h) ;
		Bitmap_cirButton[1] = BitmapWorker.getBitmap(getContext(), R.drawable.pgcirtr,w,h) ;
		Bitmap_cirButton[2] = BitmapWorker.getBitmap(getContext(), R.drawable.pgcirbl,w,h) ;
		Bitmap_cirButton[3] = BitmapWorker.getBitmap(getContext(), R.drawable.pgcirbr,w,h) ;
		
		Bitmap_cirButtonLight = new Bitmap[4];
		Bitmap_cirButtonLight[0] = BitmapWorker.getBitmap(getContext(), R.drawable.pgcirtlx2,w,h) ;
		Bitmap_cirButtonLight[1] = BitmapWorker.getBitmap(getContext(), R.drawable.pgcirtrx2,w,h) ;
		Bitmap_cirButtonLight[2] = BitmapWorker.getBitmap(getContext(), R.drawable.pgcirblx2,w,h) ;
		Bitmap_cirButtonLight[3] = BitmapWorker.getBitmap(getContext(), R.drawable.pgcirbrx2,w,h) ;
		/*
		 * Bitmap_time = Bitmap
		 * .createScaledBitmap(BitmapFactory.decodeResource( getResources(),
		 * R.drawable.circle), ftoi (circleSize), ftoi (circleSize), false);
		 */

	}

	private void timeLoopPlus() {
		stateLoop++;
	}

	/**
	 * 锁定整个能显示
	 * 
	 * */
	private void clipScreen() {
		mG.clipCircle(width / 2, height / 2, ftoi(wholeSize / 2));
	}

	private void clipScreen(float size) {
		if(mG!=null)
		mG.clipRect(0, 0, ftoi(size ), ftoi(size ));
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mLoop = false;
		DayChangeTimer.cancel();
		inialOver = null;
		MyLogger.e("aaaa", "surfaceDestroyed");

//		recyleBitmap(Bitmap_time);
//		for (int i = 0; i < Bitmap_cirAnim.length; i++) {
//			recyleBitmap(Bitmap_cirAnim[i]);
//		}
//		recyleBitmap(Bitmap_cirArrow);
//		recyleBitmap(Bitmap_cirBg);
//		recyleBitmap(b_right);
//		recyleBitmap(b_wrong);
//		recyleBitmap(b_win);
//		recyleBitmap(b_lose);
//		recyleBitmap(b_wait);
		BitmapWorker.recyleBitmap(Bitmap_cirBg);
		BitmapWorker.recyleBitmap(Bitmap_cirArrow);
		
		BitmapWorker.recyleBitmap(Bitmap_cirButton);
		BitmapWorker.recyleBitmap(Bitmap_cirButtonLight);
		
		Bitmap_cirBg=null;
		Bitmap_cirArrow=null;
		Bitmap_cirButton=null;
		Bitmap_cirButtonLight=null;
		// surfaceHolder=null;
		arcPaint = null;
		textPaintMiddle = null;
		textPaintMini = null;
	}

	

	float fontSizeNormal, fontSizeLarge;// 字体的大小
	float wholeSize;
	float circleSize;// 第二圈
	float innercircleSize;// 第一圈

	int failCount;// 失败次数
	int playCount;// 游戏次数

	int stateLoop;
	int state;

	int currentColor;
	int textColor;
	int currentMode;

	private static int FPS = 30;
	private static final float default_FPS = 5;
	public static final int STATE_CHANGEING = 0;
	public static final int STATE_DONE = 1;

	private float currentAngle;
	private float targetAngle;

	public static float speed = 270;
	float pspeed;
	int arcAngle;

	private static final int COUNT_MAX_GAMELOOP = 4000;
	public static final int COUNT_MAX_RIGHTLOOP = 1000;
	public static final int COUNT_MAX_WRONGLOOP = 1000;
	private static final int COUNT_MAX_STARTLOOP = 100;
	private static final int ALARMSTATE_SLEEPALARM = 0;
	private static final int ALARMSTATE_NOALARM = 1;
	private static final int ALARMSTATE_NORMAL = 2;

	private static float SIZE_WHOLE = 1.0f;
	private static float SIZE_BORDER_CIRCILE = 0.5f;
	private static float SIZE_INNER_CIRCLE = 0.5f;

	Bitmap b_right, b_wrong, b_win, b_lose, b_wait;
	int alarmState;

	private boolean hasAvialableAlarm() {
		if (targetAngle == 0) {
			return false;
		}
		return true;
	}

	List<AlarmBean> m_listBean;

	@Override
	public void onAlarmChange(int index, Long time,int  buttonIndex,List<AlarmBean> m_listBean) {
		// TODO Auto-generated method stub
		MyLogger.e("boxGameView", "onAlarmChange");
		synchronized (surfaceHolder) {
			this.m_listBean = m_listBean;
			surfaceHolder.notify();
			nextTime = time;
			

			if(m_listBean!=null&&openBtnAnimator!=null&&inialOver!=null){
				state = STATE_CHANGEING;
				setButtonState(buttonIndex);
				
			}
			
			if (index == currentIndex) {
				// state = STAET_DONE;
				
				return;
			}
			currentIndex = index;

			if (index > 3) {
				state = STATE_CHANGEING;
			}
			switch (index) {
			case 0:
				index = 3;
				break;
			case 1:
				index = 0;
				break;
			case 3:
				index = 1;
				break;
			case 4:
				index = 2;
				break;
			}

			if (index > AlarmProvider.MAX_ALARM_COUNT - 1) {
				targetAngle = 0;
				alarmState = ALARMSTATE_SLEEPALARM;
			} else if (index == -1) {
				targetAngle = 0;
				alarmState = ALARMSTATE_NOALARM;
			} else {
				targetAngle = 45 + 90 * index;
				alarmState = ALARMSTATE_NORMAL;
			}
			float angle = changeTOVailid(targetAngle)
					- changeTOVailid(ftoi(currentAngle));
			if (ftoi(angle) == 0) {
				state = STATE_DONE;
				return;
			}
			if (angle > 180)
				targetAngle -= 360;
			else if (angle < -180)
				currentAngle -= 360;

			pspeed = (speed) / FPS * (targetAngle > currentAngle ? 1 : -1);
			pspeed_abs = Math.abs(pspeed);
			state = STATE_CHANGEING;

			targetAngle = changeTOVailid(targetAngle);
			currentAngle = changeTOVailid(ftoi(currentAngle));

			
		}

	}

	void dropDownAnim(int index, float loc,float light,boolean isLightFirst) {
		MyLogger.e("boxGameView", "dropDownAnim "+index+" "+loc);
		if( mButtonHolder[index].getLoc()==loc) return;
		if (btnAnimSet[index] != null
				&& btnAnimSet[index].isRunning()) {
			btnAnimSet[index].cancel();
		}
		 
		btnAnimSet[index] = new AnimatorSet();
		openBtnAnimator[index] = ObjectAnimator.ofFloat(mButtonHolder[index],
				"loc", mButtonHolder[index].getLoc(), loc);
		openBtnAnimator[index].setDuration(500); 
		openBtnAnimator[index].setInterpolator(new LinearInterpolator());
		
		lightBtnAnimator[index] = ObjectAnimator.ofFloat(mButtonHolder[index],
				"light", mButtonHolder[index].getLight(), light);
		lightBtnAnimator[index].setDuration(200);
		lightBtnAnimator[index].setInterpolator(new LinearInterpolator());
		if(!isLightFirst){
			btnAnimSet[index].play(lightBtnAnimator[index]).after(openBtnAnimator[index]);
		}else{
			btnAnimSet[index].play(openBtnAnimator[index]).after(lightBtnAnimator[index]);
		}
		btnAnimSet[index] .start();
		
	}
	
	void openLightAnim(int index, float loc) {
		MyLogger.e("boxGameView", "dropDownAnim "+index+" "+loc);
		
		if (openBtnAnimator[index] != null
				&& openBtnAnimator[index].isRunning()) {
			openBtnAnimator[index].cancel();
		}
		if( mButtonHolder[index].getLoc()==loc) return;
		openBtnAnimator[index] = ObjectAnimator.ofFloat(mButtonHolder[index],
				"weight", mButtonHolder[index].getLoc(), loc);
		openBtnAnimator[index].setDuration(500);
		openBtnAnimator[index].setInterpolator(new LinearInterpolator());
		openBtnAnimator[index].addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				

			}
		});
		openBtnAnimator[index].start();
	}

	class AnimHolder {
		public AnimHolder(float loc,float light) {
			super();
			this.loc = loc;
			this.light=light;
		}

		float loc;
		float light;

		public float getLight() {
			return light;
		}

		public void setLight(float light) {
			this.light = light;
		}

		public float getLoc() {
			return loc;
		}

		public void setLoc(float loc) {
			this.loc = loc;
		}
	}

	private void setButtonState(int index) {
		if(index==-1){
		for (int i = 0; i < 4; i++) {
			dropDownAnim(i, m_listBean.get(i).getEnabled()==true?buttonMoveOver:buttonMoveStart, m_listBean.get(i).getEnabled()?1:0,m_listBean.get(i).getEnabled());
		}
		}else{
			dropDownAnim(index, m_listBean.get(index).getEnabled()==true?buttonMoveOver:buttonMoveStart, m_listBean.get(index).getEnabled()?1:0,m_listBean.get(index).getEnabled());

		}
		
	}
	
	

	private void onButtonClick(int i) {
		if(mAlarmButtonClickListener!=null){
			mAlarmButtonClickListener.onClick(i);
		}
		
//		synchronized (ALPHA) {
//			AlarmBean mAb= m_listBean.get(i);
//			if(mAb.getEnabled()==true){
//				mAb.setEnabled(false);
//			//	onAlarmChange(i, time, m_listBean)
//			//	dropDownAnim(i,buttonMoveStart);
//			}else{
//				mAb.setEnabled(true);
//			//	dropDownAnim(i,buttonMoveOver);
//			}
//			
//		}
		
	}
	AlarmButtonClickListener mAlarmButtonClickListener;
	public void setOnButtonClickListener(AlarmButtonClickListener l){
		mAlarmButtonClickListener = l;	
	}
	
	interface AlarmButtonClickListener{
		public void onClick(int index);
	}
}
