package com.example.msalary.activity;

import com.example.msalary.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
/**
 * 我要曝工资的界面
 * @author Administrator
 *
 */
public class ExposureSalary extends Activity {
	private ImageButton back_btn;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	//自定义标题
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    	setContentView(R.layout.exposure_salary_main);
    	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebutton);
    	//返回键
    	back_btn=(ImageButton) findViewById(R.id.exposure_salary_back);
    	back_btn.setBackgroundResource(R.drawable.cloud_back_click);
    }
}
