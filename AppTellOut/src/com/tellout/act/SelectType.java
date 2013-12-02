package com.tellout.act;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.tellout.adapter.TypeAdapter;
import com.tellout.entity.BaseEntity;
import com.tellout.entity.CityEntity;
import com.tellout.entity.RequestEntity;
import com.tellout.entity.TypeEntity;

/**
 * 选择地点，公司，
 * @author sunqm
 *
 */
public class SelectType extends BaseActivity implements OnClickListener{

	private EditText etInput;
	
	private ImageView imgSearch;
	
	private ListView listViewProvice;
	
	private TypeAdapter adapter =null;
	
	private List<TypeEntity> list = new ArrayList<TypeEntity>();
	
	private int requestCode =-1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_type_act);
		initView();
	}

	private void initView() {
		requestCode = getIntent().getIntExtra("flag", -1);
		findViewById(R.id.back).setOnClickListener(this);
		etInput = (EditText) findViewById(R.id.select_type_input);
		imgSearch = (ImageView) findViewById(R.id.select_type_search);
		listViewProvice = (ListView) findViewById(R.id.select_type_listview_province);
		imgSearch.setOnClickListener(this);
		
		adapter = new TypeAdapter(this);
		listViewProvice.setAdapter(adapter);
		listViewProvice.setOnItemClickListener(new OnItemClickListener() {

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
		case R.id.back:
			finish();
			break;
		}
		
	}
	String[] provinces = {"安徽", "重庆", "福建", "甘肃", "广西", "贵州", "海南",
			"河北", "河南", "黑龙江", "湖北", "湖南", "吉林", "江西", "辽宁", "内蒙古", "宁夏", "青海",
			"山东", "山西", "陕西", "四川", "西藏", "新疆", "云南" };
	
	String[] citys = {};
	
	/**
	 * 省列表
	 * @return
	 */
	private List<CityEntity> getProvince(){
		List<CityEntity> citys = new ArrayList<CityEntity>();
		return citys;
	}
	
	/**
	 * 根据省份获得城市列表
	 * @param ProvinceId
	 * @return
	 */
	private List<CityEntity> getCitys(int ProvinceId){
		List<CityEntity> citys = new ArrayList<CityEntity>();
		
		return citys;
	}
	
}
