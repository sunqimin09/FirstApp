package com.example.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.entity.BaseEntity;
import com.example.entity.UserEntity;
import com.example.entity.TestLoginEntity;
import com.example.entity.TestRegistEntity;

public class TestJsonParse {
	
	public static BaseEntity JsonParse(int requestType, String responseStr) {
		BaseEntity baseEntity = null;
		try {
			baseEntity = ParseBase(responseStr);
			switch (requestType) {
			case GlobalRequestConfig.TYPE_LOGIN:
				baseEntity = ParseLogin(baseEntity.getResultObject());
				break;
			case GlobalRequestConfig.TYPE_REGIST:
				baseEntity = ParseRegist(baseEntity.getResultObject());
				break;
			case GlobalRequestConfig.TYPE_MY_RANK_INFOR://同下
				ParsePeople(baseEntity.getResultObject());
				break;
			case GlobalRequestConfig.TYPE_MY_INPUTED_INFOR://我的曾经输入的信息
				ParsePeople(baseEntity.getResultObject());
				break;
			case GlobalRequestConfig.TYPE_MY_INFOR_EDIT://编辑
				return baseEntity;
			case GlobalRequestConfig.TYPE_WORLD_RANK://世界排名，可以限制某一地区，或者某一 行业
				baseEntity.setList(ParseRank(baseEntity.getResultObject()));
				break;
			case GlobalRequestConfig.TYPE_REGION_RANK://地区排名
				
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
	private static BaseEntity ParseLogin(JSONObject object)
			throws JSONException {
		TestLoginEntity entity = new TestLoginEntity();

		return entity;

	}
	
	private static BaseEntity ParseRegist(JSONObject object){
		TestRegistEntity entity = new TestRegistEntity();
		
		return entity;
	}
	
	private static UserEntity ParsePeople(JSONObject object){
		UserEntity entity = new UserEntity();
		
		return entity;
	}
	
	private static List<UserEntity> ParseRank(JSONObject object){
		List<UserEntity> list = new ArrayList<UserEntity>();
		
		return list;
	}

}
