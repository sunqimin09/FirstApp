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
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}
	public String getError_str() {
		return error_str;
	}
	public void setError_str(String error_str) {
		this.error_str = error_str;
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


	
	/**判断返回结果是否正确*/
	private int code =0;
	/**错误码*/
	private int error_code =0;
	
	private String error_str ="";
	
//	private ResultSet rs;
	private List<Map<String,String>> list = new ArrayList<Map<String,String>>();


	private Map<String,String> params = new HashMap<String,String>();

	/**数据结果*/
	private Object result ="";
	
}
