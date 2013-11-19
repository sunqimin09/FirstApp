package com.tellout.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tellout.act.R;
import com.tellout.entity.TellOutEntity;

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
	public View getView(final int arg0, View convertView, ViewGroup arg2) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.tellout_item, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tellout_item_author_tv);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tellout_item_content_tv);
			holder.tvOk = (TextView) convertView.findViewById(R.id.tellout_item_ok_tv);
			holder.tvNo = (TextView) convertView.findViewById(R.id.tellout_item_no_tv);
			holder.tvComment = (TextView) convertView.findViewById(R.id.tellout_item_comment_tv);
			holder.okLayout = (LinearLayout) convertView.findViewById(R.id.ll1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvName.setText(list.get(arg0).getAuthorName());
		holder.tvContent.setText(list.get(arg0).getContent());
		holder.tvOk.setText(list.get(arg0).getOkNum()+"");
//		holder.tvNo.setText(list.get(arg0).getNoNum());
//		holder.tvComment.setText(list.get(arg0).getCommentNum());
		switch(list.get(arg0).getIsOK()){
		case 0://默认状态
			holder.okLayout.setSelected(false);
			break;
		case 1://选中状态
			holder.okLayout.setSelected(true);
			break;
		}
		
		holder.okLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("tag",arg0+"isok-->"+list.get(arg0).getIsOK());
				switch(list.get(arg0).getIsOK()){
				case 0://默认状态
					holder.okLayout.setSelected(true);
					list.get(arg0).setIsOK(1);
					holder.tvOk.setText(""+(Integer.parseInt(holder.tvOk.getText().toString())+1));
					break;
				case 1://选中状态
					holder.okLayout.setSelected(false);
					list.get(arg0).setIsOK(0);
					holder.tvOk.setText(""+(Integer.parseInt(holder.tvOk.getText().toString())-1));
					
					break;
				}
				synchronized(TellOutAdapter.this) {
					TellOutAdapter.this.notifyAll();
	            }
				
				
			}
		});
		return convertView;
	}

	static class ViewHolder{
		TextView tvName;
		TextView tvContent;
		TextView tvOk;
		TextView tvNo;
		TextView tvComment;
		LinearLayout okLayout;
	}
	
}
