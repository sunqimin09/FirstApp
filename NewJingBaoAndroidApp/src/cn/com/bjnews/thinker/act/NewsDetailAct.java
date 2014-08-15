package cn.com.bjnews.thinker.act;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import android.R.fraction;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import cn.com.bjnews.thinker.share.SinaShare;
import cn.com.bjnews.thinker.share.WinxinShare;
import cn.com.bjnews.thinker.utils.FileDown;
import cn.com.bjnews.thinker.utils.Mconstant;
import cn.com.bjnews.thinker.utils.Utils;
import cn.com.bjnews.thinker.view.MViewPager;
import cn.com.bjnews.thinker.view.ViewPagerImageView;

import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;

@SuppressLint("NewApi")
public class NewsDetailAct extends BaseAct implements OnClickListener,
		OnPageChangeListener, Response {

	private LinearLayout llImages, llRelateds;

	// private View viewImage;

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
	/** 全屏模式下的图片浏览？？？？？ */
	// private ViewPager viewPagerBig;

	private RelativeLayout rlViewPagerTop;

	private LinearLayout llViewPagerTopIcons;

	private ViewPagerAdapterMedia viewPagerAdapterMedia;

	private NewsEntity newsEntity;

	private String newsId;

	/** 本文章所属的 channelid */
	private int channelId = 0;

	/** 当前应用是否正在运行 */
	private boolean isRunning = false;

	private final static int RELATED_ID = 1;
	/** 图片id */
	private final static int VIEWPAGER_ITEM_ID = 2;

	private final static int IMG_ID = 3;

	private int selectedImg;

	private int selectedViewpager;

	private ImageLoader imgLoader;

	/***/
	private boolean IsPush = false;

	IWXAPI api;

	IWeiboShareAPI mWeiboShareAPI;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_detail);
		setActionBarLayout(R.layout.act_detail_title);
		initView();
		initData();
		api = WXAPIFactory.createWXAPI(this, WinxinShare.APP_KEY, true);
		api.registerApp(WinxinShare.APP_KEY);
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, SinaShare.APP_KEY);
		mWeiboShareAPI.registerApp();
	}

	@Override
	protected void onPause() {
		Runtime.getRuntime().gc();
		super.onPause();
		MobclickAgent.onPause(this);
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
		// viewPagerBig = (ViewPager)
		// findViewById(R.id.detail_act_fragment_viewpager_big);
		llViewPagerTopIcons = (LinearLayout) findViewById(R.id.detail_act_fragment_viewpager_small_ll);
		rlViewPagerTop = (RelativeLayout) findViewById(R.id.detail_act_fragment_viewpager_small_rl);

		// viewPagerTop = new MViewPager(this);
		viewPagerTop.setOnClickListener(this);
		viewPagerTop.setOnPageChangeListener(this);
		if (Utils.getSystemVersionNew()) {
			imgBack.setVisibility(View.INVISIBLE);
		} else {
			imgBack.setVisibility(View.VISIBLE);
		}
		resizeViewpager();
	}

	private void initData() {
		imgLoader = new ImageLoader(this, R.drawable.default_img, false);
		boolean temp = getIntent().getSerializableExtra("news") instanceof NewsEntity;
		// 是否是广播

		if (getIntent().getAction() != null
				&& getIntent().getAction().equals("brocastreceiver")) {
			IsPush = false;
		} else if (getIntent().getAction() != null
				&& getIntent().getAction().equals("pushReceiver")) {
			IsPush = true;
		}

		if (getIntent().hasExtra("channelId")) {
			channelId = getIntent().getIntExtra("channelId", 0);
			// Log.d("tag","news_id>>"+ getIntent().getIntExtra("news_id",0));
		}

		if (temp) {
			newsEntity = (NewsEntity) getIntent().getSerializableExtra("news");
		}
		// Log.d("tag",
		// "news-==content>-->"+newsEntity.content.length()+"<11111>");
		if (newsEntity != null) {
			// Log.d("tag", "news-==content>" + newsEntity.medias.size()
			// + "title->" + newsEntity.title);
			tvTitle.setText(newsEntity.title);
			tvData.setText(new Utils().formatDate(newsEntity.pubDate) + " "
					+ newsEntity.source);
			if (newsEntity.content == null || newsEntity.content.equals("")) {
				tvContent.setVisibility(View.GONE);
			} else {
				tvContent.setVisibility(View.VISIBLE);
				tvContent.setText(newsEntity.content);
			}

			// tvContent.setLetterSpacing(0);
			showViewPagerTop(newsEntity.medias);
			showPics(newsEntity.images);
			showRelated(newsEntity.relateds);
			// newsEntity.getMedias().size()

		}

	}

	private void resizeViewpager() {
		ViewGroup.LayoutParams params = viewPagerTop.getLayoutParams();
		params.width = CommonUtil.getScreenWidth(this);
		params.height = (int) (CommonUtil.getScreenWidth(this) - 20) * 2 / 3;
		viewPagerTop.setLayoutParams(params);

	}

	/**
	 * 
	 */
	private void showPics(List<MediaEntity> list) {
		if (list.size() == 0) {
			llImages.setVisibility(View.GONE);
			return;
		}
		llImages.setVisibility(View.VISIBLE);
		llImages.removeAllViews();
		TextView imageText = null;
		for (int i = 0; i < list.size(); i++) {
			View viewImage = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.act_detail_pics, null);
			image = (ViewPagerImageView) viewImage
					.findViewById(R.id.act_detail_pics_img);
			imageText = (TextView) viewImage
					.findViewById(R.id.act_detail_pics_tv);
			image.setId(IMG_ID);
			imgLoader.setIsBg(true);
			imgLoader.setImgWidth(CommonUtil.getScreenWidth(this) - 20);
			Utils.getMemory("detail");
			Runtime.getRuntime().gc();
			imgLoader.DisplayImage(list.get(i).pic, image, false);
			Utils.getMemory("detail-end");
			// image.setTag(i);
			image.setOnClickListener(this);
			// image.setImage
			imageText.setText(list.get(i).caption);
			// image.setTag(2,list.get(i).getPic());
			llImages.addView(viewImage);

		}
	}

	private FileDown down = new FileDown();

	/**
	 * 上部 媒体
	 */
	private void showViewPagerTop(ArrayList<MediaEntity> medias) {
		// if(medias.size()==0){//如果当前没有大图，显示缩略图
		// MediaEntity tempEntity = new MediaEntity();
		// tempEntity.pic = newsEntity.thumbnail;
		// tempEntity.flag =1;//图片
		// tempEntity.caption = "";
		// medias.add(tempEntity);
		// }
		if (medias.size() == 0) {
			rlViewPagerTop.setVisibility(View.GONE);
			return;
		}

		if (medias.size() == 1) {// 只有一个不显示下部位置tab
			llViewPagerTopIcons.setVisibility(View.GONE);
		} else {
			llViewPagerTopIcons.setVisibility(View.VISIBLE);
		}
		// caption
		if (medias.get(0).caption == null || medias.get(0).caption.equals("")) {
			tvViewpagerCaption.setVisibility(View.GONE);
		} else {
			tvViewpagerCaption.setVisibility(View.VISIBLE);
		}
		tvViewpagerCaption.setText(medias.get(0).caption);
		rlViewPagerTop.setVisibility(View.VISIBLE);
		ArrayList<View> views = new ArrayList<View>();
		View viewImage;
		ViewPagerImageView image = null;
		// TextView imageText = null;
		ImageView imageVideo = null;
		llViewPagerTopIcons.removeAllViews();
		ImageView ImgIcon;

		for (int i = 0; i < medias.size(); i++) {
			viewImage = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.act_detail_pic, null);
			image = (ViewPagerImageView) viewImage
					.findViewById(R.id.act_detail_pic_img);
			image.setBackgroundResource(R.drawable.default_img);
			ScaleType scaleType = ScaleType.MATRIX;
			image.setScaleType(scaleType);
			Runtime.getRuntime().gc();
			imgLoader.DisplayImage(medias.get(i).pic, image, false);
			// imgLoader.setImgWidth(new ImageUtils(this).getWidth());
			// imageText = (TextView)
			// viewImage.findViewById(R.id.act_detail_pic_tv);
			imageVideo = (ImageView) viewImage
					.findViewById(R.id.act_detail_video);
			if (medias.get(i).flag == 0) {// 视频
				imageVideo.setVisibility(View.VISIBLE);
			} else {
				imageVideo.setVisibility(View.GONE);
			}
			image.setId(VIEWPAGER_ITEM_ID);
			image.setOnClickListener(this);
			// image.setImage
			// imageText.setText(medias.get(i).caption);
			views.add(viewImage);

			/***/
			ImgIcon = new ImageView(this);
			ImgIcon.setImageResource(R.drawable.scroll_selected);
			llViewPagerTopIcons.addView(ImgIcon, i);
			// Log.d("tag","downFile-->"+medias.get(i).flag
			// +"'''"+down.isExit(medias.get(i).video ));
			if (medias.get(i).flag == 0
					&& down.isExit(medias.get(i).video) == null) {
				down.down(this, null, medias.get(i).video);
			}
		}
		llViewPagerTopIcons.setPadding(0, 10, 0, 0);
		Utils.setViewPagerIcon(llViewPagerTopIcons, 0);
		viewPagerAdapterMedia = new ViewPagerAdapterMedia(views);
		viewPagerTop.setAdapter(viewPagerAdapterMedia);
	}

	/**
	 * 像是相关链接
	 * 
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
		switch (view.getId()) {
		case R.id.back:
			//
			if (IsPush) {
				try {

					Mconstant.currentPageIndex = MainActivity
							.getPageIndex(channelId);
					if (!Utils.mainIsExit(this)) {
						Intent i = new Intent(NewsDetailAct.this,
								MainActivity.class);
						i.putExtra("channelId", channelId);
						NewsDetailAct.this.startActivity(i);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Log.e("tag", "error-->" + e);
					Intent i = new Intent(NewsDetailAct.this,
							MainActivity.class);
					i.putExtra("channelId", channelId);
					NewsDetailAct.this.startActivity(i);

				}

			}
			finish();

			break;

		case R.id.share:
			Log.d("tag", "api==>" + api);
//			new SinaShare().share(this, mWeiboShareAPI, newsEntity.title,
//					newsEntity.description, newsEntity.medias.get(0).pic,
//					newsEntity.weburl);
			// try {
			
//			 share();
//			shareTestInit();
			 shareBoardInit();
			break;
		case R.id.share_board_Friends:
			new WinxinShare().share(this, api, newsEntity.medias
					.get(0).pic, newsEntity.title, newsEntity.description, newsEntity.weburl,true);
			break;
		case R.id.share_board_Winxin://微信分享
			
			 new WinxinShare().share(this, api, newsEntity.medias
			.get(0).pic, newsEntity.title, newsEntity.description, newsEntity.weburl,false);
			break;
		case R.id.share_board_Sina:
			new SinaShare().share(this, mWeiboShareAPI, newsEntity.title,
					newsEntity.description, newsEntity.medias.get(0).pic,
					newsEntity.weburl);
			break;
		case R.id.share_board_Message:
			Uri smsToUri = Uri.parse( "smsto:" );  
		    Intent sendIntent =  new  Intent(Intent.ACTION_VIEW, smsToUri);  
		    sendIntent.putExtra( "sms_body" ,  newsEntity.title+newsEntity.weburl );  
		    sendIntent.setType( "vnd.android-dir/*" );  
		    if(newsEntity.medias.size()>0){
				FileCache fileCache = new FileCache(this);
				;
				sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileCache.getFile(newsEntity.medias.get(0).pic)));
			}
		   
		    startActivityForResult(sendIntent, 1002 );  
			break;
		case R.id.share_board_Email:
			Intent email = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
			
			if (newsEntity.medias.size() > 0) {
				email.setType("*/*");
				FileCache fileCache = new FileCache(this);
				ArrayList<Uri> picturesUriArrayList = new ArrayList<Uri>();
					picturesUriArrayList.add(Uri.fromFile(fileCache
							.getFile(newsEntity.medias.get(0).pic)));
					email.putParcelableArrayListExtra(Intent.EXTRA_STREAM,
						picturesUriArrayList);
				
			}else{
				email.setType("plain/text");
			}
			// 设置邮件默认地址
			// email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReciver);
			// 设置邮件默认标题
			email.putExtra(android.content.Intent.EXTRA_SUBJECT, newsEntity.title);
			// 设置要默认发送的内容
			email.putExtra(android.content.Intent.EXTRA_TEXT, newsEntity.description);
			// 调用系统的邮件系统
			startActivityForResult(Intent.createChooser(email, "请选择邮件发送软件"),
					1001);
			break;
			
		case R.id.act_detail_pic_tv:
			startActivity(new Intent(NewsDetailAct.this, ActPlay.class));
			break;
		case RELATED_ID:// 相关内容
			startActivity(new Intent(NewsDetailAct.this, ActWeb.class)
					.putExtra("url", view.getTag().toString()));
			break;
		case R.id.detail_act_fragment_viewpager_small://
			ArrayList<MediaEntity> temp1 = (ArrayList<MediaEntity>) newsEntity.medias
					.clone();
			temp1.addAll(newsEntity.images);
			Intent i = new Intent(NewsDetailAct.this, ActPlay.class);
			i.putExtra("selectedId", selectedViewpager);
			i.putParcelableArrayListExtra("medias", temp1);
			// Log.d("tag","temp1————》"+temp1.size());
			startActivity(i);
			break;
		case IMG_ID://
			int selectedImg = 0;
			for (int j = 0; j < newsEntity.images.size(); j++) {
				if (llImages.getChildAt(j).equals(view)) {
					selectedImg = j;
					break;
				}
			}
			// Log.d("tag","temp————1》"+selectedImg);
			selectedImg = newsEntity.medias.size() + selectedImg;
			ArrayList<MediaEntity> temp = (ArrayList<MediaEntity>) newsEntity.medias
					.clone();
			// Log.d("tag","temp————2》"+temp.size());
			temp.addAll(newsEntity.images);
			Intent i1 = new Intent(NewsDetailAct.this, ActPlay.class);
			i1.putExtra("selectedId", selectedImg);
			i1.putParcelableArrayListExtra("medias", temp);
			// Log.d("tag","temp————3》"+temp.size());
			startActivity(i1);
			break;
		}
	}

	/**
	 * 分享
	 */
	private void share() {
		Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);//
		intent.setType("*/*");// text/plain;image/*
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享");

		StringBuilder sb = new StringBuilder(newsEntity.title);
		sb.append(newsEntity.picUrl).append(newsEntity.weburl);

		intent.putExtra(Intent.EXTRA_TEXT, sb.toString().replace("null", ""));

		// 添加图片
		ArrayList<Uri> picturesUriArrayList = new ArrayList<Uri>();
		FileCache fileCache = new FileCache(this);
		for (int i = 0; i < newsEntity.medias.size(); i++) {
			// sb.append(newsEntity.medias.get(i).pic);
			picturesUriArrayList.add(Uri.fromFile(fileCache
					.getFile(newsEntity.medias.get(i).pic)));
		}
		// intent.setAction(Intent.ACTION_SEND_MULTIPLE);
		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,
				picturesUriArrayList);
		// intent.putExtra(Intent.EXTRA_TITLE, "tile");
		if (hasApplication(intent)) {
			startActivity(Intent.createChooser(intent, getTitle()));
		} else {
			Toast.makeText(this, "当前没有应用可以分享", Toast.LENGTH_SHORT).show();
		}

	}

	
	private final com.umeng.socialize.controller.UMSocialService mController = com.umeng.socialize.controller.UMServiceFactory
			.getUMSocialService("com.umeng.share");

	private void shareTestInit() {

		// 首先在您的Activity中添加如下成员变量
		// addWinXin(newsEntity);
		// addWinXinTest();
		mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
				SHARE_MEDIA.DOUBAN, SHARE_MEDIA.TENCENT);
		// 设置分享内容
		(mController).setShareContent(newsEntity.title + newsEntity.weburl);

		// // 设置分享图片, 参数2为图片的url地址

		if (newsEntity.medias.size() > 0) {
			UMImage umImage = new UMImage(this, newsEntity.medias.get(0).pic);
			umImage.setTargetUrl(newsEntity.weburl);
			umImage.setTitle(newsEntity.title + newsEntity.weburl);
			mController.setShareMedia(umImage);
		}

		mController.openShare(this, new SnsPostListener() {

			@Override
			public void onStart() {
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,
					SocializeEntity entity) {
				switch (eCode) {
				case 40000:// cancel
					// Toast.makeText(NewsDetailAct.this, "Cancel",
					// Toast.LENGTH_SHORT)
					// .show();
					break;
				case 200:
					Toast.makeText(NewsDetailAct.this, "分享成功",
							Toast.LENGTH_SHORT).show();
					break;
				default:
					Toast.makeText(NewsDetailAct.this, "分享失败",
							Toast.LENGTH_SHORT).show();
					break;
				}

			}
		});
	}

	

	private void shareBoardInit() {
		View item = View.inflate(this, R.layout.share_board, null);
		initDialog(item);
		LinearLayout ll = new LinearLayout(this);
		ll.addView(item);
		Dialog d = new Dialog(this);
		d.setTitle("分享到");
		d.setContentView(ll);
		d.getCurrentFocus();
		d.show();

	}

	private void initDialog(View view) {
		View FriendsView = view.findViewById(R.id.share_board_Friends);
		FriendsView.setOnClickListener(this);
		ImageView imgFriends = (ImageView) FriendsView
				.findViewById(R.id.umeng_socialize_shareboard_image);
		TextView tvFriends = (TextView) FriendsView
				.findViewById(R.id.umeng_socialize_shareboard_pltform_name);
		tvFriends.setText("朋友圈");
		imgFriends.setImageResource(R.drawable.selelctor_friends);

		View WinxinView = view.findViewById(R.id.share_board_Winxin);
		WinxinView.setOnClickListener(this);
		ImageView imgWinXin = (ImageView) WinxinView
				.findViewById(R.id.umeng_socialize_shareboard_image);
		TextView tvWinXin = (TextView) WinxinView
				.findViewById(R.id.umeng_socialize_shareboard_pltform_name);
		tvWinXin.setText("微信");
		imgWinXin.setImageResource(R.drawable.umeng_socialize_wechat);

		View SianView = view.findViewById(R.id.share_board_Sina);
		SianView.setOnClickListener(this);
		ImageView imgSina = (ImageView) SianView
				.findViewById(R.id.umeng_socialize_shareboard_image);
		TextView tvSina = (TextView) SianView
				.findViewById(R.id.umeng_socialize_shareboard_pltform_name);
		tvSina.setText("新浪");
		imgSina.setImageResource(R.drawable.umeng_socialize_sina_on);

		View MessageView = view.findViewById(R.id.share_board_Message);
		MessageView.setOnClickListener(this);
		ImageView imgMessage = (ImageView) MessageView
				.findViewById(R.id.umeng_socialize_shareboard_image);
		TextView tvMessage = (TextView) MessageView
				.findViewById(R.id.umeng_socialize_shareboard_pltform_name);
		tvMessage.setText("短信");
		imgMessage.setImageResource(R.drawable.umeng_socialize_sms_on);

		View EmailView = view.findViewById(R.id.share_board_Email);
		EmailView.setOnClickListener(this);
		ImageView imgEmail = (ImageView) EmailView
				.findViewById(R.id.umeng_socialize_shareboard_image);
		TextView tvEmail = (TextView) EmailView
				.findViewById(R.id.umeng_socialize_shareboard_pltform_name);
		tvEmail.setText("邮件");
		imgEmail.setImageResource(R.drawable.umeng_socialize_gmail_on);

	}

	/**
	 * @see {@link Activity#onNewIntent}
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		// 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
		// 来接收微博客户端返回的数据；执行成功，返回 true，并调用
		// {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
		mWeiboShareAPI.handleWeiboResponse(intent, this);
		Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
	}

	/**
	 * 接收微客户端博请求的数据。 当微博客户端唤起当前应用并进行分享时，该方法被调用。
	 * 
	 * @param baseRequest
	 *            微博请求数据对象
	 * @see {@link IWeiboShareAPI#handleWeiboRequest}
	 */
	@Override
	public void onResponse(BaseResponse baseResp) {
		switch (baseResp.errCode) {
		case WBConstants.ErrorCode.ERR_OK:
			Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
			break;
		case WBConstants.ErrorCode.ERR_CANCEL:
			Toast.makeText(this, "cancel", Toast.LENGTH_LONG).show();
			break;
		case WBConstants.ErrorCode.ERR_FAIL:
			Toast.makeText(this,
					"failed" + "Error Message: " + baseResp.errMsg,
					Toast.LENGTH_LONG).show();
			break;
		}
	}

	public boolean hasApplication(Intent intent) {
		PackageManager packageManager = getPackageManager();
		// 查询是否有该Intent的Activity
		List<ResolveInfo> activities = packageManager.queryIntentActivities(
				intent, 0);
		// activities里面不为空就有，否则就没有
		return activities.size() > 0;
	}

	@Override
	public void onTrimMemory(int level) {
		// Log.d("tag","trim---<>"+level);
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
		// Log.d("tag","pageselected"+arg0+"size"+newsEntity.medias.size());
		if (newsEntity.medias.get(arg0).caption == null
				|| newsEntity.medias.get(arg0).caption.equals("")) {
			tvViewpagerCaption.setVisibility(View.GONE);
		} else {
			tvViewpagerCaption.setVisibility(View.VISIBLE);
		}
		tvViewpagerCaption.setText(newsEntity.medias.get(arg0).caption);
		Utils.setViewPagerIcon(llViewPagerTopIcons, arg0);
	}

	private void setActionBarLayout(int layoutId) {
		ActionBar actionBar = getActionBar();
		if (null != actionBar) {
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayHomeAsUpEnabled(Utils.getSystemVersionNew());
			actionBar.setDisplayShowCustomEnabled(true);
			LayoutInflater inflator = (LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(layoutId, null);
			ActionBar.LayoutParams layout = new ActionBar.LayoutParams(
					android.app.ActionBar.LayoutParams.FILL_PARENT,
					android.app.ActionBar.LayoutParams.FILL_PARENT);
			actionBar.setCustomView(v, layout);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			if (IsPush) {
				try {
					Mconstant.currentPageIndex = MainActivity
							.getPageIndex(channelId);
					if (!Utils.mainIsExit(this)) {
						Intent i = new Intent(NewsDetailAct.this,
								MainActivity.class);
						i.putExtra("channelId", channelId);
						NewsDetailAct.this.startActivity(i);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Intent i = new Intent(NewsDetailAct.this,
							MainActivity.class);
					i.putExtra("channelId", channelId);
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
			if (IsPush) {
				try {
					Mconstant.currentPageIndex = MainActivity
							.getPageIndex(channelId);
					if (!Utils.mainIsExit(this)) {
						Intent i = new Intent(NewsDetailAct.this,
								MainActivity.class);
						i.putExtra("channelId", channelId);
						NewsDetailAct.this.startActivity(i);
					}
				} catch (Exception e) {
					e.printStackTrace();
					// Log.e("tag","error-->"+e);
					Intent i = new Intent(NewsDetailAct.this,
							MainActivity.class);
					i.putExtra("channelId", channelId);
					NewsDetailAct.this.startActivity(i);
				}
				finish();
				return true;
			}

		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

}
