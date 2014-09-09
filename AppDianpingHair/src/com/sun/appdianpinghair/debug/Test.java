package com.sun.appdianpinghair.debug;

import android.os.Bundle;
import android.util.Log;

import com.sun.appdianpinghair.BaseAct;
import com.sun.appdianpinghair.R;

public class Test extends BaseAct{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_pay);
		new Thread(){

			@Override
			public void run() {
				for(int i=0;i<10000;i++){
					Log.d("tag","log"+i);
				}
			}
			
			
		}.start();
		
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
