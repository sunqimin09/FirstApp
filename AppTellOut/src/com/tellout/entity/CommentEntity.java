package com.tellout.entity;

/**
 * 评论
 * @author sunqm
 *
 */
public class CommentEntity extends BaseEntity{
	
	public int getTellOutId() {
		return tellOutId;
	}

	public void setTellOutId(int tellOutId) {
		this.tellOutId = tellOutId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIndext() {
		return indext;
	}

	public void setIndext(int indext) {
		this.indext = indext;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**帖子id*/
	private int tellOutId = 0;
	/**用户名*/
	private String author =null;
	

	/**评论内容*/
	private String content = null;
	/**当前标号*/
	private int indext = 0;
}
