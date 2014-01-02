package com.example.msalary.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.msalary.R;

import com.example.msalary.adapter.PositionAllCompanyAdapter;
import com.example.msalary.entity.CompanyEntity;
import com.example.msalary.entity.JobEntity;
import com.example.msalary.entity.RequestEntity;
import com.example.msalary.entity.ResponseResult;
import com.example.msalary.entity.ShowResult;
import com.example.msalary.internet.InternetHelper;
import com.example.msalary.json.JsonCompanysOfJob;
import com.example.msalary.json.JsonSelectPosition;
import com.example.msalary.util.MConstant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 某职位在所有公司的信息，包括公司名，曝光数量，平均工资。
 * @author Administrator
 *
 */
public class PositionAllCompanyActivity extends BaseActivity implements OnItemClickListener{
	private ImageButton back_btn;
	private ListView allCompanyList;
	private ShowResult showResult = null;
	PositionAllCompanyAdapter adapter;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	//设置标题，自定义标题
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    	setContentView(R.layout.position_allcompany_salary_main);
    	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.position_salary_title);
    	back_btn=(ImageButton) findViewById(R.id.position_salary_back);
    	back_btn.setBackgroundResource(R.drawable.cloud_back_click);
    	
    	initView();
    }
     protected void initView(){
    	 allCompanyList=(ListView) findViewById(R.id.allcompany_list);
    	 showResult = new  ShowResult();
    	adapter=new PositionAllCompanyAdapter(this,showResult);
    	 allCompanyList.setAdapter(adapter);
    	 allCompanyList.setOnItemClickListener(this);
    	 request(getIntent().getIntExtra("positionId", 0));
     }

      /**
  	 * 发起网络请求
  	 * @param requestStr
  	 */
  	private void request(int requestStr){
  		getString(R.string.url_home);
  		RequestEntity requestEntity =new RequestEntity(this,MConstant.URL_COMPANYS_OF_JOB);
  		HashMap<String,Object> map = new HashMap<String,Object>();
  		map.put("key", requestStr);
  		requestEntity.params = map;
  		new InternetHelper().requestThread(requestEntity, this);
  	}

  	@Override
  	public void requestSuccess(ResponseResult responseResult) {
  		Log.d("tag","showResult"+responseResult);
  		showResult = JsonCompanysOfJob.parse(responseResult,this);
  		
  		 adapter.setData(showResult);
  	}
  	
     /**
      * 点击事件。
      */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(PositionAllCompanyActivity.this,PositionDetailActivity.class);
		intent.putExtra("companyId", ((CompanyEntity)showResult.list.get((int)arg3)).getId());
		intent.putExtra("jobId", getIntent().getIntExtra("positionId", 0));
		intent.putExtra("jobName", getIntent().getIntExtra("positionName", 0));
		intent.putExtra("companyName", ((CompanyEntity)showResult.list.get((int)arg3)).getName());
	}
    
}
