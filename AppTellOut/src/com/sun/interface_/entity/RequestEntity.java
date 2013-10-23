package com.sun.interface_.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.sun.interface_.RequestInterface;

public class RequestEntity implements RequestInterface{

	@Override
	public void setPost(boolean isPost) {
		this.isPost = isPost;
	}

	@Override
	public boolean isPost() {
		return isPost;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;	
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public void setParams(Map<String, String> params) {
		
	}

	@Override
	public Map<String, String> getParams() {
		
		return null;
	}

	@Override
	public List<NameValuePair> get_post_params() {
		return null;
	}
	
	@Override
	public void setType(int type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	
	
	/**是否为post类型请求，如果不是则是get请求*/
	private boolean isPost = false;
	
	/**请求的类型：当同一页面存在多个数据请求时设置，以区分更新不同的控件*/
	private int requestType = 0;
	
	/**请求的网络地址*/
	private String  url ="";
	/**请求参数，key..value:分别对应请求的数据*/
	private Map<String,String> params = new HashMap<String,String>();
	

}
