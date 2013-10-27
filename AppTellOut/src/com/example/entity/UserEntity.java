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
	

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getSalaryPer() {
		return salaryPer;
	}

	public void setSalaryPer(String salaryPer) {
		this.salaryPer = salaryPer;
	}

	public String getWelfare() {
		return welfare;
	}

	public void setWelfare(String welfare) {
		this.welfare = welfare;
	}

	public String getWelfarePer() {
		return welfarePer;
	}

	public void setWelfarePer(String welfarePer) {
		this.welfarePer = welfarePer;
	}

	public String getWorkAge() {
		return workAge;
	}

	public void setWorkAge(String workAge) {
		this.workAge = workAge;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	
	private int requestCode;
	
	private int code;
	
	/**用户id*/
	private long id;
	/**用户昵称*/
	private String name = null;
	
	private String workAge = null;
	
	private String salary = null;
	
	private String salaryPer = null;
	
	private String welfare = null;
	
	private String welfarePer = null;
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
	/**世界排名*/
	private String worldRank = null;
	/***/
	
	public String getWorldRank() {
		return worldRank;
	}

	public void setWorldRank(String worldRank) {
		this.worldRank = worldRank;
	}
	
	
}
