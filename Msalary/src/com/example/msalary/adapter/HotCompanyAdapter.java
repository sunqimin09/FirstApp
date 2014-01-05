package com.example.msalary.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.msalary.R;
import com.example.msalary.adapter.SelectCompanyAdapter.ViewHolder;
import com.example.msalary.entity.CompanyEntity;

public class HotCompanyAdapter extends BaseAdapter{

	private List<CompanyEntity> list = new ArrayList<CompanyEntity>();
	
	private LayoutInflater inflater = null;
	
	public HotCompanyAdapter(Context context){
//		this.list = list;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setData(List<CompanyEntity> list){
		this.list = list;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;  
        if (convertView == null) {  
            holder = new ViewHolder();  
            convertView = inflater.inflate(R.layout.selectcompanylist, null);  
            holder.name_tv = (TextView) convertView  
                    .findViewById(R.id.selectcompany_tv);  
            holder.count_tv = (TextView) convertView.findViewById(R.id.company_message_tv);  
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        }  
        holder.name_tv.setText(list.get(position).getName());
        holder.count_tv.setText(list.get(position).getJobCount()+"¸öÖ°Î»");
        
		return convertView;
	}
	static class ViewHolder {  
        public TextView name_tv;  
        public TextView count_tv;  
    }  
}
