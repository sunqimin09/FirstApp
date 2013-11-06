package com.example.apptellout;

import org.apache.http.HttpStatus;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.entity.BaseEntity;
import com.example.entity.RequestEntity;
import com.example.entity.TestResponseResult;
import com.example.util.InternetHelper;
import com.example.util.MConstant;
import com.example.util.ResultHandler;
import com.example.util.JsonParse;

public class BaseActivity extends Activity{
	
	
	public void request(final RequestEntity requestEntity){
		
		new Thread() {

			@Override
			public void run() {
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
		Log.d("tag","base--showResult-->"+baseEntity);
		
	}
	
//	public void showResult(int type,Object object){
//		Log.d("tag","base--showResult-->"+object);
//	}
	
	private ResultHandler handler = new ResultHandler (this);
	
}
