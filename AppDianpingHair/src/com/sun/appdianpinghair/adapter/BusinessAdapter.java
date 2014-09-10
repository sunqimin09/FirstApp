package com.sun.appdianpinghair.adapter;

import java.util.List;

import com.sun.appdianpinghair.R;
import com.sun.appdianpinghair.entity.BusinessEntity;
import com.sun.appdianpinghair.internet.ImageLoader;
import com.sun.appdianpinghair.utils.Utils;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BusinessAdapter extends BaseAdapter{
	
	private LayoutInflater inflater = null;
	
	private List<BusinessEntity> list;
	
	private ImageLoader imgLoader;
	
	public BusinessAdapter(Context context,List<BusinessEntity> list){
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list;
		imgLoader = new ImageLoader(context);
	}
	
	public void setData(List<BusinessEntity> list){
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
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.business_item,
					null);
			holder.tv_title = (TextView) v
					.findViewById(R.id.business_item_title);
			holder.tv_description = (TextView) v
					.findViewById(R.id.business_item_description);
			holder.tv_price = (TextView) v
					.findViewById(R.id.business_item_price);
			holder.image = (ImageView) v
					.findViewById(R.id.business_item_icon);
			holder.tv_distance = (TextView) v
					.findViewById(R.id.business_item_sale_count_distance);
//			holder.image.setLayoutParams(params);
//			holder.image.setBackgroundResource(R.drawable.default_img_small);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.tv_title.setText(list.get(position).name+"|"+list.get(position).branch_name);
		holder.tv_description.setText(showRegions(list.get(position).region));
		Utils.showPrice(holder.tv_price,list.get(position).avg_price);
		
		holder.tv_distance.setText(Utils.distanceFormat(list.get(position).distance));
		
		imgLoader.DisPlay(holder.image, list.get(position).s_photo_url);
//		if (!busy){
//			mImageLoader.DisplayImage(list.get(position).thumbnail,
//					holder.image, false);
//		}
			
		return v;
	}

	static class ViewHolder {
		TextView tv_title;
		TextView tv_description;
		TextView tv_price;
		TextView tv_distance;
		ImageView image;
	}
	
	private String showRegions(List<String> list){
		StringBuilder sb = new StringBuilder();
		for(int i=1;i<list.size();i++){
			sb.append(list.get(i)).append("|");
		}
		return (sb.substring(0, sb.length()-1)).toString();
	}

	
}
