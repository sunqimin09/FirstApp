package com.example.apptellout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.CompanyRankAdapter;
import com.example.adapter.IndustryRankAdapter;
import com.example.adapter.TellOutAdapter;
import com.example.adapter.WorldRankAdapter;
import com.example.entity.BaseEntity;
import com.example.entity.RequestEntity;
import com.example.entity.TellOutEntity;
import com.example.entity.UserEntity;
import com.sun.constant.DbConstant;
import com.sun.constant.MConstant;


@SuppressLint("HandlerLeak")
public class MainActivity extends BaseActivity implements OnTabChangeListener, OnClickListener {

	private SlidingPaneLayout mSlidingLayout;

	private TabHost mTabHost;

	private ListView listViewWroldRank;
	
	private ListView listViewTellout;
	
	private ListView listViewCompanyRank;
	/**编辑个人信息*/
	private Button btnEditMyInfor;
	/**当前积分*/
	private TextView tvScore;
	/**世界排名*/
	private TextView tvWorldRank;
	
//	private LinearLayout llMyInfor;
	
	/**世界排名*/
	private WorldRankAdapter WorldAdapter;
	/**行业排名*/
	private IndustryRankAdapter industryAdapter;
	/**公司排名*/
	private CompanyRankAdapter companyAdapter;
	
	private TellOutAdapter tellOutAdapter ;
	/**吐槽数据列表*/
	private List<TellOutEntity> TellOuts = new ArrayList<TellOutEntity>();
	
	private String tellOutPageIndext = "0";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView(savedInstanceState);
		initMyInfor();
		initWorldRank();
		initTellOut();
//		initIndustryRank();
//		initCompanyRank();
	}

	private void initView(Bundle savedInstanceState) {
		mSlidingLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);

		mSlidingLayout.setPanelSlideListener(new SliderListener());
		mSlidingLayout.openPane();
		mSlidingLayout.getViewTreeObserver().addOnGlobalLayoutListener(
				new FirstLayoutListener());
		
		
		mTabHost = (TabHost)findViewById(R.id.tabhost);  
		mTabHost.setup();   //Call setup() before adding tabs if loading mmTabHost using findViewById().   
        
		mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("我的信息")
				.setContent(R.id.tab1));
		mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("排行榜")
				.setContent(R.id.tab2));
		mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("行业排行")
				.setContent(R.id.tab3));
//		mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator("公司排行")
//				.setContent(R.id.tab4));
		mTabHost.setOnTabChangedListener(this);
		
	}
	
	private void initMyInfor(){
		View viewMyInfor = findViewById(R.id.tab1);
		btnEditMyInfor = (Button) viewMyInfor.findViewById(R.id.my_infor_detail_btn);
//		llMyInfor = (LinearLayout) viewMyInfor.findViewById(R.id.my_infor_ll);
		btnEditMyInfor.setOnClickListener(this);
	}
	
	private void initWorldRank(){
		View temp = (View) findViewById(R.id.tab2);
		listViewWroldRank = (ListView)temp.findViewById(R.id.world_rank_listview);
		WorldAdapter = new WorldRankAdapter(this);
		listViewWroldRank.setAdapter(WorldAdapter);
	}
	
	private void initIndustryRank(){
		View temp = (View) findViewById(R.id.tab3);
		listViewTellout = (ListView)temp.findViewById(R.id.industry_rank_listview);
//		industryAdapter = new IndustryRankAdapter(this);
//		listViewIndustryRank.setAdapter(industryAdapter);
	}
	
	private void initCompanyRank(){
		View temp = (View) findViewById(R.id.tab4);
		listViewCompanyRank = (ListView)temp.findViewById(R.id.company_rank_listview);
		companyAdapter = new CompanyRankAdapter(this);
		listViewCompanyRank.setAdapter(companyAdapter);
	}
	
	/**
	 * 吐槽列表
	 */
	private void initTellOut(){
		View temp = (View) findViewById(R.id.tab3);
		listViewTellout = (ListView)temp.findViewById(R.id.industry_rank_listview);
		tellOutAdapter = new TellOutAdapter(this,TellOuts);
		listViewTellout.setAdapter(tellOutAdapter);
		listViewTellout.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TellOuts.get(arg2);
				Intent i = new Intent(MainActivity.this,TelloutDetailAct.class);
				Bundle b = new Bundle();
				b.putSerializable("tellout", TellOuts.get(arg2));
				i.putExtras(b);
				startActivity(i);
			}
		});
		
	}
	
	@Override
	public void showResult(int type, BaseEntity baseEntity) {
		super.showResult(type, baseEntity);
		JSONArray array = null;
		switch (type) {
		case MConstant.REQUEST_CODE_GET_MY_RANK:// 个人排名信息
			
			break;
		case MConstant.REQUEST_CODE_WORLD_RANK:// 世界排名
			WorldAdapter.setData((List<UserEntity>) baseEntity.getList());
			break;
		case MConstant.REQUEST_CODE_COMPANY_RANK:// 公司排名
			// companyAdapter.setData((List<UserEntity>) baseEntity.getList());
			break;
		case MConstant.REQUEST_CODE_INDUSTRY_RANK:// 行业排名
			// industryAdapter..setData((List<UserEntity>)
			// baseEntity.getList());
			break;
		case MConstant.REQUEST_CODE_TELLOUTS:
			TellOuts = (List<TellOutEntity>) baseEntity.getList();
			tellOutAdapter.addData(TellOuts);
			Map<String,String> map = baseEntity.getMap();
			tellOutPageIndext = map.get(MConstant.OTHER_PAGE_INDEXT);
			break;
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * This panel slide listener updates the action bar accordingly for each
	 * panel state.
	 */
	private class SliderListener extends
			SlidingPaneLayout.SimplePanelSlideListener {
		@Override
		public void onPanelOpened(View panel) {
			// mActionBar.onPanelOpened();
		}

		@Override
		public void onPanelClosed(View panel) {
			// mActionBar.onPanelClosed();
		}
	}

	/**
	 * This global layout listener is used to fire an event after first layout
	 * occurs and then it is removed. This gives us a chance to configure parts
	 * of the UI that adapt based on available space after they have had the
	 * opportunity to measure and layout.
	 */
	private class FirstLayoutListener implements
			ViewTreeObserver.OnGlobalLayoutListener {
		@SuppressLint("NewApi")
		@Override
		public void onGlobalLayout() {
			// mActionBar.onFirstLayout();
			mSlidingLayout.getViewTreeObserver().removeOnGlobalLayoutListener(
					this);
		}
	}

	@Override
	public void onTabChanged(String arg0) {
		Map<String,String> map = new HashMap<String,String>();
		RequestEntity requestEntity = new RequestEntity();
		if(arg0.equals("tab1")){
			requestEntity.setRequestType(MConstant.REQUEST_CODE_GET_MY_RANK);
//			type =0;
		}else if(arg0.equals("tab2")){
			requestEntity.setRequestType(MConstant.REQUEST_CODE_WORLD_RANK);
//			type = 1;
		}else if(arg0.equals("tab3")){
			requestEntity.setRequestType(MConstant.REQUEST_CODE_TELLOUTS);
			//当前的页数
			map.put(MConstant.OTHER_PAGE_INDEXT, tellOutPageIndext);
			
//			type = 2;
		}else if(arg0.equals("tab4")){
			requestEntity.setRequestType(MConstant.REQUEST_CODE_COMPANY_RANK);
//			type = 3;
		}
		requestEntity.setPost(false);
		
		map.put(DbConstant.DB_USER_ID, MConstant.USER_ID_VALUE);
		requestEntity.setParams(map);
		request(requestEntity);
		
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.my_infor_detail_btn:
			startActivity(new Intent(MainActivity.this,EditMyInforAct.class));
			Toast.makeText(this, "btn", Toast.LENGTH_SHORT).show();
			break;
		}
		
	}

}
