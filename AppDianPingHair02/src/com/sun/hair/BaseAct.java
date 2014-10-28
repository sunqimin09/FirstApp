package com.sun.hair;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public abstract class BaseAct extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		initTitle();
		initView();
	}
	

	public abstract void initView() ;

	
	public abstract void initTitle() ;

	
	
	
	
}
