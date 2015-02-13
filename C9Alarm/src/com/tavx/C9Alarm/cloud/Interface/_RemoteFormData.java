/**
 * 
 */
package com.tavx.C9Alarm.cloud.Interface;

import com.tavx.C9Alarm.cloud.Bean.Bmob.form_discount;

/**
 * @author Administrator
 * 
 */
public interface _RemoteFormData<T,V> {
	public T getZip();

	public String getName();

	public T getPic();

	public void setZip(T zip);

	public void setName(String name);

	public void setPic(T pic);
	
	public String getMinVer();
	public void setMinVer(String ver);
	
	
	
	
	public void setPointForSelf(V point);
	
	public V getPointForSelf();
	
	public boolean isLocal();
	public void setIsLocal(boolean isLocal);
	/**
	 * @return
	 */
	public String getAuthor();
	public void setAuthor(String a);
	public form_discount getForm_discount();
}
