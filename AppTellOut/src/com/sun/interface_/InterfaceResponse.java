package com.sun.interface_;


/**返回结果的接口*/
public interface InterfaceResponse {

	public void setCode(int code);
	
	public int getCode();
	
	public void setCodeStr(String codeStr);
	
	public String getCodeStr();
	
	
	int code = 0;
	
	String codeStr = null;
}
