/**
 * 
 */
package com.example.msalary.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.view.View;

import com.example.msalary.R;
import com.example.msalary.entity.JobEntity;
import com.example.msalary.entity.RequestEntity;
import com.example.msalary.entity.ResponseResult;
import com.example.msalary.entity.ShowResult;
import com.example.msalary.internet.InternetHelper;
import com.example.msalary.json.JsonPositionDetail;
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
		ShowResult showResult= JsonPositionDetail.parse(responseResult, this);
		
	}
	
	private void doReusult(ShowResult showResult){
		int count = showResult.list.size();
		int[] d = new int[6];
		int salary = 0;
		for(int i =0;i<count;i++){
			salary = ((JobEntity)showResult.list.get(i)).getSalary();
			if(salary<3000){
				d[0] ++;
			}else if(salary<6000){
				d[1] ++;
			}else if(salary<9000){
				d[2] ++;
			}else if(salary<15000){
				d[3] ++;
			}else if(salary<30000){
				d[4] ++;
			}else{
				d[5] ++;
			}
		}
		int sum = 0;
		for(int j = 0;j<5;j++){
			d[j] =(int) (d[j]/count*100);
			sum =sum+d[j];
		}
		d[5] = count -sum;
		
	}
	
	public void onClick(View view){
		
	}
	
}
