package com.sun.hair;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.sun.hair.utils.MConstant;
import com.sun.jumi.JMPManager;

/**
 * 主Application
 */
public class LocationApplication extends Application {
	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;
	
//	public TextView mLocationResult,logMsg;
	public TextView trigger,exit;
	public Vibrator mVibrator;
	
	public static JMPManager jmInstance;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		
		  jmInstance = JMPManager.getInstance(getApplicationContext(), "821305a1-824c-4b26-abf3-daac29f6f41f", 1);
	        jmInstance.c(1, 4, false);
	        jmInstance.o(true, false, 6, false, true, true);
	        jmInstance.debug(2); // 2 打印 log，部分功能Toast提示， 发布产品时请设置为0，默认为1
	    }

	    public static JMPManager getJmInstance() {
	        return jmInstance;
	    }
	

	
	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
//			Toast.makeText(getApplicationContext(), "location"+location.getLatitude(), Toast.LENGTH_SHORT).show();
			Log.i("tag", "onReceiveLocation"+location.getLatitude());
			MConstant.la = location.getLatitude();
			MConstant.longi =location.getLongitude();
			//Receive Location 
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				//运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			logMsg(sb.toString());
			Log.i("BaiduLocationApiDem", "BaiduLocationApiDem"+sb.toString());
		}


	}
	
	
	/**
	 * 显示请求字符串
	 * @param str
	 */
	public void logMsg(String str) {
//		try {
//			if (mLocationResult != null)
//				mLocationResult.setText(str);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	
}
