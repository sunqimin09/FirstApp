package cn.com.bjnews.thinker.act;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.bjnews.thinker.R;
import cn.com.bjnews.thinker.adapter.FragmentFirstAdapter;
import cn.com.bjnews.thinker.adapter.FragmentFirstViewPagerAdapter;
import cn.com.bjnews.thinker.db.DbHandler;
import cn.com.bjnews.thinker.db.IDbCallBack;
import cn.com.bjnews.thinker.debug.MyDebug;
import cn.com.bjnews.thinker.entity.AdIntroEntity;
import cn.com.bjnews.thinker.entity.NewsEntity;
import cn.com.bjnews.thinker.entity.NewsListEntity;
import cn.com.bjnews.thinker.entity.RequestEntity;
import cn.com.bjnews.thinker.entity.ResponseResult;
import cn.com.bjnews.thinker.img.CommonUtil;
import cn.com.bjnews.thinker.img.ImageLoader;
import cn.com.bjnews.thinker.internet.IRequestCallBack;
import cn.com.bjnews.thinker.internet.InternetHelper;
import cn.com.bjnews.thinker.json.JsonNewsList;
import cn.com.bjnews.thinker.utils.Mconstant;
import cn.com.bjnews.thinker.utils.Utils;
import cn.com.bjnews.thinker.view.IRefreshListner;
import cn.com.bjnews.thinker.view.MListView;
import cn.com.bjnews.thinker.view.MViewPager;
import cn.com.bjnews.thinker.view.ViewPagerImageView;


/**
 * 
 * @author sunqm Create at: 2014-5-13 上午7:27:56 TODO 要闻
 */
public class Fragment_First extends Fragment implements IRequestCallBack,
		OnItemClickListener, OnPageChangeListener, OnClickListener,
		IDbCallBack, IRefreshListner, OnScrollListener {

	private View HeaderView;
	/**控制标题背景色*/
	private LinearLayout llCaptionBg;
	
	private MViewPager viewPager;

	/** 刷新控件 */
	private LinearLayout loadingBars;
	private ProgressBar loadingBarLeft;
	private ProgressBar loadingBarRight;
	private ProgressBar loadingBarIndeterminate;
	
	private FragmentFirstViewPagerAdapter viewpagerAdapter;

	private ArrayList<View> views = null;

	private int currentPagerIndex = 0;

	private MListView listview = null;

	private FragmentFirstAdapter adapter;

	private List<NewsEntity> list = new ArrayList<NewsEntity>();
	
	private NewsListEntity locaListEntity = null;
	
	private DbHandler dbHandler ;
	
	private TextView tvCaption;
	
	private LinearLayout llHeaderIcon;
	
	private ArrayList<AdIntroEntity> adsNew = new ArrayList<AdIntroEntity>();
	
	private ImageLoader imgLoader;
	
	/**是否开始刷新*/
	public static boolean isRefreshStarted = false;
	
	public boolean isRefreshingTest = false;
	
	/**请求被意外打断*/
	public static boolean requestInterupted = false;
	
	private boolean isPaused = false;
	
	private static final int VIEWPAGER =1;
	
	public static Fragment_First newInstance(int pageIndex) {
		Fragment_First fragment = new Fragment_First();
		Bundle b = new Bundle();
		b.putInt("key", pageIndex);
		fragment.setArguments(b);
		fragment.pageIndex = pageIndex;
		
		return fragment;
	}
	
	private int pageIndex =0;
	
	private int currentPageIndex = 0;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MyDebug.setCurrentTime();
		currentPageIndex = getArguments().getInt("key");
		Log.d("tag","new==pageIndex->"+currentPageIndex);
		View view = View.inflate(getActivity(), R.layout.firstfragment, null);
		initView(view);
		Log.d("tag","fragment-oncreate-IsRefresh:"+isRefreshStarted);
		initData();
		if(savedInstanceState!=null)
		Log.d("tag","fragment-oncreate--end-Pageindex:"+savedInstanceState.getInt("refreshstate"));
		return view;
	}

	

	@Override
	public void onSaveInstanceState(Bundle outState) {
		
		outState.putInt("refreshstate",1);
		super.onSaveInstanceState(outState);
		Log.d("tag", "saveInstance-->");
	}



	@SuppressLint("ServiceCast")
	private void initView(View view) {
		listview = (MListView) view.findViewById(R.id.fragment_first_lv);
		
		initListView(view);
		// 初始化头
		HeaderView = (View) ((LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.fragment_first_lv_header, null);
		// 初始化 viewpager 各页面
		llCaptionBg = (LinearLayout) HeaderView.findViewById(R.id.fragment_first_header_caption_ll);
		tvCaption = (TextView) HeaderView.findViewById(R.id.fragment_first_ad_caption);
		tvCaption.setTextColor(Color.WHITE);
		viewPager = (MViewPager) HeaderView
				.findViewById(R.id.fragment_first_viewpager);
		llHeaderIcon = (LinearLayout) HeaderView
				.findViewById(R.id.fragment_first_header_icon_ll);
		views = new ArrayList<View>();
		viewpagerAdapter = new FragmentFirstViewPagerAdapter(getActivity(),views);
		
		viewPager.setAdapter(viewpagerAdapter);
		viewPager.setOnPageChangeListener(this);
		viewPager.setOnClickListener(this);
		
		listview.addHeaderView(HeaderView);
		listview.setOnScrollListener(this);
		adapter = new FragmentFirstAdapter(list, getActivity());
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		HeaderView.setVisibility(View.GONE);
		
		dbHandler = DbHandler.getInstance(getActivity());//new DbHandler(getActivity());
		
		dbHandler.selectNews(1,29807);
		imgLoader = new ImageLoader(getActivity(), R.drawable.default_img,false);
		resizeViewpager();
	}

	private void resizeViewpager(){
		ViewGroup.LayoutParams params = viewPager.getLayoutParams();
		params.width = CommonUtil.getScreenWidth(getActivity());
		params.height = (int)CommonUtil.getScreenWidth(getActivity())*2/3;
		viewPager.setLayoutParams(params);
		
	}
	
	private void initListView(View view) {
		loadingBars = (LinearLayout) view
				.findViewById(R.id.fragment_first_loadingBars);
		loadingBarLeft = (ProgressBar) view
				.findViewById(R.id.fragment_first_loadingBarLeft);
		loadingBarRight = (ProgressBar) view
				.findViewById(R.id.fragment_first_loadingBarRight);
		loadingBarIndeterminate = (ProgressBar) view
				.findViewById(R.id.fragment_first_loadingBarInDeterminate);
		listview.setLoading(loadingBars, loadingBarLeft, loadingBarRight,
				loadingBarIndeterminate,this);

	}

	private void initData() {
		readLocalData(MainActivity.localSettingEntity.channelList.get(pageIndex).id);
//		Log.d("tag","init Pageindex:->"+(pageIndex)+"current"+MainActivity.currentPageIndex);
	}
	
	
	public void pageSelected(int channelId){
		Log.d("tag",pageIndex+"fragment-oncreate-new==pageIndex-fragment-pageSelected>"+isRefreshStarted);
		if(isRefreshStarted){
			
		}
	}
	
	
	/**
	 * 读取本地数据
	 */
	private void readLocalData(int channelId) {
		dbHandler.readNewsList(this,channelId);
	}

	/**
	 * 读取并显示本地数据
	 */
	private void showData(NewsListEntity locaListEntity) {
//		Log.d("tag","viewpager-scroll-start1>"+Mconstant.canPullStart);
//		Log.d("tag","viewpager-scroll-end1>"+Mconstant.canPullEnd);
		Log.d("tag","readlocal-showdata-pageindex>"+pageIndex+"isPaused:"+isPaused);
		//viewpager
		ArrayList<AdIntroEntity> adsOld = locaListEntity.getAds();
		adsNew.clear();
		
		ImageView v1 = null;
		ImageView iconTemp = null;
		views = new ArrayList<View>();
		llHeaderIcon.removeAllViews();
		int count =0;
		//添加新闻
		if(locaListEntity.getNewsList().size()>0){
			HeaderView.setVisibility(View.VISIBLE);
			//视图添加
			v1 = new ViewPagerImageView(getActivity());
			v1.setBackgroundResource(R.drawable.default_img);
			if(locaListEntity.getNewsList().get(0).medias.size()>0){
				imgLoader.setImgWidth(CommonUtil.getScreenWidth(getActivity()));
				imgLoader.setIsBg(true);
				imgLoader.DisplayImage(locaListEntity.getNewsList().get(0).medias.get(0).pic, v1, false);
			}
			v1.setId(VIEWPAGER);
			v1.setOnClickListener(this);
			
			views.add(v1);
			
			tvCaption.setText(locaListEntity.getNewsList().get(0).title);
			//添加下部icon
			iconTemp = new ImageView(getActivity());
			iconTemp.setPadding(8, 10, 8, 10);
			iconTemp.setImageResource(R.drawable.scroll_selected);
			llHeaderIcon.addView(iconTemp,0);
			//数据添加
			AdIntroEntity ad = new AdIntroEntity();
			ad.id = (locaListEntity.getNewsList().get(0).id+"");
			if(locaListEntity.getNewsList().get(0).medias.size()>0)
				ad.picUrl = (locaListEntity.getNewsList().get(0).medias.get(0).pic);
			ad.caption = locaListEntity.getNewsList().get(0).title;
			adsNew.add(ad);
			count = 1;
		}
		adsNew.addAll(adsOld);
		if(adsNew.size()<2){//只有一条数据
			llHeaderIcon.setVisibility(View.GONE);
		}else{
			llHeaderIcon.setVisibility(View.VISIBLE);
		}
		//添加广告,添加下部的icon 标示
		
		for (int i  = 0; i < adsOld.size(); i++) {
			v1 = new ViewPagerImageView(getActivity());
			
			v1.setBackgroundResource(R.drawable.default_img);
			imgLoader.DisplayImage(adsOld.get(i).picUrl, v1, false);
			v1.setId(VIEWPAGER);
			v1.setOnClickListener(this);
			views.add(v1);
			
			//下部图标添加
			iconTemp = new ImageView(getActivity());
			iconTemp.setPadding(8, 9, 8, 10);
			iconTemp.setImageResource(R.drawable.scroll_selected);
			llHeaderIcon.addView(iconTemp,i+count);
		}
		Utils.setViewPagerIcon(llHeaderIcon, 0);
		viewpagerAdapter.setViews(views);
		//listview
		if(locaListEntity.getNewsList()!=null && locaListEntity.getNewsList().size()>0)
		list = (locaListEntity.getNewsList()).subList(1, locaListEntity.getNewsList().size());
		adapter.setData(list);
		Log.d("tag","show-data-News>"+adsNew.size());
		//viewpager
		viewpagerAdapter.setMedias(adsNew, getActivity());
		Log.d("tag","viewpager-scroll-start2>"+list.size());
//		Log.d("tag","viewpager-scroll-end2>"+Mconstant.canPullEnd);
	}

	
	
	/**
	 * 网络请求，更新本地数据
	 */
	private void updateLocalData(NewsListEntity content) {
		dbHandler.update(content);
	}
	
	/**
	 * To stop refreshing.
	 */
	public void stopLoading(){
		if(isRefreshStarted){
			
//			if(refreshListnerInstance != null){
				this.postRefresh();
//			}
			loadingBars.setVisibility(View.INVISIBLE);
			loadingBarIndeterminate.setVisibility(View.GONE);
			loadingBarIndeterminate.setIndeterminate(false);
			isRefreshStarted = false;
			requestInterupted = true;
			isRefreshingTest = false;
		}
	}

	/**O
	 * To start refreshing.
	 */
	public void startLoading(){
		if(! isRefreshStarted){
//			if(refreshListnerInstance != null){
				this.Refresh();
//			}
			loadingBars.setVisibility(View.INVISIBLE);
			loadingBarIndeterminate.setVisibility(View.VISIBLE);
			loadingBarIndeterminate.setIndeterminate(true);
			isRefreshStarted = true;
			isRefreshingTest = true;
		}
	}
	
	/**
	 * 是否在刷新
	 * @return
	 */
	public boolean isRefreshing(){
		return isRefreshStarted;
	}
	
	
	@Override
	public void request() {
		RequestEntity requestEntity = new RequestEntity(
				MainActivity.localSettingEntity.channelList
						.get(pageIndex).url);
//		requestEntity.setUrl("http://app.bjnews.com.cn/m/json/worldcup_test.html");
		Log.d("tag","request==start"+isRefreshStarted);
		new InternetHelper(getActivity()).requestThread(requestEntity, this);
	}

	@Override
	public void requestSuccess(ResponseResult responseResult) {
		
		NewsListEntity remoteListEntity = JsonNewsList.parse(responseResult.resultStr);
		Log.d("tag","request==success>"+responseResult.resultStr);
		Log.d("tag","request==success1>"+remoteListEntity);
		if(getActivity()!=null){
//			Toast.makeText(getActivity(),isRefreshStarted+"requestSuccess-->"+remoteListEntity.getNewsList().size(), Toast.LENGTH_SHORT).show();
			stopLoading();
		}
			
		if(locaListEntity!=null && remoteListEntity.equal(locaListEntity)){//不更新
			
		}else{//更新
			Log.d("tag","requestSuccess-update-Pageindex:"+pageIndex);
			locaListEntity =copy(remoteListEntity);
			updateLocalData(locaListEntity);
			
			showData(locaListEntity);
			Log.d("tag","viewpager--width->"+viewPager.getWidth()+"Height>"+viewPager.getHeight());
			if(views.size()>0)
			Log.d("tag","viewpager-item-width->"+views.get(0).getWidth()+"Height>"+views.get(0).getHeight());
			
			Log.d("tag","data--remote>"+remoteListEntity.getNewsList().size());
		}
	}

	@Override
	public void requestFailedStr(String str) {
		Log.d("tag","request==failed"+isRefreshStarted);
		if(getActivity()!=null&& pageIndex == MainActivity.currentPageIndex){//
			stopLoading();
			Toast.makeText(getActivity(),str, Toast.LENGTH_SHORT).show();
			
		}
			
	}

	@Override
	public void onPause() {
		Log.d("tag","fragment-pause-->");
		isPaused = true;
		super.onPause();
	}

	@Override
	public void onResume() {
		MyDebug.getTime(4);
		isPaused = false;
		Log.d("tag","fragment--resume");
		super.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Log.d("tag","onitem-->"+arg2+arg3);
		long time = System.currentTimeMillis();
//		hasRead[(int)arg3] = 1;
		list.get((int) arg3).state = (1);
		Intent i = new Intent(getActivity(),NewsDetailAct.class);
		i.putExtra("news", list.get((int) arg3));
		Log.d("tag","Spend--time-1>"+(System.currentTimeMillis()-time));
		startActivityForResult(i, 0);
		Log.d("tag",((MainActivity)getActivity()).getService()+"Spend--time-2>"+(System.currentTimeMillis()-time));
		((MainActivity)getActivity()).getService().update(list.get((int) arg3).id, 1);
		Log.d("tag","Spend--time-3>"+(System.currentTimeMillis()-time));
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		currentPagerIndex = arg0;
//		if(arg0==0){
//			Mconstant.canPullStart = true;
//		} 
//		if(arg0 == adsNew.size()-1){
//			Mconstant.canPullEnd = true;
//		}
		Utils.setViewPagerIcon(llHeaderIcon, arg0);
		if(adsNew.size()>arg0 && adsNew.get(arg0).picUrl!=null){
			Log.d("tag",arg0+"caption--->"+adsNew.get(arg0).caption);
			tvCaption.setText(adsNew.get(arg0).caption);
			if(adsNew.get(arg0).caption==null||adsNew.get(arg0).caption.equals("")){
				llCaptionBg.setBackgroundResource(R.drawable.listview_cachecolor);
			}else{
				llCaptionBg.setBackgroundResource(R.drawable.background_caption);
			}
//			imgLoader.DisplayImage(adsNew.get(arg0).picUrl,(ImageView)viewPager.getChildAt(arg0),false);
		}
	}

	@Override
	public void onClick(View v) {
		if(!Mconstant.isClickAble){
			return ;
		}
		new Utils().setViewUnable();
		switch(v.getId()){
		case R.id.fragment_first_viewpager:
			if(currentPagerIndex==0){//详细页面
				Intent i = new Intent(getActivity(),NewsDetailAct.class);
				i.putExtra("news", locaListEntity.getNewsList().get( 0));
				startActivityForResult(i, 0);
				
			}else{//跳转至web页面
				Intent i = new Intent((MainActivity)getActivity(),ActWeb.class);
				i.putExtra("url", locaListEntity.getAds().get(currentPagerIndex-1).url);
				startActivity(i);
			}
//			Toast.makeText(getActivity(), "viewpager", Toast.LENGTH_SHORT).show();
			break;
		case VIEWPAGER:
			
			break;
			
		}
	}

	private NewsListEntity copy(NewsListEntity newsList){
		if(locaListEntity == null){
			return newsList;
		}
		if(!locaListEntity.equal(newsList)){
			for (NewsEntity entity : newsList.newsList) {
				for(NewsEntity item: locaListEntity.newsList){
					if(entity.equal(item)){
						entity.state = item.state;
						break;
					}
				}
			}
		}
		return newsList;
	}
	
	/**
	 * 读取本地数据
	 */
	@Override
	public void ReadSuccess(NewsListEntity entity) {
		
		if (MainActivity.localSettingEntity.channelList.get(pageIndex).id != entity.channelId)
			return;
		locaListEntity = copy(entity);
		Log.d("tag","new==pageIndex-success>"+currentPageIndex+"<>"+locaListEntity);
		Log.d("tag",
				"Pageindex:" + pageIndex + "readlocal-success ads>"
						+ locaListEntity.ads.size() + "size:"
						+ locaListEntity.newsList.size());

		showData(locaListEntity);
		if(locaListEntity.newsList.size()==0){
			startLoading();;
		}
	}

	


	@Override
	public void Refresh() {
		Log.d("tag","showLoading---refresh>");
		((MainActivity) getActivity()).showLoading(R.string.loading);
		request();
	}

	

	@Override
	public void postRefresh() {
		Log.d("tag","hide-post-->");
		((MainActivity) getActivity()).hideLoading();
	}



	@Override
	public void preRefresh() {
		Log.d("tag","showLoading---pre>");
		((MainActivity) getActivity()).showLoading(R.string.pull_refresh);
		
	}



	@Override
	public void interruptPreRefresh() {
		Log.d("tag","hide-interrupt-->");
		((MainActivity) getActivity()).hideLoading();
	}
	
	public int getPageIndex(){
		return pageIndex;
	}



	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		
	}



	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
		case OnScrollListener.SCROLL_STATE_FLING:
			Log.d("tag","idle-fling-->");
			adapter.setFlagBusy(true);
			return;
		case OnScrollListener.SCROLL_STATE_IDLE:
			Log.d("tag","idle-->");
			adapter.setFlagBusy(false);
//			adapter.notifyDataSetChanged();
			break;
		case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
			Log.d("tag","idle--touch-scroll>");
//			adapter.setFlagBusy(false);
			return;
		default:
			break;
		}
		Log.d("tag","notifyData---->");
		
		
	}

}
