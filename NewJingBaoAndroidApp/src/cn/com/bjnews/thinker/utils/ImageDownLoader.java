package cn.com.bjnews.thinker.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

/**
 * @author sunqm E-mail:sunqimin09@163.com
 * @version 创建时间：2013-10-8 下午6:03:24
 * @description
 */
public class ImageDownLoader {

	private HashMap<String, SoftReference<Drawable>> cache = new HashMap<String, SoftReference<Drawable>>();
	private File cacheDir;
	private Context context;
	private int defaultDrawable = 0;
	/** 程序内 缓存中图片的数量 */
	private int limitSize = 10;

	/**
	 * @param context
	 * @param drawableID
	 *            显示默认的图片
	 */
	public ImageDownLoader(Context context, int drawableID) {

		this.context = context;
		defaultDrawable = drawableID;
		/** 设置优先级 */
		photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"bjnews");
		else
			cacheDir = context.getCacheDir();
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}

	public void DisplayImage(String url, ImageView imageView) {
		Runtime r =Runtime.getRuntime();
		r.gc();
		r.freeMemory();
		Log.d("tag","mapsize-->"+cache.size()+"total-><"+r.totalMemory()+"max->"+r.maxMemory());
//		try {
      			imageView.setTag(url);
			if (isExist(url)) {// if exist
				imageView.setImageDrawable(cache.get(url).get());
			} else {
				// down
				queuePhoto(url, imageView);
				imageView.setImageResource(defaultDrawable);
			}
//		} catch (java.lang.OutOfMemoryError exception) {
//			limitSize--;
//			exception.printStackTrace();
//		}

	}

	public void setCacheSize(int size) {
		limitSize = size;
	}

	/**
	 * 从cache 中清除size 个 图片
	 * 
	 * @param size
	 */
	private void clearCache(int size) {
		
		HashMap<String, Drawable> temp = (HashMap<String, Drawable>) cache.clone();
		if (cache.size() < limitSize)// 小于不清除
			return;
		Iterator it = temp.keySet().iterator();
		while (it.hasNext() && size-- > 0) {
			cache.remove(it.next());
		}
		temp =null;
		Runtime.getRuntime().freeMemory();
	}

	/**
	 * 下载图片 1.检查下载队列中是否存在，存在 2.如果不存在，加入下载队列
	 * 
	 */

	private void queuePhoto(String url, ImageView img) {
		photosQueue.Clean(img);
		PhotoToLoad p = new PhotoToLoad(url, img);
		synchronized (photosQueue.photosToLoad) {
			photosQueue.photosToLoad.push(p);
			photosQueue.photosToLoad.notifyAll();
		}
		if (photoLoaderThread.getState() == Thread.State.NEW)
			photoLoaderThread.start();

	}

	PhotosQueue photosQueue = new PhotosQueue();

	class PhotosQueue {
		private Stack<PhotoToLoad> photosToLoad = new Stack<PhotoToLoad>();

		/** 请求该imageView */
		public void Clean(ImageView image) {
			for (int i = 0; i < photosToLoad.size();) {
				if (photosToLoad.get(i).imageView == image) {
					photosToLoad.remove(i);
				} else
					++i;

			}
		}
	}

	PhotosLoader photoLoaderThread = new PhotosLoader();

	/**
	 * 图片下载线程
	 * 
	 * @author sunqm
	 * 
	 */
	class PhotosLoader extends Thread {

		@Override
		public void run() {
			try {
				while (true) {
					if (photosQueue.photosToLoad.size() == 0)
						synchronized (photosQueue.photosToLoad) {
							// 等待被notify，唤醒
							photosQueue.photosToLoad.wait();
						}
					if (photosQueue.photosToLoad.size() != 0) {
						PhotoToLoad photoToLoad;
						synchronized (photosQueue.photosToLoad) {
							photoToLoad = photosQueue.photosToLoad.pop();
						}
						Drawable drawable = getDrawable(photoToLoad.url);
						clearCache(5);
						cache.put(photoToLoad.url, new SoftReference<Drawable>(drawable));
						drawable = null;
						Object tag = photoToLoad.imageView.getTag();
						if (tag != null
								&& ((String) tag).equals(photoToLoad.url)) {// 显示图片
							DrawableDisplayer dDisplayer = new DrawableDisplayer(
									drawable, photoToLoad.imageView);
							Activity a = (Activity) photoToLoad.imageView
									.getContext();
							a.runOnUiThread(dDisplayer);
						}
					}
					if (Thread.interrupted())
						break;

				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * @author sunqm
	 * 
	 */
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	/** 判断该url的文件是否存在 */
	private boolean isExist(String url) {
		boolean isExist = false;
		File[] files = cacheDir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if ((String.valueOf(url.hashCode()) + getLastFileName(url))
						.equals(files[i].getName())) {
					clearCache(5);
					cache.put(url, new SoftReference<Drawable>(decodeFile(files[i])));
					isExist = true;
					break;
				} else {
					isExist = false;
				}
			}
		}
		return isExist;
	}

	/**
	 * 将文件保存成defaultDrawable 一样大小的图片
	 * 
	 * @param f
	 * @return
	 */
	private Drawable decodeFile(File f) {

		try {
			SoftReference<Drawable> d =new SoftReference<Drawable>( Drawable.createFromStream(new FileInputStream(f),
					f.getName()));
			Log.d("tag", "imageload-->" + f.getName() + "path" + f.getPath());
			// Log.d("vjifen","getdrawable-before-height->"+d.getIntrinsicHeight()+"width-->"+d.getIntrinsicWidth());
			SoftReference<Drawable> drawableDefault =new SoftReference<Drawable>(context.getResources().getDrawable(
					defaultDrawable));
			// Log.d("vjifen","getdrawable-airm-height->"+drawableDefault.getIntrinsicHeight()+"width-->"+drawableDefault.getIntrinsicWidth());
			if (d == null) {
				d = drawableDefault;
			}
			d = scale(d, drawableDefault.get().getIntrinsicWidth(),
					drawableDefault.get().getIntrinsicHeight());
			drawableDefault = null;
			// Log.d("vjifen","getdrawable--height->"+d.getIntrinsicHeight()+"width-->"+d.getIntrinsicWidth());
			return d.get();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressLint("NewApi")
	private SoftReference<Drawable> scale(SoftReference<Drawable> drawable, int w, int h) {

		int width = drawable.get().getIntrinsicWidth();
		int height = drawable.get().getIntrinsicHeight();
		// drawable转换成bitmap
		Bitmap oldbmp = drawableToBitmap(drawable);
		drawable =null;
		// 创建操作图片用的Matrix对象
		Matrix matrix = new Matrix();
		// 计算缩放比例
		float sx = ((float) w / width);
		float sy = ((float) h / height);
		// 设置缩放比例
		matrix.postScale(sx * 2, sy * 2);
		Log.d("tag","OldBitmap==>"+oldbmp.getByteCount());
		// 建立新的bitmap，其内容是对原bitmap的缩放后的图
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
				matrix, true);
		
		Drawable d =new BitmapDrawable(newbmp);
		SoftReference<Drawable> result = new SoftReference<Drawable>(d);
		oldbmp =null;
		newbmp = null;
		d = null;
		return result;
		// }
	}

	private static Bitmap drawableToBitmap(SoftReference<Drawable> drawable) {
		// 取 drawable 的长宽
		int w = drawable.get().getIntrinsicWidth();
		int h = drawable.get().getIntrinsicHeight();

		// 取 drawable 的颜色格式
		Bitmap.Config config = drawable.get().getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立对应 bitmap 的画布
		Canvas canvas = new Canvas(bitmap);
		drawable.get().setBounds(0, 0, w, h);
		// 把 drawable 内容画到画布中
		drawable.get().draw(canvas);
		drawable =null;
		return bitmap;
	}

	/**
	 * 将url 保存成文件，并读取成drawable
	 * 
	 * @param url
	 * @return
	 */
	public Drawable getDrawable(String url) {
		String filename = String.valueOf(url.hashCode()) + getLastFileName(url);
		Log.d("tag", "fileName-->" + filename);
		File f = new File(cacheDir, filename);

		Drawable d = decodeFile(f);
		if (d != null) {
			return d;
		}
		Drawable drawable = null;
		try {
			InputStream is = new URL(url).openStream();
			OutputStream out = new FileOutputStream(f);
			CopyStream(is, out);
			out.close();
			drawable = decodeFile(f);

			return drawable;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void CopyStream(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		int hasRead = 0;
		while ((hasRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, hasRead);
		}
	}

	/**
	 * 
	 * @author sunqm
	 * 
	 */
	class DrawableDisplayer implements Runnable {

		Drawable drawable;
		ImageView imageView;

		public DrawableDisplayer(Drawable d, ImageView i) {
			drawable = d;
			imageView = i;
		}

		@Override
		public void run() {
			if (drawable != null)
				imageView.setImageDrawable(drawable);
			else
				imageView.setImageResource(defaultDrawable);

		}

	}

	public void clearCache() {
		cache.clear();
		File[] files = cacheDir.listFiles();
		for (File f : files) {
			f.delete();
		}
	}

	private String getLastFileName(String url) {
		String[] temp = url.split("\\.");
		for (int i = 0; i < temp.length; i++) {
			Log.d("tag", "temp->" + temp[i]);
		}
		return "." + url.split("\\.")[url.split("\\.").length - 1];
	}

}
