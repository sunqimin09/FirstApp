package cn.com.bjnews.thinker.act;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import cn.com.bjnews.thinker.R;
import cn.com.bjnews.thinker.entity.MediaEntity;
import cn.com.bjnews.thinker.entity.ResponseResult;
import cn.com.bjnews.thinker.img.CommonUtil;
import cn.com.bjnews.thinker.img.DragImageView;
import cn.com.bjnews.thinker.img.FileManager;
import cn.com.bjnews.thinker.img.ImageLoader;
import cn.com.bjnews.thinker.internet.IRequestCallBack;
import cn.com.bjnews.thinker.utils.FileDown;
import cn.com.bjnews.thinker.utils.Utils;

import com.umeng.analytics.MobclickAgent;


public class ActPlay extends BaseAct implements OnPageChangeListener, IRequestCallBack{

	private ViewPager viewPager;
	
	private List<View> views = new ArrayList<View>();
	
	private LinearLayout llViewPagerIcon;
	
	private ArrayList<MediaEntity> medias = new ArrayList<MediaEntity>();
	
	private MViewPagerAdapter adapter;
	
	private ImageLoader imageLoader;
	
	private int currentPage = 0;
	
	private int window_width, window_height;// 控件宽度
	private int state_height;// 状态栏的高度

	private ViewTreeObserver viewTreeObserver;
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_play);
		initView();
		initData();
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.act_play_viewpager);
		llViewPagerIcon = (LinearLayout) findViewById(R.id.act_play_viewpager_ll);
		viewPager.setOnPageChangeListener(this);
	}
	
	private void initData() {
		down = new FileDown();
		imageLoader = new ImageLoader(this, R.drawable.default_img, false);
		//所有数据和当前所在位置
		medias = getIntent().getParcelableArrayListExtra("medias");
		int selectedId = getIntent().getIntExtra("selectedId", 0);
//		Log.d("tag","temp2-->"+medias.size());
		View view;
//		final DragImageView imageView;
		VideoView videoView ;
		//下部位置标
		ImageView ImgIcon;
		llViewPagerIcon.removeAllViews();
		for(int i =0;i<medias.size();i++){
			view = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.view_pager_item, null); 
			final DragImageView imageView = (DragImageView) view.findViewById(R.id.viewpager_item_img);
			videoView = (VideoView) view.findViewById(R.id.viewpager_video);
//			Log.d("tag","videoView-flag->"+medias.get(i).flag);
			if(medias.get(i).flag ==0){//视频
				down.isExit("");
//				Log.d("tag","videoView-url->"+medias.get(i).video);
				initVideo(videoView);
				videoView.setVisibility(View.VISIBLE);
				videoView.setTag(medias.get(i).video);
				imageView.setVisibility(View.GONE);
			}else{//图片h
				imageView.setBackgroundResource(R.drawable.default_img);
				ScaleType scaleType = ScaleType.MATRIX;
				imageView.setScaleType(scaleType);
				
//				Log.d("tag","")
//				imageLoader.setImgWidth(new ImageUtils(this).getWidth());
				imageLoader.setIsBg(true);
				imageLoader.setMaxSreen(true);
				imageLoader.setImgWidth(CommonUtil.getScreenWidth(this));
				imageLoader.DisplayImage(medias.get(i).pic, imageView, false);
				imageLoader.setScale(true);
				imageView.setmActivity(this);
				imageView.setVisibility(View.VISIBLE);
				viewTreeObserver = imageView.getViewTreeObserver();
				viewTreeObserver
						.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

							@Override
							public void onGlobalLayout() {
								if (state_height == 0) {
									// 获取状况栏高度
									Rect frame = new Rect();
									getWindow().getDecorView()
											.getWindowVisibleDisplayFrame(frame);
									state_height = frame.top;
									imageView.setScreen_H(window_height-state_height);
									imageView.setScreen_W(window_width);
								}

							}
						});
			}
//			Log.d("tag","imageWidth:>"+imageView.getWidth()+"Height:"+imageView.getHeight());
			views.add(view);
			
			ImgIcon = new ImageView(this);
			ImgIcon.setImageResource(R.drawable.scroll_selected);
			llViewPagerIcon.addView(ImgIcon, i);
		}
		Utils.setViewPagerIcon(llViewPagerIcon, 0);
//		Toast.makeText(this, "media--size"+llViewPagerIcon.getChildCount(), Toast.LENGTH_SHORT).show();
		adapter = new MViewPagerAdapter(views);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(selectedId);
		
		
		
	}
	
	
	private void initVideo(VideoView videoView){
		 MediaController mc = new MediaController(this);
		 
		 videoView.setMediaController(mc);
		 mc.setMediaPlayer(videoView);
		 videoView.requestFocus();
	}
	
	private void playVideoUrl(VideoView videoView,String url){
		if(down.isExit(url)==null){//不存在 下载，
			down.down(this, this, url);
			Toast.makeText(this, "视频加载中请稍后", Toast.LENGTH_SHORT).show();
		}else{//存在  直接播放
			playVideo(videoView, FileManager.getVideoPath()+down.getFileName(url));
		}
	}
	
	private void playVideo(VideoView videoView,String filepath){
//		Log.d("tag","playVideo-->");
//		name = Mconstant.LOCAL_FILE_PATH + name;
		videoView.setVideoPath(filepath);
		videoView.requestFocus();
		videoView.start();
	}
	
	private void stopVideo(VideoView videoView){
		if(videoView!=null&& videoView.isPlaying()){
			videoView.stopPlayback();
		}
		
	}
	
	private FileDown down;
	
	
	class MViewPagerAdapter extends PagerAdapter{
		
		List<View> views = new ArrayList<View>();
		
		public MViewPagerAdapter(List<View> views){
			this.views = views;
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
			((ViewPager) container).removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
//			Log.d("tag","instantiateItem-->"+position);
			((ViewPager) container).addView(views.get(position));
			return views.get(position);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	/**上一次正在播放的*/
	private VideoView lastVideoView = null;
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
         super.onConfigurationChanged(newConfig);
//         Log.d("tag","configchanged-->");
//          if(this.getResources().getConfiguration().orientation ==Configuration.ORIENTATION_LANDSCAPE) {
//        	  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//               // land donothing is ok
//          } else if(this.getResources().getConfiguration().orientation ==Configuration.ORIENTATION_PORTRAIT) {
//               // port donothing is ok
//        	  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_ORIENTATION_PORTRAIT);
//          }
    } 
	
	@Override
	public void onPageSelected(int arg0) {
		currentPage = arg0;
//		Log.d("tag","pageSelected-->"+arg0);
		Utils.setViewPagerIcon(llViewPagerIcon, arg0);
		stopVideo(lastVideoView);
//		Log.d("tag","pageSelected--22>"+medias.get(arg0).flag);
		if(medias.get(arg0).flag==0){//视频
			VideoView videoView = (VideoView)views.get(arg0).findViewWithTag(medias.get(arg0).video);
			if(down.isExit( medias.get(arg0).video)==null){
				Toast.makeText(this, "视频加载中请稍后", Toast.LENGTH_SHORT).show();
				return;
			}
//			Log.d("tag","pageSelected--33>"+down.isExit(medias.get(arg0).video));
//			initVideo(videoView);
			playVideoUrl(videoView, medias.get(arg0).video);
			lastVideoView = videoView;
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			return;
		}else{//图片
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		
	}

	@Override
	public void requestSuccess(ResponseResult responseResult) {
		medias.get(currentPage).video = (responseResult.resultStr);
		if (medias.get(currentPage).flag  == 0) {// 视频
			VideoView videoView = (VideoView) viewPager.getChildAt(currentPage)
					.findViewWithTag(medias.get(currentPage).video);
			if (down.isExit(medias.get(currentPage).video ) == null) {
				Toast.makeText(this, "视频加载中请稍后", Toast.LENGTH_SHORT).show();
				return;
			}
			playVideoUrl(videoView, medias.get(currentPage).video );
		}
	}

	@Override
	public void requestFailedStr(String str) {
		Toast.makeText(this,str, Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void request(int timeOut) {
		// TODO Auto-generated method stub
		
	}

}
