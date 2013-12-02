package com.tellout.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tellout.act.R;
import com.tellout.entity.CommentEntity;

/**
 * 评论列表 适配器
 * @author sunqm
 *
 */
public class CommentAdapter extends BaseAdapter{

	private List<CommentEntity> list = new ArrayList<CommentEntity>();
	
	private Context context;
	
	public CommentAdapter(Context context,List<CommentEntity> list){
		this.context = context;
		this.list = list;
	}
	
	public void setData(List<CommentEntity> list){
		this.list = list;
		notifyDataSetChanged();
	}
	
	public void addData(List<CommentEntity> list){
//		this.list.addAll(list);
		this.list = list;
		notifyDataSetChanged();
	}
	
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.comment_detail_item, null);
			holder = new ViewHolder();
			holder.tvName = (TextView)convertView.findViewById(R.id.comment_detail_item_author);
			holder.tvContent = (TextView) convertView.findViewById(R.id.comment_detail_item_content);
			holder.tvIndex = (TextView) convertView.findViewById(R.id.comment_detail_item_indext);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(list.get(arg0).getAuthor());
		holder.tvContent.setText(list.get(arg0).getContent());
		holder.tvIndex.setText(""+(arg0+1));
		return convertView;
	}
	
	static class ViewHolder{
		TextView tvContent;
		TextView tvName;
		TextView tvIndex;
	}
	
}
