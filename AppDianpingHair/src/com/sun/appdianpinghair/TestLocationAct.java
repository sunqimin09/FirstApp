package com.sun.appdianpinghair;

import android.os.Bundle;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class TestLocationAct extends BaseAct{

	private LocationClient mLocationClient;
	
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor="gcj02";
	
	private TextView LocationResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		LocationResult = (TextView) findViewById(R.id.textView1);
		mLocationClient = ((MyApplication) getApplication()).mLocationClient;
		 ((MyApplication)getApplication()).mLocationResult = LocationResult;
		 
		 InitLocation();
		 mLocationClient.start();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		mLocationClient.stop();
		super.onStop();
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
