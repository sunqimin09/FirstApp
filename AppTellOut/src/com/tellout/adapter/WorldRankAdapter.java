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
import com.tellout.entity.UserEntity;
/**
 * 排名 适配器
 * @author sunqm
 *
 */
public class WorldRankAdapter extends BaseAdapter{
	
	public WorldRankAdapter(Context mcontext){
		context = mcontext;
	}
	
	public WorldRankAdapter(Context mcontext,List<UserEntity> list){
		context = mcontext;
		this.list = list;
	}
	
	public void setData(List<UserEntity> list){
		this.list = list;
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

	static class ViewHolder{
		TextView tv_name;
		TextView tv_other;
		TextView tv_score;
		
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.rank_list_item, null);
			holder = new ViewHolder();
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.rank_list_item_name);
			holder.tv_other = (TextView) convertView
					.findViewById(R.id.rank_list_item_other);
			holder.tv_score = (TextView) convertView
					.findViewById(R.id.rank_list_item_score);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		// result>{"code":0,"result":{"worldRank":[{"name":"test1","score":"200","industry":"1",
		// "industry_name":"IT"},{"name":"test3","industry":"2","industry_name":"server"}],"name":"test1","score":"200","industry_id":"1","list_size":2}}
		//展示数据
		userEntity = list.get(arg0);
		
		holder.tv_name.setText(userEntity.getName());
		holder.tv_score.setText(userEntity.getScore()+"");
		holder.tv_other.setText(userEntity.getCompany_name());

		return convertView;
	}
	
	private Context context;
	
	/**展示数据*/
	private List<UserEntity> list = new ArrayList<UserEntity>();
	
	private static UserEntity userEntity = null;
}
