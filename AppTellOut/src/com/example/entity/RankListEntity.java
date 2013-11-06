package com.example.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 排名实体类
 * @author sunqm
 *
 */
public class RankListEntity extends BaseEntity {
	

	public void setSize(int size) {
		totalCount = size;
	}

	public int getSize() {
		return totalCount;
	}

	@SuppressWarnings({"unchecked"})
	public void setEntitys(ArrayList<? extends BaseEntity> list) {
		users = (List<UserEntity>) list;
	}

	public List<? extends BaseEntity> getEntitys() {
		return users;
	}

	public int getCurrentMaxIndex() {
		return currentIndex;
	}

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
