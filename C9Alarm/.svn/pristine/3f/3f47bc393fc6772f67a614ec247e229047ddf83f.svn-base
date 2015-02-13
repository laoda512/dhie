package com.tavx.C9Alarm.cloud.Bean.Bmob;



import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.google.gson.annotations.Expose;
import com.tavx.C9Alarm.cloud.Ignore;
import com.tavx.C9Alarm.cloud.Interface._RemoteFormData;

public class RemoteFormData extends BmobObject implements _RemoteFormData<BmobFileX,form_point>{
	@Expose
    private BmobFileX zip; 
	@Expose
    private String name;
	@Expose
	private BmobFileX pic;
	@Expose
	private String author;
	@Expose
	private String minVer;
//	@Expose 
//	private List<form_discount> discount;
	@Expose 
	private form_discount discount;
	
//	public List<form_discount> getDiscount() {
//		return discount;
//	}
//	public void setDiscount(List<form_discount> discount) {
//		this.discount = discount;
//	}
	public RemoteFormData() {
		super();
	}
	@Ignore
	public  Object my_Form_point;
	    
	    public com.tavx.C9Alarm.cloud.Bean.Bmob.form_point getForm_point() {
			return (com.tavx.C9Alarm.cloud.Bean.Bmob.form_point)my_Form_point;
		}
		public void setForm_point(com.tavx.C9Alarm.cloud.Bean.Bmob.form_point form_point) {
			this.my_Form_point = form_point;
		}
	
    public BmobFileX getZip() {
		return (BmobFileX)zip;
	}
	public String getName() {
		return name;
	}
	public BmobFileX getPic() {
		return (BmobFileX)pic;
	}
	public void setZip(BmobFileX zip) {
		this.zip = zip;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPic(BmobFileX pic) {
		this.pic = pic;
	}
	
	public void setPointForSelf(form_point point){
		my_Form_point = point;
	}
	
	public form_point getPointForSelf(){
		return (com.tavx.C9Alarm.cloud.Bean.Bmob.form_point)my_Form_point ;
	}
	 /**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	//public form_point form_point;
    private boolean isLocal = false;

	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.cloud.Interface._RemoteFormData#isLocal()
	 */
	@Override
	public boolean isLocal() {
		// TODO Auto-generated method stub
		return isLocal;
	}
	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.cloud.Interface._RemoteFormData#setIsLocal()
	 */
	@Override
	public void setIsLocal(boolean isLocal) {
		// TODO Auto-generated method stub
		this.isLocal = isLocal;
	}
	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.cloud.Interface._RemoteFormData#setZip(java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.cloud.Interface._RemoteFormData#getMinVer()
	 */
	@Override
	public String getMinVer() {
		// TODO Auto-generated method stub
		return minVer;
	}
	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.cloud.Interface._RemoteFormData#setMinVer(java.lang.String)
	 */
	@Override
	public void setMinVer(String ver) {
		// TODO Auto-generated method stub
		minVer = ver;
		
	}
	/* (non-Javadoc)
	 * @see com.tavx.C9Alarm.cloud.Interface._RemoteFormData#getForm_discount()
	 */
	@Override
	public form_discount getForm_discount() {
		// TODO Auto-generated method stub
		return discount;
	}
	

	

}