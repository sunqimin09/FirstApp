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
import com.sun.hair.adapter.FamousAdapter.ViewHolder;
import com.sun.hair.entity.CommentEntity;
import com.sun.hair.utils.AfinalBitmapTools;

/**
 * ͼƬ��ϸ
 * @author sunqm
 */

public class CommentsAdapter extends BaseAdapter{

	private Context context;
	
	private List<CommentEntity> comments = new ArrayList<CommentEntity>();
	
	private FinalBitmap fb;
	
	public CommentsAdapter(Context context){
		this.context = context;
		fb = AfinalBitmapTools.initBitmap(context);
	}
	
	public void setData( List<CommentEntity> comments){
		this.comments = comments;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return comments.size();
	}

	@Override
	public Object getItem(int arg0) {
		return comments.get(arg0);
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
			v = View.inflate(context,R.layout.item_act_photo,
					null);
			holder.tv_name = (TextView) v
					.findViewById(R.id.item_act_photo_nickname);
			holder.img_icon = (ImageView) v
					.findViewById(R.id.item_act_photo_icon);
			holder.tv_lou = (TextView) v
					.findViewById(R.id.item_act_photo_lou);
			holder.tv_time = (TextView) v
					.findViewById(R.id.item_act_photo_time);
			holder.tv_content = (TextView) v
					.findViewById(R.id.item_act_photo_content);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.tv_name.setText(comments.get(position).nickName);
		holder.tv_lou.setText(position+"¥");
		fb.display(holder.img_icon, comments.get(position).imgUrl);
//		holder.img_icon.setText(list.get(position).name+"name=====");
		holder.tv_time.setText(comments.get(position).time);
		holder.tv_content.setText(comments.get(position).content);
		return v;
	}

	
	static class ViewHolder {
		ImageView img_icon;
		TextView tv_name;
		TextView tv_lou;
		TextView tv_time;
		TextView tv_content;
	}

}
