package com.tavx.C9Alarm.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.SDResReadManager;
import util.SDResReadManager.LoadOverCallBack;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.ProgressCallback;
import com.mozillaonline.providers.DownloadManager;
import com.mozillaonline.providers.downloads.Downloads;
import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.R;
import com.tavx.C9Alarm.adapter.DownloadListAdapter;
import com.tavx.C9Alarm.bean.DownloadItem;
import com.tavx.C9Alarm.cloud.Bean.AVOS.RemoteFormData;

public class DownLoadsFragment extends Fragment{

	public static final int INSTALL_FAILED = -1;
	public static final int INSTALLING = -2;

	public Map<Long, DownloadItem> downloadMap = new HashMap<Long, DownloadItem>(); // key
																					// AppInfo
																					// id，value
																					// 下载进度信息
	public Map<String, Long> mapping = new HashMap<String, Long>(); // key
																	// downloadId，value
																	// AppInfo
																	// id

	String formId;
	float rate;
	List<RemoteFormData> remoteFormlist;
	DownloadListAdapter mDownloadListAdapter;
	DownloadManager dm;
	MyContentObserver mContentObserver = new MyContentObserver();

	Handler mHander = new Handler() {
		public void handleMessage(android.os.Message msg) {

			mDownloadListAdapter.notifyDataSetChanged();
		};
	};;

	public static DownLoadsFragment newInstance(String formId) {
		DownLoadsFragment details = new DownLoadsFragment();
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
		root = inflater.inflate(R.layout.downloadlist, null);
		mlView = (ListView) root.findViewById(R.id.listview);
		// mlView.setChoiceMode(ListView.CHOICE_MODE_NONE);
		dm = new DownloadManager(getActivity().getContentResolver(),
				getActivity().getPackageName());
		// handleDownloadsChanged();
		return root;
	}

	public synchronized long startDownload(RemoteFormData mRemoteFormData) {

		// 判断SD卡是否可用
		if (!checkSDCardIsAvailable()) {
			return -1;
		}
		try {

			if (mapping.containsKey(mRemoteFormData.getName())) {
				return mapping.get(mRemoteFormData.getName());
			}

			String url = mRemoteFormData.getZip().getUrl();

			Uri srcUri = Uri.parse(url);
			DownloadManager.Request request = new com.mozillaonline.providers.DownloadManager.Request(
					srcUri);
			request.setVisibleInDownloadsUi(true);
			// 设置文件保存的路径
			String name = mRemoteFormData.getName();
			String saveName = name + ".prx";
			request.setDestinationInExternalFilesDir(AlarmApplication.getApp(),
					"download", saveName);
			request.setDescription(name);
			// request.setShowRunningNotification(false);

			Long id = dm.enqueue(request);
			insertOneDownload(id, name);

			MyLogger.e("download", "start" + id + " " + saveName, true);
			// mRemoteFormData.downId = id;
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void insertOneDownload(Long id, String name) {
		downloadMap.put(id, new DownloadItem("" + id, name));
		mapping.put(name, id);
	}

	public void removeOneDownload(Long id, String name) {
		downloadMap.remove(id);
		mapping.remove(name);
	}

	@Override
	public void onStart() {

		getActivity().getContentResolver().registerContentObserver(
				Downloads.CONTENT_URI, true, mContentObserver);

		super.onStart();
	}

	@Override
	public void onStop() {

		getActivity().getContentResolver().unregisterContentObserver(
				mContentObserver);

		super.onStop();
	}

	@Override
	public void onResume() {
		super.onResume();
		

		AVQuery<AVObject> query2 = new AVQuery<AVObject>("file");

		query2.findInBackground(new FindCallback<AVObject>() {

			@Override
			public void done(List<AVObject> arg0, AVException arg1) {
				List<RemoteFormData> buff_list = new ArrayList<RemoteFormData>();
				if(arg0==null) return;
				for (AVObject item : arg0) {
					AVFile av = item.getAVFile("zip");
					if(av!=null){
					MyLogger.e("faaauck", av.getUrl()+" "+av.getName());
					RemoteFormData buff = new RemoteFormData();
					buff.setName((String) item.get("name"));
					MyLogger.e("faaauck",(String) item.get("name"));
					buff.setZip(av);
					buff_list.add(buff);
					
                    av . getDataInBackground(new GetDataCallback() {
						
						@Override
						public void done(byte[] arg0, AVException arg1) {
							MyLogger.e("actest", "byte "+arg0.length);
						}
					},new ProgressCallback() {
						
						@Override
						public void done(Integer arg0) {
							MyLogger.e("actest", ""+arg0);
						}
					});
					
					
					
					}
				}
//				 mDownloadListAdapter = new DownloadListAdapter(buff_list,
//				 getActivity(),dm,DownLoadsFragment.this);
				
				 mlView.setAdapter(mDownloadListAdapter );
				 handleDownloadsChanged();
			}
		});


	}

	private String getInsatllName(String name) {
		return name + ".prx";
	}

	public void handleDownloadsChanged() {

		DownloadManager.Query baseQuery = new DownloadManager.Query()
				.setOnlyIncludeVisibleInDownloadsUi(true);

		Cursor cursor = dm.query(baseQuery);

		int mIdColumnId = cursor
				.getColumnIndexOrThrow(DownloadManager.COLUMN_ID);
		int mStatusColumnId = cursor
				.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS);
		int mTotalBytesColumnId = cursor
				.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
		int mCurrentBytesColumnId = cursor
				.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
		int mDescriptionColumnId = cursor
				.getColumnIndexOrThrow(DownloadManager.COLUMN_DESCRIPTION);
		MyLogger.e("download", "refresh" + cursor.getCount(), true);
		while (cursor.moveToNext()) {

			long downloadId = cursor.getLong(mIdColumnId);
			long totalBytes = cursor.getLong(mTotalBytesColumnId);
			long currentBytes = cursor.getLong(mCurrentBytesColumnId);
			int status = cursor.getInt(mStatusColumnId);
			String description = cursor.getString(mDescriptionColumnId);

			if (!mapping.containsKey(description)) {
				// MyLogger.e("download", description+" "+status+" "+progress);
				// insertOneDownload(downloadId,description);
				if (status == DownloadManager.STATUS_SUCCESSFUL
						|| status == DownloadManager.STATUS_FAILED) {
					dm.remove(downloadId);
					dm.markRowDeleted(downloadId);
					MyLogger.e("download", "success or fail" + status + " del "
							+ description);
				} else {
					MyLogger.e("download", "resume download " + status + " "
							+ description);
					insertOneDownload(downloadId, description);
					if (status == DownloadManager.STATUS_RUNNING) {
						// dm.pauseDownload(downloadId);
						// status = DownloadManager.STATUS_PAUSED;
					}
				}
			}
			MyLogger.e("download", "-0--- download " + status + " " + description);
			int progress = getProgressValue(totalBytes, currentBytes);
			MyLogger.e("download", "change" + downloadId, true);
			final DownloadItem di = downloadMap.get((downloadId));

			if (di != null) {

				switch (status) {
				case DownloadManager.STATUS_SUCCESSFUL:
					di.setStatus(INSTALLING);
					mapping.remove(di.getName());
					SDResReadManager.getInstance().installOne(
							getInsatllName(di.getName()),
							new LoadOverCallBack() {
								@Override
								public void LoadOver(Boolean isSuccess,
										String name) {
									di.setStatus(INSTALL_FAILED);
									mHander.sendEmptyMessage(0);
								}
							});
					break;
				case DownloadManager.STATUS_FAILED:
					di.setStatus(DownloadManager.STATUS_FAILED);
					mapping.remove(di.getName());
					break;
				default:
					di.setStatus(status);
					break;
				}

				di.setDownloadId(downloadId);
				di.setTotalBytes(totalBytes);
				di.setCurrentBytes(currentBytes);
				// di.setStatus(status);
				di.setProgress(progress);
				MyLogger.e("download",
						"di!=null" + downloadId + " " + progress, true);

			}
		}
		cursor.close();

		mHander.sendEmptyMessage(0);
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

	private class MyContentObserver extends ContentObserver {

		public MyContentObserver() {
			super(new Handler());
		}

		@Override
		public void onChange(boolean selfChange) {

			handleDownloadsChanged();

		}
	}

}