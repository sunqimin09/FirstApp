package com.sun.hair.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.hair.entity.FamousEntity;
import com.sun.hair.entity.FamousListEntity;

public class FoodService extends BaseService{

	
	
	@Override
	public Object parseDetail(JSONObject result) throws JSONException {
		FamousListEntity show = new FamousListEntity();
		JSONArray array = result.getJSONArray("foods");
		List<FamousEntity> foods = new ArrayList<FamousEntity>();
		FamousEntity entity = null;
		JSONObject item = null;
		for(int i=0;i<array.length();i++){
			item = array.getJSONObject(i);
			entity = new FamousEntity();
			entity.id = item.getInt("id");
			entity.name = item.getString("name");
			entity.introduce = item.getString("introduce");
			entity.looked_num = item.getInt("looked_num");
			entity.ok_num = item.getInt("ok_num");
			entity.photo_url_s = item.getString("photo_url_s");
			entity.photo_url_h = item.getString("photo_url_h");
			foods.add(entity);
		}
		show.list = foods;
		show.total = result.getString("total");
		show.pageIndex =  result.getString("pageIndex");
		return show;
	}


	
}
