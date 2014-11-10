package com.sun.hair.act;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.sun.hair.BaseAct;
import com.sun.hair.R;
import com.sun.hair.service.IRequestCallBack;
import com.sun.hair.service.RequestCheckService;
import com.sun.hair.utils.AfinalBitmapTools;
import com.sun.hair.utils.MConstant;
import com.sun.hair.utils.PhotoUtils;
import com.sun.hair.utils.SpUtils;
import com.sun.hair.utils.Utils;
/**
 * 发表话题
 * @author sunqm
 *
 */
public class AddPicAct extends BaseAct implements IRequestCallBack, OnClickListener{

	private LinearLayout ll;
	
	private EditText et;
	
	private ImageView img;
	
	private static final int PHOTO_REQUEST_TAKE = 0;
	private final static int PHOTO_ALBUM = 1;
	private final static int PHOTO_REQUEST_CUT = 2;
	
	@Override
	public void initTitle() {
		setContentView(R.layout.act_add_pic);
		setTitle_("秀一下");
		setTitleBack();
		setLeftImg();
		setRightTv("提交");
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ll = (LinearLayout) findViewById(R.id.act_add_pic_ll);
		et = (EditText) findViewById(R.id.act_add_pic_input);
	}
	
	public void onClick(View view){
		
		switch(view.getId()){
		case R.id.act_title_left_img:
			finish();
			break;
		case R.id.act_title_right_tv://提交
			if(checkInput()){
				try {
					request();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			break;
		case R.id.act_add_pic_img://添加图片
			showPicDialog();
			break;
		case R.id.dialog_icon_get_from_album_tv://相册
			getFromAlbum();
			popIcon.dismiss();
			break;
		case R.id.dialog_icon_take_photo_tv:
			takePic();
			popIcon.dismiss();
			break;
		case R.id.dialog_icon_cancel_tv:
			popIcon.dismiss();
			break;
		
		}
	}
	
	

	private boolean checkInput() {
		if(et.getText().toString().equals("")){
			Toast("写点呗，亲");
			return false;
		}
		return true;
	}



	PopupWindow popIcon;
	
	/**
	 * 显示图片选择对话框
	 */
	private void showPicDialog(){
		View view = View.inflate(this, R.layout.dialog_icon, null);
		popIcon = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popIcon.setBackgroundDrawable(new BitmapDrawable());
		popIcon.setFocusable(true);
		popIcon.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
		view.findViewById(R.id.dialog_icon_cancel_tv).setOnClickListener(this);
		view.findViewById(R.id.dialog_icon_get_from_album_tv).setOnClickListener(this);
		view.findViewById(R.id.dialog_icon_take_photo_tv).setOnClickListener(this);
	}
	

	/**
	 * 提交
	 * @throws FileNotFoundException 
	 */
	private void request() throws FileNotFoundException{
		AjaxParams params = new AjaxParams();
		params.put("name",et.getText().toString());
		params.put("userid",new SpUtils(this).getId());
		File f = new File(Environment.getExternalStorageDirectory()+"/camera.jpg");
		Log.d("tag","file------->"+f.exists());
		if(f.exists())
		params.put("uploadFile", f);//
		new RequestCheckService().postImg( MConstant.URL_ADD_PIC_TEST, params,"multipart/form-data; boundary=HHHH", this);
	
	}

	@Override
	public void onSuccess(Object o) {
		
		
	}

	@Override
	public void onFailed(String msg) {
		// TODO Auto-generated method stub
		
	}
	
	private void getFromAlbum(){
		Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
//		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
		
//	    intent.addCategory(Intent.CATEGORY_OPENABLE);
	    intent.setType("image/*");
	    intent.putExtra("return-data", true);
	    startActivityForResult(intent, PHOTO_ALBUM);
	}

	Uri uri;
	
	File out;
	
	boolean hasImage = false;
	
	/**
	 * 开始拍照
	 */
	private void takePic() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		out = new File(AfinalBitmapTools.getImgPath(),
				"camera.jpg");
		if(!out.exists()){
			try {
				out.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Log.d("tag","take--out--length"+out.length());
		uri = Uri.fromFile(out);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(intent, PHOTO_REQUEST_TAKE);
	}
	
	/**
	 * 缩放图片
	 * @param uri
	 */
	private void startZoom(Uri muri){
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(muri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		out = new File(Environment.getExternalStorageDirectory(),
				"camera.jpg");
		
		Log.d("tag","take--out--zoom"+out.length());
		uri = Uri.fromFile(out);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", 98);
		intent.putExtra("outputY", 98);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}
	
	private void startZoomHight(String path){
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		out = new File(Environment.getExternalStorageDirectory(),
				"camera.jpg");
		Log.d("tag","take--out"+Uri.fromFile(new File(path)));
		uri = Uri.fromFile(out);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", 98);
		intent.putExtra("outputY", 98);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}
	
	
	/**
	 * 获取到 图片数据
	 * @param picdata
	 */
	private void getPic(Intent picdata) {
//		if(picdata==null){
//			return;
//		}
		Log.d("tag","getpic--intnetout="+out.length());
		Bundle bundle = null;
		if(picdata!=null)
			bundle = picdata.getExtras();
		Log.d("tag","getpic--bundle="+bundle);
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			Log.d("tag","pic==>"+photo);
			if (photo == null) {
				
			} else {
				hasImage = true;
				img.setImageBitmap(photo);
				new Utils().writeFile(photo, out);
				Log.d("tag","take--out--length22>>"+out.length());
				// 设置文本内容为 图片绝对路径和名字
			}
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("tag","onAct--result"+data+"request><"+requestCode);
		
		switch(requestCode){
		case PHOTO_REQUEST_TAKE://拍照
			Log.d("tag","onAct--result--take-"+uri);
			startZoom(uri);
			break;
		case PHOTO_ALBUM:
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
				Log.d("tag","take--out-4.4>"+data);
				Log.d("tag","take--out-4.4-uri>"+new PhotoUtils().getPath(this, data.getData()));
				startZoomHight(new PhotoUtils().getPath(this, data.getData()));
			} else {
				if(data!=null){
					Log.d("tag","cut==="+data);
					startZoom(data.getData());
				}
			}
//			getPic(data);
			break;
		case PHOTO_REQUEST_CUT://剪裁
			getPic(data);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


}
