package com.sun.appdianpinghair.img;

import android.os.Environment;


public class FileManager {
	
	public static String getSavePath(){
		if(hasExternal()){
			
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}else{
			return Environment.getDataDirectory().getAbsolutePath();
		}
	}
	
	private static boolean hasExternal(){
		return android.os.Environment.getExternalStorageState().equals( 
				android.os.Environment.MEDIA_MOUNTED);//sd存在并可写 
				
	}
	
}
