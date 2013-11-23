package com.tellout.act;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tellout.constant.DbConstant;
import com.tellout.constant.MConstant;
import com.tellout.entity.BaseEntity;
import com.tellout.entity.RequestEntity;
import com.tellout.entity.UserEntity;

public class EditMyInforAct extends BaseActivity implements OnClickListener,
		OnRatingBarChangeListener, OnTouchListener {

	private EditText etNickName, etSalary;
	// , etEnvironment, etFuture, etOther,
	// etSalaryPer, etEnvironmentPer,etFuturePer,etOtherPer;

	private RatingBar ratingBar = null;
	/** 地区，行业，公司 */
	private RelativeLayout rlRegion = null;

	private RelativeLayout rlIndustry = null;

	// private RelativeLayout rlCompany = null;

	private RelativeLayout rlstartTime = null;

	private TextView tvRegion = null;

	private TextView tvIndustry = null;

	private TextView tvCompany = null;

	private EditText etCompany = null;

	private TextView tvStartTime = null;

	private Button btnCancel, btnSave;

	private ImageView imgIcon;

	private EditText etMyComment;

	/** 编辑 */
	private boolean edit = false;

	/** 地区id */
	private int regionId = 0;

	/** 行业id */
	private int industryId = 0;

	/** 行业具体描述信息 */
	private String industryStr = null;

	/** 行业详细信息 */
	private String industryDetail = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_myinfor_act);
		// setTitle("我的信息");
		// setTitleColor(Color.WHITE);
		initView();
		Log.d("tag", "userid-edit-》" + MConstant.USER_ID_VALUE);
	}

	private void initView() {
		etNickName = (EditText) findViewById(R.id.edit_myinfor_nickname);
		/** 地区，行业，公司 */
		rlRegion = (RelativeLayout) findViewById(R.id.edit_myinfor_region_rl);
		rlIndustry = (RelativeLayout) findViewById(R.id.edit_myinfor_industry_rl);
		// rlCompany = (RelativeLayout)
		// findViewById(R.id.edit_myinfor_company_rl);
		rlstartTime = (RelativeLayout) findViewById(R.id.edit_myinfor_startTime_rl);
		tvRegion = (TextView) findViewById(R.id.edit_myinfor_region_tv);
		tvIndustry = (TextView) findViewById(R.id.edit_myinfor_industry_tv);
		tvCompany = (TextView) findViewById(R.id.edit_myinfor_company_tv);
		tvStartTime = (TextView) findViewById(R.id.edit_myinfor_starttime_tv);

		etCompany = (EditText) findViewById(R.id.edit_myinfor_company_et);
		etSalary = (EditText) findViewById(R.id.edit_myinfor_salary);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar_other);
		etMyComment = (EditText) findViewById(R.id.edit_myinfor_comment);
		btnCancel = (Button) findViewById(R.id.edit_myinfor_cancel);
		btnSave = (Button) findViewById(R.id.edit_myinfor_save);
		findViewById(R.id.back).setOnClickListener(this);
		rlRegion.setOnClickListener(this);
		rlIndustry.setOnClickListener(this);
		// rlCompany.setOnClickListener(this);
		rlstartTime.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		ratingBar.setOnRatingBarChangeListener(this);

		etCompany.setOnTouchListener(this);
		etNickName.setOnTouchListener(this);
		etSalary.setOnTouchListener(this);
		etMyComment.setOnTouchListener(this);
		ratingBar.setOnTouchListener(this);

		// setEnable(edit);
		// 请求网络数据
		requestGet();
	}

	@Override
	public void onClick(View arg0) {
		/** 在不能编辑情况下点击 */
		switch (arg0.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.edit_myinfor_region_rl:// 选择地区
			if (!edit) {
				Shake();
				return;
			}
			String cityName = tvRegion.getText().toString().substring(2);
			startActivityForResult(new Intent(EditMyInforAct.this,
					CityAct.class).putExtra("name", cityName), 0);
			break;
		case R.id.edit_myinfor_industry_rl:// 选择行业
			if (!edit) {
				Shake();
				return;
			}
			Intent i = new Intent(EditMyInforAct.this, IndustryAct.class);
			i.putExtra("detail", industryDetail);
			i.putExtra("industryId", industryId);
			startActivityForResult(i, 1);
			break;
		// case R.id.edit_myinfor_company_rl://选择公司
		// startActivityForResult(new
		// Intent(EditMyInforAct.this,SelectType.class).putExtra("flag",
		// MConstant.REQUEST_CODE_COMPANYS), 2);
		// break;
		case R.id.edit_myinfor_startTime_rl:// 选择日期
			if (!edit) {
				Shake();
				return;
			}
			ShowDataPicker();
			break;
		case R.id.edit_myinfor_cancel:
			finish();
			break;
		case R.id.edit_myinfor_save:
			if (edit) {
				requestSend();

			}
			btnSave.setText(edit ? "保存" : "编辑");
			edit = !edit;
			break;
		default:
			if (!edit) {
				hideSoftInput(arg0);
				Shake();
			}
		}
	}

	private void Shake() {
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		btnSave.startAnimation(shake);
	}

	private void hideSoftInput(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}


	@Override
	public void showResult(int type, BaseEntity baseEntity) {
		super.showResult(type, baseEntity);
		if (type == MConstant.REQUEST_CODE_EDIT_SELF_INFOR) {// 是否保存成功

		} else if (type == MConstant.REQUEST_CODE_GET_SELF_INFOR) {// 返回所有个人信息
			if (baseEntity instanceof UserEntity) {
				UserEntity userEntity = (UserEntity) baseEntity;
				etNickName.setText(userEntity.getName());
				tvRegion.setText("地区" + userEntity.getIndustry_name());
				etCompany.setText(userEntity.getMyCompany());
				tvIndustry.setText("行业" + userEntity.getIndustry_name());
				etSalary.setText(userEntity.getSalary() + "");
				tvStartTime.setText(userEntity.getStartWorkTime());
				ratingBar.setRating(userEntity.getWelfare());
				etMyComment.setText(userEntity.getComment());
				// etSalaryPer.setText(userEntity.getSalaryPer()+"");
				// etEnvironment.setText(userEntity.getWelfare()+"");
				// etEnvironmentPer.setText(userEntity.getWelfarePer()+"");
			} else {
				Toast("数据错误");
			}

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == -101) {
			switch (requestCode) {
			case 0:// 地区
				tvRegion.setText("地区:" + data.getStringExtra("name"));
				break;
			case 1:// 行业
				industryId = data.getIntExtra("industryId", 0);
				industryDetail = data.getStringExtra("detail");
				tvIndustry.setText("行业:" + data.getStringExtra("industryName")
						+ "--" + data.getStringExtra("detail"));
				break;
			case 2:// 公司
				tvCompany.setText("公司:" + data.getStringExtra("name"));
				break;
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void setEnable(boolean flag) {
		etNickName.setEnabled(flag);
		etSalary.setEnabled(flag);
		etCompany.setEnabled(flag);
		rlRegion.setEnabled(flag);
		rlIndustry.setEnabled(flag);
		rlstartTime.setEnabled(flag);
		ratingBar.setEnabled(flag);
		etMyComment.setEnabled(flag);
		btnSave.setText(flag ? "保存" : "编辑");
	}

	/**
	 * 请求获得
	 */
	private void requestGet() {
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setPost(false);
		Map<String, String> map = new HashMap<String, String>();
		map.put(DbConstant.DB_USER_ID, MConstant.USER_ID_VALUE);
		// requestEntity.setUrl(MConstant.URL_MYINFOR);
		requestEntity.setParams(map);
		requestEntity.setRequestType(MConstant.REQUEST_CODE_GET_SELF_INFOR);
		request(requestEntity);
	}

	/**
	 * 发起网络请求，提交数据
	 */
	private void requestSend() {
		String nickName = etNickName.getText().toString();
		/** 地区，行业,公司 */

		String companyName = etCompany.getText().toString();
		String salary = etSalary.getText().toString();
		String startTime = tvStartTime.getText().toString();
		String comment = etMyComment.getText().toString();
		float rating = ratingBar.getRating();
		if (null == nickName || null == salary) {
			Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (nickName.length() > 20) {
			Toast.makeText(this, "昵称长度不能超过20", Toast.LENGTH_SHORT).show();
			return;
		}

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setRequestType(MConstant.REQUEST_CODE_EDIT_SELF_INFOR);
		requestEntity.setPost(false);
		Map<String, String> map = new HashMap<String, String>();
		map.put(DbConstant.DB_USER_ID, MConstant.USER_ID_VALUE);
		map.put(DbConstant.DB_USER_NICK_NAME, nickName);
		map.put(DbConstant.DB_USER_REGION_ID, 0 + "");
		map.put(DbConstant.DB_USER_INDUSTRY_ID, 0 + "");
		map.put(DbConstant.DB_USER_MY_INDUSTRY_NAME, industryDetail);
		map.put(DbConstant.DB_USER_MY_COMPANY_NAME, companyName);
		map.put(DbConstant.DB_USER_START_WORK_TIME, startTime);
		map.put(DbConstant.DB_USER_COMMENT, comment);
		map.put(DbConstant.DB_USER_SALARY, salary);
		map.put(DbConstant.DB_USER_WELFARE, rating + "");
		requestEntity.setParams(map);
		request(requestEntity);
	}

	@Override
	public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
		Log.d("tag", "Rating__change->" + arg1 + "]" + arg2);

	}

	/**
	 * 显示日期选择对话框
	 */
	private void ShowDataPicker() {
		Calendar calendar = Calendar.getInstance();
		String temp = tvStartTime.getText().toString();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d = dateFormat.parse(temp);
			calendar.setTime(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatePickerDialog dateDialog = new DatePickerDialog(this,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker arg0, int arg1, int arg2,
							int arg3) {
						tvStartTime.setText(arg1 + "-" + arg2 + "-" + arg3);

					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		dateDialog.show();
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
//		Toast("touch");
		if (!edit) {
			hideSoftInput(arg0);
			Shake();
			return true;
		}
		return false;
	}

}
