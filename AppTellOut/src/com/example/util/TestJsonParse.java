package com.example.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.entity.BaseEntity;
import com.example.entity.UserEntity;
import com.sun.constant.DbConstant;
import com.sun.constant.ErrorCodeConstant;

public class TestJsonParse {
	
	public static BaseEntity JsonParse(int requestType, String responseStr) {
		BaseEntity baseEntity = null;
		try {
			baseEntity = ParseBase(responseStr);
			switch (requestType) {
			case MConstant.REQUEST_CODE_LOGIN_:
				baseEntity = ParseLogin(baseEntity);
				break;
			case MConstant.REQUEST_CODE_REGIST:
				baseEntity = ParseRegist(baseEntity);
				break;
			case MConstant.REQUEST_CODE_MYINFOR://获得我的个人信息
				baseEntity = ParseMyInfor(baseEntity);
				break;
			case MConstant.REQUEST_CODE_EDIT_SELFINFOR://我的曾经输入的信息
				baseEntity = ParseEditMyInfor(baseEntity);
				break;
			case MConstant.REQUEST_CODE_MY_RANK_INFOR://我的排名信息
				return  ParseMyRank(baseEntity);
			case MConstant.REQUEST_CODE_WORLD_RANK://世界排名，可以限制某一地区，或者某一 行业
				baseEntity = ParseRank(baseEntity);
				break;
			case MConstant.REQUEST_CODE_REGION_RANK://地区排名
				
				break;
			case MConstant.REQUEST_CODE_INDUSTRY_RANK://行业排名
				break;
			case MConstant.REQUEST_CODE_COMPANY_RANK://公司排名
				break;
			}
		} catch (JSONException e) {
			baseEntity.setCode(ErrorCodeConstant.CODE_JSON_EXCEPTION);
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
		Map<String,String> map = new HashMap<String,String>();
		map.put(DbConstant.DB_USER_ID,
				baseEntity.getResultObject().getString(DbConstant.DB_USER_ID));
		MConstant.USER_ID_VALUE = baseEntity.getResultObject().getString(DbConstant.DB_USER_ID);
		Log.d("tag","userID-->"+MConstant.USER_ID_VALUE);
		return baseEntity;

	}
	
	private static BaseEntity ParseRegist(BaseEntity baseEntity) throws JSONException{
		Map<String,String> map = new HashMap<String,String>();
		map.put(DbConstant.DB_USER_ID,
				baseEntity.getResultObject().getString(DbConstant.DB_USER_ID));
		
		MConstant.USER_ID_VALUE = baseEntity.getResultObject().getString(DbConstant.DB_USER_ID);
		return baseEntity;
	}
	
	/**
	 * 解析我的个人基本信息
	 * @param baseEntity
	 * @return
	 * @throws JSONException 
	 */
	//{"salaryPer":"50","welfare":"0","salary":"0","industryId":"0",
//	"nickName":"test1","welfarePer":"50","regionId":"0"}

	private static UserEntity ParseMyInfor(BaseEntity baseEntity) throws JSONException{
		UserEntity entity = new UserEntity();
		Log.d("tag","MyInfor-->"+baseEntity.getResultObject());
		JSONObject object = baseEntity.getResultObject();
		entity.setCode(baseEntity.getCode());
//		entity.setCompanyId(object.getString(""));
//		entity.setId(id)
		entity.setIndustryId(object.getInt(DbConstant.DB_USER_INDUSTRY_ID));
		entity.setName(object.getString(DbConstant.DB_USER_NICK_NAME));
//		entity.setScore(score)
		entity.setSalary(object.getString(DbConstant.DB_USER_SALARY));
		entity.setSalaryPer(object.getString(DbConstant.DB_USER_SALARY_PER));
		entity.setWelfare(object.getString(DbConstant.DB_USER_WELFARE));
		entity.setWelfarePer(object.getString(DbConstant.DB_USER_WELFARE_PER));
//		entity.setWorkAge(object.getString(DbConstant.DB_USER_WORK_AGE));
		return entity;
	}
	
	/**
	 * 解析是否上传成功
	 * @param baseEntity
	 * @return
	 */
	private static BaseEntity ParseEditMyInfor(BaseEntity baseEntity){
		BaseEntity entity = new UserEntity();
		
		return baseEntity;
	}
	
	/**
	 * 我的个人排行榜
	 * @param baseEntity
	 * @return
	 * @throws JSONException 
	 */
	private static UserEntity ParseMyRank(BaseEntity baseEntity) throws JSONException{
		UserEntity entity = new UserEntity();
		entity.setCode(baseEntity.getCode());
		JSONObject object = baseEntity.getResultObject();
		entity.setScore(object.getInt(DbConstant.DB_USER_SCORE));
		entity.setName(object.getString(DbConstant.DB_USER_NICK_NAME));
		entity.setWorldRank(object.getString("worldRank"));
		return entity;
	}
	
	private static BaseEntity ParseRank(BaseEntity baseEntity) throws JSONException{
		List<UserEntity> list = new ArrayList<UserEntity>();
		UserEntity entity = null;
		JSONArray array = baseEntity.getResultObject().getJSONArray("list");
		//{"list":[{"score":"4900","nickName":"test1","industryName":"未知"},
//		{"score":"0","nickName":"test2","industryName":"未知"}]}
		JSONObject object = null;
		for(int i = 0 ;i<array.length();i++){
			object = array.getJSONObject(i);
			entity = new UserEntity();
			entity.setName(object.getString(DbConstant.DB_USER_NICK_NAME));
			entity.setScore(object.getInt(DbConstant.DB_USER_SCORE));
			entity.setIndustry_name(object.getString(DbConstant.DB_INDUSTRY_NAME));
			Log.d("tag","rank-->"+entity.getIndustry_name());
			list.add(entity);
		}
		
		baseEntity.setList(list);
		return baseEntity;
	}

}
