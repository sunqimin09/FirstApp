package com.tellout.entity;


/***
 * 网络请求的返回实体类，包括请求的返回码，正确或者错误，以及一个jsonObject
 * @author sunqm
 *
 */
public class TestResponseResult {
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getResultStr() {
		return resultStr;
	}
	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}
	
	public String toString(){
		return "resultCode->"+resultCode+"resultStr-->"+resultStr;
	}
	
	/**返回码，标志是否正确，或者错误类型*/
	private int resultCode = 0;
	/**返回 需要的数据*/
	private String resultStr =null;
	
}
