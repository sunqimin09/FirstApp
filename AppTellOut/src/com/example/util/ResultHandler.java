package com.example.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.apptellout.BaseActivity;
import com.example.entity.BaseEntity;
import com.sun.constant.ErrorCodeConstant;
/**
 * 提示各种异常  或者显示正确数据
 * @author sunqm
 *
 */
public class ResultHandler extends Handler{

	private BaseActivity activity;
	
	public ResultHandler(BaseActivity activity){
		this.activity = activity;
	}
	
	@Override
	public void handleMessage(Message msg) {
		Log.d("tag","Result--handler->"+msg.what);
		String temp = "成功";
		switch(msg.what){
		case ErrorCodeConstant.CODE_NAME_EXIT:
			temp = "昵称已经存在";
			break;
		case ErrorCodeConstant.CODE_EMAIL_EXIT:
			temp = "邮箱已被注册";
			break;
		case ErrorCodeConstant.CODE_NAME_PWD_ERROR:
			temp = "用户名或密码错误";
			break;
		case ErrorCodeConstant.CODE_SUCCESS://数据正确
			activity.showResult(msg.arg1,(BaseEntity) msg.obj);
			return;
		case ErrorCodeConstant.CODE_TIME_OUT:
			temp = "请求超时，请检查网络";
			break;
		case ErrorCodeConstant.CODE_JSON_EXCEPTION:
			temp = "数据已经损坏";
			break;
		case ErrorCodeConstant.CODE_FAILED:
			temp = "未知异常";
			break;
		case ErrorCodeConstant.CODE_SQL_EXCEPTION:
			temp = "数据库异常";
			break;
		default:
			temp = "服务器异常";
		}
		Toast.makeText(activity, temp, Toast.LENGTH_SHORT).show();
	}
	
	
	
}	
