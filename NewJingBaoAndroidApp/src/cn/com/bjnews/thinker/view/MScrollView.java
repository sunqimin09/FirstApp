package cn.com.bjnews.thinker.view;

import cn.com.bjnews.thinker.utils.Mconstant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class MScrollView extends HorizontalScrollView{

	private MSCrollViewListener listener;
	
	public MScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void fling(int velocityX) {
		// TODO Auto-generated method stub
		super.fling(velocityX);
		Log.d("tag","fling-->"+velocityX);
	}
	
	public void setListener(MSCrollViewListener listener){
		this.listener = listener;
	}
	
	
	
	@SuppressLint("NewApi")
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
//		Log.d("tag","overScrollby"+deltaX+"deltay"+deltaY+"scrollx:"+scrollX
//				+"crollY"+scrollY+"scrollRangX"+scrollRangeX+"scrollRangY"+scrollRangeY
//				+"maxOverScrollX"+maxOverScrollX+"maxOverscrollY"+maxOverScrollY+"Istouch"
//				+isTouchEvent);
		if(listener!=null){
        	listener.overScrollBy(deltaX, scrollX, isTouchEvent);
        }
		// TODO Auto-generated method stub
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
				scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
	}
	
	

	@SuppressLint("NewApi")
	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
			boolean clampedY) {
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
	}

	@Override  
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {  
        super.onScrollChanged(x, y, oldx, oldy); 
        if(listener!=null){
        	listener.onScrollChanged(x, y, oldx, oldy);
        }
        Log.d("tag","changed--x》"+x+"y:"+y+"oldx:"+oldx+"oldy:"+oldy);
    }

	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		Log.d("tag","abc--scroll--intercept->"+super.onInterceptTouchEvent(arg0));
		if(MotionEvent.ACTION_MOVE ==arg0.getAction())
			return Mconstant.listViewIntercept;
		if(MotionEvent.ACTION_UP == arg0.getAction()){
			Mconstant.listViewIntercept = false;
		}
		return super.onInterceptTouchEvent(arg0);
	}

	

	
}
