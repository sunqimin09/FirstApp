package com.example.msalary.internet;

import com.example.msalary.entity.ResponseResult;

public interface IRequestCallBack {

	 public void requestSuccess(ResponseResult responseResult);
     
     public void requestFailedStr(String str);
}
