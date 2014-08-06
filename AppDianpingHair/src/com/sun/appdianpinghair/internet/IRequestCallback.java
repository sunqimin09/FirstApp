package com.sun.appdianpinghair.internet;

import com.sun.appdianpinghair.entity.JsonBaseInterface;

/**
 * 网络请求毁掉方法
 * @author sunqm
 * Create at:   2014-6-22 下午1:18:51 
 * TODO
 */
public interface IRequestCallback {
	
	public void requestSuccess(JsonBaseInterface result);
	
	public void requestFailed(String error);
}
