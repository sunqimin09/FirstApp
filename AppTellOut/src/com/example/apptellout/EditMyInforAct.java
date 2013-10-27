package com.example.apptellout;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.BaseEntity;
import com.example.entity.RequestEntity;
import com.example.entity.UserEntity;
import com.example.util.MConstant;
import com.sun.constant.DbConstant;

public class EditMyInforAct extends BaseActivity implements OnClickListener {

	private EditText etNickName, etSalary, etEnvironment, etFuture, etOther,
			etSalaryPer, etEnvironmentPer,etFuturePer,etOtherPer;
	/**地区，行业，公司*/
	private RelativeLayout rlRegion =null;
	
	private RelativeLayout rlIndustry = null;
	
	private RelativeLayout rlCompany = null;
	
	private TextView tvRegion = null;
	
	private TextView tvIndustry = null;
	
	private TextView tvCompany = null;
	
	
	private Button btnCancel,btnSave;
	
	private ImageView imgIcon;

	/**编辑*/
	private boolean edit = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_myinfor_act);
		setTitle("我的信息");
		setTitleColor(Color.WHITE);
		initView();
	}

	private void initView() {
		etNickName = (EditText) findViewById(R.id.edit_myinfor_nickname);
		/**地区，行业，公司*/
		rlRegion = (RelativeLayout) findViewById(R.id.edit_myinfor_region_rl);
		rlIndustry = (RelativeLayout) findViewById(R.id.edit_myinfor_industry_rl);
		rlCompany = (RelativeLayout) findViewById(R.id.edit_myinfor_company_rl);
		tvRegion =(TextView) findViewById(R.id.edit_myinfor_region_tv);
		tvIndustry =(TextView) findViewById(R.id.edit_myinfor_industry_tv);
		tvCompany =(TextView) findViewById(R.id.edit_myinfor_company_tv);
		
		
		etSalary= (EditText) findViewById(R.id.edit_myinfor_salary);
		etEnvironment= (EditText) findViewById(R.id.edit_myinfor_environment);
		etFuture= (EditText) findViewById(R.id.edit_myinfor_future);
		etOther= (EditText) findViewById(R.id.edit_myinfor_other);
		etSalaryPer= (EditText) findViewById(R.id.edit_myinfor_salary_percentage);
		etEnvironmentPer= (EditText) findViewById(R.id.edit_myinfor_environment_percentage);
		etFuturePer= (EditText) findViewById(R.id.edit_myinfor_future_percentage);
		etOtherPer= (EditText) findViewById(R.id.edit_myinfor_other_percentage);
		btnCancel = (Button) findViewById(R.id.edit_myinfor_cancel);
		btnSave = (Button) findViewById(R.id.edit_myinfor_save);
		rlRegion.setOnClickListener(this);
		rlIndustry.setOnClickListener(this);
		rlCompany.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		setEnable(edit);
		//请求网络数据
		requestGet();
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.edit_myinfor_region_rl://选择地区
			startActivityForResult(new Intent(EditMyInforAct.this,SelectType.class).putExtra("requestCode", "0"), 0);
			break;
		case R.id.edit_myinfor_industry_rl://选择行业
			startActivityForResult(new Intent(EditMyInforAct.this,SelectType.class).putExtra("requestCode", "1"), 1);
			break;
		case R.id.edit_myinfor_company_rl://选择公司
			startActivityForResult(new Intent(EditMyInforAct.this,SelectType.class).putExtra("requestCode", "2"), 2);
			break;
		case R.id.edit_myinfor_cancel:
			finish();
			break;
		case R.id.edit_myinfor_save:
			if(edit){
				requestSend();
				setEnable(false);
				finish();
			}
			else
				setEnable(!edit);
			edit =!edit;
			break;
		}
	}
	
	
	
	
	@Override
	public void showResult(int type, BaseEntity baseEntity) {
		super.showResult(type, baseEntity);
		if(type == MConstant.REQUEST_CODE_EDIT_SELFINFOR){//是否成功
			Toast.makeText(EditMyInforAct.this, "修改成功", Toast.LENGTH_SHORT).show();
			Log.d("tag","result-->"+baseEntity.getResultObject());
		}else if(type == MConstant.REQUEST_CODE_MYINFOR){//返回所有个人信息
			UserEntity userEntity = (UserEntity) baseEntity;
			etNickName.setText(userEntity.getName());
			tvRegion.setText("地区:"+userEntity.getRegion_name());
			tvCompany.setText("公司:"+userEntity.getCompany_name());
			tvIndustry.setText("行业:"+userEntity.getIndustry_name());
			etSalary.setText(userEntity.getSalary());
			etSalaryPer.setText(userEntity.getSalaryPer());
			etFuture.setText(userEntity.getWelfare());
			etFuturePer.setText(userEntity.getWelfarePer());
		}
	}

	/**
	 * 设置是否可以编辑
	 * @param flag
	 */
	private void setEnable(boolean flag){
		etNickName.setEnabled(flag); 
		etSalary.setEnabled(flag); 
		etEnvironment.setEnabled(flag); 
		etFuture.setEnabled(flag); 
		etOther.setEnabled(flag);
		etSalaryPer.setEnabled(flag);
		etEnvironmentPer.setEnabled(flag);
		etFuturePer.setEnabled(flag);
		etOtherPer.setEnabled(flag);
		btnSave.setText(flag?"保存":"编辑");
	}
	/**
	 * 请求获得个人信息
	 * 进入页面时调用
	 */
	private void requestGet(){
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setPost(false);
		Map<String,String> map = new HashMap<String,String>();
		Log.d("tag","useId-->"+MConstant.USER_ID_VALUE);
		map.put(DbConstant.DB_USER_ID, MConstant.USER_ID_VALUE);
		requestEntity.setUrl(MConstant.URL_GET_MY_INFOR);
		requestEntity.setRequestType(MConstant.REQUEST_CODE_MYINFOR);
		requestEntity.setParams(map);
		request(requestEntity);
	}
	
	/**
	 * 发起网络请求，提交数据
	 */
	private void requestSend(){
		String nickName = etNickName.getText().toString();
		String salary = etSalary.getText().toString();
		String salaryPer = etSalaryPer.getText().toString();
		String environment = etEnvironment.getText().toString();
		String environmentPer = etEnvironmentPer.getText().toString();
		String future= etFuture.getText().toString();
		String futurePer = etFuturePer.getText().toString();
		String other = etOther.getText().toString();
		String otherPer = etOtherPer.getText().toString();
//		
//		if(null==nickName||null==salary||null == salaryPer||
//				environment==null||environmentPer==null||future==null||
//				futurePer==null||other==null||otherPer==null){
//			Toast.makeText(this,"输入不能为空", Toast.LENGTH_SHORT).show();
//			return;
//		}
		if(nickName.length()>20){
			Toast.makeText(this,"昵称长度不能超过20", Toast.LENGTH_SHORT).show();
			return;
		}
			
		
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setRequestType(MConstant.REQUEST_CODE_EDIT_SELFINFOR);
		requestEntity.setPost(false);
		Map<String,String> map = new HashMap<String,String>();
		map.put(DbConstant.DB_USER_ID, MConstant.USER_ID_VALUE);
		map.put(DbConstant.DB_USER_NICK_NAME, nickName);
		map.put(DbConstant.DB_USER_SALARY, salary);
		map.put(DbConstant.DB_USER_SALARY_PER, salaryPer);
		map.put(DbConstant.DB_USER_WELFARE, future);
		map.put(DbConstant.DB_USER_WELFARE_PER, futurePer);
//		map.put(DbConstant.FUTURE, future);
//		map.put(DbConstant.FUTURE_PER, futurePer);
//		map.put(DbConstant.OTHER, other);
//		map.put(DbConstant.OTHER_PER, otherPer);
		requestEntity.setUrl(MConstant.URL_EDIT_MY_INFOR);
		requestEntity.setPost(false);
		requestEntity.setRequestType(MConstant.REQUEST_CODE_EDIT_SELFINFOR);
		requestEntity.setParams(map);
		request(requestEntity);
	}
	
	
	
}
