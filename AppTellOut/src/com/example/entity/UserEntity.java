package com.example.entity;
/**
 * 人
 * @author sunqm
 *
 */
public class UserEntity extends BaseEntity{
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getIndustryId() {
		return industryId;
	}

	public void setIndustryId(int industryId) {
		this.industryId = industryId;
	}

	public String getIndustry_name() {
		return industry_name;
	}

	public void setIndustry_name(String industry_name) {
		this.industry_name = industry_name;
	}
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public int getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}
	
	public int getWorldRank() {
		return worldRank;
	}

	public void setWorldRank(int worldRank) {
		this.worldRank = worldRank;
	}

	public int getRegionRank() {
		return regionRank;
	}

	public void setRegionRank(int regionRank) {
		this.regionRank = regionRank;
	}

	public int getIndustryRank() {
		return industryRank;
	}

	public void setIndustryRank(int industryRank) {
		this.industryRank = industryRank;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	private int requestCode;
	
	private int code;
	
	/**用户id*/
	private long id;
	/**用户昵称*/
	private String name = null;
	/**得分*/
	private int score = 0;
	/**行业id*/
	private int industryId = 0;
	/**行业名字*/
	private String industry_name = null;
	
	private int companyId = 0;
	
	private String company_name = null;
	
	private int regionId = 0;
	/**地区名称*/
	private String region_name = null;
	/**世界内排名*/
	private int worldRank = 0;
	
	private int salary = 0;
	
	

	/**地区内排名*/
	private int regionRank = 0;
	/**行业内排名*/
	private int industryRank = 0;
	
	
}
