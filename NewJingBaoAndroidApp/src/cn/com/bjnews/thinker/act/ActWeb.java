package cn.com.bjnews.thinker.act;

import com.umeng.analytics.MobclickAgent;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import cn.com.bjnews.thinker.R;
import cn.com.bjnews.thinker.utils.Utils;

public class ActWeb extends BaseAct{

	private WebView webView;
	
	private String url;
	
	private ProgressBar progressBar;
	
	private ImageView imageRefresh;
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_web);
		setActionBarLayout(R.layout.act_web_title);
		initView();
	}

	private void initView() {
		ImageView imgBack = (ImageView) findViewById(R.id.act_web_back);
		if(Utils.getSystemVersionNew()){
			imgBack.setVisibility(View.INVISIBLE);
			imgBack.setImageResource(R.drawable.listview_cachecolor);
		}else{
			imgBack.setVisibility(View.VISIBLE);
		}
		
		webView = (WebView) findViewById(R.id.webView1);
		progressBar = (ProgressBar) findViewById(R.id.progressing);
		imageRefresh = (ImageView)  findViewById(R.id.refresh);
		webView.setWebViewClient(new webViewClient());
		url = getIntent().getStringExtra("url");
//		Log.d("tag","url--->"+url);
		webView.loadUrl(url);
		
	}
	
	
	
	public void onClick(View view){
//		Log.d("tag","click"+view.getId());
		switch(view.getId()){
		case R.id.act_web_back://返回
			finish();
			break;
		case R.id.refresh:
			webView.reload();
			break;
		}
	}
	
	class webViewClient extends WebViewClient{ 

	       //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。 

	    @Override 

	    public boolean shouldOverrideUrlLoading(WebView view, String url) { 

	        view.loadUrl(url); 

	        //如果不需要其他对点击链接事件的处理返回true，否则返回false 
	        return true; 
	    }

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			Log.d("tag","加载结束");
			imageRefresh.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			
			imageRefresh.setVisibility(View.GONE);
			progressBar.setVisibility(View.VISIBLE);
			setProgressBarStyle();
		} 

	}

	private void setProgressBarStyle() {
		RelativeLayout.LayoutParams params = new android.widget.RelativeLayout.LayoutParams(
				imageRefresh.getWidth(), imageRefresh.getHeight());
		params.rightMargin = 30;
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		progressBar.setLayoutParams(params);
	}
	
	public void setActionBarLayout( int layoutId ){
	    ActionBar actionBar = getActionBar( );
	    if( null != actionBar ){
	        actionBar.setDisplayShowHomeEnabled(false);
	        actionBar.setDisplayHomeAsUpEnabled(Utils.getSystemVersionNew());
	        actionBar.setDisplayShowCustomEnabled(true);
	        LayoutInflater inflator = (LayoutInflater)   this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View v = inflator.inflate(layoutId, null);
	        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	        v.setPadding(0, 0, 0, 0);
	        actionBar.setCustomView(v,layout);
	    }
	}
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	        // TODO Auto-generated method stub
	        if(item.getItemId() == android.R.id.home)
	        {
	            finish();
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	
}
