package com.example.msalary.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.msalary.R;
import com.example.msalary.adapter.MainCompanysAdapter;
import com.example.msalary.adapter.ViewPagerAdapter;
import com.example.msalary.entity.CompanyEntity;
import com.example.msalary.entity.RequestEntity;
import com.example.msalary.entity.ResponseResult;
import com.example.msalary.internet.InternetHelper;
import com.example.msalary.util.MConstant;
/**
 * 预测：能破一千行
 * @author sunqm
 * Create at:   2013-12-26 下午11:04:23 
 * TODO
 */
/**
 * 预测：能破一千行
 * @author sunqm
 * Create at:   2013-12-26 下午11:04:23 
 * TODO
 */
public class MainActivity extends BaseActivity implements OnClickListener, OnKeyListener{
	   private static final int PAGE1=0;
	    private static final int PAGE2=1;
	    private ViewPager viewPager;
	    private List<View> mListViews;// Tab页面
	    private Button tab1,tab2,tab1_2,tab2_2;
	    private GridView hotPositionList;
	    private ListView hotCompanyList;
	    private View tabView1,tabView2;
	    private ImageView exposure_iv;//匿名曝光工资
	    private TextView more_tv;//更多。。
//	    private TextView hotCompany_tv;
//	    private TextView companyMessage_tv;
	    private TextView exposure1,exposure2;
	    private EditText search_company_et;
	    private EditText search_position_et;
	    private MainCompanysAdapter companysAdapter = null;
	    /**是否被按下*/
	    private boolean isPushed = false;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initPager1View();//初始化每一个pagerView的组件
		initPager2View();
	}
	
	public void initView(){
		super.initView();
		findViewById(R.id.title_line).setVisibility(View.GONE);
		findViewById(R.id.back).setVisibility(View.GONE);
		tv_title.setText(getString(R.string.main_title));
		tab1=(Button) findViewById(R.id.company_ibtn_2);
		tab2=(Button) findViewById(R.id.position_ibtn_1);
		tab1_2=(Button)findViewById(R.id.company_ibtn_1);
		tab2_2=(Button)findViewById(R.id.position_ibtn_2);
		tab1.setOnClickListener(this);
		tab2.setOnClickListener(this);
		tab1_2.setOnClickListener(this);
		tab2_2.setOnClickListener(this);
		tab1_2.setVisibility(View.GONE);
		tab2_2.setVisibility(View.GONE);
		viewPager=(ViewPager)findViewById(R.id.viewPager);//ViewPager页面
		mListViews = new ArrayList<View>();//元素集合
    	LayoutInflater inflater = LayoutInflater.from(this);
    	tabView1=inflater.inflate(R.layout.company_tab, null);
    	tabView2=inflater.inflate(R.layout.position_tab, null);
    	exposure_iv=(ImageView)tabView1.findViewById(R.id.exposure_tv);
    	exposure_iv.setOnClickListener(this);
		exposure1=(TextView) tabView1.findViewById(R.id.exposure_tv1);
		exposure2=(TextView) tabView2.findViewById(R.id.exposure_tv2);
		exposure1.setOnClickListener(this);
		exposure2.setOnClickListener(this);
    	more_tv=(TextView)tabView1.findViewById(R.id.company_more_tv);
    	more_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this, HotAct.class);
				startActivity(intent);
			}
		});
    	mListViews.add(tabView1);
    	mListViews.add(tabView2);
    	
		viewPager.setAdapter(new ViewPagerAdapter(mListViews));
		viewPager.setCurrentItem(PAGE1);//设立当前页面
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				switch(position){
				case PAGE1:
					tab1.setVisibility(1);
					tab1_2.setVisibility(View.GONE);
					
					tab2_2.setVisibility(View.GONE);
					tab2.setVisibility(1);
					break;
				case PAGE2:
					tab2.setVisibility(View.GONE);
					tab1_2.setVisibility(1);
					tab1.setVisibility(View.GONE);
					tab2_2.setVisibility(1);
					break;
				default:
					break;
					
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	/**
	 * 初始化界面1信息
	 */
	private void initPager1View(){
		
		search_company_et=(EditText) tabView1.findViewById(R.id.search_company_et);
		search_company_et.setOnKeyListener(this);
		hotCompanyList=(ListView) tabView1.findViewById(R.id.hot_company_lv);
//		ArrayList<HashMap<String, String>> list1=new ArrayList<HashMap<String,String>>();
//		for(int i=0;i<5;i++){
//			HashMap<String, String> map=new HashMap<String, String>();
//			map.put("company", "华为");
//			map.put("message", "321条信息");
//			list1.add(map);			
//		}
		ArrayList<CompanyEntity> list = new ArrayList<CompanyEntity>();
		companysAdapter=new MainCompanysAdapter(this,list);
		hotCompanyList.setAdapter(companysAdapter);
	}

	
	/**
	 * 初始化界面2的信息
	 */
	private void initPager2View(){
		
		search_position_et=(EditText) tabView2.findViewById(R.id.search_position_et);
		search_position_et.setOnKeyListener(this);
		hotPositionList=(GridView)tabView2.findViewById(R.id.hotposition_gv);
		BaseAdapter adapter=new GridViewAdapter(this);
		hotPositionList.setAdapter(adapter);
	}
	
	
	
	/**
	 * 点击卡片事件。。。
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.exposure_tv1:
		case R.id.exposure_tv2:
		case R.id.exposure_tv:
			Intent intent0=new Intent(MainActivity.this, ExposureSalary.class);
			startActivity(intent0);
			break;     
		case R.id.company_ibtn_1:
			
			tab1.setVisibility(1);
			tab1_2.setVisibility(View.GONE);
			tab2_2.setVisibility(View.GONE);
			tab2.setVisibility(1);
			viewPager.setCurrentItem(PAGE1);
			break;
		case R.id.position_ibtn_1:
			tab2.setVisibility(View.GONE);
			tab1_2.setVisibility(1);
			tab1.setVisibility(View.GONE);
			tab2_2.setVisibility(1);
			viewPager.setCurrentItem(PAGE2);
			break;
		case R.id.search_company_bt://查公司
			searchCompany();
			break;
		case R.id.search_position_bt://查职位
			searchPosition();
			break;
		default:
			break;
		}
	}
	
	


	private boolean checkInput(String str){
		if(str.equals("")){
			Toast("搜点啥呢");
			return false;
		}
		return true;
	}

	
	/**
	 * 发起网络请求
	 * @param code
	 */
	private void request(int code){
		RequestEntity requestEntity = null;
		switch(code){
		case 0://热门公司
			requestEntity = new RequestEntity(this,MConstant.URL_COMPANY_SALARY_RANK);
			
			break;
		case 1://热门职位
			requestEntity = new RequestEntity(this,MConstant.URL_COMPANY_SALARY_RANK);
			
			break;
		}
		requestEntity.requestCode = code;
		new InternetHelper(this).requestThread(requestEntity, this);
	}
	
	
	
	@Override
	public void requestSuccess(ResponseResult responseResult) {
		switch(responseResult.requestCode){
		case 0://热门公司
			
			break;
		case 1://热门职位
			
			break;
		}
	}

	

	@Override
	protected void onPause() {
		super.onPause();
//		isPushed = false;
	}

	private void searchCompany(){
		String v1= search_company_et.getText().toString();
		if(checkInput(v1))
			startActivity(new Intent(MainActivity.this,SelectCompanyActivity.class).putExtra("key",v1));
	}
	
	private void searchPosition(){
		String value=search_position_et.getText().toString();
		if(checkInput(value))
			startActivity(new Intent(MainActivity.this,SelectPositionActivity.class).putExtra("key",value));
	}

//	/**
//	 * 
//	 * @author 主页热门公司的adapter
//	 *
//	 */
//	private class ListViewAdapter extends BaseAdapter{
//		private Context context;
//		private ArrayList<HashMap<String, String>> list;
//		public ListViewAdapter(Context context,ArrayList<HashMap<String, String>> list){
//			this.context=context;
//			this.list=list;
//		}
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return list.size();
//		}
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return list.get(position);
//		}
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			// TODO Auto-generated method stub
//			convertView=LayoutInflater.from(context).inflate(R.layout.hot_company_listitem, null);
//			hotCompany_tv=(TextView) convertView.findViewById(R.id.hot_company_textview);
//			companyMessage_tv=(TextView) convertView.findViewById(R.id.company_message_textview);
//			hotCompany_tv.setText(list.get(position).get("company"));
//			companyMessage_tv.setText(list.get(position).get("message"));
////			convertView.setOnClickListener(new OnClickListener() {
////				
////				@Override
////				public void onClick(View v) {
////					// TODO Auto-generated method stub
////					Intent intent=new Intent(MainActivity.this,CompanyAllPositionActivity.class);
////					startActivity(intent);
////					
////				}
////			});
//			return convertView;
//		}
//	}
	/**
	 * 
	 * @author 主页热门职位的adapter。
	 *
	 */
 private class GridViewAdapter extends BaseAdapter {  
         
         private Context context;  
           
         public GridViewAdapter(Context context) {  
             this.context = context;  
         }  
           
         int count = 9;  
   
         @Override  
         public int getCount() {  
             return count;  
         }  
   
         @Override  
         public Object getItem(int position) {  
             return position;  
         }  
   
         @Override  
         public long getItemId(int position) {  
             return position;  
         }  
   
         @Override  
         public View getView(int position, View convertView, ViewGroup parent) {  
             TextView result = new TextView(context);  
             result.setText("Item "+position);  
             result.setTextColor(Color.BLACK);  
             result.setTextSize(24);  
            // result.setLayoutParams(new AbsListView.LayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)));  
             result.setGravity(Gravity.CENTER);  
             result.setBackgroundColor(Color.parseColor("#FFFAF0")); //设置背景颜色  
             convertView=result;
             return convertView;  
         }  
           
     }



	/* (non-Javadoc)
	 * @see android.view.View.OnKeyListener#onKey(android.view.View, int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_ENTER){//修改回车键功能
			switch(v.getId()){
			case R.id.search_company_et://公司
//				if(!isPushed){
					searchCompany();
//					isPushed = true;
//				}
				break;
			case R.id.search_position_et://职位
//				if(!isPushed){//当前没有按下
					searchPosition();
//					isPushed = true;
//				}
				break;
			}
		}
		return false;
	}  
}
