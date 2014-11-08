package com.sun.hair;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * 关于
 * @author sunqm
 *
 */
public class AboutUsFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_aboutus, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		TextView tvTitle = (TextView) view.findViewById(R.id.act_title_center);
		tvTitle.setText("关于");
		view.findViewById(R.id.act_title).setBackgroundResource(R.drawable.bg_top);
	}
	
	
	
}
