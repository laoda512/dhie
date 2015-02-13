package com.tavx.C9Alarm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import util.BitmapWorker;
import util.BreathInterpolator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.Form.Form;
import com.tavx.C9Alarm.Fragment.DetailsFragment;
import com.tavx.C9Alarm.Manager.ADManager;
import com.tavx.C9Alarm.adapter.themeAdapter;
import com.tavx.C9Alarm.bean.TagHolder;
import com.tavx.C9Alarm.listener.OnThemeItemCheckedListener;

public class SetAlarmActivity extends BaseActivity implements
		AnimatorUpdateListener {
	private final static String TAG = "alarm";
	private static final int TYPE_ALARM = 1;

	private Context mContext;

	// private AlarmBean m_alarm;

	ListView m_listViewAlarm;

	TextView m_tvAlarmName;
	// TextView m_tvAlarmTime;
	WheelView wlh, wlm;
	TextView m_tvAlarmWeeklyMode;
	TextView m_tvAlarmOccursMode;
	TextView m_tvAlarmInterval;
	TextView m_tvAlarmRingMode;

	Button m_btFinish, m_btBack;

	/** index of alarms */
	private int m_alarmIndex = 0;

	private String m_alarmName = "";
	private int m_alarmType = 0;

	private boolean m_bNameManulShowed = false;

	private String m_alarmWeekly = "";

	private String m_alarmOccurs = "";
	private String m_alarmInterval = "";

	private String m_alarmRingMode = "";

	/** the day which alarm occurs on */
	private int m_nOccursDay = 0; // 0x7f;

	private boolean m_bWeeklyManulSelected = false;

	private int m_alarmHours = 0;
	private int m_alarmMinutes = 0;

	private boolean m_gameEnable;
	private int m_nOccurs = 0;
	private int m_nInterval = 0;

	private int m_nRingMode = 0;
	private String m_ringUri = null;

	private boolean m_bEnable = false;
	private boolean m_bVibrate = false;

	private String form;
	RelativeLayout mainFrame;
	Form mForm;
	// TimePicker mTimePicker;
	View timePickerBlayout, timePickerBlayoutView, timePickerAlayout,
			timePickerAlayoutView;
	private View weekDayBLayout;
	private View weekDayBLayoutView;
	private View weekDayALayoutView;
	private View weekDayALayout;
	private View topButtonBLayout;
	private View topButtonBLayoutView;
	private View topButtonALayout;
	private View topButtonALayoutView;
	private ImageView bgHolePad;
	// private View bgHolePadView;

	View timeClock;
	private View settingPad;

	Animation fadeAnimation, realAnimation;
	View bgHolePadpad;
	private View testPad;
	TextView btn_weekDaySet[][];
	boolean btn_weekDaySetState[][];
	private int formIndex;
	private ImageView topButton;
	private TextView tvTaq1;
	private TextView tvTaq2;
	private TextView tvTaq3;
	private TextView tvTaq4;
	private TextView tvTaq5;
	TextView tvTaq[];
	ListView tvTaqList;
	private TextView tvAlarmTime;
	private TextView tvAlarmStyle;
	// private TextView tvAlarmWorkDay;
	private TextView tvAlarmConfirm;
	private TextView tvAlarmCancel;
	private TextView button1;

	private int[] dateDayIndex = { Symbol.DAY_MON, Symbol.DAY_TUE,
			Symbol.DAY_WED, Symbol.DAY_THU, Symbol.DAY_FRI, Symbol.DAY_SAT,
			Symbol.DAY_SUN };
	private TextView[] tvDateDay;
	private float dateDayTextSize = -1;
	private float dateDayTextSizeLarge = -1;

	private WheelView mWheelViewhh, mWheelViewmm;
	private ImageView topButtonOverlook;
	Handler mHandler = new Handler();
	AlarmBean alarm;
	themeAdapter mAdapter;
	
	DetailsFragment mDetailsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.alarm_list);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alarm_settings_new);
		mainFrame = (RelativeLayout) findViewById(R.id.main_frame);

		mContext = this;

		Bundle extras = getIntent().getExtras();
		m_alarmIndex = extras.getInt("alarm_position");
		alarm = AlarmProvider.geInstance(this).getAlarm(m_alarmIndex);
		// formIndex = extras.getInt("form_index");
		// mForm = Form.getForm(formIndex);

		wlh = (WheelView) findViewById(R.id.hh);
		wlm = (WheelView) findViewById(R.id.mm);

		//
		MyLogger.e("aaaa", "getAlarm" + Utils.getCalendarTime(alarm.getTime()));
		timePickerBlayout = findViewById(R.id.timepickerBlayout);
		timePickerBlayoutView = findViewById(R.id.timepickerBlayoutView);
		timePickerAlayout = findViewById(R.id.timepickerAlayout);
		timePickerAlayoutView = findViewById(R.id.timepickerAlayoutView);

		weekDayBLayout = findViewById(R.id.weekDayBLayout);
		weekDayBLayoutView = findViewById(R.id.weekDayBLayoutView);
		weekDayALayout = findViewById(R.id.weekDayALayout);
		weekDayALayoutView = findViewById(R.id.weekDayALayoutView);

		topButtonBLayout = findViewById(R.id.topButtonBLayout);
		topButtonBLayoutView = findViewById(R.id.topButtonBLayoutView);
		topButtonALayout = findViewById(R.id.topButtonALayout);
		topButtonALayoutView = findViewById(R.id.topButtonALayoutView);

		// bgHolePadView = findViewById(R.id.bg_hole_padView);

		btn_weekDaySet = new TextView[4][3];
		btn_weekDaySetState = new boolean[4][3];
		btn_weekDaySet[0][0] = (TextView) findViewById(R.id.weekdaySet11);
		btn_weekDaySet[0][1] = (TextView) findViewById(R.id.weekdaySet12);
		btn_weekDaySet[1][0] = (TextView) findViewById(R.id.weekdaySet21);
		btn_weekDaySet[1][1] = (TextView) findViewById(R.id.weekdaySet22);
		btn_weekDaySet[1][2] = (TextView) findViewById(R.id.weekdaySet23);
		btn_weekDaySet[2][0] = (TextView) findViewById(R.id.weekdaySet31);
		btn_weekDaySet[2][1] = (TextView) findViewById(R.id.weekdaySet32);
		btn_weekDaySet[2][2] = (TextView) findViewById(R.id.weekdaySet33);
		btn_weekDaySet[3][0] = (TextView) findViewById(R.id.weekdaySet41);
		btn_weekDaySet[3][1] = (TextView) findViewById(R.id.weekdaySet42);
		btn_weekDaySet[3][2] = (TextView) findViewById(R.id.weekdaySet43);

		tvAlarmConfirm = (TextView) findViewById(R.id.confirm);
		tvAlarmCancel = (TextView) findViewById(R.id.cancel);

		bgHolePad = (ImageView) findViewById(R.id.bg_hole_pad);
		topButton = (ImageView) findViewById(R.id.topButton);
		topButtonOverlook = (ImageView) findViewById(R.id.topButtonOverlook);
		tvTaq1 = (TextView) findViewById(R.id.tvTaq1);
		tvTaq2 = (TextView) findViewById(R.id.tvTaq2);
		tvTaq3 = (TextView) findViewById(R.id.tvTaq3);
		tvTaq4 = (TextView) findViewById(R.id.tvTaq4);
		tvTaq5 = (TextView) findViewById(R.id.tvTaq5);
		tvTaq = new TextView[] { tvTaq1, tvTaq2, tvTaq3, tvTaq4, tvTaq5 };
		tvTaqList =  (ListView) findViewById(R.id.tvTaqList);
	
		List<TagHolder> mList=new ArrayList<TagHolder>();
		Object formList [] = Form.getAllForm();
		for(int i=0;i<formList.length;i++){
			Form f = (Form) formList[i];
			if(alarm.getFormIndex().equals(f.getTitleName())){
				currentTagPadIndex = mList.size();
			}
			if(f!=null){
				TagHolder t =new TagHolder();
				t.name=f.getTitleName();
				t.FormId=f.getTitleName();
				t.mForm = f;
				mList.add(t);
			}
		}
		
		OnThemeItemCheckedListener mOnThemeItemCheckedListener = new OnThemeItemCheckedListener() {
			
			@Override
			public void onItemChecked(int position) {
				MyLogger.e("aaaax", "aaa");
				ADManager.normalClick();
				TagHolder t = ((TagHolder)(tvTaqList.getAdapter().getItem(position)));
				String formId=  t.FormId;
				changeCurrentIndex(position);
				mDetailsFragment.setFormId(formId);
//				if (form != index) {
//					mhandler.post(new Runnable() {
//						@Override
//						public void run() {
//							refreshTagPad();
//							return;
//						}
//					});
//				}
				form = formId;
				mForm = Form.getForm(formId);
				save2(false);
				changeToStyle(formId);
			}
		};
		mAdapter =new themeAdapter(mList, this,mOnThemeItemCheckedListener,currentTagPadIndex);
		tvTaqList.setAdapter(mAdapter);
		
		tvTaqList.setFocusable(false);
		tvTaqList.setDrawingCacheEnabled(true);
		tvTaqList.setChoiceMode(tvTaqList.CHOICE_MODE_NONE);
		
		tvAlarmTime = (TextView) findViewById(R.id.tvAlarmTime);
		tvAlarmStyle = (TextView) findViewById(R.id.tvAlarmStyle);

		button1 = (TextView) findViewById(R.id.button1);

		tvDateDay = new TextView[8];
		tvDateDay[0] = (TextView) findViewById(R.id.tvDay00);
		tvDateDay[1] = (TextView) findViewById(R.id.tvDay01);
		tvDateDay[2] = (TextView) findViewById(R.id.tvDay10);
		tvDateDay[3] = (TextView) findViewById(R.id.tvDay11);
		tvDateDay[4] = (TextView) findViewById(R.id.tvDay20);
		tvDateDay[5] = (TextView) findViewById(R.id.tvDay21);
		tvDateDay[6] = (TextView) findViewById(R.id.tvDay30);
		tvDateDay[7] = (TextView) findViewById(R.id.tvDay31);

		settingPad = findViewById(R.id.settingPad);
		testPad = findViewById(R.id.testPad);
		bgHolePadpad = findViewById(R.id.bgholePadPad);
		
		
		FragmentManager fragmentManager = super.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction(); 
		mDetailsFragment = DetailsFragment.newInstance(alarm.getFormIndex());
		fragmentTransaction.add(R.id.weekDayALayoutView,mDetailsFragment);
		fragmentTransaction.commit();
		
		mhandler.post(new Runnable() {

			@Override
			public void run() {
				iniAllUi();
			}

		});

	}

	float lastWeight2 = 0;
	float lastWeight = 0;
	int Allcount = 0;
	
	
	void iniAllUi() {
		WeekDaySet w = new WeekDaySet();
		btn_weekDaySet[0][0].setOnClickListener(w);
		btn_weekDaySet[0][0].setTag(new Point(0, 0));
		btn_weekDaySet[0][1].setOnClickListener(w);
		btn_weekDaySet[0][1].setTag(new Point(0, 1));
		for (int x = 1; x < 4; x++) {
			for (int y = 0; y < 3; y++) {
				btn_weekDaySet[x][y].setOnClickListener(w);
				btn_weekDaySet[x][y].setTag(new Point(x, y));
			}
		}

		tvAlarmConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				save2();
				refreshLeftPad();
				dropDownAnim();
			}
		});

		tvAlarmCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// refreshLeftPad();
				dropDownAnim();
				reSetTimeSetPad();
			}
		});

		// tvAlarmWorkDay = (TextView) findViewById(R.id.tvAlarmWorkDay);

		AlarmUser mAlarmUser = AlarmApplication.getApp().getUser();
		if (mAlarmUser.getHasAlarmSetHelp() == false) {
			helpMessageAnim();
			mAlarmUser.setHasAlarmSetHelp(true);
		} else {
			button1.setVisibility(View.GONE);
		}

		initialParam(alarm);
		// mTimePicker.setCurrentHour(m_alarmHours);
		// mTimePicker.setCurrentMinute(m_alarmMinutes);

		NumericWheelAdapter hourAdapter = new NumericWheelAdapter(this, 0, 23);
		hourAdapter.setItemResource(R.layout.wheel_text_item);
		hourAdapter.setItemTextResource(R.id.text);
		wlh.setViewAdapter(hourAdapter);
		wlh.setCyclic(true);
		wlh.setCurrentItem(m_alarmHours, false);

		NumericWheelAdapter minuteAdapter = new NumericWheelAdapter(this, 0, 59);
		minuteAdapter.setItemResource(R.layout.wheel_text_item);
		minuteAdapter.setItemTextResource(R.id.text);
		wlm.setViewAdapter(minuteAdapter);
		wlm.setCurrentItem(m_alarmMinutes, false);
		wlm.setCyclic(true);

		topButton.setOnClickListener(new OnClickListener() {
  
			@Override
			public void onClick(View v) {
				// mController.setShareContent("这些文字我要分享到新浪微博");

				// mController.openComment(mContext, false);
				//AlarmReceiveActivityVideo.startAlarm(false, true, form,SetAlarmActivity.this);
				AlarmReceiveActivityVideo.startAlarmActivity(SetAlarmActivity.this, false, true, form,mForm.getAlarmTypeBean().getType());//(true, true, form);
//				 Intent i = new Intent(SetAlarmActivity.this,
//						 MainActivity.class);
//			
//				 startActivity(i);
			}
		});

		// initView(alarm);
		/*
		 * tvDayMon = (TextView) findViewById(R.id.tvDayMon); tvDayTue =
		 * (TextView) findViewById(R.id.tvDayTue); tvDayWed = (TextView)
		 * findViewById(R.id.tvDayWed); tvDayThu = (TextView)
		 * findViewById(R.id.tvDayThu); tvDayFri = (TextView)
		 * findViewById(R.id.tvDayFri); tvDaySat = (TextView)
		 * findViewById(R.id.tvDaySat);
		 */
		// tvDayMon=(TextView) findViewById(R.id.tvDayMon);

		// timeClock = findViewById(R.id.timeClock);

		iniTimePicker();
		iniTopButton();
		iniWeekDay();
		inibgHolePad();

		iniTopPadAnmi();
		inibgHolePadAnmi();
		iniTestPadAnmi();
		iniWeekDayAnmi();
		iniHotInfoAnmi();
		iniTimePickerAnmi();

		timePickerAlayout.setOnTouchListener(new OnTouchListener() {
			boolean hasMove = false;
			float firstX;
			float firstY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyLogger.e("aaaab", "up" + event.getAction());
				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					hasMove = false;
					firstX = event.getX();
					firstY = event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					float X = event.getX();
					float Y = event.getY();
					X = Math.abs(X - firstX);
					Y = Math.abs(Y - firstY);
					if (X + Y > 5) {
						hasMove = true;
						return false;
					}
					return true;

				case MotionEvent.ACTION_UP:
					if (hasMove == false) {
						timePickerAlayout
								.playSoundEffect(SoundEffectConstants.CLICK);
						timePadAnim();
						event.setAction(MotionEvent.ACTION_CANCEL);
					} else {
						ADManager.normalClick();
						return false;
					}
					break;
				default:
					break;
				}
				return true;
			}
		});

		// timePickerAlayout.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// timePadAnim();
		//
		// }
		// });

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;// 宽度
		moveScare = moveScare * 1080 / width;

		//iniTagPad();
	//	changeCurrentIndex(form );
		changeToStyle(form );

		loadDay(m_nOccursDay);
		MyLogger.e("aaaa", "m_alarmHours" + m_alarmHours + "m_alarmMinutes"
				+ m_alarmMinutes);

		fadeAnimation = new AlphaAnimation(0.00f, 1.00f);
		fadeAnimation.setDuration(1000);
		realAnimation = new AlphaAnimation(1.00f, 0.00f);

		ViewTreeObserver vto2 = mainFrame.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				// MyLogger.e("cccc", mainFrame.getX()+" "+mainFrame.getY());
				// MyLogger.e("cccc",
				// mainFrame.getWidth()+" "+mainFrame.getHeight());
				if (dateDayTextSize <= 0) {
					dateDayTextSize = (tvDateDay[0].getHeight() * 0.6f);
					dateDayTextSizeLarge = (tvDateDay[0].getHeight() * 0.72f);
					refreshLeftPad();
					MyLogger.e("fuck", tvDateDay[0].getHeight() + " "
							+ tvDateDay[0].getTextSize());
				}

				if (tagIconWidth <= 0) {
					
					tagIconWidth = tvTaqList.getHeight();
					
					mAdapter.setItemHeight(tvTaqList.getHeight()/5);
				/*	
					int height = tvTaq[0].getHeight();
					int pad = (int) (height * 0.05f);
					tagIconHeight = pad * 16;
					tagIconWidth = pad * 11;

					// recyleTagPad(false);

					for (int i = 0; i < tvTaq.length; i++) {
						Form temp = Form.getForm((form) / 4, i);
						if (temp != null && i != tvTaq.length - 1) {

							Bitmap btpbuff = BitmapWorker.getBitmap(
									SetAlarmActivity.this,
									temp.getSettingPageBg(), tagIconWidth + pad
											* 4, tagIconHeight);
							BitmapDrawable mBitmapDrawable = new BitmapDrawable(
									btpbuff);
							mBitmapDrawable.setBounds(pad * 4, -pad,
									tagIconWidth + pad * 4, tagIconHeight);
							tvTaq[i].setCompoundDrawablePadding(0);
							tvTaq[i].setCompoundDrawables(mBitmapDrawable,
									null, null, null);// (mBitmapDrawable);
							// MyLogger.e("aaaa", "xx"+tvTaq[i].getWidth());
						} else {
							break;
						}
					}*/
				}

				if (topPadHeight <= 0) {
					topPadHeight = topButtonALayout.getHeight();
				}

				if (bgHolePadwidth <= 0) {
					bgHolePadwidth = bgHolePad.getWidth();
				}

				if (bgWholeSize <= 0) {
					bgWholeSize = bgHolePadpad.getWidth();
				}

				if (weekDayWidth <= 0) {
					weekDayWidth = weekDayALayout.getWidth();
				}

				if (timePickerBLayoutWidth <= 0) {
					timePickerBLayoutWidth = timePickerBlayout.getWidth();
				}

				if (mainFrameWidth <= 0) {
					mainFrameWidth = mainFrame.getWidth();
					int offsetX = (int) (0.3 * mainFrameWidth);
					testPadOffsetX = offsetX;
					mainFrameheight = mainFrame.getHeight();
					int offsetY = (int) (0.3 * mainFrameheight);
					testPadOffsetY = offsetY;
					testPad.scrollTo(offsetX, -offsetY);
					if (Build.VERSION.SDK_INT >= 11) {
						testPadScaleXMax = settingPad.getScaleX();
						testPadScaleYMax = settingPad.getScaleY();
					} else {
						testPadScaleXMax = 1;
						testPadScaleYMax = 1;
					}
				}

				if (timeAnimator != null && timeAnimator.isRunning()) {
					Allcount++;
					MyLogger.e("cccc", "start" + moveWeight + " "
							+ (moveWeight - lastWeight) + "Allcount "
							+ Allcount);
					lastWeight = moveWeight;
					// refreshLayout(moveWeight);
				} else {
					Allcount = 0;
					MyLogger.e("cccc", "close" + moveWeight);
				}
			}
		});

		makeCache();
	}

	void helpMessageAnim() {
		if (Build.VERSION.SDK_INT < 11) {
			button1.setVisibility(View.GONE);
			return;
		}
		;

		final ValueAnimator downAnimator = ObjectAnimator.ofFloat(
				new AnimHolder(0), "weight", 0.6f, 1f);
		downAnimator.setDuration(2000);
		downAnimator.setRepeatCount(30);
		downAnimator.setInterpolator(new BreathInterpolator(1));
		downAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				//ADManager.normalClick();
				if (button1.getVisibility() == View.GONE) {
					downAnimator.cancel();
				} else {
					button1.setAlpha((Float) animation.getAnimatedValue());
				}
			}
		});
		downAnimator.start();
	}

	public void closeHelpMessage() {
		if (button1.getVisibility() == View.VISIBLE) {
			button1.setVisibility(View.GONE);
		}
	}

	ValueAnimator timeAnimator;

	// ValueAnimator downAnimator;
	// ValueAnimator upAnimator;

	void timePadAnim() {
		closeHelpMessage();
		count = 0;

		if (timeAnimator != null && timeAnimator.isRunning()) {
			timeAnimator.cancel();
		}
		timeAnimator = ObjectAnimator.ofFloat(new AnimHolder(0), "weight", 0,
				0.6f);
		timeAnimator.setDuration(1000);
		timeAnimator.setInterpolator(new AccelerateInterpolator());
		timeAnimator.addUpdateListener(this);
		timeAnimator.start();

	}

	void dropUpAnim() {
		closeHelpMessage();

		if (timeAnimator != null && timeAnimator.isRunning()) {
			timeAnimator.cancel();
		}
		timeAnimator = ObjectAnimator.ofFloat(new AnimHolder(0), "weight",
				moveWeight, 0.6f);
		timeAnimator.setDuration((long) (1000 * Math
				.abs((moveWeight - 0.6) / 0.6)));
		timeAnimator.setInterpolator(new LinearInterpolator());
		timeAnimator.addUpdateListener(this);
		timeAnimator.start();

	}

	void dropDownAnim() {
		closeHelpMessage();

		if (timeAnimator != null && timeAnimator.isRunning()) {
			timeAnimator.cancel();
		}
		timeAnimator = ObjectAnimator.ofFloat(new AnimHolder(0), "weight",
				moveWeight, 0.0f);
		timeAnimator.setDuration((long) (800 * Math.abs((moveWeight) / 0.6)));
		timeAnimator.setInterpolator(new LinearInterpolator());
		timeAnimator.addUpdateListener(this);
		timeAnimator.start();
		timeAnimator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				moveWeight = 0;
				refreshLayout(moveWeight);
				ADManager.normalClick();
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
	}

	class AnimHolder {
		public AnimHolder(float weight) {
			super();
			this.weight = weight;
		}

		float weight;

		public float getWeight() {
			return weight;
		}

		public void setWeight(float weight) {
			this.weight = weight;
		}
	}

	AnimHolder mAnimHolder = new AnimHolder(0);

	int mainFrameWidth;
	int mainFrameheight;
	int testPadOffsetX;
	int testPadOffsetY;

	int bgWholeSize;

	int ScreenX;
	int ScreenY;
	int defaultX = 900;
	int defaultY = 1600;
	float scareX = ScreenX / defaultX;
	float scareY = ScreenY / defaultY;
	float moveScare = 0.0007f;

	float lastX = -1, lastY = -1, firstX = -1, firstY = -1;
	float moveWeight = 0;// 总体移动量
	float moveWeightMax = 0.6f;// 总体移动上限

	float timePickerALayoutWeight, timePickerALayoutViewWeight;
	int timePickerAWay = -1;
	float timePickerBLayoutWeight, timePickerBLayoutViewWeight;
	int timePickerBWay = 1;
	float topButtonALayoutWeight, topButtonALayoutViewWeight;
	int topButtonAWay = 1;
	float topButtonBLayoutWeight, topButtonBLayoutViewWeight;
	int topButtonBWay = 1;
	float weekDayALayoutWeight, weekDayALayoutViewWeight;
	int weekDayAWay = -1;
	float weekDayBLayoutWeight, weekDayBLayoutViewWeight;
	int weekDayBWay = 1;

	float timePickerALayoutWeightMax;
	float timePickerBLayoutWeightMax;
	float topButtonALayoutWeightMax;
	float topButtonBLayoutWeightMax;
	float weekDayALayoutWeightMax;
	float weekDayBLayoutWeightMax;
	float bgHolePadWeightMax;

	float timePickerALayoutWeightOffsetStart;
	float timePickerALayoutWeightOffsetEnd;
	float timePickerBLayoutWeightOffsetStart;
	float timePickerBLayoutWeightOffsetEnd;
	float timePickerBLayoutSpeedRealfix; // 控件实际滑动距离和总滑动距离的比值

	float topButtonALayoutSpeed = 3.0f;// 三倍速，缓入缓出
	float topButtonALayoutOffsetStart = (1 - 1 / topButtonALayoutSpeed) / 2
			* moveWeightMax;
	float topButtonALayoutOffsetEnd = (1 - (1 - 1 / topButtonALayoutSpeed) / 2)
			* moveWeightMax;
	float topButtonALayoutSpeedRealfix; // 控件实r际滑动距离和总滑动距离的比值

	float topButtonBLayoutOffsetStart;
	float topButtonBLayoutOffsetEnd;

	float weekDayALayoutOffsetStart;
	float weekDayALayoutOffsetEnd;
	float weekDayALayoutSpeedRealfix; // 控件实际滑动距离和总滑动距离的比值
	float weekDayTextViewAlphaSpeed;// 消失的速度
	
	float hotInfoOffsetStart;
	float hotInfoOffsetEnd;
	float hotInfoOffSpeedRealfix; 
	

	float weekDayBLayoutOffsetStart;
	float weekDayBLayoutOffsetEnd;

	float bgHolePadOffsetStart;
	float bgHolePadOffsetEnd;
	float bgHolePadSpeedRealfix; // 控件实际滑动距离和总滑动距离的比值

	float bgHolePadWeight;// , bgHolePadViewWeight;

	float testPadOffsetStart;
	float testPadOffsetEnd;

	float testPadScaleStart;
	float testPadScaleEnd;
	float testPadScaleXMax;
	float testPadScaleYMax;

	float testPadSpeedRealfix; // 控件实际滑动距离和总滑动距离的比值

	int bgHolePadWay = 1;

	int weekDayWidth;
	int topPadHeight;
	int bgHolePadwidth;
	int timePickerBLayoutWidth;
	int currentTagPadIndex = -1;

	int tagIconWidth = -1;
	int tagIconHeight = -1;

	/*public void iniTagPad() {
		for (int i = 0; i < tvTaq.length; i++) {
			tvTaq[i].setVisibility(View.INVISIBLE);
		}
		// recyleTagPad();

		for (int i = 0; i < tvTaq.length; i++) {
			Form temp = Form.getForm((form) / 4, i);
			final int index = i;
			if (temp != null && i != tvTaq.length - 1) {
				tvTaq[i].setVisibility(View.VISIBLE);

				tvTaq[i].setBackgroundDrawable(temp
						.getSettingPageTagDrawable(tvTaq[i],android.R.attr.state_pressed));
				tvTaq[i].getBackground().setColorFilter(
						temp.getSettingPageTagColorFilter(), Mode.MULTIPLY);

				tvTaq[i].setText(temp.getTitleName());
				tvTaq[i].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						MyLogger.e("aaaax", "aaa");
						changeCurrentIndex(index);
						if (form == (form / 4) * 4 + index) {
							mhandler.post(new Runnable() {
								@Override
								public void run() {
									refreshTagPad();
									return;
								}
							});
						}
						form = (form / 4) * 4 + index;
						mForm = Form.getForm(form / 4, index);
						save2(false);
						changeToStyle(index);
					}
				});

				tvTaq[i].setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						MyLogger.e("aaaax", ""
								+ (event.getAction() == event.ACTION_UP));
						if (event.getAction() != event.ACTION_UP) {
							// if (((TextView) v).getCompoundDrawables()[0] !=
							// null) {
							// ((TextView) v).getCompoundDrawables()[0]
							// .setAlpha(255);
							// }
						} else {
							changeCurrentIndex(index);
							refreshTagPad();
						}
						return false;
					}
				});

			} else {
				tvTaq[i].setVisibility(View.VISIBLE);
				tvTaq[i].setText("获取更多");
				tvTaq[i].setTextColor(Color.WHITE);
				tvTaq[i].setGravity(Gravity.CENTER);

				tvTaq[i].setBackgroundResource(Form.getGetMoreTagBg());
				tvTaq[i].setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						AlarmApplication.getApp().showToast(
								"更多主题正在制作中，请关注最新版本或者官方微博获取最新信息");
					}
				});
				break;
			}
		}

	}

*/	Bitmap btp1, btp2;

	private void changeCurrentIndex(int newIndex) {
		if (newIndex == currentTagPadIndex)
			return;
		currentTagPadIndex = newIndex;

	}

	public void refreshTagPad() {
		MyLogger.e("aaaa", "refreshTagPad :" + currentTagPadIndex);
		for (int i = 0; i < tvTaq.length - 1; i++) {
			if (i == currentTagPadIndex) {// alpha is 40
				MyLogger.e("aaaa", "refreshTagPad : setPressed" + i + " "
						+ true);
				tvTaq[i].setPressed(true);
				// tvTaq[i].getBackground().setAlpha(255);
				if (tvTaq[i].getCompoundDrawables()[0] != null) {
					// tvTaq[i].getCompoundDrawables()[0].setAlpha(255);
					tvTaq[i].getCompoundDrawables()[0].setColorFilter(null);
				}
			} else {
				MyLogger.e("aaaa", "refreshTagPad : setPressed" + i + " "
						+ false);
				tvTaq[i].setPressed(false);
				// tvTaq[i].getBackground().setAlpha(120);
				if (tvTaq[i].getCompoundDrawables()[0] != null) {
					tvTaq[i].getCompoundDrawables()[0].setColorFilter(
							Color.GRAY, Mode.MULTIPLY);
				}
			}
			tvTaq[i].invalidate();
		}
	}

	public void changeToStyle(final String formId) {
		if (btp1 != null) {
			BitmapWorker.recyleBitmap(btp1);
			btp1 = null;
		}

		if (btp2 != null) {
			BitmapWorker.recyleBitmap(btp2);
		}
		MyLogger.e("aaaa", "changeToStyle " + formId);
		// bgHolePad.setImageBitmap(null);
		bgHolePad.setBackgroundDrawable(null);
		// topButton.setImageBitmap(null);
		
		btp1 = Form.getBitmapByRes(mForm.getSettingPageHolePadBg(), mForm.getTitleName(), (int) (1080*AlarmApplication.getApp().getFitRateX()), (int) (1700*AlarmApplication.getApp().getFitRateY()));

		bgHolePad.setImageBitmap(btp1);
		bgHolePad.setColorFilter(mForm.getSettingPageHolePadColorFilter(),
				Mode.MULTIPLY);

		topButton.setImageDrawable(mForm.getSettingPageTopBtnBgDrawable( (int) (720*AlarmApplication.getApp().getFitRateX()),(int) (300*AlarmApplication.getApp().getFitRateY()),0));
		topButton.setColorFilter(mForm.getSettingPageTopBtnColorFilter(),
				Mode.MULTIPLY);

		// InputStream isb = this.getResources().openRawResource(
		// mForm.getSettingPageBg());
		// BitmapFactory.Options optionsb = new BitmapFactory.Options();
		// options.inJustDecodeBounds = false;
		// btp2 = BitmapFactory.decodeStream(isb, null, optionsb);
		btp2 =  Form.getBitmapByRes(mForm.getSettingPageBg(), mForm.getTitleName(), mainFrame.getWidth(), mainFrame.getHeight());
//				BitmapWorker.getBitmap(mForm.getSettingPageBgStream(),
//				mainFrame.getWidth(), mainFrame.getHeight());
		BitmapDrawable mBitmapDrawable = new BitmapDrawable(btp2);
		mainFrame.setBackgroundDrawable(mBitmapDrawable);
		// mainFrame.setBackgroundResource(mForm.getSettingPageBg());

		// btn_weekDaySet[0][1]
		// .setBackground(mForm.getSettingPagButtonSelector());
		if (mForm.getSettingPageSetButtonSelector() != null) {
			for (int x = 0; x < btn_weekDaySet.length; x++) {
				for (int y = 0; y < btn_weekDaySet[0].length; y++) {
					if (btn_weekDaySet[x][y] != null) {
						btn_weekDaySet[x][y].setBackgroundDrawable(mForm
								.getSettingPageSetButtonSelector());
					}
				}
			}
		} else {
			for (int x = 0; x < btn_weekDaySet.length; x++) {
				for (int y = 0; y < btn_weekDaySet[0].length; y++) {
					if (btn_weekDaySet[x][y] != null) {
						btn_weekDaySet[x][y]
								.setBackgroundDrawable(getResources()
										.getDrawable(
												R.drawable.btn_alarmset_weekdaychoose));
					}
				}
			}
		}

		mhandler.post(new Runnable() {

			@Override
			public void run() {
				refreshTagPad();
				System.gc();
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		refreshButtonState(-1, -1);
		refreshTagPad();
	}

	@Override
	protected void onPause() {
		super.onPause();
		MyLogger.e("aaaa", "onpause");
		if(mDetailsFragment!=null){
			mDetailsFragment.onPause();
		}
		recyleCache();
	}

	private void inibgHolePad() {
		LayoutParams l = ((LinearLayout.LayoutParams) bgHolePad
				.getLayoutParams());
		bgHolePadWeight = l.weight;

		// l = ((LinearLayout.LayoutParams) bgHolePadView.getLayoutParams());
		// bgHolePadViewWeight = l.weight;

		bgHolePadWeightMax = bgHolePadWeight;
		bgHolePadwidth = l.width;
	}

	public void iniTimePicker() {
		LayoutParams l = ((LinearLayout.LayoutParams) timePickerBlayout
				.getLayoutParams());
		timePickerBLayoutWeight = l.weight;

		l = ((LinearLayout.LayoutParams) timePickerBlayoutView
				.getLayoutParams());
		timePickerBLayoutViewWeight = l.weight;

		l = ((LinearLayout.LayoutParams) timePickerAlayout.getLayoutParams());
		timePickerALayoutWeight = l.weight;

		l = ((LinearLayout.LayoutParams) timePickerAlayoutView
				.getLayoutParams());
		timePickerALayoutViewWeight = l.weight;

		timePickerALayoutWeightMax = timePickerALayoutWeight;
		timePickerBLayoutWeightMax = timePickerBLayoutWeight;

	}

	@Override
	protected void onResume() {
		super.onResume();
		// loadDay(m_nOccursDay);
		if(mDetailsFragment!=null){
			mDetailsFragment.onResume();
		}
		MyLogger.e("aaaa", "onResume");
	}

	int btn_weekDaySetChangeDataWorkDays[][] = { { 1, 1, 0 }, { 2, 0, 1 },
			{ 2, 0, 1 }, { 2, 1, 1 }, { 2, 2, 1 }, { 3, 0, 1 }, { 3, 1, 1 },
			{ 3, 2, 1 }, { 3, 2, 0 } };
	int btn_weekDaySetChangeDataWorkAll[][] = { { 1, 1, 1 }, { 2, 0, 1 },
			{ 2, 0, 1 }, { 2, 1, 1 }, { 2, 2, 1 }, { 3, 0, 1 }, { 3, 1, 1 },
			{ 3, 2, 1 }, { 3, 2, 1 } };
	int btn_weekDaySetChangeDataWorkAllNot[][] = { { 1, 1, 0 }, { 2, 0, 0 },
			{ 2, 0, 0 }, { 2, 1, 0 }, { 2, 2, 0 }, { 3, 0, 0 }, { 3, 1, 0 },
			{ 3, 2, 0 }, { 3, 2, 0 } };

	public void refreshButtonState(int m, int n) {

		synchronized (o) {

			if (m == 0 && n == 0) {
				for (int i = 0; i < btn_weekDaySetChangeDataWorkDays.length; i++) {
					btn_weekDaySetState[btn_weekDaySetChangeDataWorkDays[i][0]][btn_weekDaySetChangeDataWorkDays[i][1]] = btn_weekDaySetChangeDataWorkDays[i][2] == 1;
				}
			} else if (m == 0 && n == 1) {

			} else if (m == 1 && n == 0) {
				for (int i = 0; i < btn_weekDaySetChangeDataWorkAll.length; i++) {
					btn_weekDaySetState[btn_weekDaySetChangeDataWorkAll[i][0]][btn_weekDaySetChangeDataWorkAll[i][1]] = btn_weekDaySetChangeDataWorkAll[i][2] == 1;
				}
			} else if (m == 1 && n == 2) {
				for (int i = 0; i < btn_weekDaySetChangeDataWorkAllNot.length; i++) {
					btn_weekDaySetState[btn_weekDaySetChangeDataWorkAllNot[i][0]][btn_weekDaySetChangeDataWorkAllNot[i][1]] = btn_weekDaySetChangeDataWorkAllNot[i][2] == 1;
				}
			}

			int pressdCount = 0;
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 3; y++) {
					if (btn_weekDaySet[x][y] != null) {
						if (btn_weekDaySetState[x][y] == false) {
							btn_weekDaySet[x][y].setPressed(false);
						} else {

							btn_weekDaySet[x][y].setPressed(true);
							if (x > 1 || (x == 1 && y == 1)) {
								pressdCount++;
							}

						}
					}
				}
			}

			if (pressdCount == 7) {
				btn_weekDaySetState[0][0] = false;
				// btn_weekDaySetState[0][1]=false;
				btn_weekDaySetState[1][0] = true;
				btn_weekDaySetState[1][2] = false;
			} else if (pressdCount == 0) {
				btn_weekDaySetState[0][0] = false;
				// btn_weekDaySetState[0][1]=false;
				btn_weekDaySetState[1][0] = false;
				btn_weekDaySetState[1][2] = true;
			} else if (pressdCount == 5 && btn_weekDaySetState[1][1] == false
					&& btn_weekDaySetState[3][2] == false) {
				btn_weekDaySetState[0][0] = true;
				// btn_weekDaySetState[0][1]=false;
				btn_weekDaySetState[1][0] = false;
				btn_weekDaySetState[1][2] = false;
			} else {
				btn_weekDaySetState[0][0] = false;
				// btn_weekDaySetState[0][1]=true;
				btn_weekDaySetState[1][0] = false;
				btn_weekDaySetState[1][2] = false;
			}

			for (int x = 0; x < 2; x++) {
				for (int y = 0; y < 3; y++) {
					if (btn_weekDaySet[x][y] != null) {
						if (btn_weekDaySetState[x][y] == false) {
							btn_weekDaySet[x][y].setPressed(false);
						} else {
							btn_weekDaySet[x][y].setPressed(true);
						}
					}

				}
			}

			if (btn_weekDaySetState[1][2] == false) {
				btn_weekDaySet[1][2].setText("反选");
			} else {
				btn_weekDaySet[1][2].setText("响一次");
			}

			if (btn_weekDaySetState[1][0] == false) {
				btn_weekDaySet[1][0].setText("全选");
			} else {
				btn_weekDaySet[1][0].setText("每天响");
			}

			if (btn_weekDaySetState[0][1] == false) {
				btn_weekDaySet[0][1].setText("已关闭");
				for (int x = 0; x < 4; x++) {
					for (int y = 0; y < 3; y++) {
						if (btn_weekDaySet[x][y] != null && !(x == 0 && y == 1))

							setAlpha(btn_weekDaySet[x][y], 0.45f);
						// btn_weekDaySet[x][y].setAlpha(0.45f);
					}
				}
			} else {
				btn_weekDaySet[0][1].setText("已开启");
				for (int x = 0; x < 4; x++) {
					for (int y = 0; y < 3; y++) {
						if (btn_weekDaySet[x][y] != null/*
														 * && !(x == 0 && y ==
														 * 1)
														 */)
							setAlpha(btn_weekDaySet[x][y], 0.75f);
						// btn_weekDaySet[x][y].setAlpha(0.75f);
					}
				}
			}

			// getParam();
			// refreshLeftPad();
		}
	}

	public void iniWeekDay() {
		LayoutParams l = ((LinearLayout.LayoutParams) weekDayBLayout
				.getLayoutParams());
		weekDayBLayoutWeight = l.weight;

		l = ((LinearLayout.LayoutParams) weekDayBLayoutView.getLayoutParams());
		weekDayBLayoutViewWeight = l.weight;

		l = ((LinearLayout.LayoutParams) weekDayALayout.getLayoutParams());
		weekDayALayoutWeight = l.weight;

//		l = ((LinearLayout.LayoutParams) weekDayALayoutView.getLayoutParams());
//		weekDayALayoutViewWeight = l.weight;

		weekDayALayoutWeightMax = weekDayALayoutWeight;
		weekDayBLayoutWeightMax = weekDayBLayoutWeight;
	}

	public void iniTopButton() {
		LayoutParams l = ((LinearLayout.LayoutParams) topButtonALayout
				.getLayoutParams());
		topButtonALayoutWeight = l.weight;

		l = ((LinearLayout.LayoutParams) topButtonALayoutView.getLayoutParams());
		topButtonALayoutViewWeight = l.weight;

		l = ((LinearLayout.LayoutParams) topButtonBLayout.getLayoutParams());
		topButtonBLayoutWeight = l.weight;

		l = ((LinearLayout.LayoutParams) topButtonBLayoutView.getLayoutParams());
		topButtonBLayoutViewWeight = l.weight;

		topButtonALayoutWeightMax = topButtonALayoutWeight;
		topButtonBLayoutWeightMax = topButtonBLayoutWeight;

	}

	float buf;
	LayoutParams mLayoutParamsbuff;

	public void onMoveCal(float currentWeight) {

		buf = currentWeight * timePickerBWay;
		timePickerBLayoutWeight = timePickerBLayoutWeightMax + buf;
		MyLogger.e("onMoveCal", timePickerBLayoutWeight + " " + buf);
		timePickerBLayoutWeight = fixRange(timePickerBLayoutWeightMax,
				timePickerBWay, timePickerBLayoutWeight);

		timePickerBLayoutViewWeight = 1 - timePickerBLayoutWeight;

		mLayoutParamsbuff = ((LinearLayout.LayoutParams) timePickerBlayout
				.getLayoutParams());
		mLayoutParamsbuff.weight = timePickerBLayoutWeight;

		mLayoutParamsbuff = ((LinearLayout.LayoutParams) timePickerBlayoutView
				.getLayoutParams());
		mLayoutParamsbuff.weight = timePickerBLayoutViewWeight;

		// =====
		if (currentWeight > moveWeightMax)
			currentWeight = moveWeightMax;
		// --------------------
		buf = currentWeight * timePickerAWay;
		timePickerALayoutWeight = timePickerALayoutWeightMax + buf;
		timePickerALayoutWeight = fixRange(timePickerALayoutWeightMax,
				timePickerAWay, timePickerALayoutWeight);

		timePickerALayoutViewWeight = 1 - timePickerALayoutWeight;

		mLayoutParamsbuff = ((LinearLayout.LayoutParams) timePickerAlayout
				.getLayoutParams());
		mLayoutParamsbuff.weight = timePickerALayoutWeight;

		mLayoutParamsbuff = ((LinearLayout.LayoutParams) timePickerAlayoutView
				.getLayoutParams());
		mLayoutParamsbuff.weight = timePickerALayoutViewWeight;

		if (currentWeight < topButtonALayoutOffsetStart) {
			buf = 0;
		}
		if (currentWeight > topButtonALayoutOffsetEnd) {
			buf = (topButtonALayoutOffsetEnd - topButtonALayoutOffsetStart)
					* topButtonAWay * topButtonALayoutSpeedRealfix;

		} else {
			buf = (currentWeight - topButtonALayoutOffsetStart) * topButtonAWay
					* topButtonALayoutSpeedRealfix;
		}
		// buf =
		// (moveWeight-topButtonALayoutOffsetStart)*topButtonAWay*topButtonALayoutSpeedRealfix;
		topButtonALayoutWeight = topButtonALayoutWeightMax + buf;

		topButtonALayoutWeight = fixRange(topButtonALayoutWeightMax,
				topButtonAWay, topButtonALayoutWeight);

		topButtonALayoutViewWeight = 1 - topButtonALayoutWeight;

		mLayoutParamsbuff = ((LinearLayout.LayoutParams) topButtonALayout
				.getLayoutParams());
		mLayoutParamsbuff.weight = topButtonALayoutWeight;

		mLayoutParamsbuff = ((LinearLayout.LayoutParams) topButtonALayoutView
				.getLayoutParams());
		mLayoutParamsbuff.weight = topButtonALayoutViewWeight;

		if (currentWeight < topButtonALayoutOffsetStart) {
			buf = 0;
		}
		if (currentWeight > topButtonALayoutOffsetEnd) {
			buf = (topButtonALayoutOffsetEnd - topButtonALayoutOffsetStart)
					* topButtonAWay * topButtonALayoutSpeedRealfix;

		} else {
			buf = (currentWeight - topButtonALayoutOffsetStart) * topButtonAWay
					* topButtonALayoutSpeedRealfix;
		}
		buf = currentWeight * weekDayBWay;
		weekDayBLayoutWeight = weekDayBLayoutWeightMax + buf;
		weekDayBLayoutWeight = fixRange(weekDayBLayoutWeightMax, weekDayBWay,
				weekDayBLayoutWeight);

		weekDayBLayoutViewWeight = 1 - weekDayBLayoutWeight;

		mLayoutParamsbuff = ((LinearLayout.LayoutParams) weekDayBLayout
				.getLayoutParams());
		mLayoutParamsbuff.weight = weekDayBLayoutWeight;

		mLayoutParamsbuff = ((LinearLayout.LayoutParams) weekDayBLayoutView
				.getLayoutParams());
		mLayoutParamsbuff.weight = weekDayBLayoutViewWeight;

		// tvDayMon.getBackground().setAlpha(tvDayMon.getBackground().geta(int)
		// ((moveWeight-moveWeight)*255));
		// tvDayMon.setTextColor(tvDayMon.getTextColors().withAlpha((int)moveWeight*255));

		if (currentWeight < bgHolePadOffsetStart) {
			buf = 0;
		}
		if (currentWeight > bgHolePadOffsetEnd) {
			buf = (bgHolePadOffsetEnd - bgHolePadOffsetStart) * bgHolePadWay
					* bgHolePadSpeedRealfix;

		} else {
			buf = (currentWeight - bgHolePadOffsetStart) * bgHolePadWay
					* bgHolePadSpeedRealfix;
		}
		// buf = moveWeight*bgHolePadWay;
		bgHolePadWeight = bgHolePadWeightMax + buf;
		bgHolePadWeight = fixRange(bgHolePadWeightMax, bgHolePadWay,
				bgHolePadWeight);

		// bgHolePadViewWeight = 1 - bgHolePadWeight;

		mLayoutParamsbuff = ((LinearLayout.LayoutParams) bgHolePad
				.getLayoutParams());
		mLayoutParamsbuff.weight = bgHolePadWeight;
		// mLayoutParamsbuff.leftMargin=300;

		// mLayoutParamsbuff = ((LinearLayout.LayoutParams) bgHolePadView
		// .getLayoutParams());
		// mLayoutParamsbuff.weight=bgHolePadViewWeight;

	}

	public void onMoveCal2(float currentWeight) {
		int xmargin;
		int ymargin;
		int margin;
		if (currentWeight < timePickerBLayoutWeightOffsetStart) {
			buf = 0;
		} else if (currentWeight > timePickerBLayoutWeightOffsetEnd) {
			buf = (timePickerBLayoutWeightOffsetEnd - timePickerBLayoutWeightOffsetStart)
					* timePickerBWay * timePickerBLayoutSpeedRealfix;
			;

		} else {
			buf = (currentWeight - timePickerBLayoutWeightOffsetStart)
					* timePickerBWay * timePickerBLayoutSpeedRealfix;
			;
		}
		// buf = moveWeight*bgHolePadWay;
		margin = (int) (buf * timePickerBLayoutWidth);
		if (timePickerBLayoutWidth + margin < 0
				&& timePickerBlayout.getScrollX() < 0) {

		} else {
			timePickerBlayout.scrollTo(margin, timePickerBlayout.getScrollY());
		}
		// x.set(timePickerBlayout, margin);

		if (currentWeight < topButtonALayoutOffsetStart) {
			buf = 0;
		}
		if (currentWeight > topButtonALayoutOffsetEnd) {
			buf = (topButtonALayoutOffsetEnd - topButtonALayoutOffsetStart)
					* topButtonAWay * topButtonALayoutSpeedRealfix;

		} else {
			buf = (currentWeight - topButtonALayoutOffsetStart) * topButtonAWay
					* topButtonALayoutSpeedRealfix;
		}

		margin = (int) (buf / 0.2 * topPadHeight);
		// mLayoutParamsbuff.bottomMargin =margin;
		topButtonALayout.scrollTo(topButtonALayout.getScrollX(), margin);

		if (topPadHeight + margin < 0 && topButtonALayout.getScrollY() < 0) {

		} else {
			topButtonALayout.scrollTo(topButtonALayout.getScrollX(), margin);
		}
		// y.set(topButtonALayout, margin);
		// MyLogger.e("bottomMargin", " weigtht:" + topButtonALayoutWeight + " "
		// + mLayoutParamsbuff.bottomMargin + " " + buf + " "
		// + topPadHeight);
		/*
		 * mLayoutParamsbuff.weight=topButtonALayoutWeight;
		 * 
		 * mLayoutParamsbuff = ((LinearLayout.LayoutParams) topButtonALayoutView
		 * .getLayoutParams());
		 * mLayoutParamsbuff.weight=topButtonALayoutViewWeight;
		 */

		if (currentWeight < weekDayALayoutOffsetStart) {
			buf = 0;
		} else if (currentWeight > weekDayALayoutOffsetEnd) {
			buf = (weekDayALayoutOffsetEnd - weekDayALayoutOffsetStart)
					* weekDayAWay * weekDayALayoutSpeedRealfix;
		} else {
			buf = (currentWeight - weekDayALayoutOffsetStart) * weekDayAWay
					* weekDayALayoutSpeedRealfix;
		}
		margin = (int) (buf * weekDayWidth);
		weekDayALayout.scrollTo(margin, weekDayALayout.getScrollY());
		weekDayALayoutView.scrollTo(margin, weekDayALayout.getScrollY());
		
		if (currentWeight < hotInfoOffsetStart) {
			mDetailsFragment.setScaleRate(0);
		} else if (currentWeight > hotInfoOffsetEnd) {
			mDetailsFragment.setScaleRate(1);
		} else {
			mDetailsFragment.setScaleRate((currentWeight-hotInfoOffsetStart)/(hotInfoOffsetEnd-hotInfoOffsetStart));
		}

//		if (weekDayWidth - margin < 0
//				&& weekDayALayout.getScrollX() > weekDayWidth) {
//
//		} else {
//			weekDayALayout.scrollTo(margin, weekDayALayout.getScrollY());
//		}

		if (currentWeight < bgHolePadOffsetStart) {
			buf = 0;
		} else if (currentWeight > bgHolePadOffsetEnd) {
			buf = (bgHolePadOffsetEnd - bgHolePadOffsetStart) * bgHolePadWay
					* bgHolePadSpeedRealfix;

		} else {
			buf = (currentWeight - bgHolePadOffsetStart) * bgHolePadWay
					* bgHolePadSpeedRealfix;
		}

		margin = (int) (buf * bgHolePadwidth);
		// mLayoutParamsbuff.leftMargin =margin;

		bgHolePadpad.scrollTo((int) (-margin), bgHolePadpad.getScrollY());
		MyLogger.e("bgHolePad", " margin:" + margin);
		if (bgHolePadwidth - margin < 0
				&& bgHolePadpad.getScrollX() > bgHolePadwidth) {

		} else {
			bgHolePadpad.scrollTo((int) (-margin), bgHolePadpad.getScrollY());
		}

		// x.set(bgHolePad, (int) (-margin));
		// mLayoutParamsbuff = ((RelativeLayout.LayoutParams) bgHolePadpad
		// .getLayoutParams());

		// mLayoutParamsbuff.weight = bgHolePadWeight;
		// mLayoutParamsbuff.leftMargin=300;

		// mLayoutParamsbuff = ((LinearLayout.LayoutParams) bgHolePadView
		// .getLayoutParams());
		// mLayoutParamsbuff.weight=bgHolePadViewWeight;

		if (currentWeight < testPadOffsetStart) {
			buf = 0;
		} else if (currentWeight > testPadOffsetEnd) {
			buf = testPadOffsetEnd - testPadOffsetStart;

		} else {
			buf = (currentWeight - testPadOffsetStart);
		}
		// buf = moveWeight*bgHolePadWay;
		xmargin = (int) (((1 - buf / (testPadOffsetEnd)) * testPadOffsetX));
		ymargin = (int) (((1 - buf / (testPadOffsetEnd)) * testPadOffsetY));
		testPad.scrollTo(xmargin, -ymargin);

		if (currentWeight < testPadScaleStart) {
			buf = 0;
		} else if (currentWeight > testPadScaleEnd) {
			buf = testPadScaleEnd - testPadScaleStart;

		} else {
			buf = (currentWeight - testPadScaleStart);
		}
		float xScale = testPadScaleXMax + (1 - testPadScaleXMax)
				* (buf / (testPadScaleEnd - testPadScaleStart));
		float yScale = (testPadScaleYMax + (1 - testPadScaleYMax)
				* (((buf / (testPadScaleEnd - testPadScaleStart)))));
		if (Build.VERSION.SDK_INT >= 11) {
			settingPad.setScaleX(xScale);
			settingPad.setScaleY(yScale);
		}
		// MyLogger.e("testPad", currentWeight + " " + xScale + " " + yScale +
		// " "
		// + testPadScaleXMax + " " + buf + " " + testPadScaleEnd + " "
		// + testPadScaleStart);

	}

	public void iniTestPadAnmi() {
		// topButtonALayoutSpeed = 3.0f;// 三倍速，缓入缓出
		testPadOffsetStart = 0;
		testPadOffsetEnd = 0.6f;

		testPadScaleStart = 0.0f;
		testPadScaleEnd = 0.6f;
		// topButtonALayoutSpeedRealfix = (1 - topButtonALayoutWeightMax)
		// / (topButtonALayoutOffsetEnd - topButtonALayoutOffsetStart);

	}

	public void iniTopPadAnmi() {
		topButtonALayoutSpeed = 3.0f;// 三倍速，缓入缓出
		topButtonALayoutOffsetStart = 0;
		topButtonALayoutOffsetEnd = 0.2f;
		topButtonALayoutSpeedRealfix = (1 - topButtonALayoutWeightMax)
				/ (topButtonALayoutOffsetEnd - topButtonALayoutOffsetStart);

	}

	public void inibgHolePadAnmi() {
		bgHolePadOffsetStart = 0;
		bgHolePadOffsetEnd = moveWeightMax;
		bgHolePadSpeedRealfix = (1 - bgHolePadWeightMax)
				/ (bgHolePadOffsetEnd - bgHolePadOffsetStart); // 控件实际滑动距离和总滑动距离的比值

	}

	public void iniTimePickerAnmi() {
		timePickerBLayoutWeightOffsetStart = 0;
		timePickerBLayoutWeightOffsetEnd = moveWeightMax / 3;
		timePickerBLayoutSpeedRealfix = (1) / (timePickerBLayoutWeightOffsetEnd - timePickerBLayoutWeightOffsetStart); // 控件实际滑动距离和总滑动距离的比值

	}

	public void iniWeekDayAnmi() {

		weekDayALayoutOffsetStart = 0.07f;
		weekDayALayoutOffsetEnd = 0.348f;
		weekDayALayoutSpeedRealfix = (1) / (weekDayALayoutOffsetEnd - weekDayALayoutOffsetStart); // 控件实际滑动距离和总滑动距离的比值
		// weekDayTextViewAlphaSpeed =

	}
	
	public void iniHotInfoAnmi() {

		hotInfoOffsetStart = 0.01f;
		hotInfoOffsetEnd = 0.348f;
		hotInfoOffSpeedRealfix= (1) / (hotInfoOffsetEnd - hotInfoOffsetStart); 
	//	weekDayALayoutSpeedRealfix = (1) / (hotInfoOffsetEnd - hotInfoOffsetStart); // 控件实际滑动距离和总滑动距离的比值
		// weekDayTextViewAlphaSpeed =

	}

	public float fixRange(float rangMax, int way, float value) {
		if (way > 0) {
			if (value > 1) {
				value = 1;
			}
			if (value < rangMax) {
				value = rangMax;
			}
		} else {
			if (value < 0) {
				value = 0;
			}
			if (value > rangMax) {
				value = rangMax;
			}
		}

		return value;
	}

	Object lock = new Object();

	Handler mhandler = new Handler();
	Timer timer = new Timer();

	/*
	 * @Override public boolean dispatchTouchEvent(MotionEvent ev) { if
	 * (ev.getAction() == MotionEvent.ACTION_DOWN) {
	 * super.dispatchTouchEvent(ev); } if (ev.getAction() ==
	 * MotionEvent.ACTION_UP) { super.dispatchTouchEvent(ev); } return
	 * false;//super.dispatchTouchEvent(ev); }
	 */

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (super.onTouchEvent(event) == false) {

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				firstX = event.getX();
				firstY = event.getY();
				lastX = firstX;
				lastY = firstY;

				makeCache();

			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				firstX = -1;
				firstY = -1;
				lastX = firstX;
				lastY = firstY;

				synchronized (timer) {
					if (moveWeight < 0.4) {
						dropDownAnim();
					} else {
						dropUpAnim();
					}
				}
				refreshLayout(moveWeight);

			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				// MyLogger.e("moveWeight", "start " + moveWeight);
				float currentX = event.getX();
				float currentY = event.getY();

				if ((lastX != -1 && lastY != -1)) {

					float deltX = -(lastX - currentX);
					float deltY = (lastY - currentY);
					if (deltX != 0) {
						deltX = Math.abs((float) ((deltX) / Math.cos(Math
								.atan(deltY / deltX))))
								* ((deltX + deltY) > 0 ? 1 : -1);
						moveWeight += deltX * moveScare;
					} else {
						moveWeight += deltY * moveScare;
					}

					moveWeight = fixRange(moveWeightMax, -1, moveWeight);
					// if (Math.abs(deltX) > 5) {
				}
				lastY = currentY;
				lastX = currentX;
				refreshLayout(moveWeight);
				// LayoutParams l = ((LinearLayout.LayoutParams)
				// timePickerBlayout
				// .getLayoutParams());
				// l.weight += 0.001 * deltX;
				// timePickerBlayout.setLayoutParams(l);
				//
				// LayoutParams l2 = ((LinearLayout.LayoutParams)
				// timePickerBlayoutView
				// .getLayoutParams());
				// l2.weight -= 0.001 * deltX / 10;
				// timePickerBlayoutView.setLayoutParams(l2);
				//
				// MyLogger.e("moveWeight", "over" + moveWeight);
				// }

			}
			// return true;

			// return
		}
		return false;
	}

	public void makeCache() {
		settingPad.setDrawingCacheEnabled(true);
		// settingPad.buildDrawingCache();
		bgHolePad.setDrawingCacheEnabled(true);
		// bgHolePad.buildDrawingCache();

		topButton.setDrawingCacheEnabled(true);
		// topButton.buildDrawingCache();

		topButtonOverlook.setDrawingCacheEnabled(true);
		// topButtonOverlook.buildDrawingCache();
		// weekDayBLayout.setDrawingCacheEnabled(true);
		weekDayALayout.setDrawingCacheEnabled(true);
	}

	public void recyleCache() {
		settingPad.destroyDrawingCache();
		bgHolePad.destroyDrawingCache();
		topButton.destroyDrawingCache();
		topButtonOverlook.destroyDrawingCache();
		weekDayALayout.destroyDrawingCache();

	}

	public void refreshLayout(float weight) {
		if (moveWeight < 0.1) {
		
			if (Build.VERSION.SDK_INT < 11) {
				settingPad.setVisibility(View.GONE);
			} else {

				if (settingPad.getVisibility() == View.VISIBLE) {
					settingPad.setVisibility(View.INVISIBLE);
					MyLogger.e("aaaa", "INVISIBLE");
				}
			}
			
			getParam();
			if(CheckhasChangedConfig()){
				save2();
				refreshLeftPad();
				//dropDownAnim();
				
			}

		} else {
			closeHelpMessage();
			
			if (Build.VERSION.SDK_INT < 11) {
				settingPad.setVisibility(View.VISIBLE);
			} else {
				if (settingPad.getVisibility() == View.INVISIBLE) {
					settingPad.setVisibility(View.VISIBLE);
				}
			}
			setAlpha(settingPad, (float) ((weight - 0.1) * 2));
		}
		// settingPad.setVisibility(View.VISIBLE);
		// settingPad.setAlpha((float) ((weight + 0.4) ));
		onMoveCal2(weight);
		mainFrame.requestLayout();
		// weekDayALayout.requestLayout();
		// topButtonALayout.requestLayout();
		// timePickerAlayout.requestLayout();
		// bgHolePad.requestLayout();
	}

	private void initialParam(final AlarmBean alarm) {
		mForm = Form.getForm(alarm.getFormIndex());
		if (mForm != null) {
			// alarm.setUri(f.getAlarmMusic());
			// alarm.setRingMode(Symbol.RING_SYSTEM);

		}

		m_alarmIndex = alarm.getIndex();

		// init alarm name
		m_alarmType = alarm.getType();
		m_alarmName = alarm.getName();

		// m_alarmWeekly = alarm.getDay();
		m_alarmOccurs = "";

		m_nOccursDay = alarm.getDay();

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(alarm.getTime());

		m_alarmHours = c.get(Calendar.HOUR_OF_DAY);
		m_alarmMinutes = c.get(Calendar.MINUTE);
		MyLogger.e("aaaa", "getAlarm" + Utils.getCalendarTime(alarm.getTime())
				+ " " + m_alarmHours + " " + m_alarmMinutes);
		m_nRingMode = alarm.getRingMode();

		m_alarmRingMode = Utils.formatRingMode(m_nRingMode);
		m_ringUri = alarm.getUri();

		m_nInterval = alarm.getSleepDelay();

		m_bEnable = alarm.getEnabled();
		btn_weekDaySetState[0][1] = m_bEnable;
		m_bVibrate = alarm.getVibrate();
		m_gameEnable = alarm.getEnableGame();
		form = alarm.getFormIndex();
		CheckhasChangedConfig();
	}



	private void reSetTimeSetPad() {
		btn_weekDaySetState = new boolean[4][3];
		btn_weekDaySetState[0][1] = m_bEnable;
		loadDay(m_nOccursDay);

		wlh.setCurrentItem(m_alarmHours, false);
		wlm.setCurrentItem(m_alarmMinutes, false);
	}

	public void setAlarmName(final AlarmBean alarm) {
		View layout = LayoutInflater.from(this).inflate(
				R.layout.set_alarm_names, null);

		final Dialog dialog = new AlertDialog.Builder(this)
				.setIcon(R.drawable.logo)
				.setTitle(getString(R.string.alarm_name_title)).setView(layout)
				.setNegativeButton(getString(R.string.cancel), null).create();

		dialog.show();

		RadioGroup rgAlarmName = (RadioGroup) layout
				.findViewById(R.id.rgAlarmNames);
		final RadioButton rbXiaoNao = (RadioButton) layout
				.findViewById(R.id.rbXiaoNao);
		final RadioButton rbXiaoLongMao = (RadioButton) layout
				.findViewById(R.id.rbXiaoLongMao);
		final RadioButton rbXiaoMeiQiu = (RadioButton) layout
				.findViewById(R.id.rbXiaoMeiQiu);
		final RadioButton rbManulInput = (RadioButton) layout
				.findViewById(R.id.rbManulInput);

		switch (m_alarmType) {
		case Symbol.TYPE_XIAONAO:
			m_tvAlarmName.setText(getString(R.string.alarm_name_xiaonao));
			rbXiaoNao.setChecked(true);
			break;
		case Symbol.TYPE_XIAOLONGMAO:
			m_tvAlarmName.setText(getString(R.string.alarm_name_xiaolongmao));
			rbXiaoLongMao.setChecked(true);
			break;
		case Symbol.TYPE_XIAOMEIQIU:
			m_tvAlarmName.setText(getString(R.string.alarm_name_xiaomeiqiu));
			rbXiaoMeiQiu.setChecked(true);
			break;
		case Symbol.TYPE_MANULINPUT:
			m_tvAlarmName.setText(alarm.getName());
			rbManulInput.setChecked(true);
			break;
		}

		rgAlarmName.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == rbXiaoNao.getId()) {
					m_alarmName = rbXiaoNao.getText().toString();
					m_alarmType = Symbol.TYPE_XIAONAO;

				} else if (checkedId == rbXiaoLongMao.getId()) {
					m_alarmName = rbXiaoLongMao.getText().toString();
					m_alarmType = Symbol.TYPE_XIAOLONGMAO;

				} else if (checkedId == rbXiaoMeiQiu.getId()) {
					m_alarmName = rbXiaoMeiQiu.getText().toString();
					m_alarmType = Symbol.TYPE_XIAOMEIQIU;

				} else if (checkedId == rbManulInput.getId()) {
					m_bNameManulShowed = true;
					setManulName(alarm);
					m_alarmType = Symbol.TYPE_MANULINPUT;
				}

				m_tvAlarmName.setText(m_alarmName);
				dialog.dismiss();

				alarm.setName(m_alarmName);
				alarm.setType(m_alarmType);
			}
		});

		rbXiaoNao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbXiaoNao.isChecked()) {
					dialog.dismiss();
				}
			}
		});

		rbXiaoLongMao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbXiaoLongMao.isChecked()) {
					dialog.dismiss();
				}
			}
		});

		rbXiaoMeiQiu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbXiaoMeiQiu.isChecked()) {
					dialog.dismiss();
				}
			}
		});

		rbManulInput.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbManulInput.isChecked()) {
					dialog.dismiss();

					// already selected on manul input type
					if (!m_bNameManulShowed) {
						setManulName(alarm);
					}
					// reset
					m_bNameManulShowed = false;
				}
			}
		});
	}

	public void setGame(final AlarmBean alarm) {
		View layout = LayoutInflater.from(this).inflate(
				R.layout.set_alarm_game, null);
		final CheckBox cb = (CheckBox) layout.findViewById(R.id.gameEnable);
		cb.setChecked(m_gameEnable);
		final Dialog dialog = new AlertDialog.Builder(this)
				.setIcon(R.drawable.logo)
				.setTitle(getString(R.string.alarm_name_title))
				.setView(layout)
				.setNegativeButton(getString(R.string.cancel), null)
				.setNeutralButton("确定",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								m_gameEnable = cb.isChecked();
								m_tvAlarmName
										.setText(m_gameEnable == false ? "关闭"
												: "开启");
							}

						})

				.create();

		dialog.show();

	}

	TextView tvDayMon;
	TextView tvDayTue;
	TextView tvDayWed;
	TextView tvDayThu;
	TextView tvDayFri;
	TextView tvDaySat;
	TextView tvDaySun;

	/*
	 * int alphaTvDayMonbg,alphaTvDayMonTx; int alphaTvDayTuebg,alphaTvDayTueTx;
	 * int alphaTvDayWedbg,alphaTvDayWedTx; int alphaTvDayMonbg,alphaTvDayMonTx;
	 * int alphaTvDayMonbg,alphaTvDayMonTx; int alphaTvDayMonbg,alphaTvDayMonTx;
	 * int alphaTvDayMonbg,alphaTvDayMonTx;
	 */

	public void setManulName(final AlarmBean alarm) {
		View layout = LayoutInflater.from(this).inflate(
				R.layout.set_manul_name, null);
		final EditText etAlarmName = (EditText) layout
				.findViewById(R.id.etAlarmName);

		new AlertDialog.Builder(this)
				.setIcon(R.drawable.logo)
				.setTitle(getString(R.string.please_input))
				.setView(layout)
				.setPositiveButton(getString(R.string.set),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								m_alarmName = etAlarmName.getText().toString();
								m_tvAlarmName.setText(m_alarmName);

								alarm.setName(m_alarmName);
							}
						}).create().show();
	}

	/*
	 * private void setTimeDialog(final AlarmBean alarm) { new
	 * TimePickerDialog(this, new OnTimeSetListener() {
	 * 
	 * @Override public void onTimeSet(TimePicker view, int hourOfDay, int
	 * minute) { // alarm.hour = hourOfDay; // alarm.minute = minute;
	 * 
	 * m_alarmHours = hourOfDay; m_alarmMinutes = minute;
	 * 
	 * // m_tvAlarmTime.setText((m_alarmHours < 10 ? "0" + m_alarmHours :
	 * m_alarmHours) + ":" // + (m_alarmMinutes < 10 ? "0" + m_alarmMinutes :
	 * m_alarmMinutes)); } }, m_alarmHours, m_alarmMinutes,
	 * DateFormat.is24HourFormat(this)).show(); }
	 */

	private void setWeeklyDialog(final AlarmBean alarm) {
		View layout = LayoutInflater.from(this).inflate(
				R.layout.set_alarm_weeklymode, null);

		final Dialog dialog = new AlertDialog.Builder(this)
				.setTitle(getString(R.string.alarm_weekly_title))
				.setView(layout).setIcon(0)
				.setNegativeButton(getString(R.string.cancel), null).create();

		dialog.show();

		RadioGroup rgWeeklyMode = (RadioGroup) layout
				.findViewById(R.id.rgWeeklyMode);
		final RadioButton rbWeeklyEveryday = (RadioButton) layout
				.findViewById(R.id.rbWeeklyEveryday);
		final RadioButton rbWeeklyWeekday = (RadioButton) layout
				.findViewById(R.id.rbWeeklyWeekday);
		final RadioButton rbWeeklyManul = (RadioButton) layout
				.findViewById(R.id.rbWeeklyManual);
		final RadioButton rbWeeklyOnce = (RadioButton) layout
				.findViewById(R.id.rbWeeklyOnce);

		// set buttons states
		m_nOccursDay = alarm.getDay();
		switch (m_nOccursDay) {
		case Symbol.DAY_ALLDAY:
			rbWeeklyEveryday.setChecked(true);
			break;
		case Symbol.DAY_WEEKDAY:
			rbWeeklyWeekday.setChecked(true);
			break;
		case Symbol.DAY_ONCE:
			rbWeeklyOnce.setChecked(true);
			break;
		default:
			rbWeeklyManul.setChecked(true);
			break;
		}

		rgWeeklyMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == rbWeeklyEveryday.getId()) {
					m_alarmWeekly = rbWeeklyEveryday.getText().toString();
					m_nOccursDay = Symbol.DAY_ALLDAY;

				} else if (checkedId == rbWeeklyWeekday.getId()) {
					m_alarmWeekly = rbWeeklyWeekday.getText().toString();
					m_nOccursDay = Symbol.DAY_WEEKDAY;

				} else if (checkedId == rbWeeklyManul.getId()) {
					m_bWeeklyManulSelected = true;
					setWeeklyManulDialog(alarm);

				} else if (checkedId == rbWeeklyOnce.getId()) {
					m_alarmWeekly = rbWeeklyOnce.getText().toString();
					m_nOccursDay = Symbol.DAY_ONCE;

				}

				Log.i("alarm", "set day: " + m_nOccursDay);

				alarm.setDay(m_nOccursDay);
				m_tvAlarmWeeklyMode.setText(m_alarmWeekly);

				dialog.dismiss();
			}
		});

		rbWeeklyEveryday.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbWeeklyEveryday.isChecked()) {
					dialog.dismiss();
				}
			}
		});

		rbWeeklyWeekday.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbWeeklyWeekday.isChecked()) {
					dialog.dismiss();
				}
			}
		});

		rbWeeklyManul.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

				if (!m_bWeeklyManulSelected) {
					setWeeklyManulDialog(alarm);
				}
				// reset
				m_bWeeklyManulSelected = false;
			}
		});

		rbWeeklyOnce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbWeeklyOnce.isChecked()) {
					dialog.dismiss();
				}
			}
		});
	}

	private void setWeeklyManulDialog(final AlarmBean alarm) {
		View layout = LayoutInflater.from(this).inflate(
				R.layout.alarm_weekly_selector, null);
		final CheckBox cbMon = (CheckBox) layout.findViewById(R.id.cbMon);
		final CheckBox cbTue = (CheckBox) layout.findViewById(R.id.cbTue);
		final CheckBox cbWed = (CheckBox) layout.findViewById(R.id.cbWed);
		final CheckBox cbThu = (CheckBox) layout.findViewById(R.id.cbThu);
		final CheckBox cbFri = (CheckBox) layout.findViewById(R.id.cbFri);
		final CheckBox cbSat = (CheckBox) layout.findViewById(R.id.cbSat);
		final CheckBox cbSun = (CheckBox) layout.findViewById(R.id.cbSun);

		// set checkboxs status
		int day = alarm.getDay();

		// occurs only once
		if ((day & Symbol.BIT_ONCE) == 0) {
			if ((day & Symbol.DAY_SUN) != 0) {
				cbSun.setChecked(true);
			}
			if ((day & Symbol.DAY_MON) != 0) {
				cbMon.setChecked(true);
			}
			if ((day & Symbol.DAY_TUE) != 0) {
				cbTue.setChecked(true);
			}
			if ((day & Symbol.DAY_WED) != 0) {
				cbWed.setChecked(true);
			}
			if ((day & Symbol.DAY_THU) != 0) {
				cbThu.setChecked(true);
			}
			if ((day & Symbol.DAY_FRI) != 0) {
				cbFri.setChecked(true);
			}
			if ((day & Symbol.DAY_SAT) != 0) {
				cbSat.setChecked(true);
			}
		}

		// final StringBuffer strbuf = new StringBuffer();

		new AlertDialog.Builder(this)
				.setTitle(getString(R.string.alarm_weekly_title))
				.setView(layout)
				.setIcon(0)
				.setPositiveButton(getString(R.string.set),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Log.i(TAG, "which : " + which);
								int day = 0;

								if (cbSun.isChecked()) {
									day |= Symbol.DAY_SUN;
								}
								if (cbMon.isChecked()) {
									day |= Symbol.DAY_MON;
								}
								if (cbTue.isChecked()) {
									day |= Symbol.DAY_TUE;
								}
								if (cbWed.isChecked()) {
									day |= Symbol.DAY_WED;
								}
								if (cbThu.isChecked()) {
									day |= Symbol.DAY_THU;
								}
								if (cbFri.isChecked()) {
									day |= Symbol.DAY_FRI;
								}
								if (cbSat.isChecked()) {
									day |= Symbol.DAY_SAT;
								}

								Log.i(TAG, "set day: " + day);
								alarm.setDay(day);

								m_nOccursDay = day;
								m_tvAlarmWeeklyMode.setText(Utils
										.FormatDay(day));
							}
						}).setNegativeButton(getString(R.string.cancel), null)
				.create().show();
	}

	private void setOccursDialog(final AlarmBean alarm) {
		View layout = LayoutInflater.from(this).inflate(
				R.layout.set_alarm_occurs, null);

		final Dialog dialog = new AlertDialog.Builder(this)
				.setTitle(getString(R.string.alarm_occurs_title))
				.setView(layout)
				.setNegativeButton(getString(R.string.cancel), null).create();

		dialog.show();

		RadioGroup rgOccursMode = (RadioGroup) layout
				.findViewById(R.id.rgOccursMode);
		final RadioButton rbOccursOnce = (RadioButton) layout
				.findViewById(R.id.rbOccursOnce);
		final RadioButton rbOccurs3 = (RadioButton) layout
				.findViewById(R.id.rbOccurs3);
		final RadioButton rbOccurs5 = (RadioButton) layout
				.findViewById(R.id.rbOccurs5);
		final RadioButton rbOccursN = (RadioButton) layout
				.findViewById(R.id.rbOccursN);

		rgOccursMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				if (checkedId == rbOccursOnce.getId()) {
					m_alarmOccurs = rbOccursOnce.getText().toString();
					m_nOccurs = 1;

				} else if (checkedId == rbOccurs3.getId()) {
					m_alarmOccurs = rbOccurs3.getText().toString();
					m_nOccurs = 3;

				} else if (checkedId == rbOccurs5.getId()) {
					m_alarmOccurs = rbOccurs5.getText().toString();
					m_nOccurs = 5;

				} else if (checkedId == rbOccursN.getId()) {
					m_alarmOccurs = rbOccursN.getText().toString();
					m_nOccurs = -1;

				}

				m_tvAlarmOccursMode.setText(m_alarmOccurs);
				dialog.dismiss();
			}
		});
	}

	private void setIntervalDialog(final AlarmBean alarm) {
		View layout = LayoutInflater.from(this).inflate(
				R.layout.set_alarm_interval, null);

		final Dialog dialog = new AlertDialog.Builder(this)
				.setTitle(getString(R.string.alarm_interval_title))
				.setView(layout)
				.setNegativeButton(getString(R.string.cancel), null).create();

		dialog.show();

		RadioGroup rgIntervalMode = (RadioGroup) layout
				.findViewById(R.id.rgIntervalMode);
		final RadioButton rbAfter5min = (RadioButton) layout
				.findViewById(R.id.rbAfter5min);
		final RadioButton rbAfter10min = (RadioButton) layout
				.findViewById(R.id.rbAfter10min);
		final RadioButton rbAfter15min = (RadioButton) layout
				.findViewById(R.id.rbAfter15min);

		rgIntervalMode
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup arg0, int checkedId) {
						if (checkedId == rbAfter5min.getId()) {
							m_alarmInterval = rbAfter5min.getText().toString();
							m_nInterval = 5;

						} else if (checkedId == rbAfter10min.getId()) {
							m_alarmInterval = rbAfter10min.getText().toString();
							m_nInterval = 10;

						} else if (checkedId == rbAfter15min.getId()) {
							m_alarmInterval = rbAfter15min.getText().toString();
							m_nInterval = 15;

						}

						m_tvAlarmOccursMode.setText(m_alarmInterval);
						dialog.dismiss();
					}
				});
	}

	private void setAlarmRingDialog(final AlarmBean alarm) {
		View layout = LayoutInflater.from(this).inflate(
				R.layout.set_alarm_ring, null);

		final Dialog dialog = new AlertDialog.Builder(this)
				.setTitle(getString(R.string.alarm_ring_title)).setView(layout)
				.setIcon(0).setPositiveButton(getString(R.string.set), null)
				.setNegativeButton(getString(R.string.cancel), null).create();

		dialog.show();

		RadioGroup rgRingMode = (RadioGroup) layout
				.findViewById(R.id.rgRingMode);

		final RadioButton rbRingSilent = (RadioButton) layout
				.findViewById(R.id.rbSilent);
		final RadioButton rbRingSystem = (RadioButton) layout
				.findViewById(R.id.rbSystemRing);
		// final RadioButton rbRingOptional = (RadioButton)
		// layout.findViewById(R.id.rbRingOptional);

		final CheckBox cbVibrate = (CheckBox) layout
				.findViewById(R.id.cbVibrate);

		// show ring mode status
		final int mode = alarm.getRingMode();
		switch (mode) {
		case Symbol.RING_SLIENT:
			rbRingSilent.setChecked(true);
			break;
		case Symbol.RING_SYSTEM:
			rbRingSystem.setChecked(true);
			break;
		/*
		 * case Symbol.RING_OPTIONAL: rbRingOptional.setChecked(true); break;
		 */
		}

		// show vibrate status
		if (alarm.getVibrate()) {
			cbVibrate.setChecked(true);
		}

		rgRingMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				if (checkedId == rbRingSilent.getId()) {
					m_alarmRingMode = rbRingSilent.getText().toString();
					m_nRingMode = Symbol.RING_SLIENT;
					m_ringUri = null;

				} else if (checkedId == rbRingSystem.getId()) {
					m_alarmRingMode = rbRingSystem.getText().toString();
					m_nRingMode = Symbol.RING_SYSTEM;

				}/*
				 * else if ( checkedId == rbRingOptional.getId() ) {
				 * m_alarmRingMode = rbRingOptional.getText().toString();
				 * m_nRingMode = Symbol.RING_OPTIONAL;
				 * 
				 * }
				 */

				m_tvAlarmRingMode.setText(m_alarmRingMode);
				alarm.setRingMode(m_nRingMode);

				dialog.dismiss();
			}
		});

		rbRingSilent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbRingSilent.isChecked()) {
					dialog.dismiss();
				}
				m_ringUri = null;
			}
		});

		rbRingSystem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbRingSystem.isChecked()) {
					dialog.dismiss();
				}
				openSystemRingtone(alarm);
			}
		});

		cbVibrate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cbVibrate.isChecked()) {
					m_bVibrate = true;
					alarm.setVibrate(true);
				} else {
					m_bVibrate = false;
					alarm.setVibrate(false);
				}
			}
		});
	}

	private void openSystemRingtone(AlarmBean alarm) {
		Intent intent = new Intent(
				android.media.RingtoneManager.ACTION_RINGTONE_PICKER);
		intent.putExtra(android.media.RingtoneManager.EXTRA_RINGTONE_TYPE,
				android.media.RingtoneManager.TYPE_RINGTONE);

		// startActivity(intent);
		startActivityForResult(intent, TYPE_ALARM);
	}

	private OnClickListener oclButtons = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btFinish:
				save();
				finish();
				break;

			case R.id.btBack:
				finish();
				break;
			}
		}
	};

	private void save() {
		m_alarmHours = wlh.getCurrentItem();
		MyLogger.e("aaaa", "xxxxxxx");
		m_alarmMinutes = wlm.getCurrentItem();
		Calendar cl = Utils.setCalendarTime(m_alarmHours, m_alarmMinutes);
		Log.i(TAG, "save--- ringMode: " + m_nRingMode + " , uri: " + m_ringUri);

		// enable the alarm
		m_bEnable = true;

		AlarmBean alarmBean = new AlarmBean(m_alarmType, m_alarmName,
				Symbol.INTERVAL_DAY, m_nOccursDay, m_alarmIndex,
				cl.getTimeInMillis(), m_bEnable, m_nRingMode, m_ringUri,
				m_nInterval, m_bVibrate, m_gameEnable, form);
		AlarmProvider.geInstance(this).setAlarm(alarmBean);

		// 重置闹铃时间
		AlarmSet.setAlarm(this);

	}

	private void save2(boolean doNotifaction) {
		getParam();
		MyLogger.e("save", " " + m_alarmIndex + " " + form);
		Form.getForm(form).loadSound();
		Calendar cl = Utils.setCalendarTime(m_alarmHours, m_alarmMinutes);
		AlarmBean alarmBean = new AlarmBean(m_alarmType, m_alarmName,
				Symbol.INTERVAL_DAY, m_nOccursDay, m_alarmIndex,
				cl.getTimeInMillis(), m_bEnable, m_nRingMode, m_ringUri,
				m_nInterval, m_bVibrate, m_gameEnable, form);
		AlarmProvider.geInstance(this).setAlarm(alarmBean);

		if (doNotifaction)
			// 重置闹铃时间
			AlarmSet.setAlarm(this);
		ADManager.normalClick();
	}

	private void save2() {
		save2(true);

	}
	
	private int last_m_nOccursDay=-1;
	private int last_m_alarmHours=-1;
	private int last_m_m_alarmMinutes=-1;
	private boolean  last_m_bEnable=false;
	
	private boolean CheckhasChangedConfig(){
		boolean hasChange = false;
		if(last_m_nOccursDay!=m_nOccursDay){
			hasChange = true;
			last_m_nOccursDay = m_nOccursDay;
		}
		
		if(last_m_alarmHours!=m_alarmHours){
			hasChange = true;
			last_m_alarmHours = m_alarmHours;
		}
		
		if(last_m_m_alarmMinutes!=m_alarmMinutes){
			hasChange = true;
			last_m_m_alarmMinutes = m_alarmMinutes;
		}
		
		if(last_m_bEnable!=m_bEnable){
			hasChange = true;
			last_m_bEnable = m_bEnable;
		}
		return hasChange;
	}
	
	private void getParam() {
		MyLogger.e("aaaa", "getParam");
		// m_alarmHours = mTimePicker.getCurrentHour();
		// m_alarmMinutes = mTimePicker.getCurrentMinute();
		m_alarmHours = wlh.getCurrentItem();
		m_alarmMinutes = wlm.getCurrentItem();
		Log.i(TAG, "save--- ringMode: " + m_nRingMode + " , uri: " + m_ringUri);
		// enable the alarm
		m_bEnable = btn_weekDaySetState[0][1];

		m_nOccursDay = saveDay();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// this.save2();
			timer.cancel();
		//	this.finish();
			// System.gc();
			// return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		MyLogger.e("aaaa", "onDestroy");
		super.onDestroy();
		bgHolePad.setBackgroundDrawable(null);
		topButton.setImageBitmap(null);
		BitmapWorker.recyleBitmap(btp1);
		BitmapWorker.recyleBitmap(btp2);
		btp1 = null;
		btp2 = null;
		//recyleTagPad(true);
	}

//	private void recyleTagPad(boolean isAll) {
//		for (int i = 0; i < tvTaq.length; i++) {
//			Form temp = Form.getForm((form) / 4, i);
//			if (temp != null && i != tvTaq.length - 1) {
//
//				Drawable[] md = tvTaq[i].getCompoundDrawables();
//
//				tvTaq[i].setCompoundDrawablesWithIntrinsicBounds(null, null,
//						null, null);
//				if (md != null && md.length > 0) {
//					for (int md_i = 0; md_i < md.length; md_i++) {
//						if (md[md_i] != null) {
//							if (md[md_i] instanceof BitmapDrawable) {
//								BitmapWorker
//										.recyleBitmap(((BitmapDrawable) md[md_i])
//												.getBitmap());
//								md[md_i] = null;
//								// ((BitmapDrawable)md[md_i]).getBitmap().recycle();
//							}
//						}
//					}
//				}
//				if (isAll) {
//					Drawable mb = tvTaq[i].getBackground();
//					tvTaq[i].setBackgroundDrawable(null);
//					if (mb != null) {
//
//						if (mb instanceof BitmapDrawable) {
//							Bitmap b = ((BitmapDrawable) mb).getBitmap();
//							if (b != null) {
//								BitmapWorker.recyleBitmap(b);
//								mb = null;
//							}
//
//						}
//					}
//				}
//
//			} else {
//				break;
//			}
//		}
//	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i(TAG, "resultCode : " + resultCode + ", requestCode: "
				+ requestCode);
		if (resultCode != RESULT_OK) {

			return;
		} else {
			Uri uri = data
					.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
			Log.i(TAG, "uri : " + uri);

			if (uri != null) {
				m_ringUri = uri.toString();
				/*
				 * if ( m_ringUri != null ) { m_alarm.setUri(m_ringUri); }
				 */

				// switch ( requestCode ) {
				// case TYPE_ALARM:
				// RingtoneManager.setActualDefaultRingtoneUri(mContext,
				// RingtoneManager.TYPE_ALARM, uri);
				// break;
				// }
			}
		}
	}

	Object o = new Object();

	class WeekDaySet implements OnClickListener {
		int x;
		int y;

		@Override
		public void onClick(View v) {
			synchronized (o) {
				Point p = (Point) v.getTag();
				x = (Integer) p.x;
				y = (Integer) p.y;
				MyLogger.e("aaaa", x + " " + y);
				btn_weekDaySetState[x][y] = btn_weekDaySetState[x][y] == false ? true
						: false;
				// if (x == 0 && y == 0) {
				// btn_weekDaySetState[0][1] = false;
				// } else if (x == 0 && y == 1) {
				// btn_weekDaySetState[0][0] = false;
				// } else if (x == 1 && y == 0) {
				// btn_weekDaySetState[0][0] = false;
				// }
				if (x != 0 || y != 1) {
					btn_weekDaySetState[0][1] = true;
				}

			}
			if (Build.VERSION.SDK_INT < 11) {
				mhandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						refreshButtonState(x, y);
					}
				}, 300);
			} else {
				mhandler.post(new Runnable() {

					@Override
					public void run() {
						refreshButtonState(x, y);
					}
				});
			}
			if (x == 0) {
				if (y == 0) {

				}
			}

		}
	}

	public int saveDay() {
		int day = 0;
		if (btn_weekDaySetState[1][2] == true) {
			day = Symbol.DAY_ONCE;
			return day;
		}
		if (btn_weekDaySetState[1][1] == true) {
			day |= Symbol.DAY_SUN;
		}
		if (btn_weekDaySetState[2][0]) {
			day |= Symbol.DAY_MON;
		}
		if (btn_weekDaySetState[2][1]) {
			day |= Symbol.DAY_TUE;
		}
		if (btn_weekDaySetState[2][2]) {
			day |= Symbol.DAY_WED;
		}
		if (btn_weekDaySetState[3][0]) {
			day |= Symbol.DAY_THU;
		}
		if (btn_weekDaySetState[3][1]) {
			day |= Symbol.DAY_FRI;
		}
		if (btn_weekDaySetState[3][2]) {
			day |= Symbol.DAY_SAT;
		}
		MyLogger.e("testaaa",
				"" + Integer.toHexString(day) + " " + Utils.FormatDay(day));
		return day;
	}

	public void loadDay(int day) {
		MyLogger.e("testaaa",
				"" + Integer.toHexString(day) + " " + Utils.FormatDay(day));
		if (day != Symbol.DAY_ONCE) {

			if ((day | Symbol.DAY_SUN) == day) {
				btn_weekDaySetState[1][1] = true;
			}
			if ((day | Symbol.DAY_MON) == day) {
				btn_weekDaySetState[2][0] = true;
			}
			if ((day | Symbol.DAY_TUE) == day) {
				btn_weekDaySetState[2][1] = true;
			}
			if ((day | Symbol.DAY_WED) == day) {
				btn_weekDaySetState[2][2] = true;
			}
			if ((day | Symbol.DAY_THU) == day) {
				btn_weekDaySetState[3][0] = true;
			}
			if ((day | Symbol.DAY_FRI) == day) {
				btn_weekDaySetState[3][1] = true;
			}
			if ((day | Symbol.DAY_SAT) == day) {
				btn_weekDaySetState[3][2] = true;
			}
		}
		refreshButtonState(-1, -1);
		// return day;
	}

	private void refreshLeftPad() {

		DecimalFormat df = new DecimalFormat("00");
		if (m_bEnable == false) {
			tvAlarmStyle.setText("未开启");
			tvAlarmTime.setTextColor(Color.GRAY);
			tvAlarmStyle.setTextColor(Color.GRAY);
			// tvAlarmWorkDay.setTextColor(Color.GRAY);
		} else {
			tvAlarmStyle
					.setText("已开启(" + (Utils.FormatDay(m_nOccursDay)) + ")");
			tvAlarmStyle.setText("已开启");
			tvAlarmTime.setTextColor(Color.BLACK);
			tvAlarmStyle.setTextColor(Color.BLACK);
			// tvAlarmWorkDay.setTextColor(Color.BLACK);
		}
		tvAlarmTime.setText(df.format(m_alarmHours) + ":"
				+ df.format(m_alarmMinutes));

		int lightColor;
		if (m_bEnable == true) {
			lightColor = 0xff31ced8;
		} else {
			lightColor = Color.GRAY;
		}
		if (m_nOccursDay == Symbol.DAY_ONCE) {
			String nextDay = Utils.getNextTimeString(m_alarmHours,
					m_alarmMinutes);
			String timeLoc = Utils.getTimeLocationString(m_alarmHours,
					m_alarmMinutes);
			tvDateDay[7].setTextSize(TypedValue.COMPLEX_UNIT_PX,
					dateDayTextSizeLarge);
			tvDateDay[7].setTextColor(Color.WHITE);
			tvDateDay[7].setBackgroundColor(lightColor);
			for (int i = 0; i < 7; i++) {
				tvDateDay[i].setTextSize(TypedValue.COMPLEX_UNIT_PX,
						dateDayTextSize);
				tvDateDay[i].setTextColor(Color.BLACK);
				tvDateDay[i].setBackgroundColor(Color.WHITE);
			}
			// tvAlarmWorkDay.setText(nextDay + timeLoc);
		} else {
			tvDateDay[7].setTextSize(TypedValue.COMPLEX_UNIT_PX,
					dateDayTextSize);
			tvDateDay[7].setTextColor(Color.BLACK);
			tvDateDay[7].setBackgroundColor(Color.WHITE);
			for (int i = 0; i < 7; i++) {
				if (Utils.isDayAlarmOn(m_nOccursDay, dateDayIndex[i])) {
					tvDateDay[i].setTextSize(TypedValue.COMPLEX_UNIT_PX,
							dateDayTextSizeLarge);
					tvDateDay[i].setTextColor(Color.WHITE);
					tvDateDay[i].setBackgroundColor(lightColor);
				} else {
					tvDateDay[i].setTextSize(TypedValue.COMPLEX_UNIT_PX,
							dateDayTextSize);
					tvDateDay[i].setTextColor(Color.BLACK);
					tvDateDay[i].setBackgroundColor(Color.WHITE);
				}
			}

		}
	}

	private String convertStringToVertical(String content) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < content.length(); i++) {
			sb.append(content.charAt(i));
			sb.append("\n");
		}

		return sb.toString();
	}

	int count = 0;

	@Override
	public void onAnimationUpdate(final ValueAnimator animation) {

		mhandler.post(new Runnable() {

			@Override
			public void run() {

				// float buf = (float)(
				// ((Math.abs(bgHolePad.getScrollX())/((float)bgHolePadwidth))/bgHolePadWay/bgHolePadSpeedRealfix+bgHolePadOffsetStart)
				// +10 * moveScare);
				// MyLogger.e("moveWeight", bgHolePad.getScrollX()+" "
				// +" "+bgHolePadwidth+" "+ moveWeight+" "+buf);
				// if(buf==moveWeight) return;

				MyLogger.e("cccd", "start" + moveWeight + " "
						+ (moveWeight - lastWeight2));
				lastWeight2 = moveWeight;

				moveWeight = (Float) (animation.getAnimatedValue());

				moveWeight = fixRange(moveWeightMax, -1, moveWeight);
				// if(count==0&&moveWeight!=0){
				refreshLayout(moveWeight);
				// count++;
				// }
				// if (moveWeight >= moveWeightMax) {
				// timer.cancel();
				// }
			}
		});

	}
}
