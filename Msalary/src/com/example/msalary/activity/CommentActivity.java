package com.example.msalary.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.msalary.R;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 
 * @author Administrator评论的界面
 *
 */
public class CommentActivity extends Activity {
	private ImageButton back_btn;
	private TextView commentTime_tv;//评论的时间
	private TextView comment_tv;//评论内容
	private ListView comment_list;//以往评论列表
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	//设置标题
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    	setContentView(R.layout.comment_main);
    	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.position_salary_title);
    	back_btn=(ImageButton) findViewById(R.id.position_salary_back);
    	back_btn.setBackgroundResource(R.drawable.cloud_back_click);
    	comment_list=(ListView) findViewById(R.id.comment_list);
    	
    	ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String,String>>();
    	//模拟评论数据
    	for(int i=0;i<5;i++){
    		HashMap<String, String> map=new HashMap<String, String>();
    		map.put("time", "20131212");
    		map.put("commentContent", "狗屁公司狗屁公司，骂的非常好，哈哈哈哈哈哈哈哈");
    		list.add(map);
    	}
    	BaseAdapter adapter=new CommentAdapter(this, list);
    	comment_list.setAdapter(adapter);
    }
     /**
      * 
      * @author 评论列表的数据adapter
      *
      */
     private class CommentAdapter extends  BaseAdapter{
         private Context context;
         private ArrayList<HashMap<String, String>> list;
         public CommentAdapter(Context context,ArrayList<HashMap<String, String>> list){
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
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView=LayoutInflater.from(context).inflate(R.layout.comments_list, null);
			commentTime_tv=(TextView) convertView.findViewById(R.id.commenttime_tv);
			comment_tv=(TextView) convertView.findViewById(R.id.comment_tv);
			commentTime_tv.setText(list.get(position).get("time"));
			comment_tv.setText(list.get(position).get("commentContent"));
			return convertView;
		}
    	 
     }
     
}
