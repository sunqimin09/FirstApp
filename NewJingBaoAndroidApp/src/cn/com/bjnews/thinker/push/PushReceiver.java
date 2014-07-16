package cn.com.bjnews.thinker.push;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;
import cn.com.bjnews.thinker.R;
import cn.com.bjnews.thinker.act.MainActivity;
import cn.com.bjnews.thinker.act.NewsDetailAct;
import cn.com.bjnews.thinker.db.DbHandler;
import cn.com.bjnews.thinker.entity.NewsEntity;

import com.baidu.frontia.api.FrontiaPushMessageReceiver;

public class PushReceiver extends FrontiaPushMessageReceiver{

	private DbHandler dbHandler = null;
	
	@Override
	public void onBind(Context arg0, int arg1, String arg2, String arg3,
			String arg4, String arg5) {
		Log.d("tag","onreceiverbind->"+arg0+arg1+arg2+arg3+arg4+arg5);
		dbHandler = DbHandler.getInstance(arg0);
	}

	@Override
	public void onDelTags(Context arg0, int arg1, List<String> arg2,
			List<String> arg3, String arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onListTags(Context arg0, int arg1, List<String> arg2,
			String arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(Context arg0, String arg1, String arg2) {
		Log.d("tag","onreceiver-message->"+arg1+"<22>"+arg2);
//		Toast.makeText(arg0, arg1, Toast.LENGTH_SHORT).show();
		if(dbHandler ==null){
			dbHandler = DbHandler.getInstance(arg0);
		}
		
		try {
			PushMessage pushMessage = parse(arg1);
			Log.d("tag","a-id>"+pushMessage.a_id+"c_id>>"+pushMessage.c_id);
//			int channelId = 1;
//			int newsId = 29641;
			NewsEntity entity = dbHandler.selectNews(pushMessage.c_id, pushMessage.a_id);
			
			Log.d("tag","MESSAGE-->"+entity);
			
			if(!isRunning(arg0))
				showNotification(arg0,pushMessage,entity,isRunning(arg0));
			else
				sendBrocastReceive(arg0,pushMessage,entity);
		} catch (JSONException e) {
			Toast.makeText(arg0, "数据损坏:"+arg1, Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void onNotificationClicked(Context arg0, String arg1, String arg2,
			String arg3) {
		Log.d("tag","clicked-->");
		
	}

	@Override
	public void onSetTags(Context arg0, int arg1, List<String> arg2,
			List<String> arg3, String arg4) {
		Log.d("sun","settags-->"+arg1+arg2+arg3+arg4);
		
	}

	@Override
	public void onUnbind(Context arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	private void sendBrocastReceive(Context context,PushMessage pushMessage,NewsEntity entity) {
		Intent intent = new Intent();
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); 
		intent.setAction("pushMessage");
		
		intent.putExtra("target", am.getRunningTasks(1).get(0).topActivity.getClassName());
		     
		// 要发送的内容
		intent.putExtra("content", pushMessage.title);
		intent.putExtra("news", entity);
		Log.d("tag","news-==content33-->"+entity);
		intent.putExtra("channelId", pushMessage.a_id);
		// 发送 一个无序广播
		context.sendBroadcast(intent);
		Log.d("tag","brocast-reveive-->");
	}
	
	/**
	 * 解析
	 * @throws JSONException 
	 */
	private PushMessage parse(String result) throws JSONException{
		
		PushMessage pushMessage = new PushMessage();
		if(result==null){
			return pushMessage;
		}
		JSONObject data = new JSONObject(result);
		pushMessage.title = data.getString("title");
//		pushMessage.description = data.getString("description");
		if(data.has("open_type"))
			pushMessage.open_type = data.getInt("open_type");
		if(data.has("custom_content")){
			JSONObject content = data.getJSONObject("custom_content");
			if(content.has("a_id"))
				pushMessage.a_id = content.getInt("a_id");
			if(content.has("c_id"))
				pushMessage.c_id =content.getInt("c_id");
		}
		
		return pushMessage;
	}
	
	class PushMessage{
		String title;
		String description ="";
		
		int open_type = 2;
		/**栏目id*/
		int a_id =0;//若为null，则启动应用
		/**文章id*/
		int c_id = 0;
	}
	
	private boolean isRunning(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); 
		List<RunningTaskInfo> list = am.getRunningTasks(1); 
		for (RunningTaskInfo info : list) { 
		    if (info.topActivity.getPackageName().equals("cn.com.bjnews.thinker") && info.baseActivity.getPackageName().equals("cn.com.bjnews.thinker")) { 
		        return true;
		        //find it, break 
		    } 
		}  
		
		//test
		List<RunningTaskInfo> list1 = am.getRunningTasks(100); 
		for (RunningTaskInfo info : list1) { 
		    if (info.topActivity.getPackageName().equals("cn.com.bjnews.thinker") && info.baseActivity.getPackageName().equals("cn.com.bjnews.thinker")) { 
		    	Log.d("tag",info.baseActivity+"info--->"+info.topActivity.getClassName()+"<>"+info.numRunning);
		        //find it, break 
		    } 
		}  
		return false;
	}
	
	

	
	     
	    /**
	     * 在状态栏显示通知
	     */
	    private void showNotification(Context context,PushMessage pushMessage,NewsEntity entity,boolean isRunning){
	        // 创建一个NotificationManager的引用   
	        NotificationManager notificationManager = (NotificationManager)    
	            context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);   
	         
	        // 定义Notification的各种属性   
	        Notification notification =new Notification(R.drawable.ic_launcher,   
	        		pushMessage.title, System.currentTimeMillis()); 
	        //FLAG_AUTO_CANCEL   该通知能被状态栏的清除按钮给清除掉
	        //FLAG_NO_CLEAR      该通知不能被状态栏的清除按钮给清除掉
	        //FLAG_ONGOING_EVENT 通知放置在正在运行
	        //FLAG_INSISTENT     是否一直进行，比如音乐一直播放，知道用户响应
	        notification.flags |= Notification.FLAG_ONGOING_EVENT; // 将此通知放到通知栏的"Ongoing"即"正在运行"组中   
	        notification.flags |= Notification.FLAG_AUTO_CANCEL; // 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用   
	        notification.flags |= Notification.FLAG_SHOW_LIGHTS;   
	        //DEFAULT_ALL     使用所有默认值，比如声音，震动，闪屏等等
	        //DEFAULT_LIGHTS  使用默认闪光提示
	        //DEFAULT_SOUNDS  使用默认提示声音
	        //DEFAULT_VIBRATE 使用默认手机震动，需加上<uses-permission android:name="android.permission.VIBRATE" />权限
	        notification.defaults = Notification.DEFAULT_ALL; 
	        //叠加效果常量
	        //notification.defaults=Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND;
	        notification.ledARGB = Color.BLUE;   
	        notification.ledOnMS =5000; //闪光时间，毫秒
	         
	        // 设置通知的事件消息   
	        CharSequence contentTitle =pushMessage.title; // 通知栏标题   
	        CharSequence contentText =pushMessage.description; // 通知栏内容   
	        Intent notificationIntent = null;
	        if(entity!=null)
	        	notificationIntent =new Intent(context, NewsDetailAct.class); // 点击该通知后要跳转的Activity   
	        else {
	        	Log.d("tag","message--main");
	        	notificationIntent =new Intent(context, MainActivity.class); 
	        }
	        Log.d("tag","MESSAGE-111->"+entity);
	        notificationIntent.putExtra("news", entity);
	        notificationIntent.putExtra("channelId", pushMessage.c_id);
	        notificationIntent.putExtra("news_id", pushMessage.a_id);
	        notificationIntent.setAction("pushReceiver");
	        PendingIntent contentItent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
	        notification.setLatestEventInfo(context, contentTitle, contentText, contentItent);   
	         
	        // 把Notification传递给NotificationManager   
	        notificationManager.notify(0, notification);   
	    }
	
	    //删除通知    
	    private void clearNotification(Context context){
	        // 启动后删除之前我们定义的通知   
	        NotificationManager notificationManager = (NotificationManager) context 
	                .getSystemService(Context.NOTIFICATION_SERVICE);   
	        notificationManager.cancel(0);  
	 
	    }
	
	

}
