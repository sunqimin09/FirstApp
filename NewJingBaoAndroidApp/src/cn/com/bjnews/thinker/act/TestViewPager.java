package cn.com.bjnews.thinker.act;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TestViewPager extends FragmentActivity {

	
	
	@Override
	protected void onCreate(Bundle arg0) {
		
		super.onCreate(arg0);
	}

	class TestAdapter extends FragmentPagerAdapter {

		public TestAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return Fragment_First.newInstance(position);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return position + "";
		}

		@Override
		public int getCount() {
			return MainActivity.localSettingEntity.channelList.size();
		}
	}

}
