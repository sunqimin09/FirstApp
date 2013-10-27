package com.example.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.entity.ResponseEntity;

public class JsonHelper {
	
	public ResponseEntity JsonParse(int type,String result){
		ResponseEntity responseEntity = new ResponseEntity();
		responseEntity.setRequestCode(type);
		try {
			JSONObject object = new JSONObject(result);
			int code = object.getInt("code");
			responseEntity.setCode(code);
//			if(code==MConstant.SUCCESS){
//				JSONObject resultJSON = object.getJSONObject("result");
//				responseEntity.setResult(resultJSON);
//			}
			Log.d("tag","parse--result->"+responseEntity.toString());
			return responseEntity;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 解析登录和注册部分
	 * @param resultObject
	 * @throws JSONException 
	 */
	private Object parseLogin_Regist(JSONObject resultObject) throws JSONException{
		String id = resultObject.getString("id");
		return id;
	}
	
	
	
}
