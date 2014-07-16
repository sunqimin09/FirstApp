package cn.com.bjnews.thinker.act;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import cn.com.bjnews.thinker.db.DbHandler;

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
		Log.d("tag","service--oncreate");
	}


	private DbHandler dbHandler;
	
	public void update(int newsId,int readFlag){
		if(dbHandler !=null)
			dbHandler.update(newsId, readFlag);
	}
	
}
