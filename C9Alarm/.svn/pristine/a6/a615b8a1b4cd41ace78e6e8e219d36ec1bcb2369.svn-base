package com.tavx.C9Alarm.Fragment;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ListView;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.R;
import com.tavx.C9Alarm.ShowContentActivity;
import com.tavx.C9Alarm.Manager.ADManager;
import com.tavx.C9Alarm.Manager.CommentManager;
import com.tavx.C9Alarm.adapter.QaAdapter;
import com.tavx.C9Alarm.adapter.QaAdapter2;
import com.tavx.C9Alarm.adapter.VoteAdapter;
import com.tavx.C9Alarm.cloud.CloudManager;
import com.tavx.C9Alarm.cloud.Bean.Bmob.QA;
import com.tavx.C9Alarm.cloud.Bean.Bmob.Vote_form_point;
import com.tavx.C9Alarm.cloud.Interface._VoteFormData;
import com.umeng.socom.Log;

public class QAFragment extends BaseFragment {
	
	


	public static QAFragment newInstance(String formId) {
		QAFragment details = new QAFragment();
		Bundle args = new Bundle();
		args.putString("formId", formId);
		details.setArguments(args);
		return details;
	}

	View root;
	ListView mlView;
	GridView gv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		root = inflater.inflate(R.layout.qa_and_help, null);
	//	mlView = (ListView) root.findViewById(R.id.listview);
		
		gv = (GridView) root.findViewById(R.id.gridView1);
		

		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				QA q =(QA) gv.getItemAtPosition(position);
				//CommentManager.getInstance().showComment(getActivity(), q.getTitle(),q.getTitle() ,q.getQuestion()+"\n"+q.getAnswer(), "");
				ShowContentActivity.start(getActivity(), q);
				ADManager.normalClick();
			}
			
		});

		gv.setFocusable(false);
		gv.setClickable(false);
		getList();
		return root;
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	public void getList(){

		CloudManager.queryQAList(getActivity(), new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == CloudManager.success) {
					List<QA> mlist = (List<QA>) msg.obj;
					
					QaAdapter2 mVoteAdapter = new QaAdapter2(mlist,
							AlarmApplication.getApp());

					gv.setAdapter(mVoteAdapter);
				}
				super.handleMessage(msg);
			}
		});
	}
	
	
}