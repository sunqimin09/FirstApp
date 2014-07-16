package cn.com.bjnews.thinker.utils;

import android.content.Context;

import cn.com.bjnews.thinker.img.CommonUtil;

public class ImageUtils {
	
	private int screenWidth;
	private int screenHeight;
	public ImageUtils(Context context){
		screenWidth= CommonUtil.getScreenWidth(context);
		screenHeight = CommonUtil.getScreenHeight(context);
	}
	
	public int getWidth(){
		return screenWidth;
	}
	
	public int getNiceWidth(){
		
		return 0;
	}
	
	public static int getNiceHeigth(){
		return 0;
	}
	
	public void getImage(){
		
	}
	
}
