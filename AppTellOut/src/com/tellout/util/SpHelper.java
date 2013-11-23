package com.tellout.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SpHelper {
	
	private SharedPreferences sp = null;
	
	public SpHelper(Context context){
		 sp=context.getSharedPreferences("Tucao", Context.MODE_PRIVATE);
	}
	
	public void putStr(String key,String value){
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public String getStr(String key){
		return sp.getString(key, "");
	}
	
	public void putBool(String key,boolean value){
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public boolean getBool(String key){
		return sp.getBoolean(key, false);
	}
	
}
