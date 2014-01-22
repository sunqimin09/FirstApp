/**
 * 
 */
package com.sun.apphair;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 项目名称：Hair
 * 文件名：ShopDetailAct.java  
 * 作者：@sunqm    
 * 创建时间：2014-1-18 下午3:18:19
 * 功能描述:  
 * 版本 V 1.0               
 */
public class ShopDetailAct extends BaseAct{
	
	
	private TextView tv_name;
	
	private TextView tv_price;
	
	private TextView tv_address;
	
	private TextView tv_phone;
	
	private ImageView img_icon;
	
	private RatingBar ratingBar;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_detail_act);
		initView();
	}
	
	private void initView(){
		
	}
	
	
}
