package com.tellout.act;

import org.apache.http.HttpStatus;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.tellout.constant.ErrorCodeConstant;
import com.tellout.constant.MConstant;
import com.tellout.entity.BaseEntity;
import com.tellout.entity.RequestEntity;
import com.tellout.entity.TestResponseResult;
import com.tellout.util.InternetHelper;
import com.tellout.util.JsonParse;
import com.tellout.util.ResultHandler;

public class BaseActivity extends Activity{
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new ResultHandler (this);
	}

	public void request(final RequestEntity requestEntity){
		
		new Thread() {

			@Override
			public void run() {
					if(!InternetHelper.isInternetAvaliable(BaseActivity.this)){
						handler.sendEmptyMessage(ErrorCodeConstant.NO_INTERNET);
						return ;
					}
					//显示正在加载对话框
					handler.sendEmptyMessage(ErrorCodeConstant.REQUEST);
					TestResponseResult entity = InternetHelper.request(
							requestEntity);
					if(entity.getResultCode()==HttpStatus.SC_OK){//成功，解析数据
						BaseEntity baseEntity = JsonParse.JsonParse(requestEntity.getRequestType(), entity.getResultStr());
						Message msg = new Message();
						/**异常码*/
						msg.what =baseEntity.getCode();
						/**请求码*/
						msg.arg1 = requestEntity.getRequestType();
						msg.obj =baseEntity;
						handler.sendMessage(msg);
					}else{
						/**发送错误提示*/
						handler.sendEmptyMessage(entity.getResultCode());
					}
					
			}

		}.start();
	}
	
	public void showResult(int type,BaseEntity baseEntity){
		Log.d("tag","base--showResult-->"+baseEntity.getList());
		
	}
	
//	public void showResult(int type,Object object){
//		Log.d("tag","base--showResult-->"+object);
//	}
	
	public void Toast(String str){
		Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
	}
	
	private ResultHandler handler = null;
	
}
