package com.sun.hair.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.spot.SpotManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.hair.R;
import com.sun.hair.adapter.ReviewAdapter;
import com.sun.hair.entity.JsonReviewEntity;
import com.sun.hair.entity.ReviewEntity;
import com.sun.hair.service.ReviewService;
import com.sun.hair.utils.InterfaceCallback;
import com.sun.hair.utils.MConstant;
/**
 *评论列表 页面
 * @author sunqm
 *
 */
public class ReViewAct extends Activity implements InterfaceCallback, OnItemClickListener{

	private ListView listView;
	
	private TextView tvMore;
	
	private ReviewAdapter adapter;
	
	private List<ReviewEntity> list = new ArrayList<ReviewEntity>();
	
	JsonReviewEntity entity = null;
	
	private int businessId = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_review);
		initView();
		initData();
	}

	
	private void initView() {
		listView = (ListView) findViewById(R.id.act_review_listview);
		tvMore = (TextView) findViewById(R.id.act_review_more_tv);
		adapter = new ReviewAdapter(this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	
	private void initData() {
		businessId = getIntent().getIntExtra("businessId", -1);
		entity = new JsonReviewEntity();
		request();
		initAd();
	}
	
	private void initAd() {
		// 实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);

		// 获取要嵌入广告条的布局
		LinearLayout adLayout = (LinearLayout) findViewById(R.id.adLayout);

		// 将广告条加入到布局中
		adLayout.addView(adView);
		SpotManager.getInstance(this).loadSpotAds();
//		if (SpotManager.getInstance(this).checkLoadComplete()) {
//			SpotManager.getInstance(this).showSpotAds(this);
//		}

	}
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	        // TODO Auto-generated method stub
	        if(item.getItemId() == android.R.id.home)
	        {
	            finish();
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }

	private void request() {
		Map<String, String> paramMap = new HashMap<String, String>();
//		Toast.makeText(this, "business-id"+businessId, Toast.LENGTH_SHORT).show();
		paramMap.put("business_id",String.valueOf(businessId));
		paramMap.put("format", "json");
		new ReviewService().request(MConstant.URL_BUSINESS_REVIEW, paramMap, this);
	}
	
	@Override
	public void onSuccess(Object o) {
		if(o instanceof JsonReviewEntity){
			entity = (JsonReviewEntity) o;
			list = ((JsonReviewEntity) o).list;
			adapter.setData(list);
			if(entity.moreUrl.equals("")){
				tvMore.setVisibility(View.GONE);
			}else{
				tvMore.setVisibility(View.VISIBLE);
			}
		}else{
			
		}
	}
	
	public void onClick(View view){
		switch(view.getId()){
		case R.id.act_review_more_tv://更多评论
			startActivity(new Intent(ReViewAct.this,WebAct.class).putExtra("url",entity.moreUrl ));
			break;
		}
	}



	@Override
	public void onFailed(String strMsg) {
		
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		startActivity(new Intent(ReViewAct.this,WebAct.class).putExtra("url", list.get(arg2).review_url));
	}

	
	
}
