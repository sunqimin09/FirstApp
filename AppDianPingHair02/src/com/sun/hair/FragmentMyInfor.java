package com.sun.hair;

import net.tsz.afinal.http.AjaxParams;

import com.sun.hair.act.LoginAct;
import com.sun.hair.act.MyInforEditAct;
import com.sun.hair.service.IRequestCallBack;
import com.sun.hair.service.LoginService;
import com.sun.hair.utils.MConstant;
import com.sun.hair.utils.SpUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 我的个人信息
 * @author sunqm
 *
 */
public class FragmentMyInfor extends Fragment implements OnClickListener, IRequestCallBack{
	
	private TextView tvName,tvSign;
	
	SpUtils sp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = new SpUtils(getActivity());
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
		tvName = (TextView) view.findViewById(R.id.fragment_myinfor_nickname);
		tvSign = (TextView) view.findViewById(R.id.fragment_myinfor_sign);
		
		if(sp.getId().equals("0")){//未登录
			tvName.setText("尚未登录");
			
		}else{
			tvName.setText(sp.get("name"));
			tvSign.setText(sp.get("sign"));
		}
		
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.act_title_right_img:
			if(sp.getId().equals("0")){
				startActivity(new Intent(getActivity(),LoginAct.class));
			}else{
				Intent i = new Intent(getActivity(),MyInforEditAct.class);
				startActivity(i);
			}
			break;
		case R.id.fragment_myinfor_show://我的秀场
			
			break;
		}
		
	}
	
	/**
	 * 获取我的个人信息
	 */
	private void request(){
		AjaxParams params = new AjaxParams();
		
		params.put("userid",new SpUtils(getActivity()).getId());
		new LoginService().request(getActivity(), MConstant.URL_LOGIN, params, this);
	}

	@Override
	public void onSuccess(Object o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailed(String msg) {
		// TODO Auto-generated method stub
		
	}
	
	
}
