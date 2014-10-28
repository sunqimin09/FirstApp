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

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;

public class WinxinShare {

	private boolean shareFriends = false;

	private static final int THUMB_SIZE = 150;

	public static final String APP_KEY = "wx7113cca33fd10b9e";
	
	/**
	 * 分享网页
	 * 
	 * @param context
	 * @param imagePath
	 * @param title
	 * @param description
	 * @param webUrl
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public void share(Context context, IWXAPI api, String imageUrl,
			String title, String description, String webUrl,boolean shareFriend) {
		this.shareFriends = shareFriend;
		Bitmap bmp = null;
		if (imageUrl != null) {// 有图时候传图
			Log.d("tag","filePath=="+new FileCache(context).getSavePath(imageUrl).indexOf("."));
			if(new FileCache(context).getSavePath(imageUrl).indexOf(".")>0){//如果包含. 即存在
				bmp = BitmapFactory.decodeFile(new FileCache(context).getSavePath(imageUrl));
			}else{//不存在。。读取网络的图片
				this.context = context;
				
				this.title = title;
				this.description = description;
				this.webUrl = webUrl;
				this.api = api;
				remoteImage(imageUrl);
				return;
			}
		} else {// 没有图，传应用标志
			bmp = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.ic_launcher);
		}
		
		shareDetail(context,api,bmp,title,description,webUrl);
		
	}
	
	private Context context;
	private IWXAPI api;
	String title;
	String description;
	String webUrl;
	
	private void shareDetail(Context context, IWXAPI api, Bitmap bmp,
			String title, String description, String webUrl){
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = webUrl;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		msg.description = description;
		Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
				THUMB_SIZE, true);
		bmp.recycle();

		msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = ShareUtils.buildTransaction("webpage");
		req.message = msg;
		req.scene = shareFriends ? SendMessageToWX.Req.WXSceneTimeline
				: SendMessageToWX.Req.WXSceneSession;
		boolean re = api.sendReq(req);
		Log.d("tag","share---end"+re);
		
	}
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			switch(msg.what){
			case 0://失败
				break;
			case 1://成功
				Bitmap bmp = (Bitmap) msg.obj ;
				shareDetail(context,api,bmp,title,description,webUrl);
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
