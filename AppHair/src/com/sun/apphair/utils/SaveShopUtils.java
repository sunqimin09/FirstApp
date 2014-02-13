/**
 * 
 */
package com.sun.apphair.utils;

import java.util.ArrayList;

import android.content.Context;

import com.baidu.mapapi.search.MKPoiInfo;
import com.sun.apphair.entity.RequestEntity;
import com.sun.apphair.entity.ResponseResult;
import com.sun.apphair.entity.ShopEntity;
import com.sun.apphair.internet.IRequestCallBack;
import com.sun.apphair.internet.InternetHelper;

/**
 * 项目名称：营销移动智能工作平台 <br>
 * 文件名：TerminalDetailsFragment.java <br>
 * 作者：@sunqm    <br>
 * 创建时间：2014-2-13 上午10:47:38
 * 功能描述: 终端详情 <br>
 * 版本 V 1.0 <br>               
 */
public class SaveShopUtils {

	private Context context;
	
	public SaveShopUtils(Context context){
		this.context = context;
	}
	
	/**
	 * 查询到10条数据，将十条数据打包，上传服务器，
	 * 如果成功，继续上传另外10条，
	 * 
	 * @param entity
	 * @param context
	 */
	public void save(ArrayList<MKPoiInfo> entitys,IRequestCallBack callBack){
		RequestEntity requestEntity = new RequestEntity(context,
				Mconstant.URL_SHOP_SAVE);
		ArrayList<ShopEntity> list = new ArrayList<ShopEntity>();
		ShopEntity shopEntity = null;
		for(MKPoiInfo entity:entitys){
			shopEntity = new ShopEntity();
			shopEntity.address = entity.address;
			shopEntity.city = entity.city;
			shopEntity.phone = entity.phoneNum;
			shopEntity.name = entity.name;
			shopEntity.latitude = entity.pt.getLatitudeE6();
			shopEntity.longitude = entity.pt.getLongitudeE6();
			list.add(shopEntity);
			requestEntity.postParams = list;
		}
		requestEntity.isPost = true;
		new InternetHelper(context).requestThread(requestEntity, callBack);
	}
	
}
