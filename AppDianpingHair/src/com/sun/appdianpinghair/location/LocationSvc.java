package com.sun.appdianpinghair.location;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.sun.appdianpinghair.MyApplication;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * @author SunnyCoffee
 * @date 2014-1-19
 * @version 1.0
 * @desc 定位服务
 * 
 */
public class LocationSvc extends Service {

	private static final String TAG = "LocationSvc";
	private LocationClient mLocationClient;
	
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor="bd09ll";
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		
	}

	@Override
	public void onStart(Intent intent, int startId) {
//		((MyApplication)getApplication()).mLocationResult = LocationResult;
		mLocationClient = ((MyApplication) getApplication()).mLocationClient;
		 InitLocation();
		 mLocationClient.start();
	}

	@Override
	public boolean stopService(Intent name) {
		return super.stopService(name);
	}

	private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);//���ö�λģʽ
		option.setCoorType(tempcoor);//���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
		int span=1000;
		try {
			span = Integer.valueOf(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		option.setScanSpan(span);//���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
	

}
