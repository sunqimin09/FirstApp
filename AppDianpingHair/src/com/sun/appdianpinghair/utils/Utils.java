package com.sun.appdianpinghair.utils;

import android.view.View;
import android.widget.TextView;

public class Utils {
	
	/**
	 * 格式化 需要显示 的 距离
	 * @param distance
	 * @return
	 */
	public static String distanceFormat(int distance){
		
		if(distance==-1){//没有输入坐标
			return "";
		}
		if(distance<1000){
			return String.valueOf(distance)+"m";
		}
		
		return String.valueOf(distance/1000)+"Km";
	}
	
	public static void showPrice(TextView tv,int price){
		if(price==0){//没有输入坐标
			tv.setVisibility(View.GONE);
		}else{
			tv.setVisibility(View.VISIBLE);
			tv.setText("￥"+String.valueOf(price)+"元");
		}
	}
	
}
