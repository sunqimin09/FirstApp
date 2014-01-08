package com.example.msalary.util;

public class ErrorCodeUtils {
	
	/**
	 * 将错误码 转化成需要显示的错误提醒字符串
	 * @param resultCode
	 * @return
	 */
	public static String changeCodeToStr(int resultCode) {
		if(resultCode<error.length-1){
			return error[resultCode];
		}else{
			return error[2];
		}
		
	}
	
	private static String[] error = {
		"无网络连接，请检查网络",	
		"数据解析错误",
		"服务器异常"
	};
	
}
