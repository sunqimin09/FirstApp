package com.example.appnaifen.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.appnaifen.entity.TestEntity;

public class ViewLinearLayout extends LinearLayout{

	private List<TestEntity> list = new ArrayList<TestEntity>();
	
	public ViewLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	
	public void setData(List<TestEntity> list){
		this.list = list;
	}
	
	public void addData(List<TestEntity> addData){
//		ImageView imageView 
//		this.addView(child)
	}

}
