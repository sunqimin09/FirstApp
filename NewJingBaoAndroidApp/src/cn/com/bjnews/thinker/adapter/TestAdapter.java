package cn.com.bjnews.thinker.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import cn.com.bjnews.thinker.act.Fragment_First;
import cn.com.bjnews.thinker.entity.ChannelEntity;
import cn.com.bjnews.thinker.entity.NewsEntity;


public class TestAdapter extends FragmentPagerAdapter {

	
	ArrayList<ChannelEntity> list = new ArrayList<ChannelEntity>();
	
	public TestAdapter(FragmentManager fm) {
		super(fm);
	}

	public void setData(ArrayList<ChannelEntity> list){
		this.list = list;
		this.notifyDataSetChanged();
	}
	
	@Override
	public Fragment getItem(int position) {
		return Fragment_First.newInstance(position);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return list.get(position).name;
	}

	@Override
	public int getCount() {
		return list.size();
	}
}

