/**
 * 
 */
package cn.com.bjnews.thinker.act.test;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * @author sunqm
 * @version 创建时间：2014-8-7 上午10:38:09
 * TODO
 */
public class BaseTools {
	
	/**
	 * 
	 * @param context
	 * @return 屏幕宽度
	 */
	public static int getWindowsWidth(Activity context){
		DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		
		return metric.widthPixels;
	}
	
}
