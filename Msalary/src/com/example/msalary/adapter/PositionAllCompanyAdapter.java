package com.example.msalary.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msalary.R;
import com.example.msalary.entity.CompanyEntity;
import com.example.msalary.entity.JobEntity;
import com.example.msalary.entity.ShowResult;

/**
 * 某一职位下公司的适配器
 * 
 * @author sunqm Create at: 2013-12-26 下午10:47:46 TODO
 */
public class PositionAllCompanyAdapter extends BaseAdapter {

	private ArrayList<CompanyEntity> list = new ArrayList<CompanyEntity>();

	private LayoutInflater inflater = null;

	@SuppressWarnings("unchecked")
	public PositionAllCompanyAdapter(Context context, ShowResult result) {
		this.list = (ArrayList<CompanyEntity>) result.list;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(ShowResult result) {
		this.list = (ArrayList<CompanyEntity>) result.list;
		this.notifyDataSetChanged();
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.position_allcompany_list,
					null);
			holder.allCompany_tv = (TextView) convertView
					.findViewById(R.id.allcompany_tv);
			holder.exposure_count_tv = (TextView) convertView
					.findViewById(R.id.exposure_number);
			holder.companySalary_tv = (TextView) convertView
					.findViewById(R.id.company_salary_tv);

			// holder.allCompany_tv.setText("华为科技有限公司");
			// holder.exposure_count_tv.setText("18人曝光");
			// holder.companySalary_tv.setText("￥3,600");
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.allCompany_tv.setText(list.get(position).getName());
		holder.exposure_count_tv.setText(list.get(position).getJobCount() + "");
		holder.companySalary_tv.setText(list.get(position).getAvgSalary() + "");
		return convertView;
	}

	static class ViewHolder {
		public TextView allCompany_tv;
		public TextView exposure_count_tv;
		public TextView companySalary_tv;
	}
}
