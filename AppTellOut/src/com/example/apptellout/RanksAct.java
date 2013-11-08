package com.example.apptellout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.example.adapter.WorldRankAdapter;
import com.example.entity.BaseEntity;
import com.example.entity.RequestEntity;
import com.example.entity.UserEntity;
import com.sun.constant.MConstant;
/**
 * 
 * @author sunqm
 * @time 2013-11-8 上午10:18:51
 */
public class RanksAct extends BaseActivity implements OnTabChangeListener, OnClickListener{

	private TabHost mTabHost;

	private ListView listViewWroldRank;
	
	private ListView listViewRegionRank;
	
	private ListView listViewIndustryRank;
	
	private ListView listViewCompanyRank;
	
	private WorldRankAdapter AdapterWorldRank;
	
	private List<UserEntity> dataWorldRank = new ArrayList<UserEntity>();
	/**当前的页数*/
	private String pageIndext = "0";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ranks_act);
		initTab();
		initView();
	}
	
	


	@Override
	public void showResult(int type, BaseEntity baseEntity) {
		super.showResult(type, baseEntity);
		switch(type){
		case MConstant.REQUEST_CODE_WORLD_RANK://世界排名
			dataWorldRank = (List<UserEntity>) baseEntity.getList();
			AdapterWorldRank.setData(dataWorldRank);
			break;
		}
	}
	
	
	@Override
	public void onTabChanged(String arg0) {
		
	}
	
	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.back://返回上一页面
			finish();
			break;
		}
	}
	
	private void initTab() {
		// TODO Auto-generated method stub
		mTabHost = (TabHost)findViewById(R.id.tabhost);  
		mTabHost.setup();   //Call setup() before adding tabs if loading mmTabHost using findViewById().   
        
		mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("世界排行")
				.setContent(R.id.world_rank));
		mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("地区排行")
				.setContent(R.id.region_rank));
		mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("行业排行")
				.setContent(R.id.industry_rank));
		mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator("公司排行")
				.setContent(R.id.company_rank));
		mTabHost.setOnTabChangedListener(this);
	}

	private void initView() {
		findViewById(R.id.back).setOnClickListener(this);	
	}

	
	private void initWorldRank(){
		View temp = (View) findViewById(R.id.tab2);
		listViewWroldRank = (ListView)temp.findViewById(R.id.world_rank_listview);
		AdapterWorldRank = new WorldRankAdapter(this);
		listViewWroldRank.setAdapter(AdapterWorldRank);
		
	}

	/**
	 * 网络请求
	 */
	private void request(){
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setPost(false);
		requestEntity.setRequestType(MConstant.REQUEST_CODE_WORLD_RANK);
		Map<String,String> map = new HashMap<String,String>();
		map.put(MConstant.OTHER_PAGE_INDEXT, pageIndext);
		requestEntity.setParams(map);
		request(requestEntity);
	}
	


}
