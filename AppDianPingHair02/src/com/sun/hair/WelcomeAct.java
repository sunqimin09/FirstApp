package com.sun.hair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.sun.jumi.JMPManager;

public class WelcomeAct extends Activity {

	JMPManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		manager = JMPManager.getInstance(getApplicationContext(),
				"821305a1-824c-4b26-abf3-daac29f6f41f", 1);
		// ≈‰÷√≤Â∆¡
		manager.c(1, 3, false);
		// ≈‰÷√Õ‚≤Â∆¡
		manager.o(true, false, 6, false, true, true);
		manager.s(this);
		// handler.sendEmptyMessageDelayed(0, 5000);
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			startActivity(new Intent(WelcomeAct.this, HomeAct.class));
		}

	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			manager.e(this);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	// @Override.
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// if (v.getId() == R.id.show_ad) {
		// manager.s(this);
		// } else if (v.getId() == R.id.exit_app) {
		// manager.e(this);
		// }
	}

}
