package com.sun.hair.adapter;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.hair.R;
import com.sun.hair.entity.FamousEntity;
import com.sun.hair.utils.AfinalBitmapTools;

public class FamousAdapter extends BaseAdapter{

	List<FamousEntity> foods = new ArrayList<FamousEntity>();
	
	private Context context;
	
	private FinalBitmap fb;
	
	public FamousAdapter(Context context){
		 this.context = context;
		 fb = AfinalBitmapTools.initBitmap(context);
	}
	
	public void setData(List<FamousEntity> foods){
		this.foods = foods;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return foods.size();
	}

	@Override
	public Object getItem(int position) {
		return foods.get(position);
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
			v = View.inflate(context,R.layout.item_food,
					null);
			holder.tv_name = (TextView) v
					.findViewById(R.id.item_food_name);
			holder.img_icon = (ImageView) v
					.findViewById(R.id.item_food_icon);
			holder.tv_look = (TextView) v
					.findViewById(R.id.item_food_look);
			holder.tv_ok = (TextView) v
					.findViewById(R.id.item_food_ok);
			holder.tv_price = (TextView) v
					.findViewById(R.id.item_food_price);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.tv_name.setText(foods.get(position).name);
		holder.tv_look.setText("浏览:"+foods.get(position).looked_num);
		fb.display(holder.img_icon, foods.get(position).photo_url_s);
//		holder.img_icon.setText(list.get(position).name+"name=====");
		holder.tv_ok.setText("赞:"+foods.get(position).ok_num);
		if(foods.get(position).price!=0){
			holder.tv_price.setText("价格:"+foods.get(position).price);
		}
		return v;
	}

	
	static class ViewHolder {
		ImageView img_icon;
		TextView tv_name;
		TextView tv_look;
		TextView tv_ok;
		TextView tv_price;
	}

}
