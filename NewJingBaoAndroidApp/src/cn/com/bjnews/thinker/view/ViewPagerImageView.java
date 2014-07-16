package cn.com.bjnews.thinker.view;

import cn.com.bjnews.thinker.utils.Mconstant;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ViewPagerImageView extends ImageView{
	
	public ViewPagerImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


	public ViewPagerImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
//		if(MotionEvent.ACTION_DOWN ==arg0.getAction()||
//				MotionEvent.ACTION_UP == arg0.getAction()
//				){
//			Log.d("tag","abcviewpager-img>Touch->"+true);
//			return true;
//		}
		
		Log.d("tag","abcviewpager-img>Touch-00>"+arg0.getAction());
		if(MotionEvent.ACTION_DOWN ==arg0.getAction()){
			return super.onTouchEvent(arg0);
		}
		if(MotionEvent.ACTION_MOVE ==arg0.getAction()){
			Mconstant.listViewIntercept = true;
			return false;
		}
		
		Log.d("tag","abcviewpager-img>Touch->"+arg0.getAction());
		
		return  super.onTouchEvent(arg0);
		
	}
	
	
}
