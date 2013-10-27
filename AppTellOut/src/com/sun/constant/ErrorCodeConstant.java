package com.sun.constant;

/**
 * 错误代码，服务器与客户端保持一致
 * @author sunqm
 *
 */
public class ErrorCodeConstant {
	/**正常*/
	public final static int CODE_SUCCESS = 200;
	/**数据库错误*/
	public final static int CODE_SQL_EXCEPTION = -101;
	/**链接超时*/
	public final static int CODE_TIME_OUT = -102;
	/**数据解析错误*/
	public final static int CODE_JSON_EXCEPTION = -103;
	/**未知异常*/
	public final static int CODE_FAILED = -104;
	/**昵称已经存在*/
	public final static int CODE_NAME_EXIT = -105;
	/**邮箱已经注册*/
	public final static int CODE_EMAIL_EXIT = -106;
	/**用户名或密码错误*/
	public final static int CODE_NAME_PWD_ERROR = -107;
	
	/**
	 * 其余错误码大于100，均属于服务器异常
	 */
	
}
