package com.sun.appdianpinghair.debug;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.sun.appdianpinghair.utils.DemoApiTool;
import com.sun.appdianpinghair.utils.Mconstant;


public class MyDebug {

	Map<String, String> paramMap = new HashMap<String, String>();

	public static String appKey = "4073748977";

	public static String secret = "a6b49517c78446c59d0302fd7551d118";

	public String getBusiness() {
//		 String apiUrl = "http://api.dianping.com/v1/business/find_businesses";

         Map<String, String> paramMap = new HashMap<String, String>();
         paramMap.put("city", "北京");
//         paramMap.put("latitude", "31.21524");
//         paramMap.put("longitude", "121.420033");
//         paramMap.put("category", "美发");
//         paramMap.put("region", "朝阳区");
//         paramMap.put("limit", "20");
//         paramMap.put("radius", "2000");
//         paramMap.put("offset_type", "0");
//         paramMap.put("has_coupon", "1");
//         paramMap.put("has_deal", "1");
//         paramMap.put("keyword", "̩泰国菜");
//         paramMap.put("sort", "7");
         paramMap.put("format", "json");

         String requestResult = DemoApiTool.requestApi(Mconstant.URL_BUSINESS, appKey, secret, paramMap);
         return requestResult;
	}

	public String getTypes(){
		Map<String, String> paramMap = new HashMap<String, String>();
//        paramMap.put("city", "北京");
		String requestResult = DemoApiTool.requestApi(Mconstant.URL_TYPES, appKey, secret, paramMap);
		return requestResult;
	}
	
	public String getDetail(int busnessId){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("business_id", String.valueOf(busnessId));
		paramMap.put("platform", String.valueOf(2));
		
		String requestResult = DemoApiTool.requestApi(Mconstant.URL_TYPES, appKey, secret, paramMap);
		return requestResult;
	}
	
	public static void Log(String str){
		Log.d("tag","mydebug-->"+str);
	}
	
	/**
	 * 跳转到本地点评应用，或者html5
	 */
	private void loacal(){
		String keywords = "";
		try {
		    keywords = URLEncoder.encode("麻辣诱惑", "UTF-8");
		    Uri url = Uri.parse("dianping://shoplist?q=" + keywords);
		    Intent intent = new Intent(Intent.ACTION_VIEW, url);
//		    startActivity(intent);
		} catch (Exception e) {
		    // 没有安装应用，默认打开HTML5站
		    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.dianping.com/search.aspx?skey=" + keywords));
//		    startActivity(intent);
		}

	}
	
	
}
