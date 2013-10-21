package com.example.entity;

import java.util.HashMap;
import java.util.Map;
public class RequestEntity {

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	public boolean isHasParams() {
		return hasParams;
	}

	public void setHasParams(boolean hasParams) {
		this.hasParams = hasParams;
	}
	
	public String toString() {
		return "Type:" + type  + "params:" + params
				+ "IntStr:" ;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	/**请求id*/
	private int type;
	
	private boolean hasParams;
	
	private String url;

	private Map<String,String> params = new HashMap<String,String>();
	
	
	


//	private List<String> list = new ArrayList<String>();

	
}
