package com.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.entity.TestRequestEntity;
import com.example.entity.TestResponseResult;

public class TestInternet {
	/**
	 * 检测是否有网络连接
	 * @param context
	 * @return
	 */
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
	 * 网络请求方法，
	 * @param requestEntity 请求参数
	 * @return	 请求结果
	 */
	public static TestResponseResult request(TestRequestEntity requestEntity){
		if(requestEntity.isPost())
			return doPost(requestEntity);
		else
			return doGet(requestEntity);
	}

	
	
	/**
	 * Get 请求参数方法
	 * @param requestEntity
	 */
	private static TestResponseResult doGet(TestRequestEntity requestEntity) {
		//获得请求的全地址
		String url =requestEntity.getUrl();
		if(!requestEntity.getParams().isEmpty()){//如果存在参数
			Map<String, String> map = requestEntity.getParams();
			for (Map.Entry<String,String> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				url +=key+"="+value+"&";
			}
		}
		//获得请求的地址
		Log.d("tag","request--url"+url);
		TestResponseResult responseResult = get(url);
		Log.d("tag","request--result"+responseResult.toString());
		return responseResult;
	}

	/**
	 * 根据url请求数据
	 * @param url
	 * @return	 
	 */
	private static TestResponseResult get(String url){
		TestResponseResult  responseResult = new TestResponseResult();
		BufferedReader reader = null;
		StringBuffer sb = null;
		String result = "";
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		try {
			/**请求超时  20秒*/
			request.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20*1000);
			
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
			responseResult.setResultCode(response.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			responseResult.setResultCode(-1);
		} catch (IOException e) {
			e.printStackTrace();
			responseResult.setResultCode(-2);
		} finally {
			try {
				if (null != reader) {
					reader.close();
					reader = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
				responseResult.setResultCode(-2);
			}
		}
		if (null != sb) {
			result = sb.toString();
		}
		responseResult.setResultStr(result);
		return responseResult ;
	}
	
	/**
	 * Post请求参数方法
	 * @param requestEntity
	 */
	@SuppressWarnings("finally")
	private static TestResponseResult doPost(TestRequestEntity requestEntity) {
		TestResponseResult  responseResult = new TestResponseResult();

		Log.d("tag", "Internet-post-url>" + requestEntity.getUrl());
		try {
			HttpPost httpPost = new HttpPost(requestEntity.getUrl());
			// 设置请求超时,20秒
			httpPost.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT,
					20);
				
			HttpResponse httpResponse = null;
			/**设置请求参数*/
			httpPost.setEntity(new UrlEncodedFormEntity(requestEntity.get_post_params(),
					HTTP.UTF_8));
			httpResponse = (HttpResponse) new DefaultHttpClient()
					.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 请求到结果
				// 第三步，使用getEntity方法活得返回结果
				
				responseResult.setResultStr(EntityUtils.toString(
						httpResponse.getEntity(), "utf-8"));
			} else {
				Log.e("tag", "Internet-post-response->"
						+ httpResponse.getStatusLine().getStatusCode());
			}
			responseResult.setResultCode(httpResponse.getStatusLine().getStatusCode());
		}  catch (org.apache.http.conn.ConnectTimeoutException e) {
			responseResult.setResultCode(0);
			e.printStackTrace();
			Log.i("tag", "Internet-请求code-4>" + e);
		}  catch (Exception e) {
			responseResult.setResultCode(1);
			e.printStackTrace();
			Log.i("tag", "Internet-请求code-6>" + e);
		}

		finally {
			Log.i("tag", "Internet-post-  end..." + responseResult.toString());
			return responseResult;
		}
	}
	

}
