package com.sun.appxiaolinyuan;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author sunqm E-mail:sunqimin09@163.com
 * @version 创建时间：2013-11-5 上午11:43:34
 * @description 
 */
public class SpHelper {
	
	SharedPreferences sp = null;
	
	public SpHelper(Context context){
		sp = context.getSharedPreferences("sunXiaolin", Context.MODE_PRIVATE);
	}
	
	public void put(String key,Long value){
		Editor et = sp.edit();
		et.putLong(key, value);
		et.commit();
	}
	
	public void put(String key,String value){
		Editor et = sp.edit();
		et.putString(key, value);
		et.commit();
	}
	
	public void put(String key,int value){
		Editor et = sp.edit();
		et.putInt(key, value);
		et.commit();
	}
	
	public String getStr(String key){
		return sp.getString(key, "");
	}
	
	public long getLong(String key){
		return sp.getLong(key, 0L);
	}
	
	public int getInt(String key){
		return sp.getInt(key, 0);
	}
	
}	
