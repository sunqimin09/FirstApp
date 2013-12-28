package com.example.msalary.entity;

import java.util.HashMap;

import com.example.msalary.R;

import android.content.Context;

public class RequestEntity {
	
	public RequestEntity(Context context,String url){
		this.url = url;
	}
	
	public String getUrl() {
		return context.getResources().getString(R.string.url_home)+url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	private Context context =null;
	
	public HashMap<String,Object> params = new HashMap<String,Object>();
    
    private String url = null;
    
	public boolean isPost = false;
}
