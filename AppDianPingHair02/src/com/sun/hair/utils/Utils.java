package com.sun.hair.utils;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.telephony.TelephonyManager;
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
	
	/**
	 * 获取手机唯一标识
	 * @param context
	 * @return
	 */
	public static String getImei(Context context){
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}
	
	public void writeFile(Bitmap b, File f) {
		try {
			FileOutputStream out = new FileOutputStream(f);
			b.compress(CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
}
