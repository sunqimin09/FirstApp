package com.sun.hair.act;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.sun.hair.BaseAct;
import com.sun.hair.R;

/**
 * µÇÂ¼Ò³Ãæ
 * @author sunqm
 *
 */
public class LoginAct extends BaseAct{

	private EditText etName,etPwd;
	
	@Override
	public void initTitle() {
		setContentView(R.layout.act_login);
		setTitle_("µÇÂ¼");
	}

	
	@Override
	public void initView() {
		etName = (EditText) findViewById(R.id.act_login_name);
		etPwd = (EditText) findViewById(R.id.act_login_pwd);
	}
	
	public void onClick(View view){
		switch(view.getId()){
		case R.id.act_login_login_btn://µÇÂ¼
			if(checkInput()){
				request();
			}
			break;
		case R.id.act_login_regist_btn://Ìø×ª×¢²áÒ³Ãæ
			startActivity(new Intent(LoginAct.this,RegistAct.class));
			break;
		}
	}
	
	private boolean checkInput(){
		if(etName.getText().toString().equals("")){
			Toast("ÇëÊäÈëÓÃ»§Ãû");
		}else if(etPwd.getText().toString().equals("")){
			Toast("ÇëÊäÈëÃÜÂë");
		}else{
			return true;
		}
		return false;
	}
	
	private  void request(){
		
	}

	
}
