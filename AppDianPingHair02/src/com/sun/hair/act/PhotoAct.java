package com.sun.hair.act;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;
import android.R.color;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import com.sun.hair.service.RequestCheckService;
import com.sun.hair.utils.MConstant;
import com.sun.hair.utils.SpUtils;
import com.sun.hair.utils.Utils;

public class PhotoAct extends BaseAct implements IRequestCallBack{
	/**¥��ͷ��*/
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
		setLeftImg();
		setTitle_("广场");
		setTitleBack();
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
		adapter  = new CommentsAdapter(this);
		listView.setAdapter(adapter);
		if(entity==null){
			Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
			request();
			return;
		}
		tvNickName.setText(entity.name);
		tvTime.setText(entity.time);
		tvConent.setText(entity.introduce);
		tvNumber.setText("等"+entity.ok_num+"人赞了");
		
		if(entity!=null){
			request();
		}
	}
	
	public void onClick(View view){
		switch(view.getId()){
		case R.id.act_title_left_img:
			finish();
			break;
		case R.id.act_photo_ok://��
			if(entity!=null){
				if(new SpUtils(this).getId().equals("0")){//尚未登录
					showLoginDialog();
				}else{
					showZan();
					requestZan();
				}
			}else
				Toast("数据丢失，请重新进入程序");
			
			
			break;
		case R.id.act_photo_no_comment_tv://
		case R.id.act_photo_add://
			if(entity!=null)
				startActivity(new Intent(PhotoAct.this,AddCommentAct.class).putExtra("id", entity.id));
			else
				Toast("数据丢失，请重新进入程序");
			break;
		case R.id.act_photo_share://����
			Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性

			intent.setType("text/plain"); // 分享发送的数据类型

//			intent.setPackage(packAgeName);

			intent.putExtra(Intent.EXTRA_SUBJECT, "分享"); // 分享的主题

			intent.putExtra(Intent.EXTRA_TEXT, "我在秀发型中看到了一个好看的发型,你也来一起玩吧"); // 分享的内容

			startActivity(Intent.createChooser(intent, "选择分享"));// 目标应用选择对话框的标题
			break;
		}
	}
	
	private void request(){
		AjaxParams params = new AjaxParams();
		if(entity!=null)
			params.put("id",String.valueOf(entity.id));
		else
			params.put("id",String.valueOf(0));
		new CommentsService().request(this, MConstant.URL_COMMENTS, params, this);
	}
	
	private void requestZan(){
		AjaxParams params = new AjaxParams();
		params.put("id",String.valueOf(entity.id));
		params.put("userid", new SpUtils(this).getId());
		params.put("imei", Utils.getImei(this));
		new RequestCheckService().request(this, MConstant.URL_COMMENTS, params, this);
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
	
	private void showLoginDialog(){
		   final Dialog alertDialog = new AlertDialog.Builder(this). 
	                setTitle("是否登录?"). 
	                setPositiveButton("是", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							startActivity(new Intent(PhotoAct.this,LoginAct.class));
						}
					}).
					setNegativeButton("滚", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							try{
								arg0.dismiss();
							}catch(Exception e){
								e.printStackTrace();
							}
							showZan();
							requestZan();
						}
					}).
	                create(); 
	        alertDialog.show(); 
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(Object o) {
		
		if(o instanceof List<?>){
			comments = (List<CommentEntity>) o;
			Toast("onsuccess"+comments.size());
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
