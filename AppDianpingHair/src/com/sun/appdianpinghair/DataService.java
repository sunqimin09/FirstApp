package com.sun.appdianpinghair;

import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.sun.appdianpinghair.entity.JsonBusinessEntity;
import com.sun.appdianpinghair.internet.IRequestCallback;
import com.sun.appdianpinghair.json.JsonBusiness;
import com.sun.appdianpinghair.utils.DemoApiTool;

/**
 * 数据加载 服务
 * 
 * @author sunqm Create at: 2014-6-22 下午1:06:09 TODO
 */
public class DataService extends Service {

	private String appKey = "4073748977";

	private String secret = "a6b49517c78446c59d0302fd7551d118";

	private final IBinder binder = new LocalBinder();

	public class LocalBinder extends Binder {
		DataService getService() {
			return DataService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();

	}

	public void request(final String url, final Map<String, String> paramMap,
			final IRequestCallback callBack) {
		new Thread() {

			@Override
			public void run() {
				String requestResult = DemoApiTool.requestApi(url, appKey,
						secret, paramMap);
				Bundle b = new Bundle();
				b.putString("result", requestResult);
				Message msg = handler.obtainMessage();
				msg.obj = callBack;
				msg.what = 1;
				handler.sendMessage(msg);
			}
		}.start();

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				JsonBusinessEntity entity = JsonBusiness.parse(msg.getData()
						.getString("result"));
				// 请求道数据 后
				if (entity.status.equals("OK")) {
					((IRequestCallback) msg.obj).requestSuccess(entity);
				} else {
					((IRequestCallback) msg.obj).requestFailed(entity.status);
				}
				break;
			}

		}

	};

}
