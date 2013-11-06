package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.apptellout.R;
import com.example.entity.TellOutEntity;

/**
 * 吐槽 列表适配器
 * @author sunqm
 *
 */
public class TellOutAdapter extends BaseAdapter{

	private Context context;
	
	private List<TellOutEntity> list = new ArrayList<TellOutEntity>();
	
	public TellOutAdapter(Context context,List<TellOutEntity> list){
		this.context = context;
		this.list = list;
	}
	
	public void setData(List<TellOutEntity> list){
		this.list = list;
		notifyDataSetChanged();
	}
	
	public void addData(List<TellOutEntity> list){
		this.list.addAll(list);
		notifyDataSetChanged();
	}
	
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.tellout_item, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tellout_item_author_tv);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tellout_item_content_tv);
			holder.tvOk = (TextView) convertView.findViewById(R.id.tellout_item_ok_tv);
			holder.tvNo = (TextView) convertView.findViewById(R.id.tellout_item_no_tv);
			holder.tvComment = (TextView) convertView.findViewById(R.id.tellout_item_comment_tv);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvName.setText(list.get(arg0).getAuthorName());
		holder.tvContent.setText(list.get(arg0).getContent());
		holder.tvOk.setText(list.get(arg0).getOkNum());
		holder.tvNo.setText(list.get(arg0).getNoNum());
		holder.tvComment.setText(list.get(arg0).getCommentNum());
		
		return convertView;
	}

	static class ViewHolder{
		TextView tvName;
		TextView tvContent;
		TextView tvOk;
		TextView tvNo;
		TextView tvComment;
	}
	
}
