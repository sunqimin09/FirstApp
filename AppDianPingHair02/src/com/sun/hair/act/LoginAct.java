package com.sun.hair.act;

import java.io.UnsupportedEncodingException;

import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.sun.hair.BaseAct;
import com.sun.hair.R;
import com.sun.hair.service.CommentsService;
import com.sun.hair.service.IRequestCallBack;
import com.sun.hair.utils.MConstant;

/**
 * 锟斤拷录页锟斤拷
 * @author sunqm
 *
 */
public class LoginAct extends BaseAct implements IRequestCallBack{

	private EditText etName,etPwd;
	
	@Override
	public void initTitle() {
		setContentView(R.layout.act_login);
		setTitle_("登录");
		findViewById(R.id.act_title).setBackgroundResource(R.drawable.bg_top);
	}

	
	@Override
	public void initView() {
		etName = (EditText) findViewById(R.id.act_login_name);
		etPwd = (EditText) findViewById(R.id.act_login_pwd);
	}
	
	public void onClick(View view) throws UnsupportedEncodingException{
		switch(view.getId()){
		case R.id.act_login_login_btn://锟斤拷录
			if(checkInput()){
				request();
			}
			break;
		case R.id.act_login_regist_btn://锟斤拷转注锟斤拷页锟斤拷
			startActivity(new Intent(LoginAct.this,RegistAct.class));
			break;
		}
	}
	
	private boolean checkInput(){
		if(etName.getText().toString().equals("")){
			Toast("用户名不能为空");
		}else if(etPwd.getText().toString().equals("")){
			Toast("密码不能为空");
		}else{
			return true;
		}
		return false;
	}
	
	private  void request() throws UnsupportedEncodingException{
		AjaxParams params = new AjaxParams();
		
		params.put("name",etName.getText().toString());
		params.put("pwd",etPwd.getText().toString());
		new CommentsService().request(this, MConstant.URL_LOGIN, params, this);
	}


	@Override
	public void onSuccess(Object o) {
		
	}


	@Override
	public void onFailed(String msg) {
		Toast(msg);
	}

	
}
