package com.sun.appquerysalary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
/**
 * 查询结果
 * 
 */
public class QueryResultAct extends Activity{

	private ListView listview;
	/**查询种类:公司0，岗位1*/
	private int queryType = 0;
	
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
