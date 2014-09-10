package com.sun.appdianpinghair;

import android.os.Bundle;
import android.view.MenuItem;

/**
 * 支付页面
 * @author sunqm
 * Create at:   2014-6-30 下午8:31:48 
 * TODO
 */
public class PayAct extends BaseAct{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_pay);
		initView();
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	private void initView(){
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
