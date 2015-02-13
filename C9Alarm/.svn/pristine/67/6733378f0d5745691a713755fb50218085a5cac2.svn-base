package com.tavx.C9Alarm;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mainView.boxGame;
import util.AnimationsContainer.FramesSequenceAnimation;
import util.SDResReadManager;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.NinePatch;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.appscumen.example.MySwitch;
import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.Form.AlarmType;
import com.tavx.C9Alarm.Form.AlarmTypeMovie;
import com.tavx.C9Alarm.Form.Form;
import com.tavx.C9Alarm.Fragment.AlarmAlertWakeFragment;
import com.tavx.C9Alarm.Manager.SleepManager;
import com.tavx.C9Alarm.Manager.SleepManager.AlarmShower;
import com.tavx.C9Alarm.View.AlarmAwakeInterface;

public class AlarmReceiveActivityVideo extends BaseActivity {
	Context c;

	// AlarmBean alm;
	FramesSequenceAnimation anim;
	FramesSequenceAnimation animBg;
	int index;
	Intent nextIntent;
	TextView tvTopMessage;
	ImageView ib_anim;
	ImageView v_alarmBackground;
	NotificationManager nfManger;
	Notification mNotification;
	Thread autoOff;
	AlarmBean ab;
	boolean isOver = false;
	AnimationDrawable fm;
	MySwitch ms;

	Drawable slideThumb;
	Drawable slideTrack;
	Drawable slideMask;
	Drawable slideLeft;

	Drawable slideRight;

	int sleepDelay = 0;

	private View mainFrame;
	private static AlarmReceiveActivityVideo mAlarmReceiveActivity;
	View vAlarm;
	Dialog ad;
	TextView tvHint;
	Form mForm;

	private static String testFormName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLogger.e(TAG, "onCreate");
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		   final Window win = getWindow();
	        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
	                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
	                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
	                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
	                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);

		//getWindow().setAttributes(lp);

		Intent i = getIntent();
		boolean isShowShare = i.getBooleanExtra("isShowShare", false);
		boolean isAlarmTest = i.getBooleanExtra("isAlarmTest", false);
		int alarm_pos_index = i.getIntExtra("alarm_pos_index", 0);

		iniData(isShowShare, isAlarmTest, alarm_pos_index);
		SleepManager.getInstance().startNewAlarm(mAlarmShower);
		showAlarm(this, isShowShare, isAlarmTest, alarm_pos_index);

	}

	private void iniData(final boolean isShowShare, boolean isAlarmTest,
			int alarm_pos_index) {
		isTest = isAlarmTest;
		index = alarm_pos_index;

		if (isTest == true) {
			ab = new AlarmBean();
			ab.setForm(testFormName);
			ab.setEnabled(true);
			ab.setEnableGame(false);
			ab.setIndex(-1);

			mForm = Form.getForm(testFormName);
		} else {
			ab = AlarmProvider.geInstance(getContext()).getAlarm(index);
			mForm = Form.getForm(ab.getFormIndex());
		}

		AlarmType alarmType = mForm.getAlarmTypeBean();
		if (alarmType != null && alarmType instanceof AlarmTypeMovie) {
			mAlarmType = (AlarmTypeMovie) alarmType;
			setRequestedOrientation(mAlarmType.getAlarm_orientation() == mAlarmType.Orientation_horizonal ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
					: ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		mAlarmShower = new AlarmShower() {

			@Override
			public void closeAlarm() {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						MyLogger.e(TAG, "finish by mAlarmShower");
						AlarmReceiveActivityVideo.this.finish();

					}
				});

			}

			@Override
			public AlarmBean getAlarmBean() {
				return ab;
			}

			@Override
			public Form getForm() {
				return mForm;
			}

			@Override
			public boolean isTest() {
				return isTest;
			}

			@Override
			public boolean isShowShare() {
				return isShowShare;
			}
		};
	}

	AlarmShower mAlarmShower;

	public static synchronized void startAlarm(Context c, boolean isShowShare,
			boolean isAlarmTest, int alarm_pos_index) {

		AlarmBean ab1 = AlarmProvider.geInstance(AlarmApplication.getApp())
				.getAlarm(alarm_pos_index);
		Form form = Form.getForm(ab1.getFormIndex());

		if (form.getAlarmTypeBean().getType() == AlarmType.TYPE_ANIM) {
			AlarmReceiveActivity.startAlarm(isShowShare, isAlarmTest,
					alarm_pos_index);
			return;
		}

		Intent i = new Intent();
		i.setClass(c, AlarmReceiveActivityVideo.class);
		i.putExtra("isShowShare", isShowShare);
		i.putExtra("isAlarmTest", isAlarmTest);
		i.putExtra("alarm_pos_index", alarm_pos_index);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		c.startActivity(i);
	}

	public static synchronized void startAlarmActivity(Context c,
			boolean isShowShare, boolean isAlarmTest, String formId, int type) {
		if (type == AlarmType.TYPE_ANIM) {
			AlarmReceiveActivity.startAlarm(isShowShare, isAlarmTest, formId);
			return;
		}

		Intent i = new Intent();
		i.setClass(c, AlarmReceiveActivityVideo.class);
		i.putExtra("isShowShare", isShowShare);
		i.putExtra("isAlarmTest", isAlarmTest);
		testFormName = formId;
		c.startActivity(i);
	}

	Handler mHandler = new Handler();
	private TextView tvTopHint;

	VideoView videoView;

	private Button back;

	private Button rePlay;

	private View controlPad;

	private Timer t;

	private AlarmAlertWakeFragment mAlarmAlertWakeFragment;

	private AlarmTypeMovie mAlarmType;

	private void showAlarm(final Context c, boolean isShowShare,
			boolean isAlarmTest, int alarm_pos_index) {
		MyLogger.e(TAG, "showAlarm");
		isOver = false;
		this.isShowShare = isShowShare;
		this.c = c;
		iniView(isShowShare, isAlarmTest, alarm_pos_index);

	}

	private Context getContext() {
		if (c != null)
			return c;
		else
			return AlarmApplication.getApp();
	}

	private void iniView(boolean isShowShare, boolean isAlarmTest,
			int alarm_pos_index) {
		MyLogger.e(TAG, "iniView");
		setContentView(R.layout.alert_alarm_video);
		inibuttonPad();
		back = (Button) this.findViewById(R.id.back);
		rePlay = (Button) this.findViewById(R.id.replay);
		videoView = (VideoView) this.findViewById(R.id.videoView1);

		controlPad = this.findViewById(R.id.controlPad);
		controlPad.setVisibility(View.GONE);
		// closeControlPad();
		videoView.setZOrderOnTop(false);
		// Uri uri = Uri.parse("android.resource://" + c.getPackageName() + "/"
		// +R.raw.x);

		String path;

		path = (String) SDResReadManager.getInstance().getResPathWithName(
				mAlarmType.getAlarm_mad(), mForm.getTitleName());

		Media.getInstance(AlarmReceiveActivityVideo.this).setVideoView(
				videoView, path, AlarmApplication.getApp().getIsMusic(),
				AlarmApplication.getApp().getIsVibrate(),
				AlarmApplication.getApp().getIsSlowLoud());
		// videoView.setOnCompletionListener(new OnCompletionListener() {
		//
		// @Override
		// public void onCompletion(MediaPlayer mp) {
		// videoView.setVideoPath(path);
		// videoView.start();
		// }
		// });
		// videoView.requestFocus();
		videoView.start();
		sleepDelay = AlarmApplication.getApp().getUser().getSleepTime();
		if (sleepDelay > 0) {
			// tvTopMessage.setText("双击下方再睡" + sleepDelay + "分钟");
		} else {
			// tvTopMessage.setText("贪睡模式未开启");
		}

		// videoView.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// if (event.getAction() == MotionEvent.ACTION_UP) {
		// if (controlPad.getVisibility() == View.VISIBLE) {
		// closeControlPad();
		// } else {
		// openControlPad();
		// }
		// }
		// return true;
		// }
		// });

//		back.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View view) {
//				onSlideOver();
//
//			}
//		});
//		rePlay.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View view) {
//				onCancelWakeUp();
//			}
//		});

		if (isShowShare == false) {
			testAlarm();
			return;
		}
		MyLogger.e("AlarmReceiver", "ffffff");

		// startAutoOff();

		// 添加或生成一次新的计时
		MyLogger.e("AlarmReceiver", "xxxxxx");

	}

	private void inibuttonPad() {
		FragmentManager fragmentManager = super.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		mAlarmAlertWakeFragment = AlarmAlertWakeFragment.newInstance("");
		mAlarmAlertWakeFragment
				.setAlarmAwakeInterface(new AlarmAwakeInterface() {

					@Override
					public void onSnooze() {
						SleepManager.getInstance()
								.endAwake(SleepManager.SNOOZE);
						// onSlideOver();
					}

					@Override
					public void onAwake() {
						SleepManager.getInstance().endAwake(SleepManager.AWAKE);
						// onCancelWakeUp();
					}
				});
		fragmentTransaction.add(R.id.fragPad, mAlarmAlertWakeFragment);
		fragmentTransaction.commit();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStop()
	 */
	@Override
	protected void onStop() {
		MyLogger.e(TAG, "onStop");
		if(!CheckIsCurrentTask(this)){
			this.finish();
			SleepManager.getInstance().endAwake(SleepManager.INTERRUPTED);
		}
		
		super.onStop();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tavx.C9Alarm.BaseActivity#onResume()
	 */
	@Override
	protected void onResume() {
		MyLogger.e(TAG, "onResume");
		super.onResume();
	}

	private void openControlPad() {
		if (t != null) {
			t.cancel();
		}
		t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						closeControlPad();
					}
				});
			}
		}, 3000);
		controlPad.setVisibility(View.VISIBLE);
	}

	private void closeControlPad() {
		controlPad.setVisibility(View.GONE);

	}

//	private void onCancelWakeUp() {
//		ADManager.normalClick();
//		if (isTest == true) {
//			AlarmApplication.getApp().showToast(
//					"点击动画后者按下两次返回键，闹钟会在" + sleepDelay + "分钟后再响，可以在主界面中取消");
//			// onDestory();
//			
//			this.finish();
//
//		} else {
//			if (sleepDelay > 0) {
//				AlarmApplication.getApp().showToast(
//						"闹钟将会在" + sleepDelay + "分钟后再响，可以在主界面中取消");
//				stillSleep();
//			} else {
//				onSlideOver();
//				// onDestory();
//			}
//
//		}
//	}

	private View DialogfindViewById(int res) {
		return findViewById(res);
	}

	Bitmap mbSlideThumb;
	Bitmap mbSlideTrack;
	Bitmap mbSlideMask;
	Bitmap mbSlideLeft;
	Bitmap mbSlideRight;

	private Drawable getDrawable(Bitmap b) {
		b.setDensity(DisplayMetrics.DENSITY_MEDIUM);
		NinePatchDrawable mDrawable;
		byte[] mb = b.getNinePatchChunk();
		if (mb != null) {
			if (NinePatch.isNinePatchChunk(mb)) {
				mDrawable = new NinePatchDrawable(AlarmApplication.getApp()
						.getResources(), new NinePatch(b, mb, null));
				return mDrawable;
			}
		}
		BitmapDrawable bB = new BitmapDrawable(AlarmApplication.getApp()
				.getResources(), b);
		return bB;
	}

	public void testAlarm() {
		isTest = true;

	}

	boolean isTest = false;
	boolean isShowShare = false;

//	public void onSlideOver() {
//		ADManager.AlarmAwake();
//		if (isShowShare) {
//			tryToAwake();
//		}
//		this.finish();
//		// onDestory();
//	}
/* (non-Javadoc)
 * @see android.support.v4.app.FragmentActivity#onDestroy()
 */
@Override
protected void onDestroy() {
	MyLogger.e(TAG, "onDestory");
	//Media.getInstance(getContext()).Stop();
	
	
	if (ad != null) {
		ad.dismiss();
	}

	c = null;
	ad = null;
	slideThumb = null;
	slideTrack = null;
	slideMask = null;
	slideLeft = null;
	slideRight = null;
	anim = null;
	animBg = null;
	nextIntent = null;
	tvTopMessage = null;
	ib_anim = null;
	v_alarmBackground = null;
	nfManger = null;
	mNotification = null;
	autoOff = null;
	ab = null;
	fm = null;
	ms = null;
	mainFrame = null;
	mAlarmReceiveActivity = null;
	vAlarm = null;
	tvHint = null;
	System.gc();
	super.onDestroy();
}

	private synchronized void tryToAwake() {
		isOver = true;
		Utils.GameStart();
		if (ab.getEnableGame() == true) {
			Intent i = new Intent(getContext(), boxGame.class);
			getContext().startActivity(i);
		} else {
			Intent intent = new Intent(Symbol.ACTION_WIN);
			intent.putExtra("alarm_form", ab.getFormIndex());
			getContext().sendBroadcast(intent);
		}
	}

//	private synchronized void stillSleep() {
//		AlarmBean ab = AlarmProvider.geInstance(getContext()).getAlarm(index);
//		ab.setDay(Symbol.DAY_ONCE);// sym
//		ab.setInterval(sleepDelay * 1000 * 60l);// sym
//		ab.setTime(System.currentTimeMillis() + ab.getInterval());
//		ab.setIndex(0);// sleepalarm ,start from 0
//		// ab.setName("10fenyan");
//		ab.setEnabled(true);
//
//		AlarmProvider.geInstance(getContext()).setSleepAlarm(ab);
//		nameIsToHard();
//
//		this.finish();
//		// onDestory();
//	}

	private synchronized void nameIsToHard() {
		Utils.GameReset();
		isOver = true;
		// nfManger.cancel(0);
		AlarmSet.setAlarm(AlarmApplication.getApp());

		// Media.getInstance(c).Stop();
		// Intent i = new Intent();
		// i.setAction(Symbol.ACTION_DONOTHING);
		// i.setClass(c, AlarmReceiver.class);

		if (nextIntent != null) {
			getContext().startActivity(nextIntent);
		}
		// AlarmReceiveActivity.this.finish();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tavx.C9Alarm.BaseActivity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (event.getKeyCode()) {
		case KeyEvent.KEYCODE_BACK: {
			if (mAlarmAlertWakeFragment != null)
				mAlarmAlertWakeFragment.toggleState();
		}
			break;
		case KeyEvent.KEYCODE_HOME: {
			this.finish();
		}

		}
		return true;
	}
	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.BaseActivity#onPause()
	 */
	@Override
	protected void onPause() {
		
		super.onPause();
	}
	
	 /**
     * 获得属于桌面的应用的应用包名称
     * @return 返回包含所有包名的字符串列表
     */
    public static ArrayList<String> getHomePackages(Context context) {
        ArrayList<String> names = new ArrayList<String>();
        PackageManager packageManager = context.getPackageManager();
        //属性
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        for(ResolveInfo ri : resolveInfo){
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }
     
   
}
