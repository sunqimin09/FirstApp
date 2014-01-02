package com.example.msalary.entity;


public class JobEntity extends ShowResult{
	//曝光人数量。
	private int exposuer_userNumber;
	private int count1;//0-3000
	private int count2;//3000-6000
	private int count3;//6000-9000
	private int count4;//9000以上
	private int userful_num;//有用数量
	
	public int getCount1() {
		return count1;
	}

	public void setCount1(int count1) {
		this.count1 = count1;
	}

	public int getCount2() {
		return count2;
	}

	public void setCount2(int count2) {
		this.count2 = count2;
	}

	public int getCount3() {
		return count3;
	}

	public void setCount3(int count3) {
		this.count3 = count3;
	}

	public int getCount4() {
		return count4;
	}

	public void setCount4(int count4) {
		this.count4 = count4;
	}

	public int getUserful_num() {
		return userful_num;
	}

	public void setUserful_num(int userful_num) {
		this.userful_num = userful_num;
	}

	public int getExposuer_userNumber() {
		return exposuer_userNumber;
	}

	public void setExposuer_userNumber(int exposuer_userNumber) {
		this.exposuer_userNumber = exposuer_userNumber;
	}

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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getStartWorkDate() {
		return startWorkDate;
	}

	public void setStartWorkDate(String startWorkDate) {
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
	/**有该岗位的公司的数量*/
	private int companyCount;
	
	private int salary;
	/**创建时间*/
	private String createDate;
	/**用户id，暂时没有*/
	private int userId;
	/**开始工作时间*/
	private String startWorkDate;
	
	
}
