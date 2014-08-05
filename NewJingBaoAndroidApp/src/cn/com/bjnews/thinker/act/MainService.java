package cn.com.bjnews.thinker.act;

import java.io.File;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import cn.com.bjnews.thinker.actlogical.FragmentFirstLogical;
import cn.com.bjnews.thinker.actlogical.IFragmentFirst;
import cn.com.bjnews.thinker.db.DbHandler;
import cn.com.bjnews.thinker.entity.NewsListEntity;
import cn.com.bjnews.thinker.entity.RequestEntity;
import cn.com.bjnews.thinker.img.FileManager;
import cn.com.bjnews.thinker.internet.IRequestCallBack;
import cn.com.bjnews.thinker.internet.InternetHelper;

public class MainService extends Service{

	private final IBinder binder = new LocalBinder();

	public class LocalBinder extends Binder {
		MainService getService() {
			return MainService.this;
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		dbHandler =DbHandler .getInstance(this);
//		Log.d("tag","service--oncreate");
//		deleteImg();
	}

	
	

	private DbHandler dbHandler;
	
	public void update(int newsId,int readFlag){
		if(dbHandler !=null)
			dbHandler.update(newsId, readFlag);
	}
	
	/**
	 * 发起网络请求
	 * @param context
	 * @param requestEntity
	 * @param callBack
	 * @param timeOut
	 */
	public void request(Context context,RequestEntity requestEntity,IRequestCallBack callBack,int timeOut){
		new InternetHelper(context).requestThread(requestEntity, callBack,timeOut);
	}
	
	public void showData(final NewsListEntity locaListEntity,final String result,final IFragmentFirst iFragmentFirst){
		final FragmentFirstLogical logical = new FragmentFirstLogical(MainService.this);
		new Thread(){

			@Override
			public void run() {
				logical.doData(locaListEntity, result, iFragmentFirst);
			}
			
		}.start();
		
	}
	
	/**
	 * 删除没有用的图片
	 */
	private void deleteImg(){
		//1.查询到数据库中包含的图片的名称，
		List<String> imgs = dbHandler.queryAllImgName();
		Log.d("tag","imgs-->"+imgs.size());
		for (String string : imgs) {
			
		}
		
		//2.一一比较名称，如果不存在数据库中就删除
//		FileDown.getFileName("");
		File filePath = new File(FileManager.getSaveFilePath());
		
		if(filePath.exists()){//路径存在
			String[] fileNames = filePath.list();
			for (String string : fileNames) {//文件
				if(imgs.contains(string)){//存在，不用任何操作
					Log.d("tag","File-Exit"+string);
				}else{//不存在，删除该文件
					Log.d("tag","File-not-Exit"+string);
				}
				
			}
		}
	}
	
}
