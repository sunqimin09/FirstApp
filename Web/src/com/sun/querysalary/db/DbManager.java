package com.sun.querysalary.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManager {

	private static Connection conn = null;

	private Statement stmt = null;

	public ResultSet doQuery(String sql) throws SQLException {

		stmt = getStatement();
		System.out.println("sql-Query->" + sql);
		ResultSet rs = stmt.executeQuery(sql);
		return rs;

	}

	public boolean doInsert(String sql) throws SQLException {
		stmt = getStatement();
		System.out.println("sql-insert->" + sql);
		boolean rs = stmt.execute(sql);
		stmt.close();
		return rs;

	}

	public int doUpdate(String sql) throws SQLException {
		stmt = getStatement();
		System.out.println("sql--update>" + sql);
		int rs = stmt.executeUpdate(sql);
		// if(rs==1)//更新一条记录成功
		return rs;
	}

	public boolean doDelete(String sql) throws SQLException {
		stmt = getStatement();
		System.out.println("sql--update>" + sql);
		boolean rs = stmt.execute(sql);
		return rs;
	}

	private Statement getStatement() throws SQLException {
		Statement stmt = null;
		if (conn == null || conn.isClosed()) {
			conn = ConnectionManager.getInstance().getConnection();
		}
		if (stmt == null) {
			stmt = conn.createStatement();
		}
		return stmt;

	}
}
