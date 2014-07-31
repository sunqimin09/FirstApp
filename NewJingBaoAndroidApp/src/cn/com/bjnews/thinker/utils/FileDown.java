package cn.com.bjnews.thinker.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import cn.com.bjnews.thinker.entity.ResponseResult;
import cn.com.bjnews.thinker.img.FileManager;
import cn.com.bjnews.thinker.img.ImageLoader;
import cn.com.bjnews.thinker.internet.IRequestCallBack;
import cn.com.bjnews.thinker.internet.InternetHelper;


public class FileDown {

	
	private IRequestCallBack requestCallback;
	
	
	public String isExit(String url){
		String fileName = getFileName(url);
		File path = new File(FileManager.getVideoPath());
		if(!path.exists()){
			path.mkdir();
		}
		String[] files = path.list();
		for(String f :files){
			if(f.equals(fileName)){//存在
				return fileName;
			}
		}
		return null;
	}
	
	public void down(Context context,IRequestCallBack requestCallback,final String url){
		Log.d("tag","downFile1"+url);
		this.requestCallback = requestCallback;
		if(!InternetHelper.isInternetAvaliable(context)){
			requestCallback.requestFailedStr("无网络连接");
			return ;
		}
		Log.d("tag","downFile2"+url);
		new Thread(){

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				try {
					Log.d("tag","downFile3"+url);
					msg.obj = getFile(url);
					msg.what = 0;
				} catch (IOException e) {
					msg.obj = e.toString();
					msg.what = 1;
					e.printStackTrace();
				}
			}
			
		}.start();
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 0://正常
				if(requestCallback!=null){
					ResponseResult result = new ResponseResult();
					result.resultStr = msg.obj.toString() ;
					requestCallback.requestSuccess(result);
				}
				break;
			case 1://异常
				if(requestCallback!=null)
					requestCallback.requestFailedStr(msg.obj.toString());
				break;
			default:
					break;
			}
		}
		
	};
	
	
	public String getFile(String url) throws IOException{
		Log.d("tag","downFile"+url);
		File f = new File(FileManager.getVideoPath()+getFileName(url));
		URL imageUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) imageUrl
				.openConnection();
		conn.setConnectTimeout(30000);
		conn.setReadTimeout(30000);
		conn.setInstanceFollowRedirects(true);
		InputStream is = conn.getInputStream();
		OutputStream os = new FileOutputStream(f);
		ImageLoader.CopyStream(is, os);
		os.close();
		Log.d("tag","downFile视频下载完成");
		return getFileName(url);
		
	}
	
	public static String getFileName(String url){
		Log.d("tag","图片下载====00>getfileName"+url);
		if(null == url){
			return null;
		}
		Log.d("tag","图片下载====>getfileName"+url);
		StringBuilder sb = new StringBuilder(
				(url.hashCode()+"."+url.split("\\.")[url.split("\\.").length-1]));
		Log.d("tag","图片下载====>getfileName"+url);
		return sb.toString();
	}
	
	/**
	 * 
	 * 移动文件
	 * @param filePath
	 * @return
	 */
	public boolean moveFile(String filePath){
		File file = new File(filePath);
		Log.d("tag","filePath===>"+file.getPath());
		// 目标文件夹
//		File dir = new File(destPath);
//		// 将文件移动到另一个文件目录下
//		boolean success = file.renameTo(new File(dir, file.getName()));
//		return success;
		return false;
	}
	
	
}
