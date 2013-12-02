package com.tellout.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.tellout.util.Utils;

/**
 * 吐槽列表
 * 
 * @author sunqm
 * 
 */
public class TellOutAct extends BaseActivity implements OnClickListener, OnScrollListener, OnTouchListener ,
		OnItemClickListener {

	private android.support.v4.widget.SlidingPaneLayout slidePane;
	/** 显示左侧菜单 */
	private ImageView imgBack;
	/** 新建一个吐槽 */
	private ImageView imgNew;
	/** 登录 */
	private Button btnLogin;

	private ListView listview;
	
	private TextView top;
	
	private TextView bottom;
	
	private LinearLayout ll;
	
	private boolean headVisiable = false;
	
	private boolean footVisiable = false; 
	
	/** 适配器 */
	private TellOutAdapter adapter;
	/** 显示的数据 */
	private List<TellOutEntity> list = new ArrayList<TellOutEntity>();
	/** 当前的页标 */
	private int pageIndext = 0;

	private int firstItem = 0;
	
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
	
	private TextView myRankViewPercent = null;
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
	protected void onResume() {
		super.onResume();
		
		if(MConstant.USER_ID_VALUE.equals("")){
			btnLogin.setText(getString(R.string.login));
		}else{
			btnLogin.setText(getString(R.string.logout));
		}
	}

	@Override
	public void showResult(int type, BaseEntity baseEntity) {
		super.showResult(type, baseEntity);
		switch(type){
		case MConstant.REQUEST_CODE_TELLOUTS:
			Log.d("tag","showresult->"+headVisiable);
			if(headVisiable){
				top.setText("更新完成");
				top.setVisibility(View.GONE);
				headVisiable = !headVisiable;
			}
			if(footVisiable){
				bottom.setVisibility(View.GONE);
				footVisiable = ! footVisiable;
			}
			showTellouts(baseEntity);
			break;
		case MConstant.REQUEST_CODE_GET_MY_RANK:
			showMyRank(baseEntity);
			break;
		}

	}
	
	private int per;
	/**
	 * 显示我的个人排名信息
	 * @param baseEntity
	 */
	private void showMyRank(BaseEntity baseEntity) {
		Toast("myRank");
		UserEntity userEntity = (UserEntity) baseEntity;
		int total = userEntity.getTotalSize();
		int workdRank = userEntity.getWorldRank();
		per = (total-workdRank)*100/(total-1);
		Log.d("tag","per->"+per);
		myRankViewTvWorldRank.setText("我的排名:"+userEntity.getWorldRank()+"");
		myRankViewTvRegionRank.setText("所地区内排名:"+userEntity.getRegionRank()+"");
		myRankViewTvIndustryRank.setText("所在行业内排名:"+userEntity.getIndustryRank()+"");
		myRankViewPercent.setText("成功击败"+per+"%用户");
	}

	private void showTellouts(BaseEntity baseEntity){
		list = (List<TellOutEntity>) baseEntity.getList();
		adapter.addData(list);
		Map<String, String> map = baseEntity.getMap();
		String pageIndextStr = map.get(MConstant.OTHER_PAGE_INDEXT) == null ? "1" : map
				.get(MConstant.OTHER_PAGE_INDEXT);
		totalSize = map.get(MConstant.OTHER_TOTAL_SIZE) == null ? "1" : map
				.get(MConstant.OTHER_TOTAL_SIZE);

		int totalInt = Integer.parseInt(totalSize);
		pageIndext = Integer.parseInt(pageIndextStr);
		// 剩余的数量
		int last = totalInt - pageIndext * 20;
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
			if(MConstant.USER_ID_VALUE.equals("")){
				btnLogin.startAnimation(shake);
			}else{
				startActivity(new Intent(TellOutAct.this, EditMyInforAct.class));
				
			}
			break;
		case R.id.left_panel_myrank:// 我的排名
			if(MConstant.USER_ID_VALUE.equals("")){
				btnLogin.startAnimation(shake);
			}else{
				request(MConstant.REQUEST_CODE_GET_MY_RANK);
				new PopWindowHelper(this).showWindow(myRankView);
			}
			break;
		case R.id.left_panel_myHistoryinfor://我发表过得
			if(MConstant.USER_ID_VALUE.equals("")){
				btnLogin.startAnimation(shake);
			}else{
				startActivity(new Intent(TellOutAct.this,MyHistoryInforAct.class));
			}
			break;
		case R.id.my_infor_share_btn:
			Utils.share(this,"您的薪资击败了"+per+"%的职友！！");
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
			Utils.share(this,"我正在使用职场吐槽神器,来一起吐槽吧!");
			
			break;
		case R.id.left_panel_suggestionback://反馈
			startActivity(new Intent(TellOutAct.this,FeedBackAct.class));
//			windowHelper.showWindow(leftFeedBackView);
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
			if(btnLogin.getText().equals(getString(R.string.login))){
				startActivity(new Intent(TellOutAct.this, Login_Regist_Act.class));
//				btnLogin.setText("注销");
			}else if(btnLogin.getText().equals(getString(R.string.logout))){
				MConstant.USER_ID_VALUE = "";
				btnLogin.setText(getString(R.string.login));
			}
			
//			finish();
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		list.get(arg2);
		Intent i = new Intent(TellOutAct.this, TelloutDetailAct.class);
		Bundle b = new Bundle();
		b.putSerializable("tellout", list.get((int) arg3));
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
//
//	private void share(String shareStr){
//		Intent shareInt = new Intent(Intent.ACTION_SEND);
//		shareInt.setType("text/plain");
//		shareInt.putExtra(Intent.EXTRA_SUBJECT, "选择分享方式");
//		shareInt.putExtra(Intent.EXTRA_TEXT, shareStr+MConstant.DOWNLOAD_URL);
//		shareInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		startActivity(shareInt);
//	}
	
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
			map.put(MConstant.OTHER_PAGE_INDEXT, pageIndext+"");
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
		/**添加下拉刷新*/
		top = new TextView(this);
		top.setText("松开立即刷新");
		ll = new LinearLayout(this);
		ll.setGravity(Gravity.CENTER);
		top.setPadding(10, 10, 10, 10);
		ll.addView(top);
		listview.addHeaderView(ll);
		top.setVisibility(View.GONE);
		headVisiable = false;
		/**上拉加载更多*/
		bottom = new TextView(this);
		bottom.setText("松开立即刷新");
		LinearLayout llBottom = new LinearLayout(this);
		llBottom.setGravity(Gravity.CENTER);
		bottom.setPadding(10, 10, 10, 10);
		llBottom.addView(bottom);
		listview.addFooterView(llBottom);
		bottom.setVisibility(View.GONE);
		footVisiable = false;
		
		adapter = new TellOutAdapter(this, list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		listview.setOnScrollListener(this);
		listview.setOnTouchListener(this);
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
		myRankViewPercent = (TextView) myRankView.findViewById(R.id.my_infor_rank_percentage);
		myRankViewBtnShare = (Button) myRankView.findViewById(R.id.my_infor_share_btn);
		myRankViewBtnShare.setOnClickListener(this);
	}

	private void initFeedBack(){
		leftFeedBackView = View.inflate(this, R.layout.suggestion_back, null);
		leftFeedBackView.findViewById(R.id.suggestion_back_btn).setOnClickListener(this);
		etLeftFeedBackContent = (EditText)leftFeedBackView.findViewById(R.id.suggestion_back_content);
		etLeftFeedBackContact = (EditText) leftFeedBackView.findViewById(R.id.suggestion_back_contact);
		
//		Toast("test123initFeedBack");
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
	
	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		Log.d("tag","onscroll->"+arg1+"->"+arg2+"->"+arg3);
		firstItem = arg1;
		if(arg1+arg2==arg3){//当前的位置+ 本页显示的数量 = 总的数量
			firstItem = arg3;
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		Log.d("tag","state-changed->"+arg1);
		if(arg1==SCROLL_STATE_TOUCH_SCROLL&&firstItem ==0){//开始下拉
			Log.d("tag","top->");
			listview.setSelection(1);	
			top.setVisibility(View.VISIBLE);
			headVisiable = true;
			top.setText("下拉刷新");
		}else if(arg1 == SCROLL_STATE_TOUCH_SCROLL&&firstItem == list.size()){//上拉加载更多
			bottom.setVisibility(View.VISIBLE);
			bottom.setText("加载更多...");
			footVisiable =true;
			request(MConstant.REQUEST_CODE_TELLOUTS);
		}else if(firstItem>0&&headVisiable){
			top.setVisibility(View.GONE);
			headVisiable = false;
		}else if(footVisiable &&firstItem == list.size()){
			bottom.setVisibility(View.GONE);
//			if(la)
			pageIndext ++;
			request(MConstant.REQUEST_CODE_TELLOUTS);
			
		}
		
	}
	
	private float downY = 0;
	
	private float paddingTop = 0; 
	
	/**
	 * 松手后刷新
	 */
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		switch(arg1.getAction()){
		case MotionEvent.ACTION_DOWN:
			downY =  arg1.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			paddingTop = arg1.getY()-downY;
			if(paddingTop>10){
				paddingTop = paddingTop>200?200:paddingTop;
				ll.setPadding(0, (int)paddingTop-10, 0, 0);
				if(paddingTop>150){
					top.setText("松开立即刷新");
				}
				break;
			}
			
			
			break;
		case MotionEvent.ACTION_UP:
			downY = 0;
			timer.schedule(new timerTask(), 0);
			
			break;
		}
		return false;
	}
	
	Handler handler = new Handler(){
		
		
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 1:
				ll.setPadding(0, msg.arg1, 0, 0);
				break;
			case 2:
				top.setText("正在刷新...");
				if(headVisiable)
					request(MConstant.REQUEST_CODE_TELLOUTS);
				break;
			}
			
			
		}

		
	};
	
	private Timer timer = new Timer();

	/**
	 * 反弹
	 * @author sunqm
	 *
	 */
	private class timerTask extends TimerTask{
		
		private int sleep = 3;
		
		@Override
		public void run() {
			while(paddingTop>10){
				Message msg = handler.obtainMessage(1);
				msg.arg1 = (int)paddingTop-10;
				handler.sendMessage(msg);
//				ll.setPadding(0, , 0, 0);
				paddingTop--;
				try {
					if(paddingTop>100){
						sleep = 1;
					}else if(paddingTop>50){
						sleep = 2;
					}else{
						sleep = 3;
					}
					synchronized (this) {
						this.wait(sleep);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Message msg = handler.obtainMessage(2);
			handler.sendMessage(msg);
		}
		
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
