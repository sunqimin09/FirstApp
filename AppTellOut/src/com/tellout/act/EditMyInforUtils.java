package com.tellout.act;

import com.tellout.constant.MConstant;
import com.tellout.util.ImageUpload;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.provider.MediaStore;


public class EditMyInforUtils {
	
	private EditMyInforAct context ;
	
	public EditMyInforUtils(EditMyInforAct context){
		this.context = context;
	}
	
	/**
	 * 打开相册
	 */
	public void openAlbum(){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		intent.putExtra("return-data", true);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);
		context.startActivityForResult(intent,2);
	}
	
	/**
	 * 拍照
	 */
	public void tokePhoto(){
		Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		it.putExtra("crop", "true");
		it.putExtra("aspectX", 1);
		it.putExtra("aspectY", 1);
		it.putExtra("outputX", 100);
		it.putExtra("outputY", 100);
		context.startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);
	}
	
	public void uploadImage(final Bitmap bit,final Handler handler){
		new Thread(){

			@Override
			public void run() {
				int result = new ImageUpload().uploadImage(MConstant.URL_IMAGE_ICON,bit)?1:0;
				handler.sendEmptyMessage(result);
			}
			
		}.start();
		
	}
}
