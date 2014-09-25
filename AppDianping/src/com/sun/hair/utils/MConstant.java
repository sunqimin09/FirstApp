package com.sun.hair.utils;

public class MConstant {

	/**大众点评key*/
	public static final String KEY = "4073748977";
	/**大众点评*/
	public static final String SECRET = "a6b49517c78446c59d0302fd7551d118";
	
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
	
}
