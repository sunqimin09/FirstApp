package cn.com.bjnews.thinker.act;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import cn.com.bjnews.thinker.R;
import cn.com.bjnews.thinker.actlogical.FragmentFirstLogical;
import cn.com.bjnews.thinker.actlogical.IFragmentFirst;
import cn.com.bjnews.thinker.adapter.FragmentFirstAdapter;
import cn.com.bjnews.thinker.db.DbHandler;
import cn.com.bjnews.thinker.db.IDbCallBack;
import cn.com.bjnews.thinker.debug.MyDebug;
import cn.com.bjnews.thinker.entity.AdIntroEntity;
import cn.com.bjnews.thinker.entity.NewsEntity;
import cn.com.bjnews.thinker.entity.NewsListEntity;
import cn.com.bjnews.thinker.entity.RequestEntity;
import cn.com.bjnews.thinker.entity.ResponseResult;
import cn.com.bjnews.thinker.internet.IRequestCallBack;
import cn.com.bjnews.thinker.internet.InternetHelper;
import cn.com.bjnews.thinker.utils.Mconstant;
import cn.com.bjnews.thinker.utils.Utils;
import cn.com.bjnews.thinker.view.IRefreshListner;
import cn.com.bjnews.thinker.view.MListView;

/**
 * 
 * @author sunqm Create at: 2014-5-13 上午7:27:56 TODO 要闻
 */
public class Fragment_First extends Fragment implements IRequestCallBack,
		OnItemClickListener,  OnClickListener,
		IDbCallBack, IRefreshListner {

	/** 刷新控件 */
	private LinearLayout loadingBars;
	private ProgressBar loadingBarLeft;
	private ProgressBar loadingBarRight;
	private ProgressBar loadingBarIndeterminate;

	private int currentPagerIndex = 0;

	private MListView listview = null;

	private FragmentFirstAdapter adapter;

	/**广告位中的新闻*/
	private NewsEntity headEntity = null;

	private NewsListEntity locaListEntity = null;

	private DbHandler dbHandler;

	/** 是否开始刷新 */
	public static boolean isRefreshStarted = false;

	public boolean isRefreshingTest = false;

	/** 请求被意外打断 */
	public static boolean requestInterupted = false;

	private static final int VIEWPAGER = 1;
	
	private MyDebug debug;

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
		debug.setCurrentTime();
		Log.i("tag","skip--oncreateView");
		currentPagerIndex = getArguments().getInt("key");
		Log.d("tag", pageIndex + "new==pageIndex->"
				+ Mconstant.currentPageIndex);
		View view = View.inflate(getActivity(), R.layout.firstfragment, null);
		TestThread(view);
		debug.getTime(0, currentPagerIndex);
		Log.i("tag","skip--oncreateView-end");
		return view;
	}
	
	private void TestThread(final View view){
		
		new Thread(){

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				msg.obj = view;
				handler.sendMessage(msg);
			}
			
		}.start();
	}
	
	Handler handler =new Handler(){

		@Override
		public void handleMessage(Message msg) {
			View view = (View) msg.obj;
			initView(view);
			initData();
			if (MainActivity.isRefreshing(currentPagerIndex)) {
				Refresh();
			} else if (!MainActivity.isRefreshing(Mconstant.currentPageIndex) ) {// 当前显示的页，没有进行加载，需要不显示title
				postRefresh();
			} 
		}
		
	};

	@SuppressLint("ServiceCast")
	private void initView(View view) {
		listview = (MListView) view.findViewById(R.id.fragment_first_lv);

		initListView(view);

		listview.initHeadView(R.layout.fragment_first_lv_header, this);
		adapter = new FragmentFirstAdapter(getActivity());
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);

		dbHandler = DbHandler.getInstance(getActivity());// new
															// DbHandler(getActivity());
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
		if (MainActivity.isRefreshing(Mconstant.currentPageIndex)) {
			Refresh();
		} else {
		}
		Log.i("tag","skip--pageSelected");
	}

	/**
	 * 读取本地数据
	 */
	private void readLocalData(int channelId) {
//		debug.getTime(1, currentPagerIndex);
		dbHandler.readNewsList(this, channelId);
//		debug.getTime(2, currentPagerIndex);
	}


	private void showstopLoading() {
		loadingBars.setVisibility(View.INVISIBLE);
		loadingBarIndeterminate.setVisibility(View.GONE);
		loadingBarIndeterminate.setIndeterminate(false);
		if(getActivity()!=null)
			((MainActivity) getActivity()).hideLoading();// (R.string.loading)
		isRefreshStarted = false;
		requestInterupted = false;
		isRefreshingTest = false;
		
	}

	private void showLoading() {
		
		((MainActivity) getActivity()).showLoading(R.string.loading);
		loadingBars.setVisibility(View.INVISIBLE);
		loadingBarIndeterminate.setVisibility(View.VISIBLE);
		loadingBarIndeterminate.setIndeterminate(true);
		isRefreshStarted = true;
		isRefreshingTest = true;
//		Toast.makeText(getActivity(), "loading---", Toast.LENGTH_SHORT).show();
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
		for(int i=0;i<adapter.getData().size();i++){
			if(newsId==adapter.getData().get(i).id){//获得该
				entity = adapter.getData().get(i);
			}
		}//获取到
		skipToDetail(entity);
		if(newsId>-1){//请求该id 数据,请求后跳转
//			Toast.makeText(getActivity(), "跳转"+entity, Toast.LENGTH_SHORT).show();
			//该推送已经处理
			if(entity!=null)
				((MainActivity) getActivity()).setArticalId(-2);
		}
	}
	
	/**
	 * 跳转到 详细页面
	 * @param newsId
	 * @param entity
	 */

	private void skipToDetail(NewsEntity entity){
		if(entity==null){
			return;
		}
		Intent i = new Intent(getActivity(), NewsDetailAct.class);
		i.putExtra("news",entity);
		startActivityForResult(i, 0);
		Log.d("tag","skip-->");
		((MainActivity) getActivity()).getService().update(entity.id, 1);
	}

	private boolean test = true;
	
	@Override
	public void request(int timeOut) {
		RequestEntity requestEntity = new RequestEntity(
				MainActivity.localSettingEntity.channelList
						.get(currentPagerIndex).url);
		if(((MainActivity)getActivity()).getService()!=null&&test){
			debug.getTime(5, currentPagerIndex);
			((MainActivity)getActivity()).getService().request(getActivity(),requestEntity,this,timeOut);
			test = false;
		}else{
			debug.getTime(10, currentPagerIndex);
			new InternetHelper(getActivity()).requestThread(requestEntity, this,timeOut);
			test = true;
		}
		
		// requestEntity.setUrl("http://app.bjnews.com.cn/m/json/worldcup_test.html");
		Log.d("tag", "readlocal--request==start" + currentPagerIndex);
		//标记 正在请求网络
		MainActivity.setState(currentPagerIndex, 1);
	}

	@Override
	public void requestSuccess(ResponseResult responseResult) {
		debug.getTime(15, currentPagerIndex);
		
		if (getActivity() != null
				&& ((MainActivity) getActivity()).getService() != null) {

			((MainActivity) getActivity()).getService().showData(
					locaListEntity, responseResult.resultStr,
					new IFragmentFirst() {

						@SuppressWarnings("unchecked")
						@Override
						public void showData(Object... params) {
							adapter.setData((List<NewsEntity>) params[0]);
							listview.setHeadData((List<AdIntroEntity>) params[1],
									Fragment_First.this);
							headEntity = (NewsEntity) params[2];
							locaListEntity = (NewsListEntity) params[3];
							postRefresh();
							
						}

						@Override
						public void noUpdate() {
							// TODO Auto-generated method stub
							postRefresh();
						}

					});
		}else{
			new FragmentFirstLogical(getActivity()).doData(locaListEntity, responseResult.resultStr, new IFragmentFirst() {
				
			

				@SuppressWarnings("unchecked")
				@Override
				public void showData(Object... params) {
					adapter.setData((List<NewsEntity>) params[0]);
					listview.setHeadData((List<AdIntroEntity>) params[1],
							Fragment_First.this);
					headEntity = (NewsEntity) params[2];
					locaListEntity = (NewsListEntity) params[3];
					postRefresh();
				}

				@Override
				public void noUpdate() {
					// TODO Auto-generated method stub
					postRefresh();
					
				}
			});
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
//			showstopLoading();
			postRefresh();
		}
		

	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.d("tag","onResume--fragment-->"+((MainActivity) getActivity()).getArticalId());
		if(getActivity()!=null){
			if(((MainActivity) getActivity()).getArticalId()>-1){//请求该id 数据,请求后跳转
				Refresh();
				request(Mconstant.TIME_OUT);
//				Toast.makeText(getActivity(), "开始请求数据", Toast.LENGTH_SHORT).show();
			}
		}
		debug.getTime(1, currentPagerIndex);
		super.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(adapter!=null)
			adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		skipToDetail(adapter.getEntity(arg3));
		adapter.getData().get((int) arg3).state = 1;
		locaListEntity.getNewsList().get((int) arg3+1).state = 1;
		
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
			if (listview.getCurrentAddIndex()
					 == 0) {// 详细页面
			// Log.d("tag","News==size"+locaListEntity.getNewsList().size());
				Intent i = new Intent(getActivity(), NewsDetailAct.class);
				i.putExtra("news", headEntity);
				startActivityForResult(i, 0);

			} else {// 跳转至web页面
				Intent i = new Intent((MainActivity) getActivity(),
						ActWeb.class);
				i.putExtra(
						"url",
						locaListEntity.getAds().get(listview.getCurrentAddIndex() - 1).url);
				startActivity(i);
			}
			// Toast.makeText(getActivity(), "viewpager",
			// Toast.LENGTH_SHORT).show();
			break;
		case VIEWPAGER:

			break;

		}
	}


	/**
	 * 读取本地数据
	 */
	@Override
	public void ReadSuccess(NewsListEntity entity) {
//		debug.getTime(3, currentPagerIndex);
		Log.i("tag","skip--readsuccess");
		if (MainActivity.localSettingEntity.channelList.get(currentPagerIndex).id != entity.channelId)
			return;
		locaListEntity = entity;
//		locaListEntity = copy(entity);
		if(entity.pubDate!=null)
		Log.d("tag", "readlocal-success>" + Utils.isOld(entity.pubDate) + "<>"
				+ entity.getNewsList().size());
		
		new FragmentFirstLogical(getActivity()).doUpdate(entity, new IFragmentFirst() {
			

			@SuppressWarnings("unchecked")
			@Override
					public void showData(Object... params) {
						adapter.setData((List<NewsEntity>) params[0]);
						listview.setHeadData((List<AdIntroEntity>) params[1],
								Fragment_First.this);
						headEntity = (NewsEntity) params[2];
						locaListEntity = (NewsListEntity) params[3];
//						debug.getTime(4, currentPagerIndex);
			}

			@Override
			public void noUpdate() {
				// TODO Auto-generated method stub
				
			}
		});
//		showDataTest(entity);
		Log.d("tag","readsuccess--->"+entity.newsList.size() +"》《"+entity.requestState);
		if (entity.newsList.size() == 0||entity.requestState==0)//(locaListEntity.pubDate != null && Utils.isOld(locaListEntity.pubDate)
				  {//老数据。或者没有数据
			Refresh();
		}else{
		}
	}

	@Override
	public void Refresh() {
		Log.d("tag", "readlocal---showLoading---refresh>" + getActivity());
		RefreshTest();
	}
	
	/**
	 * 开始刷新，
	 */
	private void RefreshTest(){
		if(getActivity()!=null){
			//当前正在请求网络。。 需要显示 加载中。。(如果没有显示的话)
			if(MainActivity.isRefreshing(currentPagerIndex)){//正在请求
				if(currentPagerIndex==Mconstant.currentPageIndex)
					showLoading();
			}else{//当前没有请求网络。。
				request(Mconstant.TIME_OUT);
				if(currentPagerIndex==Mconstant.currentPageIndex)
					showLoading();
			}
		}
	}

	/**
	 * 刷新结束
	 */
	private void postRefreshTest(){
		showstopLoading();
		MainActivity.setState(currentPagerIndex, 0);
	}
	
	@Override
	public void postRefresh() {//刷新结束
		postRefreshTest();
		//请求结束。。处理推送内容
		doPush();
	}

	
	
	@Override
	public void preRefresh() {
		 Log.d("tag","showLoading---pre-pull>");
		((MainActivity) getActivity()).showLoading(R.string.pull_refresh);
		MainActivity.setState(currentPagerIndex, 0);
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


}
