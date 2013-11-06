package com.sun.constant;

/***
 * 服务端返回的key
 * @author sunqm
 *
 */
public class DbConstant {
	//User 表
	/**用户id*/
	public final static String DB_USER_ID = "userId";
	/**昵称*/
	public final static String DB_USER_NICK_NAME = "nickName";
	
	public final static String DB_USER_PWD = "pwd";
	/**邮箱*/
	public final static String DB_USER_EMAIL = "email";
	/**薪水*/
	public final static String DB_USER_SALARY = "salary";
	/**福利得分*/
	public final static String DB_USER_WELFARE = "welfare";
	/**薪资占比例*/
	public final static String DB_USER_SALARY_PER = "salaryPer";
	/**福利所占比例*/
	public final static String DB_USER_WELFARE_PER = "welfarePer";
	/**得分*/
	public final static String DB_USER_SCORE = "score";
	/**工龄*/
	public final static String DB_USER_WORK_AGE = "workAge";
	/**注册时间*/
	public final static String DB_USER_CREATE_TIME = "createTime";
	/**地区id*/
	public final static String DB_USER_REGION_ID = "regionId";
	/**行业编号*/
	public final static String DB_USER_INDUSTRY_ID = "industryId";
	
	public final static String DB_USER_COMPANY_ID = "companyId";
	/**用户评价*/
	public final static String DB_USER_COMMENT= "comment";
	
	//地区表
	/**地区id*/
	public final static String DB_REGION_ID = DB_USER_REGION_ID;
	/**地区名称*/
	public final static String DB_REGION_NAME = "regionName";
	
	/**行业id*/
	public final static String DB_INDUSTRY_ID = DB_USER_INDUSTRY_ID;
	/**行业名称*/
	public final static String DB_INDUSTRY_NAME = "industryName";
	
	/**公司id*/
	public final static String DB_COMPANY_ID = DB_USER_COMPANY_ID;
	/**公司名称*/
	public final static String DB_COMPANY_NAME = "companyName";
	
	
}
