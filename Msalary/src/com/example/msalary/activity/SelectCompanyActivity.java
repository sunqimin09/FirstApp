package com.example.msalary.activity;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.msalary.R;
import com.example.msalary.adapter.SelectCompanyAdapter;
import com.example.msalary.entity.RequestEntity;
import com.example.msalary.entity.ResponseResult;
import com.example.msalary.entity.ShowResult;
import com.example.msalary.internet.IRequestCallBack;
import com.example.msalary.internet.InternetHelper;
import com.example.msalary.json.JsonSelectConpany;
import com.example.msalary.util.MConstant;

public class SelectCompanyActivity extends BaseActivity {

	private ListView listView;
	
	private SelectCompanyAdapter adapter = null;
	
	private ShowResult showResult = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.position_search_main);
		initView();
		request(getIntent().getStringExtra("key"));
		
	}

	protected void initView() {
		listView = (ListView) findViewById(R.id.position_list_lv);
		showResult = new  ShowResult();
		adapter = new SelectCompanyAdapter(this, showResult);
	}
	
	/**
	 * ·¢ÆðÍøÂçÇëÇó
	 * @param requestStr
	 */
	private void request(String requestStr){
		getString(R.string.url_home);
		RequestEntity requestEntity =new RequestEntity(this,MConstant.URL_SELECT_COMPANY);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("key", requestStr+"a");
		requestEntity.params = map;
		InternetHelper.requestThread(requestEntity, this);
	}

	@Override
	public void requestSuccess(ResponseResult responseResult) {
		Log.d("tag","showResult"+responseResult);
		ShowResult showResult = JsonSelectConpany.parse(responseResult,this);
		adapter.setData(showResult);
	}

	
}
