package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.WorldRankAdapter.ViewHolder;
import com.example.apptellout.R;
import com.example.entity.UserEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CompanyRankAdapter extends BaseAdapter{

	private Context context;
	
	
	public CompanyRankAdapter(Context mcontext){
		context = mcontext;
	}
	
	public CompanyRankAdapter(Context mcontext,List<UserEntity> list){
		context = mcontext;
		this.list = list;
	}
	
	public void setData(List<UserEntity> list){
		this.list = list;
		this.notifyDataSetChanged();
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
	public long getItemId(int position) {
		return position;
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
		//{"code":0,"result":{"companys":[{"name":"test2","score":"100","company_name":"testa"},{"name":"test3","company_name":"testcc"}],"list_size":2}}
		userEntity = list.get(arg0);

//		holder.tv_name.setText();
//		holder.tv_score.setText(item.getInt("score") + "分");
//		holder.tv_other.setText("行业:" + item.getString("industry_name"));
		
		return convertView;
	}

	private List<UserEntity> list = new ArrayList<UserEntity>();
	
	private static UserEntity userEntity = null;

}
