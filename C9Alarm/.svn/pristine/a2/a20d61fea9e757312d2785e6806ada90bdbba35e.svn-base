package com.tavx.C9Alarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.tavx.C9Alarm.Fragment.ShowContentFragment;
import com.tavx.C9Alarm.cloud.Bean.Bmob.QA;

public class ShowContentActivity extends BaseActivity{
	
	public static void start(Context c,QA mQa){
		Intent i = new Intent();
		i.setClass(c, ShowContentActivity.class);
		ShowContentFragment.currentQA=mQa;
		c.startActivity(i);
	}
	
	private ShowContentFragment mDownloadFragment;

	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_content);
		FragmentManager fragmentManager = super.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction(); 
		mDownloadFragment = ShowContentFragment.newInstance();
		fragmentTransaction.add(R.id.framelayout,mDownloadFragment);
		fragmentTransaction.commit();
//		
		
	}
	

}
