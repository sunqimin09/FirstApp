package com.sun.apphair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sun.apphair.adapter.KeyValueAdapter;
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

	private TextView tv_Distance,tv_Order;
	
	private ListView listview = null;

	private MainAdapter adapter = null;

	private ArrayList<ShopEntity> list = new ArrayList<ShopEntity>();
	/**坐标*/
	private long request_x,request_y;
	
	private int request_distance = 0;
	
	/**当前排序选项*/
	private int currentOrder = 0;
	
	private int currentDistance = 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	/**
	 * 控件初始化
	 */
	private void initView() {
		listview = (ListView) findViewById(R.id.main_ls);
		tv_Distance = (TextView) findViewById(R.id.main_distance_tv);
		tv_Order = (TextView) findViewById(R.id.main_order_tv);
		initOrderPop();
		initDistancePop();
		adapter = new MainAdapter(this, list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}

	/**
	 * 数据初始化
	 */
	private void initData() {
		//p
		HashMap<String, Object> map = null;
		for(int i = 0;i<orders.length;i++){
			map = new HashMap<String, Object>();
			map.put("key", i);
			map.put("value", orders[i]);
			orderHashMaps.add(map);
		}
		
		for(int i = 0;i<distances.length;i++){
			map = new HashMap<String, Object>();
			map.put("key", i);
			map.put("value", distances[i]);
			distanceMaps.add(map);
		}
	}
	
	/**
	 * 参数：坐标，筛选条件：distance:1000,
	 * 排序方式：价格高->低  ，低->高；评分：高->低  ； 距离：近到远
	 * 
	 */
	private void request(int orderby){
		RequestEntity requestEntity = new RequestEntity(this,
				Mconstant.URL_SHOPS);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("x", request_x);
		map.put("y", request_y);
		map.put("distance", request_distance);
		map.put("orderby", orderby);
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
		switch(view.getId()){
		case R.id.main_distance_tv://距离
			popDistance.showAsDropDown(tv_Distance);
			break;
		case R.id.main_order_tv://排序方式
			Toast("order");
			popOrder.showAsDropDown(tv_Order);
			break;
		}
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

	private void initOrderPop(){
		View popView = View.inflate(this, R.layout.menu_listview, null);
		ListView listViewPop = (ListView) popView.findViewById(R.id.listView1);
		KeyValueAdapter adapter = new KeyValueAdapter(this, orderHashMaps);
		listViewPop.setAdapter(adapter);
		
		popOrder = new PopupWindow(popView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,true);
		popOrder.setBackgroundDrawable(new BitmapDrawable());
		popOrder.setOutsideTouchable(true);
		
		listViewPop.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				popOrder.dismiss();
				tv_Order.setText(orderHashMaps.get(arg2).get("value").toString());
				currentOrder = arg2;
			}
			
		});
	}
	
	private void initDistancePop(){
		View popView = View.inflate(this, R.layout.menu_listview, null);
		ListView listViewPop = (ListView) popView.findViewById(R.id.listView1);
		KeyValueAdapter adapter = new KeyValueAdapter(this, distanceMaps);
		listViewPop.setAdapter(adapter);
		
		popDistance = new PopupWindow(popView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,true);
		popDistance.setBackgroundDrawable(new BitmapDrawable());
		popDistance.setOutsideTouchable(true);
		
		listViewPop.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				popDistance.dismiss();
				tv_Distance.setText(distanceMaps.get(arg2).get("value").toString());
				currentDistance =Integer.parseInt(distanceMaps.get(arg2).get("value").toString());
			}
			
		});
	}
	
//	private void showPopwindow(View view) {
//		pop.showAsDropDown(view);
//	}
	
	
	
	private PopupWindow popOrder = null;
	
	private PopupWindow popDistance = null;
	
	private List<HashMap<String,Object>> distanceMaps = new ArrayList<HashMap<String,Object>>();
	
	private List<HashMap<String,Object>> orderHashMaps = new ArrayList<HashMap<String,Object>>();
	
	private String[] orders = {"距离","评分","价格: 低->高","价格: 高->低"};

	private int[] distances = {1000,2000,5000};

	
}
