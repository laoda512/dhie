/**
 * 
 */
package com.tavx.C9Alarm.Fragment;

import com.tavx.C9Alam.connector.MyLogger;

import android.support.v4.app.Fragment;

/**
 * @author Administrator
 *
 */
public class BaseFragment extends Fragment {
	public final String TAG = getClass().getName();
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		MyLogger.e(TAG, "onResume");
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		super.onPause();
		MyLogger.e(TAG, "onPause");
		
	}

}
