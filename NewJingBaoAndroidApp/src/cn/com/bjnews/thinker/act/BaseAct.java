package cn.com.bjnews.thinker.act;

import cn.com.bjnews.thinker.R;
import cn.com.bjnews.thinker.entity.NewsEntity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 所有的 父类
 * 
 * @author sunqm
 * 
 */
public class BaseAct extends FragmentActivity {

	//
	// protected void onStart() {
	// // MyBroadcastReceiver mReceiver = new MyBroadcastReceiver();
	// IntentFilter inFilter = new IntentFilter();
	// inFilter.addAction(Intent.ACTION_TIME_TICK);
	//
	// // 我百度了一下，说getApplicationContext得到的是整个应用程序的上下文
	// // 而Activity.this仅仅是该Activity的上下文，两者不同
	// // 那意思是不是说，广播接收注册给了哪个Context，就要由哪个Context来解除注册？
	//
	// // BroadcastReceiver的onReceive函数有两个回调参数，Context和Intent
	// // 这个Context参数又是谁的上下文？感觉越来越晦涩了！
	// this.registerReceiver(dynamicReceiver, inFilter);
	// super.onStart();
	// }

	@Override
	protected void onStart() {
		IntentFilter inFilter = new IntentFilter();
		// inFilter.addAction(Intent.ACTION_TIME_TICK);
		inFilter.addAction("pushMessage");
		// 我百度了一下，说getApplicationContext得到的是整个应用程序的上下文
		// 而Activity.this仅仅是该Activity的上下文，两者不同
		// 那意思是不是说，广播接收注册给了哪个Context，就要由哪个Context来解除注册？

		// BroadcastReceiver的onReceive函数有两个回调参数，Context和Intent
		// 这个Context参数又是谁的上下文？感觉越来越晦涩了！
		this.registerReceiver(dynamicReceiver, inFilter);
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		// unregisterReceiver接收一个BroadcastReceiver实例做参数，是不是要重新构造一个？
		// 还是在注册和解除注册时都要传递同一个BroadcastReceiver实例？
		// MyBroadcastReceiver mReceiver = new MyBroadcastReceiver();
		this.unregisterReceiver(dynamicReceiver);
		super.onDestroy();
	}

	private String getClassName(){
		return this.getClass().toString().substring(6);
	}
	
	private BroadcastReceiver dynamicReceiver // 动态广播的Receiver

	= new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			if (intent.getAction().equals("pushMessage")) { // 动作检测
				String target = intent.getStringExtra("target");
				Log.d("tag", "brocast---onreceiver>"+target+"<>"+getClassName());
				String msg = intent.getStringExtra("content");
				NewsEntity entity = (NewsEntity) intent.getSerializableExtra("news");
				int channelId = intent.getIntExtra("channelId", 0);
				if(target.equals(getClassName()))
					showDialog("新闻推送",msg,channelId,entity);
				Log.d("tag","news-==content22-->"+entity);
//				Toast.makeText(context, "msg", Toast.LENGTH_SHORT).show();
//				abortBroadcast();
			}
		}
	};

	private void showDialog(String title,String content,final int channelId,final NewsEntity entity) {
		View view = View.inflate(this, R.layout.dialog_push, null);
		TextView tvContent	 = (TextView) view.findViewById(R.id.dialog_content);
		TextView tvTitle = (TextView) view.findViewById(R.id.dialog_title);
		tvTitle.setText(title);
		tvContent.setText(content);
		Dialog alertDialog = new AlertDialog.Builder(this)
				.setView(view)
				.setPositiveButton("查看", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent i = null;
						if (entity != null) {

							i = new Intent(BaseAct.this, NewsDetailAct.class); // 点击该通知后要跳转的Activity
							i.putExtra("channelId", channelId + "");
							Log.d("tag", "news-==content11-->" + entity);
							i.putExtra("news", entity);
							i.setAction("brocastreceiver");
							startActivity(i);
						}
						dialog.dismiss();
						// Intent i = new
						// Intent(BaseAct.this,NewsDetailAct.class);

					}
				})
				.setNegativeButton("忽略", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.create();
		alertDialog.show();

	}

}
