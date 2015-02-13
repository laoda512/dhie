package com.tavx.C9Alarm.Fragment;

import com.tavx.C9Alarm.bean.DownloadItem;
import com.tavx.C9Alarm.cloud.Bean.AVOS.RemoteFormData;
import com.tavx.C9Alarm.cloud.Interface._RemoteFormData;

public interface DownloadMaster{
	public void pauseDownload(_RemoteFormData m);
	public  long startDownload(_RemoteFormData mRemoteFormData);
	public  void resumeDownload(_RemoteFormData mRemoteFormData);
	
	public DownloadItem getDownloadItem(_RemoteFormData mRemoteFormData);
}