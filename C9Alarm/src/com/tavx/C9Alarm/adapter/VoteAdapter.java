package com.tavx.C9Alarm.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobFile;
import cn.sharesdk.socialization.QuickCommentBar;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.R;
import com.tavx.C9Alarm.Utils;
import com.tavx.C9Alarm.Manager.ADManager;
import com.tavx.C9Alarm.Manager.CommentManager;
import com.tavx.C9Alarm.Manager.PointManager;
import com.tavx.C9Alarm.cloud.CloudManager;
import com.tavx.C9Alarm.cloud.Bean.Bmob.Vote_form_point;
import com.tavx.C9Alarm.cloud.Bean.Bmob.form_discount;
import com.tavx.C9Alarm.cloud.Interface._VoteFormData;

public class VoteAdapter<T extends BmobFile,V extends Vote_form_point> extends BaseAdapter {
	
	List<_VoteFormData<T,V>> mList;
	Context mContext;
	private LayoutInflater mLayoutInflater;
	protected Handler refreshHandler ;

	public VoteAdapter(List<_VoteFormData<T,V>> mList, Context c
			) {
		super();
		this.mList = mList;
		mContext = c;
		mLayoutInflater = LayoutInflater.from(c);
		refreshHandler = new Handler(){
			/* (non-Javadoc)
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				notifyDataSetChanged();
				super.handleMessage(msg);
			}
		};
		// initOnekeyShare();
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder mViewHolder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(
					R.layout.listitem_vote, null);
			mViewHolder = new ViewHolder();
			mViewHolder.bt = (TextView) convertView
					.findViewById(R.id.downloadBtn);
			mViewHolder.name = (TextView) convertView.findViewById(R.id.name);
			mViewHolder.line2 = (TextView) convertView.findViewById(R.id.line2);
			mViewHolder.line3 = (TextView) convertView.findViewById(R.id.line3);

			mViewHolder.plus1 = (TextView) convertView.findViewById(R.id.plus1);

			mViewHolder.plus2 = (TextView) convertView.findViewById(R.id.plus2);

			mViewHolder.plus3 = (TextView) convertView.findViewById(R.id.plus3);
			mViewHolder.ad1 = (TextView) convertView.findViewById(R.id.ad01);
			mViewHolder.ad2 = (TextView) convertView.findViewById(R.id.ad02);
			mViewHolder.ad3 = (TextView) convertView.findViewById(R.id.ad03);
			mViewHolder.ad4 = (TextView) convertView.findViewById(R.id.ad04);
			mViewHolder.ad5 = (TextView) convertView.findViewById(R.id.ad05);
			mViewHolder.ad_bottom = (TextView) convertView.findViewById(R.id.ad_bottom);
			mViewHolder.pg = (ProgressBar) convertView
					.findViewById(R.id.download_progress);
			
			mViewHolder.pg = (ProgressBar) convertView
					.findViewById(R.id.download_progress);
			mViewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(mViewHolder);
			mViewHolder.comment = (TextView) convertView
					.findViewById(R.id.comment);
			// mViewHolder.qcBar = (QuickCommentBar)
			// convertView.findViewById(R.id.qcBar);
			// iniQcBar(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		// mViewHolder.qcBar.setTopic(mList.get(position).getName(),
		// mList.get(position).getName(), "", "");

		final _VoteFormData<T,V> mVoteFormData = mList.get(position);

		String author = mVoteFormData.getAuthor();
		String points = ""+mVoteFormData.getPointForSelf().getPoint();
		final String name = mVoteFormData.getName();
		mViewHolder.name.setText(name);
		mViewHolder.line2.setText("发起人：" + author);
		mViewHolder.line3.setText("元气指数：" + points);
		
		mViewHolder.ad1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri
						.parse("http://projectalarmx.sinaapp.com/baidu.php?key="
								+ name);
				intent.setData(content_url);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
				
			}
		});

		mViewHolder.ad2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri
						.parse("http://tieba.baidu.com/f?kw="
								+ name);
				intent.setData(content_url);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
			}
		});

		mViewHolder.ad3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri
						.parse("http://r.m.taobao.com/s?p=mm_56673559_6858157_23330854&q="
								+ name + " " + mViewHolder.ad3.getText());
				intent.setData(content_url);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
			}
		});

		mViewHolder.ad4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri
						.parse("http://r.m.taobao.com/s?p=mm_56673559_6858157_23330854&q="
								+ name + " " + mViewHolder.ad4.getText());
				intent.setData(content_url);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
			}
		});

		mViewHolder.ad5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri
						.parse("http://r.m.taobao.com/s?p=mm_56673559_6858157_23330854&q="
								+ name + " " + mViewHolder.ad5.getText());
				intent.setData(content_url);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
			}
		});
		mViewHolder.ad_bottom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String uri  = "";
				form_discount mForm_discount = mVoteFormData.getForm_discount();
				if(mForm_discount!=null){
					List<String> urilist = mForm_discount.getAdUrl();
					if(urilist!=null&&urilist.size()!=0){
						uri = urilist.get(Utils.random(0, urilist.size()-1));
					}else{
						AlarmApplication.getApp().showToast("暂无折扣信息，请耐心等苦逼作者更新后台数据");
						return;
					}
				}else{
					AlarmApplication.getApp().showToast("暂无折扣信息，请耐心等苦逼作者更新后台数据");
					return ;
				}
				
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri
						.parse(uri);
				intent.setData(content_url);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
				
				
			}
		});
		mViewHolder.plus1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PointManager.addPointToVoteForm(mVoteFormData, "5",refreshHandler );
			}
		});

		mViewHolder.plus2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PointManager.addPointToVoteForm(mVoteFormData, "20",refreshHandler);
			}
		});
		mViewHolder.plus3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyLogger.e("aaaa", "click");
				PointManager.addPointToVoteForm(mVoteFormData, "50",refreshHandler);
				
			}
		});

		mViewHolder.comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ADManager.normalClick();
				CommentManager.getInstance().showComment(mContext,name,name,"","",CommentManager.TYPE_FORM_VOTE_COMMENT);
			}
		});

		mViewHolder.icon.setImageDrawable(null);
//		if (mRemoteFormData.getPic() != null){
//			AlarmApplication.IMAGE_CACHE.get(mRemoteFormData.getPic().getThumbnailUrl(true, (int)(120*AlarmApplication.getApp().getFitRateX()), (int)(120*AlarmApplication.getApp().getFitRateX())),
//					mViewHolder.icon);
//		}
		CloudManager.setBitmap(mVoteFormData.getPic(), mViewHolder.icon);
		

		return convertView;
	}



	class ViewHolder {
		public TextView ad_bottom;
		public TextView ad5;
		public TextView ad4;
		public TextView ad3;
		public TextView ad2;
		public TextView ad1;
		public TextView line3;
		public TextView plus2;
		public TextView plus3;
		public TextView line2;
		public TextView plus1;
		public TextView point;
		public ImageView icon;
		public QuickCommentBar qcBar;
		TextView comment;
		ProgressBar pg;
		TextView bt;
		TextView name;
	}

}
