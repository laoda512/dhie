/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package com.tavx.C9Alarm.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.socialization.CommentFilter;
import cn.sharesdk.socialization.CommentFilter.FilterItem;
import cn.sharesdk.socialization.QuickCommentBar;
import cn.sharesdk.socialization.Socialization;
import cn.sharesdk.socialization.component.TopicTitle;

import com.tavx.C9Alarm.R;
import com.tavx.C9Alarm.Manager.CommentManager;
import com.tavx.C9Alarm.cloud.CloudManager;
import com.tavx.C9Alarm.cloud.Bean.Bmob.QA;

/** 评论和赞功能的演示页面 */
public class ShowADFragment extends Fragment  {
	private String testImage;
	// 模拟的主题id
	private String topicId;
	// 模拟的主题标题
	private String topicTitle;
	// 模拟的主题发布时间
	private String topicPublishTime;
	// 模拟的主题作者
	private String topicAuthor;
	private OnekeyShare oks;
	private QuickCommentBar qcBar;
	private CommentFilter filter;
	
	public static QA currentQA;

	
	
	public static ShowADFragment newInstance() {
		ShowADFragment details = new ShowADFragment();
		Bundle args = new Bundle();
		details.setArguments(args);
		return details;
	}
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ad_splash);
		
	}
	/**
	 * @wangcheng
	 * @myfitcode start
	 * */
	
	
	
	int MainViewId;
	private View MainView;

	ImageView image;
	TextView tvText;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		MainView = inflater.inflate(getContentView(), container, false);
		
	//	image = (ImageView) findViewById(R.id.ivIllustration);
	//	tvText =  (TextView) findViewById(R.id.tvText);
	//	iniView();
		return MainView;
	}
	
	
	private int getContentView(){
		return MainViewId;
	}
	
	private void setContentView(int guba_stockhome) {
		MainViewId = guba_stockhome;
	}
	
	private View getMainView(){
		return MainView;
	}
	
	private View findViewById(int tvArtiTitle) {
		return getMainView().findViewById(tvArtiTitle);
	}
	/**
	 * @wangcheng
	 * @myfitcode end
	 * */



	public void iniView() {
		if(currentQA!=null)
		topicId = currentQA.getId();
		topicTitle = currentQA.getTitle();
		topicPublishTime = currentQA.getUpdatedAt();
		topicAuthor = currentQA.getAuthor();

		TopicTitle tt = (TopicTitle) findViewById(R.id.llTopicTitle);
		tt.setTitle(topicTitle);
		tt.setPublishTime(topicPublishTime);
		tt.setAuthor(topicAuthor);
		
		CloudManager.setBitmap(currentQA.getPic(), image);
		tvText.setText(currentQA.getAnswer());
		Socialization service = ShareSDK.getService(Socialization.class);
	//	service.setCustomPlatform(new MyPlatform(this));
		initOnekeyShare();
		initQuickCommentBar();
	}

	// Socialization服务依赖OnekeyShare组件，此方法初始化一个OnekeyShare对象
	// 此方法的代码从DemoPage中复制而来
	private void initOnekeyShare() {
		oks = new OnekeyShare();
	//	oks.setNotification(R.drawable.logo_sharesdk, getString(R.string.app_name));
		oks.setTitle(topicTitle);
		oks.setTitleUrl("www.projectalarmx.com");
		oks.setText(currentQA.getAnswer());
		oks.setImagePath(testImage);
		oks.setImageUrl(currentQA.getPic().getFileUrl());
		oks.setUrl("www.projectalarmx.com");
		oks.setSite("未来闹钟");
		oks.setSiteUrl("www.projectalarmx.com");
//		oks.disableSSOWhenAuthorize();
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
			public void onShare(Platform platform, ShareParams paramsToShare) {
				// 改写twitter分享内容中的text字段，否则会超长，
				// 因为twitter会将图片地址当作文本的一部分去计算长度
				if ("Twitter".equals(platform.getName())) {
	//				paramsToShare.setText(platform.getContext().getString(R.string.share_content_short));
				}
			}
		});
	}

	private void initQuickCommentBar() {
		qcBar = (QuickCommentBar) findViewById(R.id.qcBar);
		qcBar.setTopic(CommentManager.getCommentId(topicTitle, CommentManager.TYPE_QA_CONTENT), topicTitle, topicPublishTime, topicAuthor);
		//qcBar.setTextToShare(getString(R.string.share_content));
		qcBar.setAuthedAccountChangeable(false);
		CommentFilter.Builder builder = new CommentFilter.Builder();
		// 非空过滤器
		builder.append(new FilterItem() {
			// 返回true表示是垃圾评论
			public boolean onFilter(String comment) {
				if (TextUtils.isEmpty(comment)) {
					return true;
				} else if (comment.trim().length() <= 0) {
					return true;
				} else if (comment.trim().toLowerCase().equals("null")) {
					return true;
				}
				return false;
			}

			@Override
			public int getFilterCode() {
				return 0;
			}
		});
		// 字数上限过滤器
		builder.append(new FilterItem() {
			// 返回true表示是垃圾评论
			public boolean onFilter(String comment) {
				if (comment != null) {
					String pureComment = comment.trim();
					String wordText = cn.sharesdk.framework.utils.R.toWordText(pureComment, 140);
					if (wordText.length() != pureComment.length()) {
						return true;
					}
				}
				return false;
			}

			@Override
			public int getFilterCode() {
				return 0;
			}
		});
		filter = builder.build();
		qcBar.setCommentFilter(filter);
		qcBar.setOnekeyShare(oks);
	}

	public void onClick(View v) {
		if (v.equals(qcBar.getBackButton())) {
			//finish();
		}
	}

}
