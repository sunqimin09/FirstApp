/**
 * 
 */
package com.sun.apphair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.sun.apphair.adapter.CommentAdapter;
import com.sun.apphair.entity.CommentEntity;
import com.sun.apphair.entity.RequestEntity;
import com.sun.apphair.entity.ResponseResult;
import com.sun.apphair.entity.ShopEntity;
import com.sun.apphair.entity.ShowResult;
import com.sun.apphair.internet.InternetHelper;
import com.sun.apphair.json.JsonComment;
import com.sun.apphair.utils.Mconstant;

/**
 * 项目名称：Hair
 * 文件名：ShopDetailAct.java  
 * 作者：@sunqm    
 * 创建时间：2014-1-18 下午3:18:19
 * 功能描述:  
 * 版本 V 1.0               
 */
public class ShopDetailAct extends BaseAct implements OnTabChangeListener{
	
	
	private TextView tv_name;
	
	private TextView tv_price;
	
	private TextView tv_address;
	
	private TextView tv_phone;
	
	private RelativeLayout rl_address;
	
	private RelativeLayout rl_phone;
	
	private RelativeLayout rl_order;
	
	private ImageView img_icon;
	/***/
	private RatingBar ratingBar;
	
	private TabHost tabHost = null;
	/**好评*/
	private ListView lv_good = null;
	/**差评*/
	private ListView lv_bad = null;
	
	private CommentAdapter goodAdapter = null;
	
	private CommentAdapter badAdapter = null;
	
	private List<CommentEntity> list_comment_good = new ArrayList<CommentEntity>();
	
	private List<CommentEntity> list_comment_bad = new ArrayList<CommentEntity>();
	
	private ShopEntity shopEntity = null;
	
	private final int GOOD =0;
	
	private final int BAD = 1;
	
	private boolean firstBad = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_detail_act);
		initView();
		initData();
	}
	

	private void initView(){
		tv_name = (TextView) findViewById(R.id.shop_detail_tv_name);
		tv_address = (TextView) findViewById(R.id.shop_detail_tv_address);
		tv_phone = (TextView) findViewById(R.id.shop_detail_tv_phone);
		tv_price = (TextView) findViewById(R.id.shop_detail_tv_price);
		img_icon = (ImageView) findViewById(R.id.shop_detail_img_icon);
		ratingBar = (RatingBar) findViewById(R.id.shop_detail_ratingbar);
		lv_good = (ListView) findViewById(R.id.shop_comment_ls_ok);
		lv_bad =(ListView) findViewById(R.id.shop_comment_ls_no);
		rl_address = (RelativeLayout) findViewById(R.id.rl2_address);
		rl_phone = (RelativeLayout) findViewById(R.id.rl3_phone);
		rl_order = (RelativeLayout) findViewById(R.id.rl4_order);
		
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("tab1")
			       .setIndicator(getString(R.string.comment_good),null)  
			           .setContent(R.id.tab1));  

		tabHost.addTab(tabHost.newTabSpec("tab2")
			       .setIndicator(getString(R.string.comment_bad))  
			           .setContent(R.id.tab2));
		tabHost.setOnTabChangedListener(this);
	}
	

	/**
	 * 数据初始化
	 */
	private void initData() {
		shopEntity = (ShopEntity) getIntent().getSerializableExtra("shop");
//		tv_name.setText(shopEntity.name);
//		tv_price.setText(shopEntity.address);
//		tv_phone.setText(shopEntity.phone);
//		tv_address.setText(shopEntity.address);
//		ratingBar.setProgress((int)shopEntity.ratingbarScore);
		goodAdapter = new CommentAdapter(this, list_comment_good);
		badAdapter = new CommentAdapter(this, list_comment_bad);
		
		lv_good.setAdapter(goodAdapter);
		lv_bad.setAdapter(badAdapter);
	}
	
	public void OnClick(View view){
		switch(view.getId()){
		case R.id.rl2_address://地址
			
			break;
		case R.id.rl3_phone://电话
			
			break;
		case R.id.rl4_order://购买
			if(Mconstant.LoginName.equals("")){//没有登录
				startActivity(new Intent(ShopDetailAct.this,LoginAct.class));
				return ;
			}
			break;
		}
	}
	
	
	/**
	 * 请求评论，0，好评，1：差评
	 */
	private void request(int type){
		
		RequestEntity requestEntity = new RequestEntity(this,
				Mconstant.URL_SHOPS);
		requestEntity.requestCode = type;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("shopId", shopEntity.id);
		map.put("type", type);
		requestEntity.params = map;
		new InternetHelper(this).requestThread(requestEntity, this);
	}


	@Override
	public void requestSuccess(ResponseResult responseResult) {
		super.requestSuccess(responseResult);
		ShowResult showResult = new JsonComment().parse(responseResult, this);
		list_comment_good.add((CommentEntity) showResult.list);
		switch(responseResult.requestCode){
		case GOOD://好评
			goodAdapter.notifyDataSetChanged();
			break;
		case BAD://差评
			badAdapter.notifyDataSetChanged();
			break;
		
		}
	}


	/* (non-Javadoc)
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
	 */
	@Override
	public void onTabChanged(String tabId) {
		if(tabId.equals("tab2")&&firstBad){//加载差评
//			request(BAD);
			firstBad = false;
		}
	}
	
	
	
	
}
