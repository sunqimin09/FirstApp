package com.sun.developerdown.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.developerdown.bean.DownLoadEntity;

/**
 * 实现接口
 * @author sunqm
 *
 */
public class DownLoaderDaoImp implements IDownLoadDao{

	private String TABLE_DOWN = "downtable";
	
	private String DOWN_NAME = "name";
	
	private String DOWN_URL= "url";
	
	private String DOWN_TYPE_ID = "type_id";
	
	
	public int add(DownLoadEntity loader) throws DaoException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int del(DownLoadEntity loader) throws DaoException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int update(DownLoadEntity loader) throws DaoException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List queryAll() throws DaoException {
		String sql = "select * from "+ TABLE_DOWN;
		List<DownLoadEntity> list = new ArrayList<DownLoadEntity>();
		try {
			ResultSet result =null;//= getStatement().executeQuery(sql);
			while(result.next()){
				result.getString(DOWN_NAME);
				result.getString(DOWN_URL);
				result.getInt(DOWN_TYPE_ID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
