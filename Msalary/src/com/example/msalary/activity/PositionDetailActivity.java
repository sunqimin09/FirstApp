/**
 * 
 */
package com.example.msalary.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.view.View;

import com.example.msalary.R;
import com.example.msalary.entity.RequestEntity;
import com.example.msalary.entity.ResponseResult;
import com.example.msalary.internet.InternetHelper;
import com.example.msalary.util.MConstant;

/**
 * 作者：@sqm    <br>
 * 创建时间：2013/11/24 <br>
 * 功能描述: 公司名称，岗位名称，平均薪资， <br>
 */
public class PositionDetailActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.position_detail_act);
		initView();
	}
	/**
	 * 
	 */
	public void initView() {
		int companyId = getIntent().getIntExtra("companyId", 0);
		int jobId = getIntent().getIntExtra("jobId", 0);
		request(companyId, jobId);
	}

	/**
	 * 发起网络请求
	 * @param requestStr
	 */
	private void request(int companyId,int jobId){
		RequestEntity requestEntity =new RequestEntity(this,MConstant.URL_JOB_DETAIL);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("companyId", companyId);
		map.put("jobId", jobId);
		requestEntity.params = map;
		new InternetHelper().requestThread(requestEntity, this);
	}
	
	@Override
	public void requestSuccess(ResponseResult responseResult) {
		super.requestSuccess(responseResult);
		
	}
	
	public void onClick(View view){
		
	}
	
}
