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

import com.sun.hair.act.ShopDetailAct;
import com.sun.hair.adapter.BusinessAdapter;
import com.sun.hair.entity.BusinessEntity;
import com.sun.hair.entity.DistrictsEntity;
import com.sun.hair.entity.JsonBusinessEntity;
import com.sun.hair.service.RegionsService;
import com.sun.hair.service.ShopService;
import com.sun.hair.utils.InterfaceCallback;
import com.sun.hair.utils.MConstant;

public class ContentFragment extends Fragment implements OnClickListener, InterfaceCallback, OnItemClickListener {

//	Button btn_left;
//	Button btn_right;
	
	private TextView tvAddress;

	private ListView listview;
	
	private BusinessAdapter adapter;
	
	private JsonBusinessEntity entity = null;
	
	private List<BusinessEntity> list = new ArrayList<BusinessEntity>();
	
	List<DistrictsEntity> districts = new ArrayList<DistrictsEntity>();
	
	private int sort = 1;
	
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
		request();
	}
	
	private void request(){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("city", "北京");
		paramMap.put("category", "美发");
		paramMap.put("format", "json");
		paramMap.put("platform", "2");
		paramMap.put("sort", String.valueOf(sort));
		new ShopService().request(MConstant.URL_BUSINESS, paramMap, this);
	}
	
	/**
	 * 请求地区名字
	 */
	private void requestRegion(){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("city", "北京");
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
			requestRegion();
			break;
		case R.id.frag_content_order:
			showPop(v,orders);
			break;
		default:
			break;
		}

	}

	@Override
	public void onSuccess(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof JsonBusinessEntity){
			entity =(JsonBusinessEntity) o;
			adapter.setData(entity.list);
		}else if(o instanceof List<?>){
			districts = (List<DistrictsEntity> )o;
			if(Strigadapter!=null){
				Strigadapter.addAll(districts);
			}
			
		}
	}
	
	

	@Override
	public void onFailed(String strMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent i = new Intent(getActivity(),ShopDetailAct.class);
		i.putExtra("business", entity.list.get((int)arg3));
		startActivity(i);
	}
	
	ArrayAdapter<String> Strigadapter = null;
	
	private void showPop(View view,String[] strs){
		ListView lv = new ListView(getActivity());
		lv.setBackgroundColor(Color.GRAY);
		Strigadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strs);
		
		lv.setAdapter(adapter);
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
				
			}
		});
	}
	
	private String[] orders = {"距离近到远","评价"};
	
	private int[] sorts = {7,2};
	
	private String[] address = {"上海","北京"};

}
