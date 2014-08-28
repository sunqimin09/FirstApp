package com.example.appvideo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import net.youmi.android.spot.SpotManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class VideoPlayAct extends Activity {

	private WebView webview;

	private Handler mHandler = new Handler();
	
	private String url = "Uri is  http:";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_act);
		initView();
		SpotManager.getInstance(this).loadSpotAds();
		showAd();
		listener();
	}

	@Override
	protected void onDestroy() {
		SpotManager.getInstance(this).unregisterSceenReceiver();
		super.onDestroy();
	}

	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	private void initView() {
		String url = getIntent().getStringExtra("url");
		webview = (WebView) findViewById(R.id.webView1);
		initWebview();
		webview.loadUrl(url);

	}

	private void initWebview() {
		WebSettings ws = webview.getSettings();
		/**
		 * setAllowFileAccess 启用或禁止WebView访问文件数据 setBlockNetworkImage 是否显示网络图像
		 * setBuiltInZoomControls 设置是否支持缩放 setCacheMode 设置缓冲的模式
		 * setDefaultFontSize 设置默认的字体大小 setDefaultTextEncodingName 设置在解码时使用的默认编码
		 * setFixedFontFamily 设置固定使用的字体 setJavaSciptEnabled 设置是否支持Javascript
		 * setLayoutAlgorithm 设置布局方式 setLightTouchEnabled 设置用鼠标激活被选项
		 * setSupportZoom 设置是否支持变焦
		 * */
		ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
		ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
		ws.setUseWideViewPort(true);// 可任意比例缩放
		ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
		ws.setSavePassword(true);
		ws.setSaveFormData(true);// 保存表单数据
		ws.setJavaScriptEnabled(true);
		ws.setGeolocationEnabled(true);// 启用地理定位
		ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径
		ws.setDomStorageEnabled(true);
		xWebChromeClient xwebchromeclient = new xWebChromeClient();
		webview.setWebChromeClient(xwebchromeclient);
		webview.setWebViewClient(new xWebViewClientent());
	}

	public class xWebChromeClient extends WebChromeClient {

		@Override
		// 播放网络视频时全屏会被调用的方法
		public void onShowCustomView(View view,
				WebChromeClient.CustomViewCallback callback) {
			// if (islandport) {
			// }
			// else{
			//
			// // ii = "1";
			// //
			// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			// }
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			webview.setVisibility(View.GONE);
			// 如果一个视图已经存在，那么立刻终止并新建一个
			// if (xCustomView != null) {
			// callback.onCustomViewHidden();
			// return;
			// }
			// videoview.addView(view);
			// xCustomView = view;
			// xCustomViewCallback = callback;
			// videoview.setVisibility(View.VISIBLE);
		}

		@Override
		// 视频播放退出全屏会被调用的
		public void onHideCustomView() {

			// if (xCustomView == null)//不是全屏播放状态
			// return;
			// // Hide the custom view.
			// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			// xCustomView.setVisibility(View.GONE);
			//
			// // Remove the custom view from its container.
			// videoview.removeView(xCustomView);
			// xCustomView = null;
			// videoview.setVisibility(View.GONE);
			// xCustomViewCallback.onCustomViewHidden();
			//
			// videowebview.setVisibility(View.VISIBLE);
			//
			// Log.i(LOGTAG, "set it to webVew");
		}

		// 视频加载添加默认图标
		// @Override
		// public Bitmap getDefaultVideoPoster() {
		// //Log.i(LOGTAG, "here in on getDefaultVideoPoster");
		// if (xdefaltvideo == null) {
		// xdefaltvideo = BitmapFactory.decodeResource(
		// getResources(), R.drawable.videoicon);
		// }
		// return xdefaltvideo;
		// }
		// 视频加载时进程loading
		// @Override
		// public View getVideoLoadingProgressView() {
		// //Log.i(LOGTAG, "here in on getVideoLoadingPregressView");
		//
		// if (xprogressvideo == null) {
		// LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
		// xprogressvideo = inflater.inflate(R.layout.video_loading_progress,
		// null);
		// }
		// return xprogressvideo;
		// }
		// 网页标题
	
		// @Override
		// //当WebView进度改变时更新窗口进度
		// public void onProgressChanged(WebView view, int newProgress) {
		// (MainActivity.this).getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
		// newProgress*100);
		// }

	}

	/**
	 * 处理各种通知、请求等事件
	 * 
	 * @author
	 */
	public class xWebViewClientent extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.i("webviewtest", "shouldOverrideUrlLoading: " + url);
			return false;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			Log.d("tag", "pagefinish");
			super.onPageFinished(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Log.d("tag", "pagestart");
			super.onPageStarted(view, url, favicon);
		}

	}

	private void showAd() {
		SpotManager.getInstance(this).showSpotAds(this);
	}
	
	private void listener(){
		 /**开启线程用于监听log输出的信息**/    
        new Thread(new Runnable() {  
              
            @Override  
            public void run() {  
                  
                Process mLogcatProc = null;  
                BufferedReader reader = null;  
                try {  
                        //获取logcat日志信息  
                    mLogcatProc = Runtime.getRuntime().exec(new String[] { "logcat","MediaPlayer:I *:S" });  
                    reader = new BufferedReader(new InputStreamReader(mLogcatProc.getInputStream()));  
                    String line;  
                    while ((line = reader.readLine()) != null) {  
                        if (line.indexOf(url) > 0) {  
                            //logcat打印信息在这里可以监听到  
                            // 使用looper 把给界面一个显示  
                            Looper.prepare();  
                            Toast.makeText(VideoPlayAct.this, "监听到log信息", Toast.LENGTH_SHORT).show();   
                            Looper.loop();  
                        }  
                    }  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }).start();   
	}
}
