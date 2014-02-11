/**
 * 
 */
package com.sun.apphair.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sun.apphair.R;
import com.sun.apphair.entity.CommentEntity;

/**
 * 项目名称：Hair
 * 文件名：CommentAdapter.java  
 * 作者：@sunqm    
 * 创建时间：2014-1-22 下午2:59:29
 * 功能描述:  
 * 版本 V 1.0               
 */
public class CommentAdapter extends BaseAdapter{

	private List<CommentEntity> list = new ArrayList<CommentEntity>();
	
	private LayoutInflater inflate = null;
	
	public CommentAdapter(Context context,List<CommentEntity> list){
		inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list;
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
            convertView = inflate.inflate(R.layout.comment_listview_item, null);  
            holder.tv_name = (TextView) convertView  
                    .findViewById(R.id.comment_listview_item_name);  
            holder.tv_time = (TextView) convertView.findViewById(R.id.comment_listview_item_time);  
            holder.tv_content = (TextView) convertView.findViewById(R.id.comment_listview_item_content);  
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        }  
        holder.tv_name.setText(list.get(position).userName);
        holder.tv_time.setText(list.get(position).createTime);
        holder.tv_content.setText(list.get(position).content);
		return convertView;
	}
	
	static class ViewHolder {  
        TextView tv_name;  
        TextView tv_time;  
        TextView tv_content;
        
    }  
}