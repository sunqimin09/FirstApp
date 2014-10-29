package com.sun.hair;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseAct extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		initTitle();
		initView();
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
	
}
