package com.tellout.constant;

public class MConstant {
	
	public final static String HOME_URL = "http://192.168.1.107:8080/TellOut/servlet/";

	public static String USER_ID_VALUE = "";
	
	/** 登录 */
	public final static int REQUEST_CODE_LOGIN_ = 0;
	/** 注册 */
	public final static int REQUEST_CODE_REGIST = 1;
	/** 我的信息 */
	public final static int REQUEST_CODE_GET_SELF_INFOR = 2;
	/**编辑我的信息*/
	public final static int REQUEST_CODE_EDIT_SELF_INFOR = 7;
	/**获得我的排行信息*/
	public final static int REQUEST_CODE_GET_MY_RANK = 8;
	/** 世界排名 */
	public final static int REQUEST_CODE_WORLD_RANK = 3;
	/** 公司排名 */
	public final static int REQUEST_CODE_COMPANY_RANK = 4;
	/** 行业排名 */
	public final static int REQUEST_CODE_INDUSTRY_RANK = 5;
	
	public final static int REQUEST_CODE_REGION_RANK = 6;
	
	/**地区列表*/
	public final static int REQUEST_CODE_REGIONS = 9;
	/**行业列表*/
	public final static int REQUEST_CODE_INDUSTRYS = 10;
	/**公司列表*/
	public final static int REQUEST_CODE_COMPANYS = 11;
	/**吐槽列表*/
	public final static int REQUEST_CODE_TELLOUTS = 12;
	/**评论列表*/
	public final static int REQUEST_CODE_COMMENTS = 13;
	/**新建吐槽*/
	public final static int REQUEST_CODE_NEW_TELLOUT = 14;
	/**新建评论*/
	public final static int REQUEST_CODE_NEW_COMMENT = 15;
	
	
	
	public final static String URL_LOGIN=HOME_URL+"LoginServlet";
	
	public final static String URL_REGIST=HOME_URL+"RegistServlet";
	
	public final static String URL_MYINFOR=HOME_URL+"MyInforServlet";
	
	public final static String URL_WORLD_RANK=HOME_URL+"WorldRankServlet";
	
	public final static String URL_INDUSTRY_RANK=HOME_URL+"";
	
	public final static String URL_COMPANY_RANK=HOME_URL+"";
	
	public final static String URL_EDIT_MYINFOR=HOME_URL+"EditSelfInforServlet";
	
	public final static String URL_REGIONS = HOME_URL+"";
	
	public final static String URL_INDUSTRYS = HOME_URL+"";
	/**公司列表*/
	public final static String URL_COMPANYS = HOME_URL+"";
	/**吐槽列表*/
	public final static String URL_TELLOUTS = HOME_URL+"TelloutsServlet?";
	/**评论列表*/
	public final static String URL_COMMENTS = HOME_URL+"CommentServlet?";
	/**新建吐槽*/
	public final static String URL_NEW_TELLOUT = HOME_URL+"NewTelloutServlet";
	/**新建评论*/
	public final static String URL_NEW_COMMENT = HOME_URL+"NewCommentServlet";
	
	/** 请求参数 */
	public final static String OTHER_PAGE_INDEXT = "pageIndext";
	/**总数*/
	public final static String OTHER_TOTAL_SIZE = "totalSize";
	/**当前应用的下载地址*/
	public static String DOWNLOAD_URL = "";
}
