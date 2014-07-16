package cn.com.bjnews.thinker.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import android.app.DownloadManager.Request;

/**
 * 请求 实体类
 * @author sunqm
 * Create at:   2014-5-13 上午7:34:59 
 * TODO
 */
public class RequestEntity {

	public int requestCode;
	
	public boolean isPost;
	
	public String url ;

	/**请求参数*/
	public Map<String,Object> params = new HashMap<String,Object>();
	
	public List<? extends NameValuePair> postParams = new ArrayList<NameValuePair>();
	
	public RequestEntity(String url){
		this.url = url;
	}
	
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
