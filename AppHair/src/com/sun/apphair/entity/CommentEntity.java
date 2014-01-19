/**
 * 
 */
package com.sun.apphair.entity;

/**
 * 项目名称：Hair
 * 文件名：userEntity.java  
 * 作者：@sunqm    
 * 创建时间：2014-1-19 上午9:15:52
 * 功能描述:  
 * 版本 V 1.0               
 */
public class CommentEntity extends ShowResult{
	
	public int id;
	
	public int userId;
	
	public int shopId;
	
	public String content;
	
	/**评分*/
	public float ratingbarScore;
	
	public String other;

	public String toString() {
		return "Id:" + id + "UserId" + userId + "Content:" + content
				+ "RatingBarScore" + ratingbarScore + "other:" + other;
	}
	
}
