/**
 * 
 */
package com.sun.apphair.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sun.apphair.R;
import com.sun.apphair.adapter.MainAdapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 项目名称：Hair
 * 文件名：KeyValueAdapter.java  
 * 作者：@sunqm    
 * 创建时间：2014-1-23 下午1:58:58
 * 功能描述:  
 * 版本 V 1.0               
 */
public class KeyValueAdapter extends BaseAdapter{

	private List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
	
	private Context context;
	
	public KeyValueAdapter(Context context,List<HashMap<String,Object>> list){
		this.list = list;
		this.context = context;
	}
	
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;  
        if (convertView == null) {  
            holder = new ViewHolder();  
            convertView = View.inflate(context,R.layout.list_view_item, null);  
            holder.tv_value = (TextView) convertView  
                    .findViewById(R.id.list_view_item_tv);  
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        }  
        holder.tv_value.setText(list.get(position).get("value").toString());
		return convertView;
	}
	static class ViewHolder {  
        TextView tv_value;  
    }  
}