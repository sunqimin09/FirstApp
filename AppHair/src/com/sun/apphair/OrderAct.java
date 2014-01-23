/**
 * 
 */
package com.sun.apphair;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * 项目名称：Hair
 * 文件名：OrderAct.java  
 * 作者：@sunqm    
 * 创建时间：2014-1-22 下午4:11:50
 * 功能描述:  
 * 版本 V 1.0               
 */
public class OrderAct extends BaseAct implements OnClickListener{

	private Button btn_Date,btn_submit;
	
	private TextView tv_money;
	
	private EditText et_content;
	
	private AlertDialog timeDialog = null;
	
	private TimePicker timePicker = null;
	
	private DatePicker datePicker = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_act);
		initView();
	}

	/**
	 * 
	 */
	private void initView() {
//		btn_Date = (Button) findViewById(R.id.order_btn_date);
		btn_submit = (Button) findViewById(R.id.order_btn_submit);
		tv_money = (TextView) findViewById(R.id.order_money);
		et_content = (EditText) findViewById(R.id.order_et);
		View view = View.inflate(this, R.layout.date_time_picker, null);
		timeDialog = new AlertDialog.Builder(this).setView(view).create();
		timePicker = (TimePicker) view.findViewById(R.id.order_date_timePicker1);
		datePicker = (DatePicker) view.findViewById(R.id.order_date_datePicker1);
		view.findViewById(R.id.order_date_ok).setOnClickListener(this);
		view.findViewById(R.id.order_date_cancel).setOnClickListener(this);
		
	}
	
	public void OnClick(View view){
		switch(view.getId()){
//		case R.id.order_btn_date://选择日期
//			timeDialog.show();
//			break;
		case R.id.order_btn_submit://提交订单
			
			break;
		case R.id.order_date_cancel://取消
			break;
		case R.id.order_date_ok://选择日期
//			timePicker.getCurrentHour()+timePicker.getCurrentMinute();
			break;
		}
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}
