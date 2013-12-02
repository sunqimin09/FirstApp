package com.tellout.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tellout.entity.TellOutEntity;

public class MyTelloutAdapter extends BaseAdapter{

	private Context context ;
	
	private List<TellOutEntity> list = new ArrayList<TellOutEntity>();
	
	private LayoutInflater inflater = null;
	
	public MyTelloutAdapter(Context context,List<TellOutEntity> list){
		this.context = context;
		this.list = list;
		inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		return null;
	}

}
