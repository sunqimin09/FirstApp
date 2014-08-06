package com.example.appnaifen;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	LinearLayout llLeft;
	
	LinearLayout llRight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		
	}
	
	private void initView(){
		llLeft = (LinearLayout) findViewById(R.id.ll_left);
		llRight = (LinearLayout) findViewById(R.id.ll_right);
		
		for(int i=0;i<20;i++){
			ImageView img = new ImageView(this);
			img.setImageResource(R.drawable.ic_launcher);
			llLeft.addView(img);
		}
		
	}
	
	
	
}
