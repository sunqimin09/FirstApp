package com.sun.appdianpinghair.internet;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.util.Log;

public class MemoryCache {

	private static final String TAG = "MemoryCache";

	private Map<String, SoftReference<Bitmap>> cache = Collections
			.synchronizedMap(new LinkedHashMap<String, SoftReference<Bitmap>>(10, 1.5f, true));
	
	private long size = 0;// current allocated size
	
	private long limit = 1000000;// max memory in bytes

	private static MemoryCache cacheTemp;
	
	private MemoryCache() {
		// use 25% of available heap size
		setLimit(Runtime.getRuntime().maxMemory() / 12);
	}

	public static MemoryCache getInstance(){
		if(cacheTemp == null){
			cacheTemp = new MemoryCache();
		}
		return cacheTemp;
	}
	
	public void setLimit(long new_limit) {
		limit = new_limit;
		Log.i(TAG, "MemoryCache will use up to " + limit / 1024. / 1024. + "MB");
	}

	public Bitmap get(String id) {
		try {
			if (!cache.containsKey(id))
				return null;
			return cache.get(id).get();
		} catch (NullPointerException ex) {
			return null;
		}
	}

	public void put(String id, Bitmap bitmap) {
		try {
			if (cache.containsKey(id))
				size -= getSizeInBytes(cache.get(id).get());
			
			cache.put(id, new SoftReference<Bitmap>(bitmap));
			
			size += getSizeInBytes(bitmap);
			checkSize();
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	/**
	 * �ϸ���ƶ��ڴ棬���������滻�������ʹ�õ��Ǹ�ͼƬ����
	 * 
	 */
	public void checkSize() {
		Log.i(TAG, "cache Clean size-->" + (cache.size()) + "size->"+size+"plimit"+limit+"total->"+Runtime.getRuntime().totalMemory());
		Log.d("tag","memory-before1->"+Runtime.getRuntime().totalMemory());
		if (size > limit*0.8) {
			// �ȱ����������ʹ�õ�Ԫ��
			Iterator<Entry<String, SoftReference<Bitmap>>> iter = cache.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, SoftReference<Bitmap>> entry = iter.next();
				size -= getSizeInBytes(entry.getValue().get());
//				entry.getValue().get().recycle();
				iter.remove();
				if (size <= limit*0.5)
					break;
			}
			System.gc();
			Log.i(TAG, "cache Clean size==>" + (cache.size())+ "size->"+size+"plimit"+limit+"total->"+Runtime.getRuntime().totalMemory());
			Log.d("tag","memory-before2->"+Runtime.getRuntime().totalMemory());
			
			Log.d("tag","memory-->"+Runtime.getRuntime().totalMemory());
			Log.i(TAG, "cache.Clean  size"+size+"New size memory->"+Runtime.getRuntime().totalMemory());
		}
	}

	public void clear() {
		cache.clear();
	}

	/**
	 * ͼƬռ�õ��ڴ�
	 * 
	 * [url=home.php?mod=space&uid=2768922]@Param[/url] bitmap
	 * 
	 * @return
	 */
	long getSizeInBytes(Bitmap bitmap) {
		if (bitmap == null)
			return 0;
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
}
