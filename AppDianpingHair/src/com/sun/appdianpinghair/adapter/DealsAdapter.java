package com.sun.appdianpinghair.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sun.appdianpinghair.entity.DealsEntity;

/**
 * 团购列表 适配器
 * @author sunqm
 * Create at:   2014-6-28 下午10:05:02 
 * TODO
 */
public class DealsAdapter extends BaseAdapter{

	private List<DealsEntity> list = new ArrayList<DealsEntity>();
	
	private LayoutInflater inflater;
	
	public DealsAdapter(Context context,List<DealsEntity> list){
		this.list = list;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = inflater.inflate(android.R.layout.simple_list_item_1, null);
		TextView tv = (TextView) view.findViewById(android.R.id.text1);
		tv.setText(list.get(position).description);
		
		return view;
	}

}
