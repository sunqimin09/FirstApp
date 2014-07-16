package cn.com.bjnews.thinker.view;

import cn.com.bjnews.thinker.utils.Mconstant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("NewApi")
public class MViewPager extends ViewPager{

	GestureDetector gestureDetector;
	
	public MViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		gestureDetector = new GestureDetector(context, new GestureListener());
	}

	
	public MViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		gestureDetector = new GestureDetector(context, new GestureListener());
		
	}


	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		Log.d("tag","viewpager->"+this.getClass()+"dispach->"+super.dispatchKeyEvent(event));
		return super.dispatchKeyEvent(event);
	}

	
	

	@SuppressLint("NewApi")
	@Override
	public boolean callOnClick() {
		Log.d("tag","abc--callonlicck");
		return super.callOnClick();
	}


	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		Log.d("tag","abc viewpager->Intercep->"+super.onInterceptTouchEvent(arg0));
		
//		if(MotionEvent.ACTION_DOWN ==arg0.getAction()||
//				MotionEvent.ACTION_UP == arg0.getAction()
//				)
//			return false;
//		if(MotionEvent.ACTION_MOVE == arg0.getAction()){
			return true;
//		}
//		return super.onInterceptTouchEvent(arg0);
	}

	private boolean callClick = false;

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		Log.d("tag","abcviewpager->TouchE->"+super.onTouchEvent(arg0)+arg0.getAction());
		Log.d("tag","bcdviewpager-->"+gestureDetector.onTouchEvent(arg0));
		
		if(MotionEvent.ACTION_DOWN ==arg0.getAction()){
			callClick = true;
			return true;
		}
			
		if(MotionEvent.ACTION_UP == arg0.getAction()){
			Mconstant.viewPagerIntercept = false;
			Mconstant.listViewIntercept = false;
			if(callClick)
				callOnClick();
			return true;
		}
		
		
		return gestureDetector.onTouchEvent(arg0);
//		return super.onTouchEvent(arg0);
	}

	/**
	 * 返回结果
	 * */
	private boolean flag = true;
	
	private class GestureListener extends SimpleOnGestureListener{

		private static final int SWIPE_MIN_DISTANCE = 20;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;
		
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			callClick = false;
			Log.d("tag","Gesture scroll");
			if(e1== null){
				
				Mconstant.listViewIntercept = false;
				return true;
			}
			Log.d("tag","OnScrollXX>>"+e1.getX()+"e2"+e2.getX());
			Log.d("tag","OnScrollYY>>"+e2.getY()+"e2"+e2.getY());
			Log.d("tag","OnScrolldistance-->"+distanceX+"distanceY"+distanceY);
			if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					) {
				//From Right to Left
				flag = true;
				
//				if(Mconstant.canPullEnd){
//					Mconstant.listViewIntercept = true;
//					return false;
//				}else{
					Mconstant.listViewIntercept = false;
//				}
					Log.d("tag","Gesture-->向左"+Mconstant.viewPagerIntercept);
				return true;
			}  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE ) {
				//From Left to Right
				flag = true;
				Log.d("tag","Gesture-->向右");
//				if(Mconstant.canPullEnd){
//					Mconstant.listViewIntercept = true;
//					return false;
//				}else{
					Mconstant.listViewIntercept = false;
//				}
					Log.d("tag","Gesture-->向右"+Mconstant.viewPagerIntercept);
				return true;
			}else{
				Log.d("tag","Gesture-->未知"+Mconstant.listViewIntercept+Mconstant.viewPagerIntercept+(e2.getX() - e1.getX()));
			}

			if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE+20 ) {
				//From Bottom to Top
				flag = false;
				Mconstant.listViewIntercept = true;
				Log.d("tag","Gesture-->向上");
				return false;
			}  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE+20 ) {
				//From Top to Bottom
				flag = false;
				Log.d("tag","Gesture-->向下");
				Mconstant.listViewIntercept = true;
				return false;
			}
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
		

	}
	

}
