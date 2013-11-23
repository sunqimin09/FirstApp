package com.tellout.util;

import android.content.Context;
import android.telephony.TelephonyManager;

public class ImeiUtil {
	
	/**
	 * 获得手机imei号
	 * @param context
	 * @return
	 */
	public static String getImei(Context context){
		TelephonyManager telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
}
