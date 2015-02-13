/**
 * 
 */
package com.tavx.C9Alarm.Manager;

import android.content.Context;
import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.socialization.CommentFilter;
import cn.sharesdk.socialization.CommentListPage;
import cn.sharesdk.socialization.CommentFilter.FilterItem;

/**
 * @author Administrator
 *
 */
public class CommentManager {

	private static CommentManager mCommentManager;
	public static CommentManager getInstance(){
		if(mCommentManager==null) mCommentManager =new CommentManager();
		return mCommentManager;
	}
	
	CommentListPage commentlistpage;
	private OnekeyShare oks;
	
	
	public CommentManager() {
		super();
		initOnekeyShare();
		iniQcBar();
	}

	private void iniQcBar() {

		commentlistpage = new CommentListPage();

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
					String wordText = cn.sharesdk.framework.utils.R.toWordText(
							pureComment, 140);
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
		CommentFilter filter = builder.build();
		commentlistpage.setCommentFilter(filter);
		commentlistpage.setOnekeyShare(oks);
	}
	
	public void showComment(Context c,String s1,String s2,String s3,String s4,int type){
		commentlistpage.setTopic(getCommentId(s1, type), s2, s3, s4);
		commentlistpage.show(c, null);
	}
	
	private void initOnekeyShare() {
		oks = new OnekeyShare();
		oks.setSite("未来闹钟");
		oks.setSiteUrl("http://www.projectalarmx.com");
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
			public void onShare(Platform platform, ShareParams paramsToShare) {

			}
		});
	}
	
	
	public static final int TYPE_FORM_DOWNLOAD_COMMENT = 1000;
	public static final int TYPE_FORM_VOTE_COMMENT = 1000;
	public static final int TYPE_AWAKE_COMMENT = 1000;
	public static final int TYPE_QA_CONTENT = 1235;
	
	public static String getCommentId(String name,int type){
		return name+"@"+type;
	}
}
