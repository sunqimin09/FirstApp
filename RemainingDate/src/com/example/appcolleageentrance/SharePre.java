package com.example.appcolleageentrance;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePre {
	
	/***/
	private SharedPreferences sp;
	
	
	private String ISFIRST="isFirst";
	
	private String CONTENT ="content";

	private String COLOR="color";
	
	

	private String YEAR="year";
	
	private String MONTH="month";
	
	private String DAY = "day"; 
	
	private String DAYS = "days";

	private boolean isFirst=true;
	
	public SharePre(Context context){
		sp = context.getSharedPreferences("Appwidget", Context.MODE_PRIVATE);
	}
	
	public boolean isFirst(){
		return sp.getBoolean(ISFIRST, true);
	}
	
	public void setFirst(boolean first){
		if(!first){
			isFirst = first;
			Editor ed = sp.edit();
			ed.putBoolean(ISFIRST, isFirst);
			ed.commit();
		}
		
	}
	
	
	public void setAd(boolean ad){
		Editor ed = sp.edit();
		ed.putBoolean("ad", ad);
		ed.commit();
	}
	
	public boolean hasAd(){
		return sp.getBoolean("ad", true);
	}
	
	public void writeStr(String key,String value){
		Editor ed = sp.edit();
		ed.putString(key, value);
		ed.commit();
	}
	
	public void writeInt(String key,int value){
		Editor ed = sp.edit();
		ed.putInt(key, value);
		ed.commit();
	}
	
	public String getStr(String key){
		return sp.getString(key, "");
	}
	
	public void setLong(String key,long value){
		Editor ed = sp.edit();
		ed.putLong(key, value);
		ed.commit();
	}
	
	public long get(String key){
		return sp.getLong(key,0);
	}
	
	public int getInt(String key){
		return sp.getInt(key, 0);
	}
	
	public String getCOLOR() {
		return COLOR;
	}
	
	public String getCONTENT() {
		return CONTENT;
	}

//	public void setCONTENT(String cONTENT) {
//		CONTENT = cONTENT;
//	}

	public String getYEAR() {
		return YEAR;
	}

	public void setYEAR(String yEAR) {
		YEAR = yEAR;
	}

	public String getMONTH() {
		return MONTH;
	}

	public void setMONTH(String mONTH) {
		MONTH = mONTH;
	}

	public String getDAY() {
		return DAY;
	}

	public void setDAY(String dAY) {
		DAY = dAY;
	}
	
	public String getDAYS() {
		return DAYS;
	}

	public void setDAYS(String dAYS) {
		DAYS = dAYS;
	}
	
	
	
}
