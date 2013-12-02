package com.tellout.act;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.tellout.test.FragmentNo;
import com.tellout.test.FragmentOK;

public class FeedBackAct extends FragmentActivity{

//	private TabHost tabHost = null;
	
	private ViewPager viewPager = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback_act);
		initView();
	}

	List<Fragment> fragments;
	
	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.feedback_viewpager);
		fragments = new ArrayList<Fragment>();
		fragments.add(new FragmentOK());
		fragments.add(new FragmentNo());
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().add(new FragmentOK(), "1").commit();
		fm.beginTransaction().add(new FragmentNo(), "2").commit();
		
		myFragmentPagerAdapter adapter = new myFragmentPagerAdapter(fm);
		viewPager.setAdapter(adapter);
		
	}
	
	class myFragmentPagerAdapter extends FragmentPagerAdapter{

		public myFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.size();
		}
		
	}
	
	
	
}
