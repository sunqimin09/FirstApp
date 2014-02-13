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
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.apphair.R;
import com.sun.apphair.entity.OrderEntity;

/**
 * 项目名称：营销移动智能工作平台 <br>
 * 文件名：TerminalDetailsFragment.java <br>
 * 作者：@sunqm    <br>
 * 创建时间：2014-2-11 下午4:15:08
 * 功能描述: 终端详情 <br>
 * 版本 V 1.0 <br>               
 */
public class MyOrdersAdapter extends BaseAdapter{

	private List<OrderEntity> list = new ArrayList<OrderEntity>();
	
	private LayoutInflater inflate = null;
	
	public MyOrdersAdapter(Context context,List<OrderEntity> alist){
		list = alist;
		inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
	public Object getItem(int position) {
		return list.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
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
            convertView = inflate.inflate(R.layout.my_orders_lv_item, null);  
            holder.tv_shop_name = (TextView) convertView  
                    .findViewById(R.id.my_orders_item_tv_shop_name);  
            holder.tv_order_name = (TextView) convertView.findViewById(R.id.my_orders_item_tv_order_name);  
            holder.tv_order_count = (TextView) convertView.findViewById(R.id.my_orders_item_tv_order_count);  
            holder.tv_order_price = (TextView) convertView.findViewById(R.id.my_orders_item_tv_order_price);
            holder.img_icon = (ImageView) convertView.findViewById(R.id.my_orders_item_img_shop_icon);
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        }  
        holder.tv_shop_name.setText(list.get(position).shopName);
        holder.tv_order_name.setText(list.get(position).orderName);
        holder.tv_order_count.setText(list.get(position).count);
        holder.tv_order_price.setText(list.get(position).money+"元");
        
		return convertView;
	}
	
	static class ViewHolder {  
        TextView tv_shop_name;  
        TextView tv_order_name;  
        TextView tv_order_count;
        TextView tv_order_price;
        ImageView img_icon;
        
    }  

}
