package com.sun.appdianpinghair.internet;

import java.util.Map;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.sun.appdianpinghair.debug.MyDebug;
import com.sun.appdianpinghair.entity.JsonBusinessEntity;
import com.sun.appdianpinghair.json.JsonBusiness;
import com.sun.appdianpinghair.utils.DemoApiTool;
import com.sun.appdianpinghair.utils.Mconstant;

public class RequestUtils {

	public static void requestAct(final String url,
			final Map<String, String> paramMap, final Handler handler) {
		new Thread() {

			@Override
			public void run() {
				if(Mconstant.latitude!=0){//添加地理坐标
					paramMap.put("latitude", String.valueOf(Mconstant.latitude));
			         paramMap.put("longitude", String.valueOf(Mconstant.longitude));
				}
				String requestResult = DemoApiTool.requestApi(url,
						MyDebug.appKey, MyDebug.secret, paramMap);
				JsonBusinessEntity entity = JsonBusiness.parse(requestResult);
				Log.d("tag", "entity--->" + entity.list.size());
				Message msg = handler.obtainMessage();
				msg.obj = entity;
				handler.sendMessage(msg);
				//
			}
		}.start();
	}

}
