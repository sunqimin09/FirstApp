package com.tellout.act;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.tellout.constant.DbConstant;
import com.tellout.constant.MConstant;
import com.tellout.entity.BaseEntity;
import com.tellout.entity.RequestEntity;
import com.tellout.util.SpHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 欢迎页面
 * 1.等待两秒跳转到吐槽页面
 * 2.下载下一次需奥显示的图片
 * @author sunqm
 *
 */
public class WelcomeAct extends BaseActivity{
	
	private ImageView img;
	
	private boolean isPaused = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_act);
		initView();
	}

	private void initView() {
		img = (ImageView) findViewById(R.id.welcome_img);
		skip(2000);
	}
	
	private void skip(int time){
		Timer time1 = new Timer();
		time1.schedule(task, time);
	}
	
	
	@Override
	protected void onPause() {
		isPaused = true;
		super.onPause();
	}

	private void autoLogin(){
		SpHelper spHelper = new SpHelper(this);
		if(spHelper.getBool("autoLogin")){
			String email = spHelper.getStr("email");
			String pwd = spHelper.getStr("pwd");
			requestLogin(email,pwd);
		}
	}
	
	
	
	@Override
	public void showResult(int type, BaseEntity baseEntity) {
		super.showResult(type, baseEntity);
		//保存已经登录的信息
		MConstant.USER_ID_VALUE = baseEntity.getMap().get(DbConstant.DB_USER_ID);
	}

	private void requestLogin(String email,String pwd){
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setRequestType(MConstant.REQUEST_CODE_LOGIN_);
		requestEntity.setPost(true);
		Map<String,String> map = new HashMap<String,String>();
		
		map.put(DbConstant.DB_USER_EMAIL, email);
		map.put(DbConstant.DB_USER_PWD, pwd);
		requestEntity.setParams(map);
		request(requestEntity);
	}
	
	
	TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			if(!isPaused){
				startActivity(new Intent(WelcomeAct.this,TellOutAct.class));
				finish();
			}
			
		}
	};
}
