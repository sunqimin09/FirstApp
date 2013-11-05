package com.example.apptellout;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.adapter.TypeAdapter;
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
	
	private TypeAdapter adapter;
	
	private List<TypeEntity> list = new ArrayList<TypeEntity>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_type_act);
		initView();
	}

	private void initView() {
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
				
			}
			
		});
		
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.select_type_search://查询相似
			
			break;
		}
		
	}
	
}
