package com.example.msalary.activity;

import com.example.msalary.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
/**
 * æ¿¥ÌΩÁ√Ê
 * @author Administrator
 *
 */
public class MistakeActivity extends Activity {
	private ImageButton back_btn;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    	setContentView(R.layout.exposure_salary_main);
    	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebutton);
    	back_btn=(ImageButton) findViewById(R.id.mistake_back);
    	back_btn.setBackgroundResource(R.drawable.cloud_back_click);
    }
}
