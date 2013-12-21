package com.sun.appquerysalary.interface_;

import com.sun.appquerysalary.utils.ErrorCodeUtils;

public abstract class AbsRequestCallBack implements IRequestCallBack{
	
	public void requestFailedCode(int code){
		requestFailedStr(ErrorCodeUtils.changeCodeToStr(code));
	}
}
