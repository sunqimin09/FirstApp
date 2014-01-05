package com.example.msalary.util;

public class MConstant {
//	115.29.45.6 192.168.1.111
	public final static String URL_HOME_PATH = "http://115.29.45.6:8080/Web/servlet/";
	
	public final static String URL_TEST = "Test";
	/**查询公司*/
	public final static String URL_SELECT_COMPANY = URL_HOME_PATH + "QueryCompanyServlet?";
	/**查询职位*/
	public final static String URL_SELECT_POSITION = URL_HOME_PATH + "QueryJobServlet?";
	
	/**公司详情--该公司的岗位列表*/
	public final static String URL_COMPANY_DETAIL = URL_HOME_PATH + "JobsOfCompanyServlet?";
	/**公司薪资排名*/
	public final static String URL_COMPANY_SALARY_RANK = URL_HOME_PATH + "CompanySalaryRankServlet";
	/**公司评论*/
	public final static String URL_COMPANY_COMMENT =  URL_HOME_PATH +"CompanyCommentsServlet?";
	/**新建评论*/
	public final static String URL_COMPANY_COMMENT_NEW = URL_HOME_PATH + "NewCommentServlet?";
	/**有该职位的所有公司*/
	public final static String URL_COMPANYS_OF_JOB = URL_HOME_PATH + "CompanysOfJobServlet?";
	/**岗位详情*/
	public final static String URL_JOB_DETAIL = URL_HOME_PATH + "JobDetailServlet?";
	/**曝工资*/
	public final static String URL_NEW_JOB = URL_HOME_PATH + "NewSalaryServlet?";
	
}
