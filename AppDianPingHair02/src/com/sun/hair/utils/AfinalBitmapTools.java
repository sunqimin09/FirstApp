package com.sun.hair.utils;

import java.io.File;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class AfinalBitmapTools {
	
	static FinalBitmap fb = null;
	
	public static FinalBitmap initBitmap(Context context){
		if(fb!=null)
			return fb;
		fb=FinalBitmap.create(context);
  	  // ��ȡӦ�ó����������ڴ�  
      int maxMemory = (int) Runtime.getRuntime().maxMemory();  
      int cacheSize = (int) (maxMemory *0.75);
//      fb.clearCache();
      fb.configMemoryCacheSize(cacheSize);
      fb.configDiskCacheSize(50*1024);
      
      String imagePath = Environment.getDownloadCacheDirectory() + "/" + "appdianping";  
      Log.d("tag","path=====><"+imagePath);
      fb.configDiskCachePath(imagePath);
      return fb;
	}
	
	/**
	 * 获取图片存数路径
	 * @return
	 */
	public static String getImgPath(){
		String path = Environment.getExternalStorageDirectory() + "/" + "appdianping";
		File file = new File(path);
		if(!file.exists())
			file.mkdir();
		return path;  
	     
	}
	
	
}
