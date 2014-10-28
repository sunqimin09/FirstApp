package com.sun.hair.act;

import java.util.HashMap;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.hair.R;
import com.sun.hair.entity.BusinessEntity;
import com.sun.hair.service.ShopService2;
import com.sun.hair.utils.AfinalBitmapTools;
import com.sun.hair.utils.InterfaceCallback;
import com.sun.hair.utils.MConstant;
import com.sun.hair.utils.Utils;

public class ShopDetailAct2 extends Activity implements InterfaceCallback, OnClickListener{

	private TextView tvName,tvPrice,tvNumber,tvAddress,tvPhone;
	
	private TextView tvNoComment;
	
	private ImageView imgIcon;
	
	private ImageView imgRating;
	
	private Button btnBuy;
	
	private BusinessEntity entity = null;
	
	FinalBitmap fb;
	
	private LinearLayout llMap;
	
	private RelativeLayout rlTuanGou,rlDianPing;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_detail);
		initView();
		initData();
	}

	private void initView() {
		tvName = (TextView) findViewById(R.id.act_detail_name);
		tvNumber = (TextView) findViewById(R.id.act_detail_number);
		tvAddress = (TextView) findViewById(R.id.act_detail_location);
		tvPhone = (TextView) findViewById(R.id.act_detail_phone);
		tvPrice = (TextView) findViewById(R.id.act_detail_price);
		entity = (BusinessEntity) getIntent().getSerializableExtra("business");
		imgIcon = (ImageView) findViewById(R.id.act_detail_icon);
		imgRating = (ImageView) findViewById(R.id.act_detail_rating);
		btnBuy = (Button) findViewById(R.id.act_detail_buy_btn);
		llMap = (LinearLayout) findViewById(R.id.act_detail_ll_map);
		rlTuanGou = (RelativeLayout) findViewById(R.id.act_detail_tuangou);
		rlDianPing = (RelativeLayout) findViewById(R.id.act_detail_dianping);
		btnBuy.setEnabled(false);
		btnBuy.setOnClickListener(this);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	private void initData() {
		tvName.setText(entity.name);
		fb = AfinalBitmapTools.initBitmap(this);
		fb.display(imgIcon, entity.photo_url);
		fb.display(imgRating, entity.rating_img_url);
		Utils.showPrice(tvPrice, entity.avg_price);
		tvAddress.setText("" + entity.address);
		tvPhone.setText(entity.telephone);
		tvPrice.setText("人均:" + entity.avg_price + "元");
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
		if (SpotManager.getInstance(this).checkLoadComplete()) {
//			SpotManager.getInstance(this).showSpotAds(this);
		}

	}
	
	@Override
	protected void onDestroy() {
		SpotManager.getInstance(this).unregisterSceenReceiver();
		super.onDestroy();
	}

	public void onClick(View view){
		Log.d("tag","onclick"+ entity.deals);
//		Toast.makeText(this, "skip--onclick", Toast.LENGTH_SHORT).show();
		switch(view.getId()){
		case R.id.act_detail_ll_map://打开地图应用
			Uri uri = Uri.parse("geo:38.899533,-77.036476");
			 
			Intent it = new Intent(Intent.ACTION_VIEW,uri);
			 
			startActivity(it);
			break;
		case R.id.act_detail_buy_btn://支付页面
			Intent i = new Intent(ShopDetailAct2.this,WebAct.class);
//			
			i.putExtra("url", entity.deals.get(0).url);
			startActivity(i);
			break;
		case R.id.act_detail_rl_phone://拨打电话
			Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+entity.telephone));
			startActivity(intent);
			break;
		case R.id.act_detail_tuangou://团购
			startActivity(new Intent(ShopDetailAct2.this,WebAct.class).putExtra("url", entity.coupon_url));
			
			break;
		case R.id.act_detail_dianping://点评
			startActivity(new Intent(ShopDetailAct2.this,ReViewAct.class).putExtra("businessId", entity.id));
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 请求详情
	 */
	private void request(){
		Log.d("tag","buinessid-->"+entity.id);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("business_id", String.valueOf(entity.id));
		paramMap.put("format", "json");
		new ShopService2().request(MConstant.URL_BUSINESS_DETAIL, paramMap, this);
	}

	@Override
	public void onSuccess(Object o) {
//		Toast.makeText(this, getClass()+"请求成功"+o, Toast.LENGTH_SHORT).show();
		if(o instanceof BusinessEntity){
//			Toast.makeText(this, "请求成功", Toast.LENGTH_SHORT).show();
			entity = (BusinessEntity) o;
			fb.display(imgRating, entity.rating_img_url);
			if(entity.has_coupon!=0){
				rlTuanGou.setVisibility(View.VISIBLE);
			}else{
				rlTuanGou.setVisibility(View.GONE);
			}
				
			if(entity.has_deal==0){//不能购买
				btnBuy.setEnabled(false);
				btnBuy.setText("尚未开通在线购买服务");
			}else{
				btnBuy.setEnabled(true);
			}
			
		}
		
	}

	@Override
	public void onFailed(String strMsg) {
		// TODO Auto-generated method stub
		
	}
	
	
}
