package com.sun.hair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseAct extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		try{
			initTitle();
			initView();
		}catch(Exception e){
			Log.e("tag","base--ex"+e);
		}
	}
	
	public abstract void initTitle() ;

	public abstract void initView() ;

	public void Toast(String str){
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	
	public void setTitle_(String title){
		if(findViewById(R.id.act_title_center)!=null){
			TextView tv = ((TextView) findViewById(R.id.act_title_center));
			tv.setText(title);
		}
		
	}
	
	public void setLeftImg(){
		if(findViewById(R.id.act_title_left_img)!=null)
			((ImageView) findViewById(R.id.act_title_left_img)).setImageResource(R.drawable.left_arrow);
	}
	
	public void setRightTv(String str){
		(((TextView) findViewById(R.id.act_title_right_tv))).setText(str);
		
	}
	
	public void setTitleBack(){
		View view = findViewById(R.id.act_title);
		view.setBackgroundResource(R.drawable.bg_top);
	}
	
}
