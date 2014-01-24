/**
 * 
 */
package com.sun.apphair;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 项目名称：Hair
 * 文件名：LoginAct.java  
 * 作者：@sunqm    
 * 创建时间：2014-1-24 上午10:41:34
 * 功能描述:  
 * 版本 V 1.0               
 */
public class LoginAct extends BaseAct{

	private EditText et_name;
	
	private EditText et_pwd;
	
	private EditText et_pwd_again;
	
	private Button btn_login_or_regist;
	
	private TextView tv_regist;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_act);
		initView();
	}

	/**
	 * 
	 */
	private void initView() {
		et_name = (EditText) findViewById(R.id.login_name);
		et_pwd = (EditText) findViewById(R.id.login_pwd);
		et_pwd_again = (EditText) findViewById(R.id.login_pwd_again);
		btn_login_or_regist = (Button) findViewById(R.id.login_login_or_regist);
		tv_regist = (TextView) findViewById(R.id.login_regist);
				
		
	}
	
	public void OnClick(View view){
		switch(view.getId()){
		case R.id.login_login_or_regist:
			if(tv_regist.getText().equals(getString(R.string.login))){//当前注册页面
				
			}else{//当前登录页面
				
			}
			break;
		case R.id.login_regist:
			if(tv_regist.getText().equals(getString(R.string.login))){
				tv_regist.setText(getString(R.string.regist));
				btn_login_or_regist.setText(getString(R.string.login));
				et_pwd_again.setVisibility(View.GONE);
			}else{
				tv_regist.setText(getString(R.string.login));
				btn_login_or_regist.setText(getString(R.string.regist));
				et_pwd_again.setVisibility(View.VISIBLE);
			}
			
			break;
		}
	}
	
}
