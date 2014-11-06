package com.sun.hair;

import com.sun.hair.act.MyInforEditAct;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentMyInfor extends Fragment implements OnClickListener{

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_myinfor, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		view.findViewById(R.id.act_title).setBackgroundResource(R.drawable.bg_top);
		((TextView)view.findViewById(R.id.act_title_center)).setText("个人信息");
		ImageView imgRight = (ImageView) view.findViewById(R.id.act_title_right_img);
		imgRight.setImageResource(R.drawable.icon_edit);
		imgRight.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.act_title_right_img:
			Intent i = new Intent(getActivity(),MyInforEditAct.class);
			i.putExtra("name", "name");
			i.putExtra("sign", "sign");
			startActivity(i);
			break;
		case R.id.fragment_myinfor_show://我的秀场
			
			break;
		}
		
	}
	
	
	
}
