package com.example.msalary.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import com.example.msalary.R;
import com.example.msalary.entity.CompanyEntity;
import com.example.msalary.entity.JobEntity;
import com.example.msalary.entity.RequestEntity;
import com.example.msalary.entity.ResponseResult;
import com.example.msalary.entity.ShowResult;
import com.example.msalary.internet.InternetHelper;
import com.example.msalary.json.JsonSelectCompany;
import com.example.msalary.json.JsonSelectPosition;
import com.example.msalary.util.MConstant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 选择要查的职位界面。。（一个类别的不同职位，从主页面的查找引出）
 * 
 * @author Administrator
 * 
 */
public class SelectPositionActivity extends BaseActivity implements
		OnItemClickListener {

	private ArrayList<HashMap<String, String>> listPosition;
	private ListView position_list;
	private ShowResult showResult = null;
	private selectPositionAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.position_search_main);
		initView();
		request(getIntent().getStringExtra("key"));

	}

	@Override
	protected void initView() {
		super.initView();
		tv_title.setText(getString(R.string.select_position_title));
		// TODO Auto-generated method stub
		position_list = (ListView) findViewById(R.id.position_list_lv);
		position_list.setOnItemClickListener(this);
		showResult = new ShowResult();
		adapter = new selectPositionAdapter(this, showResult);
		position_list.setAdapter(adapter);
	}

	/**
	 * 发起网络请求
	 * 
	 * @param requestStr
	 */
	private void request(String requestStr) {
		RequestEntity requestEntity = new RequestEntity(this,
				MConstant.URL_SELECT_POSITION);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("key", requestStr);
		requestEntity.params = map;
		new InternetHelper(this).requestThread(requestEntity, this);
	}

	@Override
	public void requestSuccess(ResponseResult responseResult) {
		Log.d("tag", "showResult" + responseResult);
		showResult = JsonSelectPosition.parse(responseResult, this);

		adapter.setData(showResult);
	}

	/**
	 * 同类不同职位的列表相应的adapter。
	 * 
	 * @author Administrator
	 * 
	 */
	private class selectPositionAdapter extends BaseAdapter {
		private Context context;
		private ArrayList<JobEntity> list;

		private selectPositionAdapter(Context context, ShowResult result) {
			this.context = context;
			this.list = (ArrayList<JobEntity>) result.list;
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
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public void setData(ShowResult result) {
			this.list = (ArrayList<JobEntity>) result.list;
			this.notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder vh = null;
			if (convertView == null) {
				vh = new ViewHolder();

				convertView = LayoutInflater.from(context).inflate(
						R.layout.selectpositionlist, null);
				vh.selectposition_tv = (TextView) convertView
						.findViewById(R.id.selectposition_tv);
				vh.position_message_tv = (TextView) convertView
						.findViewById(R.id.position_message_tv);

				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			vh.selectposition_tv.setText(list.get(position).getName());
			vh.position_message_tv.setText(list.get(position).getCompanyCount()
					+ "");
			return convertView;
		}

	}

	static class ViewHolder {
		public TextView selectposition_tv;
		public TextView position_message_tv;
	}

	/**
	 * 点击事件，转换到下一个Activity，带着特定职位的id
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SelectPositionActivity.this,
				PositionAllCompanyActivity.class);
		intent.putExtra("positionId",
				((JobEntity) showResult.list.get((int) arg3)).getId());
//		intent.putExtra("positionName",
//				((JobEntity) showResult.list.get((int) arg3)).getName());
		startActivity(intent);
	}
}
