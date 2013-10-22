package com.sun.entity;

public abstract class DbEntity {

	protected int type = 0;

	public int getType() {
		return type;
	}

	public void setType(int mType) {
		this.type = mType;
	}

}
