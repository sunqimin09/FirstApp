/**
 * 
 */
package com.example.msalary.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

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
   private ImageButton back_btn;
   private TextView mistake_tv;
   private ShowResult showResult = null;
   //在0-3000等四个范围内曝光的数量。
   private TextView textView1,textView2,textView3,textView4;
   private TextView userful_tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.position_detail_act);
    	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.salary_message_title);
    	back_btn=(ImageButton) findViewById(R.id.salary_message_back);
    	back_btn.setBackgroundResource(R.drawable.cloud_back_click);
		
		initView();
	}
	/**
	 * 
	 */
	public void initView() {
		int companyId = getIntent().getIntExtra("companyId", 0);
		int jobId = getIntent().getIntExtra("jobId", 0);
		request(companyId, jobId);
		textView1=(TextView) findViewById(R.id.textView1);
		textView2=(TextView) findViewById(R.id.textView2);
		textView3=(TextView) findViewById(R.id.textView3);
		textView4=(TextView) findViewById(R.id.textView4);
		userful_tv=(TextView) findViewById(R.id.userful_tv);
		mistake_tv=(TextView) findViewById(R.id.detail_mistake_tv);
		mistake_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent=	new Intent(PositionDetailActivity.this,MistakeActivity.class);
			intent.putExtra("jobName", getIntent().getIntExtra("jobName", 0));
			intent.putExtra("companyName",getIntent().getIntExtra("companyName", 0) );
			startActivity(intent);
			}
		});
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
		showResult=JsonPositionDetail.parse(responseResult,this);
		textView1.setText(((JobEntity)(showResult.list.get(0))).getCount1()+"");
		textView2.setText(((JobEntity)(showResult.list.get(0))).getCount2()+"");
		textView3.setText(((JobEntity)(showResult.list.get(0))).getCount3()+"");
		textView4.setText(((JobEntity)(showResult.list.get(0))).getCount4()+"");
		userful_tv.setText("有用("+((JobEntity)(showResult.list.get(0))).getUserful_num()+")");
	}
	
	public void onClick(View view){
		
	}
	
}
