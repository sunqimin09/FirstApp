package com.tellout.util;
 
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.tellout.constant.MConstant;

public class ImageUpload {
	
	String path = Environment.getExternalStorageDirectory()+"/tellout/";
	
	String fileName = MConstant.USER_ID_VALUE+".jpeg";
	
	/**
	 * 上传图片
	 * @param bit
	 */
	public boolean uploadImage(String url,Bitmap bit){
		saveMyBitmap(fileName,bit);
		return scaleAndUploadImage(url,path+fileName);
	}
	
	public boolean scaleAndUploadImage(String url,String FilePpath){
		
		BitmapFactory.Options opt = new BitmapFactory.Options(); 
		opt.inSampleSize = 2;
		if(url.equals(MConstant.URL_IMAGE_ICON)){//头像
			opt.outHeight = 40;
			opt.outWidth = 40;
		}
		Bitmap bitmap = BitmapFactory.decodeFile(FilePpath,opt);
		File f = saveMyBitmap(MConstant.USER_ID_VALUE+".jpeg", bitmap);
		return uploadFile(url, f);
	}
	
	
	/* 上传文件至Server的方法 */
	private boolean uploadFile(String actionUrl,File uploadFile) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		try {
			URL url = new URL(actionUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			/* 设置DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"file1\";filename=\"" + MConstant.USER_ID_VALUE+".jpeg" + "\"" + end);
			ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			FileInputStream fStream = new FileInputStream(uploadFile);
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			fStream.close();
			ds.flush();
			/* 取得Response内容 */
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
//			Looper.prepare();
			/* 将Response显示于Dialog */
			Log.i("tag","上传成功" + b.toString().trim());
			/* 关闭DataOutputStream */
			ds.close();
			return true;
		} catch (Exception e) {
			Log.e("tag","上传失败" + e);
		}
		return false;
	}
	
	/**
	 * 将缩放完的图片保存
	 * @param filename
	 * @param bit
	 */
	private File saveMyBitmap(String filename,Bitmap bit){
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		File f = new File(path+filename);
		try {
			if(!f.exists())
				f.createNewFile();
			FileOutputStream out = new FileOutputStream(f);
			bit.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			bit.recycle();
			bit = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}
	
	
}
