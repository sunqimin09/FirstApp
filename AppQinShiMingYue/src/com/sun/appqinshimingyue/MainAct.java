package com.sun.appqinshimingyue;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.utils.Utils;
import net.youmi.android.AdManager;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.sun.appqinshimingyue.adapter.MainAdapter;
import com.sun.appqinshimingyue.entity.InforEntity;

public class MainAct extends Activity implements OnItemClickListener{

	private String url = "http://115.29.45.6/Qinsmy/servlet/GetData";
	
	private ListView listview;
	
	MainAdapter adapter = null;
	
	private List<InforEntity> datas = new ArrayList<InforEntity>();
	
	private boolean debug = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainact);
		AdManager.getInstance(this).init("8ae59e7c7f930ce9", "ed04179c2450dc5b", false);
		initView();
		initBaidu();
		request(url);
	}

	private void initBaidu() {
		// 启动推送服务
		 PushManager.startWork(getApplicationContext(),
		 PushConstants.LOGIN_TYPE_API_KEY,
		 Utils.getMetaValue(MainAct.this, "api_key"));
		 List<String> list = new ArrayList<String>();
		 list.add("debug");//目前都是test 系列
		 PushManager.setTags(this, list);
		
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.listView1);
		adapter = new MainAdapter(this, new TestData().getData());
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	private void initViewTest() {
		/**
		
		**/
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		if(!debug){
//			Intent intent = new Intent();
//			intent.setClass(MainAct.this, BBVideoPlayer.class);
//			if(datas.get(arg2).url.indexOf("www")>0){//bu正确的。
//				intent.putExtra("url", new TestData().getData().get(arg2).url);
//			}else{
//				intent.putExtra("url", datas.get(arg2).url);
//			}
//			
//			intent.putExtra("cache",
//					Environment.getExternalStorageDirectory().getAbsolutePath()
//							+ "/VideoCache/" + System.currentTimeMillis() + ".mp4");
//			startActivity(intent);
//			finish();
//		}else{
//			Intent i = new Intent(MainAct.this,VideoPlayAct.class);
//			i.putExtra("url", new TestData().urls[arg2]);
//			startActivity(i);
//		}
//		
		
	}
	
	FinalHttp finalHttp = new FinalHttp();
	
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
				try {
					datas = new JsonParse().parse(t);
					adapter.setData(datas);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			
		});
}
	
	
}
