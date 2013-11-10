package com.sun.utils;

public class MConstant {

	/** 登录 */
	public final static int REQUEST_LOGIN = 0;
	/** 注册 */
	public final static int REQUEST_REGIST = 1;
	/** 世界排名 */
	public final static int WORLD_RANK = 2;
	/** 企业排名 */
	public final static int COMPANY_RANK = 3;
	/** 行业排名 */
	public final static int INDUSTRY_RANK = 4;

	public final static int MY_INFOR = 5;
	
	public final static int EDIT_MY_INFOR = 6;
	
	public final static int GET_MY_DETAIL = 7;
	/**新建行业*/
	public final static int NEW_INDUSTRY = 8;
	/**新建公司*/
	public final static int NEW_COMPANY = 9;
	
	public final static int CHANGE_PASSWORLD = 10;
	/**请求获得吐槽列表*/
	public final static int REQUEST_TELLOUTS = 11;
	/**请求评论列表*/
	public final static int REQUEST_COMMENTS = 12;
	/**新建吐槽*/
	public final static int REQUEST_NEW_TELLOUTS = 13;
	/**新建评论*/
	public final static int REQUEST_NEW_COMMENTS = 14;
	/**赞*/
	public final static int REQUEST_OK = 15;
	
//	/** 成功 */
//	public final static int SUCCESS = 0;
//	/** 失败 */
//	public final static int FAILED = -1;

	/** 用户已经存在 */
//	public final static int USER_EXIST = -101;
//	
//	public final static int EMAIL_EXIST = -104;

//	public final static int SQL_EXCEPTION = -102;
	
//	public final static int JSON_EXCEAPTION = -103;

	/**正常*/
	public final static int ERROR_SUCCESS = 200;
	/**数据库错误*/
	public final static int ERROR_SQL_EXCEPTION = -101;
	/**链接超时*/
	public final static int ERROR_TIME_OUT = -102;
	/**数据解析错误*/
	public final static int ERROR_JSON_EXCEPTION = -103;
	/**未知异常*/
	public final static int ERROR_OTHER = -104;
	/**昵称已经存在*/
	public final static int ERROR_NAME_EXIT = -105;
	/**邮箱已经注册*/
	public final static int ERROR_EMAIL_EXIT = -106;
	/**用户名或密码错误*/
	public final static int ERROR_NAME_PWD_ERROR = -107;
	
	public final static int ERROR_NO_INTERNET = -10001;
	
	
	
	/** 请求参数 */
	/**请求是否包含标志*/
	public final static String REQUEST_CODE = "request_code";
	
//	public final static String USER_ID = "id";
//	
//	public final static String USER_NAME = "name";
//
//	public final static String USER_PWD = "pwd";
//
//	public final static String USER_EMAIL = "email";

//	public final static String USER_COMPANY_ID = "company_id";
//
//	public final static String USER_INDUSTRY_ID = "industry_id";
//
//	public final static String USER_REGION_ID = "region_id";

	public final static String COMPANY_ID = "company_id";
	
	public final static String INDUSTRY_ID = "industry_id";
	
	public final static String REGION_ID = "region_id";
	/**编辑个人信息*/
	
//	public final static String SALARY = "pay";
//	
//	public final static String SALARY_PER = "pay_percentage";
	
//	public final static String ENVIRONMENT = "environment";
//	
//	public final static String ENVIRONMENT_PER = "environment_percentage";
	
	public final static String FUTURE = "welfare";
	
	public final static String FUTURE_PER = "welfare_percentage";
	/**其他*/
	public final static String OTHER = "other";
	
	/**其他占的比例*/
	public final static String OTHER_PER = "other_percentage";
	
	/**分数*/
	public final static String SCORE = "score";
}
