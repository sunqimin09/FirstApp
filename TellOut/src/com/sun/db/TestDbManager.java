package com.sun.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDbManager {

	private static String url = "jdbc:mysql://localhost:3306/tucao";

	private static String DRIVER = "com.mysql.jdbc.Driver";

	private static Connection conn = null;

	private Statement stmt = null;

	private String sql = null;

	/** 数据库用户名、秘密 */
	private static String user_name = "sun";

	private final static String pwd = "sun123";

	private static Connection getConn() {
		try {
			Class.forName(DRIVER);
			System.out.println("----conne-->class");
			conn = DriverManager.getConnection(url, user_name, pwd);
			System.out.println("----conne-->success");
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet doQuery(String sql) throws SQLException {

		if (conn == null) {
			conn = getConn();
		}
		stmt = conn.createStatement();
		System.out.println("sql-Query->" + sql);
		ResultSet rs = stmt.executeQuery(sql);
		return rs;

	}

	public boolean doInsert(String sql) throws SQLException {
		if (conn == null) {
			conn = getConn();
		}
		stmt = conn.createStatement();
		System.out.println("sql-insert->" + sql);
		boolean rs = stmt.execute(sql);
		stmt.close();
		return rs;

	}

	public int doUpdate(String sql) {
		try {
			if (conn == null) {
				conn = DriverManager.getConnection(url, user_name, pwd);
			}
			if (stmt == null) {
				stmt = conn.createStatement();
			}
			System.out.println("sql--update>" + sql);
			int rs = stmt.executeUpdate(sql);
			// if(rs==1)//更新一条记录成功
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
