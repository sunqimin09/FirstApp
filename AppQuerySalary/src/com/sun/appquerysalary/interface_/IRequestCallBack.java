package com.sun.appquerysalary.interface_;

import com.sun.appquerysalary.entity.ShowResult;

public interface IRequestCallBack {
	
	public void requestSuccess(ShowResult showResult);
	
	public void requestFailedStr(String str);
}
