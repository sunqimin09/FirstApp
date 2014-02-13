/**
 * 
 */
package com.sun.apphair.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sun.apphair.R;

/**
 * 项目名称：Hair
 * 文件名：StringAdapter.java  
 * 作者：@sunqm    
 * 创建时间：2014-1-21 上午10:07:01
 * 功能描述:  
 * 版本 V 1.0               
 */
public class StringAdapter extends BaseAdapter{

	private String[] strs = null;
	
	private Context context = null;
	
	public StringAdapter(Context context,String[] strings){
		this.context = context;
		this.strs = strings;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return strs.length;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return strs[position];
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view =  View.inflate(context, R.layout.test, null);
		TextView tv = (TextView) view.findViewById(R.id.textView1);
		tv.setText(strs[position]);
		return tv;
	}

	

}
