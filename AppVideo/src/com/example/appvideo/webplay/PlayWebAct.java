package com.example.appvideo.webplay;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.appvideo.R;

/**
 * 
 * @author sunqm
 * @version 创建时间：2014-8-28 下午5:42:07
 * TODO  网页播放
 */
public class PlayWebAct extends Activity {

	private WebView webView;
	
	private String fileName =null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playweb);
		webView = (WebView) findViewById(R.id.webView1);
		initWebview();
		webView.loadUrl("file:///android_asset/hello.html");
		fileHelper = new FileHelper();
		fileName = getIntent().getStringExtra("fileName");
		request(getIntent().getStringExtra("url"));
	}
	
	
	
	private void initWebview() {
		WebSettings ws = webView.getSettings();
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
//		xWebChromeClient xwebchromeclient = new xWebChromeClient();
//		webView.setWebChromeClient(xwebchromeclient);
//		webView.setWebViewClient(new xWebViewClientent());
	}


	FinalHttp finalHttp = new FinalHttp();
	
	private FileHelper fileHelper ;
	
	private void request(String url){
		
		finalHttp.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
			}

			@Override
			public void onStart() {
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				Log.d("tag","success---?"+t);
				String fileName1 = fileHelper.write(fileName, t.toString());
				if(fileName1!=null){
					webView.loadUrl(fileName1);
				}else{
					Toast.makeText(PlayWebAct.this, "视频错误", Toast.LENGTH_SHORT).show();
					
				}
			}
			
			
		});
	}

}
