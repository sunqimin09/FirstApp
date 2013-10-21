package com.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.apptellout.BaseActivity;
import com.example.entity.RequestEntity;
import com.example.entity.ResponseEntity;

public class HttpHelper {

	public static boolean isInternetAvaliable(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("error", e.toString());
		}
		return false;
	}

	/**
	 * 网络请求说明 
	 * 1.请求参数:请求的地址，参数map，请求标志id，请求类型
	 * 2.返回参数:数据类型，
	 * 3.
	 * 4.
	 * @param requestEntity 请求参数
	 * @return
	 */
	
	
	
	public static ResponseEntity request(
			RequestEntity requestEntity) {
		return requestGet( requestEntity);
	}
	
	public static ResponseEntity requestGet(
			RequestEntity requestEntity) {
		String url =getUrl(requestEntity.getType());
		if (requestEntity.isHasParams()) {//有参数
			url = url+"?";
			Map<String, String> map = requestEntity.getParams();
			for (Map.Entry<String,String> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				url +=key+"="+value+"&";
			}
			url = url.substring(0, url.length()-1);
		}else{//无参数
			url =requestEntity.getUrl();
		}
		String result = get(MConstant.HOME_URL + url);
		Log.d("tag", "HttpHelper--result>" + result);
		return new JsonHelper().JsonParse(requestEntity.getType(), result);
	}
	
	private static String getUrl(int type){
		String url ="";
		switch(type){
		case MConstant.LOGIN_REQUEST_CODE:
			url = "LoginServlet";
			break;
		case MConstant.REGIST_REQUEST_CODE:
			url = "RegistServlet";
			break;
		case MConstant.MYINFOR_REQUEST_CODE:// 我的个人信息
			url = "MyInforServlet";
			break;
		case MConstant.WORLD_RANK_REQUEST_CODE:// 世界排名
			url = "WorldRankServlet";
			break;
		case MConstant.COMPANY_RANK_REQUEST_CODE:// 企业排名
			url = "CompanyRankServlet";
			break;
		case MConstant.INDUSTRY_RANK_REQUEST_CODE:// 行业排名
			url = "IndustryRankServlet";
			break;
		case MConstant.EDIT_SELFINFOR_REQUEST_CODE://编辑个人信息
			url = "EditSelfInforServlet";
			break;
		}
		return url;
	}

//	public static void request(Activity context, int type) {
//		String childUrl = "";
//		switch (type) {
//		case MConstant.MYINFOR_REQUEST_CODE:// 我的个人信息
//			childUrl = "MyInforServlet?user_id=" + MConstant.USER_ID;
//			break;
//		case MConstant.WORLD_RANK_REQUEST_CODE:// 世界排名
//			childUrl = "?user_id=" + MConstant.USER_ID;
//			break;
//		case MConstant.COMPANY_RANK_REQUEST_CODE:// 企业排名
//			childUrl = "CompanyRankServlet?user_id=" + MConstant.USER_ID;
//			break;
//		case MConstant.INDUSTRY_RANK_REQUEST_CODE:// 行业排名
//			childUrl = "IndustryRankServlet?user_id=" + MConstant.USER_ID;
//			break;
//		}
//		String result = get(MConstant.HOME_URL + childUrl);
//
//	}
//
//	public static void request(BaseActivity context, int type, String strings) {
//		String result = get(MConstant.HOME_URL + strings);
//		Log.d("tag", "HttpHelper--result>" + result);
//		new JsonHelper().JsonParse(context, type, result);
//	}

	/**
	 * 以get方式发送请求，访问web
	 * 
	 * @param uri
	 *            web地址
	 * @return 响应数据
	 */
	public static String get(String uri) {
		BufferedReader reader = null;
		StringBuffer sb = null;
		String result = "";
		HttpClient client = new DefaultHttpClient();
		Log.d("tag", "HttpHelper--url->" + uri);
		HttpGet request = new HttpGet(uri);
		try {
			// 发送请求，得到响应
			HttpResponse response = client.execute(request);

			// 请求成功
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				reader = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
				sb = new StringBuffer();
				String line = "";
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			} else {
				Log.e("tag", "HttpHelper-get-result->"
						+ response.getStatusLine().getStatusCode());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != reader) {
					reader.close();
					reader = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (null != sb) {
			result = sb.toString();
		}
		return result;
	}

}
