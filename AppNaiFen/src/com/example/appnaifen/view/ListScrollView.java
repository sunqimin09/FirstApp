package com.example.appnaifen.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.example.appnaifen.minterface.OnScrollListener;

/**
 * 瀑布流加载数据
 * @author sunqm
 * 1.监听 滑到顶部，以及底部
 *
 */
public class ListScrollView extends ScrollView implements OnScrollListener{

	public ListScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	@Override
	public void onBottom() {
		
	}

	@Override
	public void onTop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll() {
		// TODO Auto-generated method stub
		
	}
	
	
	private void init(){
		
		//加载一页数据
		loadMoreData();
		
	}
	
	
	
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		gestureDetector.onTouchEvent(ev);
		return super.onTouchEvent(ev);
	}

	@Override
	public boolean pageScroll(int direction) {
		// TODO Auto-generated method stub
		return super.pageScroll(direction);
	}

	/**
	 * 主动请求
	 */
	private void loadMoreData(){
		
	}
	
	
	
	GestureDetector gestureDetector = new GestureDetector(this.getContext(), new GestureListener());
	
	
	private class GestureListener extends SimpleOnGestureListener{

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			Log.d("tag","onFling==="+velocityX+"velocityY"+velocityY);
			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			Log.d("tag","onScroll===top:"+ListScrollView.this.getTop()+"Bottom"+ListScrollView.this.getBottom());
			
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
		
		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			super.onLongPress(e);
		}
		

	}
	
}
