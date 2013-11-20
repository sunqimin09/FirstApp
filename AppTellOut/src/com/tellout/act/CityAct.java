package com.tellout.act;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tellout.city.MyDatabase;

public class CityAct extends Activity implements OnItemClickListener{

	private ListView listviewPro,listviewCitys;
	
	private MyDatabase myDatabase;
	
	private String provinces[][];//(2,n)
	
	private String citys[][];
	
	private int selectedProvinceId = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_act);
		initView();
	}

	private void initView() {
		myDatabase = new MyDatabase(this);
		listviewPro = (ListView) findViewById(R.id.citys_provinces_listview);
		listviewCitys = (ListView) findViewById(R.id.citys_city_listview);
		listviewPro.setOnItemClickListener(this);
		listviewCitys.setOnItemClickListener(this);
		initProvinces();
		initCitys("10000");
	}

	private void initProvinces() {
		Cursor c = myDatabase.getProvinces();
		provinces = new String[2][c.getCount()];
		provinces = getCitys(c);
		List<String> list = getShowData(provinces);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		listviewPro.setAdapter(adapter);
	}
	
	private void initCitys(String provinceId){
		Cursor c = myDatabase.getCities(provinceId);
		if(c.getCount()==0){
			citys = new String[2][1];
			citys[0][0] = provinces[0][selectedProvinceId];
			citys[1][0] = provinces[1][selectedProvinceId];
		}else{//大于0
			citys = new String[2][c.getCount()];
			citys = getCitys(c);
		}
		List<String> list = getShowData(citys);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,list);
		listviewCitys.setAdapter(adapter);
	}
	
	private String[][] getCitys(Cursor c){
		int cursorSize = c.getCount();
		String temp[][] = new String[2][cursorSize];
		Log.d("tag","cursorSize==>"+cursorSize);
		for (int j = 0; j < cursorSize; j++) {
			Log.d("tag","name==>"+temp[1][j]+"j->"+j);
			temp[0][j] =c.getString(0);
			temp[1][j] = c.getString(1);
			Log.d("tag","name==>"+temp[1][j]+"j->"+j);
			c.moveToNext();
		}
		c.close();
		return temp;
	}
	
	private List<String> getShowData(String temp[][]){
		List<String> list = new ArrayList<String>();
		int length = temp[0].length;
		for(int i = 0;i<length;i++){
			list.add(temp[1][i]);
		}
		return list;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch(arg0.getId()){
		case R.id.citys_provinces_listview:
			selectedProvinceId = arg2;
			initCitys(provinces[0][arg2]);
			break;
		case R.id.citys_city_listview:
			Log.d("tag","city-->"+citys[1][arg2]);
			Intent data = new Intent(CityAct.this,EditMyInforAct.class);
			data.putExtra("name", citys[1][arg2]);
			setResult(-101, data);
			finish();
			break;
		}
	}
	
}
