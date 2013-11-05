package com.example.util;

/***
 * 请求变量
 * @author sunqm
 *
 */
public class GlobalRequestConfig {
	//请求type 参数
	/**登录*/
	public final static int TYPE_LOGIN=0;
	
	/**注册*/
	public final static int TYPE_REGIST=1;
	
	/**我的排名信息*/
	public final static int TYPE_MY_RANK_INFOR=2;
	
	/**获取我曾经的输入信息*/
	public final static int TYPE_MY_INPUTED_INFOR=3;
	
	/**上传更新我的输入信息*/
	public final static int TYPE_MY_INFOR_EDIT=4;
	
	/**世界排名*/
	public final static int TYPE_WORLD_RANK=5;
	
	/**行业排名*/
	public final static int TYPE_INDUSTRY_RANK=6;
	
	/**公司排名*/
	public final static int TYPE_COMPANY_RANK=7;
	
	/**地区排名*/
	public final static int TYPE_REGION_RANK = 8;
	
	//URL
	/**服务器地址*/
	public final static String BASE_URL = "";
	
	/**登录地址*/
	public final static String URL_LOGIN =BASE_URL+"";
	
	/**注册地址*/
	public final static String URL_REGIST =BASE_URL+"";
	
	/**我的排行信息地址*/
	public final static String URL_MY_RANK_INFOR =BASE_URL+"";
	
	/**我曾经输入 的个人信息地址地址*/
	public final static String URL_MY_INPUTED_INFOR =BASE_URL+"";
	
	/**上传个人信息地址地址*/
	public final static String URL_MY_INFOR_EDIT =BASE_URL+"";
	
	/**世界排名地址地址*/
	public final static String URL_WORLD_RANK =BASE_URL+"";
	
	/**行业排名地址地址*/
	public final static String URL_INDUSTRY_RANK =BASE_URL+"";
	
	/**公司地址地址*/
	public final static String URL_COMPANY_RANK =BASE_URL+"";
	
	
	
	
}
