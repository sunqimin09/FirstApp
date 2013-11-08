package com.tellout.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public List<? extends BaseEntity> getList() {
		return list;
	}

	public void setList(List<? extends BaseEntity> list) {
		this.list = list;
	}

	
	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	/**服务器返回的错误值*/
	protected int code;
	/**错误描述*/
	protected String codeString;
	/**解析得到*/
	protected JSONObject resultObject;

	protected List<? extends BaseEntity> list = new ArrayList<BaseEntity>();
	/**其他参数*/
	protected Map<String,String> map = new HashMap<String,String>();

	
}
