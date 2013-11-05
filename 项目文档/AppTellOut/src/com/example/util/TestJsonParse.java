package com.example.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.entity.BaseEntity;
import com.example.entity.UserEntity;
import com.sun.constant.DbConstant;

public class TestJsonParse {
	
	public static BaseEntity JsonParse(int requestType, String responseStr) {
		BaseEntity baseEntity = null;
		try {
			baseEntity = ParseBase(responseStr);
			switch (requestType) {
			case MConstant.REQUEST_CODE_LOGIN_:
				baseEntity = ParseLogin_Regist(baseEntity.getResultObject());
				break;
			case MConstant.REQUEST_CODE_REGIST:
				baseEntity = ParseLogin_Regist(baseEntity.getResultObject());
				break;
			case MConstant.REQUEST_CODE_GET_SELF_INFOR://获得我的个人信息
				ParseGetSelfInfor(baseEntity.getResultObject());
				break;
			case MConstant.REQUEST_CODE_EDIT_SELF_INFOR://编辑的信息,返回是否成功
//				ParseEditSelfInfor(baseEntity.getResultObject());
				break;
			case MConstant.REQUEST_CODE_GET_MY_RANK://我的个人排名信息
				ParseGetSelfRankInfor(baseEntity.getResultObject());
				break;
			case MConstant.REQUEST_CODE_WORLD_RANK://世界排名，可以限制某一地区，或者某一 行业
				baseEntity.setList(ParseRank(baseEntity.getResultObject()));
				break;
			case MConstant.REQUEST_CODE_REGION_RANK://地区排名
				
				break;
			case MConstant.REQUEST_CODE_INDUSTRY_RANK://行业排名
				break;
			case MConstant.REQUEST_CODE_COMPANY_RANK://公司排名
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
	private static BaseEntity ParseLogin_Regist(JSONObject object)
			throws JSONException {
		BaseEntity entity = new BaseEntity();
		Map<String,String> map = new HashMap<String,String>();
		map.put(DbConstant.DB_USER_ID, object.getString(DbConstant.DB_USER_ID));
		entity.setMap(map);
		return entity;

	}
	
	/**
	 * 解析我的个呢信息
	 * @param object
	 * @return  所有的个人信息
	 */
	private static UserEntity ParseGetSelfInfor(JSONObject object){
		UserEntity entity = new UserEntity();
		
		
		return entity;
		
	}
	
	/**
	 * 获得
	 * @param object
	 * @return
	 * @throws JSONException 
	 */
	private static UserEntity ParseGetSelfRankInfor(JSONObject object) throws JSONException{
		UserEntity entity = new UserEntity();
		entity.setWorldRank(object.getInt("worldRank"));
		entity.setRegionRank(object.getInt("regionRank"));
		entity.setIndustryRank(object.getInt("industryRank"));
		entity.setName(object.getString(DbConstant.DB_USER_NICK_NAME));
		entity.setScore(object.getInt(DbConstant.DB_USER_SCORE));
		return entity;
	}
	
	
	/**
	 * 编辑我的个人信息
	 * @param object
	 * @return  是否成功编辑
	 */
	private static UserEntity ParseEditSelfInfor(JSONObject object){
		UserEntity entity = new UserEntity();
		
		return entity;
	}
	
	private static List<UserEntity> ParseRank(JSONObject object){
		List<UserEntity> list = new ArrayList<UserEntity>();
		
		return list;
	}

}
