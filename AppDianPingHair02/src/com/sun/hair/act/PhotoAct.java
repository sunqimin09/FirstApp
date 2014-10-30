package com.sun.hair.act;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;
import android.R.color;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.hair.BaseAct;
import com.sun.hair.R;
import com.sun.hair.adapter.CommentsAdapter;
import com.sun.hair.entity.CommentEntity;
import com.sun.hair.entity.FamousEntity;
import com.sun.hair.service.CommentsService;
import com.sun.hair.service.IRequestCallBack;
import com.sun.hair.utils.MConstant;

public class PhotoAct extends BaseAct implements IRequestCallBack{
	/**楼主头像*/
	private ImageView imgIcon;
	
	private TextView tvNickName;
	
	private TextView tvTime;
	
	private TextView tvConent;
	
	private TextView tvNoComment;
	
	private ImageView imgContent;
	
	private ImageView img1,img2,img3,img4;
	
	private TextView tvNumber;
	
	private ListView listView ;
	
	private CommentsAdapter adapter;
	
	private FamousEntity entity;
	
	private List<CommentEntity> comments = new ArrayList<CommentEntity>();
	
	@Override
	public void initTitle() {
		setContentView(R.layout.act_photo);
		View view = findViewById(R.id.act_title);
		TextView tv = (TextView) findViewById(R.id.act_title_center);
		tv.setText("话题");
		view.setBackgroundResource(R.drawable.bg_top);
	}

	
	public void initView() {
		
		imgIcon = (ImageView) findViewById(R.id.act_photo_icon);
		tvNickName = (TextView) findViewById(R.id.act_photo_nickname);
		tvTime = (TextView) findViewById(R.id.act_photo_time);
		tvConent = (TextView) findViewById(R.id.act_photo_description);
		tvNoComment = (TextView) findViewById(R.id.act_photo_no_comment_tv);
		imgContent = (ImageView) findViewById(R.id.act_photo_img);
		img1 = (ImageView) findViewById(R.id.act_photo_img1);
		img2 = (ImageView) findViewById(R.id.act_photo_img2);
		img3 = (ImageView) findViewById(R.id.act_photo_img3);
		img4 = (ImageView) findViewById(R.id.act_photo_img4);
		tvNumber = (TextView) findViewById(R.id.act_photo_ok_number);
		listView = (ListView) findViewById(R.id.act_photo_listview);
		tvNoComment.setText("快来抢沙发吧!");
		initData();
	}
	
	private void initData() {
		try{
			entity = (FamousEntity) getIntent().getSerializableExtra("photo");
		}catch(Exception e){
			e.printStackTrace();
			Log.d("tag","log--init"+entity+e);
		}
		
		
		if(entity==null){
			Toast.makeText(this, "数据错误", Toast.LENGTH_SHORT).show();
			return;
		}
			
		tvNickName.setText(entity.name);
		tvTime.setText(entity.time);
		tvConent.setText(entity.introduce);
		tvNumber.setText("等"+entity.ok_num+"人赞过");
		adapter  = new CommentsAdapter(this);
		listView.setAdapter(adapter);
		if(entity!=null){
			request();
		}
	}
	
	public void onClick(View view){
		switch(view.getId()){
		case R.id.act_photo_ok://赞
			try{
				showZan();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			break;
		case R.id.act_photo_no_comment_tv://
		case R.id.act_photo_add://新增评论
			startActivity(new Intent(PhotoAct.this,AddCommentAct.class));
			break;
		case R.id.act_photo_share://分享
			break;
		}
	}
	
	private void request(){
		AjaxParams params = new AjaxParams();
		params.put("id",String.valueOf(entity.id));
		new CommentsService().request(this, MConstant.URL_COMMENTS, params, this);
	}
	
	private void requestZan(){
		AjaxParams params = new AjaxParams();
		params.put("id",String.valueOf(entity.id));
		new CommentsService().request(this, MConstant.URL_COMMENTS, params, this);
	}
	
	private void showZan(){
		Toast t = new Toast(this);
		TextView tv = new TextView(this);
		tv.setText("+1");
		tv.setTextColor(Color.RED);
		t.setView(tv);
		t.setGravity(Gravity.BOTTOM|Gravity.LEFT, 90, 100);
		t.show();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(Object o) {
		if(o instanceof List<?>){
			comments = (List<CommentEntity>) o;
			adapter.setData(comments);
			if(comments.size()==0){
				tvNoComment.setText("快来抢沙发吧!");
			}else{
				tvNoComment.setText("");
			}
		}
		
	}

	@Override
	public void onFailed(String msg) {
		// TODO Auto-generated method stub
		tvNoComment.setText("快来抢沙发吧!");
	}

	
	
	
	
}
