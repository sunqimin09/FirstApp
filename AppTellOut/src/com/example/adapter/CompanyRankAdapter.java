package com.example.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.WorldRankAdapter.ViewHolder;
import com.example.apptellout.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CompanyRankAdapter extends BaseAdapter{

private Context context;
	
	private JSONArray array ;
	
	public CompanyRankAdapter(Context mcontext){
		context = mcontext;
		array = new JSONArray();
	}
	
	public void setData(JSONArray array){
		this.array = array;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return array.length();
	}

	@Override
	public Object getItem(int arg0) {
		try {
			return array.get(arg0);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
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


		try {
			JSONObject item = array.getJSONObject(arg0);
			holder.tv_name.setText(item.getString("company_name"));
			holder.tv_score.setText(item.getInt("score") + "分");
//			holder.tv_other.setText("行业:" + item.getString("industry_name"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return convertView;
	}


}
