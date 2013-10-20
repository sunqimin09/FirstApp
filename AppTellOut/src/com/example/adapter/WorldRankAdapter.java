package com.example.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.apptellout.R;

public class WorldRankAdapter extends BaseAdapter{

	
	private Context context;
	
	private LayoutInflater inflate;
	
	private JSONArray array ;
	
	public WorldRankAdapter(Context mcontext){
		context = mcontext;
		inflate = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
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

		try {
			JSONObject item = array.getJSONObject(arg0);
			holder.tv_name.setText(item.getString("name"));
			holder.tv_score.setText(item.getString("score") + "分");
			holder.tv_other.setText("行业:" + item.getString("industry_name"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertView;
	}

}
