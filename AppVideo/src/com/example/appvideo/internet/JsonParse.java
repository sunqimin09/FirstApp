package com.example.appvideo.internet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;

import com.example.appvideo.InforEntity;


public class JsonParse {
	
	@SuppressLint("NewApi")
	public List<InforEntity> parse(Object t) throws JSONException{
		JSONArray array = new JSONArray(t.toString());
		JSONObject item = null;
		InforEntity entity = null;
		List<InforEntity> list = new ArrayList<InforEntity>();
		for(int i =0;i<array.length();i++){
			entity = new InforEntity();
			item = array.getJSONObject(i);
			entity.id = item.getInt("id");
			entity.name = item.getString("name");
			entity.url = item.getString("url");
			list.add(entity);
		}
		return list;
	}
	
}
