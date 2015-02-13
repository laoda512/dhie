package com.tavx.C9Alarm;

import mainView.boxGame;
import util.AlarmAlertWakeLock;
import util.AnimationsContainer;
import util.AnimationsContainer.FramesSequenceAnimation;
import util.BitmapWorker;
import util.SDResReadManager;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.NinePatch;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.appscumen.example.MySwitch;
import com.appscumen.example.MySwitch.OnChangeAttemptListener;
import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.Form.AlarmType;
import com.tavx.C9Alarm.Form.AlarmTypeAnim;
import com.tavx.C9Alarm.Form.Form;
import com.tavx.C9Alarm.Manager.ADManager;
import com.tavx.C9Alarm.Manager.SleepManager;
import com.tavx.C9Alarm.Manager.SleepManager.AlarmShower;

public class AlarmReceiveActivity extends BaseActivity {
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
	private static AlarmReceiveActivity mAlarmReceiveActivity;
	View vAlarm;
	Dialog ad;
	TextView tvHint;

	AlarmTypeAnim mAlarmType;

	private static String testFormName;
	private CloseSystemDialogsReceiver mCloseSystemDialogsReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		// Turn on the screen unless we are being launched from the AlarmAlert
		// subclass.
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
		lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		// XXX DO NOT COPY THIS!!! THIS IS BOGUS! Making an activity have
		// a system alert type is completely broken, because the activity
		// manager will still hide/show it as if it is part of the normal
		// activity stack. If this is really what you want and you want it
		// to work correctly, you should create and show your own custom window.
		lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		lp.token = null;
		getWindow().setAttributes(lp);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

		Intent i = getIntent();
		boolean isShowShare = i.getBooleanExtra("isShowShare", false);
		boolean isAlarmTest = i.getBooleanExtra("isAlarmTest", false);
		int alarm_pos_index = i.getIntExtra("alarm_pos_index", 0);

		showAlarm(this, isShowShare, isAlarmTest, alarm_pos_index);
	}

	public static synchronized void startAlarm(boolean isShowShare,
			boolean isAlarmTest, int alarm_pos_index) {
		if (mAlarmReceiveActivity == null) {
			mAlarmReceiveActivity = new AlarmReceiveActivity();
		}
		mAlarmReceiveActivity.showAlarm(AlarmApplication.getApp(), isShowShare,
				isAlarmTest, alarm_pos_index);

	}

	public static synchronized void startAlarm(boolean isShowShare,
			boolean isAlarmTest, String formId) {
		if (mAlarmReceiveActivity == null) {
			mAlarmReceiveActivity = new AlarmReceiveActivity();
		}
		testFormName = formId;
		mAlarmReceiveActivity.showAlarm(AlarmApplication.getApp(), isShowShare,
				isAlarmTest, -1);

	}

	public static synchronized void startAlarm(Context c, boolean isShowShare,
			boolean isAlarmTest, int alarm_pos_index) {
		/*
		 * if (mAlarmReceiveActivity == null) { mAlarmReceiveActivity = new
		 * AlarmReceiveActivity(); }
		 * mAlarmReceiveActivity.showAlarm(c,isShowShare,isAlarmTest,
		 * alarm_pos_index);
		 */
		Intent i = new Intent();
		i.setClass(c, AlarmReceiveActivity.class);
		i.putExtra("isShowShare", isShowShare);
		i.putExtra("isAlarmTest", isAlarmTest);
		i.putExtra("alarm_pos_index", alarm_pos_index);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		c.startActivity(i);
	}

	KeyguardLock mKeyguardLock;
	KeyguardManager mKeyguardManager;
	Handler mHandler = new Handler();
	private TextView tvTopHint;

	private void showAlarm(final Context c, boolean isShowShare,
			boolean isAlarmTest, int alarm_pos_index) {
		if (ad != null) {
			ad.cancel();
			anim = null;
		}

		isOver = false;
		this.isShowShare = isShowShare;
		this.c = c;
		iniView(isShowShare, isAlarmTest, alarm_pos_index);
		// if(ad==null){
		ad = new Dialog(c, R.style.Transparent);
		ad.setContentView(vAlarm);
		// }
		ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		ad.setCanceledOnTouchOutside(false);
		// ad.getWindow().setLayout(LayoutParams.FILL_PARENT,
		// LayoutParams.FILL_PARENT);
		ad.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				// AlarmApplication.getApp().showToast("xiaoshi");
				try {

					 if (mCloseSystemDialogsReceiver != null)
					 c.unregisterReceiver(mCloseSystemDialogsReceiver);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					MyLogger.e(TAG, "dismiss");
//					SleepManager.getInstance().endAwake(
//							SleepManager.INTERRUPTED);
					onDestory();
				}
			}
		});
		ad.setOnKeyListener(new OnKeyListener() {
			int count = 0;

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_BACK: {
					if (event.getAction() == event.ACTION_UP) {
						count++;
						if (count >= 2) {
							onCancelWakeUp();
							count = 0;
						} else {
							if (sleepDelay > 0) {
								tvHint.setText("再次点击返回按钮开启贪睡模式");
							} else {
								tvHint.setText("再次点击下方关闭");
							}
						}

					}
				}
					return true;
				case KeyEvent.KEYCODE_HOME: {
					onCancelWakeUp();
				}

				}
				return false;
			}
		});

		ad.show();

		 IntentFilter filter = new IntentFilter(
		 Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		
		 mCloseSystemDialogsReceiver = new CloseSystemDialogsReceiver();
		 c.registerReceiver(mCloseSystemDialogsReceiver, filter);

	}

	private Context getContext() {
		if (c != null)
			return c;
		else
			return AlarmApplication.getApp();
	}

	private void iniView(final boolean isShowShare, boolean isAlarmTest,
			int alarm_pos_index) {
		LayoutInflater l = LayoutInflater.from(getContext());
		// if(vAlarm==null){
		vAlarm = l.inflate(R.layout.alert_alarm, null);
		mainFrame = DialogfindViewById(R.id.main_Frame);
		tvHint = (TextView) DialogfindViewById(R.id.hint);
		ib_anim = (ImageView) DialogfindViewById(R.id.imageView1);
		v_alarmBackground = (ImageView) DialogfindViewById(R.id.alarm_bg);
		tvTopMessage = (TextView) DialogfindViewById(R.id.topHint);
		ms = (MySwitch) DialogfindViewById(R.id.switch3);

		// }else{
		// mainFrame.requestLayout();
		// }
		isTest = isAlarmTest;
		index = alarm_pos_index;
		MyLogger.e("aaaa", "alarm_index" + index);
		String path;
		final Form mForm;
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

		// ms.toggle();
		ms.setChecked(false);
		ms.toggle();
		ms.disableClick();
		ms.fixate(true);

		AlarmType alarmType = mForm.getAlarmTypeBean();
		if (alarmType != null && alarmType instanceof AlarmTypeAnim) {
			mAlarmType = (AlarmTypeAnim) alarmType;
		}
		readSlideRes(mAlarmType, mForm);

		sleepDelay = AlarmApplication.getApp().getUser().getSleepTime();
		if (sleepDelay > 0) {
			tvTopMessage.setText("双击下方再睡" + sleepDelay + "分钟");
		} else {
			tvTopMessage.setText("贪睡模式未开启");
		}

		ms.setOnChangeAttemptListener(new OnChangeAttemptListener() {

			@Override
			public void onChangeAttempted(boolean isChecked) {
				MyLogger.e("aaaa", "xxxx2");
				if (isChecked == true)
					onSlideOver();
			}
		});

		// fm =(AnimationDrawable) ib_anim.getDrawable();

		if (mAlarmType != null) {

			Media.getInstance(getContext()).playMusic(
					SDResReadManager.getInstance().getResPathWithName(
							mAlarmType.getAlarmMusic(), mForm.getTitleName()),
					AlarmApplication.getApp().getIsMusic(),
					AlarmApplication.getApp().getIsVibrate(),
					AlarmApplication.getApp().getIsSlowLoud(),
					new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							startAnim(mForm);
						}
					});

		} else {
			path = ab.getUri();
			Media.getInstance(getContext()).playMusic(
					path,
					AlarmProvider.geInstance(getContext()).getAlarm(index)
							.getVibrate());

		}

		ib_anim.setOnClickListener(new OnClickListener() {
			int count = 0;

			@Override
			public void onClick(View v) {
				if (sleepDelay > 0) {
					count++;
					if (count < 2) {
						tvTopMessage.setText("再次点击下方进入贪睡模式");
						return;
					}
					onCancelWakeUp();
				} else {
					count++;
					if (count < 2) {
						tvTopMessage.setText("再次点击下方关闭");
						return;
					}
					onCancelWakeUp();
				}

			}
		});

		SleepManager.getInstance().startNewAlarm(new AlarmShower() {

			@Override
			public boolean isTest() {
				return isTest;
			}

			@Override
			public boolean isShowShare() {
				return isShowShare;
			}

			@Override
			public Form getForm() {
				return mForm;
			}

			@Override
			public AlarmBean getAlarmBean() {
				return ab;
			}

			@Override
			public void closeAlarm() {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						onDestory();
					}
				});
			}
		});

		startAnim(mForm);

		if (isShowShare == false) {
			testAlarm();
			return;
		}

	}

	public void startAnim(Form f) {
		if (anim != null) {
			anim.stop();
			anim = null;
		}

		int mWidth = 720;// 宽度
		int mHeight = 960;// 高度
		int realWidth = (int) (mWidth * AlarmApplication.getApp().getFitRateX());
		int realHeight = (int) (mHeight * AlarmApplication.getApp()
				.getFitRateY());
		anim = AnimationsContainer.getInstance().createSplashAnim(ib_anim,
				mAlarmType.getAlarmAnim(), realWidth, realHeight);

		if (Build.VERSION.SDK_INT < 11) {

			AlarmApplication.getApp().showError("非常抱歉Android2.x系统的机器无法暂时播放动画");
		} else {
			if (!AlarmApplication.getApp().getUser().getIsLowMemory()) {
				if (animBg == null || animBg.isRun() == false) {
					animBg = AnimationsContainer.getInstance()
							.createSplashAnim(v_alarmBackground,
									f.getAlarmBgAnim(), realWidth, 960);
					animBg.start();
				}
				if (anim != null)
					anim.start();
			}
			// animBg = AnimationsContainer.getInstance().createSplashAnim(
			// v_alarmBackground, f.getAlarmBgAnim());

			// animBg.start();
		}

	}

	private void onCancelWakeUp() {
		SleepManager.getInstance().endAwake(SleepManager.SNOOZE);
		/*
		 * ADManager.normalClick(); if (isTest == true) {
		 * AlarmApplication.getApp().showToast( "点击动画后者按下两次返回键，闹钟会在" +
		 * sleepDelay + "分钟后再响，可以在主界面中取消"); onDestory();
		 * 
		 * } else { if (sleepDelay > 0) { AlarmApplication.getApp().showToast(
		 * "闹钟将会在" + sleepDelay + "分钟后再响，可以在主界面中取消"); stillSleep(); } else {
		 * onSlideOver(); //onDestory(); }
		 * 
		 * }
		 */}

	private View DialogfindViewById(int res) {
		return vAlarm.findViewById(res);
	}

	Bitmap mbSlideThumb;
	Bitmap mbSlideTrack;
	Bitmap mbSlideMask;
	Bitmap mbSlideLeft;
	Bitmap mbSlideRight;

	public void readSlideRes(AlarmTypeAnim alarmType, Form f) {

		if (alarmType.getSlideThumb() != 0) {
			mbSlideThumb = SDResReadManager.getInstance().getResBitmap(
					alarmType.getSlideThumb(), f.getTitleName(),
					(int) (100 * AlarmApplication.getApp().getFitRateX()),
					(int) (100 * AlarmApplication.getApp().getFitRateY()));

			slideThumb = getDrawable(mbSlideThumb);
			ms.setmThumbDrawable(slideThumb);
		}
		if (alarmType.getSlideTrack() != 0) {
			mbSlideTrack = SDResReadManager.getInstance().getResBitmap(
					alarmType.getSlideTrack(), f.getTitleName(),
					(int) (400 * AlarmApplication.getApp().getFitRateX()),
					(int) (100 * AlarmApplication.getApp().getFitRateY()));

			slideTrack = getDrawable(mbSlideTrack);
			ms.setmTrackDrawable(slideTrack);
		}
		if (alarmType.getSlideMask() != 0) {

			if (alarmType.getSlideMask() == alarmType.getSlideTrack()) {
				slideMask = slideTrack;
				mbSlideMask = mbSlideTrack;
			} else {
				mbSlideMask = SDResReadManager.getInstance().getResBitmap(
						alarmType.getSlideMask(), f.getTitleName(),
						(int) (400 * AlarmApplication.getApp().getFitRateX()),
						(int) (100 * AlarmApplication.getApp().getFitRateY()));
				slideMask = getDrawable(mbSlideMask);// new
														// BitmapDrawable(AlarmApplication.getApp().getResources(),mbSlideMask);
			}

			ms.setmMaskDrawable(slideMask);
			if (alarmType.getSlideLeft() != 0) {

				if (alarmType.getSlideLeft() == alarmType.getSlideMask()) {
					mbSlideLeft = mbSlideMask;
					slideLeft = slideMask;
				} else {
					mbSlideLeft = SDResReadManager.getInstance().getResBitmap(
							alarmType.getSlideLeft(),
							f.getTitleName(),
							(int) (400 * AlarmApplication.getApp()
									.getFitRateX()),
							(int) (100 * AlarmApplication.getApp()
									.getFitRateY()));
					slideLeft = getDrawable(mbSlideLeft);// new
															// BitmapDrawable(AlarmApplication.getApp().getResources(),mbSlideLeft);
				}

				ms.setmLeftBackground(slideLeft);
			}
			if (alarmType.getSlideRight() != 0) {
				if (alarmType.getSlideRight() == alarmType.getSlideMask()) {
					mbSlideRight = mbSlideMask;
					slideRight = slideMask;
				} else {
					mbSlideRight = SDResReadManager.getInstance().getResBitmap(
							alarmType.getSlideRight(),
							f.getTitleName(),
							(int) (400 * AlarmApplication.getApp()
									.getFitRateX()),
							(int) (100 * AlarmApplication.getApp()
									.getFitRateY()));
					slideRight = getDrawable(mbSlideRight);// new
															// BitmapDrawable(AlarmApplication.getApp().getResources(),mbSlideRight);
				}
				ms.setmRightBackground(slideRight);
			}
		}

	}

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

	public void onSlideOver() {
		//ADManager.AlarmAwake();
		SleepManager.getInstance().endAwake(SleepManager.AWAKE);
	}

	private void onDestory() {
		if (anim != null) {
			anim.stop();
			anim.recyle();
			Drawable mDrawable = ib_anim.getDrawable();
			ib_anim.setImageBitmap(null);
			if (mDrawable != null) {
				if (mDrawable instanceof BitmapDrawable) {

					if (((BitmapDrawable) mDrawable).getBitmap().isRecycled()) {
						mDrawable = null;
					} else {
						BitmapWorker.recyleBitmap(((BitmapDrawable) mDrawable)
								.getBitmap());
						mDrawable = null;
					}
				}
			} else {
			}
			anim = null;
		}
		if (animBg != null) {
			animBg.stop();
			// animBg.recyle();

			Drawable mDrawable = v_alarmBackground.getDrawable();
			v_alarmBackground.setImageBitmap(null);
			if (mDrawable != null) {
				if (mDrawable instanceof BitmapDrawable) {
					if (((BitmapDrawable) mDrawable).getBitmap().isRecycled()) {
						mDrawable = null;
					} else {
						BitmapWorker.recyleBitmap(((BitmapDrawable) mDrawable)
								.getBitmap());
						mDrawable = null;
					}
				}
			} else {
			}

			animBg = null;
		}

		Media.getInstance(getContext()).Stop();
		if (ad != null) {
			ad.dismiss();
		}

		if (mCloseSystemDialogsReceiver != null)
			try {
				getContext().unregisterReceiver(mCloseSystemDialogsReceiver);
			} catch (Exception e) {
				e.printStackTrace();
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

	


	private class CloseSystemDialogsReceiver extends BroadcastReceiver {
		final String SYSTEM_DIALOG_REASON_KEY = "reason";
		final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
				String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
				if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
					onCancelWakeUp();
					try {
						getContext().unregisterReceiver(
								mCloseSystemDialogsReceiver);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

}
