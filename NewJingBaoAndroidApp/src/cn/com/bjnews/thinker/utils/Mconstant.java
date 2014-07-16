package cn.com.bjnews.thinker.utils;

import android.os.Environment;

/**
 * 静态 量
 * @author sunqm
 * Create at:   2014-5-13 上午7:55:31 
 * TODO
 */
public class Mconstant {
	
	public static boolean isClickAble = true;
	/**是否可以向右滑动*/
//	public static boolean canPullEnd = false;
//	/**是否可以向左滑动*/
//	public static boolean canPullStart = true;
	
	public static int currentPageIndex = 0;
	
	public static boolean listViewIntercept = false;
	
	public static boolean viewPagerIntercept = false;
	
	public static boolean scrollviewIntercept = true;
	
	public final static String TEST = "";
	
	public final static String LOCAL_FILE_PATH = Environment.getExternalStorageDirectory()+"/bjnews/";
	
	public final static String LOCAL_FILE_NAME = LOCAL_FILE_PATH+"bjnews_settings.json";
	
	public final static String URL_HOME = "http://app.bjnews.com.cn/m/json/";
	/**频道*/
	public final static String URL_CHANNELS = URL_HOME + "bjnews_settings.json";
	/**新闻列表*/
	public final static String URL_NEWS = "";
	
	
}
