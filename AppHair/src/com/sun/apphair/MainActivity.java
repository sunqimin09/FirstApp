package com.sun.apphair;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.sun.apphair.adapter.MainAdapter;
import com.sun.apphair.entity.RequestEntity;
import com.sun.apphair.entity.ResponseResult;
import com.sun.apphair.entity.ShopEntity;
import com.sun.apphair.internet.InternetHelper;
import com.sun.apphair.json.JsonMainList;
import com.sun.apphair.utils.Mconstant;

/**
 * 
 * 项目名称：Hair<br>
 * 文件名：MainActivity.java <br>
 * 作者：@sunqm <br>
 * 创建时间：2014-1-18 下午2:04:32 功能描述: 商店列表 版本 V 1.0 <br>
 */
public class MainActivity extends BaseAct implements OnItemClickListener {

	private ListView listview = null;

	private MainAdapter adapter = null;

	private ArrayList<ShopEntity> list = new ArrayList<ShopEntity>();
	/**坐标*/
	private long request_x,request_y;
	
	private int request_distance = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	/**
	 * 
	 */
	private void initView() {
		listview = (ListView) findViewById(R.id.main_ls);

		adapter = new MainAdapter(this, list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}

	/**
	 * 参数：坐标，筛选条件：distance:1000,
	 * 排序方式：价格高->低  ，低->高；评分：高->低  ； 距离：近到远
	 * 
	 */
	private void request(){
		RequestEntity requestEntity = new RequestEntity(this,
				Mconstant.URL_SHOPS);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("x", request_x);
		map.put("y", request_y);
		map.put("distance", request_distance);
		requestEntity.params = map;
		new InternetHelper(this).requestThread(requestEntity, this);
	}
	
	@Override
	public void requestSuccess(ResponseResult responseResult) {
		super.requestSuccess(responseResult);
		list = new JsonMainList().parse(responseResult, this);
		adapter.setData(list);//只更新20条数据
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void OnClick(View view) {

	}

	/*
	 * (non-Javadoc)
	 *  //进入详情页面
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent i = new Intent(MainActivity.this,ShopDetailAct.class);
		i.putExtra("shop", list.get((int)arg3));
		startActivity(i);
	}

}
