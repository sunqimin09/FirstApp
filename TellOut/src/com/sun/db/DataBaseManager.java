package com.sun.db;

/**
 * 数据库的初始化???
 * @author sunqm
 *
 */
public class DataBaseManager {

	String sql_user = "create table user (" + DbConstant.DB_USER_ID
			+ " int(11) not null primary key auto_increment,"
			+ DbConstant.DB_USER_NICK_NAME + " char(16) not null,"
			+ DbConstant.DB_USER_EMAIL + " char(32) not null, "
			+ DbConstant.DB_USER_SALARY + " int(10) default 0,"
			+ DbConstant.DB_USER_SALARY_PER + " int(3) default 50, "
			+ DbConstant.DB_USER_WELFARE + " int(3) default 0,"
			+ DbConstant.DB_USER_WELFARE_PER + " int(3) default 50, "
			+ DbConstant.DB_USER_SCORE + " int(10) default 0,"
			+ DbConstant.DB_USER_WORK_AGE + " int(2) default 0,"
			+ DbConstant.DB_USER_CREATE_TIME + " Date not null,"
			+ DbConstant.DB_USER_REGION_ID + " int(6) default 0,"
			+ DbConstant.DB_USER_INDUSTRY_ID + " int(6) default 0,"
			+ DbConstant.DB_USER_COMMENT + " text )";
	
	
	public void createTable() {
		
	}

}
