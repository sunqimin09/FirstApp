package com.sun.developerdown.dao;

import java.util.List;

import com.sun.developerdown.bean.DownLoadEntity;

public interface IDownLoadDao {

	/**添加信息*/
	public int add(DownLoadEntity loader) throws DaoException;
	
	public int del(DownLoadEntity loader) throws DaoException;
	
	public int update(DownLoadEntity loader) throws DaoException;
	
	public List queryAll() throws DaoException;
	
}
