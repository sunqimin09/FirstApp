package cn.com.bjnews.thinker.push;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import cn.com.bjnews.newsroom.R;
import cn.com.bjnews.thinker.utils.Utils;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.umeng.update.UmengUpdateAgent;

public class BaiduPushTestAct extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); 
		setContentView(R.layout.baidu_test);
		setActionBarLayout(R.layout.title_menu);
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title_menu);
		
//		setTitle("菜单");
		setProgress(80);
		
		initView();
	}
	
	
	public void setActionBarLayout( int layoutId ){
	    ActionBar actionBar = getActionBar( );
	    if( null != actionBar ){
	        actionBar.setDisplayShowHomeEnabled( false );
	        actionBar.setDisplayShowCustomEnabled(true);
	        LayoutInflater inflator = (LayoutInflater)  this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View v = inflator.inflate(layoutId, null);
	        ActionBar.LayoutParams layout = new     ActionBar.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	        actionBar.setCustomView(v,layout);
	    }
	}
	
	private void initView(){
		PushManager.startWork(this, PushConstants.LOGIN_TYPE_API_KEY, Utils.getMetaValue(this, "api_key"));
		UmengUpdateAgent.update(this);
	}
	
}
