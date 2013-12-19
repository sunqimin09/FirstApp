package com.tellout.util;

import org.apache.http.HttpStatus;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.tellout.act.BaseActivity;
import com.tellout.constant.ErrorCodeConstant;
import com.tellout.entity.BaseEntity;
import com.tellout.entity.RequestEntity;
import com.tellout.entity.TestResponseResult;
import com.tellout.interface_.RequestCallBack;

public class RequestUtils {
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			String temp = null;
				switch(msg.what){
				case ErrorCodeConstant.REQUEST://发出请求，显示正在加载对话框
//					showProgressingDialog();
					return ;
				case ErrorCodeConstant.NO_INTERNET:
					temp = "当前无网络连接";
					break;
				case ErrorCodeConstant.NAME_EXIT:
					temp = "昵称已经存在";
					break;
				case ErrorCodeConstant.EMAIL_EXIT:
					temp = "邮箱已被注册";
					break;
				case ErrorCodeConstant.NAME_PWD_ERROR:
					temp = "用户名或密码错误";
					break;
				case ErrorCodeConstant.SUCCESS://数据正确
					requestCallBack.showResult((BaseEntity) msg.obj);
//					activity.showResult(msg.arg1,(BaseEntity) msg.obj);
//					closeDialog();
					return ;
				case ErrorCodeConstant.TIME_OUT:
					temp = "请求超时，请检查网络";
					break;
				case ErrorCodeConstant.JSON_EXCEPTION:
					temp = "数据已经损坏";
					break;
				case ErrorCodeConstant.OTHER:
					temp = "未知异常";
					break;
				case ErrorCodeConstant.SQL_EXCEPTION:
					temp = "数据库异常";
					break;
//					closeDialog();
				}
					Toast.makeText(context, temp, Toast.LENGTH_SHORT).show();
		}
		
	};
	
	private Context context;
	
	private RequestCallBack requestCallBack = null;
	
	public RequestUtils(Context mcontext){
		this.context = mcontext;
	}
	
	public void request(final RequestEntity requestEntity,RequestCallBack requestCallBack){
		this.requestCallBack = requestCallBack;
		new Thread() {

			@Override
			public void run() {
					if(!InternetHelper.isInternetAvaliable(context)){
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


	
}
