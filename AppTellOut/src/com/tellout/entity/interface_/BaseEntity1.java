package com.tellout.entity.interface_;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json 解析出来的数据结果，即需要显示的结
 * 果
 * 
 * @author sunqm
 *
 */
public class BaseEntity1 implements ErrorInterface,ObjectInterface{

	@Override
	public void setCode(int code) {
		this.code = code;
		
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public void setCodeStr(String str) {
		codeStr = str;
	}

	@Override
	public String getCodeStr() {
		return codeStr;
	}

	@Override
	public void setList(List<Object> list) {
		this.list = list;
		
	}

	@Override
	public List<Object> getList() {
		return list;
	}

	@Override
	public void setParams(Map<String, String> map) {
		this.map = map;
		
	}

	@Override
	public Map<String, String> getParams() {
		return map;
	} 
	
	private int code ;
	
	private String codeStr;
	
	private List<Object> list = new ArrayList<Object>();

	private Map<String,String> map = new HashMap<String,String>();
	
}
