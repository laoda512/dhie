package com.tavx.C9Alarm;

import java.util.List;

import util.AnimationsContainer;
import util.AnimationsContainer.FramesSequenceAnimation;
import util.BitmapWorker;
import util.OnAnimationStoppedListener;
import util.ResponseManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.bmob.v3.listener.CloudCodeListener;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.CenterCirView.AlarmButtonClickListener;
import com.tavx.C9Alarm.Form.Form;
import com.tavx.C9Alarm.Manager.ADManager;
import com.tavx.C9Alarm.cloud.CloudManager;
import com.umeng.update.UmengUpdateAgent;

public class boxAlarmActivity extends BaseActivity implements RefreshAlarm {
	private final static String TAG = "alarm";

	RelativeLayout m_layoutMain;

	// ListView m_alarmList;

	Media mMedia;
	TextView m_btStopSleepAlarm;
	TextView m_btComment;
	TextView m_btSetting;
	RelativeLayout tl, tr, br, bl;
	TextView tvtlname, tvtltime, tvtlring;
	TextView tvblname, tvbltime, tvblring;
	TextView tvtrname, tvtrtime, tvtrring;
	TextView tvbrname, tvbrtime, tvbrring;
	ImageView blm, tlm, trm, brm;
	ImageView imageView_cirtl, imageView_cirtr, imageView_cirbl,
			imageView_cirbr;
	CenterCirView cv;
	Form mForm[];
	private List<AlarmBean> m_listBean;
	RelativeLayout mainLayout ;
	
	private boolean hasCheckUpdate = false;
	// Button m_btChangeBg;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLogger.e("aaaa", "boxAlarm_onCreate");
		long a = System.currentTimeMillis();
		long b;
		// set no title mode
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainm);
		// HolidayReceiver.setAlarm(this);
		mainLayout=(RelativeLayout) findViewById(R.id.layoutMain);
		AlarmApplication.getApp().showError(
				"欢迎回来," + AlarmApplication.getApp().getUserNickName());

		mMedia = Media.getInstance(this);
		b = System.currentTimeMillis();
		MyLogger.e("initfirst Use time", "" + (b - a));
		a = b;

		initUI();
		b = System.currentTimeMillis();
		MyLogger.e("initUI Use time", "" + (b - a));
		a = b;
		setListener();
		b = System.currentTimeMillis();
		MyLogger.e("setListener Use time", "" + (b - a));
		a = b;
		setUI();
		b = System.currentTimeMillis();
		MyLogger.e("setUI Use time", "" + (b - a));
		a = b;
		setForm();
		b = System.currentTimeMillis();
		MyLogger.e("setForm Use time", "" + (b - a));
		a = b;
		
		if(!hasCheckUpdate){
		UmengUpdateAgent.update(this);
		hasCheckUpdate = true;
		}
		
		CloudManager.queryLock(this);

		
	}

	Bitmap cirbgtl;
	Bitmap cirbgtlx;
	Bitmap cirbgbl;
	Bitmap cirbgblx;
	Bitmap cirbgtr;
	Bitmap cirbgtrx;
	Bitmap cirbgbr;
	Bitmap cirbgbrx;

	private View bottomLeftPad;

	private View bottomRightPad;

	public void loadBitmap() {
		if (cirbgtl == null) {
			cirbgtl = Utils.getBitmapByStream(this,
					mForm[0].getCirRes(Form.TOP_LEFT, true));
			cirbgtlx = Utils.getBitmapByStream(this,
					mForm[0].getCirRes(Form.TOP_LEFT, false));
			cirbgbl = Utils.getBitmapByStream(this,
					mForm[1].getCirRes(Form.BOTTOM_LEFT, true));
			cirbgblx = Utils.getBitmapByStream(this,
					mForm[1].getCirRes(Form.BOTTOM_LEFT, false));

			cirbgtr = Utils.getBitmapByStream(this,
					mForm[2].getCirRes(Form.TOP_RIGHT, true));
			cirbgtrx = Utils.getBitmapByStream(this,
					mForm[2].getCirRes(Form.TOP_RIGHT, false));
			cirbgbr = Utils.getBitmapByStream(this,
					mForm[3].getCirRes(Form.BOTTOM_RIGHT, true));
			cirbgbrx = Utils.getBitmapByStream(this,
					mForm[3].getCirRes(Form.BOTTOM_RIGHT, false));
		}
	}

	public void recyleBitmap() {
		if (cirbgtl != null) {

			imageView_cirtl.setImageBitmap(null);
			imageView_cirtr.setImageBitmap(null);
			imageView_cirbl.setImageBitmap(null);
			imageView_cirbr.setImageBitmap(null);
			
			BitmapWorker.recyleBitmap(cirbgtl);
			BitmapWorker.recyleBitmap(cirbgtlx);
			BitmapWorker.recyleBitmap(cirbgbl);
			BitmapWorker.recyleBitmap(cirbgblx);
			
			BitmapWorker.recyleBitmap(cirbgtr);
			BitmapWorker.recyleBitmap(cirbgtrx);
			BitmapWorker.recyleBitmap(cirbgbr);
			BitmapWorker.recyleBitmap(cirbgbrx);

			cirbgtl = null;
			cirbgtlx = null;
			cirbgbl = null;
			cirbgblx = null;
			cirbgtr = null;
			cirbgtrx = null;
			cirbgbr = null;
			cirbgbrx = null;

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		MyLogger.e("aaaa", "boxAlarm_onResume");
		//loadBitmap();
		refreshUI();
		setForm();
		
		isOnButtonAnim = false;
		setBottomUI();
	}
	
	public void setBottomUI(){
		boolean isSleepAlarmOn=AlarmProvider.geInstance(this).isSleepAlarmEnable(0);
		if(isSleepAlarmOn==true){
			m_btStopSleepAlarm.setBackgroundResource(R.drawable.btn_lazyon);
		}else{
			m_btStopSleepAlarm.setBackgroundResource(R.drawable.btn_lazyoff);
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	//	mForm =null;
	}

	@Override
	protected void onStop() {
		MyLogger.e("aaaa", "onStop");
		super.onStop();
		recyleBitmap();
	}

	private void initUI() {
		// m_layoutMain = (RelativeLayout) findViewById(R.id.layoutMain);
		m_btStopSleepAlarm = (TextView) findViewById(R.id.btStop);
		m_btComment = (TextView) findViewById(R.id.btComment);
		m_btSetting = (TextView) findViewById(R.id.btSetting);
		 bottomLeftPad =  findViewById(R.id.leftPad);
		 bottomRightPad =  findViewById(R.id.rightPad);
		
		// tl =(RelativeLayout) findViewById(R.id.include1);
		// tvtlname = (TextView) findViewById(R.id.texttlname);
		// tvtlring = (TextView) findViewById(R.id.texttltime);
		// tvtltime = (TextView) findViewById(R.id.texttlring);
		tlm = (ImageView) findViewById(R.id.imageBttl);
		imageView_cirtl = (ImageView) findViewById(R.id.imageView_cirtl);

		// bl =(RelativeLayout) findViewById(R.id.include3);
		// tvblname = (TextView) findViewById(R.id.textblname);
		// tvbltime = (TextView) findViewById(R.id.textbltime);
		// tvblring = (TextView) findViewById(R.id.textblring);
		blm = (ImageView) findViewById(R.id.imageBtbl);
		imageView_cirtr = (ImageView) findViewById(R.id.imageView_cirtr);

		// tr =(RelativeLayout) findViewById(R.id.include2);
		// tvtrname = (TextView) findViewById(R.id.texttrname);
		// tvtrtime = (TextView) findViewById(R.id.texttrtime);
		// tvtrring = (TextView) findViewById(R.id.texttrring);
		trm = (ImageView) findViewById(R.id.imageBttr);
		imageView_cirbl = (ImageView) findViewById(R.id.imageView_cirbl);

		// br =(RelativeLayout) findViewById(R.id.include4);
		// tvbrname = (TextView) findViewById(R.id.textbrname);
		// tvbrtime = (TextView) findViewById(R.id.textbrtime);
		// tvbrring = (TextView) findViewById(R.id.textbrring);
		brm = (ImageView) findViewById(R.id.imageBtbr);
		imageView_cirbr = (ImageView) findViewById(R.id.imageView_cirbr);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int widthPixels = metrics.widthPixels;
		int heightPixels = metrics.heightPixels;
		
		cv = (CenterCirView) findViewById(R.id.analogClock1);
		
	    LayoutParams l= cv.getLayoutParams();
		l.width=widthPixels*210*2/1080;
		l.height=widthPixels*210*2/1080;
		cv.setLayoutParams(l);
		cv.setOnButtonClickListener(new AlarmButtonClickListener() {

			@Override
			public void onClick(int index) {
				boolean state = !m_listBean.get(index).getEnabled();
				m_listBean.get(index).setEnabled(state);
				setButtonState(index, state);
				refreshData(index);
			}
		});
		cv.setVisibility(View.VISIBLE);
		imageView_cirtl.setVisibility(View.GONE);
		imageView_cirbr.setVisibility(View.GONE);
		imageView_cirtr.setVisibility(View.GONE);
		imageView_cirbl.setVisibility(View.GONE);

	}

	private void setListener() {
		m_btStopSleepAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (AlarmProvider.geInstance(boxAlarmActivity.this).isSleepAlarmEnable(0)) {
					Utils.forceStopSleepAlarm(boxAlarmActivity.this);
				} else {
					//m_btStopSleepAlarm.setEnabled(false);
					int count = AlarmApplication.getApp().getUser().getLazyButtonOffClickCount()+1;
					AlarmApplication.getApp().getUser().setLazyButtonOffClickCount(count);
					AlarmApplication.getApp().showToast(""+ResponseManager.getResp(ResponseManager.INDEX_LAZY_BUTTON, count).content);
				}
				setBottomUI();
			
			}

		});
		bottomLeftPad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//FeedbackAgent agent = new FeedbackAgent(boxAlarmActivity.this);
				//agent.startFeedbackActivity();
				DownlistActivity.startActivity(boxAlarmActivity.this, DownlistActivity.TYPE_DOWNLOAD);
				ADManager.normalClick();
//				Intent i = new Intent();
//				i.setClass(boxAlarmActivity.this, DownlistActivity.class);
//				startActivity(i);
				
			}
		});

		bottomRightPad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				Intent i = new Intent(boxAlarmActivity.this,
//						SettingFragment.class);
//				startActivity(i);
				DownlistActivity.startActivity(boxAlarmActivity.this, DownlistActivity.TYPE_SETTING);
				ADManager.normalClick();
			}
		});
		// m_alarmList = (ListView) findViewById(R.id.alarmList);

		tlm.setOnClickListener(new KeyListener(0));
		imageView_cirtl.setOnClickListener(new StateKeyListener(0));
		trm.setOnClickListener(new KeyListener(1));
		imageView_cirtr.setOnClickListener(new StateKeyListener(1));
		blm.setOnClickListener(new KeyListener(2));
		imageView_cirbl.setOnClickListener(new StateKeyListener(2));
		brm.setOnClickListener(new KeyListener(3));
		imageView_cirbr.setOnClickListener(new StateKeyListener(3));

	}

	FramesSequenceAnimation anmi[] = new FramesSequenceAnimation[4];

	private void setForm() {
		mForm = new Form[4];
		mForm[0] = Form.getForm(m_listBean.get(0).getFormIndex());
		mForm[1] = Form.getForm(m_listBean.get(1).getFormIndex());
		mForm[2] = Form.getForm(m_listBean.get(2).getFormIndex());
		mForm[3] = Form.getForm(m_listBean.get(3).getFormIndex());
		mForm[0].loadSound();
		mForm[1].loadSound();
		mForm[2].loadSound();
		mForm[3].loadSound();
		
		
		 DisplayMetrics dm = new DisplayMetrics();
		 getWindowManager().getDefaultDisplay().getMetrics(dm);
		 int  screen_width = dm.widthPixels;//宽度
		 int  screen_height  = dm.heightPixels ;//高度
		float fitRateX = 1080/screen_width;
		float fitRateY = 1920/screen_height;
		int realWidth = (int)(540/fitRateX);
		int realHeight = (int)(900/fitRateY);
		
		if (mForm[0] != null) {

			// ib_anim.setImageResource(f.getAlarmAnim());
			anmi[0] = AnimationsContainer.getInstance().createSplashAnim(tlm,
					mForm[0].getBtnAnim(),realWidth,realHeight);
			anmi[0].setOverRun(false);
			// anim.start();
			// MyLogger.e("aaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaa");
		}
		if (mForm[1] != null) {
			anmi[1] = AnimationsContainer.getInstance().createSplashAnim(trm,
					mForm[1].getBtnAnim(),realWidth,realHeight);
			anmi[1].setOverRun(false);
			// MyLogger.e("aaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaa");
		}
		if (mForm[2] != null) {
			anmi[2] = AnimationsContainer.getInstance().createSplashAnim(blm,
					mForm[2].getBtnAnim(),realWidth,realHeight);
			anmi[2].setOverRun(false);
			// MyLogger.e("aaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaa");
		}
		if (mForm[3] != null) {
			anmi[3] = AnimationsContainer.getInstance().createSplashAnim(brm,
					mForm[3].getBtnAnim(),realWidth,realHeight);
			anmi[3].setOverRun(false);
			// MyLogger.e("aaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaa");
		} 
	}

	Handler mHandler = new Handler();

	private void setUI() {
		m_listBean = AlarmProvider.geInstance(this).getAlarms();

		try {
			cv.onAlarmChange(AlarmProvider.geInstance(boxAlarmActivity.this)
					.getNextAlarm().getIndex(),
					AlarmProvider.geInstance(boxAlarmActivity.this)
							.getNextAlarm().getTime(),-1, m_listBean);
		} catch (Exception e) {
			//e.printStackTrace();
			cv.onAlarmChange(-1, -1l, -1,m_listBean);
		}

		// tvtlname.setText(m_listBean.get(0).getName());
		// tvtltime.setText(Utils.getCalendarTime(m_listBean.get(0).getTime()));
		// tvtlring.setText(
		// Utils.formatRingMode(m_listBean.get(0).getRingMode()));

		// tvtrname.setText(m_listBean.get(1).getName());
		// tvtrtime.setText(Utils.getCalendarTime(m_listBean.get(1).getTime()));
		// tvtrring.setText(
		// Utils.formatRingMode(m_listBean.get(1).getRingMode()));
		//
		//
		//
		// tvblname.setText(m_listBean.get(2).getName());
		// tvbltime.setText(Utils.getCalendarTime(m_listBean.get(2).getTime()));
		// tvblring.setText(
		// Utils.formatRingMode(m_listBean.get(2).getRingMode()));
		//
		//
		//
		// tvbrname.setText(m_listBean.get(3).getName());
		// tvbrtime.setText(Utils.getCalendarTime(m_listBean.get(3).getTime()));
		// tvbrring.setText(
		// Utils.formatRingMode(m_listBean.get(3).getRingMode()));

	//	setButtonState();

//		if (AlarmProvider.geInstance(this).isSleepAlarmEnable(0)) {
//			m_btStopSleepAlarm.setEnabled(true);
//		} else {
//			m_btStopSleepAlarm.setEnabled(false);
//		}
		//
	}

	private void setButtonState() {
		for (int i = 0; i < 4; i++) {
			setButtonState(i, m_listBean.get(i).getEnabled());
		}
	}

	private void setButtonState(int i, boolean state) {
		ImageView iv = null;
		switch (i) {
		case 0: {
			iv = imageView_cirtl;
			if (state == true) {
				imageView_cirtl.setImageBitmap(cirbgtlx);
				// iv.setColorFilter(Color.BLACK, Mode.MULTIPLY);
			} else {
				imageView_cirtl.setImageBitmap(cirbgtl);
				// iv.setColorFilter(null);
			}
			break;
		}
		case 1: {
			iv = imageView_cirtr;
			if (state == true) {
				imageView_cirtr.setImageBitmap(cirbgtrx);
				// iv.setColorFilter(Color.YELLOW, Mode.MULTIPLY);
			} else {
				imageView_cirtr.setImageBitmap(cirbgtr);
				// iv.setColorFilter(Color.RED, Mode.MULTIPLY);
			}
			break;

		}
		case 2: {
			iv = imageView_cirbl;
			if (state == true) {
				imageView_cirbl.setImageBitmap(cirbgblx);
				// iv.setColorFilter(Color.RED, Mode.MULTIPLY);
			} else {
				imageView_cirbl.setImageBitmap(cirbgbl);
				iv.setColorFilter(null);
			}
			break;
		}
		case 3: {
			iv = imageView_cirbr;
			if (state == true) {
				imageView_cirbr.setImageBitmap(cirbgbrx);
				// iv.setColorFilter(Color.WHITE, Mode.MULTIPLY);
			} else {
				imageView_cirbr.setImageBitmap(cirbgbr);
				iv.setColorFilter(null);
			}
			break;
		}
		default:
			return;

		}
		int alpha = 255;
		if (state == false) {

			alpha = 0;
		}
		// iv.setColorFilter(Color.YELLOW, Mode.MULTIPLY);
		// iv.setAlpha(alpha);

		// iv.setAlpha(alpha)
		// iv.setVisibility(View.INVISIBLE);
		// iv.invalidate();
		// recyleBitmap();
	}

	@Override
	public void refreshData() {
		AlarmProvider ap = AlarmProvider.geInstance(this
				.getApplicationContext());
		for (int i = 0; i < m_listBean.size(); i++) {
			ap.setAlarmEnable(i, (m_listBean.get(i).isEnabled()));
		}

		AlarmSet.setAlarm(this);

	}

	public void refreshData(int i) {
		AlarmProvider ap = AlarmProvider.geInstance(this
				.getApplicationContext());
		ap.setAlarmEnable(i, (m_listBean.get(i).isEnabled()));
		AlarmSet.setAlarm(this);
		AlarmBean a = AlarmProvider.geInstance(this).getNextAlarm();
		if (a != null)
			cv.onAlarmChange(a.getIndex(), a.getTime(),i, m_listBean);
		else {
			cv.onAlarmChange(-1, -1l, i,m_listBean);
		}

	}

	@Override
	public void refreshUI() {

		setUI();
	}

	Boolean isOnButtonAnim = false;

	/**
	 * 选择闹铃按钮
	 * */
	class KeyListener implements OnClickListener {

		private int id;

		public KeyListener(int id) {
			super();
			// TODO Auto-generated constructor stub
			this.id = id;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// ShareDActivity.Share(boxAlarmActivity.this,null,null);
			synchronized (isOnButtonAnim) {
				if (isOnButtonAnim == true) {
					return;
				} else {
					isOnButtonAnim = true;
				}
			}

			if (mForm[id] != null) {
				mForm[id].playClickSound();
			}
			anmi[id].start();
			anmi[id].setmOnAnimationStoppedListener(new OnAnimationStoppedListener() {

				@Override
				public void AnimationStopped() {
					anmi[id].recyle();
					Bundle bundle = new Bundle();
					bundle.putInt("alarm_position", id);
					Intent intent = new Intent(boxAlarmActivity.this,
							SetAlarmActivity.class);
					intent.putExtras(bundle);
					boxAlarmActivity.this.startActivity(intent);
					synchronized (isOnButtonAnim) {
						isOnButtonAnim = false;
					}
				}
			});

		}
	};

	/**
	 * 是否启用的状态按钮
	 * */
	class StateKeyListener implements OnClickListener {

		private int id;

		public StateKeyListener(int id) {
			super();
			// TODO Auto-generated constructor stub
			this.id = id;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ADManager.normalClick();
			boolean state = !m_listBean.get(id).getEnabled();
			m_listBean.get(id).setEnabled(state);
			setButtonState(id, state);
			refreshData(id);

		}
	};
	
	
}
