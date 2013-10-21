package com.example.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.entity.BaseEntity;
import com.example.entity.TestLoginEntity;
import com.example.entity.TestRegistEntity;

public class TestJsonParse {
	
	public static BaseEntity JsonParse(int requestType, String responseStr) {
		BaseEntity baseEntity = null;
		try {
			baseEntity = ParseBase(responseStr);
			switch (requestType) {
			case GlobalRequestConfig.TYPE_LOGIN:
				baseEntity = ParseLogin(baseEntity);
				break;
			case GlobalRequestConfig.TYPE_REGIST:
				break;
			case GlobalRequestConfig.TYPE_MY_RANK_INFOR:
				break;
			case GlobalRequestConfig.TYPE_MY_INPUTED_INFOR:
				break;
			case GlobalRequestConfig.TYPE_MY_INFOR_EDIT:
				break;
			case GlobalRequestConfig.TYPE_WORLD_RANK:
				break;
			case GlobalRequestConfig.TYPE_INDUSTRY_RANK:
				break;
			case GlobalRequestConfig.TYPE_COMPANY_RANK:
				break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			
		}
		
		return baseEntity;
	}

	private static BaseEntity ParseBase(String str) throws JSONException{
		BaseEntity entity = new BaseEntity();
		JSONObject object = new JSONObject(str);
		// 得到code值
		entity.setCode(object.getInt("code"));
		JSONObject result = object.getJSONObject("result");
		entity.setResultObject(result);
		return entity;
	}
	
	/**
	 * 解析登录返回信息
	 * 
	 * @param responseStr
	 * @return
	 * @throws JSONException
	 */
	private static BaseEntity ParseLogin(BaseEntity baseEntity)
			throws JSONException {
		TestLoginEntity entity = new TestLoginEntity();
//		entity.set
//		JSONObject result = baseEntity.getResultObject();
//		entity.setCodeString(result.getString("codeStr"));

		return entity;

	}
	
	private static BaseEntity ParseRegist(String str){
		TestRegistEntity entity = new TestRegistEntity();
		
		return entity;
	}

}
