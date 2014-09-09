package com.sun.appdianpinghair.internet;

import java.io.File;

import android.os.Environment;
import android.util.Log;

public class FileHelper {

	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		} 
		return true;
	}
	
	public static boolean createDirectory(String path){
		//创建跟文件夹
		File root = new File(getSavePath());
		if(!root.exists()){
			return root.mkdirs();
		}
		/*创建 图片文件夹*/
		File f = new File(path) ;
		if(!f.exists()){
			return f.mkdirs();
		}
		return false;
	}
	
	public static void deleteDirectory(String path){
		File f = new File(path) ;
		if(!f.exists()){
			f.delete();
		}
	}
	
	private static String getRootPath(){
		if (hasSDCard()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";// filePath:/sdcard/
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath: /data/data/
		}
	}
	
	public static String getSaveImgPath(){
		StringBuilder sb = new StringBuilder(getRootPath());
		sb.append("bjHair/imageCache/");
		return sb.toString();
	}

	private static String getSavePath(){
		StringBuilder sb = new StringBuilder(getRootPath());
		sb.append("bjHair");
		return sb.toString();
	}
	
	/**
	 * 获取 文件名
	 * @param url
	 * @return
	 */
	public static String getFileName(String url){
		Log.d("tag","图片下载--filename>"+url);
		StringBuilder sb = new StringBuilder(String.valueOf(url.hashCode()));
		sb.append(".").append(url.split("\\.")[url.split("\\.").length-1]);
		Log.d("tag","图片下载--filename-end>"+sb);
		return sb.toString();
	}

	
}
