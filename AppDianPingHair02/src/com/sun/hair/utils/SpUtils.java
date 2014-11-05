package com.sun.hair.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SpUtils {

	SharedPreferences sp = null;
	
	public SpUtils(Context context){
		sp = context.getSharedPreferences("xiufaxing", Context.MODE_PRIVATE);
	}
	
	public void putId(String id){
		Editor edit = sp.edit();
		edit.putString("userId", id);
		edit.commit();
	}
	
	public String getId(){
		return sp.getString("userId", "0");
	}
	
	public void put(String key,String value){
		Editor edit = sp.edit();
		
		edit.putString(key, value);
		edit.commit();
	}
	
	public String get(String key){
		return sp.getString(key, "");
	}
	
}
