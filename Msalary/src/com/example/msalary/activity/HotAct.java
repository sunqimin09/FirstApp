package com.example.msalary.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.msalary.R;
import com.example.msalary.adapter.HotCompanyAdapter;
import com.example.msalary.entity.CompanyEntity;
import com.example.msalary.entity.RequestEntity;
import com.example.msalary.entity.ResponseResult;
import com.example.msalary.entity.ShowResult;
import com.example.msalary.internet.InternetHelper;
import com.example.msalary.json.JsonSalaryRank;
import com.example.msalary.util.MConstant;

public class HotAct extends BaseActivity implements OnItemClickListener{

	private ListView listView;
	
	private HotCompanyAdapter adapter = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hot_act);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		tv_title.setText(getString(R.string.hot_title));
		listView = (ListView) findViewById(R.id.hot_act_listview);
		adapter = new HotCompanyAdapter(this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		request();
	}

	public void onClick(View view){
		switch(view.getId()){
		case R.id.back:
			finish();
			break;
		}
	}
	
	/**
	 * ·¢ÆðÍøÂçÇëÇó
	 * @param requestStr
	 */
	private void request(){
		RequestEntity requestEntity =new RequestEntity(this,MConstant.URL_COMPANY_SALARY_RANK);
		new InternetHelper(this).requestThread(requestEntity, this);
	}
	
	@Override
	public void requestSuccess(ResponseResult responseResult) {
		super.requestSuccess(responseResult);
		ShowResult showResult = JsonSalaryRank.parse(responseResult,this);
		adapter.setData((List<CompanyEntity>) showResult.list);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent i = new Intent(HotAct.this,CompanyAllPositionActivity.class);
		i.putExtra("companyId", ((CompanyEntity)adapter.getItem(arg2)).getId());
		startActivity(i);
	}
	
}
