package com.sun.apphair.internet;

import com.sun.apphair.entity.ResponseResult;


public interface IRequestCallBack {

	/**
	 * @param responseResult
	 */
	 public void requestSuccess(ResponseResult responseResult);
     
	 /**
	  * @param str
	  */
     public void requestFailedStr(String str);
}
