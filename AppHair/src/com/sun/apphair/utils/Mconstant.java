/**
 * 
 */
package com.sun.apphair.utils;

/**
 * 项目名称：Hair
 * 文件名：Mconstant.java  
 * 作者：@sunqm    
 * 创建时间：2014-1-18 下午3:57:44
 * 功能描述:  
 * 版本 V 1.0               
 */
public class Mconstant {
	
	/**登录用户名*/
	public static String LoginName = "";
	//115.29.45.6  
	public static final String URL_HOME = "http://192.168.1.108:8080/Hair/servlet/"; 
	/**商铺列表*/
	public static final String URL_SHOPS = URL_HOME + "ShopListServlet?";
	/**商铺详细*/
	public static final String URL_SHOP_DETAIL = URL_HOME + "";
	
	public static final String URL_SHOP_COMMENT = URL_HOME+"CommentsServlet?";

	public static final String ERROR_JSON = "数据错误";
	
	public static final String ERROR_SERVER = "服务器离家出走了";
	
	
	
	
	
}
