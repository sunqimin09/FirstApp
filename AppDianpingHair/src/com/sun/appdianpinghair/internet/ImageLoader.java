package com.sun.appdianpinghair.internet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoader {

	private FileCache fileCache;
	
	private MemoryCache memoryCache;
	
	private ExecutorService executorService;
	
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	
	public ImageLoader(Context context){
		fileCache = new FileCache(context);
		memoryCache = MemoryCache.getInstance();
		executorService = Executors.newFixedThreadPool(5);
	}
	
	public void DisPlay(ImageView imageView,String url){
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		}else  {
			queuePhoto(url, imageView);
		}
	}
	
	private void queuePhoto(String url, ImageView imageView) {
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(p));
	}
	
	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			Log.d("tag", "图片下载:1");
			if (imageViewReused(photoToLoad))
				return;
			Log.d("tag", "图片下载:2" + photoToLoad.url);
			Bitmap bmp = null;
			try {
				bmp = decodeBitmap(getFile(photoToLoad.url));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			memoryCache.put(photoToLoad.url, bmp);
			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);

			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);

		}
	}
	
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null) {
				photoToLoad.imageView.setImageBitmap(bitmap);
			}
		}
	}
	
		boolean imageViewReused(PhotoToLoad photoToLoad) {
			String tag = imageViews.get(photoToLoad.imageView);
			if (tag == null || !tag.equals(photoToLoad.url))
				return true;
			return false;
		}
		
	/***
	 * 下载 文件
	 * @param url
	 * @return
	 */
	public File getFile(String url) {
		
		File f = fileCache.getFile(url);
		Log.d("tag","图片下载:3");
//		Bitmap b = null;
		if (f != null && f.exists()) {
			return f;
		}
		Log.d("tag","图片下载:4");
		try {
			Log.d("tag","图片下载:"+url);
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			CopyStream(is, os);
			os.close();
			Log.d("tag","图片下载end:"+url);
			return f;
		} catch (Exception ex) {
			Log.e("",
					"getBitmap catch Exception...\nmessage = "
							+ ex.getMessage());
			return null;
		}
	}

	
	
	/**
	 * 只调用native 方法（据说省内存）
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 */
	private Bitmap decodeBitmap(File file) throws FileNotFoundException {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// opt.inSampleSize = 4;
		// 获取资源图片
		InputStream is = new FileInputStream(file);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	private static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
			Log.e("", "CopyStream catch Exception...");
		}
	}
	
}
