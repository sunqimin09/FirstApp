package com.sun.appquerysalary.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShowResult implements Serializable{

	
	public int requestCode;
	/**服务器返回的错误值*/
	public int resultCode;

	public List<? extends ShowResult> list = new ArrayList<ShowResult>();
	/**其他参数*/
	public Map<String,String> map = new HashMap<String,String>();
 
	
}
