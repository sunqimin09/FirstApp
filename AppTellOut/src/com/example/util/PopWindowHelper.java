package com.example.util;

import com.example.apptellout.R;

import android.content.Context;
import android.view.View;
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
	
	public void showAbout(View parentView){
		View view = View.inflate(context, R.layout.about, null);
		showWindow(parentView, view);
	}
	
	private void showWindow(View parentView,View view){
		pop = new PopupWindow(view,200,200);
		pop.setContentView(view);
		pop.showAsDropDown(parentView);
		
	}
	
	
}
