package com.example.msalary.entity;

import java.util.HashMap;

import com.example.msalary.R;

import android.content.Context;

public class RequestEntity {
	
	public RequestEntity(Context context,String url){
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public int requestCode = 0;
	
	public HashMap<String,Object> params = new HashMap<String,Object>();
    
    private String url = null;
    
	public boolean isPost = false;
}
