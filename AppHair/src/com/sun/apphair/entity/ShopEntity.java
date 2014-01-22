/**
 * 
 */
package com.sun.apphair.entity;

import java.io.Serializable;

/**
 * 项目名称：Hair
 * 文件名：ShopEntity.java  
 * 作者：@sunqm    
 * 创建时间：2014-1-18 下午2:22:22
 * 功能描述:  
 * 版本 V 1.0               
 */
public class ShopEntity extends ShowResult implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int id;
	
	public String name;
	
	public String logoUrl;
	
	public float price;
	
	public String address;
	
	public String phone;
	
	public int distance ;
	
	/**得分--几星*/
	public float ratingbarScore;
	/**好评数*/
	public int good_count;
	/**差评数*/
	public int bad_count;
	
	/**经纬度*/
	public double latitude;
	
	public double longitude; 
	

	public String toString() {
		return "ID:" + id + "Name:" + name + "LogoUrl" + logoUrl + "Price:"
				+ price +"Address:"+address+"Phone:"+phone+"Distance:"+distance+
				"Latitue:" + latitude + "Longitude:" + longitude
				+ "Score:" + ratingbarScore;
	}

}
