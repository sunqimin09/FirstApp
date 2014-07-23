package cn.com.bjnews.thinker.act;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.com.bjnews.thinker.R;

public class Fragment_Second extends Fragment {

	public static Fragment_Second newInstance(int id) {
		Fragment_Second fragment = new Fragment_Second();

		fragment.id = id;

		return fragment;
	}

	private int id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.secondfragment, null);
//		Log.d("tag", "second");
		return view;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
