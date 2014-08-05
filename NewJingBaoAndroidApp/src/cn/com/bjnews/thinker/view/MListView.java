package cn.com.bjnews.thinker.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.com.bjnews.thinker.R;
import cn.com.bjnews.thinker.act.Fragment_First;
import cn.com.bjnews.thinker.adapter.FragmentFirstViewPagerAdapter;
import cn.com.bjnews.thinker.entity.AdIntroEntity;
import cn.com.bjnews.thinker.img.CommonUtil;
import cn.com.bjnews.thinker.img.ImageLoader;
import cn.com.bjnews.thinker.utils.Mconstant;
import cn.com.bjnews.thinker.utils.Utils;


public class MListView extends ListView  implements OnPageChangeListener{

	private final int PROGRESS_THRESHOLD = 200;
	
	private Context context;
	
	private GestureDetector gestureDetector;
	
	private boolean isProgessStarted;
	
	private boolean isPulling = false;
	
	
	private boolean isScrollFromTop;
	
	private int value;
	
	private LinearLayout loadingBars;
	
	private ProgressBar loadingBarRight;
	
	private ProgressBar loadingBarLeft;
	
	private ProgressBar loadingBarIndeterminate;
	
	private IRefreshListner refreshListener;
	
	public void setLoading(LinearLayout loadingBars,
			ProgressBar loadingBarLeft, ProgressBar loadingBarRight,
			ProgressBar loadingBarIndeterminate,IRefreshListner listener ) {
		this.loadingBars = loadingBars;
		this.loadingBarLeft = loadingBarLeft;
		this.loadingBarRight = loadingBarRight;
		this.loadingBarIndeterminate = loadingBarIndeterminate;
		this.refreshListener = listener;
	}

	
	public MListView(Context context) {
		super(context);
		this.context = context;
		gestureDetector = new GestureDetector(this.context, new GestureListener());
	}
	
	public MListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		gestureDetector = new GestureDetector(this.context, new GestureListener());
	}

	public MListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		gestureDetector = new GestureDetector(this.context, new GestureListener());
		
	}
	
	
	private ArrayList<View> adViews = new ArrayList<View>();
	
	private View HeaderView;
	
	private LinearLayout llHeaderIcon;
	
	/** 控制标题背景色 */
	private LinearLayout llCaptionBg;
	
	private TextView tvCaption;
	
	private MViewPager viewPager;
	
	private ImageLoader imgLoader;
	
	private FragmentFirstViewPagerAdapter viewpagerAdapter;
	
	private List<AdIntroEntity> ads = new ArrayList<AdIntroEntity>();
	
	private static final int VIEWPAGER = 1;
	
	public int currentAddPageIndex = 0;
	
	public void initHeadView(int resId,OnClickListener clickListener){
		HeaderView = (View) ((LayoutInflater) context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE)).inflate(
						resId, null);
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
		viewpagerAdapter = new FragmentFirstViewPagerAdapter(context,
				adViews);

		viewPager.setAdapter(viewpagerAdapter);
		viewPager.setOnPageChangeListener(this);
		viewPager.setOnClickListener(clickListener);
		this.addHeaderView(HeaderView);
		HeaderView.setVisibility(View.GONE);
		imgLoader = new ImageLoader(context, R.drawable.default_img,
				false);
		resizeViewpager();
	}
	
	private void resizeViewpager() {
		ViewGroup.LayoutParams params = viewPager.getLayoutParams();
		params.width = CommonUtil.getScreenWidth(context);
		params.height = (int) CommonUtil.getScreenWidth(context) * 2 / 3;
		viewPager.setLayoutParams(params);

	}
	
	/**
	 * 设置广告数据
	 * @param ads
	 */
	public void setHeadData(List<AdIntroEntity> ads,OnClickListener clickListener){
		if(ads.size()==0){
			HeaderView.setVisibility(View.GONE);
			return ;
		}
		this.ads = ads;
		HeaderView.setVisibility(View.VISIBLE);
		
		adViews.clear();
		llHeaderIcon.removeAllViews();
		
		tvCaption.setText(ads.get(0).caption);
		ImageView img;
		ImageView ImgIcon;
		imgLoader.setImgWidth(CommonUtil.getScreenWidth(context));
		imgLoader.setIsBg(false);
		for(int i=0;i<ads.size();i++){//
			img = new ImageView(context);
			img.setId(VIEWPAGER);
			img.setOnClickListener(clickListener);
					
			imgLoader.DisplayImage(ads.get(i).picUrl, img, false);
			adViews.add(img);
			// 添加icon
			ImgIcon = new ImageView(context);
			ImgIcon.setPadding(8, 9, 8, 10);
			ImgIcon.setImageResource(R.drawable.scroll_selected); 
			llHeaderIcon.addView(ImgIcon,i);
			
		}
		
		Utils.setViewPagerIcon(llHeaderIcon, 0);
		if (ads.size() < 2) {// 只有一条数据
			llHeaderIcon.setVisibility(View.GONE);
		} else {
			llHeaderIcon.setVisibility(View.VISIBLE);
		}
		
		
		viewpagerAdapter = new FragmentFirstViewPagerAdapter(context,
				adViews);

		viewPager.setAdapter(viewpagerAdapter);
	}
	
	/**
	 * 当前显示的Ads
	 * @return
	 */
	public List<AdIntroEntity> getAds(){
		return ads;
	}
	
	/**
	 * @return
	 */
	public int getCurrentAddIndex(){
		return currentAddPageIndex;
	}
	
	

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		Log.d("tag","abcListView--onTouch");
		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN: {
			isScrollFromTop = true;
			Log.d("tag","listview--IN"+Mconstant.listViewIntercept+"viewpager-->"+Mconstant.viewPagerIntercept);
		}

		case MotionEvent.ACTION_UP: {
			Mconstant.listViewIntercept = false;
			Mconstant.viewPagerIntercept = false;
			Fragment_First.requestInterupted = false;
			isScrollFromTop = true;
			isPulling = false;
			showLogo();
			new AnimateBackProgressBars().execute(Math.abs(loadingBarLeft.getProgress()));

		}
		
		case MotionEvent.ACTION_MOVE: {
			isProgessStarted = true;
			Log.d("tag","listview-move-->"+event.getY());
			gestureDetector.onTouchEvent(event);
		}

		}
		Log.d("tag","abc---listview-touch"+Mconstant.listViewIntercept+"<viewpager>"+super.onTouchEvent(event));
		return super.onTouchEvent(event);
	}
	
	@SuppressLint("NewApi")
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

		Log.d("tag","overscroillby-->"+isProgessStarted+(!Fragment_First.requestInterupted));
		if(getFirstVisiblePosition() == 0 && isProgessStarted&& !Fragment_First.requestInterupted){
			
			value  += deltaY;
			Log.d("tag","deltaY-->"+value);
			if(deltaY < 0 && Math.abs(value)>30){
				Log.d("tag","deltaY--1>"+Fragment_First.isRefreshStarted);
				if(! Fragment_First.isRefreshStarted){
//					//更新页面 刷新标题，提示下拉即可刷新
//					handler.sendEmptyMessage(1);
					loadingBars.setVisibility(View.VISIBLE);
					loadingBarIndeterminate.setVisibility(View.GONE);
					if(refreshListener != null && !isPulling){
						refreshListener.preRefresh();
						isPulling = true;
					}
					loadingBarLeft.setProgress(Math.abs(Math.abs(value)-30));
					loadingBarRight.setProgress(Math.abs(Math.abs(value)-30));
//
					if (Math.abs(Math.abs(value)-30) > PROGRESS_THRESHOLD) {
						if(! Fragment_First.isRefreshStarted){
							if(refreshListener != null){
								Log.d("tag","refresh-->"+Fragment_First.isRefreshStarted);
								refreshListener.Refresh();
							}
							
							loadingBars.setVisibility(View.INVISIBLE);
							loadingBarIndeterminate.setVisibility(View.VISIBLE);
							loadingBarIndeterminate.setIndeterminate(true);
							Fragment_First.isRefreshStarted = true;
							//已经开始刷新，初始化 刷新指标
							value = 0;
//							MainActivity.setState(Mconstant.currentPageIndex, 1);
						}
					}
				}
			}
		}

		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
				scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
	}
	
	
	private class AnimateBackProgressBars extends AsyncTask<Integer, Integer, ProgressBar> {
		protected ProgressBar doInBackground(Integer...paths) {

			while (loadingBarRight.getProgress() > 0) {

				int val = loadingBarRight.getProgress();
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
				}
				loadingBarRight.setProgress(--val);
				loadingBarLeft.setProgress(--val);
			}
			handler.sendEmptyMessage(1);
			return null;
		}

		protected void onPostExecute(ProgressBar result) {
			loadingBars.setVisibility(View.INVISIBLE);
			value = 0;
		}

	}
	
	private class GestureListener extends SimpleOnGestureListener{

		private static final int SWIPE_MIN_DISTANCE = 80;
		private static final int SWIPE_THRESHOLD_VELOCITY = 60;
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
//			Log.d("tag","Gesture scroll");
			if(e1== null){
				Mconstant.viewPagerIntercept = true;
				return true;
			}
			if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					) {
				//From Right to Left
//				Log.d("tag","Gesture-->向左");
				Mconstant.viewPagerIntercept = true;
				Mconstant.listViewIntercept = false;
				return true;
			}  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE ) {
				//From Left to Right
//				Log.d("tag","Gesture-->向右");
				Mconstant.viewPagerIntercept = true;
				Mconstant.listViewIntercept = false;
				return true;
			}

			if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE ) {
				//From Bottom to Top
				Mconstant.viewPagerIntercept = false;
				Mconstant.listViewIntercept = true;
//				Log.d("tag","Gesture-->向上");
				return false;
			}  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE ) {
				//From Top to Bottom
//				Log.d("tag","Gesture-->向下");
				Mconstant.viewPagerIntercept = false;
				Mconstant.listViewIntercept = true;
				return false;
			}
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
		
		
		

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2,float velocityX, float velocityY) {
			isProgessStarted = false;
//			Log.d("tag","e1->"+e1+"e2"+e2);
			if(e1==null){
				return true;
			}
			if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE &&
					Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				//From Right to Left
//				Log.d("tag","Gesture-->向左");
				return true;
			}  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE &&
					Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				//From Left to Right
//				Log.d("tag","Gesture-->向右");
				return true;
			}

			if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE &&
					Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
				//From Bottom to Top
//				Log.d("tag","Gesture-->向上");
				loadingBars.setVisibility(View.INVISIBLE);
				return true;
			}  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE &&
					Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
				//From Top to Bottom
//				Log.d("tag","Gesture-->向下");
				loadingBars.setVisibility(View.INVISIBLE);
				return true;
			}
			return false;
		}

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		Log.d("tag",Mconstant.listViewIntercept+"abcListView--onIntercept"+super.onInterceptTouchEvent(arg0)+arg0.getAction());
		if(MotionEvent.ACTION_MOVE ==arg0.getAction())
			return Mconstant.listViewIntercept;
		return super.onInterceptTouchEvent(arg0);
	}

	


	public void showLogo(){
		
	}
	
	public void hideLogo(){
		
	}
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {

			if(msg.what == 1){
				Log.d("tag","handler--->"+refreshListener);
				if(refreshListener!=null&&!Fragment_First.isRefreshStarted)
					refreshListener.interruptPreRefresh();
				}	
			}
		};

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageSelected(int arg0) {
		currentAddPageIndex = arg0;
		Utils.setViewPagerIcon(llHeaderIcon, arg0);
		if (ads.size() > arg0 && ads.get(arg0).picUrl != null) {
			// Log.d("tag",arg0+"caption--->"+adsNew.get(arg0).caption);
			tvCaption.setText(ads.get(arg0).caption);
			if (ads.get(arg0).caption == null
					|| ads.get(arg0).caption.equals("")) {
				llCaptionBg
						.setBackgroundResource(R.drawable.listview_cachecolor);
			} else {
				llCaptionBg
						.setBackgroundResource(R.drawable.background_caption);
			}
			// imgLoader.DisplayImage(adsNew.get(arg0).picUrl,(ImageView)viewPager.getChildAt(arg0),false);
		}
		
	}
	};
	
	

