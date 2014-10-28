package com.sun.hair.utils;

public class MConstant {
	
	
	public static double longi = 0;
	

	public static double la= 0;
	
	/**成功*/
	public final static int CODE_SUCCESS = 0;
	/**失败*/
	public final static int CODE_FAILED = 1;
	
	/**大众点评key*/
	public static final String KEY = "4073748977";
	/**大众点评*/
	public static final String SECRET = "a6b49517c78446c59d0302fd7551d118";
	
	public static final String YOUMI_KEY = "85ddcc09249cd722";
	
	public static final String YOUMI_SECRET = "a20ebbb68158be6f";
	
	private static final String URL_HOME = "http://api.dianping.com/v1/";
	
	/**商户*/
	public static final String URL_BUSINESS = URL_HOME +"business/find_businesses";
	
	/**种类*/
	public static final String URL_TYPES = URL_HOME + "metadata/get_categories_with_businesses";
	
	/**商家详情*/
	public static final String URL_BUSINESS_DETAIL = URL_HOME + "business/get_single_business";
	/**评论*/
	public static final String URL_BUSINESS_REVIEW = URL_HOME + "review/get_recent_reviews";
	/**区域*/
	public static final String URL_REGION = URL_HOME + "metadata/get_regions_with_businesses";
	/**城市*/
	public static final String URL_CITYS = URL_HOME + "metadata/get_cities_with_businesses";
	
	
	/**我的服务器地址*/
	public static final String URL_HOME_MY = "http://192.168.0.109:8080/ChiHuoWeb/servlet/";
	
	/**名人--列表*/
	public static final String URL_FAMOUSES = URL_HOME_MY + "FoodListServlet";
	
	/**评论--列表*/
	public static final String URL_COMMENTS = URL_HOME_MY + "CommentsServlet";
	
//	
	
}
