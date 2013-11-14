package com.tellout.act;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tellout.constant.DbConstant;
import com.tellout.constant.MConstant;
import com.tellout.entity.BaseEntity;
import com.tellout.entity.RequestEntity;
import com.tellout.entity.UserEntity;

public class EditMyInforAct extends BaseActivity implements OnClickListener, OnRatingBarChangeListener {

	private EditText etNickName, etSalary;
//	, etEnvironment, etFuture, etOther,
//			etSalaryPer, etEnvironmentPer,etFuturePer,etOtherPer;
	
	private RatingBar ratingBar = null;
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_myinfor_act);
		setTitle("我的信息");
		setTitleColor(Color.WHITE);
		initView();
		Log.d("tag","userid-edit-》"+MConstant.USER_ID_VALUE);
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
		ratingBar = (RatingBar) findViewById(R.id.ratingBar_other);
//		etEnvironment= (EditText) findViewById(R.id.edit_myinfor_environment);
//		etFuture= (EditText) findViewById(R.id.edit_myinfor_future);
//		etOther= (EditText) findViewById(R.id.edit_myinfor_other);
//		etSalaryPer= (EditText) findViewById(R.id.edit_myinfor_salary_percentage);
//		etEnvironmentPer= (EditText) findViewById(R.id.edit_myinfor_environment_percentage);
//		etFuturePer= (EditText) findViewById(R.id.edit_myinfor_future_percentage);
//		etOtherPer= (EditText) findViewById(R.id.edit_myinfor_other_percentage);
		btnCancel = (Button) findViewById(R.id.edit_myinfor_cancel);
		btnSave = (Button) findViewById(R.id.edit_myinfor_save);
		findViewById(R.id.back).setOnClickListener(this);
		rlRegion.setOnClickListener(this);
		rlIndustry.setOnClickListener(this);
		rlCompany.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		ratingBar.setOnRatingBarChangeListener(this);
		setEnable(edit);
		//请求网络数据
		requestGet();
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.back:
			finish();
			break;
		case R.id.edit_myinfor_region_rl://选择地区
			startActivityForResult(new Intent(EditMyInforAct.this,SelectType.class).putExtra("flag", MConstant.REQUEST_CODE_REGIONS), 0);
			break;
		case R.id.edit_myinfor_industry_rl://选择行业
			startActivityForResult(new Intent(EditMyInforAct.this,IndustryAct.class).putExtra("flag", MConstant.REQUEST_CODE_INDUSTRYS), 1);
			break;
		case R.id.edit_myinfor_company_rl://选择公司
			startActivityForResult(new Intent(EditMyInforAct.this,SelectType.class).putExtra("flag", MConstant.REQUEST_CODE_COMPANYS), 2);
			break;
		case R.id.edit_myinfor_cancel:
			finish();
			break;
		case R.id.edit_myinfor_save:
			if(edit){
				requestSend();
				setEnable(false);
//				finish();
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
		if(type == MConstant.REQUEST_CODE_EDIT_SELF_INFOR){//是否保存成功
			
		}else if(type == MConstant.REQUEST_CODE_GET_SELF_INFOR){//返回所有个人信息
			if(baseEntity instanceof UserEntity){
				UserEntity userEntity = (UserEntity) baseEntity;
				etNickName.setText(userEntity.getName());
				tvCompany.setText(userEntity.getCompany_name());
				tvIndustry.setText(userEntity.getIndustry_name());
				etSalary.setText(userEntity.getSalary()+"");
//				etSalaryPer.setText(userEntity.getSalaryPer()+"");
//				etEnvironment.setText(userEntity.getWelfare()+"");
//				etEnvironmentPer.setText(userEntity.getWelfarePer()+"");
			}else{
				Toast("数据错误");
			}
			
		}
	}

	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == -101){
			switch(requestCode){
			case 0://地区
				tvRegion.setText("地区:"+data.getStringExtra("name"));
				break;
			case 1://行业
				tvIndustry.setText("行业:"+data.getStringExtra("industryName")+data.getStringExtra("detail"));
				break;
			case 2://公司
				tvCompany.setText("公司:"+data.getStringExtra("name"));
				break;
			}
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void setEnable(boolean flag){
		etNickName.setEnabled(flag); 
		etSalary.setEnabled(flag); 
//		etEnvironment.setEnabled(flag); 
//		etFuture.setEnabled(flag); 
//		etOther.setEnabled(flag);
//		etSalaryPer.setEnabled(flag);
//		etEnvironmentPer.setEnabled(flag);
//		etFuturePer.setEnabled(flag);
//		etOtherPer.setEnabled(flag);
		btnSave.setText(flag?"保存":"编辑");
	}
	/**
	 * 请求获得
	 */
	private void requestGet(){
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setPost(false);
		Map<String,String> map = new HashMap<String,String>();
		map.put(DbConstant.DB_USER_ID, MConstant.USER_ID_VALUE);
//		requestEntity.setUrl(MConstant.URL_MYINFOR);
		requestEntity.setParams(map);
		requestEntity.setRequestType(MConstant.REQUEST_CODE_GET_SELF_INFOR);
		request(requestEntity);
	}
	
	/**
	 * 发起网络请求，提交数据
	 */
	private void requestSend(){
		String nickName = etNickName.getText().toString();
		String salary = etSalary.getText().toString();
//		String salaryPer = etSalaryPer.getText().toString();
//		String environment = etEnvironment.getText().toString();
//		String environmentPer = etEnvironmentPer.getText().toString();
//		String future= etFuture.getText().toString();
//		String futurePer = etFuturePer.getText().toString();
//		String other = etOther.getText().toString();
//		String otherPer = etOtherPer.getText().toString();
		
		if(null==nickName||null==salary){
			Toast.makeText(this,"输入不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if(nickName.length()>20){
			Toast.makeText(this,"昵称长度不能超过20", Toast.LENGTH_SHORT).show();
			return;
		}
			
		
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setRequestType(MConstant.REQUEST_CODE_EDIT_SELF_INFOR);
		requestEntity.setPost(false);
		Map<String,String> map = new HashMap<String,String>();
		map.put(DbConstant.DB_USER_ID, MConstant.USER_ID_VALUE);
		map.put(DbConstant.DB_USER_NICK_NAME, nickName);
		map.put(DbConstant.DB_USER_REGION_ID, 0+"");
		map.put(DbConstant.DB_USER_INDUSTRY_ID, 0+"");
		
		map.put(DbConstant.DB_USER_SALARY, salary);
//		map.put(DbConstant.DB_USER_SALARY_PER, salaryPer);
//		map.put(DbConstant.DB_USER_WELFARE, environment);
//		map.put(DbConstant.DB_USER_WELFARE_PER, environmentPer);
//		map.put(DbConstant.FUTURE, future);
//		map.put(DbConstant.FUTURE_PER, futurePer);
//		map.put(DbConstant.OTHER, other);
//		map.put(DbConstant.OTHER_PER, otherPer);
		
		requestEntity.setParams(map);
		request(requestEntity);
	}

	@Override
	public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
		Log.d("tag","Rating__change->"+arg1+"]"+arg2);
		
	}
	
	
	
}
