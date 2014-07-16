package cn.com.bjnews.thinker.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import cn.com.bjnews.thinker.entity.MediaEntity;

/**
 * 详细页面中  媒体 适配器
 * @author sunqm
 * Create at:   2014-5-28 上午7:22:16 
 * TODO
 */
public class ViewPagerAdapterMedia extends PagerAdapter{

	private ArrayList<View> view = new ArrayList<View>();
	
	public ViewPagerAdapterMedia(ArrayList<View> list){
		this.view = list;
	}
	
	@Override
	public int getCount() {
		return view.size();
	}

	@Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
    

    @Override
    public void destroyItem(View container, int position, Object object) {
    	Log.d("tag","destroy-->"+position);
        ((ViewPager)container).removeView(view.get(position));
    }


    @Override
    public Object instantiateItem(View container, int position) {
    	Log.d("tag","instance-->"+position);
        ((ViewPager)container).addView(view.get(position));
        return view.get(position);
    }
	

}
