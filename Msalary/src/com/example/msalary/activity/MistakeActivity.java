package com.example.msalary.activity;

import com.example.msalary.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
/**
 * æ¿¥ÌΩÁ√Ê
 * @author Administrator
 *
 */
public class MistakeActivity extends BaseActivity {
	private TextView mistake_companyName;
	private TextView mistake_jobName;
	private EditText mistake_salary_et;
	private EditText mistake_critical_et;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.mistake_main);
    }
     protected void initView(){
    	 super.initView();
    	 tv_title.setText(getString(R.string.mistake_title));
    	 mistake_companyName=(TextView) findViewById(R.id.mistake_companyName);
    	 mistake_jobName=(TextView) findViewById(R.id.mistake_critical_et);
    	 mistake_salary_et=(EditText) findViewById(R.id.mistake_salary_et);
    	 mistake_critical_et=(EditText) findViewById(R.id.mistake_critical_et);
    	 mistake_companyName.setText(getIntent().getStringExtra("companyName"));
    	 mistake_jobName.setText(getIntent().getStringExtra("jobName"));
     }
}
