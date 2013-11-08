package com.example.util;

import com.example.apptellout.R;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class PopWindowHelper {
	
	private Context context =null;
	
	private PopupWindow pop = null;
	
	public PopWindowHelper(Context context){
		this.context = context;
		
	}
	
	public void closeWindow(){
		pop.dismiss();
	}
	
//	/**
//	 * 显示关于框
//	 * @param parentView
//	 */
	public void showAbout(){
		View view = View.inflate(context, R.layout.about, null);
		showWindow(view);
	}
//	
	public void showMyRank(){
		View view = View.inflate(context, R.layout.my_infor_rank_act, null);
		showWindow(view);
	}
//	
//	public void showFeedBack(View view){
////		View view = View.inflate(context, R.layout.suggestion_back, null);
//		showWindow(view);
//	}
	
	public void showWindow(View view){
		pop = new PopupWindow(view,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		pop.setContentView(view);
		pop.setFocusable(true);
//		pop.setOutsideTouchable(true); 
		pop.setBackgroundDrawable(new PaintDrawable());
		View parentView1 = View.inflate(context, R.layout.tellout_act, null);
//		pop.showAsDropDown(parentView1,100,100);
		pop.showAtLocation(parentView1, Gravity.CENTER, 0, 0);
		
	}
	
	
}
