package com.sun.hair.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CityService extends BaseService{

	/**
	 * {
    status: "OK",
    cities: [
        "阿坝",
        "鞍山",
        "安康",
        "安庆",
        "安阳",
        ......
    ]
}
	 */
	@Override
	public Object parse(String result) {
		List<String> citys = new ArrayList<String>();
		JSONObject o;
		try {
			o = new JSONObject(result);
			if(o.getString("status").equals("OK")){
				JSONArray citysj = o.getJSONArray("cities");
				for(int i=0;i<citysj.length();i++){
					citys.add(citysj.getString(i));
				}
				return citys;
			}else{
				return"数据错误";
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return"数据错误";
		}
	}

	
}
