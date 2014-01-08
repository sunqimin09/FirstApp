package com.example.msalary.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
       public static HttpClient httpClient=new DefaultHttpClient();
       public static final String BASE_URL=null;
       public static String getRequest(final String url) throws Exception{
    	   FutureTask<String> task=new FutureTask<String>(new Callable<String>() {

			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				HttpGet get=new HttpGet(url);
				HttpResponse httpResponse=httpClient.execute(get);
				if(httpResponse.getStatusLine().getStatusCode()==200){
					String result=EntityUtils.toString(httpResponse.getEntity());
					return result;
				}
				return null;
			}
		   
    	   });
    	   new Thread(task).start();
    	   return task.get();
       }
     /**
       主要思路

       1、创建HttpPost实例，设置需要请求服务器的url。

       2、为创建的HttpPost实例设置参数，参数设置时使用键值对的方式用到NameValuePair类。

       3、发起post请求获取返回实例HttpResponse

       4、使用EntityUtils对返回值的实体进行处理（可以取得返回的字符串，也可以取得返回的byte数组）
       */
       public static String postRequest(final String url,final Map<String,String> rawParams)throws Exception{
		  FutureTask<String> task=new FutureTask<String>(new Callable<String>() {

			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				HttpPost post=new HttpPost(url);
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				for(String key:rawParams.keySet()){
					//封装请求参数 
					params.add(new BasicNameValuePair(key, rawParams.get(key)));
				}
				///设置请求参数
				post.setEntity(new UrlEncodedFormEntity(params,"gbk"));
				HttpResponse httpResponse=httpClient.execute(post);
				if(httpResponse.getStatusLine().getStatusCode()==200){
					String result=EntityUtils.toString(httpResponse.getEntity());
					return result;
				}
				return null;
			}
		
		  });
    	   new Thread(task).start();
    	   return task.get();    	   
       }
}
