package com.example.msalary.entity;

import java.sql.Date;

public class CompanyEntity extends ShowResult{
	
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public int getJobCount() {
		return jobCount;
	}

	public void setJobCount(int jobCount) {
		this.jobCount = jobCount;
	}
	
	public int getAvgSalary() {
		return avgSalary;
	}

	public void setAvgSalary(int avgSalary) {
		this.avgSalary = avgSalary;
	}
	
	private int id;
	
	private String name;
	
	private Date createDate;
	
	private int locationId;
	
	private int jobCount;

	private int avgSalary;

	
	
}
