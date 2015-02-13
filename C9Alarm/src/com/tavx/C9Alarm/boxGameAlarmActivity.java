package com.tavx.C9Alarm;

import holidaynotifaction.HolidayReceiver;

import java.util.List;

import com.tavx.C9Alarm.Form.Form;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class boxGameAlarmActivity extends Activity implements RefreshAlarm {
	private final static String TAG = "alarm";

	RelativeLayout m_layoutMain;

	// ListView m_alarmList;

	Button m_btStopSleepAlarm;
	RelativeLayout tl, tr, br, bl;
	TextView tvtlname, tvtltime, tvtlring;
	TextView tvblname, tvbltime, tvblring;
	TextView tvtrname, tvtrtime, tvtrring;
	TextView tvbrname, tvbrtime, tvbrring;
	ImageButton blm, tlm, trm, brm;
	ImageView imageView_cirtl, imageView_cirtr, imageView_cirbl,
			imageView_cirbr;
	private List<AlarmBean> m_listBean;

	// Button m_btChangeBg;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainm);
		HolidayReceiver.setAlarm(AlarmApplication.getApp());
		initUI();
		setListener();
		setUI();
	}

	@Override
	protected void onResume() {
		refreshUI();
		super.onResume();
	}

	private void initUI() {
		m_layoutMain = (RelativeLayout) findViewById(R.id.layoutMain);
		m_btStopSleepAlarm = (Button) findViewById(R.id.btStop);

		tl = (RelativeLayout) findViewById(R.id.include1);
		tvtlname = (TextView) findViewById(R.id.texttlname);
		tvtlring = (TextView) findViewById(R.id.texttltime);
		tvtltime = (TextView) findViewById(R.id.texttlring);
		tlm = (ImageButton) findViewById(R.id.imageBttl);
		imageView_cirtl = (ImageView) findViewById(R.id.imageView_cirtl);

		bl = (RelativeLayout) findViewById(R.id.include3);
		tvblname = (TextView) findViewById(R.id.textblname);
		tvbltime = (TextView) findViewById(R.id.textbltime);
		tvblring = (TextView) findViewById(R.id.textblring);
		blm = (ImageButton) findViewById(R.id.imageBtbl);
		imageView_cirtr = (ImageView) findViewById(R.id.imageView_cirtr);

		tr = (RelativeLayout) findViewById(R.id.include2);
		tvtrname = (TextView) findViewById(R.id.texttrname);
		tvtrtime = (TextView) findViewById(R.id.texttrtime);
		tvtrring = (TextView) findViewById(R.id.texttrring);
		trm = (ImageButton) findViewById(R.id.imageBttr);
		imageView_cirbl = (ImageView) findViewById(R.id.imageView_cirbl);

		br = (RelativeLayout) findViewById(R.id.include4);
		tvbrname = (TextView) findViewById(R.id.textbrname);
		tvbrtime = (TextView) findViewById(R.id.textbrtime);
		tvbrring = (TextView) findViewById(R.id.textbrring);
		brm = (ImageButton) findViewById(R.id.imageBtbr);
		imageView_cirbr = (ImageView) findViewById(R.id.imageView_cirbr);
	}

	private void setListener() {
		m_btStopSleepAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Utils.forceStopSleepAlarm(AlarmApplication.getApp());
			}

		});
		// m_alarmList = (ListView) findViewById(R.id.alarmList);

		tlm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putInt("alarm_position", 0);
				Intent intent = new Intent(boxGameAlarmActivity.this,
						SetAlarmActivity.class);
				intent.putExtras(bundle);
				boxGameAlarmActivity.this.startActivity(intent);
			}

		});

		imageView_cirtl.setOnClickListener(new OnClickListener() {
			int position = 0;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean state = !m_listBean.get(position).getEnabled();
				m_listBean.get(position).setEnabled(state);
				setButtonState(position, state);
				refreshData(position);
			}

		});

		trm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putInt("alarm_position", 1);
				Intent intent = new Intent(boxGameAlarmActivity.this,
						SetAlarmActivity.class);
				intent.putExtras(bundle);
				boxGameAlarmActivity.this.startActivity(intent);
			}

		});

		imageView_cirtr.setOnClickListener(new OnClickListener() {
			int position = 1;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean state = !m_listBean.get(position).getEnabled();
				m_listBean.get(position).setEnabled(state);
				setButtonState(position, state);
				refreshData(position);
			}

		});

		blm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putInt("alarm_position", 2);
				Intent intent = new Intent(boxGameAlarmActivity.this,
						SetAlarmActivity.class);
				intent.putExtras(bundle);
				boxGameAlarmActivity.this.startActivity(intent);
			}

		});

		imageView_cirbl.setOnClickListener(new OnClickListener() {
			int position = 2;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean state = !m_listBean.get(position).getEnabled();
				m_listBean.get(position).setEnabled(state);
				setButtonState(position, state);
				refreshData(position);
			}

		});

		brm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putInt("alarm_position", 3);
				Intent intent = new Intent(boxGameAlarmActivity.this,
						SetAlarmActivity.class);
				intent.putExtras(bundle);
				boxGameAlarmActivity.this.startActivity(intent);
			}

		});
		imageView_cirbr.setOnClickListener(new OnClickListener() {
			int position = 3;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean state = !m_listBean.get(position).getEnabled();
				m_listBean.get(position).setEnabled(state);
				setButtonState(position, state);
				refreshData(position);
			}

		});

	}

	private void setUI() {
		m_listBean = AlarmProvider.geInstance(AlarmApplication.getApp())
				.getAlarms();

		for (int i = 0; i < m_listBean.size(); i++) {
			Form f = Form.getForm(m_listBean.get(i).getFormIndex());
			f.loadSound();
		}

		setButtonState();

		if (AlarmProvider.geInstance(AlarmApplication.getApp())
				.isSleepAlarmEnable(0)) {
			m_btStopSleepAlarm.setEnabled(true);
		} else {
			m_btStopSleepAlarm.setEnabled(false);
		}
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
			break;
		}
		case 1: {
			iv = imageView_cirtr;
			break;

		}
		case 2: {
			iv = imageView_cirbl;
			break;
		}
		case 3: {
			iv = imageView_cirbr;
			break;
		}
		default:
			return;

		}
		int alpha = 255;
		if (state == false) {
			alpha = 0;
		}
		iv.setAlpha(alpha);
		// iv.setAlpha(alpha)
		// iv.setVisibility(View.INVISIBLE);
		iv.invalidate();
	}

	@Override
	public void refreshData() {
		AlarmProvider ap = AlarmProvider.geInstance(AlarmApplication.getApp());
		for (int i = 0; i < m_listBean.size(); i++) {
			ap.setAlarmEnable(i, (m_listBean.get(i).isEnabled()));
		}

		AlarmSet.setAlarm(AlarmApplication.getApp());

	}

	public void refreshData(int i) {
		AlarmProvider ap = AlarmProvider.geInstance(AlarmApplication.getApp());
		ap.setAlarmEnable(i, (m_listBean.get(i).isEnabled()));
		AlarmSet.setAlarm(AlarmApplication.getApp());

	}

	@Override
	public void refreshUI() {

		setUI();
	}
}
