package com.sun.hair.utils;

import android.view.View;
import android.widget.TextView;

public class Utils {
	
	/**
	 * 格式�?�?��显示 �?距离
	 * @param distance
	 * @return
	 */
	public static String distanceFormat(int distance){
		
		if(distance==-1){//没有输入坐标
			return "0 m";
		}
		if(distance<1000){
			return String.valueOf(distance)+"m";
		}
		
		return String.valueOf(distance/1000)+"Km";
	}
	
	public static void showPrice(TextView tv,int price){
		if(price==0){//没有输入坐标
//			tv.setVisibility(View.GONE);
			tv.setText("￥"+String.valueOf(price)+"元");
		}else{
			tv.setVisibility(View.VISIBLE);
			tv.setText("￥"+String.valueOf(price)+"元");
		}
	}
	
}
