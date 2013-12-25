package com.sun.querysalary.bean;

import java.sql.Date;

public class JobBean {
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getStartWorkDate() {
		return startWorkDate;
	}

	public void setStartWorkDate(Date startWorkDate) {
		this.startWorkDate = startWorkDate;
	}

	public int getCompanyCount() {
		return companyCount;
	}

	public void setCompanyCount(int companyCount) {
		this.companyCount = companyCount;
	}
	
	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	private int id;
	
	private String name;

	private int companyId;
	
	private int companyCount;
	
	private int salary;

	private Date createDate;
	
	private int userId;
	
	private Date startWorkDate;
	
	
}
