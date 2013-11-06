package com.example.apptellout;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.adapter.TypeAdapter;
import com.example.entity.BaseEntity;
import com.example.entity.RequestEntity;
import com.example.entity.TypeEntity;

/**
 * 选择地点，公司，
 * @author sunqm
 *
 */
public class SelectType extends BaseActivity implements OnClickListener{

	private EditText etInput;
	
	private ImageView imgSearch;
	
	private ListView listView;
	
	private TypeAdapter adapter =null;
	
	private List<TypeEntity> list = new ArrayList<TypeEntity>();
	
	private int requestCode =-1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_type_act);
		initView();
	}

	private void initView() {
		requestCode = getIntent().getIntExtra("flag", -1);
		etInput = (EditText) findViewById(R.id.select_type_input);
		imgSearch = (ImageView) findViewById(R.id.select_type_search);
		listView = (ListView) findViewById(R.id.select_type_listview);
		imgSearch.setOnClickListener(this);
		
		adapter = new TypeAdapter(this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {//返回前以页面
				Intent data = new Intent(SelectType.this,EditMyInforAct.class);
				data.putExtra("name", list.get(arg2).getName());
				setResult(-101, data);
				finish();
			}
			
		});
		request();
		
	}

	/**
	 * 发起网络请求
	 */
	public void request(){
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setPost(false);
		requestEntity.setRequestType(requestCode);
		request(requestEntity);
	}
	
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public void showResult(int type, BaseEntity baseEntity) {
		super.showResult(type, baseEntity);
		list = (List<TypeEntity>) baseEntity.getList();
		adapter.setData(list);
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.select_type_search://查询相似
			
			break;
		}
		
	}
	
}
