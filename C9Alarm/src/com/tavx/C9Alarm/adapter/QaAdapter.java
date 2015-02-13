package com.tavx.C9Alarm.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tavx.C9Alarm.R;
import com.tavx.C9Alarm.cloud.Bean.Bmob.QA;

public class QaAdapter extends BaseAdapter {
	List<QA> qsList;
	Context mContext;
	LayoutInflater mLayoutInflater;
	public QaAdapter(List<QA> qsList,Context c) {
		this.qsList = qsList;
		this.mContext=c;
		this.mLayoutInflater = LayoutInflater.from(c);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return qsList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public QA getItem(int position) {
	
		return qsList.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Viewholder mViewholder = null;
		QA mQa;
		if(convertView==null){
			mViewholder = new Viewholder();
			convertView = mLayoutInflater.inflate(R.layout.listitem_qa, null);
			mViewholder.v1 = (TextView) convertView.findViewById(R.id.textview1);
			mViewholder.v2 = (TextView) convertView.findViewById(R.id.textview2);
			convertView.setTag(mViewholder);
		}else{
			mViewholder = (Viewholder) convertView.getTag();	
		}
		mQa = getItem(position);
		mViewholder.v1.setText(mQa.getTitle());
		return convertView;
	}
	
	class Viewholder{
		TextView v1;
		TextView v2;
	}
	
}