package com.sun.hair.act;

import net.tsz.afinal.http.AjaxParams;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sun.hair.BaseAct;
import com.sun.hair.R;
import com.sun.hair.service.CommentsService;
import com.sun.hair.service.IRequestCallBack;
import com.sun.hair.service.RequestCheckService;
import com.sun.hair.utils.MConstant;

/**
 * ע��
 * @author sunqm
 *
 */
public class RegistAct extends BaseAct implements IRequestCallBack{

	private EditText etName,etPwd,etPwd2;
	
	@Override
	public void initTitle() {
		setContentView(R.layout.act_regist);
		setTitle_("注册");
//		TextView tv = (TextView) findViewById(R.id.act_title_left_tv);
//		tv.setText("ȡ��");
		findViewById(R.id.act_title).setBackgroundResource(R.drawable.bg_top);
	}

	
	@Override
	public void initView() {
		etName = (EditText) findViewById(R.id.act_regist_name);
		etPwd = (EditText) findViewById(R.id.act_regist_pwd);
		etPwd2 = (EditText) findViewById(R.id.act_regist_pwd2);
	}
	
	public void onClick(View view){
		switch(view.getId()){
		case R.id.act_regist_btn://��¼
			if(checkInput()){
				request();
			}
			break;
		}
		
	}
	
	private boolean checkInput(){
		if(etName.getText().toString().equals("")){
			Toast("用户名不能为空");
		}else if(etPwd.getText().toString().equals("")){
			Toast("密码不能为空");
		}else if(etPwd2.getText().toString().equals("")){
			Toast("请再次输入密码");
		}else if(!etPwd.getText().toString().equals(etPwd2.getText().toString())){
			Toast("两次密码不一致");
		}else{
			return true;
		}
		return false;
	}
	
	private  void request(){
		AjaxParams params = new AjaxParams();
		params.put("name",etName.getText().toString());
		params.put("pwd",etPwd.getText().toString());
		new RequestCheckService().request(this, MConstant.URL_REGIST, params, this);
	}


	@Override
	public void onSuccess(Object o) {
		// TODO Auto-generated method stub
		Toast("注册成功");
	}


	@Override
	public void onFailed(String msg) {
		Toast(msg);
		
	}

	
	
}
