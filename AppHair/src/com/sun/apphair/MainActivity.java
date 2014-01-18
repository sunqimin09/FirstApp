package com.sun.apphair;


import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class MainActivity extends Activity {

	private Vibrator mVibrator01 =null;
	private LocationClient mLocClient;
	
	private boolean  mIsStart;
	private static int count = 1;
	
	private Button startBtn = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startBtn = (Button) findViewById(R.id.startBtn);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void OnClick(View view){
		switch(view.getId()){
		case R.id.startBtn:
			location();
			break;
		}
	}
	
	private void location(){
		if (!mIsStart) {
			setLocationOption();
			mLocClient.start();
			startBtn.setText("停止");
			mIsStart = true;

		} else {
			mLocClient.stop();
			mIsStart = false;
			startBtn.setText("开始");
		} 
	}
	
	//设置相关参数
		private void setLocationOption(){
//			LocationClientOption option = new LocationClientOption();
//			option.setOpenGps(true);				//打开gps mGpsCheck.isChecked()
//			option.setCoorType("bd09ll");		//设置坐标类型 mCoorEdit.getText().toString()
//			option.setServiceName("com.baidu.location.service_v2.9");
//			option.setPoiExtraInfo(true);	//mIsAddrInfoCheck.isChecked()//地址信息
//			if(mIsAddrInfoCheck.isChecked())
//			{
//				option.setAddrType("all");
//			}		
//			if(null!=mSpanEdit.getText().toString())
//			{
//				boolean b = isNumeric(mSpanEdit.getText().toString());
//				 if(b)
//				{
//					option.setScanSpan(Integer.parseInt(mSpanEdit.getText().toString()));	//设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
//				}
//			}
////			option.setScanSpan(3000);
//			
//			if(mPriorityCheck.isChecked())
//			{
//				option.setPriority(LocationClientOption.NetWorkFirst);      //设置网络优先
//			}
//			else
//			{
//				option.setPriority(LocationClientOption.GpsFirst);        //不设置，默认是gps优先
//			}
//
//			option.setPoiNumber(10);
//			option.disableCache(true);		
//			mLocClient.setLocOption(option);
		}

		protected boolean isNumeric(String str) {   
			Pattern pattern = Pattern.compile("[0-9]*");   
			return pattern.matcher(str).matches();   
		}  

}
