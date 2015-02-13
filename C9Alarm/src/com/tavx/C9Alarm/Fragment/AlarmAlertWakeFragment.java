package com.tavx.C9Alarm.Fragment;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.View.AlarmAwakeInterface;
import com.tavx.C9Alarm.View.AlarmButtonPad;
import com.tavx.C9Alarm.View.AlarmButtonPadSAO;
import com.tavx.C9Alarm.View.HotInfoPad;
import com.tavx.C9Alarm.bean.HotInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class AlarmAlertWakeFragment extends Fragment implements AlarmAwakeInterface {
	String formId;
	float rate;

	public static AlarmAlertWakeFragment newInstance(String formId) {
		AlarmAlertWakeFragment details = new AlarmAlertWakeFragment();
		Bundle args = new Bundle();
		args.putString("formId", formId);
		details.setArguments(args);
		return details;
	}

	public String getShownFormId() {
		return getArguments().getString("formId");
	}

	AlarmButtonPadSAO mAlarmButtonPad;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (container == null)
			return null;
		mAlarmButtonPad = new AlarmButtonPadSAO(getActivity());

//		mAlarmButtonPad.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				mAlarmButtonPad.toggleState();
//			}
//		});
		mAlarmButtonPad.setAlarmAwakeInterface(this);
		return mAlarmButtonPad;
	}
	
	AlarmAwakeInterface mAlarmAwakeInterface;
	public void setAlarmAwakeInterface(AlarmAwakeInterface mAlarmAwakeInterface){
		this.mAlarmAwakeInterface = mAlarmAwakeInterface;
	} 
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		mAlarmButtonPad.recyle();
		super.onPause();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.View.AlarmAwakeInterface#onAwake()
	 */
	@Override
	public void onAwake() {
		if(mAlarmAwakeInterface!=null)mAlarmAwakeInterface.onAwake();
	}

	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.View.AlarmAwakeInterface#onSnooze()
	 */
	@Override
	public void onSnooze() {
		if(mAlarmAwakeInterface!=null)mAlarmAwakeInterface.onSnooze();
	}
	
	public void toggleState(){
		mAlarmButtonPad.toggleState();
	}
}