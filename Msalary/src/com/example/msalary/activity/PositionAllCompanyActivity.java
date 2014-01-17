package com.example.msalary.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.msalary.R;
import com.example.msalary.adapter.PositionAllCompanyAdapter;
import com.example.msalary.entity.CompanyEntity;
import com.example.msalary.entity.RequestEntity;
import com.example.msalary.entity.ResponseResult;
import com.example.msalary.entity.ShowResult;
import com.example.msalary.internet.InternetHelper;
import com.example.msalary.json.JsonCompanysOfJob;
import com.example.msalary.util.MConstant;

/**
 * 某职位在所有公司的信息，包括公司名，曝光数量，平均工资。
 * 
 * @author Administrator
 * 
 */
public class PositionAllCompanyActivity extends BaseActivity implements
		OnItemClickListener {
	private ListView allCompanyList;
	private ShowResult showResult = null;
	private PositionAllCompanyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 设置标题，自定义标题
		setContentView(R.layout.position_allcompany_salary_main);

		initView();
	}

	protected void initView() {
		super.initView();
		tv_title.setText(getString(R.string.position_all_company_title));
		allCompanyList = (ListView) findViewById(R.id.allcompany_list);
		TextView tvName = (TextView) findViewById(R.id.some_position_tv);
		tvName.setText(getIntent().getStringExtra("positionName"));
		showResult = new ShowResult();
		adapter = new PositionAllCompanyAdapter(this, showResult);
		allCompanyList.setAdapter(adapter);
		allCompanyList.setOnItemClickListener(this);
		request(getIntent().getIntExtra("positionId", 0));
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		}
	}

	/**
	 * 发起网络请求
	 * 
	 * @param requestStr
	 */
	private void request(int requestStr) {
		RequestEntity requestEntity = new RequestEntity(this,
				MConstant.URL_COMPANYS_OF_JOB);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("jobId", requestStr);
		requestEntity.params = map;
		new InternetHelper(this).requestThread(requestEntity, this);
	}

	@Override
	public void requestSuccess(ResponseResult responseResult) {
		Log.d("tag", "showResult" + responseResult);
		showResult = JsonCompanysOfJob.parse(responseResult, this);

		adapter.setData(showResult);
	}

	/**
	 * 点击事件。
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PositionAllCompanyActivity.this,
				PositionDetailActivity.class);
		intent.putExtra("companyId",
				((CompanyEntity) showResult.list.get((int) arg3)).getId());
		intent.putExtra("jobId", getIntent().getIntExtra("positionId", 0));
		 intent.putExtra("jobName", getIntent().getStringExtra("positionName"));
		 intent.putExtra("companyName",
		 ((CompanyEntity)showResult.list.get((int)arg3)).getName());
		
		 Log.d("tag","companyname"+ ((CompanyEntity)showResult.list.get((int)arg3)).getName());
		 
		startActivity(intent);
	}

}
