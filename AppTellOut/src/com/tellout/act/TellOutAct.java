package com.tellout.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tellout.adapter.TellOutAdapter;
import com.tellout.constant.DbConstant;
import com.tellout.constant.MConstant;
import com.tellout.entity.BaseEntity;
import com.tellout.entity.RequestEntity;
import com.tellout.entity.TellOutEntity;
import com.tellout.entity.UserEntity;
import com.tellout.util.PopWindowHelper;

/**
 * 吐槽列表
 * 
 * @author sunqm
 * 
 */
public class TellOutAct extends BaseActivity implements OnClickListener,
		OnItemClickListener {

	private android.support.v4.widget.SlidingPaneLayout slidePane;
	/** 显示左侧菜单 */
	private ImageView imgBack;
	/** 新建一个吐槽 */
	private ImageView imgNew;
	/** 登录 */
	private Button btnLogin;

	private ListView listview;
	/** 适配器 */
	private TellOutAdapter adapter;
	/** 显示的数据 */
	private List<TellOutEntity> list = new ArrayList<TellOutEntity>();
	/** 当前的页标 */
	private String pageIndext = "0";

	private String totalSize = "0";

	/** 左侧菜单 */
	private PopWindowHelper windowHelper = null;
	
	/**反馈*/
	private View leftFeedBackView = null;
	
	private EditText etLeftFeedBackContent = null;
	
	private EditText etLeftFeedBackContact = null;
	
	private View myRankView = null;
	
	private TextView myRankViewTvScore = null;
	
	private TextView myRankViewTvWorldRank = null;
	
	private TextView myRankViewTvRegionRank = null;
	/**我所在行业排名*/
	private TextView myRankViewTvIndustryRank = null;
	/**分享给小伙伴*/
	private Button myRankViewBtnShare = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_main_tellout_act);
		initView();
		initLeftPanel();
		request(MConstant.REQUEST_CODE_TELLOUTS);
		Log.d("tag","userid-tell-》"+MConstant.USER_ID_VALUE);
	}

	@Override
	public void showResult(int type, BaseEntity baseEntity) {
		super.showResult(type, baseEntity);
		switch(type){
		case MConstant.REQUEST_CODE_TELLOUTS:
			showTellouts(baseEntity);
			break;
		case MConstant.REQUEST_CODE_GET_MY_RANK:
			showMyRank(baseEntity);
			break;
		}

	}
	
	/**
	 * 显示我的个人排名信息
	 * @param baseEntity
	 */
	private void showMyRank(BaseEntity baseEntity) {
		Toast("myRank");
		UserEntity userEntity = (UserEntity) baseEntity;
		myRankViewTvWorldRank.setText(userEntity.getWorldRank()+"");
		myRankViewTvRegionRank.setText(userEntity.getRegionRank()+"");
		myRankViewTvIndustryRank.setText(userEntity.getIndustryRank()+"");
	}

	private void showTellouts(BaseEntity baseEntity){
		list = (List<TellOutEntity>) baseEntity.getList();
		adapter.addData(list);
		Map<String, String> map = baseEntity.getMap();
		pageIndext = map.get(MConstant.OTHER_PAGE_INDEXT) == null ? "1" : map
				.get(MConstant.OTHER_PAGE_INDEXT);
		totalSize = map.get(MConstant.OTHER_TOTAL_SIZE) == null ? "1" : map
				.get(MConstant.OTHER_TOTAL_SIZE);

		int totalInt = Integer.parseInt(totalSize);
		int indext = Integer.parseInt(pageIndext);
		// 剩余的数量
		int last = totalInt - indext * 20;
		if (last > 0) {
			addFootView();
		} else {
//			listview.removeAllViews();
		}
	}
	
	
	@Override
	public void onClick(View arg0) {
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		switch (arg0.getId()) {
		case R.id.back:
			slidePane.openPane();
			break;
		case R.id.tellout_new_img:
			if(MConstant.USER_ID_VALUE.equals("")){
				slidePane.openPane();
				btnLogin.startAnimation(shake);
			}else{
				startActivity(new Intent(TellOutAct.this, NewTellOutAct.class));
			}
			break;
		case 1000:// 加载更多
			
			request(MConstant.REQUEST_CODE_TELLOUTS);
			break;
		case R.id.left_panel_myinfor:
//			if(MConstant.USER_ID_VALUE.equals("")){
//				btnLogin.startAnimation(shake);
//			}else{
				startActivity(new Intent(TellOutAct.this, EditMyInforAct.class));
				
//			}
			break;
		case R.id.left_panel_myrank:// 我的排名
			if(MConstant.USER_ID_VALUE.equals("")){
				btnLogin.startAnimation(shake);
			}else{
				request(MConstant.REQUEST_CODE_GET_MY_RANK);
				new PopWindowHelper(this).showWindow(myRankView);
			}
			break;
		case R.id.left_panel_worldrank:
			startActivity(new Intent(TellOutAct.this, RanksAct.class).putExtra(
					"flag", 0));
			break;
		case R.id.left_panel_industryrank:
			startActivity(new Intent(TellOutAct.this, RanksAct.class).putExtra(
					"flag", 1));
			break;
		case R.id.left_panel_regionrank:
			startActivity(new Intent(TellOutAct.this, RanksAct.class).putExtra(
					"flag", 2));
			break;
		case R.id.left_panel_companyrank:
			startActivity(new Intent(TellOutAct.this, RanksAct.class).putExtra(
					"flag", 3));
			break;
		case R.id.left_panel_share:
			Intent shareInt = new Intent(Intent.ACTION_SEND);
			shareInt.setType("text/plain");
			shareInt.putExtra(Intent.EXTRA_SUBJECT, "选择分享方式");
			shareInt.putExtra(Intent.EXTRA_TEXT, "我正在使用职场吐槽神器,来一起吐槽吧!"+MConstant.DOWNLOAD_URL);
			shareInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(shareInt);
			break;
		case R.id.left_panel_suggestionback://反馈
			windowHelper.showWindow(leftFeedBackView);
			break;
		case R.id.suggestion_back_btn:
			String temp = etLeftFeedBackContent.getText().toString();
			if(temp!=null&&temp.length()>3){
				windowHelper.closeWindow();
			}else{
				Toast("您输入的太少了!");
			}
			break;
		case R.id.left_panel_about://
			windowHelper.showAbout();
			break;
		case R.id.left_panel_login_btn:// 登录
			startActivity(new Intent(TellOutAct.this, Login_Regist_Act.class));
//			finish();
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		list.get(arg2);
		Intent i = new Intent(TellOutAct.this, TelloutDetailAct.class);
		Bundle b = new Bundle();
		b.putSerializable("tellout", list.get(arg2));
		i.putExtras(b);
		startActivity(i);
	}

	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if(slidePane.isOpen()){
				slidePane.closePane();
			}else{
				showExitDialog();
			}
		}
		return false;
	}

	
	/**
	 * 网络请求
	 * @param requestCode 请求参数
	 */
	private void request(int requestCode){
		RequestEntity entity = new RequestEntity();
		entity.setPost(false);
		entity.setRequestType(requestCode);
		Map<String, String> map = new HashMap<String, String>();
		switch(requestCode){
		case MConstant.REQUEST_CODE_TELLOUTS://吐槽列表
			map.put(MConstant.OTHER_PAGE_INDEXT, pageIndext);
			break;
		case MConstant.REQUEST_CODE_GET_MY_RANK://我的排名
			map.put(DbConstant.DB_USER_ID, MConstant.USER_ID_VALUE);
			break;
		}
		entity.setParams(map);
		//调用父类方法
		request(entity);
	}

	private void initView() {
		slidePane = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout_tellout);
		View view = findViewById(R.id.tellout_act);
		imgBack = (ImageView) view.findViewById(R.id.back);
		imgNew = (ImageView) view.findViewById(R.id.tellout_new_img);
		listview = (ListView) view.findViewById(R.id.tellout_listview);

		imgBack.setOnClickListener(this);
		imgNew.setOnClickListener(this);

		adapter = new TellOutAdapter(this, list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}

	private void initLeftPanel() {
		windowHelper = new PopWindowHelper(this);
		View view = findViewById(R.id.left_pane);
		RelativeLayout myinforRl = (RelativeLayout) view.findViewById(R.id.left_panel_myinfor);
		RelativeLayout myrankRl = (RelativeLayout) view.findViewById(R.id.left_panel_myrank);
		view.findViewById(R.id.left_panel_worldrank).setOnClickListener(this);
		RelativeLayout regionRankRl = (RelativeLayout) view.findViewById(R.id.left_panel_regionrank);
		RelativeLayout companyRank = (RelativeLayout) view.findViewById(R.id.left_panel_companyrank);
		RelativeLayout industryRank = (RelativeLayout) view.findViewById(R.id.left_panel_industryrank)
				;
		view.findViewById(R.id.left_panel_share).setOnClickListener(this);
		view.findViewById(R.id.left_panel_suggestionback).setOnClickListener(this);
		
		View leftAboutView = view.findViewById(R.id.left_panel_about);
		btnLogin = (Button) view.findViewById(R.id.left_panel_login_btn);
		btnLogin.setOnClickListener(this);
		myinforRl.setOnClickListener(this);
		myrankRl.setOnClickListener(this);
		regionRankRl.setOnClickListener(this);
		companyRank.setOnClickListener(this);
		industryRank.setOnClickListener(this);
		
		
		leftAboutView.setOnClickListener(this);
		initMyRank();
		initFeedBack();
	}
	

	/**
	 * 初始化我的排名
	 */
	private void initMyRank() {
		myRankView = View.inflate(this, R.layout.my_infor_rank_act, null);
		myRankViewTvWorldRank = (TextView) myRankView.findViewById(R.id.my_infor_rank_worldrank);
		myRankViewTvRegionRank = (TextView) myRankView.findViewById(R.id.my_infor_rank_regionrank);
		myRankViewTvIndustryRank = (TextView) myRankView.findViewById(R.id.my_infor_rank_industryrank);
		myRankViewBtnShare = (Button) myRankView.findViewById(R.id.my_infor_share_btn);
	}

	private void initFeedBack(){
		leftFeedBackView = View.inflate(this, R.layout.suggestion_back, null);
		leftFeedBackView.findViewById(R.id.suggestion_back_btn).setOnClickListener(this);
		etLeftFeedBackContent = (EditText)leftFeedBackView.findViewById(R.id.suggestion_back_content);
		etLeftFeedBackContact = (EditText) leftFeedBackView.findViewById(R.id.suggestion_back_contact);
		
		Toast("test123initFeedBack");
	}
	
	/**
	 * 添加更多按钮
	 */
	private void addFootView() {
		listview.removeAllViews();
		Button btnNext = new Button(this);
		btnNext.setOnClickListener(this);
		btnNext.setId(1000);
		//
		btnNext.setText("加载更多");
		listview.addFooterView(btnNext);

	}
	
	
	private void showExitDialog() {
		Dialog alertDialog = new AlertDialog.Builder(this)
				.setTitle("提示")
				.setMessage("确定退出吗")
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("退出",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();
								dialog.dismiss();
							}
						})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();

							}
						})		
				.create();

		alertDialog.show();
	}

}
