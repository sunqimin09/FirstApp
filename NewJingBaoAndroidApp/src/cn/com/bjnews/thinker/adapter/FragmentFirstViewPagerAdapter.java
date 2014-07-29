package cn.com.bjnews.thinker.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import cn.com.bjnews.thinker.R;
import cn.com.bjnews.thinker.entity.AdIntroEntity;
import cn.com.bjnews.thinker.img.ImageLoader;


public class FragmentFirstViewPagerAdapter extends PagerAdapter{

	ArrayList<View> views = new ArrayList<View>();
	
	private ArrayList<AdIntroEntity> medias = new ArrayList<AdIntroEntity>();
	
	private ImageLoader imgLoader = null;
	
	private Context context = null;
	
	public FragmentFirstViewPagerAdapter(Context context,ArrayList<View> views){
		this.context = context;
		this.views = views;
		if(imgLoader==null)
			imgLoader = new ImageLoader(context, R.drawable.default_img,false);
		
		
	}
	
	public void setMedias(ArrayList<AdIntroEntity> medias){
		Log.d("tag","设置显示的数据");
		this.medias = medias;
		imgLoader = new ImageLoader(context, R.drawable.default_img,false);
		this.notifyDataSetChanged();
	}
	
	public void setViews(ArrayList<View> views){
		this.views = views;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return views.size();
	}


	@Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
    

    @Override
    public void destroyItem(View container, int position, Object object) {
    	Log.d("tag","destroy-->"+position);
        ((ViewPager)container).removeView(views.get(position));
    }


    
    @Override
    public Object instantiateItem(View container, int position) {
    	Log.d("tag","instance-->"+position);
    	if(medias.size()>position && medias.get(position).picUrl!=null){
    		Log.d("tag","instance--开始下载>"+medias.get(position).picUrl);
    		imgLoader.DisplayImage(medias.get(position).picUrl,(ImageView) views.get(position),false);
    	}
        ((ViewPager)container).addView(views.get(position));
        return views.get(position);
    }
	
}
