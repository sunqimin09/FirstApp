package com.tellout.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.tellout.adapter.MyHistoryInforAdapter;
import com.tellout.constant.DbConstant;
import com.tellout.constant.MConstant;
import com.tellout.entity.BaseEntity;
import com.tellout.entity.RequestEntity;
import com.tellout.entity.UserEntity;

public class MyHistoryInforAct extends BaseActivity {

	private ListView listview;
	
	private MyHistoryInforAdapter adapter = null;
	
	private List<UserEntity> list = new ArrayList<UserEntity>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_infor_history_act);
		initView();
		request(new RequestEntity());
	}

	private void initView() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.listView1);
		adapter = new MyHistoryInforAdapter(this, list);
		listview.setAdapter(adapter);
	}

	public void onClick(View view){
		finish();
	}
	
	@Override
	public void request(RequestEntity requestEntity) {
		requestEntity.setPost(false);
		requestEntity.setRequestType(MConstant.REQUEST_CODE_MY_HISTORY_INFOR);
		HashMap<String,String> map = new HashMap<String,String>();
		map.put(DbConstant.DB_USER_ID, MConstant.USER_ID_VALUE);
//		map.put(dbcon, value)
		requestEntity.setParams(map);
		super.request(requestEntity);
	}

	@Override
	public void showResult(int type, BaseEntity baseEntity) {
		super.showResult(type, baseEntity);
		list = (List<UserEntity>) baseEntity.getList();
//		adapter.notifyDataSetChanged();
		adapter.setaData(list);
	}
	
	
}
