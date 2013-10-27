package com.example.util;

public class MConstant {
	
	public final static String HOME_URL = "http://192.168.1.108:8080/TellOut/servlet/";

	public static String USER_ID_VALUE = "";
	
	/**成功*/
//	public final static int SUCCESS =0;
	/**失败*/
//	public final static int FAILED = -1;
	
	/**用户已经存在*/
//	public final static int USER_EXIST =-101;
//	
//	public final static int SQL_EXCEPTION = -102;
	
	/** 登录 */
	public final static int REQUEST_CODE_LOGIN_ = 0;
	/** 注册 */
	public final static int REQUEST_CODE_REGIST = 1;
	/** 我的信息 */
	public final static int REQUEST_CODE_MYINFOR = 2;
	/** 世界排名 */
	public final static int REQUEST_CODE_WORLD_RANK = 3;
	/** 公司排名 */
	public final static int REQUEST_CODE_COMPANY_RANK = 4;
	/** 行业排名 */
	public final static int REQUEST_CODE_INDUSTRY_RANK = 5;
	
	public final static int REQUEST_CODE_REGION_RANK = 6;
	/**编辑我的信息*/
	public final static int REQUEST_CODE_EDIT_SELFINFOR = 7;
	/**我的排名*/
	public final static int REQUEST_CODE_MY_RANK_INFOR = 8;
	
	
	
	public final static String URL_LOGIN=HOME_URL+"LoginServlet";
	
	public final static String URL_REGIST=HOME_URL+"RegistServlet";
	
	public final static String URL_GET_MY_INFOR=HOME_URL+"GetSelfInforSerlet?";
	
	public final static String URL_EDIT_MY_INFOR=HOME_URL+"EditSelfInforServlet?";
	
	public final static String URL_GET_MY_RANK =HOME_URL+"MyRankInforServlet?";
	
	public final static String URL_WORLD_RANK=HOME_URL+"WorldRankServlet";
	
	public final static String URL_INDUSTRY_RANK=HOME_URL+"IndustryRankServlet";
	
	public final static String URL_COMPANY_RANK=HOME_URL+"CompanyRankServlet";
	
	
	
	/** 请求参数 */

	public final static String USER_ID = "id";
	
	public final static String USER_NAME = "name";

	public final static String USER_PWD = "pwd";

	public final static String USER_EMAIL = "email";

	public final static String USER_COMPANY_ID = "my_company_id";

	public final static String USER_INDUSTRY_ID = "my_industry_id";

	public final static String USER_REGION_ID = "my_region_id";

	public final static String COMPANY_ID = "company_id";
	
	public final static String INDUSTRY_ID = "industry_id";
	
	public final static String REGION_ID = "region_id";
	/**编辑个人信息*/
	public final static String SALARY = "pay";
	
	public final static String SALARY_PER = "pay_percentage";
	
	public final static String ENVIRONMENT = "environment";
	
	public final static String ENVIRONMENT_PER = "environment_percentage";
	
	public final static String FUTURE = "welfare";
	
	public final static String FUTURE_PER = "welfare_percentage";
	
	public final static String OTHER = "other";
	
	public final static String OTHER_PER = "other_percentage";
	
}
