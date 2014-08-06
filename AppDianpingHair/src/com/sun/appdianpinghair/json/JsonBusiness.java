package com.sun.appdianpinghair.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.sun.appdianpinghair.debug.MyDebug;
import com.sun.appdianpinghair.entity.BusinessEntity;
import com.sun.appdianpinghair.entity.DealsEntity;
import com.sun.appdianpinghair.entity.JsonBusinessEntity;

public class JsonBusiness {
	
	public static JsonBusinessEntity parse(String content){
		JsonBusinessEntity businessEntity = new JsonBusinessEntity();
		try {
			JSONObject object = new JSONObject(content);
			String status = object.getString("status");
			if(status.equals("OK")){
				businessEntity.status = status;
				if(object.has("total_count"))
					businessEntity.totalCount = object.getInt("total_count");
				businessEntity.count = object.getInt("count");
				if(object.has("businesses"))
					businessEntity.list = parseBusiness(object.getJSONArray("businesses"));
			}else{
				businessEntity.status = status;
			}
			Log.d("tag","parse-->"+businessEntity.status);
		} catch (JSONException e) {
			businessEntity.status = "数据错误";
			e.printStackTrace();
		}
		return businessEntity;
		
	}
	
	private static List<BusinessEntity> parseBusiness(JSONArray businessArray) throws JSONException{
		MyDebug.Log("array-->"+businessArray);
		List<BusinessEntity> list = new ArrayList<BusinessEntity>();
		BusinessEntity entity;
		JSONObject object;
		for(int i=0;i<businessArray.length();i++){
			object = businessArray.getJSONObject(i);
			entity = new BusinessEntity();
			entity.id = object.getInt("business_id");
			entity.name = object.getString("name");
			entity.branch_name = object.getString("branch_name");
			entity.address = object.getString("address");
			entity.telephone = object.getString("telephone");
			entity.city = object.getString("city");
			entity.region = parseRegions( object.getJSONArray("regions"));
//			object.getJSONArray("categories");
			entity.latitude =  object.getDouble("latitude");
			entity.longitude = object.getDouble("longitude");
			entity.avg_rating = (float) object.getDouble("avg_rating");
//			object.getString("rating_img_url");
//			object.getString("rating_s_img_url");
			entity.product_grade = object.getInt("product_grade");
			entity.decoration_grade = object.getInt("decoration_grade");
			entity.service_grade = object.getInt("service_grade");
			entity.product_score = (float)object.getDouble("product_score");
			entity.decoration_scrore = (float) object.getDouble("decoration_score");
			entity.service_score = (float) object.getDouble("service_score");
			entity.avg_price = object.getInt("avg_price");
			entity.review_count = object.getInt("review_count");
			entity.distance = object.getInt("distance");
			entity.business_url = object.getString("business_url");
			entity.photo_url = object.getString("photo_url");
			entity.s_photo_url = object.getString("s_photo_url");
			entity.has_coupon = object.getInt("has_coupon");
			entity.coupon_id = object.getInt("coupon_id");
			entity.coupon_description = object.getString("coupon_description");
			entity.coupon_url = object.getString("coupon_url");
			entity.has_deal = object.getInt("has_deal");
			entity.deal_count = object.getInt("deal_count");
//			object.getJSONArray("deals");
			entity.deals = parseDeals(object.getJSONArray("deals"));
			entity.has_online_reservation = object.getInt("has_online_reservation");
			entity.online_reservation_url = object.getString("online_reservation_url");
			list.add(entity);
		}
		Log.d("tag","Business--size"+list.size());
		return list;
	}
	
	private static List<String> parseRegions(JSONArray regions) throws JSONException{
		List<String> list = new ArrayList<String>();
		for(int i =0;i<regions.length();i++){
			list.add(regions.getString(i));
		}
		return list;
	}
	
	private static List<DealsEntity> parseDeals(JSONArray deals) throws JSONException{
		List<DealsEntity> list = new ArrayList<DealsEntity>();
		JSONObject object = null;
		DealsEntity entity;
		for(int i =0;i<deals.length();i++){
			object = deals.getJSONObject(i);
			entity = new DealsEntity();
			entity.description = object.getString("description");
			entity.url = object.getString("url");
			entity.id = object.getString("id");
			list.add(entity);
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
}
