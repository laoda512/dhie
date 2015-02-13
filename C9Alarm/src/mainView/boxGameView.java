package mainView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.R;

public class boxGameView extends SurfaceView implements SurfaceHolder.Callback,
		Runnable, ClickCallback {

	private int SLEEPTIME = 0;
	int width, height;
	SurfaceHolder mSurfaceHolder;
	Boolean mLoop;
	Paint arcPaint;
	private Graphics mG = new Graphics();

	public boxGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		MyLogger.e("boxGameView", "boxGameView");
		mSurfaceHolder = this.getHolder();

		mSurfaceHolder.addCallback(this);

		this.setZOrderOnTop(true);
		// this.setEGLConfigChooser(8, 8, 8, 8, 16, 0); 0Wm-` ZA
		this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		
		this.setOnTouchListener(new OnTouchListener(){
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				synchronized(arcPaint){
				if(state==STATE_WIN||state==STATE_LOSE){
					surfaceDestroyed(mSurfaceHolder);
					try {
						
						arcPaint.wait();
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					iniData();
					startGame();
					
				}else if(state==STATE_WAIT){
					iniData();
					
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
		

		while (mLoop) {
			synchronized (mSurfaceHolder) {
				updateLogic();
				c = mSurfaceHolder.lockCanvas();

				try {
					mG.setCanvas(c);
					drawNewPage(mG);
					switch (state) {
					case STATE_CLICK_WRONG:
						drawClickWrong(mG);
						;
						break;
					case STATE_CLICK_RIGHT:
						drawClickRight(mG);
						;
						break;
					case STATE_START:
						drawStart(mG);
						;
						break;
					case STATE_LOSE:
						drawLose(mG);
						//MyLogger.e("aaaaaaaa", "aaaa");
						;
						break;
					case STATE_WIN:
						drawWin(mG);
						;
						break;
					case STATE_GAME:
						drawGame(mG);
						
						;
						break;
					case STATE_PAUSE:
						drawPause(mG);
						;break;
					case STATE_RESUME:
						drawResume(mG);
						;break;	
					case STATE_WAIT:
						drawWait(mG);
						;break;	
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					mSurfaceHolder.unlockCanvasAndPost(c);
				}
			}
			try {
				Thread.sleep(SLEEPTIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		synchronized (arcPaint){
			arcPaint.notify();
		}
		MyLogger.e("boxGameView", "thread over");
	}

	

	private void updateLogic() {
		// TODO Auto-generated method stub
		MyLogger.e("boxGameView", "updateLogic start");
		switch (state) {
		case STATE_CLICK_RIGHT:
			if (stateLoop >= COUNT_MAX_RIGHTLOOP) {
				stateLoop = 0;
				state = STATE_GAME;
				updateLogic();

			}
			break;
		case STATE_CLICK_WRONG:
			if (stateLoop >= COUNT_MAX_WRONGLOOP) {
				stateLoop = COUNT_MAX_GAMELOOP;
				state = STATE_GAME;
				updateLogic();

			}
			break;

		case STATE_START: {
			if (stateLoop >= COUNT_MAX_STARTLOOP && ncb != null) {
				stateLoop = 0;
				state = STATE_GAME;
				updateLogic();

			}
			break;
		}
		case STATE_GAME: {
			if (stateLoop == COUNT_MAX_GAMELOOP) {
				stateLoop = 0;
				failCount++;
				playCount++;
				if (failCount >= COUNT_MAX_LOSECOUNT) {
					state = STATE_LOSE;
					break;
				}
			} 
			if (stateLoop == 0) {
				if ((playCount )== COUNT_MAX_GAMECOUNT) {
					state = STATE_WIN;
					break;

				} else {
					ncb.onNewRound();
					currentColor = (int) (Math.random() * COLOR_COUNT);
					//change mode
					currentMode = Math.random()*(playCount-failCount)/COUNT_MAX_GAMECOUNT<0.15?MODE_COLOR:MODE_WORD;
					//change back color
					
					if(currentMode==MODE_WORD){
						textColor = getRandomColor();
					}
					playCount++;
					MyLogger.e("playCount", "" + playCount);
				}
			}
			break;
		}
		case STATE_LOSE:
			ncb.gameOver(false);
			break;
		case STATE_WIN:
			ncb.gameOver(true);
			break;
		}
		MyLogger.e("boxGameView", "updateLogic over");
	}
	private void drawWait(Graphics mG2) {
		// TODO Auto-generated method stub
		clipScreen();
		mG.drawBitmap(b_right, width / 2, height / 2, mG.HCENTER | mG.VCENTER);
	}

	private void drawPause(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	private void drawResume(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	private void drawNewPage(Graphics g) {
		// TODO Auto-generated method stub
		clipScreen();
		mG.drawColor(Color.WHITE);
	}

	private void drawClickRight(Graphics mG2) {
		// TODO Auto-generated method stub
		clipScreen();
		mG.drawBitmap(b_right, width / 2, height / 2, mG.HCENTER | mG.VCENTER);
		timeLoopPlus();
	}
	private void timeLoopPlus(){
		stateLoop+=SLEEPTIME;
	}

	private void drawClickWrong(Graphics mG2) {
		// TODO Auto-generated method stub
		clipScreen();
		mG.drawBitmap(b_wrong, width / 2, height / 2, mG.HCENTER | mG.VCENTER);
		timeLoopPlus();
	}

	private void drawWin(Graphics g) {
		// TODO Auto-generated method stub
		clipScreen();
		mG.drawBitmap(b_win, width / 2, height / 2, mG.HCENTER | mG.VCENTER);
		timeLoopPlus();
	}

	private void drawLose(Graphics g) {
		// TODO Auto-generated method stub
		clipScreen();
		mG.drawBitmap(b_lose, width / 2, height / 2, mG.HCENTER | mG.VCENTER);
		timeLoopPlus();
	}

	private void drawStart(Graphics g) {
		// TODO Auto-generated method stub

		timeLoopPlus();
	}

	private void drawGame(Graphics g) {
		// TODO Auto-generated method stub
		int color = 0;
		switch (currentColor) {
		case COLOR_BLUE:
			color = Color.BLUE;
			break;
		case COLOR_RED:
			color = Color.RED;
			break;
		case COLOR_YELLOW:
			color = Color.YELLOW;
			break;
		case COLOR_DARK:
			color = Color.DKGRAY;
			break;
		}
		switch (currentMode) {
		case MODE_COLOR:
			mG.setColor(color);
			mG.drawCircle((float) (width / 2), (float) (height / 2),
					circleSize/2,null);
			break;
		case MODE_WORD:
			mG.setColor(textColor);
			mG.drawSystemString(getColorText(currentColor),  width/2,ftio (fontSize/2+height/2));
			break;
		}
		 clipScreen();
		arcPaint.setColor(Color.CYAN);
		mG.drawArc(width / 2, height / 2, ftio (wholeSize),
				ftio (wholeSize), 0,  (stateLoop* 360/COUNT_MAX_GAMELOOP ), arcPaint);
		arcPaint.setColor(Color.YELLOW);
		clipScreen();
		mG.drawArc(width / 2, height / 2, ftio (wholeSize),
				ftio (wholeSize),  (stateLoop* 360/COUNT_MAX_GAMELOOP ), 360, arcPaint);

		timeLoopPlus();

	}
	
	private int ftio(float f){
		return (int)(f+0.5f);
	}

	 
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		MyLogger.e("boxGameView", "surfaceChanged");
		this.width = width;
		this.height = height;

	}
	private int getRandomColor(){
		int color = Color.GREEN;
		int currentColor=(int) (Math.random()*COLOR_COUNT);
		switch (currentColor) {
		case COLOR_BLUE:
			color = Color.BLUE;
			break;
		case COLOR_RED:
			color = Color.RED;
			break;
		case COLOR_YELLOW:
			color = Color.YELLOW;
			break;
		case COLOR_DARK:
			color = Color.DKGRAY;
			break;
		}
		return color;
	}
	
	private String getColorText(int color){
		switch (color) {
		case COLOR_BLUE:
			return "蓝";
		case COLOR_RED:
			return "红";
		case COLOR_YELLOW:
			return "黄";
		case COLOR_DARK:
			return "黑";
		}
		return "fk";
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		MyLogger.e("boxGameView", "surfaceCreated");
		this.width = getWidth();
		this.height = getHeight();

		arcPaint = new Paint();
		arcPaint.setColor(Color.CYAN);
		arcPaint.setStyle(Paint.Style.STROKE);
		arcPaint.setStrokeWidth(width * SIZE_BORDER_CIRCILE);
		arcPaint.setAntiAlias(true);
		
		circleSize = (float) (height*SIZE_INNER_CIRCLE);
		fontSize = (float) (0.7*circleSize);
		wholeSize = (width*(SIZE_INNER_CIRCLE+SIZE_BORDER_CIRCILE));
		
		mG.setFont(Typeface.DEFAULT_BOLD, Style.FILL_AND_STROKE,0,fontSize);
		
		
		iniData();
		loadResource();
		state = STATE_WAIT;
		startGame();
		
	}
	private void startGame(){
		t=new Thread(this);
		t.start();
	}
Thread t;
	private void iniData() {
		state = STATE_START;
		mLoop = true;
		stateLoop = 0;
		failCount = 0;
		playCount = 0;
		SLEEPTIME= ftio (200*5/FPS);
	}

	private void loadResource() {
		b_right = Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.nr),
				width, height, false);
		b_wrong = Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.nr),
				width, height, false);
		b_win = Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.success),
				width, height, false);
		b_lose = Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), 0),
				width, height, false);
		b_wait = Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(),0),
				width, height, false);

	}

	/**
	 * 锁定整个能显示
	 * 
	 * */
	private void clipScreen() {
		mG.clipCircle(width / 2, height / 2, ftio ((wholeSize)/2));
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mLoop = false;
	}

	float fontSize;//字体的大小
	float wholeSize;
	float circleSize;
	
	int failCount;//失败次数
	int playCount;//游戏次数
	

	int stateLoop;
	int state;

	int currentColor;
	int textColor;
	int currentMode;

	private static int FPS = 10;
	private static final float default_FPS = 5;
	public static final int STATE_START = 0;
	public static final int STATE_LOSE = 1;
	public static final int STATE_WIN = 2;
	public static final int STATE_GAME = 3;
	public static final int STATE_CLICK_RIGHT = 4;
	public static final int STATE_CLICK_WRONG = 5;
	public static final int STATE_PAUSE = 6;
	public static final int STATE_RESUME = 7;
	public static final int STATE_WAIT = 8;

	public static final int MODE_COLOR = 0;
	public static final int MODE_WORD = 1;

	public static final int COLOR_COUNT = 4;
	public static final int COLOR_BLUE = 0;
	public static final int COLOR_RED = 1;
	public static final int COLOR_YELLOW = 2;
	public static final int COLOR_DARK = 3;

	private static final int COUNT_MAX_GAMELOOP = 4000;
	public static final int COUNT_MAX_RIGHTLOOP = 1000;
	public static final int COUNT_MAX_WRONGLOOP = 1000;
	private static final int COUNT_MAX_STARTLOOP = 100;
	
	private static final int COUNT_MAX_GAMECOUNT = 26;// 总数
	private static final int COUNT_MAX_LOSECOUNT = 1;// 可以失败的次数
	

	

	private static float SIZE_WHOLE = 1.0f;
	private static float SIZE_BORDER_CIRCILE = 0.1f;
	private static float SIZE_INNER_CIRCLE = 0.7f;
	
	
	Bitmap b_right, b_wrong,b_win,b_lose,b_wait;
	
	

	private NewRoundCallback ncb;

	@Override
	public void onKeyCLick(int keyCode) {
		// TODO Auto-generated method stub
		if (state == STATE_GAME) {
			if (keyCode == currentColor) {
				clickRight();
			} else {
				clickWrong();
			}
		}
	}

	private void clickRight() {
		synchronized (mSurfaceHolder) {
			stateLoop = 0;
			state = STATE_CLICK_RIGHT;
			MyLogger.e("clickright", "");
		}
	}

	private void clickWrong() {
		synchronized (mSurfaceHolder) {
			stateLoop = 0;
			state = STATE_CLICK_WRONG;
			MyLogger.e("clickwrong", "");
		}
	}
	private void gamePause() {
		synchronized (mSurfaceHolder) {
			stateLoop = 0;
			state = STATE_CLICK_WRONG;
			MyLogger.e("clickwrong", "");
		}
	}

	public void setNewRoundCallBack(NewRoundCallback ncb) {
		this.ncb = ncb;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
		state = STATE_PAUSE;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		if(state == STATE_PAUSE)
		state = STATE_RESUME;
	}
	
	

}
