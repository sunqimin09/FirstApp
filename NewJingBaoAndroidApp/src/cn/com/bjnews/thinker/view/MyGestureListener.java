package cn.com.bjnews.thinker.view;

import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class MyGestureListener extends SimpleOnGestureListener{

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if(e1==null){
			return true;
		}
		if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE &&
				Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			//From Right to Left
			
			left();
			return true;
		}  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE &&
				Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			//From Left to Right
			
			right();
			return true;
		}

		if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE &&
				Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
			//From Bottom to Top
			top();
			return true;
		}  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE &&
				Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
			//From Top to Bottom
			bottom();
			return true;
		}
		return false;
//	}
		
//		return super.onFling(e1, e2, velocityX, velocityY);
	}

	/**
	 * 向左滑动
	 */
	protected void left() {
		Log.d("tag","Gesture-->向左");
		
	}
	/**
	 * 向右滑动
	 */
	protected void right() {
		Log.d("tag","Gesture-->向右");
	}
	
	/**
	 * 向上 滑动
	 */
	protected void top() {
		Log.d("tag","Gesture-->向上");
	}
	
	/**
	 * 向下滑动
	 */
	protected void bottom() {
		Log.d("tag","Gesture-->向下");
	}
	
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return super.onScroll(e1, e2, distanceX, distanceY);
	}
	
	
	
}
