package com.example.msalary.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.msalary.R;
import com.example.msalary.entity.CompanyCommentEntity;
import com.example.msalary.entity.RequestEntity;
import com.example.msalary.entity.ResponseResult;
import com.example.msalary.entity.ShowResult;
import com.example.msalary.internet.InternetHelper;
import com.example.msalary.json.JsonCommentsOfCompany;
import com.example.msalary.json.JsonSuccessOrFail;
import com.example.msalary.util.MConstant;
/**
 * 
 * @author Administrator评论的界面
 *
 */
public class CommentActivity extends BaseActivity implements OnClickListener{
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
    	setContentView(R.layout.comment_main);
    	initView();
    	request(0);

    }
     protected void initView(){
    	 super.initView();
    	 tv_title.setText(getString(R.string.comment_title));
    	 comment_list=(ListView) findViewById(R.id.comment_list);
    	 comment_title = (TextView) findViewById(R.id.comment_company_name);
    	 comment_title.setText(getIntent().getStringExtra("companyName"));
    	 showResult=new ShowResult();
    	 adapter=new CommentAdapter(this, showResult);
    	 comment_list.setAdapter(adapter);
    	 make_comment_et=(EditText) findViewById(R.id.make_comments_et);
    	 make_comment_btn=(Button) findViewById(R.id.make_comments_btn);
     }
     
     @Override
 	public void onClick(View v) {
 		// TODO Auto-generated method stub
 		switch (v.getId()) {
 		case R.id.make_comments_btn:
 			String temp = make_comment_et.getText().toString();
 			if(checkInput(temp))
 				request(1,temp);
// 			   if(make_comment_et.getText().toString()!=null){
// 				   SimpleDateFormat  sDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");    
// 				   String date= sDateFormat.format(new java.util.Date());
// 				   
// 			   }else{
// 				   Toast("请添加评论内容");
// 			   }
 			break;
 		case R.id.back://
 			finish();
 			break;
 		case R.id.comment_company_name:
 			finish();
 			break;
 		default:
 			break;
 		}
 	}
     
     private boolean checkInput(String temp){
    	 if(temp.equals("")){
    		 Toast("您写的太少了吧");
    		 return false;
    	 }else if(temp.trim().length()>100){
    		 Toast("评论内容太长了");
    		 return false;
    	 }
    	 
    	 return true;
     }
     
     private void request(int requestCode,String... arg0){
    	 RequestEntity requestEntity = null;
    	 HashMap<String,Object> map = new HashMap<String,Object>();
    	 switch(requestCode){
    	 case 0://
    		 requestEntity =new RequestEntity(this,MConstant.URL_COMPANY_COMMENT);
    		 map.put("key",getIntent().getIntExtra("companyId", 0));
    		 break;
    	 case 1://
    		 requestEntity =new RequestEntity(this,MConstant.URL_COMPANY_COMMENT_NEW);
    		 map.put("companyId",getIntent().getIntExtra("companyId", 0));
    	 	 map.put("commentContent", arg0[0]);
    		 break;
    	 }
    	 requestEntity.requestCode = requestCode;
    	 requestEntity.params = map;
  		 new InternetHelper(this).requestThread(requestEntity, this);
     }
     
     
     
     /**
 	 * 发起网络请求
 	 * @param requestStr
 	 */
// 	private void request(int requestStr){
// 		RequestEntity requestEntity =new RequestEntity(this,MConstant.URL_COMPANY_COMMENT);
// 		HashMap<String,Object> map = new HashMap<String,Object>();
// 		map.put("key", requestStr);
// 		requestEntity.params = map;
// 		new InternetHelper(this).requestThread(requestEntity, this);
// 	}
	@Override
 	public void requestSuccess(ResponseResult responseResult) {
 		Log.d("tag","showResult"+responseResult);
 		switch(responseResult.requestCode){
 		case 0://评论列表
 			showResult = JsonCommentsOfCompany.parse(responseResult,this);
 	 		adapter.setData(showResult);
 			break;
 		case 1://发表评论
 			showResult = JsonSuccessOrFail.parse(responseResult, this);
 			if(showResult!=null&&showResult.resultCode==0){
 				Toast("提交成功");
 				make_comment_et.setText("");
 				request(0); 			}
 			break;
 		}
 		
 	}
// 	private void request(String createDate,String content){
// 		RequestEntity requestEntity =new RequestEntity(this,MConstant.URL_COMPANY_COMMENT_NEW);
// 		HashMap<String,Object> map = new HashMap<String,Object>();
// 		map.put("createDate", createDate);
// 		map.put("commentContent", content);
// 		requestEntity.params = map;
// 
// 		new InternetHelper(this).requestThread(requestEntity, callback1);
// 	}
// 	
//	IRequestCallBack callback1=new IRequestCallBack() {
//		
//		@Override
//		public void requestSuccess(ResponseResult responseResult) {
//			// TODO Auto-generated method stub
//			make_comment_et.setText("");
//			Toast("评论发表成功");
//		}
//		
//		@Override
//		public void requestFailedStr(String str) {
//			// TODO Auto-generated method stub
//			
//		}
//	};
 
     /**
      * 
      * @author 评论列表的数据adapter
      *
      */
     private class CommentAdapter extends BaseAdapter{
         private Context context;
         private ArrayList<CompanyCommentEntity> list;
         @SuppressWarnings("unchecked")
		public CommentAdapter(Context context,ShowResult result){
        	 this.context=context;
        	 this.list=(ArrayList<CompanyCommentEntity>)result.list;
         }
         @SuppressWarnings("unchecked")
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
			if(list.get(position).getCreateDate()!=null&&list.get(position).getCreateDate().length()>10)
				holder.commentTime_tv.setText(list.get(position).getCreateDate().substring(0, 10));
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
	
     
}
