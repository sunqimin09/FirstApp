package com.tellout.entity.interface_;

import java.util.List;
import java.util.Map;

public interface ObjectInterface {
	
	public void setList(List<Object> list);
	
	public List<Object> getList();
	
	public void setParams(Map<String,String> map);
	
	public Map<String,String> getParams();
	
	
}
