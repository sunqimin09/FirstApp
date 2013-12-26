package com.example.msalary.entity;

import java.util.HashMap;

public class RequestEntity {
	
	public RequestEntity(String url){
		this.url = url;
	}
	
	public HashMap<String,Object> params = new HashMap<String,Object>();
    
    public String url = null;
    
    public boolean isPost = false;
}
