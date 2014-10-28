package com.sun.hair.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.hair.entity.CommentEntity;

/**
 * ÆÀÂÛ½âÎö
 * @author sunqm
 *
 */
public class CommentsService extends BaseService{

	@Override
	public Object parseDetail(JSONObject result) throws JSONException {
		List<CommentEntity> comments = new ArrayList<CommentEntity>();
		JSONArray jsonArray = result.getJSONArray("comments");
		JSONObject item = null;
		CommentEntity entity = null;
		for(int i=0;i<jsonArray.length();i++){
			item = jsonArray.getJSONObject(i);
			entity = new CommentEntity();
			entity.id = item.getString("id");
			entity.nickName = item.getString("nickname");
			entity.imgUrl = item.getString("imgurl");
			entity.time = item.getString("tiem");
			entity.content = item.getString("content");
			comments.add(entity);
		}
		return comments;
	}

	
	
}
