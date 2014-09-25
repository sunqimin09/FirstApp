package com.sun.hair.service;

import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.sun.hair.utils.DemoApiTool;
import com.sun.hair.utils.InterfaceCallback;
import com.sun.hair.utils.MConstant;

public class BaseService {
	
	
	
	public void request(final String url,final Map<String,String> params,final InterfaceCallback callBack){
		new Thread(){

			@Override
			public void run() {
				String requestResult = DemoApiTool.requestApi(url,
						MConstant.KEY, MConstant.SECRET, params);
				Log.d("tag","result--->"+requestResult);
				Object o  = parse(requestResult);
				send(o, callBack);
			}
			
		}.start();
		
		
	}
	
	public Object parse(String result){
		
		return result;
	}
	
	public void send(Object result,InterfaceCallback callBack){
		Message msg = handler.obtainMessage();
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("result", result);
		map.put("interface", callBack);
		msg.what = 0;
		msg.obj = map;
		handler.sendMessage(msg);
	}
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			HashMap<String, Object> maps = ((HashMap<String,Object>)msg.obj);
			switch(msg.what){
			case 0:
				((InterfaceCallback)maps.get("interface")).onSuccess(maps.get("result"));
				break;
			case 1:
				break;
			}
		}
		
	};
	
	
}
