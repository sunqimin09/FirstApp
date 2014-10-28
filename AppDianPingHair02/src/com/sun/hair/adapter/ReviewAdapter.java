package com.sun.hair.adapter;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.hair.R;
import com.sun.hair.entity.ReviewEntity;
import com.sun.hair.utils.AfinalBitmapTools;

public class ReviewAdapter extends BaseAdapter{

	public Context context;
	
	private List<ReviewEntity> list = new ArrayList<ReviewEntity>();
	
	FinalBitmap fb = null;
	
	public ReviewAdapter(Context context){
		this.context = context;
		fb = AfinalBitmapTools.initBitmap(context);
	}
	
	public void setData(List<ReviewEntity> list){
		this.list = list;
		notifyDataSetChanged();
		
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context,R.layout.item_review, null);
		TextView tvContent = (TextView) view.findViewById(R.id.item_review_content);
		TextView tvName = (TextView) view.findViewById(R.id.item_review_name);
		TextView tvTime = (TextView) view.findViewById(R.id.item_review_time);
		ImageView imgIcon = (ImageView) view.findViewById(R.id.item_review_rating);
		tvContent.setText(list.get(position).text_excerpt);
		tvName.setText(list.get(position).user_nickname);
		tvTime.setText(list.get(position).created_time);
		fb.display(imgIcon,list.get(position).rating_s_img_url);
		return view;
	}

}
