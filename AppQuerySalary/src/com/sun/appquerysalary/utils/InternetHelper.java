package com.sun.appquerysalary.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

import com.sun.appquerysalary.entity.RequestEntity;
import com.sun.appquerysalary.entity.ResponseResult;
import com.sun.appquerysalary.entity.ShowResult;
import com.sun.appquerysalary.interface_.AbsRequestCallBack;
import com.sun.appquerysalary.interface_.IRequestCallBack;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class InternetHelper {
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
	
	public static void requestThread(final RequestEntity requestEntity,final IRequestCallBack requestCallBack){
		new Thread(){

			@Override
			public void run() {
				ResponseResult responseResult = InternetHelper.request(requestEntity);
				if(responseResult.resultCode==HttpStatus.SC_OK){//成功
//					Jsonpar
					ShowResult showResult = null;
					if(showResult.resultCode==HttpStatus.SC_OK){//数据解析成功
						requestCallBack.requestSuccess(showResult);
						return ;//正确
					}else{
						//失败
						requestCallBack.requestFailedStr(ErrorCodeUtils.changeCodeToStr(showResult.resultCode));
					}
					
				}else{
					//失败
					requestCallBack.requestFailedStr(ErrorCodeUtils.changeCodeToStr(responseResult.resultCode));
				}
				
				
			}
			
		}.start();
	}
	
	/**
	 * 网络请求方法，
	 * @param requestEntity 请求参数
	 * @return	 请求结果
	 */
	private static ResponseResult request(RequestEntity requestEntity){
//		if(requestEntity.isPost){
//			
//		}
//			return doPost(requestEntity);
//		else
			return doGet(requestEntity);
	}

	
	
	/**
	 * Get 请求参数方法
	 * @param requestEntity
	 */
	private static ResponseResult doGet(RequestEntity requestEntity) {
		//获得请求的全地址
		String url =requestEntity.url;
		if(!requestEntity.params.isEmpty()){//如果存在参数
			Map<String, Object> map = requestEntity.params;
			for (Map.Entry<String,Object> entry : map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				try {
					if(value!=null){
						value = URLEncoder.encode(String.valueOf(value),"UTF-8");
//						value = new String(value.getBytes(),"utf-8");
						Log.d("tag","value-->"+value);
					}
						
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				url +=key+"="+value+"&";
			}
			url = url.substring(0,url.length()-1);
		}
		url= url.replaceAll(" ", "%20");
		//获得请求的地址
		Log.d("tag","request--url"+url);
		ResponseResult responseResult = get(url);
		Log.d("tag","request--result||>"+responseResult.toString());
		return responseResult;
	}

	/**
	 * 根据url请求数据
	 * @param url
	 * @return	 
	 */
	private static ResponseResult get(String url){
		ResponseResult  responseResult = new ResponseResult();
		BufferedReader reader = null;
		StringBuffer sb = null;
		String result = "";
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		try {
			/**请求超时  20秒*/
			request.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20*1000);
//			request.getParams().setParameter(Charset.defaultCharset(), arg1)
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
			responseResult.resultCode=(response.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			responseResult.resultCode=(-1);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("tag", "HttpHelper-get-result-exception->"
					+ e);
			responseResult.resultCode=(-2);
		} finally {
			try {
				if (null != reader) {
					reader.close();
					reader = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
				responseResult.resultCode=(-2);
			}
			client.getConnectionManager().shutdown();
		}
		if (null != sb) {
			result = sb.toString();
		}
		responseResult.resultStr=(result);
		return responseResult ;
	}
	
	/**
	 * Post请求参数方法
	 * @param requestEntity
	 */
//	@SuppressWarnings("finally")
//	private static ResponseResult doPost(RequestEntity requestEntity) {
//		ResponseResult  responseResult = new ResponseResult();
//
//		Log.d("tag", "Internet-post-url>" + requestEntity.url);
//		HttpPost httpPost = null;
//		try {
//			httpPost = new HttpPost(requestEntity.url);
//			// 设置请求超时,20秒
//			httpPost.getParams().setParameter(
//					CoreConnectionPNames.CONNECTION_TIMEOUT,
//					20*1000);
//				
//			HttpResponse httpResponse = null;
//			/**设置请求参数*/
//			httpPost.setEntity(new UrlEncodedFormEntity(requestEntity.get_post_params(),
//					HTTP.UTF_8));
//			httpResponse = (HttpResponse) new DefaultHttpClient()
//					.execute(httpPost);
//			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 请求到结果
//				// 第三步，使用getEntity方法活得返回结果
//				
//				responseResult.setResultStr(EntityUtils.toString(
//						httpResponse.getEntity(), "utf-8"));
//			} else {
//				Log.e("tag", "Internet-post-response->"
//						+ httpResponse.getStatusLine().getStatusCode());
//			}
//			responseResult.setResultCode(httpResponse.getStatusLine().getStatusCode());
//		}  catch (org.apache.http.conn.ConnectTimeoutException e) {
//			responseResult.setResultCode(0);
//			e.printStackTrace();
//			Log.i("tag", "Internet-请求code-4>" + e);
//		}  catch (Exception e) {
//			responseResult.setResultCode(1);
//			e.printStackTrace();
//			Log.i("tag", "Internet-请求code-6>" + e);
//		}
//
//		finally {
////			client.getConnectionManager().shutdown();
//			Log.i("tag", "Internet-post-  end..." + responseResult.toString());
//			return responseResult;
//		}
//	}
	

}
