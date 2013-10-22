package com.sun.interface_;
import java.util.List;

import com.sun.abstract_.ResponseResultEnity;



/**
 * 操作列表数据
 * @author sunqm
 *
 */
public interface InterfaceResponseEntityList {
	
	public void setList(List<? extends ResponseResultEnity> list);
	
	public List<? extends ResponseResultEnity> getList();
	
	
}
