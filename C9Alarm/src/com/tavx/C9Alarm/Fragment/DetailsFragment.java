package com.tavx.C9Alarm.Fragment;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.tavx.C9Alarm.AlarmApplication;
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
 public  class DetailsFragment extends Fragment implements AnimatorUpdateListener{
	 String  formId;
	 float rate;
        public static DetailsFragment newInstance(String formId) { 
            DetailsFragment details = new DetailsFragment();
            Bundle args = new Bundle();
            args.putString("formId", formId);
            details.setArguments(args);
            return details;
        }

        public String getShownFormId() {
            return getArguments().getString("formId");
        }
        HotInfoPad mHotInfoPad;
		private ValueAnimator timeAnimator;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            if (container == null)
                return null;
            mHotInfoPad =new HotInfoPad(getActivity());
            setFormId(getShownFormId());
            
            mHotInfoPad.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AlarmApplication.getApp().showToast("咕噜噜，咕噜噜");
					
					dropUpAnim();
				}
			});
            
            return mHotInfoPad;
        }
        
        public void setFormId(String formId){
        	this.formId = formId;
        	HotInfo mHotInfo = new HotInfo(getActivity(), ""+formId);
        	//mHotInfo.setFormIndex(index);
        	setHotInfo(mHotInfo);
        }
        
        public void setHotInfo(HotInfo mHotInfo){
        	mHotInfoPad.setParam(mHotInfo);
        	mHotInfoPad.invalidate();
        }
        
        public void setScaleRate(float rate){
        	if(Math.abs(this.rate-rate)<0.000001 ){
        		return;
        	}
        	if(timeAnimator!=null&&timeAnimator.isRunning()){
        		timeAnimator.cancel();
        		timeAnimator =null;
        	}
        	this.rate=rate;
        	rotate(rate*3);
        }
        
        private void rotate(float rate){
        	mHotInfoPad.setRoatation(rate);
        }
        
		@Override
		public void onResume() {
			super.onResume();
			setFormId(formId);
		}
		
		void dropUpAnim() {

			if (timeAnimator != null && timeAnimator.isRunning()) {
				timeAnimator.cancel();
			}
			if(Math.random()>0.5){
				timeAnimator = ObjectAnimator.ofFloat(new AnimHolder(0), "weight",
						0,1);
			}else{
				timeAnimator = ObjectAnimator.ofFloat(new AnimHolder(0), "weight",
						1,0);
			}
			
			timeAnimator.setDuration(1000);
			timeAnimator.setInterpolator(new LinearInterpolator());
			timeAnimator.addUpdateListener(this);
			timeAnimator.start();

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
		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			rotate( (Float) (animation.getAnimatedValue()));			
		}

}