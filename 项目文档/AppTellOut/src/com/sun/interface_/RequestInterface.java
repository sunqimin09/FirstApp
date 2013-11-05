package com.sun.interface_;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

/**实现请求的接口*/
public interface RequestInterface {
	
	/**是否为post传输*/
	public void setPost(boolean isPost);
	
	public boolean isPost();
	/**设置地址*/
	public void setUrl(String url);
	
	public String getUrl();
	
	/**设置参数*/
	public void setParams(Map<String,String> params);
	
	/**获得get请求参数*/
	public Map<String,String> getParams();
	
	/**获得post请求参数*/
	public List<NameValuePair> get_post_params();
	
	/**请求type*/
	public void setType(int type);
	
	public int getType();
	
}
