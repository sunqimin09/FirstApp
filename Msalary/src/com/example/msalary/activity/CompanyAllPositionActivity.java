package com.example.msalary.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.msalary.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 某公司下所有职位列表，以及评论数量，点击评论会到评论界面并且显示以往评论的内容。
 * @author Administrator
 *
 */
public class CompanyAllPositionActivity extends Activity {
	private ImageButton back_btn;
	private TextView  allPosition_tv;
	private TextView  positionSalary_tv;//职位薪资
	private ListView  allPosition_list;//一个公司所有职位列表
	private TextView  comment_someCompany;//评论
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	//设置标题，自定义标题
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    	setContentView(R.layout.company_allpositions_salary);
    	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.position_salary_title);
    	back_btn=(ImageButton) findViewById(R.id.position_salary_back);
    	back_btn.setBackgroundResource(R.drawable.cloud_back_click);
    	allPosition_list=(ListView) findViewById(R.id.allposition_list);
    	//评论事件处理
    	comment_someCompany=(TextView) findViewById(R.id.comment_somecompany_tv);
    	comment_someCompany.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(CompanyAllPositionActivity.this, CommentActivity.class);
				startActivity(intent);
			}
		});
    	//为list赋值。职位、薪资。
    	ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String,String>>();
    	for(int i=0;i<7;i++){
    		HashMap<String, String> map=new HashMap<String, String>();
    		map.put("position", "职位职位");
    		map.put("salary", "￥3500");
    		list.add(map);
    	}
    	allpositionAdapter adapter=new allpositionAdapter(this, list);
    	allPosition_list.setAdapter(adapter);
    }
     /**
      * 
      * @author 某一公司的所有职位及其相应工资。adapter
      *
      */
     private class allpositionAdapter extends BaseAdapter{
    	 private Context context;
    	 private ArrayList<HashMap<String, String>> list;
       public allpositionAdapter(Context context,ArrayList<HashMap<String, String>> list){
    	   this.context=context;
    	   this.list=list;
       }
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView=LayoutInflater.from(context).inflate(R.layout.company_allposition_list, null);
			allPosition_tv=(TextView) convertView.findViewById(R.id.allposition_tv);
			positionSalary_tv=(TextView) convertView.findViewById(R.id.position_salary_tv);
			allPosition_tv.setText(list.get(position).get("position"));
			positionSalary_tv.setText(list.get(position).get("salary"));
			
			return convertView;
		}
    	 
     }
}
