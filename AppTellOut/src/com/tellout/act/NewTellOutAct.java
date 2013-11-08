package com.tellout.act;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tellout.constant.DbConstant;
import com.tellout.constant.MConstant;
import com.tellout.entity.BaseEntity;
import com.tellout.entity.RequestEntity;
/**
 * 
 * @author sunqm
 * Create at:   2013-11-6 下午8:59:45 
 * TODO	新建吐槽
 */
public class NewTellOutAct extends BaseActivity implements OnClickListener{
	
	/**返回*/
	private ImageView imgBack;
	/**保存--提交*/
	private ImageView imgSave;
	/**输入内容*/
	private EditText etInput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_tell_out_act);
		initView();
	}

	private void initView() {
		imgBack = (ImageView) findViewById(R.id.new_tell_out_back);
		imgSave = (ImageView) findViewById(R.id.new_tell_out_save);
		etInput = (EditText) findViewById(R.id.new_tell_out_input_et);
		imgBack.setOnClickListener(this);
		imgSave.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.new_tell_out_back:
			finish();
			break;
		case R.id.new_tell_out_save:
			String tempStr = etInput.getText().toString();
			if(tempStr!=null&&tempStr.length()>5){
				Map<String,String> map = new HashMap<String,String>();
				map.put(DbConstant.DB_TELLOUT_CONTENT, tempStr);
				map.put(DbConstant.DB_TELLOUT_AUTHOR, MConstant.USER_ID_VALUE);
				RequestEntity requestEntity = new RequestEntity();
				requestEntity.setParams(map);
				requestEntity.setPost(false);
				requestEntity.setRequestType(MConstant.REQUEST_CODE_NEW_TELLOUT);
				request(requestEntity);
			}else{
				Toast.makeText(this,"您写的太少了", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	@Override
	public void showResult(int type, BaseEntity baseEntity) {
		super.showResult(type, baseEntity);
		Toast.makeText(this,"提交成功!", Toast.LENGTH_SHORT).show();
		finish();
	}
	
	
}
