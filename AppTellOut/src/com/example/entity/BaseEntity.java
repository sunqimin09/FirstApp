package com.example.entity;

import org.json.JSONObject;

/**
 * 所有实体类的父类
 * @author sunqm
 *
 */
public class BaseEntity {
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public String getCodeString() {
		return codeString;
	}

	public void setCodeString(String codeString) {
		this.codeString = codeString;
	}

	public JSONObject getResultObject() {
		return resultObject;
	}

	public void setResultObject(JSONObject resultObject) {
		this.resultObject = resultObject;
	}
	
	/**服务器返回的错误值*/
	protected int code;
	/**错误描述*/
	protected String codeString;
	/**解析得到*/
	protected JSONObject resultObject;

	
	
	
}
