package com.tellout.constant;

/**
 * 错误代码，服务器与客户端保持一致
 * @author sunqm
 *
 */
public class ErrorCodeConstant {
	/**正常*/
	public final static int SUCCESS = 200;
	/**数据库错误*/
	public final static int SQL_EXCEPTION = -101;
	/**链接超时*/
	public final static int TIME_OUT = -102;
	/**数据解析错误*/
	public final static int JSON_EXCEPTION = -103;
	/**未知异常*/
	public final static int OTHER = -104;
	/**昵称已经存在*/
	public final static int NAME_EXIT = -105;
	/**邮箱已经注册*/
	public final static int EMAIL_EXIT = -106;
	/**用户名或密码错误*/
	public final static int NAME_PWD_ERROR = -107;
	
	public final static int NO_INTERNET = -10001;
	/**
	 * 其余错误码大于100，均属于服务器异常
	 */
	
}
