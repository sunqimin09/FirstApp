package com.sun.appquerysalary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class CompanyAct extends Activity{

	private ListView listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.companyact);
		initView();
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.company_listview);
		
		
	}
	
}
