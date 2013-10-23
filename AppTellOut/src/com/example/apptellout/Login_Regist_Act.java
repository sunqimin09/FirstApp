package com.example.apptellout;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.entity.BaseEntity;
import com.example.entity.RequestEntity;
import com.example.util.InternetHelper;
import com.example.util.MConstant;
import com.sun.constant.DbConstant;

public class Login_Regist_Act extends BaseActivity implements OnClickListener,
		OnCheckedChangeListener {

	private EditText edit1, edit2, edit3, edit4;

	private Button leftBtn, rightBtn;

	private CheckBox checkBox;

	private boolean isLogin = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_regist_act);
		initView();
	}

	private void initView() {
		edit1 = (EditText) findViewById(R.id.login_regist_ededitText1);
		edit2 = (EditText) findViewById(R.id.login_regist_ededitText2);
		edit3 = (EditText) findViewById(R.id.login_regist_ededitText3);
		edit4 = (EditText) findViewById(R.id.login_regist_ededitText4);
		leftBtn = (Button) findViewById(R.id.login_regist_left_btn);
		rightBtn = (Button) findViewById(R.id.login_regist_right_btn);
		checkBox = (CheckBox) findViewById(R.id.login_regist_checkBox1);
		checkBox.setOnCheckedChangeListener(this);
		leftBtn.setOnClickListener(this);
		rightBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.login_regist_left_btn:// 左侧
			if (isLogin) {
				edit3.setVisibility(View.VISIBLE);
				edit4.setVisibility(View.VISIBLE);
				edit1.setHint(getString(R.string.nick_name));
				edit2.setHint(getString(R.string.email));
				edit2.setInputType(InputType.TYPE_CLASS_TEXT);
				leftBtn.setText(getString(R.string.login));
				rightBtn.setText(getString(R.string.regist));
			} else {
				edit1.setText("test1@163.com");
				edit2.setText("123456");
				edit1.setHint(getString(R.string.email));
				edit2.setHint(getString(R.string.pwd));
				edit2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
				edit3.setVisibility(View.GONE);
				edit4.setVisibility(View.GONE);
				leftBtn.setText(getString(R.string.regist));
				rightBtn.setText(getString(R.string.login));
			}
			isLogin = !isLogin;

			break;
		case R.id.login_regist_right_btn:// 右侧
			if (!InternetHelper.isInternetAvaliable(this)) {
				Toast("当前无网络");
				return;
			}
			if (isLogin) {// 登录
				if(!checkLogin())//检查登录输入是否正确
					return ;
				startActivity(new Intent(Login_Regist_Act.this, MainActivity.class));
				finish();
				LoginRequest();
			} else {// 注册
				
				if(!checkRegist()){//检查注册输入是否正确
					return;
				}
				RegistRequest();
			}

			break;
		}
	}
	
	
	
	@Override
	public void showResult(int type, BaseEntity baseEntity) {
		super.showResult(type, baseEntity);
//		if(type ==MConstant.REQUEST_CODE_LOGIN_){//
//			
//		}else if(type == MConstant.REQUEST_CODE_REGIST){
//			
//		}
		MConstant.USER_ID_VALUE = baseEntity.getMap().get(DbConstant.DB_USER_ID);
		startActivity(new Intent(Login_Regist_Act.this, MainActivity.class));
		
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	private boolean checkLogin() {
		String email = edit1.getText().toString();
		String pwd = edit2.getText().toString();
		if ("" == email || "" == pwd) {
			Toast("邮箱或者密码不能为空");
			return false;
		}
		if(!EmailFormat(email)){//验证邮箱格式
			Toast("邮箱格式错误");
			return false;
		}
		if(pwd.length()<6 || pwd.length()>15){
			Toast("密码长度应为6~15位");
			return false;
		}
		return true;
	}

	private boolean EmailFormat(String eMAIL1) {// 邮箱判断正则表达式
		Pattern pattern = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher mc = pattern.matcher(eMAIL1);
		return mc.matches();
	}

	private void LoginRequest(){
		String email = edit1.getText().toString();
		String pwd = edit2.getText().toString();
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setRequestType(MConstant.REQUEST_CODE_LOGIN_);
		requestEntity.setPost(true);
		Map<String,String> map = new HashMap<String,String>();
		
		map.put(MConstant.USER_EMAIL, email);
		map.put(MConstant.USER_PWD, pwd);
		requestEntity.setParams(map);
		request(requestEntity);
	}
	
	private void RegistRequest(){
		String nickName = edit1.getText().toString();
		String email = edit2.getText().toString();
		String pwd = edit3.getText().toString();
		
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setRequestType(MConstant.REQUEST_CODE_REGIST);
		requestEntity.setPost(true);
		Map<String,String> map = new HashMap<String,String>();
		map.put(MConstant.USER_NAME, nickName);
		map.put(MConstant.USER_EMAIL, email);
		map.put(MConstant.USER_PWD, pwd);
		
		requestEntity.setParams(map);
		request(requestEntity);
	}
	
	private boolean checkRegist() {
		String nickName = edit1.getText().toString();
		String email = edit2.getText().toString();
		String pwd = edit3.getText().toString();
		String pwd2 = edit4.getText().toString();
		if(nickName==""){
			Toast("昵称不能为空");
			return false;
		}
		if(email == ""){
			Toast("邮箱不能为空");
			return false;
		}
		if(pwd ==""|| pwd2==""){
			Toast("密码不能为空");
			return false;
		}
		if(nickName.length()>20){
			Toast("昵称长度不能超过20");
			return false;
		}
		if(!EmailFormat(email)){
			Toast("邮箱格式不正确");
			return false;
		}
		if(!pwd.equals(pwd2)){
			Toast("两次密码不一致");
			return false;
		}
		if(pwd.length()<6 || pwd.length()>15){
			Toast("密码长度应为6~15位");
			return false;
		}
		return true;
	}

	private void Toast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	
	
//	public void showResult(int type, Object object) {
//		JSONObject jsonObject = (JSONObject) object;
//		Log.d("tag", "Login--->showResult" + jsonObject);
//		try {
//			
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		
//		
//
//	}	

}
