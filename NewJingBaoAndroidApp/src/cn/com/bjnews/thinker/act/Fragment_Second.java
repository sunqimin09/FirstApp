package cn.com.bjnews.thinker.act;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import cn.com.bjnews.newsroom.R;
import cn.com.bjnews.thinker.adapter.FragmentFirstAdapter;
import cn.com.bjnews.thinker.db.DbHandler;
import cn.com.bjnews.thinker.db.IDbCallBack;
import cn.com.bjnews.thinker.entity.NewsListEntity;
import cn.com.bjnews.thinker.view.IRefreshListner;
import cn.com.bjnews.thinker.view.MListView;

/**
 * 
 * @author sunqm
 * @version 创建时间：2014-8-7 下午2:12:58 TODO 提高上一版本的性能
 */
public class Fragment_Second extends Fragment implements OnItemClickListener,
		OnClickListener, IRefreshListner, IDbCallBack {

	/** 刷新控件 */
	private LinearLayout loadingBars;
	private ProgressBar loadingBarLeft;
	private ProgressBar loadingBarRight;
	private ProgressBar loadingBarIndeterminate;

	private MListView listview;
	
	
	private  FragmentFirstAdapter adapter;
	/**当前viewpager 页标签*/
	private int currenPageindex;
	/**当前频道*/
	private int currentChannelId;
	
	/**数据操作*/
	private DbHandler dbHandler;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.firstfragment, null);
		initView(view);
		initData();
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
	
	private void initView(View view){
		listview = (MListView) view.findViewById(R.id.fragment_first_lv);
		initListView(view);

		listview.initHeadView(R.layout.fragment_first_lv_header, this);
		adapter = new FragmentFirstAdapter(getActivity());
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		
		
	}
	
	private void initListView(View view) {
		// TODO Auto-generated method stub
		loadingBars = (LinearLayout) view
				.findViewById(R.id.fragment_first_loadingBars);
		loadingBarLeft = (ProgressBar) view
				.findViewById(R.id.fragment_first_loadingBarLeft);
		loadingBarRight = (ProgressBar) view
				.findViewById(R.id.fragment_first_loadingBarRight);
		loadingBarIndeterminate = (ProgressBar) view
				.findViewById(R.id.fragment_first_loadingBarInDeterminate);
		listview.setLoading(loadingBars, loadingBarLeft, loadingBarRight,
				loadingBarIndeterminate, this);
	}

	private void initData(){
		currenPageindex = getArguments().getInt("index");
		currentChannelId = getArguments().getInt("channelId");
		//readlocal(),
		dbHandler = DbHandler.getInstance(getActivity());
		adapter = new FragmentFirstAdapter(getActivity());
		listview.setAdapter(adapter);
		readLocal();
	}

	/**
	 * 读取本地缓存的数据
	 */
	private void readLocal(){
		dbHandler.readNewsList(this, currentChannelId);
	}
	
	private void requestRemote(){
		
	}
	
	
	/**
	 * 
	 */
	private void refreshView(){
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interruptPreRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ReadSuccess(NewsListEntity entity) {
		// TODO Auto-generated method stub
		
	}
	
	
}
