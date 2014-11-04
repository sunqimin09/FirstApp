package com.sun.hair.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sun.hair.R;
import com.sun.hair.entity.DistrictsEntity;

/**
 * µÿ«¯  ≈‰∆˜
 * @author sunqm
 *
 */
public class DistrictsAdapter extends BaseAdapter{

	private Context context;
	
	List<DistrictsEntity> list = new ArrayList<DistrictsEntity>();
	
	public DistrictsAdapter(Context context){
		this.context = context;
	}
	
	public void setData(List<DistrictsEntity> list){
		this.list = list;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv = (TextView) View.inflate(context, R.layout.item_region_tv, null);
		tv.setText(list.get(position).name);
		return tv;
	}

}
