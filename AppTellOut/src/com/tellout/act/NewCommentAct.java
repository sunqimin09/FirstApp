package com.tellout.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
/**
 * 新建一个吐槽评论
 * @author sunqm
 * Create at:   2013-11-10 上午11:09:20 
 * TODO
 */
public class NewCommentAct extends BaseActivity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_comment_act);
		initView();
	}

	private void initView() {
		findViewById(R.id.back).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case  R.id.back://返回
			finish();
			break;
		}
	}
	
}
