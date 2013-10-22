package com.sun.app.timer;



import android.app.Activity;
import android.os.Bundle;

public class SimpleDeclaringLayout extends Activity {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 使用声明式布局，详见/res/layout/simple_declaring.xml
		setContentView(R.layout.simple);
	}
}