/**
 * 
 */
package com.sun.apphair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
	
	private Button btn_order;
	
	private ImageView img_icon;
	/***/
	private ImageView ratingBar;
	
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_detail_act);
		initView();
		initData();
	}
	

	private void initView(){
		findViewById(R.id.title_back).setVisibility(View.VISIBLE);
		tv_name = (TextView) findViewById(R.id.title_content);
		tv_address = (TextView) findViewById(R.id.shop_detail_tv_address);
		tv_phone = (TextView) findViewById(R.id.shop_detail_tv_phone);
		tv_price = (TextView) findViewById(R.id.shop_detail_tv_price);
		img_icon = (ImageView) findViewById(R.id.shop_detail_img_icon);
		ratingBar = (ImageView) findViewById(R.id.shop_detail_ratingbar);
		lv_good = (ListView) findViewById(R.id.shop_comment_ls_ok);
		lv_bad =(ListView) findViewById(R.id.shop_comment_ls_no);
		rl_address = (RelativeLayout) findViewById(R.id.rl2_address);
		rl_phone = (RelativeLayout) findViewById(R.id.rl3_phone);
		btn_order = (Button) findViewById(R.id.shop_detail_btn_order);
		
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
		tv_name.setText(shopEntity.name);
		tv_price.setText("￥"+shopEntity.price);
		tv_phone.setText(shopEntity.phone);
		tv_address.setText(shopEntity.address);
		goodAdapter = new CommentAdapter(this, list_comment_good);
		badAdapter = new CommentAdapter(this, list_comment_bad);
		
		lv_good.setAdapter(goodAdapter);
		lv_bad.setAdapter(badAdapter);
		request(GOOD);
	}
	
	public void OnClick(View view){
		switch(view.getId()){
		case R.id.title_back://返回
			finish();
			break;
		case R.id.rl2_address://地址
			startActivity(new Intent(ShopDetailAct.this,LocationOverlayDemo.class));
			break;
		case R.id.rl3_phone://电话
			Uri uri = Uri.parse("tel:"+shopEntity.phone);   
			 
			Intent intent = new Intent(Intent.ACTION_DIAL, uri);     
			 
			startActivity(intent);  
			break;
		case R.id.shop_detail_btn_order://购买
			if(Mconstant.LoginName.equals("1")){//没有登录
				startActivity(new Intent(ShopDetailAct.this,LoginAct.class));
				return ;
			}else{
				startActivity(new Intent(ShopDetailAct.this,OrderAct.class));
			}
			break;
		}
	}
	
	
	/**
	 * 请求评论，0，好评，1：差评
	 */
	private void request(int type){
		
		RequestEntity requestEntity = new RequestEntity(this,
				Mconstant.URL_SHOP_COMMENT);
		requestEntity.requestCode = type;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("shop_id", shopEntity.id);
		map.put("type", type);
		requestEntity.params = map;
		new InternetHelper(this).requestThread(requestEntity, this);
	}


	@SuppressWarnings("unchecked")
	@Override
	public void requestSuccess(ResponseResult responseResult) {
		super.requestSuccess(responseResult);
		ShowResult showResult = new JsonComment().parse(responseResult, this);
		
		switch(responseResult.requestCode){
		case GOOD://好评
			list_comment_good.addAll((List<CommentEntity>) showResult.list);
			goodAdapter.notifyDataSetChanged();
			break;
		case BAD://差评
			list_comment_bad.addAll((List<CommentEntity>) showResult.list);
			badAdapter.notifyDataSetChanged();
			break;
		
		}
	}


	/* (non-Javadoc)
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
	 */
	@Override
	public void onTabChanged(String tabId) {
		if(tabId.equals("tab2")&& firstBad ){//加载差评
			request(BAD);
			firstBad = false;
		}
	}
	
	
	
	
}
