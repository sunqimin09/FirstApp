package com.sun.appdianpinghair.entity;

import java.util.List;

/**
 * 
 * @author sunqm
 * Create at:   2014-6-21 下午4:56:15 
 * TODO   商户信息 实体类
 */
public class BusinessEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	/**商户名*/
	public String name;
	/**分店名*/
	public String branch_name;
	/**地址	*/
	public String address;
	/**带区号的电话*/
	public String telephone;
	
	public String city;
	
	public List<String> region;
	
	public List<String> categories;
	/**纬度坐标*/
	public double latitude;
	/**经度坐标*/
	public double longitude;
	/**星级评论*/
	public float avg_rating;
	/**评分图标*/
	public String rating_img_url;
	
	
	/**产品评价*/
	public int product_grade;
	/**环境评价*/
	public int decoration_grade;
	/**服务评价*/
	public int service_grade;
	/**产品分*/
	public float product_score;
	/**环境分*/
	public float decoration_scrore;
	/**服务分*/
	public float service_score;
	/**人均价格*/
	public int avg_price;
	/**点评数量*/
	public int review_count;
	/**商户与参数的距离，单位一米*/
	public int distance;
	/**商户页面链接*/
	public String business_url;
	/**照片链接*/
	public String photo_url;
	/**小图片*/
	public String s_photo_url;
	/**是否有优惠券*/
	public int has_coupon;
	/**优惠券id*/
	public int coupon_id;
	/**优惠券描述*/
	public String coupon_description;
	/**优惠券页面链接*/
	public String coupon_url;
	/**是否有团购*/
	public int has_deal;
	/**商户当前在线数量*/
	public int deal_count ;
	/**团购列表*/
	public List<DealsEntity> deals;
	/**是否有在线预订，0:没有，1:有*/
	public int has_online_reservation;
	/**在线预订页面链接*/
	public String online_reservation_url;
	
	
	public String toString(){
		return "id:"+id+"name:"+name;
	}
	
}
