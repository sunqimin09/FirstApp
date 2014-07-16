package cn.com.bjnews.thinker.view;

import cn.com.bjnews.thinker.utils.Mconstant;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class DetailLinnerlayout extends LinearLayout{

	public DetailLinnerlayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
//		Log.d("tag","abc--linn--intercept->"+Mconstant.listViewIntercept);
		if(MotionEvent.ACTION_MOVE ==arg0.getAction())
			return Mconstant.listViewIntercept;
		if(MotionEvent.ACTION_UP == arg0.getAction()){
//			Mconstant.listViewIntercept = false;
		}
		return super.onInterceptTouchEvent(arg0);
	}


}
