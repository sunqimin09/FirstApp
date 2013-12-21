package com.sun.appquerysalary;

import com.sun.appquerysalary.entity.RequestEntity;
import com.sun.appquerysalary.entity.ShowResult;
import com.sun.appquerysalary.interface_.IRequestCallBack;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnCheckedChangeListener, IRequestCallBack {

	private EditText input_et;
	
	MainActUtils mainActUtils = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		
	}

	private void initView() {
		input_et = (EditText) findViewById(R.id.input_et);
		RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup1);
		group.setOnCheckedChangeListener(this);
	}

	public void onClick(View view){
		switch(view.getId()){
		case R.id.query_btn:
			RequestEntity requestEntity = null;
			mainActUtils.request(requestEntity, this);
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		
		switch(arg1){
		case R.id.radio0:
			Log.d("tag","ar0-->");
			break;
		case R.id.radio1:
			Log.d("tag","ar1-->");
			break;
		}
	}

	@Override
	public void requestSuccess(ShowResult showResult) {
		switch(showResult.requestCode){
		case 0:
			break;
		case 1:
			break;
		}
		
	}

	@Override
	public void requestFailedStr(String str) {
		Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
	}
}
