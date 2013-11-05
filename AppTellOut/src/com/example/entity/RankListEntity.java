package com.example.entity;

import java.util.ArrayList;
import java.util.List;

import com.sun.interface_.InterfaceListEntity;

/**
 * 排名实体类
 * @author sunqm
 *
 */
public class RankListEntity extends BaseEntity implements InterfaceListEntity{
	

	@Override
	public void setSize(int size) {
		totalCount = size;
	}

	@Override
	public int getSize() {
		return totalCount;
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public void setEntitys(ArrayList<? extends BaseEntity> list) {
		users = (List<UserEntity>) list;
	}

	@Override
	public List<? extends BaseEntity> getEntitys() {
		return users;
	}

	@Override
	public int getCurrentMaxIndex() {
		return currentIndex;
	}

	@Override
	public void setCurrentMaxIndex(int indext) {
		currentIndex = indext;
	}
	
	/**实体类列表*/
	private List<UserEntity> users = new ArrayList<UserEntity>();
	/**当前显示的最大的编号*/
	private int currentIndex = 0;
	/**数据总数*/
	private int totalCount = 0;
	

	
}
