package com.sun.apphair;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.sun.apphair.adapter.MyOrdersAdapter;
import com.sun.apphair.entity.OrderEntity;
import com.sun.apphair.entity.ResponseResult;
/**
 * 
 * 项目名称：Hair <br>
 * 文件名：TerminalDetailsFragment.java <br>
 * 作者：@sunqm    <br>
 * 创建时间：2014-2-11 下午3:46:01
 * 功能描述: 
 * 版本 V 1.0 <br>
 */
public class MyOrdersAct extends BaseAct{

	private ListView listview;
	
	private MyOrdersAdapter adapter = null;
	
	private List<OrderEntity> list = new ArrayList<OrderEntity>();
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_orders_act);
		initView();
	}

	/**
	 * 
	 */
	private void initView() {
		listview = (ListView) findViewById(R.id.my_orders_lv);
		adapter = new MyOrdersAdapter(this, list);
		listview.setAdapter(adapter);
	}
	
	/**
	 * 请求订单列表，已完成，未完成
	 */
	private void request(){
		
	}
	
	@Override
	public void requestSuccess(ResponseResult responseResult) {
		super.requestSuccess(responseResult);
	}
}
