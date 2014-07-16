package cn.com.bjnews.thinker.debug;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import cn.com.bjnews.thinker.entity.YaoWenEntity;

public class MyDebug {
	
	/**
	 * 获取新闻列表
	 */
	public static ArrayList<YaoWenEntity> getNewsList(){
		ArrayList<YaoWenEntity> list = new ArrayList<YaoWenEntity>();
		YaoWenEntity entity;
		for(int i = 0;i<10;i++){
			entity = new YaoWenEntity();
			entity.setContent("content"+i);
			entity.setId("id:"+i);
		}
		Runtime.getRuntime().totalMemory();
		Runtime.getRuntime().maxMemory();
		return list;
	}
	
	static long tempTime = 0;
	
	public static void setCurrentTime(){
		tempTime= System.currentTimeMillis();
		Log.d("tag","Time:"+tempTime);
	}
	
	public static void getTime(int i){
		Log.d("tag",i+"--SpendTime:"+(System.currentTimeMillis()-tempTime));
	}
	
	
	public static void testSubList(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		List<String> temp =  list.subList(1, list.size());
		for(int i=0;i<temp.size();i++){
			Log.d("tag",temp.get(i)+"temp_>"+i);
		}
		
	}
	
}
