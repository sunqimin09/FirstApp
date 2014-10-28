package com.sun.hair.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.hair.entity.JsonReviewEntity;
import com.sun.hair.entity.ReviewEntity;

public class ReviewService extends BaseService{

	@Override
	public Object parse(String result) {
		JsonReviewEntity entity = new JsonReviewEntity();
		try {
			JSONObject object = new JSONObject(result);
			String status = object.getString("status");
			if(status.equals("OK")){
			List<ReviewEntity> list = parseReView(object.getJSONArray("reviews"));
			entity.moreUrl = object.getJSONObject("additional_info").getString("more_reviews_url");
			entity.list = list;
			
				return entity;
			}else{
				return "·þÎñÆ÷Òì³£";
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<ReviewEntity> parseReView(JSONArray jsonArray) throws JSONException {
		JSONObject item =null;
		List<ReviewEntity> list = new ArrayList<ReviewEntity>();
		ReviewEntity entity = null;
		for(int i=0;i<jsonArray.length();i++){
			entity = new ReviewEntity();
			item = jsonArray.getJSONObject(i);
			entity.review_id = item.getInt("review_id");
			entity.user_nickname = item.getString("user_nickname");
			entity.created_time = item.getString("created_time");
			entity.text_excerpt = item.getString("text_excerpt");
			entity.rating_s_img_url = item.getString("rating_s_img_url");
			entity.review_rating = item.getDouble("review_rating");
			entity.product_rating = item.getDouble("product_rating");
			entity.decoration_rating = item.getDouble("decoration_rating");
			entity.service_rating = item.getDouble("service_rating");
			entity.review_url = item.getString("review_url");
			list.add(entity);
		}
		
		return list;
	}
	
	
}
