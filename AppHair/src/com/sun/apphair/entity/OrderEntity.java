/**
 * 
 */
package com.sun.apphair.entity;

/**
 * 项目名称：Hair
 * 文件名：OrderEntity.java  
 * 作者：@sunqm    
 * 创建时间：2014-1-19 上午9:22:34
 * 功能描述:  
 * 版本 V 1.0               
 */
public class OrderEntity extends ShowResult{

	public int id;
	
	public int userId;
	
	public int shopId;
	
	public float money;
	
	public int status;
	
	public String toString() {
		return "Id:" + id + "UserId:" + userId + "ShopId:" + shopId + "Money:"
				+ money+"Status:"+status;
	}

}
