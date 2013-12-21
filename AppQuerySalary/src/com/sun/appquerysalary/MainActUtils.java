package com.sun.appquerysalary;

import android.content.Context;
import android.os.Handler;

import com.sun.appquerysalary.entity.RequestEntity;
import com.sun.appquerysalary.interface_.AbsRequestCallBack;
import com.sun.appquerysalary.interface_.IRequestCallBack;
import com.sun.appquerysalary.utils.InternetHelper;

public class MainActUtils {

	private Context context = null;
	
	private Handler handler = null;
	
	private IRequestCallBack requestCallBack = null;
	
	public MainActUtils (Context context,Handler handler){
		this.context = context ;
		this.handler = handler;
	}
	
	/**
	 * 发起网络请求
	 * @param type 请求类型，公司，岗位
	 * @param inputStr 输入查询的字段
	 */
	public void request(final RequestEntity requestEntity,final IRequestCallBack requestCallBack){
		//1.网络可用，2，输入合法 3.上传type和inputStr
		this.requestCallBack = requestCallBack;
		if(!InternetHelper.isInternetAvaliable(context)){
			requestCallBack.requestFailedStr("当前无网络连接，请检查网络连接...");
			return ;
		}
		
		if(!checkInput(String.valueOf(requestEntity.params.get("input")))){
			requestCallBack.requestFailedStr("格式不正确哦");
			return ;
		}
		InternetHelper.requestThread(requestEntity, requestCallBack);
		
		
	}

	/**
	 * 判断输入
	 * @param inputStr
	 * @return
	 */
	private boolean checkInput(String inputStr) {
		
		return false;
	}
	
}
