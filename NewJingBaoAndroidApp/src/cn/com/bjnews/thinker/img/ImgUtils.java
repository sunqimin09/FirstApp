package cn.com.bjnews.thinker.img;

import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.Telephony.Mms.Draft;
import android.widget.ImageView;

/**
 * 图片工具类
 * @author sunqm
 *
 */
public class ImgUtils {

	/**
	 * 加载本地数据到内存
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap getBitmap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}
	
	@SuppressLint("NewApi")
	public static void showBackground(ImageView img,int resId){
		InputStream is = img.getContext().getResources().openRawResource(resId);
		Drawable d = Drawable.createFromStream(is, null);
		img.setBackground(d);
	}
	
	

}
