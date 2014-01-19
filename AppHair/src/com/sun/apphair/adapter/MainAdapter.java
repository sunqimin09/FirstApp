/**
 * 
 */
package com.sun.apphair.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sun.apphair.R;
import com.sun.apphair.entity.ShopEntity;

/**
 * 项目名称：Hair
 * 文件名：MainAdapter.java  
 * 作者：@sunqm    
 * 创建时间：2014-1-18 下午2:18:46
 * 功能描述:  商店列表
 * 版本 V 1.0               
 */
public class MainAdapter extends BaseAdapter{

	private LayoutInflater inflate = null;
	
	private ArrayList<ShopEntity> list = new ArrayList<ShopEntity>();
	
	public MainAdapter(Context context,ArrayList<ShopEntity> list){
		inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list ;
	}
	
	public void setData(ArrayList<ShopEntity> list){
		this.list = list ;
		notifyDataSetChanged();
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return list.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;  
        if (convertView == null) {  
            holder = new ViewHolder();  
            convertView = inflate.inflate(R.layout.main_ls_item, null);  
            holder.tv_name = (TextView) convertView  
                    .findViewById(R.id.main_ls_item_name);  
            holder.tv_price = (TextView) convertView.findViewById(R.id.main_ls_item_price);  
            holder.tv_address = (TextView) convertView.findViewById(R.id.main_ls_item_address);  
            holder.tv_distance = (TextView) convertView.findViewById(R.id.main_ls_item_distance); 
            holder.img_icon =(ImageView)  convertView.findViewById(R.id.main_ls_item_icon); 
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.main_ls_item_ratingbar);
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        }  
        holder.tv_name.setText(list.get(position).name);
        holder.tv_price.setText("￥"+list.get(position).price);
        holder.tv_address.setText(list.get(position).address);
        holder.tv_distance.setText(list.get(position).distance+"m");
        holder.ratingBar.setRating(list.get(position).ratingbarScore);
		return convertView;
	}
	static class ViewHolder {  
        TextView tv_name;  
        TextView tv_price;  
        TextView tv_address;
        TextView tv_distance;
        ImageView img_icon;
        RatingBar ratingBar;
        
    }  
}