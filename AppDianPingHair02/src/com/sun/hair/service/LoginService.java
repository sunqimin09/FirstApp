package com.sun.hair.service;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginService extends BaseService{

	@Override
	public Object parseDetail(JSONObject result) throws JSONException {
		return result.getInt("id");
	}
	
	
}
