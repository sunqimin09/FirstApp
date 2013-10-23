package com.sun.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据返回/发送的实体类
 * @author sunqm
 *
 */
public class ResponseEntity {
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public List<Map<String, String>> getList() {
		return list;
	}
	public void setList(List<Map<String, String>> list) {
		this.list = list;
	}

	public String getCode_str() {
		return code_str;
	}
	public void setCode_str(String codeStr) {
		code_str = codeStr;
	}

	
	/**判断返回结果是否正确*/
	private int code =0;
	
	private String code_str = null;
	

	//	private ResultSet rs;
	private List<Map<String,String>> list = new ArrayList<Map<String,String>>();


	private Map<String,String> params = new HashMap<String,String>();

	/**数据结果*/
	private Object result ="";
	
}
