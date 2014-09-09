package com.sun.appdianpinghair.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.sun.appdianpinghair.debug.MyDebug;
import com.sun.appdianpinghair.entity.BusinessEntity;
import com.sun.appdianpinghair.entity.DealsEntity;
import com.sun.appdianpinghair.entity.JsonBusinessEntity;

public class JsonBusiness {
	
	public static JsonBusinessEntity parse(String content){
		JsonBusinessEntity businessEntity = new JsonBusinessEntity();
		try {
			JSONObject object = new JSONObject(content);
			String status = object.getString("status");
			if(status.equals("OK")){
				businessEntity.status = status;
				if(object.has("total_count"))
					businessEntity.totalCount = object.getInt("total_count");
				businessEntity.count = object.getInt("count");
				if(object.has("businesses"))
					businessEntity.list = parseBusiness(object.getJSONArray("businesses"));
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
	
	private static List<BusinessEntity> parseBusiness(JSONArray businessArray) throws JSONException{
		MyDebug.Log("array-->"+businessArray);
		List<BusinessEntity> list = new ArrayList<BusinessEntity>();
		BusinessEntity entity;
		JSONObject object;
		for(int i=0;i<businessArray.length();i++){
			object = businessArray.getJSONObject(i);
			entity = new BusinessEntity();
			entity.id = object.getInt("business_id");
			entity.name = object.getString("name");
			entity.branch_name = object.getString("branch_name");
			entity.address = object.getString("address");
			entity.telephone = object.getString("telephone");
			entity.city = object.getString("city");
			entity.region = parseRegions( object.getJSONArray("regions"));
//			object.getJSONArray("categories");
			entity.latitude =  object.getDouble("latitude");
			entity.longitude = object.getDouble("longitude");
			entity.avg_rating = (float) object.getDouble("avg_rating");
//			object.getString("rating_img_url");
//			object.getString("rating_s_img_url");
			entity.product_grade = object.getInt("product_grade");
			entity.decoration_grade = object.getInt("decoration_grade");
			entity.service_grade = object.getInt("service_grade");
			entity.product_score = (float)object.getDouble("product_score");
			entity.decoration_scrore = (float) object.getDouble("decoration_score");
			entity.service_score = (float) object.getDouble("service_score");
			entity.avg_price = object.getInt("avg_price");
			entity.review_count = object.getInt("review_count");
			entity.distance = object.getInt("distance");
			entity.business_url = object.getString("business_url");
			entity.photo_url = object.getString("photo_url");
			entity.s_photo_url = object.getString("s_photo_url");
			entity.has_coupon = object.getInt("has_coupon");
			entity.coupon_id = object.getInt("coupon_id");
			entity.coupon_description = object.getString("coupon_description");
			entity.coupon_url = object.getString("coupon_url");
			entity.has_deal = object.getInt("has_deal");
			entity.deal_count = object.getInt("deal_count");
//			object.getJSONArray("deals");
			entity.deals = parseDeals(object.getJSONArray("deals"));
			entity.has_online_reservation = object.getInt("has_online_reservation");
			entity.online_reservation_url = object.getString("online_reservation_url");
			list.add(entity);
		}
		Log.d("tag","Business--size"+list.size());
		return list;
	}
	
	private static List<String> parseRegions(JSONArray regions) throws JSONException{
		List<String> list = new ArrayList<String>();
		for(int i =0;i<regions.length();i++){
			list.add(regions.getString(i));
		}
		return list;
	}
	
	private static List<DealsEntity> parseDeals(JSONArray deals) throws JSONException{
		List<DealsEntity> list = new ArrayList<DealsEntity>();
		JSONObject object = null;
		DealsEntity entity;
		for(int i =0;i<deals.length();i++){
			object = deals.getJSONObject(i);
			entity = new DealsEntity();
			entity.description = object.getString("description");
			entity.url = object.getString("url");
			entity.id = object.getString("id");
			list.add(entity);
		}
		return list;
	}
	
	/**
	 * [{"deal_count":0,"service_grade":0,"review_count":0,"coupon_id":232494,"business_url":"http:\/\/dpurl.cn\/p\/h8D6onStia","has_online_reservation":0,
	 * "regions":["西城区","丰台区","右安门","陶然亭"],"coupon_description":"加入“真爱敢试团”，免费吃喝玩乐","city":"北京","has_coupon":1,"distance":-1,
	 * "rating_s_img_url":"http:\/\/i3.dpfile.com\/s\/i\/app\/api\/16_0star.png","name":"局气(这是一条测试商户数据，仅用于测试开发，开发完成后请申请正式数据...)",
	 * "business_id":15999991,"coupon_url":"http:\/\/dpurl.cn\/p\/bOk2H7GTJe","photo_list_url":"http:\/\/dpurl.cn\/p\/Y65yEA-0MZ","longitude":116.36794,
	 * "photo_count":3951,"branch_name":"","avg_rating":0,"decoration_grade":0,"rating_img_url":"http:\/\/i3.dpfile.com\/s\/i\/app\/api\/32_0star.png",
	 * "decoration_score":0,"product_score":0,"photo_url":"http:\/\/i3.dpfile.com\/pc\/8a5c5b91ee4a1cc2c70ac04ee556f255(700x700)\/thumb.jpg",
	 * "s_photo_url":"http:\/\/i2.dpfile.com\/pc\/8a5c5b91ee4a1cc2c70ac04ee556f255(278x200)\/thumb.jpg","review_list_url":"http:\/\/dpurl.cn\/p\/+YouBB-gVS",
	 * "deals":[],"product_grade":0,"service_score":0,"address":"西城区半步桥街14号","has_deal":0,"categories":["北京菜"],"latitude":39.8749,"avg_price":0,
	 * "telephone":"010-68080800","online_reservation_url":""},
	 * 
	 * {"deal_count":17,"service_grade":0,"review_count":0,"coupon_id":0,"business_url":"http:\/\/dpurl.cn\/p\/qRH5mLBzTQ",
	 * "has_online_reservation":0,"regions":["朝阳区","十八里店"],"coupon_description":"","city":"北京","has_coupon":0,"distance":-1,"rating_s_img_url":
	 * "http:\/\/i1.dpfile.com\/s\/i\/app\/api\/16_0star.png",
	 * "name":"欢乐谷(这是一条测试商户数据，仅用于测试开发，开发完成后请申请正式数据...)","business_id":1916992,"coupon_url":"","photo_list_url":"http:\/\/dpurl.cn\/p\/PLd0APyZun",
	 * "longitude":116.494255,"photo_count":4952,"branch_name":"","avg_rating":0,"decoration_grade":0,"rating_img_url":"http:\/\/i2.dpfile.com\/s\/i\/app\/api\/32_0star.png",
	 * "decoration_score":0,"product_score":0,"photo_url":"http:\/\/i3.dpfile.com\/2007-10-03\/224372_b.jpg(700x700)\/thumb.jpg","s_photo_url":"http:\/\/i1.dpfile.com\/2007-10-03\/224372_b.jpg(278x200)\/thumb.jpg",
	 * "review_list_url":"http:\/\/dpurl.cn\/p\/7dOcN-xtR1","deals":[{"id":"2-6163402","url":"http:\/\/dpurl.cn\/p\/eO-QeIV1--","description":"北京欢乐谷日场成人门票!仅售215元，价值230元北京欢乐谷日场成人门票，在线预约，开心出游，乐趣纷呈，尽享缤纷假日！"},
	 * {"id":"2-6486713","url":"http:\/\/dpurl.cn\/p\/1IiubVOx25","description":"北京欢乐谷中秋节单人特惠票!仅售165元，价值230元北京欢乐谷中秋节单人特惠票，男女通用，在线预约，方便快捷！相约中秋，不见不散！"},{"id":"2-6340236","url":"http:\/\/dpurl.cn\/p\/GZr4HBhg0y",
	 * "description":"欢乐谷成人门票!仅售215元，价值230元欢乐谷成人门票，日场夜场均可使用，节假日通用，男女通用，追逐快乐无限，尽在欢乐谷！"},
	 * 
	 * {"id":"2-8016697","url":"http:\/\/dpurl.cn\/p\/aQims35HBf","description":"北京欢乐谷!仅售165元，价值230元中秋节单人特惠票，节假日通用！中国唯一大型室内环球漂流“欢乐世界“！"},{"id":"2-8030817","url":"http:\/\/dpurl.cn\/p\/VIDFE8ELHh","description":"北京欢乐谷!仅售138元，价值230元大学生9月特惠票，节假日通用！新生开学季，共享欢乐谷！"},{"id":"2-6371365","url":"http:\/\/dpurl.cn\/p\/CNPyqaxWvh","description":"欢乐谷!仅售215元，价值230元成人日场票，节假日通用！"},{"id":"2-8051690","url":"http:\/\/dpurl.cn\/p\/4QxdChFDqk","description":"北京欢乐谷夜场票!仅售75元，价值100元北京欢乐谷夜场票！提前1天预约！"},{"id":"2-6206360","url":"http:\/\/dpurl.cn\/p\/vOGwv+j8mo","description":"北京  欢乐谷直通车单人票!仅售218元，价值248元北京欢乐谷直通车单人票，节假日通用！"},{"id":"2-6475342","url":"http:\/\/dpurl.cn\/p\/O5lEEgg3is","description":"北京欢乐谷!仅�
	 */

	
	
	
	
	
	
	
	
	
}
