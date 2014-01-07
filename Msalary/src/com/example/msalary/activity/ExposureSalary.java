package com.example.msalary.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.msalary.R;
import com.example.msalary.entity.RequestEntity;
import com.example.msalary.entity.ResponseResult;
import com.example.msalary.entity.ShowResult;
import com.example.msalary.internet.InternetHelper;
import com.example.msalary.util.MConstant;

/**
 * 我要曝工资的界面
 * 
 * @author Administrator
 * 
 */
public class ExposureSalary extends BaseActivity {

	private EditText et_CompanyName, et_JobName, et_Salary;

	private ShowResult showResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		// 自定义标题
		setContentView(R.layout.exposure_salary_main);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		tv_title.setText(getString(R.string.exposure_title));
		// 返回键
		et_CompanyName = (EditText) findViewById(R.id.exposure_et_companyName);
		et_JobName = (EditText) findViewById(R.id.exposure_et_jobName);
		et_Salary = (EditText) findViewById(R.id.exposure_et_salary);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.exposure_btn_submit:// 这个在xml中设置的
			String company = et_CompanyName.getText().toString().trim();
			String job = et_JobName.getText().toString().trim();
			String salary = et_Salary.getText().toString().trim();

			if (checkInput(company, job, salary)) {
				request(company, job, salary);
			}
			break;
		case R.id.back://返回
			finish();
			break;
		}
	}

	private boolean checkInput(String company, String job, String salary) {
		if (company.equals("")) {
			Toast("亲，写个公司名呗");
			return false;
		} else if (job.equals("")) {
			Toast("亲，职位也要有个名字啊");
			return false;
		} else if (salary.equals("")) {
			Toast("亲，您是志愿者吗?");
			return false;
		}
		Toast("success>" + company);
		return true;
	}

	private void request(String company, String job, String salary) {
		RequestEntity requestEntity = new RequestEntity(this,
				MConstant.URL_NEW_JOB);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("companyName", company);
		map.put("jobName", job);
		map.put("salary", salary);
		requestEntity.params = map;
		new InternetHelper(this).requestThread(requestEntity, this);
	}

	@Override
	public void requestSuccess(ResponseResult responseResult) {
		Log.d("tag", "showResult" + responseResult);
		// showResult = JsonSelectConpany.parse(responseResult,this);
		Intent i = new Intent(ExposureSalary.this, MainActivity.class);
		startActivity(i);

	}

}
