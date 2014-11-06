package com.sun.hair.act;

import net.tsz.afinal.http.AjaxParams;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sun.hair.BaseAct;
import com.sun.hair.R;
import com.sun.hair.service.CommentsService;
import com.sun.hair.service.IRequestCallBack;
import com.sun.hair.service.RequestCheckService;
import com.sun.hair.utils.CheckInput;
import com.sun.hair.utils.MConstant;

/**
 * 修改我的个人信息
 * @author sunqm
 *
 */
public class MyInforEditAct extends BaseAct implements IRequestCallBack{

	private EditText etName,etSign;
	
	@Override
	public void initTitle() {
		setContentView(R.layout.act_edit_myinfor);
		TextView tv = (TextView) findViewById(R.id.act_title_center);
		tv.setText("个人信息编辑");
		TextView tvsubmit = (TextView) findViewById(R.id.act_title_right_tv);
		tvsubmit.setText("提交");
		TextView tvback = (TextView) findViewById(R.id.act_title_left_tv);
		tvback.setText("返回");
		findViewById(R.id.act_title).setBackgroundResource(R.drawable.bg_top);
	}

	@Override
	public void initView() {
		etSign = (EditText) findViewById(R.id.act_edit_myinfor_sign);
		etName = (EditText) findViewById(R.id.act_edit_myinfor_name);
		etName.setText(getIntent().getStringExtra("name"));
		etSign.setText(getIntent().getStringExtra("sign"));
	}
	
	public void onClick(View view){
		switch(view.getId()){
		case R.id.act_title_right_tv://提交
			if(checkInput()){
				request();
			}
			break;
		case R.id.act_title_left_tv://返回上一页面
			finish();
			break;
		}
	}
	
	private boolean checkInput(){
		if(CheckInput.isNull(etName)){
			Toast("昵称不能为空");
			return false;
		}else if(CheckInput.isNull(etSign)){
			Toast("签名不能为空");
			return false;
		}else{
			return true;
		}
	}
	
	private void request(){
		AjaxParams params = new AjaxParams();
		params.put("name",etName.getText().toString());
		params.put("pwd",etSign.getText().toString());
		new RequestCheckService().request(this, MConstant.URL_MYINFOR_EDIT, params, this);
	}

	@Override
	public void onSuccess(Object o) {
		
		
	}

	@Override
	public void onFailed(String msg) {
		
		
	}
	
	
}
