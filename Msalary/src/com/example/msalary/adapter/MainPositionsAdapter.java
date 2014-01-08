/**
 * 
 */
package com.example.msalary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 项目名称：营销移动智能工作平台 <br>
 * 创建时间：2013/11/24 <br>
 * 功能描述: 终端详情 <br>
 * 版本 V 1.0 <br>               
 * 修改履历 <br>
 * 日期      原因  BUG号    修改人 修改版本 <br>
 */
public class MainPositionsAdapter extends BaseAdapter{

	  private Context context;  
      
      public MainPositionsAdapter(Context context) {  
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
