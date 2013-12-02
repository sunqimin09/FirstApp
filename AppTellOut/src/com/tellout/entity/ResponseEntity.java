package com.tellout.entity;

import org.json.JSONObject;


public class ResponseEntity {
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}
	
	public String toString(){
		return "code"+code+"result:"+result;
	}
	
	public int getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	private int requestCode;

	private int code ;
	
	private String url ;

	private JSONObject result;

	
	
}
