package com.sun.appxiaolinyuan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobads.AdView;
import com.baidu.mobads.appoffers.OffersManager;
import com.baidu.mobads.appoffers.PointsChangeListener;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener {

	private String url = "http://www.xiaolinyuan.com";

	private WebView webView;
	/** show no Internet */
	private TextView tvInternet;
	/** close Ad */
	private ImageView imgClose;

	private AdView adView;

	private int currentPoint = 0;

	private SpHelper spHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		tvInternet = (TextView) findViewById(R.id.textView1);
		webView = (WebView) findViewById(R.id.webView1);
		imgClose = (ImageView) findViewById(R.id.imgClose);
		adView = (AdView) findViewById(R.id.adView);
		imgClose.setOnClickListener(this);
		tvInternet.setOnClickListener(this);

		webView.loadUrl(url);
		if (isNetworkConnected(this)) {
			tvInternet.setVisibility(View.GONE);
			webView.setVisibility(View.VISIBLE);
		} else {
			tvInternet.setVisibility(View.VISIBLE);
			webView.setVisibility(View.GONE);
		}

		// webView.getSettings().setJavaScriptEnabled(true);
		// webView.getSettings().setDisplayZoomControls(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (isNetworkConnected(MainActivity.this)) {
					tvInternet.setVisibility(View.GONE);
					view.setVisibility(View.VISIBLE);
				} else {
					tvInternet.setVisibility(View.VISIBLE);
					view.setVisibility(View.GONE);
				}
				view.loadUrl(url);
				return false;

			}
		});
		spHelper = new SpHelper(this);
		currentPoint = spHelper.getInt(MConstant.CURRENT_POINT);
		OffersManager.setPointsChangeListener(new PointsChangeListener() {

			@Override
			public void onPointsChanged(int arg0) {
				Log.d("onPointsChanged", "total points is: " + arg0);
				currentPoint = arg0;
				spHelper.put(MConstant.CURRENT_POINT, arg0);
			}

		});
		long hideTime = spHelper.getLong(MConstant.HIDE_TIME);
		if ((System.currentTimeMillis() - hideTime) < 1000 * 60 * 60 * 24 * 7) {// 小于七天
			adView.setVisibility(View.GONE);
		} else {
			adView.setVisibility(View.GONE);
		}

	}

	/**
	 * 获取当前网络是否可以使用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.imgClose:
			mShowDialog();
			break;
		case R.id.textView1:
			webView.reload();
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void mShowDialog() {
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("是否关闭广告");
		builder.setMessage("关闭广告需要消耗:20积分/7天\n您的积分余额为:" + currentPoint
				+ "\n下载应用可以免费获取积分");
		builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		if (currentPoint > 19) {// 关闭广告
			builder.setNegativeButton("关闭广告",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							adView.setVisibility(View.GONE);
							spHelper.put(MConstant.HIDE_TIME,
									System.currentTimeMillis());
							dialog.dismiss();
						}
					});
		}

		builder.setNeutralButton("获取积分", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				OffersManager.showOffers(MainActivity.this);
				dialog.dismiss();
			}
		});
		builder.show();
	}

}
