package com.example.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author sunqm E-mail:sunqimin09@163.com
 * @version 创建时间：2013-11-12 下午4:10:53
 * @description 
 */
public class DbManager {
	
	public DbManager(){
		
	}
	
	public  void getList(){
		
	}
	
	public void select(int id){
		
	}
	
	public void insert(){
		
	}
	
	public void update(){
		
	}
	
	public void delete(int id){
		
	}
	
	
	class dbHelper extends SQLiteOpenHelper{

		public dbHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

//		public dbHelper(Context context,String name){
//			super(context,name,"",1);
//		}
		/**字段:id,content,time,*/
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
