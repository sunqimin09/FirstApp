package cn.com.bjnews.thinker.internet;

import cn.com.bjnews.thinker.entity.ResponseResult;



public interface IRequestCallBack {

	/**
	 * 发起网络请求
	 */
	public void request(int timeOut);
	
	/**
	 * @param responseResult
	 */
	 public void requestSuccess(ResponseResult responseResult);
     
	 /**
	  * @param str
	  */
     public void requestFailedStr(String str);
}
