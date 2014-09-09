package com.sun.appdianpinghair.internet;

import android.content.Context;
import android.util.Log;

public class FileCache extends AbstractFileCache{

	public FileCache(Context context) {
		super(context);
	
	}

	/**
	 * 获得url 对应文件存储路径
	 */
	@Override
	public String getSavePath(String url) {
		
		String filename = FileHelper.getFileName(url);
		Log.d("tag","图片下载--cacheDir>"+getCacheDir()+getCacheDir());
		return getCacheDir() + filename;
	}
	

	@Override
	public String getCacheDir() {
		return FileHelper.getSaveImgPath();
	}
	
}
