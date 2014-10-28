package com.sun.hair;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LeftListFragment extends ListFragment implements
		OnItemClickListener {

	
	private String[] contents = {"精品商家","关于我们","明星"};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SampleAdapter adapter = new SampleAdapter(getActivity());
		for (int i = 0; i < 3; i++) {
			adapter.add(new SampleItem(contents[i],
					android.R.drawable.ic_menu_search));
		}
		setListAdapter(adapter);
		getListView().setOnItemClickListener(this);
	}

	private class SampleItem {
		public String tag;
		public int iconRes;

		public SampleItem(String tag, int iconRes) {
			this.tag = tag;
			this.iconRes = iconRes;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.row, null);
			}
//			ImageView icon = (ImageView) convertView
//					.findViewById(R.id.row_icon);
//			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView
					.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);
			return convertView;
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
//		getActivity().startActivity(new Intent(getActivity(),AboutAct.class));
		switch(arg2){
		case 0://在线预订
			 ((HomeAct) getActivity()).switchFragment(0);
			break;
		case 1://关于我们
			 ((HomeAct) getActivity()).switchFragment(1);
			break;
		case 2://优惠券
			 ((HomeAct) getActivity()).switchFragment(2);
			break;
		}
	}
}
