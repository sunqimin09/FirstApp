/**
 * 
 */
package com.example.msalary.activity;

import com.example.msalary.entity.ResponseResult;
import com.example.msalary.internet.IRequestCallBack;

import android.app.Activity;
import android.widget.Toast;

/**
 * 项目名称：营销移动智能工作平台 <br>
 * 文件名：TerminalDetailsFragment.java <br>
 * 作者：@沈潇    <br>
 * 创建时间：2013/11/24 <br>
 * 功能描述: 终端详情 <br>
 * 版本 V 1.0 <br>               
 * 修改履历 <br>
 * 日期      原因  BUG号    修改人 修改版本 <br>
 */
public class BaseActivity extends Activity implements IRequestCallBack{

	
	/**
	 * 初始化控件
	 */
	protected void initView(){
		
	}
	
	/* (non-Javadoc)
	 * @see com.example.msalary.internet.IRequestCallBack#requestSuccess(com.example.msalary.entity.ResponseResult)
	 */
	@Override
	public void requestSuccess(ResponseResult responseResult) {
		
	}

	/* (non-Javadoc)
	 * @see com.example.msalary.internet.IRequestCallBack#requestFailedStr(java.lang.String)
	 */
	@Override
	public void requestFailedStr(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	
}
