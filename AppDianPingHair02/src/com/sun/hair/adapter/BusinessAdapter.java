package com.sun.hair.adapter;

import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.hair.R;
import com.sun.hair.entity.BusinessEntity;
import com.sun.hair.utils.AfinalBitmapTools;
import com.sun.hair.utils.Utils;

public class BusinessAdapter extends BaseAdapter{
	
	private LayoutInflater inflater = null;
	
	private List<BusinessEntity> list;
	
	private FinalBitmap fb;
	
	public BusinessAdapter(Context context,List<BusinessEntity> list){
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list;
		fb = AfinalBitmapTools.initBitmap(context);
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
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		try{
			
		
		holder.tv_title.setText(list.get(position).name+"|"+list.get(position).branch_name);
		holder.tv_description.setText(showRegions(list.get(position).region));
		
		holder.tv_distance.setText(Utils.distanceFormat(list.get(position).distance));
		Utils.showPrice(holder.tv_price, list.get(position).avg_price);
		fb.display(holder.image, list.get(position).s_photo_url);
		}catch(Exception e){
			Log.e("tag","error-->"+e);
			e.printStackTrace();
		}
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
