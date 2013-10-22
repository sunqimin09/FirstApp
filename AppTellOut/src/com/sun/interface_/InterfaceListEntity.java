package com.sun.interface_;
import java.util.ArrayList;
import java.util.List;

import com.example.entity.BaseEntity;

/**
 * 列表 接口
 * @author sunqm
 *
 */
public interface InterfaceListEntity {
	
	public void setSize(int size);
	
	public int getSize();
	
	public void setEntitys(ArrayList<? extends BaseEntity> list);
	
	public List<? extends BaseEntity> getEntitys();
	
	public int getCurrentMaxIndex();
	
	public void setCurrentMaxIndex(int indext);
	
}
