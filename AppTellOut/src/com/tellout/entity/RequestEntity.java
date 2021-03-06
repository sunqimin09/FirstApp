package com.tellout.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.tellout.constant.MConstant;

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
		setUrl(requestType);
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
	
	private void setUrl(int requestCode){
		switch(requestCode){
		case MConstant.REQUEST_CODE_LOGIN_://登录
			this.url = MConstant.URL_LOGIN;
			break;
		case MConstant.REQUEST_CODE_REGIST:
			this.url = MConstant.URL_REGIST;
			break;
		case MConstant.REQUEST_CODE_GET_MY_RANK:
			this.url = MConstant.URL_MYINFOR;
			break;
		case MConstant.REQUEST_CODE_GET_SELF_INFOR://获得个人信息
			this.url = MConstant.URL_GET_SELF_INFOR;
			break;
		case MConstant.REQUEST_CODE_EDIT_SELF_INFOR:
			this.url = MConstant.URL_EDIT_MYINFOR;
			break;
		case MConstant.REQUEST_CODE_WORLD_RANK:
			this.url = MConstant.URL_WORLD_RANK;
			break;
		case MConstant.REQUEST_CODE_REGIONS://地区列表
			this.url = MConstant.URL_REGIONS;
			break;
		case MConstant.REQUEST_CODE_INDUSTRYS://行业列表
			this.url = MConstant.URL_INDUSTRYS;
			break;
		case MConstant.REQUEST_CODE_COMPANYS://公司列表
			this.url = MConstant.URL_COMPANYS;
			break;
		case MConstant.REQUEST_CODE_TELLOUTS://吐槽列表
			this.url = MConstant.URL_TELLOUTS;
			break;
		case MConstant.REQUEST_CODE_COMMENTS://评论列表
			this.url = MConstant.URL_COMMENTS;
			break;
		case MConstant.REQUEST_CODE_NEW_TELLOUT://新建吐槽
			this.url = MConstant.URL_NEW_TELLOUT;
			break;
		case MConstant.REQUEST_CODE_NEW_COMMENT://新建评论
			this.url = MConstant.URL_NEW_COMMENT;
			break;
		case MConstant.REQUEST_CODE_OK:
			this.url = MConstant.URL_OK;
			break;
		case MConstant.REQUEST_CODE_MY_HISTORY_INFOR://我的历史信息
			this.url = MConstant.URL_MY_HISTORY_INFOR;
			break;
		}
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
