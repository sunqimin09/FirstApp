/**
 * 
 */
package com.example.msalary.util;

import android.content.Context;
import android.telephony.TelephonyManager;

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
public class Utils {
	
	/**
	 * 获得手机imei码
	 * @param context
	 * @return
	 */
	public static String getImeiCode(Context context){
		String Imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
				.getDeviceId();
		return Imei;
	}
	
}
