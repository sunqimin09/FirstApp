package com.example.msalary.activity;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.msalary.R;

import com.example.msalary.entity.CompanyCommentEntity;
import com.example.msalary.entity.CompanyEntity;
import com.example.msalary.entity.RequestEntity;
import com.example.msalary.entity.ResponseResult;
import com.example.msalary.entity.ShowResult;
import com.example.msalary.internet.IRequestCallBack;
import com.example.msalary.internet.InternetHelper;
import com.example.msalary.json.JsonCommentsOfCompany;
import com.example.msalary.json.JsonSelectConpany;
import com.example.msalary.util.MConstant;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 
 * @author Administrator评论的界面
 *
 */
public class CommentActivity extends BaseActivity implements OnClickListener{
	private ImageButton back_btn;
	private TextView comment_title;
	private TextView commentTime_tv;//评论的时间
	private TextView comment_tv;//评论内容
	private ListView comment_list;//以往评论列表
	private ShowResult showResult;
	private CommentAdapter adapter;
	private EditText make_comment_et;
	private Button make_comment_btn;
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
    	
    	initView();
    	request(getIntent().getIntExtra("companyId", 0));

    }
     protected void initView(){
    	 comment_list=(ListView) findViewById(R.id.comment_list);
    	 comment_title = (TextView) findViewById(R.id.comment_company_name);
    	 comment_title.setText(getIntent().getStringExtra("companyName"));
    	 showResult=new ShowResult();
    	 adapter=new CommentAdapter(this, showResult);
    	 comment_list.setAdapter(adapter);
    	 make_comment_et=(EditText) findViewById(R.id.make_comments_et);
    	 make_comment_btn=(Button) findViewById(R.id.make_comments_btn);
     }
     /**
 	 * 发起网络请求
 	 * @param requestStr
 	 */
 	private void request(int requestStr){
 		getString(R.string.url_home);
 		RequestEntity requestEntity =new RequestEntity(this,MConstant.URL_COMPANY_COMMENT);
 		HashMap<String,Object> map = new HashMap<String,Object>();
 		map.put("key", requestStr);
 		requestEntity.params = map;
 		new InternetHelper().requestThread(requestEntity, this);
 	}
	@Override
 	public void requestSuccess(ResponseResult responseResult) {
 		Log.d("tag","showResult"+responseResult);
 		showResult = JsonCommentsOfCompany.parse(responseResult,this);
 		adapter.setData(showResult);
 	}
 	private void request(String createDate,String content){
 		getString(R.string.url_home);
 		RequestEntity requestEntity =new RequestEntity(this,MConstant.URL_COMPANY_COMMENT_NEW);
 		HashMap<String,Object> map = new HashMap<String,Object>();
 		map.put("createDate", createDate);
 		map.put("commentContent", content);
 		requestEntity.params = map;
 
 		new InternetHelper().requestThread(requestEntity, callback1);
 	}
	IRequestCallBack callback1=new IRequestCallBack() {
		
		@Override
		public void requestSuccess(ResponseResult responseResult) {
			// TODO Auto-generated method stub
			make_comment_et.setText("");
			Toast("评论发表成功");
		}
		
		@Override
		public void requestFailedStr(String str) {
			// TODO Auto-generated method stub
			
		}
	};
 
     /**
      * 
      * @author 评论列表的数据adapter
      *
      */
     private class CommentAdapter extends  BaseAdapter{
         private Context context;
         private ArrayList<CompanyCommentEntity> list;
         public CommentAdapter(Context context,ShowResult result){
        	 this.context=context;
        	 this.list=(ArrayList<CompanyCommentEntity>)result.list;
         }
         public void setData(ShowResult result){
     		this.list = (ArrayList<CompanyCommentEntity>) result.list;
     		this.notifyDataSetChanged();
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
			ViewHolder holder;
			if(convertView==null){
				holder=new ViewHolder();
				convertView=LayoutInflater.from(context).inflate(R.layout.comments_list, null);
				holder.commentTime_tv=(TextView) convertView.findViewById(R.id.commenttime_tv);
				holder.comment_tv=(TextView) convertView.findViewById(R.id.comment_tv);
				  convertView.setTag(holder); 
			}else{
				 holder = (ViewHolder) convertView.getTag();
			}
			holder.commentTime_tv.setText(list.get(position).getCreateDate());
			holder.comment_tv.setText(list.get(position).getContent());
			
			return convertView;
		}
		class ViewHolder{
			public TextView commentTime_tv;
			public TextView comment_tv;
		}
    	 
     }
     /**
      * 点击事件
      */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.make_comments_btn:
			   if(make_comment_et.getText().toString()!=null){
				   SimpleDateFormat  sDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");    
				   String date= sDateFormat.format(new java.util.Date());
				   request(date,make_comment_et.getText().toString());
			   }else{
				   Toast("请添加评论内容");
			   }
			break;
		case R.id.comment_company_name://
			finish();
			break;
		default:
			break;
		}
	}
     
}
