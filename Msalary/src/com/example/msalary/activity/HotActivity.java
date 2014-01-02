package com.example.msalary.activity;

import com.example.msalary.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
/**
 * 热门职位以及热门公司界面
 * @author Administrator
 *
 */
public class HotActivity extends Activity {
	
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    	setContentView(R.layout.hot_main1);
    	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.hot_title);
    	GridView gridView1=(GridView) findViewById(R.id.gridlayout_num1);
    	GridView gridView2=(GridView) findViewById(R.id.gridlayout_num2);
    	GridView gridView3=(GridView) findViewById(R.id.gridlayout_num3);
    	GridView gridView4=(GridView) findViewById(R.id.gridlayout_num4);
    	BaseAdapter adapter1=new GridViewAdapter(this);
    	gridView1.setAdapter(adapter1);
    	gridView1.setAdapter(adapter1);
    	gridView1.setAdapter(adapter1);
    	gridView1.setAdapter(adapter1);
//    	String[] chars1=new String[]{
//    			"电话销售","网络销售","3",
//    			"4","5","6"
//    	};
//    	for(int i=0;i<chars1.length;i++){
//    		TextView tv=new TextView(this);
//    		tv.setText(chars1[i]);
//    		tv.setTextSize(23);
//    		tv.setBackgroundColor(Color.WHITE);
//    		tv.setGravity(Gravity.CENTER);
//    	
//    		GridLayout.Spec rowSpec=GridLayout.spec(i/3+1);
//    		GridLayout.Spec columSpec=GridLayout.spec(i%3);
//    		GridLayout.LayoutParams params=new GridLayout.LayoutParams(rowSpec,columSpec);
//    		params.setGravity(Gravity.FILL);
//    		gridlaout1.addView(tv,params);
//    	}
    }
     /**
      * 给GridView加数据，自定义Adapter
      * @author Administrator
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
             result.setBackgroundColor(Color.WHITE); //设置背景颜色  
             convertView=result;
             return convertView;  
         }  
           
     }  
}
