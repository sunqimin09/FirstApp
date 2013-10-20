package com.example.appcolleageentrance;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class DesktopApp extends AppWidgetProvider{
	String TAG = "AppColleage";
	
	private SharePre sp;
	
	private RemoteViews views;
	
	private ComponentName componentName;
	
	private Context context;
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		this.context =context;
		sp = new SharePre(context);
		super.onEnabled(context);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.d(TAG,"WIDGETIDS-->"+appWidgetIds);
		views = new RemoteViews(context.getPackageName(), R.layout.widget);
		new Utills().addListener(context, views);
		componentName = new ComponentName(context, DesktopApp.class);
		appWidgetManager.updateAppWidget(componentName, views);
	}
	

	@Override
	public void onReceive(final Context context, Intent intent) {
		if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")||
				intent.getAction().equals("android.intent.action.BATTERY_CHANGED")){//开机检查更新日期显示
			
			this.context = context;
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
			int days = utils.compare(now, calendar);
			if(utils.compare(now, calendar)==0){//如果日期相同
				Log.d("tag","日期过了--->");
//				OutOfDateAction(context,"目标日期过了");
				Toast.makeText(context, context.getString(R.string.outofdate), Toast.LENGTH_LONG).show();
			}
			//开启实时监控的服务
			context.startService(new Intent(context,DateService.class));
			MConstant.startFlag = 1;
			utils.setNum(utils.compare(now, calendar),rv);
			utils.setText(sp.getStr(sp.getCONTENT()), rv);
			rv.setTextColor(R.id.tv_widget, sp.getInt(sp.getCOLOR()));
			rv.setTextColor(R.id.textView1, sp.getInt(sp.getCOLOR()));
			
			ComponentName cn = new ComponentName(context, DesktopApp.class);
			AppWidgetManager am = AppWidgetManager.getInstance(context);
			new Utills().addListener(context, rv);
			am.updateAppWidget(cn, rv);
//			Toast.makeText(
//					context,sp.getStr(sp.getCONTENT())+"|"+
//					sp.getInt(sp.getYEAR()) + "年" + sp.getInt(sp.getMONTH())
//							+ "月" + sp.getInt(sp.getDAY()) + "日" + "boot"
//							+ utils.compare(now, calendar), Toast.LENGTH_LONG)
//					.show();
		}
		Log.d(TAG, "onreceive-->"+intent.getAction());
		super.onReceive(context, intent);
	}

	/**
	 * 过期后所做
	 */
	private void OutOfDateAction(Context context,String str){
//		AlertDialog.Builder builder = new Builder(context);
//		builder.setMessage(str);
//
//		builder.setTitle("提示");
//
//		builder.setPositiveButton("确认", new OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//
//			}
//		});
//
//		builder.setNegativeButton("取消", new OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//			}
//		});
//
//		builder.create().show();
		
		
		
		Dialog dialog = new AlertDialog.Builder(context).setIcon(
			     android.R.drawable.btn_star).setTitle("喜好调查").setMessage(
			     "你喜欢李连杰的电影吗？").setPositiveButton("很喜欢",
			     new OnClickListener() {

			      @Override
			      public void onClick(DialogInterface dialog, int which) {
			       // TODO Auto-generated method stub
//			       Toast.makeText(Main.this, "我很喜欢他的电影。",
//			         Toast.LENGTH_LONG).show();
			      }
			     }).setNegativeButton("不喜欢", new OnClickListener() {

			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			     // TODO Auto-generated method stub
//			     Toast.makeText(Main.this, "我不喜欢他的电影。", Toast.LENGTH_LONG)
//			       .show();
			    }
			   }).setNeutralButton("一般", new OnClickListener() {

			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			     // TODO Auto-generated method stub
//			     Toast.makeText(Main.this, "谈不上喜欢不喜欢。", Toast.LENGTH_LONG)
//			       .show();
			    }
			   }).create();

			   dialog.show();
		
		
		
		

	}

	Handler handler = new Handler (){

		@Override
		public void handleMessage(Message msg) {
			Toast.makeText(
					context,
					sp.getInt(sp.getYEAR()) + "年" + sp.getInt(sp.getMONTH())
							+ "月" + sp.getInt(sp.getDAY()) + "日" + "boot"
							, Toast.LENGTH_LONG)
					.show();
		}
		
	};
	
}
