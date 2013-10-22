package com.sun.entity;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 接收数据实体类
 * @author sunqm
 *
 */
public class RequestEntity {
	
	
	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**请求种类，以区分请求的哪个接口*/
	private int typeId;
	
	/**参数*/
	private Map<String,String> params = new HashMap<String,String>();
	
	private HttpServletRequest request;

}
