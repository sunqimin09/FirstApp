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
import android.text.method.HideReturnsTransformationMethod;
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
import cn.com.bjnews.thinker.img.ImgUtils;
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
	/** 控制标题背景色 */
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

	private int currentAddPageIndex = 0;

	private int selectedPageIndex = 0;

	private MListView listview = null;

	private FragmentFirstAdapter adapter;

	private List<NewsEntity> list = new ArrayList<NewsEntity>();

	private NewsListEntity locaListEntity = null;

	private DbHandler dbHandler;

	private TextView tvCaption;

	private LinearLayout llHeaderIcon;

	private ArrayList<AdIntroEntity> adsNew = new ArrayList<AdIntroEntity>();

	private ImageLoader imgLoader;

	/** 是否开始刷新 */
	public static boolean isRefreshStarted = false;

	public boolean isRefreshingTest = false;

	/** 请求被意外打断 */
	public static boolean requestInterupted = false;

	private boolean isPaused = false;

	private static final int VIEWPAGER = 1;

	public static Fragment_First newInstance(int pageIndex) {
		Fragment_First fragment = new Fragment_First();
		Bundle b = new Bundle();
		b.putInt("key", pageIndex);
		fragment.setArguments(b);
		fragment.pageIndex = pageIndex;

		return fragment;
	}

	private int pageIndex = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MyDebug.setCurrentTime();
		currentPagerIndex = getArguments().getInt("key");
		Log.d("tag", pageIndex + "new==pageIndex->"
				+ Mconstant.currentPageIndex);
		View view = View.inflate(getActivity(), R.layout.firstfragment, null);
		initView(view);
		// Log.d("tag","fragment-oncreate-IsRefresh:"+MainActivity.getState(currentPagerIndex));
		initData();
		if (MainActivity.getState(currentPagerIndex) == 1) {
			showLoading();
		} else if (MainActivity.getState(Mconstant.currentPageIndex) == 0) {// 当前显示的页，没有进行加载，需要不显示title
			showstopLoading();
		} else if (MainActivity.getState(Mconstant.currentPageIndex) == 1) {
			showLoading();
		}
		// if(savedInstanceState!=null)
		// Log.d("tag","fragment-oncreate--end-Pageindex:"+savedInstanceState.getInt("refreshstate"));
		return view;
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
		llCaptionBg = (LinearLayout) HeaderView
				.findViewById(R.id.fragment_first_header_caption_ll);
		tvCaption = (TextView) HeaderView
				.findViewById(R.id.fragment_first_ad_caption);
		tvCaption.setTextColor(Color.WHITE);
		viewPager = (MViewPager) HeaderView
				.findViewById(R.id.fragment_first_viewpager);
		llHeaderIcon = (LinearLayout) HeaderView
				.findViewById(R.id.fragment_first_header_icon_ll);
		views = new ArrayList<View>();
		viewpagerAdapter = new FragmentFirstViewPagerAdapter(getActivity(),
				views);

		viewPager.setAdapter(viewpagerAdapter);
		viewPager.setOnPageChangeListener(this);
		viewPager.setOnClickListener(this);

		listview.addHeaderView(HeaderView);
		listview.setOnScrollListener(this);
		adapter = new FragmentFirstAdapter(list, getActivity());
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		HeaderView.setVisibility(View.GONE);

		dbHandler = DbHandler.getInstance(getActivity());// new
															// DbHandler(getActivity());

		// dbHandler.selectNews(1,29807);
		imgLoader = new ImageLoader(getActivity(), R.drawable.default_img,
				false);
		resizeViewpager();

		// if(MainActivity.getState(Mconstant.currentPageIndex)==1){
		// // startLoading();
		// }else{
		// // stopLoading();
		// }
	}

	private void resizeViewpager() {
		ViewGroup.LayoutParams params = viewPager.getLayoutParams();
		params.width = CommonUtil.getScreenWidth(getActivity());
		params.height = (int) CommonUtil.getScreenWidth(getActivity()) * 2 / 3;
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
				loadingBarIndeterminate, this);

	}

	private void initData() {
		readLocalData(MainActivity.localSettingEntity.channelList
				.get(currentPagerIndex).id);
		Log.d("tag", "readlocal->" + (pageIndex) + "current"
				+ MainActivity.currentPageIndex);
	}

	public void pageSelected(int channelId) {
		Log.d("tag", Mconstant.currentPageIndex + "fragment-pageSelected>"
				+ getActivity());
		if (MainActivity.getState(Mconstant.currentPageIndex) == 1) {
			startLoading();
		} else {
			// stopLoading();
		}
	}

	/**
	 * 读取本地数据
	 */
	private void readLocalData(int channelId) {
		dbHandler.readNewsList(this, channelId);
	}

	/**
	 * 读取并显示本地数据
	 */
	private void showData(NewsListEntity locaListEntity) {
		
		// viewpager
		Log.d("tag", "readlocal-->showData--" + currentPagerIndex + "><"
				+ locaListEntity);
		ArrayList<AdIntroEntity> adsOld = locaListEntity.getAds();
		adsNew.clear();

		ImageView v1 = null;
		ImageView iconTemp = null;
		views = new ArrayList<View>();
		llHeaderIcon.removeAllViews();
		int count = 0;
		// 添加新闻
		if (locaListEntity.getNewsList().size() > 0) {
			HeaderView.setVisibility(View.VISIBLE);
			// 视图添加
			v1 = new ViewPagerImageView(getActivity());
			ImgUtils.showBackground(v1, R.drawable.default_img);
//			v1.setBackgroundResource(R.drawable.default_img);
			Log.d("tag","First--image->22"+locaListEntity.getNewsList().get(0).medias.size());
			if (locaListEntity.getNewsList().get(0).medias.size() > 0) {
				imgLoader.setImgWidth(CommonUtil.getScreenWidth(getActivity()));
				imgLoader.setIsBg(false);
				imgLoader.DisplayImage(
						locaListEntity.getNewsList().get(0).medias.get(0).pic,
						v1, false);
				Log.d("tag","First--image->00"+locaListEntity.getNewsList().get(0).medias.get(0).pic);
			} else {
				imgLoader.setImgWidth(CommonUtil.getScreenWidth(getActivity()));
				imgLoader.setIsBg(false);
				imgLoader.DisplayImage(
						locaListEntity.getNewsList().get(0).thumbnail, v1,
						false);
				Log.d("tag",v1+"First--image->11"+locaListEntity.getNewsList().get(0).thumbnail);
			}
			v1.setId(VIEWPAGER);
			v1.setOnClickListener(this);

			views.add(v1);

			tvCaption.setText(locaListEntity.getNewsList().get(0).title);
			// 添加下部icon
			iconTemp = new ImageView(getActivity());
			iconTemp.setPadding(8, 10, 8, 10);
			iconTemp.setImageResource(R.drawable.scroll_selected);
			llHeaderIcon.addView(iconTemp, 0);
			// 数据添加
			AdIntroEntity ad = new AdIntroEntity();
			ad.id = (locaListEntity.getNewsList().get(0).id + "");
			if (locaListEntity.getNewsList().get(0).medias.size() > 0)
				ad.picUrl = (locaListEntity.getNewsList().get(0).medias.get(0).pic);
			ad.caption = locaListEntity.getNewsList().get(0).title;
			adsNew.add(ad);
			count = 1;
		}
		adsNew.addAll(adsOld);
		if (adsNew.size() < 2) {// 只有一条数据
			llHeaderIcon.setVisibility(View.GONE);
		} else {
			llHeaderIcon.setVisibility(View.VISIBLE);
		}
		// 添加广告,添加下部的icon 标示

		for (int i = 0; i < adsOld.size(); i++) {
			v1 = new ViewPagerImageView(getActivity());

			v1.setBackgroundResource(R.drawable.default_img);
			imgLoader.DisplayImage(adsOld.get(i).picUrl, v1, false);
			v1.setId(VIEWPAGER);
			v1.setOnClickListener(this);
			views.add(v1);

			// 下部图标添加
			iconTemp = new ImageView(getActivity());
			iconTemp.setPadding(8, 9, 8, 10);
			iconTemp.setImageResource(R.drawable.scroll_selected);
			llHeaderIcon.addView(iconTemp, i + count);
		}
		Utils.setViewPagerIcon(llHeaderIcon, 0);
		
		
		// listview
		if (locaListEntity.getNewsList() != null
				&& locaListEntity.getNewsList().size() > 0)
			list = (locaListEntity.getNewsList()).subList(1, locaListEntity
					.getNewsList().size());
		adapter.setData(list);
		// Log.d("tag","show-data-News>"+adsNew.size());
		
		viewpagerAdapter = new FragmentFirstViewPagerAdapter(getActivity(),
				views);
		viewpagerAdapter.setMedias(adsNew);
		viewPager.setAdapter(viewpagerAdapter);
//		 Log.d("tag","Request--viewpager-scroll-start2>"+list.size());
	}

	private void showDataTest(NewsListEntity locaListEntity){
		//listview  显示数据
		if (locaListEntity.getNewsList() != null
				&& locaListEntity.getNewsList().size() > 0)
			list = (locaListEntity.getNewsList()).subList(1, locaListEntity
					.getNewsList().size());
		adapter.setData(list);
		adsNew.clear();
		views.clear();
		llHeaderIcon.removeAllViews();
		//viewpager 显示数据？？
		//adsnews  data
		if (locaListEntity.getNewsList().size() > 0) {
			HeaderView.setVisibility(View.VISIBLE);
			AdIntroEntity ad = new AdIntroEntity();
			ad.id = (locaListEntity.getNewsList().get(0).id + "");
			ad.picUrl = locaListEntity.getNewsList().get(0).medias.size() > 0 ? locaListEntity
					.getNewsList().get(0).medias.get(0).pic : locaListEntity
					.getNewsList().get(0).thumbnail;
			ad.caption = locaListEntity.getNewsList().get(0).title;
			adsNew.add(ad);
			tvCaption.setText(ad.caption);
		}
		adsNew.addAll(locaListEntity.getAds());
		
		
		ImageView img;
		ImageView ImgIcon;
		imgLoader.setImgWidth(CommonUtil.getScreenWidth(getActivity()));
		imgLoader.setIsBg(false);
		for(int i=0;i<adsNew.size();i++){//
			img = new ImageView(getActivity());
			img.setId(VIEWPAGER);
			img.setOnClickListener(this);
					
			imgLoader.DisplayImage(adsNew.get(i).picUrl, img, false);
			views.add(img);
			// 添加icon
			ImgIcon = new ImageView(getActivity());
			ImgIcon.setPadding(8, 9, 8, 10);
			ImgIcon.setImageResource(R.drawable.scroll_selected); 
			llHeaderIcon.addView(ImgIcon,i);
			
		}
		
		viewpagerAdapter = new FragmentFirstViewPagerAdapter(getActivity(), views);
//		viewpagerAdapter.setViews(views);
		viewpagerAdapter.setMedias(adsNew);
		viewPager.setAdapter(viewpagerAdapter);
		//显示下部的 icon
		Utils.setViewPagerIcon(llHeaderIcon, 0);
		if (adsNew.size() < 2) {// 只有一条数据
			llHeaderIcon.setVisibility(View.GONE);
		} else {
			llHeaderIcon.setVisibility(View.VISIBLE);
		}
		
		
		
	}
	
	/**
	 * 网络请求，更新本地数据
	 */
	private void updateLocalData(NewsListEntity content) {
		dbHandler.update(content);
	}

	/**
	 * O To start refreshing.
	 */
	public void startLoading() {
		
		if (!isRefreshStarted || MainActivity.getState(currentPagerIndex) != 1) {// 该页面没有正在加载
			if (getActivity() != null) {
				this.Refresh();
				showLoading();
				Log.d("tag", "startloading---->" + isRefreshStarted);
			}
		}
	}

	/**
	 * To stop refreshing.
	 */
	public void stopLoading() {
		
		if (isRefreshStarted) {
			showstopLoading();
			// if(refreshListnerInstance != null){
			this.postRefresh();
			// }

		}
	}

	private void showstopLoading() {
		loadingBars.setVisibility(View.INVISIBLE);
		loadingBarIndeterminate.setVisibility(View.GONE);
		loadingBarIndeterminate.setIndeterminate(false);
		((MainActivity) getActivity()).hideLoading();// (R.string.loading)
		isRefreshStarted = false;
		requestInterupted = false;
		isRefreshingTest = false;
		
	}

	private void showLoading() {
		((MainActivity) getActivity()).showLoading(R.string.loading);
		loadingBars.setVisibility(View.VISIBLE);
		loadingBarIndeterminate.setVisibility(View.VISIBLE);
		loadingBarIndeterminate.setIndeterminate(true);
		isRefreshStarted = true;
		isRefreshingTest = true;
//		Toast.makeText(getActivity(), "loading---", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 显示下拉即可刷新
	 */
	private void showPreLoading(){
		
	}

	/**
	 * 是否在刷新
	 * 
	 * @return
	 */
	public boolean isRefreshing() {
		return isRefreshStarted;
	}
	
	/**
	 * 处理推送 消息
	 */
	private void doPush(){
		int newsId = ((MainActivity) getActivity()).getArticalId();
		NewsEntity entity = null;
		Log.d("tag","doPush-->"+newsId);
		for(int i=0;i<locaListEntity.newsList.size();i++){
			if(newsId==locaListEntity.newsList.get(i).id){//获得该
				entity = locaListEntity.newsList.get(i);
			}
		}//获取到
		skipToDetail(newsId, entity);
		if(newsId>-1){//请求该id 数据,请求后跳转
//			Toast.makeText(getActivity(), "跳转"+entity, Toast.LENGTH_SHORT).show();
			
			//该推送已经处理
			if(entity!=null)
				((MainActivity) getActivity()).setArticalId(-2);
		}
	}
	
	private void skipToDetail(int newsId,NewsEntity entity){
		if(entity==null){
			return;
		}
		Intent i = new Intent(getActivity(), NewsDetailAct.class);
		i.putExtra("news",entity);
		startActivityForResult(i, 0);
		Log.d("tag","skip-->");
		((MainActivity) getActivity()).getService().update(newsId, 1);
	}

	@Override
	public void request(int timeOut) {
		RequestEntity requestEntity = new RequestEntity(
				MainActivity.localSettingEntity.channelList
						.get(currentPagerIndex).url);
		// requestEntity.setUrl("http://app.bjnews.com.cn/m/json/worldcup_test.html");
		Log.d("tag", "readlocal--request==start" + currentPagerIndex);
		new InternetHelper(getActivity()).requestThread(requestEntity, this,timeOut);
	}

	@Override
	public void requestSuccess(ResponseResult responseResult) {
		NewsListEntity remoteListEntity = JsonNewsList
				.parse(responseResult.resultStr);
		
		// Log.d("tag","request==success>"+responseResult.resultStr);
		// Log.d("tag","request==success1>"+remoteListEntity);
		if (getActivity() != null) {
//			 Toast.makeText(getActivity(),isRefreshStarted+"requestSuccess-->"+remoteListEntity.getNewsList().size(),
//			 Toast.LENGTH_SHORT).show();
			 Log.d("tag","RequestSuccessssss-->"+isRefreshStarted);
			 showstopLoading();
		}

		if (locaListEntity != null && remoteListEntity.equal(locaListEntity)) {// 不更新  &&
//
		} else {// 更新 本地没数据，更新，本地有数据，但是时间不一致，更新
		// Log.d("tag","requestSuccess-update-Pageindex:"+pageIndex);
			locaListEntity = copy(remoteListEntity);
			updateLocalData(locaListEntity);
//			showData(locaListEntity);
//			Toast.makeText(getActivity(), "请求结束", Toast.LENGTH_SHORT).show();
			doPush();
			Log.d("tag", "viewpager--width->" + viewPager.getWidth()
					+ "Height>" + viewPager.getHeight());
		}
	}

	@Override
	public void requestFailedStr(String str) {
		// Log.d("tag","request==failed"+isRefreshStarted);
		if (getActivity() != null
				&& currentPagerIndex == MainActivity.currentPageIndex) {//
			Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();

		}
		if(getActivity()!=null){
			showstopLoading();
		}
		

	}

	@Override
	public void onPause() {
		// Log.d("tag","fragment-pause-->"+currentPagerIndex);
		isPaused = true;
		// isRefreshStarted = false;
		// isRefreshingTest = false;
		super.onPause();
	}

	@Override
	public void onResume() {
		isPaused = false;
		Log.d("tag","onResume--fragment-->"+((MainActivity) getActivity()).getArticalId());
		if(getActivity()!=null){
			if(((MainActivity) getActivity()).getArticalId()>-1){//请求该id 数据,请求后跳转
				startLoading();
				request(Mconstant.TIME_OUT);
//				Toast.makeText(getActivity(), "开始请求数据", Toast.LENGTH_SHORT).show();
			}
		}
			
		super.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		skipToDetail(list.get((int) arg3).id, list.get((int) arg3));
		list.get((int) arg3).state = (1);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		currentAddPageIndex = arg0;

		// if(arg0==0){
		// Mconstant.canPullStart = true;
		// }
		// if(arg0 == adsNew.size()-1){
		// Mconstant.canPullEnd = true;
		// }
		Utils.setViewPagerIcon(llHeaderIcon, arg0);
		if (adsNew.size() > arg0 && adsNew.get(arg0).picUrl != null) {
			// Log.d("tag",arg0+"caption--->"+adsNew.get(arg0).caption);
			tvCaption.setText(adsNew.get(arg0).caption);
			if (adsNew.get(arg0).caption == null
					|| adsNew.get(arg0).caption.equals("")) {
				llCaptionBg
						.setBackgroundResource(R.drawable.listview_cachecolor);
			} else {
				llCaptionBg
						.setBackgroundResource(R.drawable.background_caption);
			}
			// imgLoader.DisplayImage(adsNew.get(arg0).picUrl,(ImageView)viewPager.getChildAt(arg0),false);
		}
	}

	@Override
	public void onClick(View v) {
		// Log.d("tag","News==size11"+locaListEntity.getNewsList().size());
		if (!Mconstant.isClickAble) {
			return;
		}
		new Utils().setViewUnable();
		switch (v.getId()) {
		case R.id.fragment_first_viewpager:
			if (currentAddPageIndex == 0) {// 详细页面
			// Log.d("tag","News==size"+locaListEntity.getNewsList().size());
				Intent i = new Intent(getActivity(), NewsDetailAct.class);
				i.putExtra("news", locaListEntity.getNewsList().get(0));
				startActivityForResult(i, 0);

			} else {// 跳转至web页面
				Intent i = new Intent((MainActivity) getActivity(),
						ActWeb.class);
				i.putExtra(
						"url",
						locaListEntity.getAds().get(currentAddPageIndex - 1).url);
				startActivity(i);
			}
			// Toast.makeText(getActivity(), "viewpager",
			// Toast.LENGTH_SHORT).show();
			break;
		case VIEWPAGER:

			break;

		}
	}

	private NewsListEntity copy(NewsListEntity newsList) {
		if (locaListEntity == null) {
			return newsList;
		}
		if (!locaListEntity.equal(newsList)) {
			for (NewsEntity entity : newsList.newsList) {
				for (NewsEntity item : locaListEntity.newsList) {
					if (entity.equal(item)) {
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

		if (MainActivity.localSettingEntity.channelList.get(currentPagerIndex).id != entity.channelId)
			return;
		locaListEntity = copy(entity);
		if(locaListEntity.pubDate!=null)
		Log.d("tag", "readlocal-success>" + Utils.isOld(locaListEntity.pubDate) + "<>"
				+ locaListEntity.getNewsList().size());
		// Log.d("tag",
		// "Pageindex:" + pageIndex + "readlocal-success ads>"
		// + locaListEntity.ads.size() + "size:"
		// + locaListEntity.newsList.size());

//		showData(locaListEntity);
		showDataTest(locaListEntity);
		if ((locaListEntity.pubDate != null && Utils
				.isOld(locaListEntity.pubDate))
				|| locaListEntity.newsList.size() == 0) {//老数据。或者没有数据
			startLoading();
			;
		}
	}

	@Override
	public void Refresh() {
		Log.d("tag", "readlocal---showLoading---refresh>" + getActivity());
		if (getActivity() != null) {
			((MainActivity) getActivity()).showLoading(R.string.loading);
			request(Mconstant.TIME_OUT);
			MainActivity.setState(currentPagerIndex, 1);
		}

	}

	@Override
	public void postRefresh() {
		// Log.d("tag","hide-post-->");
		((MainActivity) getActivity()).hideLoading();
		MainActivity.setState(currentPagerIndex, 0);
	}

	@Override
	public void preRefresh() {
		 Log.d("tag","showLoading---pre-pull>");
		((MainActivity) getActivity()).showLoading(R.string.pull_refresh);
		MainActivity.setState(currentPagerIndex, 1);
	}

	@Override
	public void interruptPreRefresh() {
		// Log.d("tag","hide-interrupt-->");
		((MainActivity) getActivity()).hideLoading();
		MainActivity.setState(currentPagerIndex, 0);
	}

	public int getPageIndex() {
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
			// Log.d("tag","idle-fling-->");
			adapter.setFlagBusy(true);
			return;
		case OnScrollListener.SCROLL_STATE_IDLE:
			// Log.d("tag","idle-->");
			adapter.setFlagBusy(false);
			// adapter.notifyDataSetChanged();
			break;
		case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
			// Log.d("tag","idle--touch-scroll>");
			// adapter.setFlagBusy(false);
			return;
		default:
			break;
		}
		// Log.d("tag","notifyData---->");

	}

}
