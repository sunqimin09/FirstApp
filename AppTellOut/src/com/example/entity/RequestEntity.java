package com.example.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * 网络请求实体类，包含了一切请求的数据
 * @author sunqm
 *
 */
public class RequestEntity {
	
	public boolean isPost() {
		return isPost;
	}

	public void setPost(boolean isPost) {
		this.isPost = isPost;
	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	/**
	 * 获得post 请求参数
	 * @return
	 */
	public List<NameValuePair> get_post_params() {
		List<NameValuePair> post_params = new ArrayList<NameValuePair>();

		Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = iter.next();
			NameValuePair param = new BasicNameValuePair(entry.getKey(),
					entry.getValue());
			post_params.add(param);
		}
		return post_params;
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
