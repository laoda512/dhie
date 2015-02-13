package com.tavx.C9Alarm.Fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.R;
import com.tavx.C9Alarm.adapter.DownloadListAdapter;
import com.tavx.C9Alarm.adapter.VoteAdapter;
import com.tavx.C9Alarm.cloud.CloudManager;
import com.tavx.C9Alarm.cloud.Bean.Bmob.Vote_form_point;
import com.tavx.C9Alarm.cloud.Bean.Bmob.form_point;
import com.tavx.C9Alarm.cloud.Interface._RemoteFormData;
import com.tavx.C9Alarm.cloud.Interface._VoteFormData;

public class VoteFragment<T extends BmobFile,V extends Vote_form_point> extends BaseFragment {
	
	


	public static VoteFragment newInstance(String formId) {
		VoteFragment details = new VoteFragment();
		Bundle args = new Bundle();
		args.putString("formId", formId);
		details.setArguments(args);
		return details;
	}

	View root;
	ListView mlView;
	GridView gv;
	VoteAdapter<BmobFile, Vote_form_point> mVoteAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		root = inflater.inflate(R.layout.downloadlist, null);
		mlView = (ListView) root.findViewById(R.id.listview);
		
		//VoteAdapter<BmobFile, Vote_form_point> mV=new VoteAdapter<BmobFile, Vote_form_point>(mList, getActivity());
		getList();
		return root;
	}

	@Override
	public void onResume() {
		super.onResume();

	}
	
	private void getList(){
		CloudManager.queryVotePoint(getActivity(), new Handler() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == CloudManager.success) {
					List<Vote_form_point> mlist = (List<Vote_form_point>) msg.obj;
					List<_VoteFormData<T, V>> resList = new ArrayList<_VoteFormData<T,V>>();
					for (Vote_form_point item : mlist) {
						if (item.getJifen() != null) {
							item.getJifen().setPointForSelf(item);
							resList.add((_VoteFormData<T, V>) item.getJifen());

						} else {
							
						}
					}

					mVoteAdapter = new VoteAdapter(resList,
							AlarmApplication.getApp());

					mlView.setAdapter(mVoteAdapter);
				}
				super.handleMessage(msg);
			}
		}, "jifen","jifen.discount");
	}
	
}