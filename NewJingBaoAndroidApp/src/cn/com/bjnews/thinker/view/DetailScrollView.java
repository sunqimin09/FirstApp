package cn.com.bjnews.thinker.view;

import cn.com.bjnews.thinker.utils.Mconstant;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 详细页面--滚动
 * @author sunqm
 * Create at:   2014-5-31 下午11:27:00 
 * TODO
 */
public class DetailScrollView extends ScrollView {

	public DetailScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if(MotionEvent.ACTION_MOVE ==arg0.getAction())
			return Mconstant.listViewIntercept;
		if(MotionEvent.ACTION_UP == arg0.getAction()){
//			Mconstant.listViewIntercept = false;
		}
//		Log.d("tag","onintercept--s>"+super.onInterceptTouchEvent(arg0));
		return super.onInterceptTouchEvent(arg0);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if(Math.abs(oldt-t)<120){//左右滑动
			Mconstant.listViewIntercept = false;
		}else{//上下滑动
			Mconstant.listViewIntercept = true;
		}
//		Log.d("tag","abc--onScrollChangeed--"+l+"t:>"+t);
		super.onScrollChanged(l, t, oldl, oldt);
	}
	
	
	
}
