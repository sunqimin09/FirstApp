/**
 * 
 */
package com.sun.apphair.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.apphair.entity.ResponseResult;
import com.sun.apphair.entity.ShopEntity;
import com.sun.apphair.entity.ShowResult;
import com.sun.apphair.internet.IRequestCallBack;
import com.sun.apphair.utils.Mconstant;

/**
 * 项目名称：Hair
 * 文件名：JsonMainList.java  
 * 作者：@sunqm    
 * 创建时间：2014-1-18 下午3:49:48
 * 功能描述:  
 * 版本 V 1.0               
 */
public class JsonMainList {
	/**
	 * 解析店铺列表
	 * @param responseResult
	 */
	public ArrayList<ShopEntity> parse(ResponseResult responseResult,IRequestCallBack requestCallBack){
		ShowResult showResult = new ShowResult();
		ArrayList<ShopEntity> list = new ArrayList<ShopEntity>();
		try {
			JSONObject object = new JSONObject(responseResult.resultStr);
			JSONArray array = object.getJSONArray("list");
			ShopEntity entity =  null;
			JSONObject item = null;
			for(int i =0;i<array.length();i++){
				item = array.getJSONObject(i);
				entity = new ShopEntity();
				entity.name = (item.getString("name"));
				entity.id = (item.getInt("id"));
				entity.address = item.getString("address");
				entity.distance = item.getInt("distance");
				entity.price = (float) item.getDouble("price");
				entity.ratingbarScore = (float) item.getDouble("ratingbarScore");
				entity.logoUrl = item.getString("logoUrl");
				list.add(entity);
			}
			showResult.list = list;
		} catch (JSONException e) {
			requestCallBack.requestFailedStr(Mconstant.ERROR_JSON);
			e.printStackTrace();
			return null;
		}
		return list;
	}
}
