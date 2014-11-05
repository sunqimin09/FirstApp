package com.sun.hair;

import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.sun.hair.act.PhotoAct;
import com.sun.hair.adapter.FamousAdapter;
import com.sun.hair.entity.FamousListEntity;
import com.sun.hair.service.FoodService;
import com.sun.hair.service.IRequestCallBack;
import com.sun.hair.utils.MConstant;

/**
 * �����б�
 * @author sunqm
 *
 */
public class FamousFragment extends Fragment implements IRequestCallBack, OnItemClickListener, OnClickListener{

	private PullToRefreshGridView grid;
	
	private FamousAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_famouse, null);
		initTitle(view);
		initView(view);
		return view;
	}

	private void initTitle(View view) {
		TextView tvTitle = (TextView) view.findViewById(R.id.act_title);
		view.setBackgroundResource(R.drawable.bg_top);
		tvTitle.setText("");
		
	}

	private void initView(View view) {
		grid = (PullToRefreshGridView) view.findViewById(R.id.frag_famous_grid);
		view.findViewById(R.id.frag_famous_add).setOnClickListener(this);
		view.findViewById(R.id.frag_famous_share).setOnClickListener(this);
		
		adapter = new FamousAdapter(getActivity());
		grid.setAdapter(adapter);
		grid.setMode(Mode.PULL_UP_TO_REFRESH);
		grid.setOnPullEventListener(new OnPullEventListener<GridView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<GridView> refreshView,
					State state, Mode direction) {
				if(state==State.RESET){
					
				}
			}
		});
		grid.setOnItemClickListener(this);
		entity = new FamousListEntity();
		request();
	}
	String currentPageIndex2="-2";
	
	FamousListEntity entity = null;
	
	private void request(){
		
		if((Integer.parseInt(currentPageIndex2)+1)*20<Integer.parseInt(entity.total)){//С�� �����������
			AjaxParams params = new AjaxParams();
			params.put("pageIndex",( Integer.parseInt(currentPageIndex2)+1)+"");
			new FoodService().request(getActivity(), MConstant.URL_FAMOUSES, params, this);
			getActivity().setProgressBarIndeterminateVisibility(true);
		}else{
			Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onSuccess(Object o) {
		getActivity().setProgressBarIndeterminateVisibility(false);
		Log.d("tag","onsuccess--show"+o);
		if(o instanceof FamousListEntity){
			entity = (FamousListEntity) o;
			
			adapter.setData(entity.list);
//			Toast.makeText(getActivity(), "onsuccess"+entity.list.size(), Toast.LENGTH_SHORT).show();
		}
		
	}

	@Override
	public void onFailed(String msg) {
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		startActivity(new Intent(getActivity(),PhotoAct.class).putExtra("photo", entity.list.get(arg2)));
		
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.frag_famous_add:
			break;
		case R.id.frag_famous_share:
			break;
		}
		
	}
	
	
}
