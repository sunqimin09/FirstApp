	package cn.com.bjnews.thinker.act;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.bjnews.thinker.R;
import cn.com.bjnews.thinker.adapter.ViewPagerAdapterMedia;
import cn.com.bjnews.thinker.entity.MediaEntity;
import cn.com.bjnews.thinker.entity.NewsEntity;
import cn.com.bjnews.thinker.entity.RelatedEntity;
import cn.com.bjnews.thinker.img.CommonUtil;
import cn.com.bjnews.thinker.img.FileCache;
import cn.com.bjnews.thinker.img.ImageLoader;
import cn.com.bjnews.thinker.utils.FileDown;
import cn.com.bjnews.thinker.utils.Mconstant;
import cn.com.bjnews.thinker.utils.Utils;
import cn.com.bjnews.thinker.view.MViewPager;
import cn.com.bjnews.thinker.view.ViewPagerImageView;


@SuppressLint("NewApi")
public class NewsDetailAct extends BaseAct implements OnClickListener, OnPageChangeListener {

	private LinearLayout llImages, llRelateds;

//	private View viewImage;

	private ViewPagerImageView image;

	private TextView tvViewpagerCaption;

	private TextView tvTitle;

	private TextView tvData;
	// 文章来源
	private TextView tvFrom;
	// 作者
	private TextView tvAuthor;

	private TextView tvContent;

	private MViewPager viewPagerTop;
	/**全屏模式下的图片浏览？？？？？*/
//	private ViewPager viewPagerBig;
	
	private RelativeLayout rlViewPagerTop;
	
	private LinearLayout llViewPagerTopIcons;
	
	private ViewPagerAdapterMedia viewPagerAdapterMedia;
	
	private NewsEntity newsEntity;

	private String newsId;
	
	/**本文章所属的 channelid*/
	private int channelId = 0;

	/**当前应用是否正在运行*/
	private boolean isRunning = false;
	
	private final static int RELATED_ID = 1;
	/**图片id*/
	private final static int VIEWPAGER_ITEM_ID = 2;
	
	private final static int IMG_ID = 3;
	
	private int selectedImg;
	
	private int selectedViewpager;
	
	private ImageLoader imgLoader;
	
	/***/
	private boolean IsPush = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_detail);
		setActionBarLayout(R.layout.act_detail_title);
		initView();
		initData();
		Log.d("tag", "getcalling->"+(getCallingActivity()==null)+getIntent().getAction());
//		if(getCallingActivity()==null){
//			IsPush = true;
//		}else{
//			IsPush = false;
//		}
	}

	private void initView() {
		newsId = getIntent().getStringExtra("id");
		ImageView imgBack = (ImageView) findViewById(R.id.back);
		llImages = (LinearLayout) findViewById(R.id.detail_act_fragment_pics_ll);
		llRelateds = (LinearLayout) findViewById(R.id.detail_act_fragment_related_ll);
		tvTitle = (TextView) findViewById(R.id.detail_act_fragment_title);
		tvData = (TextView) findViewById(R.id.detail_act_fragment_date);
		tvFrom = (TextView) findViewById(R.id.detail_act_fragment_from);
		tvAuthor = (TextView) findViewById(R.id.detail_act_fragment_author);
		tvContent = (TextView) findViewById(R.id.detail_act_fragment_content);
		viewPagerTop = (MViewPager) findViewById(R.id.detail_act_fragment_viewpager_small);
		tvViewpagerCaption = (TextView) findViewById(R.id.detail_act_fragment_viewpager_small_caption);
//		viewPagerBig = (ViewPager) findViewById(R.id.detail_act_fragment_viewpager_big);
		llViewPagerTopIcons = (LinearLayout) findViewById(R.id.detail_act_fragment_viewpager_small_ll);
		rlViewPagerTop = (RelativeLayout) findViewById(R.id.detail_act_fragment_viewpager_small_rl);
		
		
//		viewPagerTop = new MViewPager(this);
		viewPagerTop.setOnClickListener(this);
		viewPagerTop.setOnPageChangeListener(this);
		if(Utils.getSystemVersionNew()){
			imgBack.setVisibility(View.INVISIBLE);
		}else{
			imgBack.setVisibility(View.VISIBLE);
		}
		resizeViewpager();
	}
	 
	private void initData() {
		imgLoader = new ImageLoader(this, R.drawable.default_img,false);
		boolean temp = getIntent().getSerializableExtra("news") instanceof NewsEntity;
		//是否是广播
		
		if(getIntent().getAction()!=null && getIntent().getAction().equals("brocastreceiver")){
			IsPush = false;
		}else if(getIntent().getAction()!=null && getIntent().getAction().equals("pushReceiver")){
			IsPush = true;
		}
		
		if(getIntent().hasExtra("channelId")){
			channelId = getIntent().getIntExtra("channelId",0);
			Log.d("tag","news_id>>"+ getIntent().getIntExtra("news_id",0));
		}
			
		if (temp) {
			newsEntity = (NewsEntity) getIntent().getSerializableExtra("news");
		}
		Log.d("tag", temp+"news-==content>--" + newsEntity.toString());
		if (newsEntity != null) {
			Log.d("tag", "news-==content>" + newsEntity.medias.size()
					+ "title->" + newsEntity.title);
			tvTitle.setText(newsEntity.title);
			tvData.setText(new Utils().formatDate(newsEntity.pubDate));
			tvContent.setText(newsEntity.content);
//			tvContent.setLetterSpacing(0);
			showViewPagerTop(newsEntity.medias);
			showPics(newsEntity.images);
			showRelated(newsEntity.relateds);
			// newsEntity.getMedias().size()
		}
	}


	private void resizeViewpager(){
		ViewGroup.LayoutParams params = viewPagerTop.getLayoutParams();
		params.width = CommonUtil.getScreenWidth(this);
		params.height = (int)(CommonUtil.getScreenWidth(this)-20)*2/3;
		viewPagerTop.setLayoutParams(params);
		
	}

	/**
	 * 
	 */
	private void showPics(List<MediaEntity> list) {
		if(list.size()==0){
			llImages.setVisibility(View.GONE);
			return ;
		}
		llImages.setVisibility(View.VISIBLE);
		llImages.removeAllViews();
		TextView imageText =null;
		for (int i = 0; i < list.size(); i++) {
			View viewImage = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.act_detail_pics, null);
			image = (ViewPagerImageView) viewImage.findViewById(R.id.act_detail_pics_img);
			imageText = (TextView) viewImage.findViewById(R.id.act_detail_pics_tv);
			image.setId(IMG_ID);
			imgLoader.setIsBg(true);
			imgLoader.setImgWidth(CommonUtil.getScreenWidth(this)-20);
			imgLoader.DisplayImage(list.get(i).pic , image,false);
			
//			image.setTag(i);
			image.setOnClickListener(this);
			// image.setImage
			imageText.setText(list.get(i).caption );
//			image.setTag(2,list.get(i).getPic());
			llImages.addView(viewImage);
			
		}
	}

	private FileDown down =new FileDown();
	
	/**
	 * 上部 媒体 
	 */
	private void showViewPagerTop(ArrayList<MediaEntity> medias) {
		if(medias.size() ==0 ){
			rlViewPagerTop.setVisibility(View.GONE);
			return;
		}
		if(medias.size()==1){//只有一个不显示下部位置tab
			llViewPagerTopIcons.setVisibility(View.GONE);
		}else{
			llViewPagerTopIcons.setVisibility(View.VISIBLE);
		}
		//caption
		tvViewpagerCaption.setText(medias.get(0).caption);
		rlViewPagerTop.setVisibility(View.VISIBLE);
		ArrayList<View> views = new ArrayList<View>();
		View viewImage;
		ViewPagerImageView image = null;
//		TextView imageText = null;
		ImageView imageVideo = null;
		llViewPagerTopIcons.removeAllViews();
		ImageView ImgIcon;
		for(int i =0;i<medias.size();i++){
			viewImage = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.act_detail_pic, null);
			image = (ViewPagerImageView) viewImage.findViewById(R.id.act_detail_pic_img);
			image.setBackgroundResource(R.drawable.default_img);
			ScaleType scaleType = ScaleType.MATRIX;
			image.setScaleType(scaleType);
			imgLoader.DisplayImage(medias.get(i).pic ,image,false);
//			imgLoader.setImgWidth(new ImageUtils(this).getWidth());
//			imageText = (TextView) viewImage.findViewById(R.id.act_detail_pic_tv);
			imageVideo = (ImageView) viewImage.findViewById(R.id.act_detail_video);
			if(medias.get(i).flag  == 0 ){//视频
				imageVideo.setVisibility(View.VISIBLE);
			}else{
				imageVideo.setVisibility(View.GONE);
			}
			image.setId(VIEWPAGER_ITEM_ID);
			image.setOnClickListener(this);
			// image.setImage
//			imageText.setText(medias.get(i).caption);
			views.add(viewImage);
			
			/***/
			ImgIcon = new ImageView(this);
			ImgIcon.setImageResource(R.drawable.scroll_selected);
			llViewPagerTopIcons.addView(ImgIcon, i);
//			Log.d("tag","downFile-->"+medias.get(i).flag +"'''"+down.isExit(medias.get(i).video ));
			if(medias.get(i).flag  == 0 && down.isExit(medias.get(i).video) == null){
				down.down(this, null, medias.get(i).video );
			}
		}
		llViewPagerTopIcons.setPadding(0, 10, 0, 0);
		Utils.setViewPagerIcon(llViewPagerTopIcons, 0);
		viewPagerAdapterMedia = new ViewPagerAdapterMedia(views);
		viewPagerTop.setAdapter(viewPagerAdapterMedia);
	}

	/**
	 * 像是相关链接	
	 * @param relateds
	 */
	private void showRelated(ArrayList<RelatedEntity> relateds) {
		if (relateds.size() == 0) {
			llRelateds.setVisibility(View.GONE);
			return;
		}
		llRelateds.setVisibility(View.VISIBLE);
		llRelateds.setPadding(10, 8, 0, 8);
		Resources resource = (Resources) getResources();
		ColorStateList txtColor = (ColorStateList) resource
				.getColorStateList(R.color.related_txt);
		TextView tvTemp;
		for (int i = 0; i < relateds.size(); i++) {//
			tvTemp = new TextView(this);
			tvTemp.setText(relateds.get(i).getTitle());
			tvTemp.setTextColor(txtColor);
			tvTemp.setPadding(0, 10, 0, 10);
			tvTemp.setTextSize(18);
			tvTemp.setTag(relateds.get(i).getUrl());
			tvTemp.setId(RELATED_ID);
			tvTemp.setOnClickListener(this);
			llRelateds.addView(tvTemp);
		}
	}


	public void onClick(View view) {
		Log.d("tag","click-->1"+view.getId());
		if(!Mconstant.isClickAble){
			return;
		}
		Log.d("tag","click-->2"+Mconstant.isClickAble);
		new Utils().setViewUnable();
		switch (view.getId()) {
		case R.id.back:
			if(IsPush){
				try{
					Mconstant.currentPageIndex = MainActivity.getPageIndex(channelId);
				}catch(Exception e){
					e.printStackTrace();
					Log.e("tag","error-->"+e);
					Intent i = new Intent(NewsDetailAct.this,MainActivity.class);
					i.putExtra("channelId",channelId);
					NewsDetailAct.this.startActivity(i);
				}
				
			}
			finish();
			
			break;
		case R.id.share:
			share();
			break;
		case R.id.act_detail_pic_tv:
			startActivity(new Intent(NewsDetailAct.this, ActPlay.class));
			break;
		case RELATED_ID:// 相关内容
			startActivity(new Intent(NewsDetailAct.this, ActWeb.class)
					.putExtra("url", view.getTag().toString()));
			break;
		case R.id.detail_act_fragment_viewpager_small://
			ArrayList<MediaEntity> temp1 = newsEntity.medias;
			temp1.addAll(newsEntity.images);
			Intent i = new Intent(NewsDetailAct.this,ActPlay.class);
			i.putExtra("selectedId", selectedViewpager);
			i.putParcelableArrayListExtra("medias",temp1);
			Log.d("tag","temp1————》"+temp1.size());
			startActivity(i);
			break;
		case IMG_ID://
			int selectedImg = 0;
			for(int j = 0 ;j<newsEntity.images.size();j++){
				if(llImages.getChildAt(j).equals(view)){
					selectedImg = j;
					break;
				}
			}
			Log.d("tag","temp————1》"+selectedImg);
			selectedImg = newsEntity.medias.size() + selectedImg;
			ArrayList<MediaEntity> temp = (ArrayList<MediaEntity>) newsEntity.medias.clone();
			Log.d("tag","temp————2》"+temp.size());
			temp.addAll(newsEntity.images);
			Intent i1 = new Intent(NewsDetailAct.this,ActPlay.class);
			i1.putExtra("selectedId", selectedImg);
			i1.putParcelableArrayListExtra("medias",temp);
			Log.d("tag","temp————3》"+temp.size());
			startActivity(i1);
			break;
		}
	}
	

	/**
	 * 分享
	 */
	private void share() {
		Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);//
		intent.setType("*/*");//text/plain;image/*
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
		
		StringBuilder sb = new StringBuilder(newsEntity.title);
		sb.append(newsEntity.picUrl).append(newsEntity.weburl);
		
		
		intent.putExtra(Intent.EXTRA_TEXT,sb.toString().replace("null", ""));
		
		// 添加图片
		ArrayList<Uri> picturesUriArrayList = new ArrayList<Uri>();
		FileCache fileCache = new FileCache(this);
		for(int i =0;i<newsEntity.medias.size();i++){
//			sb.append(newsEntity.medias.get(i).pic);
			picturesUriArrayList.add(Uri.fromFile(fileCache.getFile(newsEntity.medias.get(i).pic)));
		}
//		intent.setAction(Intent.ACTION_SEND_MULTIPLE);
		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,
				picturesUriArrayList);
//		intent.putExtra(Intent.EXTRA_TITLE, "tile");
		if (hasApplication(intent)) {
			startActivity(Intent.createChooser(intent, getTitle()));
		} else {
			Toast.makeText(this, "当前没有应用可以分享", Toast.LENGTH_SHORT).show();
		}
		
		
	}

	
	
	
	public boolean hasApplication(Intent intent){    
        PackageManager packageManager = getPackageManager();    
        //查询是否有该Intent的Activity    
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);    
        //activities里面不为空就有，否则就没有    
        return activities.size() > 0 ; 
	}
	
	@Override
	public void onTrimMemory(int level) {
		Log.d("tag","trim---<>"+level);
		super.onTrimMemory(level);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
		
	}

	@Override
	public void onPageSelected(int arg0) {
		selectedViewpager = arg0; 
		Log.d("tag","pageselected"+arg0+"size"+newsEntity.medias.size());
		tvViewpagerCaption.setText(newsEntity.medias.get(arg0).caption);
		Utils.setViewPagerIcon(llViewPagerTopIcons, arg0);
	}
	
	private void setActionBarLayout( int layoutId ){
	    ActionBar actionBar = getActionBar( );
	    if( null != actionBar ){
	        actionBar.setDisplayShowHomeEnabled( false );
	        actionBar.setDisplayHomeAsUpEnabled(Utils.getSystemVersionNew());
	        actionBar.setDisplayShowCustomEnabled(true);
	        LayoutInflater inflator = (LayoutInflater)   this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View v = inflator.inflate(layoutId, null);
	        ActionBar.LayoutParams layout = new  ActionBar.LayoutParams(android.app.ActionBar.LayoutParams.FILL_PARENT, android.app.ActionBar.LayoutParams.FILL_PARENT);
	        actionBar.setCustomView(v,layout);
	    }
	}
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	        // TODO Auto-generated method stub
	        if(item.getItemId() == android.R.id.home)
	        {
	        	if(IsPush){
					try{
						Mconstant.currentPageIndex = MainActivity.getPageIndex(channelId);
					}catch(Exception e){
						e.printStackTrace();
						Intent i = new Intent(NewsDetailAct.this,MainActivity.class);
						i.putExtra("channelId",channelId);
						NewsDetailAct.this.startActivity(i);
					}
					
				}
	            finish();
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(IsPush){
				try{
					Mconstant.currentPageIndex = MainActivity.getPageIndex(channelId);
				}catch(Exception e){
					e.printStackTrace();
					Log.e("tag","error-->"+e);
					Intent i = new Intent(NewsDetailAct.this,MainActivity.class);
					i.putExtra("channelId",channelId);
					NewsDetailAct.this.startActivity(i);
				}
				
			}
            finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	 
	 

}
