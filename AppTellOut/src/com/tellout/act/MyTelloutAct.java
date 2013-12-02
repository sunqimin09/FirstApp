package com.tellout.act;

import android.os.Bundle;
import android.widget.ListView;

import com.tellout.entity.BaseEntity;
import com.tellout.entity.RequestEntity;

public class MyTelloutAct extends BaseActivity{

	private ListView listview =null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_tellout_act);
		initView();
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.listView1);
		
	}

	@Override
	public void request(RequestEntity requestEntity) {
		// TODO Auto-generated method stub
		super.request(requestEntity);
	}

	@Override
	public void showResult(int type, BaseEntity baseEntity) {
		// TODO Auto-generated method stub
		super.showResult(type, baseEntity);
	}
	
}
