package com.tellout.act;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;

import com.tellout.constant.DbConstant;
import com.tellout.constant.MConstant;
import com.tellout.entity.BaseEntity;
import com.tellout.entity.RequestEntity;
/**
 * 新建一个吐槽评论
 * @author sunqm
 * Create at:   2013-11-10 上午11:09:20 
 * TODO
 */
public class NewCommentAct extends BaseActivity implements OnClickListener{
	
	/**当前吐槽的id*/
	private int TellOutId = 0;
	
	private EditText etInput = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_comment_act);
		initView();
	}

	private void initView() {
		TellOutId = getIntent().getIntExtra(DbConstant.DB_TELLOUT_ID, 0);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.new_comment_save).setOnClickListener(this);
		etInput = (EditText) findViewById(R.id.new_comment_input_et);
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case  R.id.back://返回
			finish();
			break;
		case R.id.new_comment_save://保存
			if(!MConstant.USER_ID_VALUE.equals("")){
				String input = etInput.getText().toString().trim();
				if(input==null){
					Toast("您输入的太少了");
				}else{
					request(input);
				}
			}else{
				startActivity(new Intent(NewCommentAct.this,Login_Regist_Act.class));
			}
			break;
		}
	}
	
	/**
	 * 提交请求
	 */
	private void request(String input) {
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setRequestType(MConstant.REQUEST_CODE_NEW_COMMENT);
		requestEntity.setPost(false);
		Map<String,String> map = new HashMap<String,String>();
		map.put(DbConstant.DB_COMMENT_CONTENT, input);
		map.put(DbConstant.DB_COMMENT_TELLOUT_ID, TellOutId+"");
		map.put(DbConstant.DB_COMMENT_AUTHOR_ID, MConstant.USER_ID_VALUE);
		requestEntity.setParams(map);
		request(requestEntity);
	}

	@Override
	public void showResult(int type, BaseEntity baseEntity) {
		super.showResult(type, baseEntity);
		Toast("评论成功");
//		startActivity(new Intent(NewCommentAct.this,TelloutDetailAct.class));
		finish();
	}
	
	
}
