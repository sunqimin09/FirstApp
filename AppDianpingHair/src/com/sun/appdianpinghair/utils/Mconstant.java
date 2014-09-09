package com.sun.appdianpinghair.utils;

public class Mconstant {
	
	private static final String URL_HOME = "http://api.dianping.com/v1/";
	
	/**商户*/
	public static final String URL_BUSINESS = URL_HOME +"business/find_businesses";
	
	/**种类*/
	public static final String URL_TYPES = URL_HOME + "metadata/get_categories_with_businesses";
	
	/**商家详情*/
	public static final String URL_BUSINESS_DETAIL = URL_HOME + "business/get_single_business";
	/**评论*/
	public static final String URL_BUSINESS_REVIEW = URL_HOME + "review/get_recent_reviews";
	
	/**维度*/
	public static double latitude = 0;
	/**经度*/
	public static double longitude = 0;
	
	//?business_id=5429278&appkey=[appkey]&sign=[signature
	
	// http://api.dianping.com/v1/business/get_single_business?business_id=9964442&appkey=[appkey]&sign=[signature]
	
}
