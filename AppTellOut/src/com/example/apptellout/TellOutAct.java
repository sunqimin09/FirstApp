package com.example.apptellout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
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
 * @author sunqm
 *
 */
public class TellOutAct extends BaseActivity implements OnClickListener, OnItemClickListener{
	
	private android.support.v4.widget.SlidingPaneLayout slidePane;
	/**显示左侧菜单*/
	private ImageView imgBack;
	/**新建一个吐槽*/
	private ImageView imgNew;
	
	private ListView listview;
	/**适配器*/
	private TellOutAdapter adapter ;
	/**显示的数据*/
	private List<TellOutEntity> list = new ArrayList<TellOutEntity>();
	/**当前的页标*/
	private String pageIndext = "0";
	
	private String totalSize = "0";
	
	/**左侧菜单*/
	private PopWindowHelper windowHelper = null;
	
	private View leftAboutView;
	
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
		Map<String,String> map = baseEntity.getMap();
		pageIndext = map.get(MConstant.OTHER_PAGE_INDEXT);
		totalSize = map.get(MConstant.OTHER_TOTAL_SIZE);
		
		
		int totalInt = Integer.parseInt(totalSize);
		int indext = Integer.parseInt(pageIndext);
		//剩余的数量
		int last = totalInt - indext*20;
		if(last>0){
			addFootView();
		}else{
			listview.removeAllViews();
		}
		
	}
	

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.back:
			slidePane.openPane();
			break;
		case R.id.tellout_new_img:
			startActivity(new Intent(TellOutAct.this,NewTellOutAct.class));
			break;
		case 1000://加载更多
			request(pageIndext);
			break;
		case R.id.left_panel_myinfor:
			startActivity(new Intent(TellOutAct.this,EditMyInforAct.class));
			break;
		case R.id.left_panel_myrank://我的排名
			break;
		case R.id.left_panel_worldrank:
			break;
		case R.id.left_panel_industryrank:
			break;
		case R.id.left_panel_regionrank:
			break;
		case R.id.left_panel_companyrank:
			break;
		case R.id.left_panel_share:
			break;
		case R.id.left_panel_setting:
			break;
		case R.id.left_panel_about:
			Toast("about");
			windowHelper.showAbout(leftAboutView);
			break;
		}
		
	}

	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		list.get(arg2);
		Intent i = new Intent(TellOutAct.this,TelloutDetailAct.class);
		Bundle b = new Bundle();
		b.putSerializable("tellout", list.get(arg2));
		i.putExtras(b);
		startActivity(i);
	}
	
	/**
	 * 网络请求
	 * */
	private void request(String pageIndext){
		RequestEntity entity = new RequestEntity();
		entity.setPost(false);
		entity.setRequestType(MConstant.REQUEST_CODE_TELLOUTS);
		Map<String,String> map = new HashMap<String,String>();
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

	private void initLeftPanel(){
		windowHelper = new PopWindowHelper(this);
		View view = findViewById(R.id.left_pane);
		view.findViewById(R.id.left_panel_myinfor).setOnClickListener(this);
		view.findViewById(R.id.left_panel_myrank).setOnClickListener(this);
		view.findViewById(R.id.left_panel_worldrank).setOnClickListener(this);
		view.findViewById(R.id.left_panel_regionrank).setOnClickListener(this);
		view.findViewById(R.id.left_panel_companyrank).setOnClickListener(this);
		view.findViewById(R.id.left_panel_industryrank).setOnClickListener(this);
		view.findViewById(R.id.left_panel_share).setOnClickListener(this);
		view.findViewById(R.id.left_panel_setting).setOnClickListener(this);
		leftAboutView = view.findViewById(R.id.left_panel_about);
		
		leftAboutView.setOnClickListener(this);
		
	}
	
	
	/**
	 * 添加更多按钮
	 */
	private void addFootView(){
		listview.removeAllViews();
		Button btnNext = new Button(this);
		btnNext.setOnClickListener(this);
		btnNext.setId(1000);
		//
		btnNext.setText("加载更多");
		listview.addFooterView(btnNext);
		
	}
	
	
}
