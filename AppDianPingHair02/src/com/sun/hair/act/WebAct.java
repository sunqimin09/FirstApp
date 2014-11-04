package com.sun.hair.act;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.sun.hair.R;

public class WebAct extends Activity{

	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_web);
		initView();
//		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private void initView() {
		Toast.makeText(this, "webview--initview", Toast.LENGTH_SHORT).show();
		webView = (WebView) findViewById(R.id.webView1);
		webView.setWebViewClient(new webViewClient());
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
			// Activity和Webview根据加载程度决定进度条的进度大小
			// 当加载到100%的时�?进度条自动消�?
			setTitle("Loading...");
			setProgress(progress * 100);
			}
			});
		
		String url = getIntent().getStringExtra("url");
		if(url!=null)
			webView.loadUrl(url);
		else
			Toast.makeText(this, "无数据", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case android.R.id.home:
//			finish();
//			break;
//		}
		return super.onOptionsItemSelected(item);
	}
	
	class webViewClient extends WebViewClient{ 

	       //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开�?

	    @Override 

	    public boolean shouldOverrideUrlLoading(WebView view, String url) { 

	        view.loadUrl(url); 

	        //如果不需要其他对点击链接事件的处理返回true，否则返回false 
	        return false; 
	    }

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			Log.d("tag","加载结束");
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			
//			imageRefresh.setVisibility(View.GONE);
//			progressBar.setVisibility(View.VISIBLE);
//			setProgressBarStyle();
		} 

	}
	
}
