package com.sun.apphair.internet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
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

import com.sun.apphair.entity.RequestEntity;
import com.sun.apphair.entity.ResponseResult;
import com.sun.apphair.utils.Mconstant;


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
	 * ����Ƿ�����������
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

	
	
	Handler handler = new Handler() {

		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				requestCallBack.requestSuccess((ResponseResult) msg.obj);
				break;
			case -1://显示服务器异常，返回的错误描述信息
				requestCallBack
						.requestFailedStr(getErrorStr((ResponseResult) msg.obj));
				break;
			case -2:// Json解析错误
				requestCallBack.requestFailedStr(Mconstant.ERROR_JSON);
				break;
			case -3://服务器 没响应 等错误
				requestCallBack.requestFailedStr(Mconstant.ERROR_SERVER);
				break;
			}
			progressDialog.dismiss();
		}

	};

	private String getErrorStr(ResponseResult responseResult){
		String errorStr = null;
		try {
			JSONObject object = new JSONObject(responseResult.resultStr);
			errorStr = object.getString("error_str");
		} catch (JSONException e) {
			e.printStackTrace();
			return Mconstant.ERROR_JSON;
		}
		return errorStr;
		
	}
	
	private IRequestCallBack requestCallBack = null;

	/**
	 * 
	 * @param requestEntity
	 * @param requestCallBack
	 */
	public void requestThread(final RequestEntity requestEntity,
			final IRequestCallBack requestCallBack) {
		progressDialog.show();
		this.requestCallBack = requestCallBack;
		if (!isInternetAvaliable(context)) {
			sendMsg(null, 0);
			Log.d("tag", "数据加载中...");
			return;
		}
		new Thread() {

			@Override
			public void run() {
				ResponseResult responseResult = null;
				try {
					responseResult = InternetHelper
							.request(requestEntity);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (responseResult.resultCode == HttpStatus.SC_OK) {// �ɹ�
					Log.d("tag", "result-->" + responseResult.resultStr);
					JSONObject object;
					try {
						object = new JSONObject(responseResult.resultStr);
						int code = object.getInt("code");
						if (code != 0) {// ��ݲ���
							sendMsg(responseResult, -1);
							// requestCallBack.requestFailedStr(ErrorCodeUtils.changeCodeToStr(1));
						} else {// �����
							sendMsg(responseResult, 1);
						}
					} catch (JSONException e) {
						sendMsg(responseResult, -2);
						e.printStackTrace();
					}
					// requestCallBack.requestSuccess(responseResult);
				} else {
					sendMsg(responseResult, -3);
					// ʧ��
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
	private static ResponseResult request(RequestEntity requestEntity) throws UnsupportedEncodingException {
		// if(requestEntity.isPost){
		//
		// }
		// return doPost(requestEntity);
		// else
		return doGet(requestEntity);
	}

	/**
	 * Get �������
	 * 
	 * @param requestEntity
	 * @throws UnsupportedEncodingException 
	 */
	private static ResponseResult doGet(RequestEntity requestEntity) throws UnsupportedEncodingException {
		// ��������ȫ��ַ

		String url = getUrl(requestEntity.getUrl(), requestEntity.params);
		// �������ĵ�ַ
		Log.d("tag", "request--url" + url + "urlend");
		ResponseResult responseResult = get(url);
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
				// try {
				// if (value != null) {
				// Log.d("tag", "value-->" + value);
				// value = URLEncoder.encode(String.valueOf(value),
				// "UTF-8");
				// // value = new String(value.getBytes(),"utf-8");
				// Log.d("tag", "value--utf>" + value);
				// }
				//
				// } catch (UnsupportedEncodingException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
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
	private static ResponseResult get(String url) {
		ResponseResult responseResult = new ResponseResult();
		BufferedReader reader = null;
		StringBuffer sb = null;
		String result = "";
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		try {
			/** ����ʱ 20�� */
			request.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 20 * 1000);
			
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
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			responseResult.resultCode = (2);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("tag", "HttpHelper-get-result-exception->" + e);
			responseResult.resultCode = (2);
		} finally {
			try {
				if (null != reader) {
					reader.close();
					reader = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
				responseResult.resultCode = (2);
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
	// @SuppressWarnings("finally")
	// private static ResponseResult doPost(RequestEntity requestEntity) {
	// ResponseResult responseResult = new ResponseResult();
	//
	// Log.d("tag", "Internet-post-url>" + requestEntity.url);
	// HttpPost httpPost = null;
	// try {
	// httpPost = new HttpPost(requestEntity.url);
	// // ��������ʱ,20��
	// httpPost.getParams().setParameter(
	// CoreConnectionPNames.CONNECTION_TIMEOUT,
	// 20*1000);
	//
	// HttpResponse httpResponse = null;
	// /**�����������*/
	// httpPost.setEntity(new
	// UrlEncodedFormEntity(requestEntity.get_post_params(),
	// HTTP.UTF_8));
	// httpResponse = (HttpResponse) new DefaultHttpClient()
	// .execute(httpPost);
	// if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {//
	// ���󵽽��
	// // ����ʹ��getEntity������÷��ؽ��
	//
	// responseResult.setResultStr(EntityUtils.toString(
	// httpResponse.getEntity(), "utf-8"));
	// } else {
	// Log.e("tag", "Internet-post-response->"
	// + httpResponse.getStatusLine().getStatusCode());
	// }
	// responseResult.setResultCode(httpResponse.getStatusLine().getStatusCode());
	// } catch (org.apache.http.conn.ConnectTimeoutException e) {
	// responseResult.setResultCode(0);
	// e.printStackTrace();
	// Log.i("tag", "Internet-����code-4>" + e);
	// } catch (Exception e) {
	// responseResult.setResultCode(1);
	// e.printStackTrace();
	// Log.i("tag", "Internet-����code-6>" + e);
	// }
	//
	// finally {
	// // client.getConnectionManager().shutdown();
	// Log.i("tag", "Internet-post-  end..." + responseResult.toString());
	// return responseResult;
	// }
	// }

}