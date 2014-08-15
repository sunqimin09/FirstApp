package cn.com.bjnews.thinker.act;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.bjnews.thinker.R;
import cn.com.bjnews.thinker.act.MainService.LocalBinder;
import cn.com.bjnews.thinker.adapter.TestAdapter;
import cn.com.bjnews.thinker.db.DbHandler;
import cn.com.bjnews.thinker.debug.MyDebug;
import cn.com.bjnews.thinker.entity.ChannelEntity;
import cn.com.bjnews.thinker.entity.MainSettingEntity;
import cn.com.bjnews.thinker.entity.NewsEntity;
import cn.com.bjnews.thinker.entity.RequestEntity;
import cn.com.bjnews.thinker.entity.ResponseResult;
import cn.com.bjnews.thinker.internet.IRequestCallBack;
import cn.com.bjnews.thinker.internet.InternetHelper;
import cn.com.bjnews.thinker.json.JsonSettings;
import cn.com.bjnews.thinker.utils.Mconstant;
import cn.com.bjnews.thinker.utils.Utils;
import cn.com.bjnews.thinker.view.MyTabPageIndicator;
import cn.com.bjnews.thinker.view.WholeViewPager;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.umeng.analytics.MobclickAgent;


public class MainActivity extends BaseAct implements IRequestCallBack,
		OnClickListener, OnPageChangeListener {

	private TextView tvLoading;

	private ImageView imgLogo;

//	private ImageView imgMenu;

	private WholeViewPager viewPager;

	/** 本地数据 */
	public static MainSettingEntity localSettingEntity;

	private TestAdapter adapter;

	private MyTabPageIndicator indicator;

	public static int currentPageIndex = 0;
	
	/**fragment 的状态*/
	public static int[] state;

	/***推送中新闻id*/
	private static int newsId = -2;
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		indicator.setCurrentItem(Mconstant.currentPageIndex);
		Log.d("tag","current-item-resume>"+viewPager.getCurrentItem());
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
//		MyDebug.testSubList();
		setContentView(R.layout.activity_main);
		// Log.d("tag","localfile-->"+Mconstant.LOCAL_FILE_NAME);
		initView();
		initData();
		// 发起网络请求
		request(Mconstant.TIME_OUT);

//		Log.d("tag", "path->"
//				+ Environment.getDataDirectory().getAbsolutePath());
	}

	/**
	 * 初始化各种控件
	 */
	private void initView() {
		// 各tab
		// ll_tab = (LinearLayout) findViewById(R.id.activity_main_type_ll);
		tvLoading = (TextView) findViewById(R.id.activity_main_loading_tv);
		imgLogo = (ImageView) findViewById(R.id.activity_main_logo);
//		imgMenu = (ImageView) findViewById(R.id.menu);
		viewPager = (WholeViewPager) findViewById(R.id.activity_main_viewpager);
		adapter = new TestAdapter(getSupportFragmentManager());

		viewPager.setAdapter(adapter);

		indicator = (MyTabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(viewPager);
		indicator.setOnPageChangeListener(this);
		
		localSettingEntity = new MainSettingEntity();
	}

	/**
	 * 初始化数据，读取本地数据， 获取网络数据，
	 */
	private void initData() {
		readLocalData();
		int channelId = getIntent().getIntExtra("channelId", 0);
//		Log.d("tag","channelId-->"+channelId);
		showLocalData(localSettingEntity);
		state = new int[localSettingEntity.channelList.size()];
		indicator.setCurrentItem(getPageIndex(channelId));
		Mconstant.currentPageIndex = getPageIndex(channelId);
		// 启动推送服务
		 PushManager.startWork(getApplicationContext(),
		 PushConstants.LOGIN_TYPE_API_KEY,
		 Utils.getMetaValue(MainActivity.this, "api_key"));
		 List<String> list = new ArrayList<String>();
		 list.add("bjnews212");//目前都是test 系列
		 PushManager.setTags(this, list);
		 
	}

	/**
	 * 设置推送
	 */
	private void setPush(Intent intent){
		Log.d("tag","setPush-->+"+getIntent().hasExtra("channelId")+getIntent().getSerializableExtra("news"));
		if(intent.hasExtra("channelId")){//如果是推送消息
			int channelId = intent.getIntExtra("channelId", 0);
			int newsId = intent.getIntExtra("news_id", 0);
			NewsEntity entity = (NewsEntity) intent.getSerializableExtra("news");
			if(intent.getSerializableExtra("news")==null){//本地不存在，需要请求网络数据，请求网络后，继续跳转
				indicator.setCurrentItem(getPageIndex(channelId));
				Log.d("tag","currentpage--==>"+getPageIndex(channelId));
				this.newsId = newsId;
//				((Fragment_First)adapter.getItem(getPageIndex(channelId))).request();
				
			}else{//已经存在，设置当前栏目，并跳转
				indicator.setCurrentItem(getPageIndex(channelId));
				Intent i = new Intent(MainActivity.this,NewsDetailAct.class);
				i.putExtra("news", entity);
				startActivityForResult(i, 0);
				if(service!=null){
					service.update(newsId, 1);
				}else{
					DbHandler.getInstance(this).update(newsId, 1);
				}
			}
			
		}
		
	}
	
	public int getArticalId(){
		return newsId;
	}
	
	public void setArticalId(int id){
		newsId = id;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setPush(intent);
		super.onNewIntent(intent);
	}

	
	
	public static int getPageIndex(int channelId) {
		for (int i = 0; i < localSettingEntity.channelList.size(); i++) {
			if (localSettingEntity.channelList.get(i).id == channelId){
//				Log.d("tag","channelid--pageIndex"+i);
				return i;
			}
		}
		return 0;
	}
	
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.menu:// 菜单页面
			startActivity(new Intent(MainActivity.this, ActMenu.class));
			break;
		}
	}

	/**
	 * 读取本地数据
	 */
	private void readLocalData() {
		// 开始读取本地
		String localData = Utils.readFile(Mconstant.LOCAL_FILE_NAME);
		if (null == localData) {// 如果本地文件不存在
			return;
		} else {
			localSettingEntity = JsonSettings.parse(localData);
		}
		DbHandler dbHandler = DbHandler.getInstance(this);
		for (ChannelEntity entity : localSettingEntity.channelList) {
			dbHandler.updateChannelUpdate(entity.id, 0);
		}

	}

	/**
	 * 显示本地数据
	 */
	private void showLocalData(MainSettingEntity data) {
		// 如果没有数据
		if (data.channelList.size() == 0) {// 没有数据
//			Toast.makeText(this, ".", Toast.LENGTH_SHORT).show();
			// 此处处理无数据情况
		} else {// 有数据
			adapter.setData(data.channelList);
			indicator.notifyDataSetChanged();
			viewPager.setCurrentItem(0);
		}
	}

	/**
	 * 网络请求，对不后，更新本地数据
	 */
	private void updateLocalData(MainSettingEntity entity, String content) {
		// 开始读取本地
		Utils.writeToFile(content, Mconstant.LOCAL_FILE_NAME);
	}

	public void showLoading(int str) {
//		Log.d("tag","showLoading--->"+getString(str));
		if(tvLoading.getVisibility() == View.VISIBLE){//已经显示
			tvLoading.setText(getString(str));
		}else{
			tvLoading.setText(getString(str));
			imgLogo.setVisibility(View.GONE);
//			imgMenu.setVisibility(View.GONE);
			Animation animation = AnimationUtils.loadAnimation(this,
					R.anim.down);
			tvLoading.setAnimation(animation);
			tvLoading.setVisibility(View.VISIBLE);
		}

	}

	public void hideLoading() {
		tvLoading.clearAnimation();
		tvLoading.setVisibility(View.GONE);
//		imgMenu.setVisibility(View.VISIBLE);
		imgLogo.setVisibility(View.VISIBLE);
	}

	@Override
	public void request(int timeOut) {
		RequestEntity requestEntity = new RequestEntity(Mconstant.URL_CHANNELS);
		new InternetHelper(this).requestThread(requestEntity, this,timeOut);
	}

	@Override
	public void requestSuccess(ResponseResult responseResult) {
		
		// 服务器上的设置
		MainSettingEntity remoteSettingEntity = JsonSettings
				.parse(responseResult.resultStr);
		if (!localSettingEntity.compare(remoteSettingEntity)) {// 如果不一致,更新本地
			localSettingEntity = remoteSettingEntity;
			updateLocalData(remoteSettingEntity, responseResult.resultStr);
			showLocalData(remoteSettingEntity);
		}
		setPush(getIntent()); 

	}

	@Override
	public void requestFailedStr(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		currentPageIndex = arg0;
		Mconstant.currentPageIndex = arg0;
//		Log.d("tag","new==pageIndex-pageSElected--->"+arg0);
		 ((Fragment_First)adapter.getItem(arg0)).pageSelected(localSettingEntity.channelList.get(arg0).id);
		 
	}
	
	public static boolean isRefreshing(int pageIndex){
		if(pageIndex>state.length||pageIndex==state.length){
			return false;
		}
		return state[pageIndex]==1;
	}
	
//	public static int getState(int pageIndex){
//		if(pageIndex>state.length||pageIndex==state.length){
//			return -1;
//		}
//		return state[pageIndex];
//	}
	
	public static void setState(int pageIndex,int stateTemp){
		if(pageIndex>state.length||pageIndex==state.length){
			return ;
		}
		state[pageIndex] = stateTemp;
	}

	
	@Override
	protected void onStart() {
		
		Intent intent = new Intent(this, MainService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		unbindService(mConnection);
	}

	/** 与service 通信 */
	private MainService service = null;
	
	public MainService getService(){
		return service;
	}

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className,
				IBinder localBinder) {
			service = ((LocalBinder) localBinder).getService();
//			Log.d("tag", "service-connected->" + service);
		}

		public void onServiceDisconnected(ComponentName arg0) {
			service = null;
		}
	};

}