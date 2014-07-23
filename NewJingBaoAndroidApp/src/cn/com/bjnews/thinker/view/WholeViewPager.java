package cn.com.bjnews.thinker.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import cn.com.bjnews.thinker.utils.Mconstant;
/**
 * 主页面最大 的 滑动控件
 * @param context
 * @param attrs
 */
public class WholeViewPager extends ViewPager{

	public WholeViewPager(Context context) {
		super(context);
	}

	
	public WholeViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		switch(arg0.getAction()){
		case MotionEvent.ACTION_DOWN:
			Mconstant.viewPagerIntercept = false;
			Mconstant.listViewIntercept = false;
			break;
		case MotionEvent.ACTION_MOVE:
			return Mconstant.viewPagerIntercept;
		case MotionEvent.ACTION_UP:
			Mconstant.viewPagerIntercept = false;
			Mconstant.listViewIntercept = false;
			break;
		}
//		if(MotionEvent.ACTION_MOVE ==arg0.getAction())
//			return Mconstant.viewPagerIntercept;
//		if(MotionEvent.ACTION_UP == arg0.getAction())
//			
		Log.d("tag","abcviewpager->intercept->"+super.onInterceptTouchEvent(arg0));
		return super.onInterceptTouchEvent(arg0);
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		Log.d("tag","abcviewpager->TouchE->"+super.onTouchEvent(arg0)+arg0.getAction());
		if(MotionEvent.ACTION_UP == arg0.getAction()){
			Mconstant.viewPagerIntercept = false;
		}
		return super.onTouchEvent(arg0);
	}

	

}
