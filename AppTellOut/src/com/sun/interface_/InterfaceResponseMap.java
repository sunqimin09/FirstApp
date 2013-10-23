package com.sun.interface_;
import java.util.Map;

/**
 * 操作返回的一些 --非实体类的参数
 * @author sunqm
 *
 */
public interface InterfaceResponseMap {
	
	public void setMap(Map<String,String> map);
	
	public Map<String,String> getMap();
}
