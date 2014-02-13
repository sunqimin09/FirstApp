package com.sun.apphair.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;

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
    
	public List<? extends NameValuePair> postParams = new ArrayList<NameValuePair>();
	
    private String url = null;
    
	public boolean isPost = false;
}
