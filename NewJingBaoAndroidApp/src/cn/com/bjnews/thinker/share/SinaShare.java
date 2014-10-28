package cn.com.bjnews.thinker.share;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.com.bjnews.newsroom.R;
import cn.com.bjnews.thinker.img.FileCache;

import com.sina.weibo.sdk.api.BaseMediaObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.utils.Utility;

public class SinaShare {
	
	private static final int THUMB_SIZE = 150;
	
	public static final String APP_KEY  = "2869512966";      // 应用的APP_KEY
	public static final String REDIRECT_URL = "appimg.bjnews.com.cn";// 应用的回调页
	public static final String SCOPE =                 // 应用申请的高级权限
	"email,direct_messages_read,direct_messages_write,"
	+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
	+ "follow_app_official_microblog," + "invitation_write";

	public void share(Context context,IWeiboShareAPI mWeiboShareAPI,String title,String description,String imageUrl,String url){
		this.title = title;
		this.description = description;
		this.url = url;
		this.mWeiboShareAPI = mWeiboShareAPI;
		Bitmap bmp = getBmp(context,imageUrl);
		if(bmp==null){
			return;
		}
		send(mWeiboShareAPI,bmp);

	}

	String title;
	String description;
	String url;
	IWeiboShareAPI mWeiboShareAPI;
	
	private void send(IWeiboShareAPI mWeiboShareAPI,Bitmap bmp){
		WeiboMultiMessage weiboMessage = new WeiboMultiMessage();//初始化微博的分享消息
		
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();

		// 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）
		
		weiboMessage.mediaObject = getWebpageObj(title,description,bmp,url);

		// 2. 初始化从第三方到微博的消息请求
		// 用transaction唯一标识一个请求
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = weiboMessage;

		// 3. 发送请求消息到微博，唤起微博分享界面
		mWeiboShareAPI.sendRequest(request);
	}
	
	private BaseMediaObject getWebpageObj(String title,String description,Bitmap bmp,String url) {
		WebpageObject mediaObject = new WebpageObject();
		mediaObject.identify = Utility.generateGUID();
		mediaObject.title = title;
		mediaObject.description = description;

		// 设置 Bitmap 类型的图片到视频对象里
		Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
				THUMB_SIZE, true);
		bmp.recycle();
		mediaObject.setThumbImage(thumbBmp);
		mediaObject.actionUrl = url;
		mediaObject.defaultText = "Webpage 默认文案";
		return mediaObject;
	}

	private Bitmap getBmp(Context context,String imageUrl) {
		Bitmap bmp = null;
		if (imageUrl != null) {// 有图时候传图
			Log.d("tag",
					"filePath=="
							+ new FileCache(context).getSavePath(imageUrl)
									.indexOf("."));
			if (new FileCache(context).getSavePath(imageUrl).indexOf(".") > 0) {// 如果包含.
																				// 即存在
				bmp = BitmapFactory.decodeFile(new FileCache(context)
						.getSavePath(imageUrl));
			} else {// 不存在。。读取网络的图片
				remoteImage(imageUrl);
				return null;
			}
		} else {// 没有图，传应用标志
			bmp = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.ic_launcher);
		}
		return bmp;
	}
	
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			switch(msg.what){
			case 0://失败
				break;
			case 1://成功
				Bitmap bmp = (Bitmap) msg.obj ;
				send(mWeiboShareAPI, bmp);
				break;
			}
			
		}
		
	};
	
	private void remoteImage(final String imageUrl){
		new Thread(){

			@Override
			public void run() {
				try {
					Bitmap bmp = BitmapFactory.decodeStream(new URL(imageUrl).openStream());
					Message msg = new Message();
					msg.obj = bmp;
					msg.what = 1;
					handler.sendMessage(msg);
				} catch (MalformedURLException e) {
					handler.sendEmptyMessage(0);
					e.printStackTrace();
				} catch (IOException e) {
					handler.sendEmptyMessage(0);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}.start();
	}

}
