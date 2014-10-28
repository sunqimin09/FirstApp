package cn.com.bjnews.thinker.act.test;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.bjnews.newsroom.R;
import cn.com.bjnews.thinker.act.Fragment_First;
import cn.com.bjnews.thinker.entity.ChannelEntity;
import cn.com.bjnews.thinker.entity.MainSettingEntity;
import cn.com.bjnews.thinker.json.JsonSettings;
import cn.com.bjnews.thinker.utils.Mconstant;
import cn.com.bjnews.thinker.utils.Utils;

/**
 * 修改测试
 * @author sunqm
 *
 */
public class TestMainAct extends FragmentActivity {
	/**标题父控件*/
	private LinearLayout lltabs;
	/**标题滑动控件*/
	private NavigateHorizonScrollView scrollViewtabs;
	
	private ViewPager viewPager;
	/***/
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	
	private MainSettingEntity localSettingEntity;
	/**导航显示的数据*/
	private List<ChannelEntity> channels = new ArrayList<ChannelEntity>();
	
	/** 屏幕宽度，，主要用于导航栏的显示*/
	private int mScreenWidth;
	/**每一tab 的宽度*/
	private int tabWidth;
	/**是否刷新标志位，1.正在刷新，0：没有正在刷新*/
	private int[] refreshing;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.test_main);
		initView();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void initView(){
		mScreenWidth = BaseTools.getWindowsWidth(this);
		tabWidth = mScreenWidth/7;
		scrollViewtabs = (NavigateHorizonScrollView) findViewById(R.id.test_scrollviewtabs);
		lltabs = (LinearLayout) findViewById(R.id.test_tabs_ll);
		viewPager = (ViewPager) findViewById(R.id.test_viewpager);
		setChangeView();
	}
	
	/**
	 * 栏目变化时调用
	 */
	private void setChangeView(){
		initTabData();
		initTab();
		initFragment();

	}
	private void initTabData() {
		//从服务端获取数据。。如果有就显示
//		channels
		String localData = Utils.readFile(Mconstant.LOCAL_FILE_NAME);
		if (null == localData) {// 如果本地文件不存在
			return;
		} else {
			localSettingEntity = JsonSettings.parse(localData);
			channels = localSettingEntity.channelList;
		}
		refreshing = new int[channels.size()];
		
	}		
		
	private void initTab() {
		// TODO 
		int countTemp = channels.size();
		LinearLayout.LayoutParams paramsTemp = new LinearLayout.LayoutParams(
				tabWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
		paramsTemp.setMargins(5, 0, 5, 0);
		
		for(int i = 0;i<countTemp;i++){
			TextView tvTab = new TextView(this);
			tvTab.setGravity(Gravity.CENTER);
			tvTab.setId(i);
			tvTab.setClickable(true);
			tvTab.setText(channels.get(i).name);
			tvTab.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					//
					
				}
			});
			lltabs.addView(tvTab, i, paramsTemp);
			
		}
		
	}


	private void initFragment() {
		fragments.clear();
		//
		Bundle bTemp = new Bundle();
		for(int i =0;i<channels.size();i++){
			Fragment_First fragmentTemp = new Fragment_First();
			bTemp.putInt("index", i);
			bTemp.putString("name", channels.get(i).name);
			bTemp.putInt("channelId", channels.get(i).id);
			fragmentTemp.setArguments(bTemp);
			fragments.add(fragmentTemp);
		}
		TestNewsFragmentPagerAdapter viewpagerAdapter = new TestNewsFragmentPagerAdapter(
				getSupportFragmentManager(), fragments);
		viewPager.setAdapter(viewpagerAdapter);
		if(fragments.size()>0)
			viewPager.setCurrentItem(0);
	}
	
	/**
	 * 
	 * @param pageIndex
	 * @return 该页是否正在刷新
	 */
	public boolean isRefreshing(int pageIndex){
		if(pageIndex<refreshing.length)
			return refreshing[pageIndex] ==1;
		else 
			return false;
	}
	
	/**
	 * 设置当前的状态
	 * @param pageIndex
	 * @param state
	 */
	public void setRefresh(int pageIndex,int state){
		if(pageIndex<refreshing.length)
			 refreshing[pageIndex] = state;
	}
	
	
}
