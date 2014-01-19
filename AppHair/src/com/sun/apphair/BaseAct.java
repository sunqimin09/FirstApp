/**
 * 
 */
package com.sun.apphair;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

import com.sun.apphair.entity.ResponseResult;
import com.sun.apphair.internet.IRequestCallBack;

/**
 * 项目名称：Hair<br>
 * 文件名：BaseAct.java BaseAct <br>
 * 作者：@sunqm    <br>
 * 创建时间：2014-1-18 上午11:59:02
 * 功能描述: 终端详情 <br>
 * 版本 V 1.0 <br>               
 */
public class BaseAct extends Activity implements IRequestCallBack{

	private String TAG = "baseAct";
	
	/* (non-Javadoc)
	 * @see com.sun.apphair.internet.IRequestCallBack#requestSuccess(com.sun.apphair.entity.ResponseResult)
	 */
	@Override
	public void requestSuccess(ResponseResult responseResult) {
		Log.d(TAG,"请求成功->"+responseResult.toString());
	}

	/* (non-Javadoc)
	 * @see com.sun.apphair.internet.IRequestCallBack#requestFailedStr(java.lang.String)
	 */
	@Override
	public void requestFailedStr(String str) {
		
	}
	

}
