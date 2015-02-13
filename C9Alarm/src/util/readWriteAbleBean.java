package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import com.tavx.C9Alam.connector.MyLogger;

import android.content.Context;
import android.content.SharedPreferences;


public abstract class readWriteAbleBean {
	private Boolean isOnLoad ;
	private String _tag;
	public readWriteAbleBean(Context c) {
		iniParam();
		isOnLoad = true;
		loadData(c);
		isOnLoad=false;
		
	}
	
	public readWriteAbleBean(Context c,String _tag) {
		this._tag=_tag;
		iniParam();
		isOnLoad = true;
		loadData(c);
		isOnLoad=false;
		
	}
	
	public abstract void iniParam();
	
	protected String getSaveName() {
		if(_tag!=null) return this.getClass().getName()+_tag;
		else 
		return this.getClass().getName();
	};
	
  
	
	private void loadData(Context c) {
		
		SharedPreferences s = c.getSharedPreferences(getSaveName(), 0);
		Map<?, ?> map = s.getAll();
		Iterator i = map.keySet().iterator();
		while (i.hasNext()) {
			String key = (String) i.next();
			Object value = map.get(key);
			Class t;
			if(value.getClass()==int.class||value.getClass()==Integer.class){
				t=Integer.class;
			}else if(value.getClass()==long.class||value.getClass()==Long.class){
				t=Long.class;
			}else if(value.getClass()==float.class||value.getClass()==Float.class){
				t=Float.class;
			}else if(value.getClass()==boolean.class||value.getClass()==Boolean.class){
				t=Boolean.class;
			}else if(value.getClass()==char.class||value.getClass()==Character.class){
				t=Character.class;
			}else{
				t=value.getClass();
			}
			Method m;
			try {
				m = getClass().getMethod(translateValueNameToSet(key),
						t);
				m.invoke(this, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public void writeData(Context c, String fieldName)
			 {
		if(isOnLoad==true) return;
		
		try {
			SharedPreferences.Editor e = c.getSharedPreferences(getSaveName(), 0)
					.edit();
			String mtName = translateValueNameToGet(fieldName);
			Method m = getClass().getMethod(mtName, null);
			Class type = m.getReturnType();
			if (type.equals(String.class)) {
				MyLogger.e("aaaa", "String save"+fieldName);
				e.putString(fieldName,
						(String) m.invoke(this, null));
			} else if (type.equals(Integer.class)) {
				MyLogger.e("aaaa", "Integer save"+fieldName);
				e.putInt(fieldName,
						(Integer) m.invoke(this, null));
			} else if (type.equals(Long.class)) {
				MyLogger.e("aaaa", "Long save"+fieldName);
				e.putLong(fieldName,
						(Long) m.invoke(this, null));
			} else if (type.equals(Float.class)) {
				MyLogger.e("aaaa", "Float save"+fieldName);
				e.putFloat(fieldName,
						(Float) m.invoke(this, null));
			} else if (type.equals(Boolean.class)) {
				MyLogger.e("aaaa", "Boolean save "+mtName+" "+(Boolean) m.invoke(this, null)); 
				e.putBoolean(fieldName,
						(Boolean) m.invoke(this, null));
			}else if (type.equals(int.class)) {
				MyLogger.e("aaaa", "Integer save"+fieldName);
				e.putInt(fieldName,
						(Integer) m.invoke(this, null));
			} else if (type.equals(long.class)) {
				MyLogger.e("aaaa", "Long save"+fieldName);
				e.putLong(fieldName,
						(Long) m.invoke(this, null));
			} else if (type.equals(float.class)) {
				MyLogger.e("aaaa", "Float save"+fieldName);
				e.putFloat(fieldName,
						(Float) m.invoke(this, null));
			} else if (type.equals(boolean.class)) {
				MyLogger.e("aaaa", "Boolean save "+fieldName+" "+(Boolean) m.invoke(this, null));
				e.putBoolean(fieldName,
						(Boolean) m.invoke(this, null));
			}else{
				MyLogger.e("aaaa", "none save"+fieldName+" "+type.getClass());
			}
			e.commit();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	protected void writeAllData(Context c) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		SharedPreferences.Editor e = c.getSharedPreferences(getSaveName(), 0)
				.edit();
		Method[] m = getClass().getMethods();
		for (int i = 0; i < m.length; i++) {
			if (m[i].getName().startsWith("get")
					&& m[i].getParameterTypes().length == 0) {
				Class type = m[i].getReturnType();
				if (type.equals(String.class)) {
					e.putString(translateMethodToName(m[i].getName(), "get"),
							(String) m[i].invoke(this, null));
				} else if (type.equals(Integer.class)) {
					e.putInt(translateMethodToName(m[i].getName(), "get"),
							(Integer) m[i].invoke(this, null));
				} else if (type.equals(Long.class)) {
					e.putLong(translateMethodToName(m[i].getName(), "get"),
							(Long) m[i].invoke(this, null));
				} else if (type.equals(Float.class)) {
					e.putFloat(translateMethodToName(m[i].getName(), "get"),
							(Float) m[i].invoke(this, null));
				} else if (type.equals(Boolean.class)) {
					e.putBoolean(translateMethodToName(m[i].getName(), "get"),
							(Boolean) m[i].invoke(this, null));
				}else if (type.equals(int.class)) {
					e.putInt(translateMethodToName( m[i].getName(), "get"),
							(Integer)  m[i].invoke(this, null));
				} else if (type.equals(long.class)) {
					e.putLong(translateMethodToName( m[i].getName(), "get"),
							(Long)  m[i].invoke(this, null));
				} else if (type.equals(float.class)) {
					e.putFloat(translateMethodToName( m[i].getName(), "get"),
							(Float)  m[i].invoke(this, null));
				} else if (type.equals(boolean.class)) {
					e.putBoolean(translateMethodToName( m[i].getName(), "get"),
							(Boolean)  m[i].invoke(this, null));
				}else{
				}
			}
		}
		e.commit();
	}

	private String translateValueNameToSet(String content) {
		return translateValueName(content, "set");
	}

	private String translateValueNameToGet(String content) {
		return translateValueName(content, "get");
	}

	private String translateValueName(String content, String perfix) {
		if (content.length() > 1) {
			MyLogger.e("aaaa", content+" "+perfix+" "+Character.toUpperCase(content.charAt(0))+" "+content.substring(0)+" "+(perfix + Character.toUpperCase(content.charAt(0))
					+ content.substring(1)));
			content = perfix + Character.toUpperCase(content.charAt(0))
					+ content.substring(1);
		}
		return content;
	}

	private String translateMethodToName(String content, String perfix) {
		content = content.substring(perfix.length());
		content = Character.toLowerCase(content.charAt(0))
				+ content.substring(1);
		return content;
	}
}
