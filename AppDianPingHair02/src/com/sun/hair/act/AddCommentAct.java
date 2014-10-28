package com.sun.hair.act;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sun.hair.R;
/**
 * 新增评论
 * @author sunqm
 *
 */
public class AddCommentAct extends Activity{

	private EditText etInput;
	
	private Button btnSubmit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_add_comment);
		etInput = (EditText) findViewById(R.id.act_add_comment_et);
		btnSubmit = (Button) findViewById(R.id.act_add_comment_submit);
		
	}
	
	public void onClick(View view){
		switch(view.getId()){
		case R.id.act_add_comment_submit://
			if(checkInput()){
				request();
			}
			break;
		}
	}
	
	private boolean checkInput(){
		if(etInput.getText()==null||etInput.getText().toString().equals("")){//为空的
			Toast.makeText(this, "写点呗，亲", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	/**
	 * 提交评论
	 */
	public void request(){
		
	}
	
	
	
}
