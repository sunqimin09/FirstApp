/**
 * 
 */
package com.sun.apphair.entity;

import java.io.Serializable;

import org.apache.http.NameValuePair;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 项目名称：Hair
 * 文件名：ShopEntity.java  
 * 作者：@sunqm    
 * 创建时间：2014-1-18 下午2:22:22
 * 功能描述:  
 * 版本 V 1.0               
 */
public class ShopEntity extends ShowResult implements Serializable,NameValuePair{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int id;
	
	public String name;
	
	public String logoUrl;
	
	public float price;
	
	public String address;
	
	public String city;
	
	public String phone;
	
	public int distance ;
	
	/**得分--几星*/
	public float ratingbarScore;
	/**好评数*/
	public int good_count;
	/**差评数*/
	public int bad_count;
	
	/**纬度*/
	public int latitude;//E6
	/**经度*/
	public int longitude; 
	
	
	/**营业时间--新增的*/
	public String shop_hours;
	
	public String toString() {
		return "ID:" + id + "Name:" + name + "LogoUrl" + logoUrl + "Price:"
				+ price +"Address:"+address+"Phone:"+phone+"Distance:"+distance+
				"Latitue:" + latitude + "Longitude:" + longitude
				+ "Score:" + ratingbarScore;
	}


	/* (non-Javadoc)
	 * @see org.apache.http.NameValuePair#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see org.apache.http.NameValuePair#getValue()
	 */
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}


	

}
