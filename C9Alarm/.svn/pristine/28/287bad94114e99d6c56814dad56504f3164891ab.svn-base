package com.tavx.C9Alarm.cloud.Bean.AVOS;



import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

@AVClassName("file")
public class RemoteFormData extends AVObject{
    private AVFile zip;
    private String name;
    private AVFile pic;
    private String auther;
    
//    public String point="--";
    
    public AVObject form_point;
    
    public String getAuthor() {
    	return getString("author");
	}
	public void setAuthor(String author) {
		put("author", auther);
	}
	public AVFile getPic() {
		return getAVFile("pic");
	}
    public void setPic(AVFile pic) {
		put("pic", pic);
	}
	public AVFile getZip() {
		return getAVFile("zip");
	}
	public String getName() { 
		return getString("name");
	}
	public void setZip(AVFile zip) {
		put("zip", zip);
	}
	public void setName(String name) {
		put("name", name);
	}
	
	public Long downId=-1l;
    public boolean isLocal = false;

}