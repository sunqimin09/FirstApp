package com.example.apptellout;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.entity.RequestEntity;
import com.example.entity.ResponseEntity;
import com.example.entity.TestRequestEntity;
import com.example.entity.TestResponseResult;
import com.example.util.HttpHelper;
import com.example.util.MConstant;
import com.example.util.TestInternet;

public class BaseActivity extends Activity{
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			String showResult = "获取数据失败";
			switch(msg.arg1){
			case MConstant.SUCCESS://显示正确结果
				showResult(msg.what, msg.obj);
				break;
			case MConstant.FAILED:
				switch(msg.what){
				case MConstant.LOGIN_REQUEST_CODE:
					showResult = "登录失败";
					break;
				case MConstant.REGIST_REQUEST_CODE:
					showResult = "注册失败";
					break;
				}
				break;
			case MConstant.USER_EXIST:
				showResult="用户已经存在";
				break;
			case MConstant.SQL_EXCEPTION:
				showResult="服务器异常";
				break;
			default:
				showResult="服务器异常2";
				
			}
			if(msg.arg1!=MConstant.SUCCESS)
				Toast.makeText(BaseActivity.this, showResult, Toast.LENGTH_SHORT).show();	
		}
		
	};
	
	public Handler getHandler(){
		return handler;
	}


	
	public void request(final RequestEntity requestEntity){
		new Thread() {

			@Override
			public void run() {
//					ResponseEntity entity = HttpHelper.request(
//							requestEntity);
//					Message msg = new Message();
//					msg.what =entity.getRequestCode();
//					msg.arg1 = entity.getCode();
//					msg.obj =entity.getResult();
//					handler.sendMessage(msg);
			}

		}.start();
	}
	
	
	public void Testrequest(final TestRequestEntity requestEntity){
		new Thread() {

			@Override
			public void run() {
					TestResponseResult entity = TestInternet.request(
							requestEntity);
					if(entity.getResultCode()==HttpStatus.SC_OK){//成功，解析数据
						
					}else{
						
					}
					Message msg = new Message();
					msg.what =entity.getResultCode();
					msg.arg1 = requestEntity.getRequestType();
					msg.obj =entity.getResultStr();
					handler.sendMessage(msg);
			}

		}.start();
	}
	
	public void showResult(String str){
		Log.d("tag","base--showResult-->"+str);
	}
	
	public void showResult(int type,Object object){
		Log.d("tag","base--showResult-->"+object);
	}
	
}
