package com.sun.utils;

import java.util.List;
import java.util.Map;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

import com.sun.db.DbConstant;
import com.sun.entity.ResponseEntity;

public class JsonHelper {
	
	/**
	 * 注册
	 * @param responseEntity
	 * @return
	 */
	public static JSONObject encodeRegist(ResponseEntity responseEntity){
		JSONObject object = new JSONObject();
		try {
			object.put("code", responseEntity.getCode());
			object.put("codeStr", responseEntity.getCode_str());
			JSONObject result = new JSONObject();
			result.put("id", responseEntity.getParams().get(DbConstant.DB_USER_ID));
			object.put("result", result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * 编码登录
	 * @param responseEntity
	 * @return
	 */
	public static JSONObject encodeLogin(ResponseEntity responseEntity){
		JSONObject object = new JSONObject();
		try {
			object.put("code", responseEntity.getCode());
			object.put("codeStr", responseEntity.getCode_str());
			JSONObject result = new JSONObject();
			result.put("id", responseEntity.getParams().get(DbConstant.DB_USER_ID));
			object.put("result", result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * 编码我的排名
	 * @param responseEntity
	 * @return
	 */
	public static JSONObject encodeMyRankInfor(ResponseEntity responseEntity) {
		JSONObject object = new JSONObject();
		try {
			object.put("code", responseEntity.getCode());
			object.put("codeStr", responseEntity.getCode_str());
			JSONObject result = new JSONObject();
			object.put("result", result);
			Map<String, String> map = responseEntity.getParams();
			result.put("worldRank", map.get("worldRank"));
			result.put("regionRank", map.get("regionRank"));
			result.put("industryRank", map.get("industryRank"));
			result.put(DbConstant.DB_USER_NICK_NAME, map.get(DbConstant.DB_USER_NICK_NAME));
			result.put(DbConstant.DB_USER_SCORE, map.get(DbConstant.DB_USER_SCORE));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * 返回是否成功即可
	 * @return
	 */
	public static JSONObject encodeMyInputedInforEdited(ResponseEntity responseEntity){
		JSONObject object = new JSONObject();
		try {
			object.put("code", responseEntity.getCode());
			object.put("codeStr", responseEntity.getCode_str());
			JSONObject result = new JSONObject();
			object.put("result", result);
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		
		return object;
	}
	
	public static JSONObject encodeMyInfor_Get(ResponseEntity responseEntity){
		JSONObject object = new JSONObject();
		try {
			object.put("code", responseEntity.getCode());
			object.put("codeStr", responseEntity.getCode_str());
			JSONObject result = new JSONObject();
			object.put("result", result);
			
			Map<String, String> map = responseEntity.getParams();
			for (Map.Entry<String,String> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				result.put(key, value);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * 编码 世界排名
	 * @param responseEntity
	 * @return
	 */
	public static JSONObject encodeWorldRank(ResponseEntity responseEntity){
		JSONObject object = new JSONObject();
		try {
			object.put("code", responseEntity.getCode());
			object.put("codeStr", responseEntity.getCode_str());
			JSONObject result = new JSONObject();
			object.put("result", result);
			JSONArray jsonArray = new JSONArray();
			JSONObject item = null;
			List<Map<String,String>> list = responseEntity.getList();
			Map<String, String> map = null;
			int total = list.size();
			for(int i =0;i<total ;i++){//列表
				map = list.get(i);
				item = new JSONObject();
				for (Map.Entry<String,String> entry : map.entrySet()) {//每一项
					String key = entry.getKey();
					String value = entry.getValue();
					item.put(key, value);
				}
				jsonArray.add(item);
			}
			object.put("list", jsonArray);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
	
}
