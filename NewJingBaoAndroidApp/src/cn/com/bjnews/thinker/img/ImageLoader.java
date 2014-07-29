package cn.com.bjnews.thinker.img;

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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class ImageLoader {

	public static ImageLoader imageloader = null;
	
	private MemoryCache memoryCache = null;
	private static AbstractFileCache fileCache;
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	// �̳߳�
	private ExecutorService executorService;

//	private Bitmap defaultBitmap;
	
	private int defaultWidth;
	
	private int defaultHeight;
	
	private int defaultDrawable;
	
	/**是否缩小*/
	private boolean isScaleAble = false;
	
	private Context context;
	
	private boolean setWidth = false;
	
	private int aimWidth =0;
	
	public ImageLoader(Context context,int drawable,boolean isScale) {
		memoryCache =MemoryCache.getInstance();
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(5);
		//???
		Bitmap b = ImgUtils.getBitmap(context, drawable);
//		Bitmap b = BitmapFactory.decodeResource(context.getResources(), drawable);
		defaultWidth = b.getWidth();
		defaultHeight = b.getHeight();
		b.recycle();
		b =null;
		defaultDrawable = drawable;
		this.context = context;
		this.isScaleAble = isScale;
		setWidth = false;
	}

	public void setImgWidth(int width){
		setWidth = true;
		aimWidth = width;
	}
	
	
	private boolean isBg = true;
	
	private boolean maxScreen = false;
	
	public void setIsBg(boolean isBg){
		this.isBg = isBg;
	}
	
	public void setMaxSreen(boolean maxScreen){
		this.maxScreen = maxScreen;
	}
	
	// ����Ҫ�ķ���
	public void DisplayImage(String url, ImageView imageView,
			boolean isLoadOnlyFromCache) {
		if(imageView == null){
			return;
		}
//		imageView.setImageResource(defaultDrawable);//ImageBitmap(BitmapFactory.decodeResource(context.getResources(), defaultDrawable));
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		Log.d("tag",url+"First--image->--download->"+bitmap);
		if (bitmap != null) {
//			Log.d("tag", "showimage==imageWidth-bitmap>" + bitmap.getWidth()
//					+ "height:" + bitmap.getHeight());
			// imageView.destroyDrawingCache();
			ViewGroup.LayoutParams params = imageView.getLayoutParams();
			if (setWidth&&params!=null) {
				
				if (maxScreen
						&& bitmap.getHeight() * aimWidth / bitmap.getWidth() > CommonUtil
								.getScreenHeight(context)) {// 大于屏幕高度
					params.height = CommonUtil.getScreenHeight(context);
					params.width = bitmap.getWidth()
							* CommonUtil.getScreenHeight(context)
							/ bitmap.getHeight();
				} else {
					params.width = aimWidth;
					params.height = bitmap.getHeight() * aimWidth
							/ bitmap.getWidth();
					Log.d("tag","ImageHeight-->"+params.height);
				}
				imageView.setLayoutParams(params);
			}
			
			Log.d("tag","showimage==imageWidth->"+imageView.getWidth()+"height:"+imageView.getHeight());
			if(isBg)
				imageView.setBackground(new BitmapDrawable(bitmap));
			else
				imageView.setImageBitmap(bitmap);
			Log.d("tag","ImageWidth--Height1:"+imageView.getHeight());
//			imageView.setImageBitmap(bitmap);
			Log.d("tag","showimage--put2>"+Runtime.getRuntime().totalMemory());
		}else if (!isLoadOnlyFromCache) {
			// ��û�еĻ��������̼߳���ͼƬ
			queuePhoto(url, imageView,setWidth);
//			imageView.setBackgroundResource(defaultDrawable);
		}
	}

	
	private void queuePhoto(String url, ImageView imageView,boolean setWidth) {
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		p.setWidth = setWidth ;
		executorService.submit(new PhotosLoader(p));
	}

	private Bitmap getBitmap(String url) {
		
		File f = fileCache.getFile(url);
		Log.d("tag","图片下载:3"+f+url);
		Bitmap b = null;
		if (f != null && f.exists()) {
			b = decodeFile(f);
		}
		Log.d("tag","图片下载:4"+b);
		if (b != null) {
			return b;
		}
		
		try {
			Log.d("tag","图片下载:"+url);
			Bitmap bitmap = null;
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
			bitmap = decodeFile(f);
			Log.d("tag","memory--put3>"+Runtime.getRuntime().totalMemory());
			return bitmap;
		} catch (Exception ex) {
			Log.e("",
					"getBitmap catch Exception...\nmessage = "
							+ ex.getMessage());
			return null;
		}
	}

	// decode���ͼƬ���Ұ����������Լ����ڴ���ģ�������ÿ��ͼƬ�Ļ����СҲ�������Ƶ�
	private Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			o.inSampleSize = computeSampleSize(o, -1, defaultWidth*defaultHeight); 
			o.inJustDecodeBounds = false;
			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 100;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (isScaleAble) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
//			o.inSampleSize = 
			Log.d("tag","scale-->"+scale);
			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static int computeSampleSize(BitmapFactory.Options options,

	        int minSideLength, int maxNumOfPixels) {

	    int initialSize = computeInitialSampleSize(options, minSideLength,

	            maxNumOfPixels);



	    int roundedSize;

	    if (initialSize <= 8) {

	        roundedSize = 1;

	        while (roundedSize < initialSize) {

	            roundedSize <<= 1;

	        }

	    } else {

	        roundedSize = (initialSize + 7) / 8 * 8;

	    }



	    return roundedSize;

	}

	
	private static int computeInitialSampleSize(BitmapFactory.Options options,

	        int minSideLength, int maxNumOfPixels) {

	    double w = options.outWidth;

	    double h = options.outHeight;



	    int lowerBound = (maxNumOfPixels == -1) ? 1 :

	            (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));

	    int upperBound = (minSideLength == -1) ? 128 :

	            (int) Math.min(Math.floor(w / minSideLength),

	            Math.floor(h / minSideLength));



	    if (upperBound < lowerBound) {

	        // return the larger one when there is no overlapping zone.

	        return lowerBound;

	    }



	    if ((maxNumOfPixels == -1) &&

	            (minSideLength == -1)) {

	        return 1;

	    } else if (minSideLength == -1) {

	        return lowerBound;

	    } else {

	        return upperBound;

	    }

	} 
	
	/**
	 * 改变图片的大小
	 * @param from
	 * @param to
	 * @return
	 */
	private Bitmap resizeBitmap(Bitmap from ) {

		int width = from.getWidth();
		int height = from.getHeight();
		// 创建操作图片用的Matrix对象
		Matrix matrix = new Matrix();
		// 计算缩放比例
		float sx = ((float)   defaultWidth / width);
		float sy = ((float)   defaultHeight / height);
		Log.d("tag","memory==before>"+Runtime.getRuntime().totalMemory());
		long before = Runtime.getRuntime().totalMemory();
		// 设置缩放比例
		matrix.postScale(sx , sy );
		// 建立新的bitmap，其内容是对原bitmap的缩放后的图
//		boolean err = false;
		try{
			from = Bitmap.createBitmap(from, 0, 0, width, height, matrix,
					true);
		}catch(java.lang.OutOfMemoryError error){
			clearCache();
//			err = true;
		}finally{
//			if(err)
//			from = BitmapFactory.decodeResource(context.getResources(), defaultDrawable);
		}
		
		Log.d("tag",Runtime.getRuntime().totalMemory()+"memory==>"+(Runtime.getRuntime().totalMemory()-before)+"bitmap->");
		return from;
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public boolean setWidth =false;
		
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
			Log.d("tag","图片下载:1");
			if (imageViewReused(photoToLoad))
				return;
			Log.d("tag","图片下载:2"+photoToLoad.url);
			Bitmap bmp = getBitmap(photoToLoad.url);
			memoryCache.put(photoToLoad.url, bmp);
			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			// ���µĲ�������UI�߳���
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
			
		}
	}

	/**
	 * ��ֹͼƬ��λ
	 * 
	 * @param photoToLoad
	 * @return
	 */
	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// ������UI�߳��и��½���
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
				Log.d("tag", "showimage-bitmap>" + bitmap.getWidth()
						+ "Height:>" + bitmap.getHeight());

				// photoToLoad.imageView.setImageBitmap(resizeBitmap(bitmap));
				ViewGroup.LayoutParams params = photoToLoad.imageView
						.getLayoutParams();
				if (photoToLoad.setWidth&&params!=null) {
					Log.d("tag","params>"+bitmap.getHeight());
					if (maxScreen
							&& bitmap.getHeight() * aimWidth
									/ bitmap.getWidth() > CommonUtil
										.getScreenHeight(context)) {// 大于屏幕高度
						params.height = CommonUtil.getScreenHeight(context);
						params.width = bitmap.getWidth()*CommonUtil.getScreenHeight(context)/bitmap.getHeight();
					} else {
						
						params.width = aimWidth;
						params.height = bitmap.getHeight() * aimWidth
								/ bitmap.getWidth();
					}

					photoToLoad.imageView.setLayoutParams(params);
					Log.d("tag",params.height+"ImageWidth--Height3:"+photoToLoad.imageView.getHeight());
				}
				Log.d("tag","showimage-imagewith-result_width>"+photoToLoad.url);
				if(isBg)
					photoToLoad.imageView.setBackground(new BitmapDrawable(bitmap));
				else{
					Log.d("tag","shwoimage-url"+photoToLoad.url+bitmap);
					photoToLoad.imageView.setImageBitmap(bitmap);
				}
					
//					
//				}
				Log.d("tag","ImageWidth--Height2:"+photoToLoad.imageView.getHeight());
				if(photoToLoad.imageView.getParent()!=null)
				Log.d("tag","showimage-parent>"+photoToLoad.imageView.getParent());
//				
			}
		}
	}
	
	

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

	public static void CopyStream(InputStream is, OutputStream os) {
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