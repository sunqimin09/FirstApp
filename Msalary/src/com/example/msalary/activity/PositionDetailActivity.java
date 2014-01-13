/**
 * 
 */
package com.example.msalary.activity;

import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
   private TextView mistake_tv;
   private ShowResult showResult = null;
   //在0-3000等四个范围内曝光的数量。
   private TextView textView1,textView2,textView3,textView4;
   private TextView userful_tv;
   
   private int[] salarys =new int[4];
   
   private ProgressBar progressBar1,progressBar2,progressBar3,progressBar4;
   
   /**有用的数量*/
   private int userfullCount = 0;
   
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
		super.initView();
		tv_title.setText(getString(R.string.position_detail_title));
//		int companyId = getIntent().getIntExtra("companyId", 0);
//		int jobId = getIntent().getIntExtra("jobId", 0);
		request(0);
		textView1=(TextView) findViewById(R.id.textView1);
		textView2=(TextView) findViewById(R.id.textView2);
		textView3=(TextView) findViewById(R.id.textView3);
		textView4=(TextView) findViewById(R.id.textView4);
		progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
		progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
		progressBar4 = (ProgressBar) findViewById(R.id.progressBar4);
		progressBar1.setMax(10);
		userful_tv=(TextView) findViewById(R.id.userful_tv);
		mistake_tv=(TextView) findViewById(R.id.detail_mistake_tv);
	}
 
	public void onClick(View view){
		switch(view.getId()){
		case R.id.back: 
			finish();
			break;
		case R.id.userful_tv://点击有用
			request(1);
			
			userful_tv.setEnabled(false);
			break;
		case R.id.detail_mistake_tv:
			Intent intent=	new Intent(PositionDetailActivity.this,MistakeActivity.class);
			intent.putExtra("jobName", getIntent().getIntExtra("jobName", 0));
			intent.putExtra("companyName",getIntent().getIntExtra("companyName", 0) );
			startActivity(intent);
			break;
		
		}
	}
	
	/**
	 * 发起网络请求
	 * @param requestStr
	 */
	private void request(int requestCode,String... str){
		RequestEntity requestEntity =null;
		HashMap<String,Object> map = new HashMap<String,Object>();
		switch(requestCode){
		case 0:
			requestEntity = new RequestEntity(this,MConstant.URL_JOB_DETAIL);
			break;
		case 1:
			requestEntity = new RequestEntity(this,MConstant.URL_USERFUL);
			break;
		}
		map.put("companyId", getIntent().getIntExtra("companyId", 0));
		map.put("jobId", getIntent().getIntExtra("jobId", 0));
		requestEntity.params = map;
		new InternetHelper(this).requestThread(requestEntity, this);
	}
	
	@Override
	public void requestSuccess(ResponseResult responseResult) {
		super.requestSuccess(responseResult);
		switch(responseResult.requestCode){
		case 0://获得详细内容
			showResult=JsonPositionDetail.parse(responseResult,this);
			salarys = doResult((List<JobEntity>) showResult.list);
			showResult(salarys);
			userful_tv.setText("有用("+((JobEntity)(showResult.list.get(0))).getUserful_num()+")");
			break;
		case 1://提交赞成功
			Toast("赞+1");
			break;
		}
		
	}
	
	
	/**
	 * 将结果显示
	 * @param salarys
	 */
	private void showResult(int[] salarys){
		int max = 0;
		for(int i = 0;i<salarys.length;i++){
			max +=salarys[i];
		}
		textView1.setText(salarys[0]/max+"");
		textView2.setText(salarys[1]/max+"");
		textView3.setText(salarys[2]/max+"");
		textView4.setText((1-(salarys[0]/max+salarys[1]/max+salarys[2]/max))+"");
		
		progressBar1.setMax(max);
		progressBar2.setMax(max);
		progressBar3.setMax(max);
		progressBar4.setMax(max);
		progressBar1.setProgress((salarys[0]/max)*100);
		progressBar2.setProgress((salarys[1]/max)*100);
		progressBar3.setProgress((salarys[2]/max)*100);
		progressBar4.setProgress((1-(salarys[0]/max+salarys[1]/max+salarys[2]/max))*100);
		
		
	}
	
	/**
	 * 处理结果
	 * @param list
	 */
	private int[] doResult(List<JobEntity> list){
		int[] salarys =new int[4];
		
		for(int i = 0;i<list.size();i++){
			if(list.get(i).getSalary()<3000){
				salarys[0]++;
			}else if(list.get(i).getSalary()<6000){
				salarys[1]++;
			}else if(list.get(i).getSalary()<9000){
				salarys[2]++;
			}else {
				salarys[3]++;
			}
		}
		return salarys;
	}
	
}
