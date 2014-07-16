package cn.com.bjnews.thinker.img;

import cn.com.bjnews.thinker.utils.FileDown;

import android.content.Context;
import android.util.Log;

public class FileCache extends AbstractFileCache{

	public FileCache(Context context) {
		super(context);
	
	}

	@Override
	public String getSavePath(String url) {
		Log.d("tag","图片下载--cacheDir>"+getCacheDir());
		String filename = FileDown.getFileName(url);
				
		return getCacheDir() + filename;
	}

	@Override
	public String getCacheDir() {
		
		return FileManager.getSaveFilePath();
	}

}
