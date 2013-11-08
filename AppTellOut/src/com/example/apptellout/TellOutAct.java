package com.example.apptellout;

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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.adapter.TellOutAdapter;
import com.example.entity.BaseEntity;
import com.example.entity.RequestEntity;
import com.example.entity.TellOutEntity;
import com.example.util.PopWindowHelper;
import com.sun.constant.MConstant;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_main_tellout_act);
		initView();
		initLeftPanel();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void showResult(int type, BaseEntity baseEntity) {
		super.showResult(type, baseEntity);
		list = (List<TellOutEntity>) baseEntity.getList();
		adapter.addData(list);
		Map<String, String> map = baseEntity.getMap();
		pageIndext = map.get(MConstant.OTHER_PAGE_INDEXT);
		totalSize = map.get(MConstant.OTHER_TOTAL_SIZE);

		int totalInt = Integer.parseInt(totalSize);
		int indext = Integer.parseInt(pageIndext);
		// 剩余的数量
		int last = totalInt - indext * 20;
		if (last > 0) {
			addFootView();
		} else {
			listview.removeAllViews();
		}

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.back:
			slidePane.openPane();
			break;
		case R.id.tellout_new_img:
			startActivity(new Intent(TellOutAct.this, NewTellOutAct.class));
			break;
		case 1000:// 加载更多
			request(pageIndext);
			break;
		case R.id.left_panel_myinfor:
			startActivity(new Intent(TellOutAct.this, EditMyInforAct.class));
			break;
		case R.id.left_panel_myrank:// 我的排名
			windowHelper.showMyRank();
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
			finish();
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
	 * */
	private void request(String pageIndext) {
		RequestEntity entity = new RequestEntity();
		entity.setPost(false);
		entity.setRequestType(MConstant.REQUEST_CODE_TELLOUTS);
		Map<String, String> map = new HashMap<String, String>();
		map.put(MConstant.OTHER_PAGE_INDEXT, pageIndext);
		entity.setParams(map);
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
		view.findViewById(R.id.left_panel_myinfor).setOnClickListener(this);
		view.findViewById(R.id.left_panel_myrank).setOnClickListener(this);
		view.findViewById(R.id.left_panel_worldrank).setOnClickListener(this);
		view.findViewById(R.id.left_panel_regionrank).setOnClickListener(this);
		view.findViewById(R.id.left_panel_companyrank).setOnClickListener(this);
		view.findViewById(R.id.left_panel_industryrank)
				.setOnClickListener(this);
		view.findViewById(R.id.left_panel_share).setOnClickListener(this);
		view.findViewById(R.id.left_panel_suggestionback).setOnClickListener(this);
		initFeedBack();
		View leftAboutView = view.findViewById(R.id.left_panel_about);
		btnLogin = (Button) view.findViewById(R.id.left_panel_login_btn);
		btnLogin.setOnClickListener(this);
		leftAboutView.setOnClickListener(this);

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
				.setTitle("对话框的标题")
				.setMessage("对话框的内容")
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("exit",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();
								dialog.dismiss();
							}
						})
				.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

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
