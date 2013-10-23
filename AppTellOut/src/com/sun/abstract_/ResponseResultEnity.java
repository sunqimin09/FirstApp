package com.sun.abstract_;

import org.json.JSONObject;


public abstract class ResponseResultEnity{
	
	public abstract void setCode(int code);
	
	public abstract int getCode();
	
	public abstract void setCodeStr(String codeStr);
	
	public abstract String getCodeStr();
	
	public abstract JSONObject getJsonObject();
	
	public abstract void setJsonObject(JSONObject object);
	
	int code = 0;
	
	String codeStr = null;
	
	JSONObject object = null;
	
}
