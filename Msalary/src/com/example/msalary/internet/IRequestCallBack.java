package com.example.msalary.internet;

import com.example.msalary.entity.ResponseResult;

public interface IRequestCallBack {

	/**
	 * ÍøÂçÇëÇó³É¹¦
	 * @param responseResult
	 */
	 public void requestSuccess(ResponseResult responseResult);
     
	 /**
	  * ÍøÂçÇëÇóÊ§°Ü
	  * @param str
	  */
     public void requestFailedStr(String str);
}
