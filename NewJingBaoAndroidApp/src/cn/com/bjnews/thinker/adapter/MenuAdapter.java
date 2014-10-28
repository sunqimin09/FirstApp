package cn.com.bjnews.thinker.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.bjnews.newsroom.R;
import cn.com.bjnews.thinker.entity.MenuEntity;


public class MenuAdapter extends BaseAdapter{

	private List<MenuEntity> list = new ArrayList<MenuEntity>();
	
	private LayoutInflater inflater;
	
	public MenuAdapter(Context context){
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public MenuAdapter(Context context,List<MenuEntity> list){
		this.list = list;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setData(List<MenuEntity> list){
		this.list = list;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;  
        if (convertView == null) {  
            holder = new ViewHolder();  
            convertView = inflater.inflate(R.layout.menu_item, null);  
            holder.tv_name = (TextView) convertView  
                    .findViewById(R.id.menu_item_content);  
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        }  
        holder.tv_name.setText(list.get(position).getName());
		return convertView;
	}
	
	static class ViewHolder {  
        TextView tv_name;  
        
    }  
}