package com.sun.hair.adapter;

import java.util.ArrayList;
import java.util.List;

import com.sun.hair.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * ×Ö·û´®ÊÊÅäÆ÷
 * @author sunqm
 *
 */
public class MArrayAdapter extends BaseAdapter{

	private Context context;
	
	private List<String> list = new ArrayList<String>();
	
	public MArrayAdapter(Context context){
		this.context = context;
	}
	
	public void setData(List<String> list){
		this.list = list;
		notifyDataSetChanged();
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
		
		TextView tv = (TextView) View.inflate(context, R.layout.item_region_tv, null);
		tv.setText(list.get(position));
		return tv;
	}

}
