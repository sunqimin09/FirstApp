package com.sun.db;

public class DbConstant {
	/**吐槽表*/
	public final static String DB_TABLE_TELLOUTS = "tellouttable";
	/**评论表*/
	public final static String DB_TABLE_COMMENTS = "commenttable";
	
	
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
	/**公司id*/
	public final static String DB_USER_COMPANY_ID = "companyId";
	
	/**行业编号*/
	public final static String DB_USER_INDUSTRY_ID = "industryId";
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
	
	/***吐槽表*/
	public final static String DB_TELLOUT_ID = "telloutId";
	/**内容*/
	public final static String DB_TELLOUT_CONTENT = "telloutContent";
	/**创建时间*/
	public final static String DB_TELLOUT_CREATE_TIME = "telloutCreateTime";
	/**作者*/
	public final static String DB_TELLOUT_USER_ID = DB_USER_ID;
	/**赞的人数*/
	public final static String DB_TELLOUT_OK = "telloutOk";
	/**吐的人数*/
	public final static String DB_TELLOUT_NO = "telloutNo";
	
	/**评论表*/
	
	public final static String DB_COMMENT_ID = "commentId";
	/**被评论内容的id*/
	public final static String DB_COMMENT_TELLOUT_ID = DB_TELLOUT_ID;
	/**评论内容*/
	public final static String DB_COMMENT_CONTENT = "commentContent";
	/**作者*/
	public final static String DB_COMMENT_AUTHOR_ID = DB_USER_ID;
	/**评论时间*/
	public final static String DB_COMMENT_CREATE_TIME = "commentCreateTime";
	
	
	/**********以下是固定的---非数据库字段*/
	public final static String COMMENT_NUM = "commentNum";
	/**页码*/
	public final static String OTHER_PAGE_INDEXT = "pageIndext";

	public final static String OTHER_TOTAL_SIZE = "totalSize";

	
	
}
