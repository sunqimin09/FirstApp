package com.sun.hair.act;

import net.tsz.afinal.http.AjaxParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.hair.BaseAct;
import com.sun.hair.R;
import com.sun.hair.service.IRequestCallBack;
import com.sun.hair.service.RequestCheckService;
import com.sun.hair.utils.MConstant;
import com.sun.hair.utils.SpUtils;
/**
 * ��������
 * @author sunqm
 *
 */
public class AddCommentAct extends BaseAct implements IRequestCallBack{

	private EditText etInput;
	
	private TextView tvSubmit;
	
	int topicId =  -1;
	
	@Override
	public void initTitle() {
		setContentView(R.layout.act_add_comment);
		TextView tvLeft = (TextView) findViewById(R.id.act_title_left_tv);
		TextView tvCenter = (TextView) findViewById(R.id.act_title_center);
		tvSubmit = (TextView) findViewById(R.id.act_title_right_tv);
		View ll = (View) findViewById(R.id.act_title);
		tvLeft.setText("返回");
		tvSubmit.setText("提交");
		tvCenter.setText("添加评论");
		ll.setBackgroundResource(R.drawable.bg_top);
		
	}
	

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		topicId = getIntent().getIntExtra("id", -1);
		etInput = (EditText) findViewById(R.id.act_add_comment_et);
		etInput.addTextChangedListener(new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			if(arg0.length()>0){
				tvSubmit.setEnabled(true);
			}else{
				tvSubmit.setEnabled(false);
			}
		}
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}
		@Override
		public void afterTextChanged(Editable arg0) {
		}
	});
	}
	

	public void onClick(View view){
		switch(view.getId()){
		case R.id.act_title_right_tv://�ύ
			if(checkInput()){
				request();
			}
			break;
		case R.id.act_title_left_tv://ȡ��
			finish();
			break;
		}
	}
	
	private boolean checkInput(){
		if(etInput.getText()==null||etInput.getText().toString().equals("")){//Ϊ�յ�
			Toast.makeText(this, "亲，写点呗", Toast.LENGTH_SHORT).show();
			return false;
		}else if(topicId==-1){
			Toast("亲，话题数据错误了");
			return false;
		}
		return true;
	}
	
	/**
	 * �ύ����
	 */
	public void request(){
		AjaxParams params = new AjaxParams();
		
		params.put("comment",etInput.getText().toString());
		params.put("userid",new SpUtils(this).getId());
		Log.d("tag","request-->id"+topicId);
		params.put("id",topicId+"");
		new RequestCheckService().request(this, MConstant.URL_COMMENT_ADD, params, this);
	}


	@Override
	public void onSuccess(Object o) {
		Toast("评论成功");
		finish();
	}


	@Override
	public void onFailed(String msg) {
		Toast(msg);
		
	}


	
	
	
	
}
