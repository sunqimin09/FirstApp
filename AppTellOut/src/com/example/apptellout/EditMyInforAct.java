package com.example.apptellout;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.RequestEntity;
import com.example.util.MConstant;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditMyInforAct extends BaseActivity implements OnClickListener {

	private EditText etNickName, etSalary, etEnvironment, etFuture, etOther,
			etSalaryPer, etEnvironmentPer,etFuturePer,etOtherPer;
	
	private Button btnCancel,btnSave;
	
	private ImageView imgIcon;

	/**编辑*/
	private boolean edit = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_myinfor_act);
		initView();
	}

	private void initView() {
		etNickName = (EditText) findViewById(R.id.edit_myinfor_nickname);
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
		btnCancel.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		setEnable(edit);
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.edit_myinfor_cancel:
			finish();
			break;
		case R.id.edit_myinfor_save:
			if(edit){
				request();
				setEnable(false);
				finish();
			}
			else
				setEnable(!edit);
			edit =!edit;
			break;
		}
	}
	
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
	
	private void request(){
		String nickName = etNickName.getText().toString();
		String salary = etSalary.getText().toString();
		String salaryPer = etSalaryPer.getText().toString();
		String environment = etEnvironment.getText().toString();
		String environmentPer = etEnvironmentPer.getText().toString();
		String future= etFuture.getText().toString();
		String futurePer = etFuturePer.getText().toString();
		String other = etOther.getText().toString();
		String otherPer = etOtherPer.getText().toString();
		
		if(null==nickName||null==salary||null == salaryPer||
				environment==null||environmentPer==null||future==null||
				futurePer==null||other==null||otherPer==null){
			Toast.makeText(this,"输入不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if(nickName.length()>20){
			Toast.makeText(this,"昵称长度不能超过20", Toast.LENGTH_SHORT).show();
			return;
		}
			
		
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setType(MConstant.EDIT_SELFINFOR_REQUEST_CODE);
		requestEntity.setHasParams(true);
		Map<String,String> map = new HashMap<String,String>();
		map.put(MConstant.USER_ID, MConstant.USER_ID_VALUE);
		map.put(MConstant.USER_NAME, nickName);
		map.put(MConstant.SALARY, salary);
		map.put(MConstant.SALARY_PER, salaryPer);
		map.put(MConstant.ENVIRONMENT, environment);
		map.put(MConstant.ENVIRONMENT_PER, environmentPer);
		map.put(MConstant.FUTURE, future);
		map.put(MConstant.FUTURE_PER, futurePer);
		map.put(MConstant.OTHER, other);
		map.put(MConstant.OTHER_PER, otherPer);
		
		requestEntity.setParams(map);
		request(requestEntity);
	}
	
}
