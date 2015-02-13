/**
 * 
 */
package com.tavx.C9Alam.connector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @author Administrator
 *
 */
public class responseBean {
	String errorContent;
	public String getErrorContent() {
		return errorContent;
	} 
	public int getSuccess() {
		return success;
	}
	public void setErrorContent(String errorContent) {
		this.errorContent = errorContent;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	int success ;
	
	public boolean isSuccess(){
		return success==1;
	}

	public static responseBean parseBean(String arg){
		Gson g =new GsonBuilder().create();
		return  g.fromJson((arg).replace("\\", ""), new TypeToken<responseBean>(){}.getType());
	}
}
