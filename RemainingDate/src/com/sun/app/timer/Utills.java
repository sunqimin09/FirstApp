package com.sun.app.timer;

import java.util.Calendar;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class Utills {
	

	private int[] pics = { R.drawable.num00, R.drawable.num01,
			R.drawable.num02, R.drawable.num03, R.drawable.num04,
			R.drawable.num05, R.drawable.num06, R.drawable.num07,
			R.drawable.num08, R.drawable.num09};
	
	private int[] imgViews ={R.id.imageView1,R.id.imageView2,R.id.imageView3,R.id.imageView4};
	
	
	public void setNum(int num,RemoteViews rv){
		num=Math.abs(num);
		char[] nums = (num+"").toCharArray();
		switch(nums.length){
		case 1://个位数
			Log.d("tag","pics-->"+nums[0]);
			rv.setImageViewResource(imgViews[0], pics[0]);
			rv.setImageViewResource(imgViews[1], pics[0]);
			rv.setImageViewResource(imgViews[2], pics[0]);
			rv.setImageViewResource(imgViews[3], pics[Integer.parseInt(nums[0]+"")]);
			break;
		case 2://十位数
			rv.setImageViewResource(imgViews[0], pics[0]);
			rv.setImageViewResource(imgViews[1], pics[0]);
			rv.setImageViewResource(imgViews[2], pics[Integer.parseInt(nums[0]+"")]);
			rv.setImageViewResource(imgViews[3], pics[Integer.parseInt(nums[1]+"")]);
			break;
		case 3://百位数
			rv.setImageViewResource(imgViews[0], pics[0]);
			rv.setImageViewResource(imgViews[1], pics[Integer.parseInt(nums[0]+"")]);
			rv.setImageViewResource(imgViews[2], pics[Integer.parseInt(nums[1]+"")]);
			rv.setImageViewResource(imgViews[3], pics[Integer.parseInt(nums[2]+"")]);
			break;
		case 4://四位
			rv.setImageViewResource(imgViews[0], pics[Integer.parseInt(nums[0]+"")]);
			rv.setImageViewResource(imgViews[1], pics[Integer.parseInt(nums[1]+"")]);
			rv.setImageViewResource(imgViews[2], pics[Integer.parseInt(nums[2]+"")]);
			rv.setImageViewResource(imgViews[3], pics[Integer.parseInt(nums[3]+"")]);
			break;
		}
	}
	
	public void setText(String str,RemoteViews rv){
		if(str!=null)
			rv.setTextViewText(R.id.tv_widget, str);
	}
	
	public boolean isOutOfDate(Calendar nowCalendar,Calendar comCalendar){
		
		return true;
	}
	
	
	
	/**
	 * 比较 两个日期间隔
	 * @param nowCalendar
	 * @param comCalendar
	 * @return
	 */
	public int compare(Calendar nowCalendar,Calendar comCalendar){
		
		if(nowCalendar.before(comCalendar)){//当前日期小
			long millis = (comCalendar.getTimeInMillis()-nowCalendar.getTimeInMillis());
			Log.d("tag","millis--->"+millis);
			int day =(int) (millis/(1000*60*60*24));
			return day;
		}else if(nowCalendar.after(comCalendar)){
//			long millisT =(nowCalendar.getTimeInMillis()-comCalendar.getTimeInMillis());
//			Log.d("tag","millisTTT--->"+millisT);
//			int dayT =(int) (millisT/(1000*60*60*24));
//			return -dayT;
			return 0;
		}else{
			return 0;
		}
	}
	
	/**
	 * 给当前 views 添加监听事件
	 * @param context
	 * @param views
	 */
	public void addListener(Context context,RemoteViews views){
		Intent fullIntent=new Intent(context,MainActivity.class);
		fullIntent.setAction("com.sun");
		PendingIntent pendingIntent=PendingIntent.getActivity(context, 0, fullIntent, 0);
//		views.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
		views.setOnClickPendingIntent(R.id.ll, pendingIntent);
	}
	
	
}
