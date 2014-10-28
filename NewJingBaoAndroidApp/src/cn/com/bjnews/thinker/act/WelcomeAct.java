package cn.com.bjnews.thinker.act;

import java.io.File;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import cn.com.bjnews.newsroom.R;
import cn.com.bjnews.thinker.act.test.TestMainAct;
import cn.com.bjnews.thinker.db.DbHandler;
import cn.com.bjnews.thinker.internet.InternetHelper;
import cn.com.bjnews.thinker.utils.ImageDownLoader;
import cn.com.bjnews.thinker.utils.Mconstant;
import cn.com.bjnews.thinker.utils.Utils;


public class WelcomeAct extends Activity implements OnClickListener{

	Dialog alertDialog;
	
	ImageView image;
	
	private ImageDownLoader imageDown;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_welcome);
		image = (ImageView ) findViewById(R.id.imageView1);
		showDownloadImage();
		if(!InternetHelper.isInternetAvaliable(this)&&isFirst()){//如果当前无网络
			showDialog();
		}else{//进入下一个页面
			MobclickAgent.updateOnlineConfig( this );
			skip();
		}
		Utils.deleteData(this);
	}
	
	/**
	 * 是否为第一次进入应用，判断标准，是否存在channle 文件
	 * @return
	 */
	private boolean isFirst(){
		String localData = Utils.readFile(Mconstant.LOCAL_FILE_NAME);
		if (null == localData) {// 如果本地文件不存在
			return true;
		} else {
			return false;
		}
	}
	
	private void showDialog() {
		alertDialog = new AlertDialog.Builder(this).setTitle("未连接网络")
				.setMessage("当前无网络连接").setPositiveButton("确定",this).create();
		alertDialog.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		alertDialog.dismiss();
		finish();
	}
	
	
	private void skip() {
		final DbHandler dbhandler = DbHandler.getInstance(WelcomeAct.this);
		new Thread() {

			@Override
			public void run() {
				super.run();
				try {
					dbhandler.AllNotRead();
					Thread.sleep(1000);
					
					startActivity(new Intent(WelcomeAct.this,MainActivity
							.class));
//					WelcomeAct.this.finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
					WelcomeAct.this.finish();
				}
				
			}
			
		}.start();
	}

	
	
	


	private void showDownloadImage(){
		String fileName = "ic_launcher.png";
		File file = new File(android.os.Environment.getExternalStorageDirectory()
				+ "/bjnews",fileName);
		if(file.exists()){
			imageDown = new ImageDownLoader(this, R.drawable.logo);
			image.setImageDrawable(imageDown.getDrawable(fileName));
		}
	}
	
	private void setTextSize(){
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		this.finish();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	
}
