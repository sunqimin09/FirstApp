package com.sun.interface_;

import org.json.JSONObject;

public interface InterfaceResponseJsonResult {
	/**操作 json中 result 的值*/
	public void setJsonResult(JSONObject object);
	
	public JSONObject getJsonResult();
	
	
}
