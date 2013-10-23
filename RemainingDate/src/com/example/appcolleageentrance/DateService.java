package com.example.appcolleageentrance;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class DateService extends Service{

	private DateBrocastReceiver receiver;
	
	
	private SharePre sp;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		sp = new SharePre(this);
//		if(MConstant.startFlag==1){//开机启动
//			//显示过期对话框
//			showOutOfDate();
//		}
		receiver = new DateBrocastReceiver();
		regist();
		super.onCreate();
	}

	private void showOutOfDate() {
		final Calendar calendar = Calendar.getInstance();
		final Calendar now = Calendar.getInstance();
		calendar.set(Calendar.YEAR, sp.getInt(sp.getYEAR()));
		calendar.set(Calendar.MONTH, sp.getInt(sp.getMONTH()));
		calendar.set(Calendar.DAY_OF_MONTH, sp.getInt(sp.getDAY()));
		Utills utils = new Utills();
		int days = utils.compare(now, calendar);
		if (days == 0) {// 如果时间到了，显示对话框
			Log.d("tag","service-->0000");
			Dialog dialog = new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.btn_star).setTitle("喜好调查")
					.setMessage("你喜欢李连杰的电影吗？")
					.setPositiveButton("很喜欢", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							// Toast.makeText(Main.this, "我很喜欢他的电影。",
							// Toast.LENGTH_LONG).show();
						}
					}).setNegativeButton("不喜欢", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							// Toast.makeText(Main.this, "我不喜欢他的电影。",
							// Toast.LENGTH_LONG)
							// .show();
						}
					}).setNeutralButton("一般", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							// Toast.makeText(Main.this, "谈不上喜欢不喜欢。",
							// Toast.LENGTH_LONG)
							// .show();
						}
					}).create();

			dialog.show();
		}
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}
	
	private void regist(){
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.DATE_CHANGED");
		registerReceiver(receiver, filter);
	}
	
	class DateBrocastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals("android.intent.action.DATE_CHANGED")||
					intent.getAction().equals("android.intent.action.BATTERY_CHANGED")){//日期改变广播
				final Utills utils = new Utills();
				final Calendar calendar = Calendar.getInstance();
				final Calendar now = Calendar.getInstance();
				Log.d("tag","Boot-->");
				
				if(null==sp){
					sp = new SharePre(context);
				}
				calendar.set(Calendar.YEAR, sp.getInt(sp.getYEAR()));
				calendar.set(Calendar.MONTH, sp.getInt(sp.getMONTH()));
				calendar.set(Calendar.DAY_OF_MONTH, sp.getInt(sp.getDAY()));
				final RemoteViews rv = new RemoteViews(context.getPackageName(),
						R.layout.widget);
				utils.setNum(utils.compare(now, calendar),rv);
				ComponentName cn = new ComponentName(context, DesktopApp.class);
				AppWidgetManager am = AppWidgetManager.getInstance(context);
				new Utills().addListener(context, rv);
				am.updateAppWidget(cn, rv);
//				showOutOfDate();
			}
			
		}
		
	}
	
	
	
}
