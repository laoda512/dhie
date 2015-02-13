package com.tavx.C9Alarm.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.SDResReadManager;
import util.SDResReadManager.LoadOverCallBack;
import util.download.entity.DownloadInfo;
import util.download.entity.LoadInfo;
import util.download.service.Downloader;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVQuery.CachePolicy;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.ProgressCallback;
import com.mozillaonline.providers.DownloadManager;
import com.mozillaonline.providers.downloads.Downloads;
import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.R;
import com.tavx.C9Alarm.Symbol;
import com.tavx.C9Alarm.Utils;
import com.tavx.C9Alarm.Manager.PointManager;
import com.tavx.C9Alarm.adapter.DownloadListAdapter;
import com.tavx.C9Alarm.bean.DownloadItem;
import com.tavx.C9Alarm.cloud.CloudManager;
import com.tavx.C9Alarm.cloud.Bean.AVOS.RemoteFormData;
import com.tavx.C9Alarm.cloud.Bean.Bmob.form_point;
import com.tavx.C9Alarm.cloud.Interface._RemoteFormData;

public class DownLoadsFragment2<T extends BmobFile, V extends BmobObject>
		extends BaseFragment implements DownloadMaster {
	public static final int INSTALL_FAILED = -1;
	public static final int INSTALLING = -2;

	public Map<Object, DownloadItem> downloadMap = new HashMap<Object, DownloadItem>(); // key
	// AppInfo
	// id，value
	// 下载进度信息
	public Map<String, Object> mapping = new HashMap<String, Object>(); // key
	// downloadId，value
	// AppInfo
	// id

	String formId;
	float rate;
	List<RemoteFormData> remoteFormlist;
	DownloadListAdapter mDownloadListAdapter;
	// DownloadManager dm;

	Handler mHander = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.obj != null) {
				if (mDownloadListAdapter.isItemVisible((DownloadItem) msg.obj)) {
					MyLogger.e("aaaa", "need_Refresh");
					mDownloadListAdapter.notifyDataSetChanged();
				}
			} else {
				MyLogger.e("aaaa", "force_RefreshALL");
				mDownloadListAdapter.notifyDataSetChanged();
			}

		};
	};;

	public static DownLoadsFragment2 newInstance(String formId) {
		DownLoadsFragment2 details = new DownLoadsFragment2();
		Bundle args = new Bundle();
		args.putString("formId", formId);
		details.setArguments(args);
		return details;
	}

	View root;
	ListView mlView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		MyLogger.e("aaaaxxx", "dlcreate");
		root = inflater.inflate(R.layout.downloadlist, null);
		mlView = (ListView) root.findViewById(R.id.listview);
		mlView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {

				new AlertDialog.Builder(getActivity())
						.setTitle("确定要删除该主题并重置下载记录么？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										if (((_RemoteFormData) mlView
												.getAdapter().getItem(position))
												.isLocal() == true) {
											AlarmApplication.getApp()
													.showError("初始主题无法删除哦~");
											return;
										}

										removeDownload((_RemoteFormData) mlView
												.getAdapter().getItem(position));
									}
								}).setNegativeButton("取消", null).create()
						.show();
				return false;

			}

		});
		// mlView.setChoiceMode(ListView.CHOICE_MODE_NONE);
		getList();
		// handleDownloadsChanged();
		return root;
	}

	private Map<String, Downloader> downloaders = new HashMap<String, Downloader>();
	int id = 0;

	public synchronized int getCount() {
		return id++;
	}

	public String getDownloadNameTag(_RemoteFormData mRemoteFormData) {
		return mRemoteFormData.getName();
	}

	public String getDownloadIdTag(_RemoteFormData mRemoteFormData) {
		return CloudManager.getFileUrl(mRemoteFormData.getZip());// mRemoteFormData.getZip().getUrl();
	}

	public synchronized long startDownload(_RemoteFormData mRemoteFormData) {

		// 判断SD卡是否可用
		if (!checkSDCardIsAvailable()) {
			return -1;
		}
		try {
			DownloadTask d = new DownloadTask(mRemoteFormData);
			d.execute("");

			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void removeDownload(_RemoteFormData mRemoteFormData) {
		removeDownload(getDownloadIdTag(mRemoteFormData));

	}

	public void removeDownload(String downloadIdtag) {
		DownloadItem di = downloadMap.get(downloadIdtag);
		if (downloaders.get(downloadIdtag) != null) {
			downloaders.get(downloadIdtag).delete();
			downloaders.get(downloadIdtag).reset();// (mRemoteFormData.getZip().getUrl());
			downloaders.remove(downloadIdtag);
		}

		downloadMap.remove(downloadIdtag);
		if (di != null) {
			mapping.remove(di.getName());
			SDResReadManager.getInstance().removeOneData(
					getInsatllName(di.getName()));
			SDResReadManager.getInstance().removeOneInstall(di.getName());
		}
		refreshAdapter(di);
	}

	private void refreshAdapter(DownloadItem di) {
		if (di == null) {
			mHander.sendEmptyMessage(0);
		} else {
			Message msg = new Message();
			msg.obj = di;
			mHander.sendMessage(msg);
		}

	}

	public void pauseDownload(_RemoteFormData mRemoteFormData) {
		Downloader d = downloaders.get(getDownloadIdTag(mRemoteFormData));
		d.pause();
	}

	public void resumeDownload(_RemoteFormData mRemoteFormData) {
		Downloader d = downloaders.get(getDownloadIdTag(mRemoteFormData));
		startDownload(mRemoteFormData);
	}

	class DownloadTask extends AsyncTask<String, Integer, LoadInfo> {
		Downloader downloader = null;
		View v = null;
		String urlstr = null;
		_RemoteFormData<T, V> mRemoteFormData;

		public DownloadTask(_RemoteFormData<T, V> mRemoteFormData) {
			this.mRemoteFormData = mRemoteFormData;
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected LoadInfo doInBackground(String... params) {
			if (!downloaders.containsKey(getDownloadIdTag(mRemoteFormData))) {
				downloader = new Downloader(
						CloudManager.getFileUrl(mRemoteFormData.getZip()),
						getSaveParth(mRemoteFormData.getName()), 1,
						getActivity(), loaderHandler,
						getDownloadIdTag(mRemoteFormData));
				downloaders.put(getDownloadIdTag(mRemoteFormData), downloader);
			} else {
				downloader = downloaders.get(getDownloadIdTag(mRemoteFormData));
			}

			if (downloader.isdownloading()) {
				return null;// mDownloader.download(0);
			}

			// 得到下载信息类的个数组成集合
			return downloader.getDownloaderInfors();
		}

		@Override
		protected void onPostExecute(LoadInfo mLoadInfo) {
			if (mLoadInfo != null) {
				DownloadItem ditem = downloadMap
						.get(getDownloadIdTag(mRemoteFormData));
				if (ditem == null) {
					ditem = new DownloadItem(getDownloadIdTag(mRemoteFormData),
							getDownloadNameTag(mRemoteFormData));

					downloadMap.put(getDownloadIdTag(mRemoteFormData), ditem);
					mapping.put(getDownloadNameTag(mRemoteFormData),
							getDownloadIdTag(mRemoteFormData));
				}
				int complete = mLoadInfo.getComplete();
				ditem.setStatus(DownloadManager.STATUS_RUNNING);
				ditem.setCurrentBytes(complete);
				ditem.setTotalBytes(mLoadInfo.getFileSize());

				downloader.download(0);
				// refreshAdapter(ditem);
				mHander.sendEmptyMessage(0);
			}
		}

	};

	private String getSaveParth(String nameWithoutPrifx) {
		String saveName = nameWithoutPrifx + ".prx";
		return SDResReadManager.path_download + saveName;
	}

	public void insertOneDownload(Long id, String name, Downloader mDownloader) {
		downloadMap.put(id, new DownloadItem("" + id, name));
		// mapping.put(name, id);
		downloaders.put(name, mDownloader);
	}

	public void removeOneDownload(Long id, String name) {
		downloadMap.remove(id);
		mapping.remove(name);
	}

	@Override
	public void onStart() {

		super.onStart();
	}

	Handler loaderHandler = new Handler() {
		public void handleMessage(Message msg) {
			int length = msg.arg1;
			int id = msg.arg2;
			String downloadIdtag = (String) msg.obj;
			// RemoteFormData mRemoteFormData = downloadMap.get(downloadIdtag);
			if (downloadMap.containsKey(downloadIdtag)
					&& downloaders.containsKey(downloadIdtag)) {
				final DownloadItem di = downloadMap.get(downloadIdtag);
				Downloader dr = downloaders.get(downloadIdtag);
				boolean needRefresh = false;
				if (dr.isFailed()) {
					needRefresh = true;
					if (di.getTotalBytes() < 0) {
						removeDownload(downloadIdtag);

					} else {
						di.setStatus(DownloadManager.STATUS_FAILED);
					}

				} else {
					int old_progress = di.getProgress();

					di.increastCurrentBytes(length);

					if (di.getCurrentBytes() >= di.getTotalBytes()
							&& di.getCurrentBytes() != 0) {
						di.setStatus(DownloadManager.STATUS_SUCCESSFUL);
						needRefresh = true;

						downloaders.get(downloadIdtag).delete();
						downloaders.get(downloadIdtag).reset();// (mRemoteFormData.getZip().getUrl());
						downloaders.remove(downloadIdtag);
						downloadMap.remove(downloadIdtag);
						DownloadItem ditem = new DownloadItem(di.getId(),
								di.getName());
						downloadMap.put(downloadIdtag, ditem);
						mapping.remove(di.getName());

						SDResReadManager.getInstance().installOne(
								getInsatllName(di.getName()),
								new LoadOverCallBack() {
									@Override
									public void LoadOver(Boolean isSuccess,
											String name) {
										// di.setStatus(INSTALL_FAILED);
										// mHander.sendEmptyMessage(0);
										refreshAdapter(di);
									}
								});

					} else if (dr.isdownloading()) {
						if (old_progress != di.getProgress()) {
							needRefresh = true;
						}
						di.setStatus(DownloadManager.STATUS_RUNNING);
						// di.setProgress((int) (length/di.getTotalBytes()));
					} else {
						di.setStatus(DownloadManager.STATUS_PAUSED);
						needRefresh = true;
						// di.setProgress((int) (length/di.getTotalBytes()));
					}

				}
				if (needRefresh)
					refreshAdapter(di);
			}

			// mHander.sendEmptyMessage(0);
			// ProgressBar bar = ProgressBars.get(url);

		}
	};

	@Override
	public void onStop() {

		super.onStop();
	}

	@Override
	public void onResume() {
		super.onResume();

		// AVQuery<AVObject> query2 = new AVQuery<AVObject>("file");
		// AVQuery<RemoteFormData> query2 =
		// AVObject.getQuery(RemoteFormData.class);
		// query2.setCachePolicy(AVQuery.CachePolicy.CACHE_THEN_NETWORK);
		// query2.include("jifen");
		// query2.orderByAscending("jifen.point");
		//
		// query2.findInBackground(new FindCallback<RemoteFormData>() {
		//
		// @Override
		// public void done(List<RemoteFormData> arg0, AVException arg1) {
		// List<RemoteFormData> buff_list = new ArrayList<RemoteFormData>();
		// if(arg0==null) return;
		//
		// for (RemoteFormData item : arg0) {
		// buff_list.add(item);
		// item.point=""+item.getAVObject("jifen").getInt("point");
		// //new PointManager().getPoint(item);
		// }
		//
		// mDownloadListAdapter = new DownloadListAdapter(buff_list,
		// getActivity(),DownLoadsFragment2.this);
		//
		// mlView.setAdapter(mDownloadListAdapter );
		// handleDownloadsChanged(buff_list);
		// }
		// });

		// AVQuery<AVObject> query = AVQuery.getQuery("form_point");
		// query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		// query.whereEqualTo("jifen", mRemoteFormData);
		// query.findInBackground(new FindCallback<AVObject>() {
		// public void done(List<AVObject> commentList, AVException e) {
		// if(e==null){
		//
		// if(commentList.size()==0){
		// AVObject myComment = new AVObject("form_point");
		// myComment.put("name", mRemoteFormData.getName());
		// myComment.put("point", 100);
		// myComment.put("jifen", mRemoteFormData);
		// myComment.saveInBackground();
		// }else{
		// AVObject myComment =commentList.get(0);
		// myComment.increment("point", 100);
		// myComment.saveInBackground();
		// MyLogger.e("point",""+myComment.get("point"));
		// }
		//
		// }
		// }
		//
		// }
		//
		// );

	}

	private void getList() {
		CloudManager.queryFromPoint(getActivity(), new Handler() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == CloudManager.success) {
					List<form_point> mlist = (List<form_point>) msg.obj;
					if (mlist instanceof form_point) {
						MyLogger.e("aaaa", "instanceof");
					} else {
						MyLogger.e("aaaa", "not instanceof");
					}
					List<_RemoteFormData<T, V>> resList = new ArrayList<_RemoteFormData<T, V>>();
					for (form_point item : mlist) {
						if (item.getJifen() != null) {
							item.getJifen().setPointForSelf(item);
							resList.add((_RemoteFormData<T, V>) item.getJifen());

						} else {

						}
					}
					mDownloadListAdapter = new DownloadListAdapter(resList,
							AlarmApplication.getApp(), DownLoadsFragment2.this);

					
					handleDownloadsChanged(resList);
					mlView.setAdapter(mDownloadListAdapter);
				}
				super.handleMessage(msg);
			}
		}, "jifen","jifen.discount");
	}

	protected void handleDownloadsChanged(List<_RemoteFormData<T, V>> buff_list) {
		for (int i = 0; i < buff_list.size(); i++) {
			_RemoteFormData<T, V> mRemoteFormData = buff_list.get(i);
			if (mRemoteFormData.getZip() == null) {
				continue;
			}

			Downloader mDownloader = new Downloader(
					CloudManager.getFileUrl(mRemoteFormData.getZip()),
					getSaveParth(mRemoteFormData.getName()), 1, getActivity(),
					loaderHandler, getDownloadIdTag(mRemoteFormData));

			DownloadItem mDownloadItem = new DownloadItem(
					getDownloadIdTag(mRemoteFormData),
					getDownloadNameTag(mRemoteFormData));
			if (!mDownloader.isFirst(CloudManager.getFileUrl(mRemoteFormData
					.getZip()))) {
				LoadInfo mLoadInfo = mDownloader.getDownloaderInfors();
				int complete = mLoadInfo.getComplete();
				mDownloadItem.setStatus(DownloadManager.STATUS_PAUSED);
				mDownloadItem.setCurrentBytes(complete);
				mDownloadItem.setTotalBytes(mLoadInfo.getFileSize());

			}
			downloadMap.put(getDownloadIdTag(mRemoteFormData), mDownloadItem);
			mapping.put(getDownloadNameTag(mRemoteFormData),
					getDownloadIdTag(mRemoteFormData));

			downloaders.put(getDownloadIdTag(mRemoteFormData), mDownloader);
			// TODO:
			//mHander.sendEmptyMessage(0);
		}
	}

	private String getInsatllName(String name) {
		return name + ".prx";
	}

	public int getProgressValue(long totalBytes, long currentBytes) {
		if (totalBytes == -1) {
			return 0;
		}
		return (int) (currentBytes * 100 / totalBytes);
	}

	/**
	 * 判断 SDCard是否可用
	 * 
	 * @return
	 */
	public boolean checkSDCardIsAvailable() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	@Override
	public DownloadItem getDownloadItem(_RemoteFormData mRemoteFormData) {
		return downloadMap.get(getDownloadIdTag(mRemoteFormData));
	}

}