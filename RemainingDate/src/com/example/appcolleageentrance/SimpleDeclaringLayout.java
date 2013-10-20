package com.example.appcolleageentrance;


import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class SimpleDeclaringLayout extends Activity {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 使用声明式布局，详见/res/layout/simple_declaring.xml
		setContentView(R.layout.simple);
	}
}