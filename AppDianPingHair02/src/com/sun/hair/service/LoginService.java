package com.sun.hair.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.hair.entity.UserEntity;

public class LoginService extends BaseService{

	@Override
	public Object parseDetail(JSONObject result) throws JSONException {
		UserEntity entity = new UserEntity();
		entity.setId(result.getInt("id"));
		entity.setSign("sign");
		return entity;
	}
	
	
}
