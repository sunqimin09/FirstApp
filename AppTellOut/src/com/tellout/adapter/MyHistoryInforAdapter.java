package com.tellout.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.SimpleFormatter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tellout.act.R;
import com.tellout.entity.UserEntity;

public class MyHistoryInforAdapter extends BaseAdapter{

	private List<UserEntity> list = new ArrayList<UserEntity>();
	
	private LayoutInflater inflater = null;
	
	public MyHistoryInforAdapter(Context context,List<UserEntity> list){
		this.list = list;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setaData(List<UserEntity> list){
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
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.my_history_infor_item, null);
			holder = new ViewHolder();
			holder.tvName = (TextView)convertView.findViewById(R.id.my_history_infor_item_salary);
			holder.tvContent = (TextView) convertView.findViewById(R.id.my_history_infor_item_comment);
			holder.tvIndex = (TextView) convertView.findViewById(R.id.my_history_infor_item_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(list.get(arg0).getSalary()+"");
		holder.tvContent.setText(list.get(arg0).getComment());
		holder.tvIndex.setText(dataFormat(list.get(arg0).getOther()));
		Log.d("tag","getview->"+list.get(arg0).getOther());
		return convertView;
	}

	private String dataFormat(String str){
		if(str!=null){
			return str.substring(0, str.length()-5);
		}
		return null;
	}
	
	static class ViewHolder{
		TextView tvContent;
		TextView tvName;
		TextView tvIndex;
	}
}
