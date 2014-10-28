package com.sun.hair.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.sun.hair.entity.BusinessEntity;
import com.sun.hair.entity.DealsEntity;
import com.sun.hair.entity.JsonBusinessEntity;

/**
 * 
 * @author sunqm
 *
 */
public class ShopService2 extends BaseService{

	@Override
	public Object parse(String result) {
		
		JsonBusinessEntity businessEntity = new JsonBusinessEntity();
		try {
			JSONObject object = new JSONObject(result);
			String status = object.getString("status");
			if(status.equals("OK")){
				businessEntity.status = status;
//				if(object.has("total_count"))
//					businessEntity.totalCount = object.getInt("total_count");
//				businessEntity.count = object.getInt("count");
				Log.d("tag","parse-->2"+businessEntity.status);
				if(object.has("businesses"))
					return parseBusiness(object.getJSONArray("businesses"));
			}else{
				businessEntity.status = status;
			}
			Log.d("tag","parse-->"+businessEntity.status);
		} catch (JSONException e) {
			businessEntity.status = "数据错误";
			e.printStackTrace();
		}
		return businessEntity;
	}

	private BusinessEntity parseBusiness(JSONArray jsonArray) throws JSONException {
		if (jsonArray.length() > 0) {
			JSONObject object = jsonArray.getJSONObject(0);
			BusinessEntity entity = new BusinessEntity();
			entity.id = object.getInt("business_id");
			entity.name = object.getString("name");
			entity.branch_name = object.getString("branch_name");
			entity.address = object.getString("address");
			entity.telephone = object.getString("telephone");
			entity.city = object.getString("city");
//			entity.region = parseRegions(object.getJSONArray("regions"));
			// object.getJSONArray("categories");
			entity.latitude = object.getDouble("latitude");
			entity.longitude = object.getDouble("longitude");
			entity.avg_rating = (float) object.getDouble("avg_rating");
			entity.rating_img_url = object.getString("rating_img_url");
			// object.getString("rating_s_img_url");
			entity.product_grade = object.getInt("product_grade");
			entity.decoration_grade = object.getInt("decoration_grade");
			entity.service_grade = object.getInt("service_grade");
			entity.product_score = (float) object.getDouble("product_score");
			entity.decoration_scrore = (float) object
					.getDouble("decoration_score");
			entity.service_score = (float) object.getDouble("service_score");
			entity.avg_price = object.getInt("avg_price");
			entity.review_count = object.getInt("review_count");
//			entity.distance = object.getInt("distance");
			entity.business_url = object.getString("business_url");
			entity.photo_url = object.getString("photo_url");
			entity.s_photo_url = object.getString("s_photo_url");
			entity.has_coupon = object.getInt("has_coupon");
			entity.coupon_id = object.getInt("coupon_id");
			entity.coupon_description = object.getString("coupon_description");
			entity.coupon_url = object.getString("coupon_url");
			entity.has_deal = object.getInt("has_deal");
			entity.deal_count = object.getInt("deal_count");
			// object.getJSONArray("deals");
			entity.deals = parseDeals(object.getJSONArray("deals"));
			entity.has_online_reservation = object
					.getInt("has_online_reservation");
			entity.online_reservation_url = object
					.getString("online_reservation_url");
			return entity;
		}
		return null;
	}
/**
 *{""addr"三里屯"],"categories":["美发"],"latitude":39.93327,"longitude":116.45379,"avg_rating":5.0,
 *"rating_img_url":"http://i3.dpfile.com/s/i/app/api/32_5star.png","rating_s_img_url":
 *"http://i3.dpfile.com/s/i/app/api/16_5star.png","product_grade":5,"decoration_grade":5,"service_grade":5,"product_score"
 *:9.3,"decoration_score":9.0,"service_score":9.3,"review_count":831,"review_list_url":"http://dpurl.cn/p/Lm4dbMGRmE",
 *"avg_price":236,"business_url":"http://dpurl.cn/p/rmNdn08jnc","photo_url":
 *"http://i3.dpfile.com/pc/fa8dc9acf2153e7be0a054cabc7b82ba(700x700)/thumb.jpg","s_photo_url":
 *"http://i1.dpfile.com/pc/fa8dc9acf2153e7be0a054cabc7b82ba(278x200)/thumb.jpg","photo_count":1202,"photo_list_url":
 *"http://dpurl.cn/p/O0F-rZ0Sfx","has_coupon":1,"coupon_id":235660,"coupon_description":"年末折扣季 全项目优惠 四店通用","coupon_url"
 *:"http://dpurl.cn/p/uc7gGERnAd","has_deal":1,"deal_count":1,
 *"deals":[{"id":"2-6179008","description":"尚·丹尼造型!仅售198元，价值300元终身85折会员卡1张，提供免费wifi，男女通用！
 *精湛的技艺，口碑的美发机构，让您享受贴心服务的同时更能享受超值折上折！","url":"http://dpurl.cn/p/XFozXVwWzI"}],"has_online_reservation":0,"online_reservation_url":""}]}

 * @param jsonArray
 * @return
 * @throws JSONException 
 */
	private List<DealsEntity> parseDeals(JSONArray jsonArray) throws JSONException {
		List<DealsEntity> deals = new ArrayList<DealsEntity>();
		DealsEntity entity = new DealsEntity();
		JSONObject item = null;
		for(int i=0;i<jsonArray.length();i++){
			item = jsonArray.getJSONObject(i);
			entity = new DealsEntity();
			entity.id = item.getString("id");
			entity.description = item.getString("description");
			entity.url = item.getString("url");
			deals.add(entity);
		}
		
		return deals;
	}
	
}
