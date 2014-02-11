/**
 * 
 */
package com.sun.apphair;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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
public class OrderAct extends BaseAct implements OnClickListener, TextWatcher{

	private Button btn_Date,btn_submit;
	
	private TextView tv_money;
	
	private TextView tv_unit_price;
	
	private TextView tv_total_price;
	
	private EditText et_content;
	
	private EditText et_number;
	
	private Button btn_plus;
	
	private Button btn_decrease;
	
	private AlertDialog timeDialog = null;
	
	private TimePicker timePicker = null;
	
	private DatePicker datePicker = null;
	
	/**单价*/
	private int unitPrice = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_act);
		initView();
		initData();
	}

	private void initData() {
		tv_unit_price.setText("￥"+unitPrice+"元");
		
	}

	/**
	 * 
	 */
	private void initView() {
//		btn_Date = (Button) findViewById(R.id.order_btn_date);
		btn_submit = (Button) findViewById(R.id.order_btn_submit);
		tv_money = (TextView) findViewById(R.id.order_money);
		tv_unit_price = (TextView) findViewById(R.id.order_tv_unitprice);
		tv_total_price = (TextView) findViewById(R.id.order_tv_total_price);
		et_number = (EditText) findViewById(R.id.order_et_number);
		et_content = (EditText) findViewById(R.id.order_et);
		btn_plus = (Button) findViewById(R.id.order_img_add);
		btn_decrease = (Button) findViewById(R.id.order_img_decrease);
		View view = View.inflate(this, R.layout.date_time_picker, null);
		timeDialog = new AlertDialog.Builder(this).setView(view).create();
		timePicker = (TimePicker) view.findViewById(R.id.order_date_timePicker1);
		datePicker = (DatePicker) view.findViewById(R.id.order_date_datePicker1);
		view.findViewById(R.id.order_date_ok).setOnClickListener(this);
		view.findViewById(R.id.order_date_cancel).setOnClickListener(this);
		et_number.addTextChangedListener(this);
	}
	
	public void OnClick(View view){
		switch(view.getId()){
		case R.id.order_img_decrease:
			String content = et_number.getText().toString().equals("")?"0":et_number.getText().toString();
			et_number.setText(""+(Integer.parseInt(content)-1));;
			break;
		case R.id.order_img_add:
			String content_add = et_number.getText().toString().equals("")?"0":et_number.getText().toString();
			et_number.setText(""+(Integer.parseInt(content_add)+1));;
			break;
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

	@Override
	public void afterTextChanged(Editable arg0) {
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		Log.d("tag", "on" + arg0.toString().equals(""));
		if (arg0.toString().equals("") ) {
			btn_decrease.setEnabled(false);
			return;
		}
		if(Integer.parseInt(arg0.toString())<1){
			btn_decrease.setEnabled(false);
		}else if(Integer.parseInt(arg0.toString())>99){
			btn_plus.setEnabled(false);
		}else{
			tv_total_price.setText("￥"+unitPrice*Integer.parseInt(arg0.toString())+"元");
			btn_decrease.setEnabled(true);
			btn_plus.setEnabled(true);
		}
	}
	
}
