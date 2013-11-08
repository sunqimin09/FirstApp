package com.tellout.entity;

import java.io.Serializable;

/**
 * 吐槽内容
 * @author sunqm
 *
 */
public class TellOutEntity extends BaseEntity implements Serializable{
	
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public int getTellOutId() {
		return tellOutId;
	}
	public void setTellOutId(int tellOutId) {
		this.tellOutId = tellOutId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	public int getOkNum() {
		return okNum;
	}
	public void setOkNum(int okNum) {
		this.okNum = okNum;
	}
	public int getNoNum() {
		return noNum;
	}
	public void setNoNum(int noNum) {
		this.noNum = noNum;
	}
	
	
	/**作者名*/
	private String authorName = null;
	
	/**id*/
	private int tellOutId = 0;
	
	/**标题*/
	private String title = null;
	
	/**内容*/
	private String content = null;
	
	/**创建时间*/
	private long time;
	/**评论数量*/
	private int commentNum;
	/**赞的数量*/
	private int okNum;
	/**吐的数量*/
	private int noNum;
	
}
