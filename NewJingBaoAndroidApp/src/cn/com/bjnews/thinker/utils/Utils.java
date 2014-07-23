package cn.com.bjnews.thinker.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.com.bjnews.thinker.R;

public class Utils {

	/**
	 * 将内容写入文件
	 * 
	 * @param content
	 * @param fileName
	 */
	public static void writeToFile(String content, String fileName) {
		File file = new File(fileName);
		File filePath = new File(Mconstant.LOCAL_FILE_PATH);
		if (!filePath.exists()) {
			filePath.mkdir();
		}
		if (file.exists()) {
			file.deleteOnExit();
		}
		try {
			// file.createNewFile();
			Log.d("tag", "content-->" + content);
			// 写入
			FileOutputStream out = new FileOutputStream(file);
			byte[] buffer = content.getBytes();
			out.write(buffer);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("resource")
	public static String readFile(String fileName) {
		File file = new File(fileName);
		String result = null;

		BufferedReader br = null;
		StringBuffer buffer = null;
		if (!file.exists()) {
			return null;
		}
		try {
			Log.d("tag", "filePath-->" + file.getPath());
			buffer = new StringBuffer();
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					file), "utf-8");
			br = new BufferedReader(isr);
			int s;
			while ((s = br.read()) != -1) {
				buffer.append((char) s);
			}
			result = buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}

	public static boolean renameFile(String oldName, String newName) {
		File cacheDir = new File(
				android.os.Environment.getExternalStorageDirectory(), "bjnews");
		File[] files = cacheDir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (String.valueOf(oldName.hashCode()).equals(
						files[i].getName())) {
					File file = new File(
							android.os.Environment.getExternalStorageDirectory()
									+ "/bjnews", oldName);
					file.renameTo(new File(android.os.Environment
							.getExternalStorageDirectory()
							+ "/bjnews/"
							+ newName));
					return true;
				}
			}
		}
		return false;
	}

	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			// PushManager.startWork(arg0, arg1, arg2)
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

	/**
	 * 改变 viewpager 下部显示图标
	 * 
	 * @param ll
	 * @param index
	 */
	public static void setViewPagerIcon(LinearLayout ll, int index) {
		for (int i = 0; i < ll.getChildCount(); i++) {
			if (index != i)
				((ImageView) ll.getChildAt(i))
						.setImageResource(R.drawable.scroll_selected);
		}
		if (index < ll.getChildCount()) {
			Log.d("tag", index + "icon-->" + ll.getChildAt(index));
			((ImageView) ll.getChildAt(index))
					.setImageResource(R.drawable.scroll_default);
		}

	}

	public void setViewUnable() {
		Mconstant.isClickAble = false;
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				Mconstant.isClickAble = true;
			}

		}, 500);
	}

	public String formatDate(String str) {
		Log.d("tag", "format-->" + str);
		// "Wed, 25 Sep 2013 12:10:17 +0200"
		SimpleDateFormat format = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss ZZZZ", Locale.ENGLISH);// "EEE, dd MMM yyyy HH:mm:ss ZZZZ");
		SimpleDateFormat formatShow = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			return formatShow.format(format.parse(str));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 当前数据是否是老数据
	 * @return
	 */
	public boolean isOld(String str){
		SimpleDateFormat format = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss ZZZZ", Locale.ENGLISH);// "EEE, dd MMM yyyy HH:mm:ss ZZZZ");
		
		Calendar c = Calendar.getInstance();  
		c.setTime(new Date());
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day-1);
		Date yesterday = new Date(c.getTimeInMillis());
		try {
			return yesterday.after(format.parse(str));//获得的日期比昨天还早
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	/**
	 * 像素转 dip
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		DisplayMetrics m = context.getResources().getDisplayMetrics();
		Log.d("tag", "scale==->" + scale + "dpi:" + m.densityDpi + "XX"
				+ m.xdpi + "YY" + m.ydpi);

		return (int) (pxValue / scale + 0.5f);
	}

	public static String getVersionName(Context context) throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),
				0);
		String version = packInfo.versionName;
		return version;
	}
	
	public static boolean getSystemVersionNew(){
		int currentapiVersion=android.os.Build.VERSION.SDK_INT;
		Log.d("tag","vsersion"+currentapiVersion);
		if(currentapiVersion<19){
			return false;
		}else{
			return true;
		}
	}
	
	/***
	 * mainActivity 是否在运行
	 * @param context
	 * @return
	 */
	public static boolean mainIsExit(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); 
		
		//test
		List<RunningTaskInfo> list1 = am.getRunningTasks(100); 
		for (RunningTaskInfo info : list1) { 
		    if (info.topActivity.getPackageName().equals("cn.com.bjnews.thinker") && info.baseActivity.getPackageName().equals("cn.com.bjnews.thinker")) { 
		    	Log.d("tag",(info.baseActivity.getClassName().equals("cn.com.bjnews.thinker.act.MainActivity"))+"info---222>"+info.topActivity.getClassName()+"<>");
		    	if(info.baseActivity.getClassName().equals("cn.com.bjnews.thinker.act.MainActivity")){
		    		return true;
		    	}
		        //find it, break 
		    } 
		}  
		return false;
	}

}
