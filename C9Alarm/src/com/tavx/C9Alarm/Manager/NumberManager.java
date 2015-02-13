/**
 * 
 */
package com.tavx.C9Alarm.Manager;

/**
 * @author Administrator
 *
 */
public class NumberManager {
	
	private static NumberManager instance;
	public final String mix = "@";
	public static NumberManager getInstance(){
		if(instance==null)instance  =new NumberManager();
		return instance;
	}
	
	public int parseInt(String str){
		String res=str.replaceAll(mix, "");
		try {
			int i = Integer.parseInt(res);
			return i;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	} 
	
	public String getMixedString(int i){
		StringBuilder str = new StringBuilder(""+i);
		int count = str.length();
		for(int x=0;x<count;x++){
			str.insert(x*2, mix);
		}
		return str.toString();
	}
	
	public boolean isEqual(String a,String b){
		return parseInt(a)==parseInt(b);
	}

	public boolean isLarger(String a,String b){
		return parseInt(a)>parseInt(b);
	}
	
	public boolean isLargerEqual(String a,String b){
		return parseInt(a)>=parseInt(b);
	}
	
	public boolean isSmallerEqual(String a,String b){
		return parseInt(a)<=parseInt(b);
	}
	
	public boolean isSmaller(String a,String b){
		return parseInt(a)<parseInt(b);
	}
	
	
	public String add(String str,int count){
		return getMixedString(parseInt(str)+count);
	}
	
	public String minus(String str,int count){
		return getMixedString(parseInt(str)-count);
	}
	
	public String add(String str,String count){
		return add(str, parseInt(count));
	}
	
	public String minus(String str,String count){
		return minus(str, parseInt(count));
	}
}
