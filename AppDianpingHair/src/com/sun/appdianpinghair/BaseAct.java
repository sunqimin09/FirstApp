package com.sun.appdianpinghair;

import android.app.Activity;
import android.widget.Toast;

public class BaseAct extends Activity{
	
	public void Toast(String text){
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	
}
