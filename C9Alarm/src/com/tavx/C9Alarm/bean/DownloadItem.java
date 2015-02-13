package com.tavx.C9Alarm.bean;

public class DownloadItem {
	private String id;
	private long downloadId;
	private long currentBytes;
	private long totalBytes;
	private int status;
	private int progress;
	
	private String name;
	public static final int STATUS_NONE=-100;
	public DownloadItem(String id, String name) {
		super();
		this.id = id;
		this.name = name;
		status = STATUS_NONE;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getDownloadId() {
		return downloadId;
	}
	public void setDownloadId(long downloadId) {
		this.downloadId = downloadId;
	}
	public long getCurrentBytes() {
		return currentBytes;
	}
	
	public synchronized void increastCurrentBytes(int Bytes){
		currentBytes +=Bytes;
	}
	public void setCurrentBytes(long currentBytes) {
		this.currentBytes = currentBytes;
	}
	public long getTotalBytes() {
		return totalBytes;
	}
	public void setTotalBytes(long totalBytes) {
		this.totalBytes = totalBytes;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getProgress() {
		if(totalBytes==0) return 100;
		return (int) (currentBytes*100/totalBytes);
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	
}
