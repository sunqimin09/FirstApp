package com.example.msalary.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.msalary.R;
import com.example.msalary.adapter.ViewPagerAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
/**
 * 预测：能破一千行
 * @author sunqm
 * Create at:   2013-12-26 下午11:04:23 
 * TODO
 */
public class MainActivity extends Activity implements OnClickListener{
	   private static final int PAGE1=0;
	    private static final int PAGE2=1;
	    private ViewPager viewPager;
	    private List<View> mListViews;// Tab页面
	    private ImageButton tab1,tab2,tab1_2,tab2_2;
	    private GridView hotPositionList;
	    private ListView hotCompanyList;
	    private View tabView1,tabView2;
	    private ImageView exposure_iv;//匿名曝光工资
	    private TextView more_tv;//更多。。
	    private TextView hotCompany_tv;
	    private TextView companyMessage_tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initPager1View();//初始化每一个pagerView的组件
		initPager2View();
	}
	private void initView(){
		tab1=(ImageButton) findViewById(R.id.company_ibtn_2);
		tab2=(ImageButton) findViewById(R.id.position_ibtn_1);
		tab1_2=(ImageButton)findViewById(R.id.company_ibtn_1);
		tab2_2=(ImageButton)findViewById(R.id.position_ibtn_2);
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
    	exposure_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this, ExposureSalary.class);
				startActivity(intent);
			}
		});
    	more_tv=(TextView)tabView1.findViewById(R.id.company_more_tv);
    	more_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this, HotActivity.class);
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
		hotCompanyList=(ListView) tabView1.findViewById(R.id.hot_company_lv);
		ArrayList<HashMap<String, String>> list1=new ArrayList<HashMap<String,String>>();
		for(int i=0;i<5;i++){
			HashMap<String, String> map=new HashMap<String, String>();
			map.put("company", "华为");
			map.put("message", "321条信息");
			list1.add(map);			
		}
		BaseAdapter adapter1=new ListViewAdapter(this, list1);
		hotCompanyList.setAdapter(adapter1);
	}
	/**
	 
	private void initPager1View(){
//		hotCompanyList=(ListView) findViewById(R.id.hot_company_lv);
//		hotCompanyList.addHeaderView(LayoutInflater.from(this).inflate(R.layout.listview_header, null));
		hotCompanyList=(ListView) tabView1.findViewById(R.id.hot_company_lv);
		hotCompanyList.addHeaderView(LayoutInflater.from(this).inflate(R.layout.listview_header, null));
		ArrayList<Map<String, String>> listItems=new ArrayList<Map<String,String>>();
		HashMap<String, String> map;
		for(int i=0;i<5;i++){
			map=new HashMap<String, String>();
			map.put("company", "华为");
			map.put("message", "100条信息");
			listItems.add(map);
		}
		SimpleAdapter smapt=new SimpleAdapter(this, listItems, R.layout.hot_company_listitem, new String[] {"company","message"}, new int[] {R.id.hot_company_textview,R.id.company_message_textview});
		hotCompanyList.setAdapter(smapt);
	}
	*/
	/**
	 * 初始化界面2的信息
	 */
	private void initPager2View(){
		hotPositionList=(GridView)tabView2.findViewById(R.id.hotposition_gv);
		BaseAdapter adapter=new GridViewAdapter(this);
		hotPositionList.setAdapter(adapter);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	/**
	 * 点击卡片事件。。。
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
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
//			String value = 
			startActivity(new Intent(MainActivity.this,SelectCompanyActivity.class).putExtra("key", ""));
			break;
		case R.id.search_position_bt:
			break;
		default:
			break;
		}
	}
	
	/**
	 * 
	 * @author 主页热门公司的adapter
	 *
	 */
	private class ListViewAdapter extends BaseAdapter{
		private Context context;
		private ArrayList<HashMap<String, String>> list;
		public ListViewAdapter(Context context,ArrayList<HashMap<String, String>> list){
			this.context=context;
			this.list=list;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView=LayoutInflater.from(context).inflate(R.layout.hot_company_listitem, null);
			hotCompany_tv=(TextView) convertView.findViewById(R.id.hot_company_textview);
			companyMessage_tv=(TextView) convertView.findViewById(R.id.company_message_textview);
			hotCompany_tv.setText(list.get(position).get("company"));
			companyMessage_tv.setText(list.get(position).get("message"));
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(MainActivity.this,CompanyAllPositionActivity.class);
					startActivity(intent);
					
				}
			});
			return convertView;
		}
	}
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
}
