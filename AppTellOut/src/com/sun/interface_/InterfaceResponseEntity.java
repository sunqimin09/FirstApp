package com.sun.interface_;

import com.sun.abstract_.ResponseResultEnity;


/**
 * 操作返回的某一个实体类
 * @author sunqm
 *
 */
public interface InterfaceResponseEntity {
	
	public void setEntity(Class<? extends ResponseResultEnity> a);
	
	public Class<? extends ResponseResultEnity> getEntity();
	
}
