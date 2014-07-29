package cn.com.bjnews.thinker.internet;

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
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import cn.com.bjnews.thinker.entity.RequestEntity;
import cn.com.bjnews.thinker.entity.ResponseResult;
import cn.com.bjnews.thinker.utils.Mconstant;



public class InternetHelper {

	private Context context = null;

	ProgressDialog progressDialog = null;
	
	public InternetHelper(Context context) {
		this.context = context;
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("数据加载中...");
	}

	public InternetHelper() {
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isInternetAvaliable(Context context) {
		// ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ��?
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// ��ȡ�������ӹ���Ķ���
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// �жϵ�ǰ�����Ƿ��Ѿ�����
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						Log.d("tag","������");
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

	private final static int NO_INTERNET =0 ;
	
	private final static int SUCCESS = 1;
	
	private final static int SERVER_ERROR = 2;
	
	private final static int DATA_ERROR = 3;
	
	Handler handler = new Handler() {

		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case NO_INTERNET:
				requestCallBack.requestFailedStr("网络链接错误");
				break;
			case SUCCESS:
				requestCallBack.requestSuccess((ResponseResult) msg.obj);
				break;
			case SERVER_ERROR://显示服务器异常，返回的错误描述信息
				Log.d("tag","server-error-->");
				requestCallBack
						.requestFailedStr("服务器异常");
				break;
			case DATA_ERROR:// Json解析错误
				requestCallBack.requestFailedStr("数据已经损坏");
				break;
//			case -3://服务器 没响应 等错误
//				requestCallBack.requestFailedStr(Mconstant.ERROR_SERVER);
//				break;
			}
//			progressDialog.dismiss();
		}

	};

	private String getErrorStr(ResponseResult responseResult){
		String errorStr = null;
//		try {
//			JSONObject object = new JSONObject(responseResult.resultStr);
//			errorStr = object.getString("error_str");
//		} catch (JSONException e) {
//			e.printStackTrace();
//			return Mconstant.ERROR_JSON;
//		}
		return errorStr;
		
	}
	
	private IRequestCallBack requestCallBack = null;

	/**
	 * 
	 * @param requestEntity
	 * @param requestCallBack
	 */
	public void requestThread(final RequestEntity requestEntity,
			final IRequestCallBack requestCallBack,final int timeOut) {
//		progressDialog.show();
		this.requestCallBack = requestCallBack;
		if (!isInternetAvaliable(context)) {
			sendMsg(null, NO_INTERNET);
			Log.d("tag", "数据加载中...");
			return;
		}
		new Thread() {

			@Override
			public void run() {
				ResponseResult responseResult = null;
				try {
					responseResult = InternetHelper
							.request(requestEntity,timeOut);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				if (responseResult.resultCode == HttpStatus.SC_OK) {// �ɹ�
					Log.d("tag", "result-->" + responseResult.resultStr);
					JSONObject object;
					try {
						object = new JSONObject(responseResult.resultStr);
						sendMsg(responseResult, SUCCESS);
						
					} catch (JSONException e) {
						sendMsg(responseResult, DATA_ERROR);
						e.printStackTrace();
					}
					// requestCallBack.requestSuccess(responseResult);
				} else {
					sendMsg(responseResult, SERVER_ERROR);
					// requestCallBack.requestFailedStr(ErrorCodeUtils.changeCodeToStr(responseResult.resultCode));
				}

			}

		}.start();
	}

	private void sendMsg(ResponseResult responseResult, int flag) {
		Message msg = handler.obtainMessage();
		msg.what = flag;
		msg.obj = responseResult;
		handler.sendMessage(msg);
	}

	/**
	 * �������󷽷���
	 * 
	 * @param requestEntity
	 *            �������
	 * @return ������
	 * @throws UnsupportedEncodingException 
	 */
	private static ResponseResult request(RequestEntity requestEntity,int timeOut) throws UnsupportedEncodingException {
		 if(!requestEntity.isPost){//get 方法
			 return doGet(requestEntity,timeOut);
		 }
		return doPost(requestEntity);
	}

	/**
	 * Get �������
	 * 
	 * @param requestEntity
	 * @throws UnsupportedEncodingException 
	 */
	private static ResponseResult doGet(RequestEntity requestEntity,int timeOut) throws UnsupportedEncodingException {
		// ��������ȫ��ַ
		Log.d("tag", "request--url-before" + requestEntity.getUrl() + "urlend");
		String url = getUrl(requestEntity.getUrl(), requestEntity.params);
		// �������ĵ�ַ
		Log.d("tag", "request--url" + url + "urlend");
		ResponseResult responseResult = get(url,timeOut);
		responseResult.requestCode = requestEntity.requestCode;
		Log.d("tag", new String(responseResult.toString().getBytes(),"utf-8")+"request--result||>" + responseResult.toString());
		return responseResult;
	}

	/**
	 * ��ϵ�ַ
	 * 
	 * @param url
	 * @param map
	 * @return
	 */
	private static String getUrl(String url, Map<String, Object> map) {
		if (!map.isEmpty()) {// 
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				
				url += key + "=" + value + "&";
			}
			url = url.substring(0, url.length() - 1);
		}
		url = url.replaceAll(" ", "%20");
		return url;
	}

	/**
	 * ���url�������
	 * 
	 * @param url
	 * @return
	 */
	private static ResponseResult get(String url,int timeOut) {
		ResponseResult responseResult = new ResponseResult();
		BufferedReader reader = null;
		StringBuffer sb = null;
		String result = "";
		HttpClient client = new DefaultHttpClient();
		url = url.replace(" ", "20%").replace("|", "");
		
		try {
			HttpGet request = new HttpGet(url);
			/** ����ʱ 20�� */
			request.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, timeOut);
			request.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeOut);
			
//			 request.getParams().setParameter(Charset.defaultCharset(),"gbk");
			// �������󣬵õ���Ӧ
			HttpResponse response = client.execute(request);

			// ����ɹ�
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
			responseResult.resultCode = (response.getStatusLine()
					.getStatusCode());
//http://192.168.1.103:8080/Hair/servlet/SaveShopServlet?longitude=116461931&phone=010-65970579|010-65970578&latitude=39923336&address=北京市朝阳区门外大街６号院１５号楼新城国际１５－１０５&name=ｂａｎｇｓ　ｈａｉｒ　ｓａｌｏ

//				ｎ美发店&city=北京市

			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			responseResult.resultCode = (SERVER_ERROR);
		} catch (Exception e) {
			Log.d("tag", "HttpHelper-get-result->"
					+ url);
			e.printStackTrace();
			Log.e("tag", "HttpHelper-get-result-exception->" + e);
			responseResult.resultCode = (SERVER_ERROR);
		} finally {
			try {
				if (null != reader) {
					reader.close();
					reader = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
				responseResult.resultCode = (SERVER_ERROR);
			}
			client.getConnectionManager().shutdown();
		}
		if (null != sb) {
			result = sb.toString();
		}
		responseResult.resultStr = (result);
		return responseResult;
	}

	/**
	 * Post�������
	 * 
	 * @param requestEntity
	 */
	@SuppressWarnings("finally")
	private static ResponseResult doPost(RequestEntity requestEntity) {
		ResponseResult responseResult = new ResponseResult();

		Log.d("tag", "Internet-post-url>" + requestEntity.getUrl());
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(requestEntity.getUrl());
			// ��������ʱ,20��
			httpPost.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, Mconstant.TIME_OUT);

			HttpResponse httpResponse = null;
			/** ����������� */
			Log.d("tag", "Internet-postParams>" + requestEntity.postParams);
			
			httpPost.setEntity(new UrlEncodedFormEntity(
					requestEntity.postParams, HTTP.UTF_8));
			httpResponse = (HttpResponse) new DefaultHttpClient()
					.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {//

				// ����ʹ��getEntity������÷��ؽ��

				 responseResult.resultStr = (EntityUtils.toString(
				 httpResponse.getEntity(), "utf-8"));
			} else {
				Log.e("tag", "Internet-post-response->"
						+ httpResponse.getStatusLine().getStatusCode());
			}
			// responseResult.setResultCode(httpResponse.getStatusLine().getStatusCode());
		} catch (org.apache.http.conn.ConnectTimeoutException e) {
			// responseResult.setResultCode(0);
			e.printStackTrace();
			Log.i("tag", "Internet-����code-4>" + e);
		} catch (Exception e) { 
			// responseResult.setResultCode(1);
			e.printStackTrace();
			Log.i("tag", "Internet-����code-6>" + e);
		}

		finally {
			// client.getConnectionManager().shutdown();
			Log.i("tag", "Internet-post-  end..." + responseResult.toString());
			return responseResult;
		}
	}
	
		
}