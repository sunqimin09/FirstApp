package cn.com.bjnews.thinker.act;

import java.util.ArrayList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.com.bjnews.newsroom.R;
import cn.com.bjnews.thinker.adapter.MenuAdapter;
import cn.com.bjnews.thinker.entity.MainSettingEntity;
import cn.com.bjnews.thinker.entity.MenuEntity;
import cn.com.bjnews.thinker.json.JsonSettings;
import cn.com.bjnews.thinker.utils.Mconstant;
import cn.com.bjnews.thinker.utils.Utils;

/**
 * 菜单页面
 * @author sunqm
 * Create at:   2014-5-22 上午9:16:26 
 * TODO
 */
public class ActMenu extends BaseAct implements OnItemClickListener{

	private ListView listview;
	
	private MenuAdapter adapter;
	
	private List<MenuEntity> menus = new ArrayList<MenuEntity>();
	
	private MainSettingEntity settingEntity;
	
	private int menuCount;
	
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
		setContentView(R.layout.act_menu);
		setActionBarLayout(R.layout.act_menu_title);
		initView();
		initData();
		
	}


	private void initView() {
		listview = (ListView) findViewById(R.id.act_menu_listview);
		adapter = new MenuAdapter(this);
		listview.setOnItemClickListener(this);
		listview.setAdapter(adapter);
	}
	

	private void initData() {
		
		settingEntity = JsonSettings.parse(Utils.readFile(Mconstant.LOCAL_FILE_NAME));
		menus = settingEntity.menus;
		menuCount = menus.size();
		addOther();
		adapter.setData(menus);
	}

	private void addOther(){
		String[] menuOthers = {"分享app","用户反馈","用户评价","关于我们"};
		MenuEntity entity;
		for(int i =0;i<4;i++){
			entity = new MenuEntity();
			entity.setName(menuOthers[i]);
			entity.setUrl("");
			menus.add(entity);
		}
	}
	
	public void onClick(View view){
		switch(view.getId()){
		case R.id.act_menu_back:
			this.finish();
			break;
		}
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(arg2<menuCount){
			Intent i = new Intent(ActMenu.this,ActWeb.class);
			i.putExtra("url", menus.get(arg2).getUrl());
			startActivity(i);
		}else{
			if(arg2==menuCount){//分享app
				Intent intent=new Intent(Intent.ACTION_SEND);   
		        intent.setType("image/*");   
		        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");   
		        intent.putExtra(Intent.EXTRA_TEXT, "新生代读者随身新闻源——新京报新闻客户端下载:"+menus.get(0).getUrl());    
		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
		        startActivity(Intent.createChooser(intent, getTitle()));
			}else if(arg2==menuCount+1){//用后反馈
				 	String[] reciver = new String[] { settingEntity.AboutUsEmail };  
			        String[] mySbuject = null;
					try {
						mySbuject = new String[] { "新京报新闻Android版"+Utils.getVersionName(ActMenu.this)+"版用户反馈；" };
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
			        String myCc = "cc";  
			        String mybody = "测试Email Intent";  
			        Intent myIntent = new Intent(android.content.Intent.ACTION_SEND);  
			        myIntent.setType("plain/text");  
			        myIntent.putExtra(android.content.Intent.EXTRA_EMAIL, reciver);  
			        myIntent.putExtra(android.content.Intent.EXTRA_CC, myCc);  
			        myIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mySbuject);  
			        myIntent.putExtra(android.content.Intent.EXTRA_TEXT, mybody);  
			        startActivity(Intent.createChooser(myIntent, "mail test")); 
			}else if(arg2==menuCount+2){//用户评价
				
			}else if(arg2==menuCount+3){//关于我们
				Intent i = new Intent(ActMenu.this,ActWeb.class);
				i.putExtra("url", settingEntity.AboutUsUrl);
//				Log.d("tag","url-->"+settingEntity.AboutUsUrl);
				startActivity(i);
			}
		}
		
	}
	
	private void setActionBarLayout( int layoutId ){
	    ActionBar actionBar = getActionBar( );
	    if( null != actionBar ){
	        actionBar.setDisplayShowHomeEnabled( false );
	        actionBar.setDisplayShowCustomEnabled(true);
	        LayoutInflater inflator = (LayoutInflater)   this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View v = inflator.inflate(layoutId, null);
	        ActionBar.LayoutParams layout = new     ActionBar.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	        actionBar.setCustomView(v,layout);
	    }
	}
	
}
