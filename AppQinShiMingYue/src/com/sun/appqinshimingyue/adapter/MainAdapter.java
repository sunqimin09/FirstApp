package com.sun.appqinshimingyue.adapter;

import java.util.ArrayList;
import java.util.List;

import com.sun.appqinshimingyue.entity.InforEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter{

	private Context context;
	
	private List<InforEntity> list = new ArrayList<InforEntity>();
	
	public MainAdapter(Context context,List<InforEntity> list){
		this.context = context;
		this.list = list;
	}
	
	public void setData(List<InforEntity> list){
		this.list = list;
		this.notifyDataSetChanged();
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
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		TextView tv = new TextView(context);
		tv.setText(list.get(arg0).name);
		tv.setPadding(10, 10, 10, 10);
		return tv;
	}

}
