package cn.com.bjnews.thinker.entity;

import java.io.Serializable;

/**
 * 相关推荐
 * @author sunqm
 * Create at:   2014-5-16 下午2:26:38 
 * TODO
 */
public class RelatedEntity implements Serializable {
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String title;
	
	private String url;
}
