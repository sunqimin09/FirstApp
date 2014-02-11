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
            holder.img_start = (ImageView) convertView.findViewById(R.id.main_ls_item_img_start);
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        }  
        holder.tv_name.setText(list.get(position).name);
        holder.tv_price.setText("￥"+list.get(position).price);
        holder.tv_address.setText(list.get(position).address);
        holder.tv_distance.setText(list.get(position).distance+"m");
        setStart(holder.img_start, (int)list.get(position).ratingbarScore);
		return convertView;
	}
	
	private void setStart(ImageView imgView,int start){
		int temp = 0;
		if(start<5){
			temp = 0;
		}else if(start<10){
			temp = 1;
		}else if(start<15){
			temp = 2;
		}else if(start<20){
			temp = 3;
		}else if(start<25){
			temp = 4;
		}else if(start<30){
			temp = 5;
		}else if(start<35){
			temp = 6;
		}else if(start<40){
			temp = 7;
		}else if(start<45){
			temp = 8;
		}else {
			temp = 9;
		}
		imgView.setImageResource(starts[temp]);
	}
	
	private int[] starts = { R.drawable.star0, R.drawable.star10,
			R.drawable.star15, R.drawable.star20, R.drawable.star25,
			R.drawable.star30, R.drawable.star35, R.drawable.star40,
			R.drawable.star45, R.drawable.star50 };

	static class ViewHolder {  
        TextView tv_name;  
        TextView tv_price;  
        TextView tv_address;
        TextView tv_distance;
        ImageView img_icon;
        ImageView img_start;
//        RatingBar ratingBar;
        
    }  
}