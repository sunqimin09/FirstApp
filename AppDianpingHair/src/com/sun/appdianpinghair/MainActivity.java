package com.sun.appdianpinghair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.appdianpinghair.adapter.BusinessAdapter;
import com.sun.appdianpinghair.debug.MyDebug;
import com.sun.appdianpinghair.entity.BusinessEntity;
import com.sun.appdianpinghair.entity.JsonBaseInterface;
import com.sun.appdianpinghair.entity.JsonBusinessEntity;
import com.sun.appdianpinghair.internet.IRequestCallback;
import com.sun.appdianpinghair.internet.RequestUtils;
import com.sun.appdianpinghair.location.LocationSvc;
import com.sun.appdianpinghair.service.MainActService;
import com.sun.appdianpinghair.utils.LocationTools;
import com.sun.appdianpinghair.utils.LocationUtils;
import com.sun.appdianpinghair.utils.Mconstant;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends BaseAct implements OnItemClickListener, IRequestCallback {

	private TextView  tvPosition;
	
	private TextView tvOrder;
	
	private ListView listview;
	
	private String[] orders =new String[4];
	
	private int[] orderInt = {R.string.comment,R.string.distance,R.string.price_from_l,
			R.string.price_from_h};
	
	private BusinessAdapter adapter;
	
	private List<BusinessEntity> list;
	
	private MainActService service1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setTitle("附近商家");
        initView();
     // 启动服务  
        Intent intent = new Intent();  
        intent.setClass(this, LocationSvc.class);  
        startService(intent); 
	}
	

	private void initView() {
		tvPosition = (TextView) findViewById(R.id.main_position);
		tvOrder = (TextView) findViewById(R.id.main_order);
		listview = (ListView) findViewById(R.id.main_listview);
		listview.setOnItemClickListener(this);
		for(int i =0;i<orders.length;i++){
			orders[i] = getString(orderInt[i]);
		}
		list = new ArrayList<BusinessEntity>();
		adapter = new BusinessAdapter(this.getApplicationContext(), list);
		listview.setAdapter(adapter);
		request();
//		getLocation();
		service1 = new MainActService();
		
	}
	private void getLocation() {
		Toast("正在获取当前坐标");
		LocationTools.getInternetLocation(MainActivity.this);
		new Thread(){

			@Override
			public void run() {
//				double[] result = 
						
//				Mconstant.latitude = result[0];
//				Mconstant.longitude = result[1];
				handler.sendEmptyMessage(1);
			}
			
		}.start();
		
		
	}


	/**
	 * 请求网络数据
	 */
	private void request(){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("city", "北京");
		paramMap.put("category", "美发");
		paramMap.put("format", "json");
		
		if(service==null){
			RequestUtils.requestAct(Mconstant.URL_BUSINESS, paramMap, handler);
		}else{
			service.request(Mconstant.URL_BUSINESS, paramMap, this);
		}
	}
	
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 1://获取坐标
				Toast("已获取当前坐标"+Mconstant.latitude);
				
				break;
			default:
				requestSuccess((JsonBusinessEntity)msg.obj);
				break;
			}
			
		}
		
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_comment:
			list = service1.sortByComment(list);
			break;
		case R.id.menu_distance:
			list = service1.sortByDistance(list);
			break;
		case R.id.menu_price_l:
			list = service1.sortByMoneyCheap(list);
			break;
		case R.id.menu_price_h:
			list = service1.sortByMoneyExpensice(list);
			break;
		}
		adapter.setData(list);
//		Toast("menu"+item.getItemId());
		return super.onOptionsItemSelected(item);
	}

	
	static class RequestAPILickListener implements OnClickListener {

		private Activity activity;

		public RequestAPILickListener(Activity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(View v) {
			Log.d("tag", "onclick-->" + new MyDebug().getBusiness());
		}
	}



	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent i = new Intent(MainActivity.this,DetailAct.class);
		i.putExtra("business", list.get((int)arg3));
		startActivity(i);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Intent intent = new Intent(this, DataService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		super.onStop();
		unbindService(mConnection);
	}

	/** 与service 通信 */
	private DataService service = null;
	
	public DataService getService(){
		return service;
	}

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className,
				IBinder localBinder) {
			service = ((com.sun.appdianpinghair.DataService.LocalBinder) localBinder).getService();
			Log.d("tag", "service-connected->" + service);
		}

		public void onServiceDisconnected(ComponentName arg0) {
			service = null;
		}
	};


	@Override
	public void requestFailed(String error) {
		Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
	}


	@Override
	public void requestSuccess(JsonBaseInterface result) {
		/**网络请求结束*/
		list = ((JsonBusinessEntity) result).list;
		adapter.setData(list);
		Log.d("tag","requestSuccess-->"+((JsonBusinessEntity) result).list.size());
	}

	private class LocationBroadcastReceiver extends BroadcastReceiver {  
		  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            if (!intent.getAction().equals("LOCATION_ACTION")) return;  
//            String locationInfo = intent.getStringExtra(Common.LOCATION);  
//            text.setText(locationInfo);  
//            dialog.dismiss();  
            MainActivity.this.unregisterReceiver(this);// 不需要时注销  
        }  
    }  
	
}
