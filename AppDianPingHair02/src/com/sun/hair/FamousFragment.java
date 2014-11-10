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
import com.sun.hair.act.AddPicAct;
import com.sun.hair.act.PhotoAct;
import com.sun.hair.adapter.FamousAdapter;
import com.sun.hair.entity.FamousListEntity;
import com.sun.hair.service.FoodService;
import com.sun.hair.service.IRequestCallBack;
import com.sun.hair.utils.MConstant;

/**
 * 锟斤拷锟斤拷锟叫憋拷
 * @author sunqm
 *
 */
public class FamousFragment extends Fragment implements IRequestCallBack, OnItemClickListener, OnClickListener{

	private com.handmark.pulltorefresh.library.PullToRefreshGridView grid;
	
	private FamousAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		entity = new FamousListEntity();
		adapter = new FamousAdapter(getActivity());
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_famouse, null);//
		initTitle(view);
		initView(view);
		return view;
	}

	private void initTitle(View view) {
		TextView tvTitle = (TextView) view.findViewById(R.id.act_title_center);
		view.findViewById(R.id.act_title).setBackgroundResource(R.drawable.bg_top);
		tvTitle.setText("秀场");
		if(entity.list.size()==0){
			request();
		}
	}

	private void initView(View view) {
		grid = (PullToRefreshGridView) view.findViewById(R.id.frag_famous_grid);
		view.findViewById(R.id.frag_famous_add).setOnClickListener(this);
		view.findViewById(R.id.frag_famous_share).setOnClickListener(this);
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
		
		
	}
	String currentPageIndex2="-2";
	
	FamousListEntity entity = null;
	
	private void request(){
		AjaxParams params = new AjaxParams();
		if((Integer.parseInt(currentPageIndex2)+1)*20<Integer.parseInt(entity.total)){//小锟斤拷 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�			AjaxParams params = new AjaxParams();
			params.put("pageIndex",( Integer.parseInt(currentPageIndex2)+1)+"");
			new FoodService().request(getActivity(), MConstant.URL_FAMOUSES, params, this);
			getActivity().setProgressBarIndeterminateVisibility(true);
		}else{
			Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
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
		Log.d("tag","onFailed--show"+msg);
		Toast.makeText(getActivity(), "onFailed"+msg, Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		startActivity(new Intent(getActivity(),PhotoAct.class).putExtra("photo", entity.list.get(arg2)));
		
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.frag_famous_add:
			startActivity(new Intent(getActivity(),AddPicAct.class));
			break;
		case R.id.frag_famous_share:
			Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性

			intent.setType("text/plain"); // 分享发送的数据类型

//			intent.setPackage(packAgeName);

			intent.putExtra(Intent.EXTRA_SUBJECT, "分享"); // 分享的主题

			intent.putExtra(Intent.EXTRA_TEXT, "我在秀发型中看到了一个好看的发型,你也来一起玩吧"); // 分享的内容

			startActivity(Intent.createChooser(intent, "选择分享"));// 目标应用选择对话框的标题
			break;
		}
		
	}
	
	
}
