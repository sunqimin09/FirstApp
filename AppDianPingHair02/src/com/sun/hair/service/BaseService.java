package com.sun.hair.service;

import java.util.HashMap;
import java.util.Map;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;

import com.sun.hair.utils.DemoApiTool;
import com.sun.hair.utils.InterfaceCallback;
import com.sun.hair.utils.MConstant;

public class BaseService {
	
	FinalHttp finalHttp;
	
	public BaseService(){
		finalHttp = new FinalHttp();
	}
	
	
	public void request(final String url,final Map<String,String> params,final InterfaceCallback callBack){
		new Thread(){

			@Override
			public void run() {
				String requestResult = DemoApiTool.requestApi(url,
						MConstant.KEY, MConstant.SECRET, params);
				Log.d("tag","result--->"+requestResult);
				Object o;
				try {
					o = parse(requestResult);
					send(o, callBack);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
		}.start();
		
		
	}
	
	public void request(Context context,String url,AjaxParams params,final IRequestCallBack callBack){
		showStartLoading(context);
		Log.d("tag","onrequest-->"+url+params);
		post(url,params,"",callBack);
//		finalHttp.configCharset("utf-8");
//		
//		finalHttp.get(url, params, new AjaxCallBack<Object>() {
//
//			@Override
//			public void onFailure(Throwable t, int errorNo, String strMsg) {
//				callBack.onFailed(strMsg);
//				hideLoading();
//			}
//
//			@Override
//			public void onSuccess(Object t) {
//				Log.d("tag","onsuccess-->"+t);
//				try {
//					hideLoading();
//					parseBase(t,callBack);
//					
//					Log.d("tag","onsuccess--parse---end>"+t);
//				} catch (JSONException e) {
//					e.printStackTrace();
//					callBack.onFailed("��ݴ���11");
//				}
//				
//			}
//			
//		});
		
	}
	
	private void post(String url,AjaxParams params,String contentType,final IRequestCallBack callBack){
		finalHttp.post(url,  params,  new AjaxCallBack<Object>() {
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				callBack.onFailed(strMsg);
				hideLoading();
			}

			@Override
			public void onSuccess(Object t) {
				Log.d("tag","onsuccess-->"+t);
				try {
					hideLoading();
					parseBase(t,callBack);
					
					Log.d("tag","onsuccess--parse---end>"+t);
				} catch (JSONException e) {
					e.printStackTrace();
					callBack.onFailed("数据错误");
				}
				
			}
			
			
		});
	}
	
	public Object parse(String result) throws JSONException{
		
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
				Log.d("tag","handler-->"+maps.get("result"));
				((InterfaceCallback)maps.get("interface")).onSuccess(maps.get("result"));
				break;
			case 1:
				break;
			}
		}
		
	};
	
ProgressBar dialog = null;
	
	/**
	 * ��ʼ����
	 */
	public void showStartLoading(Context context){
//		dialog = new ProgressBar(context);
		
	}
	
	public void hideLoading(){
		Log.d("tag","dialog--dismiss"+dialog);
//		if(dialog!=null&&dialog.isShowing()){
//			dialog.dismiss();
//		}
	}
	
	
	private void parseBase(Object t,IRequestCallBack callBack) throws JSONException{
		JSONObject object = new JSONObject(t.toString());
		int code = object.getInt("code");
		Log.d("tag","onsuccess--1>"+code+"--"+(code==MConstant.CODE_SUCCESS));
		if(code==MConstant.CODE_SUCCESS){
			
			JSONObject result = object.getJSONObject("result");
			callBack.onSuccess(parseDetail(result));
		}else{
			callBack.onFailed(object.getString("msg"));
		}
		
	}
	
	public Object parseDetail(JSONObject result) throws JSONException{
		Log.d("tag","base--parse-detail--");
		return "";
	}
	
	
}
