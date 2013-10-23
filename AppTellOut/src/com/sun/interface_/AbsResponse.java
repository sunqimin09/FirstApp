package com.sun.interface_;


public abstract class AbsResponse implements InterfaceResponse {
	
	public abstract void setCode(int code);
	
	public abstract int getCode();
	
	public abstract void setCodeStr(String codeStr);
	
	public abstract String getCodeStr();
	
	
	int code = 0;
	
	String codeStr = null;
}
