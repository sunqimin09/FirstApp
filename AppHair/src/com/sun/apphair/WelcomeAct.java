/**
 * 
 */
package com.sun.apphair;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;

/**
 * 项目名称：Hair<br>
 * 文件名：BaseAct.java BaseAct <br>
 * 作者：@sunqm    <br>
 * 创建时间：2014-1-18 上午11:59:02
 * 功能描述: 终端详情 <br>
 * 版本 V 1.0 <br>               
 */
public class WelcomeAct extends BaseAct{

	private long delay = 1*1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_act);
		timer.schedule(task, delay);
	}
	
	Timer timer = new Timer();
	
	TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			startActivity(new Intent(WelcomeAct.this,LocationOverlayDemo.class));
			finish();
		}
	};
	
}
