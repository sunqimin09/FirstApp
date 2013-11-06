package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.apptellout.R;
import com.example.entity.TypeEntity;

public class TypeAdapter extends BaseAdapter{
	
	public TypeAdapter(Context context){
		this.context = context;
	}
	
	public TypeAdapter(Context context,List<TypeEntity> list){
        this.context = context;
		this.list = list;
	}
	
	public void setData(List<TypeEntity> list){
		this.list = list;
		notifyDataSetChanged();
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
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.rank_list_item, null);
			holder = new ViewHolder();
			holder.tvName = new TextView(context);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(list.get(arg0).getName());
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView tvName;
	}
	private List<TypeEntity> list = new ArrayList<TypeEntity>();
	
	private Context context = null;

}
