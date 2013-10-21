package com.example.appcolleageentrance;

import java.util.Calendar;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewLinstener;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.offers.PointsChangeNotify;
import net.youmi.android.offers.PointsManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener, OnItemSelectedListener, PointsChangeNotify {

	private EditText et;

	private TextView time;

	/**当前分数*/
	private TextView tv_point;
	
	/** 字体颜色 */
	private Spinner textColors;

	private Calendar nowCalendar = Calendar.getInstance();

	private Calendar settingCalendar = Calendar.getInstance();

	/** 当前显示的天数 */
	private int currentDay;

	private DesktopApp receiver;
	
	/**当前得分*/
	private int currentPoint =0;
	/**数字图片*/
	private int[] pics = { R.drawable.num00, R.drawable.num01,
			R.drawable.num02, R.drawable.num03, R.drawable.num04,
			R.drawable.num05, R.drawable.num06, R.drawable.num07,
			R.drawable.num08, R.drawable.num09 };

	/**字体颜色*/
	private Integer[] Colors = {Color.BLACK, Color.WHITE, Color.BLUE, Color.GREEN,
			Color.RED, Color.YELLOW };

	private int[] imgViews = { R.id.imageView1, R.id.imageView2,
			R.id.imageView3, R.id.imageView4 };
	
	private String[] ColorsStr={"黑色","白色","蓝色","绿色","红色","黄色"};
	
	private SharePre sp;

	private boolean setDate = false;

	private int textColor = Color.BLACK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et = (EditText) findViewById(R.id.editText1);
		time = (TextView) findViewById(R.id.textView1);
		textColors = (Spinner) findViewById(R.id.textColor_spinner);

		Button btn = (Button) findViewById(R.id.button1);
		Button btnShow = (Button) findViewById(R.id.btn_show);
		btnShow.setOnClickListener(this);
		btn.setOnClickListener(this);
		time.setOnClickListener(this);
		receiver = new DesktopApp();
		sp = new SharePre(this);

		Log.d("tag", "isFirst->" + sp.isFirst());
		regist();
		startService(new Intent(MainActivity.this, DateService.class));
		MConstant.startFlag = 0;
		Log.d("tag", "flags-->" + getIntent().getAction());
		et.setText(sp.getStr(sp.getCONTENT()));
		time.setText(sp.getInt(sp.getDAYS()) + "");
		ArrayAdapter<String> aa = new ArrayAdapter<String>(MainActivity.this,
				android.R.layout.simple_spinner_item, ColorsStr);
		textColors.setAdapter(aa);
		textColors.setOnItemSelectedListener(this);
		initYouMiAd();
	}

	private void initYouMiAd() {
		 AdManager.getInstance(this).init("e751ee68c5a074bf","f36c1150519d4fa6", false); 
		 OffersManager.getInstance(this).onAppLaunch();
		/** 获得当前得分 */
		currentPoint = PointsManager.getInstance(this).queryPoints();
		tv_point = (TextView) findViewById(R.id.pint_tv);
		tv_point.setText("当前积分:"+currentPoint);
		// 将广告条adView添加到需要展示的layout控件中
		final RelativeLayout adLayout = (RelativeLayout) findViewById(R.id.youmi_adview_ll);
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		ImageView imgClose = new ImageView(this);
		imgClose.setImageResource(android.R.drawable.ic_delete);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        imgClose.setLayoutParams(params);
        imgClose.setClickable(true);
        imgClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("tag","click");
				String str = "";
				if(currentPoint>49){
					PointsManager.getInstance(MainActivity.this).spendPoints(50);
					str = "您花费了50积分,三天内将没广告啦，亲";
					adLayout.setVisibility(View.GONE);
					/**
					 * 把今天的日期保存到sp中
					 * */
					sp.setLong("day", System.currentTimeMillis());
					Toast.makeText(MainActivity.this,str, Toast.LENGTH_SHORT).show();
				}else{
					str = "您的积分不足，请补充积分";
					Toast.makeText(MainActivity.this,str, Toast.LENGTH_SHORT).show();
					OffersManager.getInstance(MainActivity.this).showOffersWall();
					
				}
				
			}
		});
        adLayout.addView(adView);
        adLayout.addView(imgClose);
        //分数监听器
        
        PointsManager.getInstance(this).registerNotify(this);
        // 监听广告条接口
        adView.setAdListener(new AdViewLinstener() {
            
            @Override
            public void onSwitchedAd(AdView arg0) {
                Log.i("YoumiSample", "广告条切换");
            }
            
            @Override
            public void onReceivedAd(AdView arg0) {
                Log.i("YoumiSample", "请求广告成功");
                
            }
            
            @Override
            public void onFailedToReceivedAd(AdView arg0) {
                Log.i("YoumiSample", "请求广告失败");
            }
        });
        int days = (int) (System.currentTimeMillis()-(sp.get("day"))/1000/60/60/24);
        if(days>0){
        	adLayout.setVisibility(View.GONE);
        }else{
        	adLayout.setVisibility(View.VISIBLE);
        }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.textView1:// 更改时间

			DatePickerDialog dialog = new DatePickerDialog(this, dateListener,
					settingCalendar.get(Calendar.YEAR),
					settingCalendar.get(Calendar.MONTH),
					settingCalendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();

			break;
		case R.id.button1:// 保存设置
			final RemoteViews rv = new RemoteViews(this.getPackageName(),
					R.layout.widget);
			String etStr = et.getText().toString();
			if (!etStr.equals("")) {
				Log.d("tag","setColor");
				rv.setTextViewText(R.id.tv_widget, etStr);
				rv.setTextColor(R.id.tv_widget, textColor);
				rv.setTextColor(R.id.textView1, textColor);
				sp.writeStr(sp.getCONTENT(), etStr);
				sp.writeInt(sp.getCOLOR(), textColor);
			}
			if (setDate) {// 如果设置了日期
				new Utills().setNum(currentDay, rv);
				sp.writeInt(sp.getYEAR(), settingCalendar.get(Calendar.YEAR));
				sp.writeInt(sp.getMONTH(), settingCalendar.get(Calendar.MONTH));
				sp.writeInt(sp.getDAY(),
						settingCalendar.get(Calendar.DAY_OF_MONTH));
				sp.writeInt(sp.getDAYS(), currentDay);
			} else {
				int s = Integer.parseInt(time.getText().toString());
				new Utills().setNum(s, rv);
			}
			ComponentName cn = new ComponentName(this, DesktopApp.class);
			AppWidgetManager am = AppWidgetManager.getInstance(this);
			new Utills().addListener(this, rv);
			am.updateAppWidget(cn, rv);
			Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case R.id.btn_show:
			Toast.makeText(
					this,
					sp.getInt(sp.getYEAR()) + "年" + sp.getInt(sp.getMONTH())
							+ "月" + sp.getInt(sp.getDAY()) + "日" + "boot",
					Toast.LENGTH_LONG).show();
			startActivity(new Intent(MainActivity.this,
					SimpleDeclaringLayout.class));
			break;
		}

	}

	DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker datePicker, int year, int month,
				int dayOfMonth) {
			setDate = true;
			settingCalendar.set(year, month, dayOfMonth);
			currentDay = new Utills().compare(nowCalendar, settingCalendar);
			time.setText(currentDay + "");
		}

	};

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		OffersManager.getInstance(this).onAppExit(); 
		super.onDestroy();
	}

	private void regist() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.DATE_CHANGED");
		registerReceiver(receiver, filter);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		textColor=Colors[arg2];
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
		
	}

	@Override
	public void onPointBalanceChange(int arg0) {		
		Log.d("tag","Point--->"+arg0);
		currentPoint = arg0;
		tv_point.setText("当前积分:"+currentPoint);
	}

}
