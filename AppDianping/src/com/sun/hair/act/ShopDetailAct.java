package com.sun.hair.act;

import java.util.HashMap;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.sun.hair.R;
import com.sun.hair.adapter.DealsAdapter;
import com.sun.hair.entity.BusinessEntity;
import com.sun.hair.service.ShopDetailService;
import com.sun.hair.utils.AfinalBitmapTools;
import com.sun.hair.utils.InterfaceCallback;
import com.sun.hair.utils.MConstant;
import com.sun.hair.utils.Utils;

/**
 * 
 * @author sunqm
 *
 */
public class ShopDetailAct extends Activity implements OnItemClickListener, InterfaceCallback{

	private TextView tvName;
	
	private TextView tvPrice;
	
	private TextView tvBuyer;
	
	private ImageView img_Icon;
	
	private TabHost tabHost;
	
	private TextView tvDescription;
	/**优惠券*/
	private TextView tvCoupon;
	/**分店名*/
	private TextView tvBranchName;
	
	private TextView tvAddress;
	
	private TextView tvDistance;
	
	private TextView tvPhone;
	
	private Button btnBuy;
	
	private ListView listViewDeals;
	
	private TabSpec tabReview;
	
	private DealsAdapter dealsAdapter;
	
	private BusinessEntity entity = null;
	
	private FinalBitmap fb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_act);
		setTitle("商家详细");
		initView();
		initData();
//		 android.app.Activity.getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private void initView() {
		img_Icon = (ImageView) findViewById(R.id.detail_act_icon_img);
		tvName = (TextView) findViewById(R.id.detail_act_name_tv);
		tvPrice = (TextView) findViewById(R.id.detail_act_price_tv);
		tvBuyer = (TextView) findViewById(R.id.detail_act_buyer);
		tvBranchName = (TextView) findViewById(R.id.detail_act_branch_name_tv);
		tvPhone = (TextView)findViewById(R.id.detail_act_phone_tv);
		btnBuy = (Button) findViewById(R.id.detail_act_buy_btn);
		tvDescription = (TextView) findViewById(R.id.tab1_description_tv);
		tvAddress = (TextView) findViewById(R.id.detail_act_address_tv);
		tvCoupon = (TextView) findViewById(R.id.tab2_coupon_tv);
		tvDistance = (TextView) findViewById(R.id.detail_act_distance_tv);
		listViewDeals = (ListView) findViewById(R.id.tab2_lv_deals);
		listViewDeals.setOnItemClickListener(this);
		tvDescription.setText("test");
		entity = (BusinessEntity) getIntent().getSerializableExtra("business");
		initTab();
		
	}
	
	private void initTab(){
		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		Log.d("tag","tabHost-->"+tabHost);
//		tabHost.addTab(tabHost.newTabSpec("tab1")
//                .setIndicator(getString(R.string.detail))
//                .setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator(getString(R.string.coupon_and_deal))
                .setContent(R.id.tab2));
		tabReview = tabHost.newTabSpec("tab3")
                .setIndicator(getString(R.string.comment)+"("+entity.review_count+")")
                .setContent(R.id.tab3);
		tabHost.addTab(tabReview);
//		tabHost.addTab(tabHost.newTabSpec("tab4")
//                .setIndicator(getString(R.string.other))
//                .setContent(R.id.tab4));
	}
	
	private void initData() {
		
		tvName.setText(entity.name);
		fb = AfinalBitmapTools.initBitmap(this);
		fb.display(img_Icon, entity.photo_url);
//		ImageLoader imgLoader = new ImageLoader(this);
//		imgLoader.DisPlay(img_Icon, entity.photo_url);
		Utils.showPrice(tvPrice,entity.avg_price);
//		tvBuyer.setText(entity.)
		tvBranchName.setText(""+entity.branch_name);
		tvAddress.setText(""+entity.address);
		tvDistance.setText(Utils.distanceFormat(entity.distance));
		tvPhone.setText(entity.telephone);
//		tvDescription.setText(""+entity.coupon_description);
		Log.d("tag","data-->"+entity.deals);
		dealsAdapter = new DealsAdapter(this,entity.deals);
		listViewDeals.setAdapter(dealsAdapter);
		
		request();
		if(entity.deals.size()==0){
			btnBuy.setVisibility(View.GONE);
		}else{
			btnBuy.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 请求评价
	 */
	private void request(){
		Log.d("tag","buinessid-->"+entity.id);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("business_id", String.valueOf(entity.id));
		paramMap.put("format", "json");
		new ShopDetailService().request(MConstant.URL_BUSINESS_REVIEW, paramMap, this);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case android.R.id.home:
//			finish();
//			break;
//		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClick(View view){
		switch(view.getId()){
		case R.id.detail_act_buy_btn://进入支付页面
			Intent i = new Intent(ShopDetailAct.this,WebAct.class);
			i.putExtra("url", entity.deals.get(0).url);
			if(entity.deals.size()>0)

			startActivity(i);
//			startActivity(new Intent(DetailAct.this,PayAct.class));
			break;
		case R.id.detail_act_buiness_detail_rl://进入支付详情页面
			Intent intent1 = new Intent(ShopDetailAct.this,WebAct.class);
			intent1.putExtra("url", entity.business_url);
			startActivity(intent1);
			break;
		case R.id.detail_act_phone_rl://电话
			Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+entity.telephone));
			startActivity(intent);
			break;
		}
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//进入团购页面
		Intent i = new Intent(ShopDetailAct.this,WebAct.class);
		i.putExtra("url", entity.deals.get(arg2).url);
		startActivity(i);
		
	}


	@Override
	public void onSuccess(Object o) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onFailed(String strMsg) {
		// TODO Auto-generated method stub
		
	}
	
	
}
