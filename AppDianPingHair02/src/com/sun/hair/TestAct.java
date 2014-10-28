package com.sun.hair;

import com.sun.hair.act.AddCommentAct;
import com.sun.hair.act.PhotoAct;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class TestAct extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_test);
	}
	
	public void onClick(View view){
		Intent i = new Intent();
		switch(view.getId()){
		case R.id.button1:
			i.setClass(TestAct.this, PhotoAct.class);
			break;
		case R.id.button2:
			i.setClass(TestAct.this, AddCommentAct.class);
			break;
		}
		startActivity(i);
	}
	
}
