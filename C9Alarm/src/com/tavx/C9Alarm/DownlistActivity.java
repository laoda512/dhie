package com.tavx.C9Alarm;

import java.util.List;

import util.SDResReadManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.FindListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.socialization.Socialization;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.Form.Form;
import com.tavx.C9Alarm.Fragment.ADListFragment;
import com.tavx.C9Alarm.Fragment.DownLoadsFragment2;
import com.tavx.C9Alarm.Fragment.QAFragment;
import com.tavx.C9Alarm.Fragment.SettingFragment;
import com.tavx.C9Alarm.Fragment.VoteFragment;
import com.tavx.C9Alarm.Manager.ADManager;
import com.tavx.C9Alarm.Manager.PointManager;
import com.tavx.C9Alarm.bd.InterstitialAd;
import com.tavx.C9Alarm.cloud.CloudManager;
import com.tavx.C9Alarm.cloud.Bean.AVOS.RemoteFormData;
import com.tavx.C9Alarm.cloud.Bean.Bmob.title_hint;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

public class DownlistActivity extends BaseActivity {
	public static final int TYPE_DOWNLOAD = 1;
	public static final int TYPE_SETTING = 2;
	public static final int TYPE_AWAKE = 3;
	public static final int TYPE_SHARE = 4;

	public static final int INDEX_DOWNLOAD = 0;
	public static final int INDEX_QA = 1;
	public static final int INDEX_SETTING = 2;
	public static final int INDEX_VOTE = 3;
	public static final int INDEX_AD = 4;
	protected static final int INDEX_SHARE = 5;

	private static final String[] TITLE = new String[] { "主题下载", "关于和帮助",
			"高级设置", "主题投票", "一整页广告", "2.5次元的交流" };

	int[] sort_setting = { INDEX_AD, INDEX_DOWNLOAD, INDEX_QA, INDEX_VOTE,
			INDEX_SETTING, INDEX_AD };
	int[] sort_download = { INDEX_AD, INDEX_DOWNLOAD, INDEX_QA, INDEX_VOTE,
			INDEX_SETTING, INDEX_AD };
	int[] sort_share = { INDEX_AD, INDEX_SHARE, INDEX_DOWNLOAD, INDEX_QA,
			INDEX_VOTE, INDEX_SETTING, INDEX_AD };;
	int[] sort_awake;

	private DownLoadsFragment2 mDownloadFragment;
	private TextView hint;
	title_hint th;
	private InterstitialAd interAd;
	Bundle bundle_for_Share;

	int type;

	public static void startActivity(Activity a, int type) {
		Intent i = new Intent();
		i.setClass(a, DownlistActivity.class);
		i.putExtra("type", type);
		a.startActivity(i);
	}

	public static void startActivity(Activity a, int type, Bundle b,
			String bundleName) {
		Intent i = new Intent();
		i.putExtra("type", type);
		i.putExtra("" + TYPE_SHARE, b);
		MyLogger.e("DownlistActivity", "startActivity");
		if (a == null) {
			i.setClass(AlarmApplication.getApp(), DownlistActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			AlarmApplication.getApp().startActivity(i);
		} else {
			i.setClass(a, DownlistActivity.class);
			a.startActivity(i);
		}

	}

	Object youmiCallBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_downlits);
		RelativeLayout main = (RelativeLayout) findViewById(R.id.main);
		String formName = AlarmApplication.getApp().getUser().getLastFormTag();
		if (!formName.equals("default")) {
			Form f = Form.getForm(formName);

			int mWidth = 1080;// 宽度
			int mHeight = 1920;// 高度
			int realWidth = (int) (mWidth * AlarmApplication.getApp()
					.getFitRateX());
			int realHeight = (int) (mHeight * AlarmApplication.getApp()
					.getFitRateY());
			if (f != null) {
				main.setBackground(new BitmapDrawable(SDResReadManager
						.getInstance().getResBitmap(f.getSettingPageBg(),
								f.getTitleName(), mWidth, mHeight)));
			} else {

			}
		}

		Intent i = getIntent();
		if (i != null) {
			type = i.getIntExtra("type", TYPE_DOWNLOAD);
			if (type == TYPE_SHARE) {
				bundle_for_Share = i.getBundleExtra("" + TYPE_SHARE);
			}

		}
		hint = (TextView) findViewById(R.id.hint);
		try {
			test();
		} catch (Exception e) {
			e.printStackTrace();
		}
		iniView();

//		AVObject.registerSubclass(RemoteFormData.class);
//		AVOSCloud.initialize(this,
//				"27rp8tazy26hutebuak47r4oxmtx09t248sbydauv3tqn78q",
//				"ifjze3w4qx866eush40x38nkbd3vxrtw53fyqfurws5j8q67");
		ShareSDK.registerService(Socialization.class);

		View v = findViewById(R.id.giveMorePoints);
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlarmApplication.getApp().showToast(
						"元气会随着未来闹钟的日常使用自动增长，详情可以查看关于与帮助中的介绍");
				// PointManager.addPoint("10");
				// OffersManager.showOffers(DownlistActivity.this);
			}
		});

		final TextView point = (TextView) findViewById(R.id.points);
		point.setText("" + PointManager.getPoint());

		// ADManager.iniYoumi();
		PointManager.addPointCallBack(new Handler() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				point.setText("" + PointManager.getPoint());
				super.handleMessage(msg);
			}
		});

		th = new title_hint();

		CloudManager.iniImageCache();
		CloudManager.IniImageCacheListener();
		CloudManager.queryHint(this, new Handler() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == CloudManager.success) {
					List<title_hint> mlist = (List<title_hint>) msg.obj;
					if (mlist.size() != 0) {
						th = mlist.get(0);
					} else {
						th = new title_hint();
					}
				}

				super.handleMessage(msg);
			}
		});
		CloudManager.IniImageCacheListener();
		// boolean showTitleBar=false;//如果为true，则显示页面顶端的bar；为false则不显示。
		// OffersView ov=new OffersView(this, showTitleBar);
		//
		// RelativeLayout.LayoutParams rllp=new
		// RelativeLayout.LayoutParams(-1,-1);
		// rllp.addRule(RelativeLayout.BELOW, 100);
		// main.addView(ov, rllp);
	}

	ViewPager pager;
	String Help_Start;

	private void iniView() {
		// ViewPager的adapter
		TabPageIndicatorAdapter adapter = new TabPageIndicatorAdapter(
				getSupportFragmentManager());
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		switch (type) {
		case TYPE_DOWNLOAD:
			pager.setCurrentItem(1);
			break;
		case TYPE_SETTING:
			pager.setCurrentItem(4);
			break;

		case TYPE_SHARE:
			pager.setCurrentItem(1);
			break;
		default:
			break;
		}

		// PagerTabStrip mPagerTabStrip = (PagerTabStrip)
		// findViewById(R.id.pagerTab);
		// mPagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.blue));
		// mPagerTabStrip.setDrawFullUnderline(true);
		// mPagerTabStrip.setTextSpacing(50);
		// mPagerTabStrip.setTextColor(color.white);
		// mPagerTabStrip.setBackgroundColor(color.black);

		// 实例化TabPageIndicator然后设置ViewPager与之关联
		TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		final float density = getResources().getDisplayMetrics().density;
		indicator.setBackgroundColor(0x77FEDFE1);
		indicator.setFooterColor(0x99AA2222);
		indicator.setFooterLineHeight(1 * density); // 1dp
		indicator.setFooterIndicatorHeight(3 * density); // 3dp
		indicator.setFooterIndicatorStyle(IndicatorStyle.Underline);
		indicator.setTextColor(0xAA000000);
		indicator.setSelectedColor(0xFF000000);
		indicator.setSelectedBold(true);

		// 如果我们要对ViewPager设置监听，用indicator设置就行了
		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// Toast.makeText(getApplicationContext(), TITLE[arg0],
				// Toast.LENGTH_SHORT).show();

				String mhint = "";
				arg0 = getRealIndex(arg0);
				switch (arg0) {
				case INDEX_DOWNLOAD:

					mhint = th.getHint_download();
					break;
				case INDEX_QA:
					if (Utils.random(0, 10) > 6)
						ADManager.normalClick();
					mhint = th.getHint_qa();
					break;
				case INDEX_SETTING:
					mhint = th.getHint_setting();
					break;
				case INDEX_VOTE:
					mhint = th.getHint_vote();
					break;
				case INDEX_AD:
					if (Utils.random(0, 10) > 6)
						ADManager.normalClick();
					mhint = th.getHint_Ad();
					break;
				case INDEX_SHARE:
					mhint = th.getHint_Share();
					break;
				case 999: {
					mhint = th.getHint_Square();

				}
					break;
				}
				hint.setText(mhint);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private int getRealIndex(int index) {
		switch (type) {
		case TYPE_SETTING: {
			return sort_setting[index];
		}
		case TYPE_AWAKE: {
			return sort_awake[index];
		}
		case TYPE_DOWNLOAD: {
			return sort_download[index];
		}
		case TYPE_SHARE: {
			return sort_share[index];
		}
		}
		return index;
	}

	/**
	 * ViewPager适配器
	 * 
	 * @author len
	 * 
	 */
	class TabPageIndicatorAdapter extends FragmentStatePagerAdapter {
		public TabPageIndicatorAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// 新建一个Fragment来展示ViewPager item的内容，并传递参数
			position = getRealIndex(position);
			Fragment fragment = null;
			switch (position) {
			case INDEX_DOWNLOAD: {
				fragment = DownLoadsFragment2.newInstance("");
			}

				break;
			case INDEX_QA: {
				fragment = QAFragment.newInstance("");
				break;
			}
			case INDEX_SETTING: {
				fragment = SettingFragment.newInstance("");
			}
				break;

			case INDEX_VOTE: {
				fragment = VoteFragment.newInstance("");
			}
				break;
			case INDEX_AD: {
				fragment = ADListFragment.newInstance("");
			}
				break;
			case INDEX_SHARE: {
				fragment = ShareToFriendActivity.newInstance(bundle_for_Share);
			}
				break;
			default:
				break;
			}

			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLE[getRealIndex(position) % TITLE.length];
		}

		@Override
		public int getCount() {
			switch (type) {
			case TYPE_SETTING: {
				return sort_setting.length;
			}
			case TYPE_AWAKE: {
				return sort_awake.length;
			}
			case TYPE_DOWNLOAD: {
				return sort_download.length;
			}
			case TYPE_SHARE: {
				return sort_share.length;
			}
			}
			return sort_download.length;
		}
	}

	private void test() {
		BmobQuery<RemoteFormData> query = new BmobQuery<RemoteFormData>();
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		query.findObjects(this, new FindListener<RemoteFormData>() {
			@Override
			public void onSuccess(List<RemoteFormData> object) {
				// TODO Auto-generated method stub
				// toast("查询成功：共"+object.size()+"条数据。");
				MyLogger.e("aaaa", "vvvvv" + object.size());
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
				// toast("查询失败："+msg);
				MyLogger.e("aaaa", "vvvvv" + msg);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		CloudManager.recyleImageCache();
		ShareSDK.stopSDK(this);
		// PointManager.removePointCallBack();

		super.onDestroy();
	}

	public void gotoADPage() {
		if (pager != null) {
			pager.setCurrentItem(0);
		}

	}
}
