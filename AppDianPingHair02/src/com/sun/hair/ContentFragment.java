package com.sun.hair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.sun.hair.act.ShopDetailAct;
import com.sun.hair.act.ShopDetailAct2;
import com.sun.hair.adapter.BusinessAdapter;
import com.sun.hair.adapter.DistrictsAdapter;
import com.sun.hair.adapter.MArrayAdapter;
import com.sun.hair.entity.BusinessEntity;
import com.sun.hair.entity.DistrictsEntity;
import com.sun.hair.entity.JsonBusinessEntity;
import com.sun.hair.service.CityService;
import com.sun.hair.service.RegionsService;
import com.sun.hair.service.ShopService;
import com.sun.hair.utils.InterfaceCallback;
import com.sun.hair.utils.MConstant;
import com.sun.hair.utils.SpUtils;

public class ContentFragment extends Fragment implements OnClickListener, InterfaceCallback, OnItemClickListener {

//	Button btn_left;
//	Button btn_right;
	
	private TextView tvAddress;

	private ListView listview;
	
	private BusinessAdapter adapter;
	
	private JsonBusinessEntity entity = null;
	
	private List<BusinessEntity> list = new ArrayList<BusinessEntity>();
	
	private List<DistrictsEntity> districts = new ArrayList<DistrictsEntity>();
	
	private List<String> cities = new ArrayList<String>();
	
	private int sort = 1;
	
	private DistrictsAdapter  regionAdapter;
	
	private MArrayAdapter cityAdapter;
	
	private String currentCity = "北京";
	private String region = null,neighbur = null;
	
	private LocationClient mLocationClient;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor="gcj02";
	
	private boolean needLocation = true;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.content, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getActivity().findViewById(R.id.frag_content_order).setOnClickListener(this);
		tvAddress = (TextView) getActivity().findViewById(R.id.frag_content_address);
		tvAddress.setOnClickListener(this);
//		btn_left.setOnClickListener(this);
//		btn_right.setOnClickListener(this);
		listview = (ListView) getActivity().findViewById(R.id.content_lv);
		adapter = new BusinessAdapter(getActivity(), list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		request(true);
		mLocationClient = ((LocationApplication)getActivity().getApplication()).mLocationClient;
		InitLocation();
		mLocationClient.start();
		mLocationClient.registerLocationListener(new MyLocationListener());
	}
	
	/**
	 * 
	 * @param isLocation  是否需要地址
	 */
	private void request(boolean isLocation){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("city", currentCity);
		if(region!=null)
			paramMap.put("region", region);
		
		paramMap.put("category", "美发");
		paramMap.put("format", "json");
		paramMap.put("platform", "2");
		paramMap.put("sort", String.valueOf(sort));
		paramMap.put("limit", "40");
//		Log.d("tag","latitude;>"+MConstant.la+"Longitude:>"+MConstant.longi);
		if(isLocation){
			paramMap.put("latitude",new SpUtils(getActivity()).get("la"));
			paramMap.put("longitude", new SpUtils(getActivity()).get("lo"));
//			Toast.makeText(getActivity(), "request->"+MConstant.la, Toast.LENGTH_SHORT).show();
			
			
		}
		new ShopService().request(MConstant.URL_BUSINESS, paramMap, this);
	}
	
	private void requestCity(){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("format", "json");
		new CityService().request(MConstant.URL_CITYS, paramMap, new InterfaceCallback() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object o) {
				cities = (List<String>)o;
				cityAdapter.setData(cities);
			}
			
			@Override
			public void onFailed(String strMsg) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/**
	 * �����������
	 */
	private void requestRegion(String cityName){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("city", cityName);
		paramMap.put("format", "json");
		new RegionsService().request(MConstant.URL_REGION, paramMap, this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_left:
			((HomeAct) getActivity()).getMenu().showMenu();

			break;
		case R.id.btn_right:
			((HomeAct) getActivity()).getMenu().showSecondaryMenu();
			break;
		case R.id.frag_content_address:
			showCity(v);
			break;
		case R.id.frag_content_order:
			showPop(v,orders);
			break;
		default:
			break;
		}

	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(Object o) {
		Log.d("tag","onsuccess====-->"+o);
		if(o instanceof JsonBusinessEntity){
			entity =(JsonBusinessEntity) o;
			adapter.setData(entity.list);
			Log.d("tag","onsuccess====-111->"+o);
		}else if(o instanceof List<?>){
			districts = (List<DistrictsEntity>) o;
			regionAdapter.setData(districts);
		}
	}
	
	

	@Override
	public void onFailed(String strMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent i = new Intent(getActivity(),ShopDetailAct2.class);
		i.putExtra("business", entity.list.get((int)arg3));
		startActivity(i);
	}
	
	/**
	 * ����ѡ��
	 * @param view
	 * @param strs
	 */
	private void showPop(View view,String[] strs){
		ListView lv = new ListView(getActivity());
		lv.setBackgroundColor(Color.GRAY);
		ArrayAdapter Strigadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strs);
		
		lv.setAdapter(Strigadapter);
		final PopupWindow pop = new PopupWindow(lv, 300, LayoutParams.WRAP_CONTENT);
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.showAsDropDown(view);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sort = sorts[arg2];
				pop.dismiss();
				
				request(true);
			}
		});
	}
	
	private void showCity(View view){
		
		Log.d("tag","showCity==>");
//		if(districts.size()==0)
			requestCity();
		View convertView = View.inflate(getActivity(), R.layout.dialog_inner_city, null);
		final PopupWindow pop = new PopupWindow(convertView, 500, LayoutParams.WRAP_CONTENT);
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.showAsDropDown(view);
		
		ListView lv1 = (ListView) convertView.findViewById(R.id.listView1);
		ListView lv2 = (ListView) convertView.findViewById(R.id.listView2);
		regionAdapter= new DistrictsAdapter(getActivity());
		 cityAdapter = new MArrayAdapter(getActivity());
		
		lv1.setAdapter(cityAdapter);
		lv2.setAdapter(regionAdapter);
		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				currentCity = cities.get(arg2);
				requestRegion(cities.get(arg2));				
			}
		});
		lv2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				pop.dismiss();
				region= districts.get(arg2).name;
				tvAddress.setText(currentCity+"-"+region);
				needLocation = false;
				request(needLocation);
			}
		});
	}
	
	@Override
	public void onStop() {
		mLocationClient.stop();
		super.onStop();
	}

	/**
	 * 
	 */
	private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);//���ö�λģʽ
		option.setCoorType(tempcoor);//���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
		int span=5000;
		option.setScanSpan(span);//���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
	
	/**
	 * ʵ��ʵλ�ص�����
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
//			Toast.makeText(getActivity(), "location"+location.getLatitude(), Toast.LENGTH_SHORT).show();
//			Log.i("tag", "onReceiveLocation"+MConstant.la+(MConstant.la==0));
//			if(MConstant.la==0){
			SpUtils sp = new SpUtils(getActivity());
				if(sp.get("la").equals(""+location.getLatitude())){//与之前的一样
					
				}else{
					new SpUtils(getActivity()).put("la", location.getLatitude()+"");
					new SpUtils(getActivity()).put("lo", location.getLongitude()+"");
					request(needLocation);
				}
				
//			}
			Log.i("BaiduLocationApiDem", "BaiduLocationApiDem");
			mLocationClient.stop();
		}


	}
	
	
	private DistrictsEntity  currentEntity = null;
	
	private String[] orders = {"距离近到远","评价"};
	
	private int[] sorts = {7,2};
	
	private String[] address = {"上海","北京"};

}
