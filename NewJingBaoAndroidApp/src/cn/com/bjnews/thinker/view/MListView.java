package cn.com.bjnews.thinker.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import cn.com.bjnews.thinker.act.Fragment_First;
import cn.com.bjnews.thinker.utils.Mconstant;


public class MListView extends ListView{

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

	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		Log.d("tag","abcListView--onTouch");
		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN: {
			isScrollFromTop = true;
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
//			Log.d("tag","yyy-->"+event.getY());
			gestureDetector.onTouchEvent(event);
		}

		}
		Log.d("tag","abc---listview-touch"+Mconstant.listViewIntercept+"<viewpager>"+Mconstant.viewPagerIntercept);
		return super.onTouchEvent(event);
	}
	
	@SuppressLint("NewApi")
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {


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

		private static final int SWIPE_MIN_DISTANCE = 60;
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
				return true;
			}  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE ) {
				//From Left to Right
//				Log.d("tag","Gesture-->向右");
				Mconstant.viewPagerIntercept = true;
				return true;
			}

			if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE ) {
				//From Bottom to Top
				Mconstant.viewPagerIntercept = false;
//				Log.d("tag","Gesture-->向上");
				return false;
			}  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE ) {
				//From Top to Bottom
//				Log.d("tag","Gesture-->向下");
				Mconstant.viewPagerIntercept = false;
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
	};
	
	

