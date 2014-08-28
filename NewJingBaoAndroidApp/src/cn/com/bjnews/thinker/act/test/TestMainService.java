package cn.com.bjnews.thinker.act.test;

import android.content.Context;
import cn.com.bjnews.thinker.entity.RequestEntity;
import cn.com.bjnews.thinker.internet.IRequestCallBack;
import cn.com.bjnews.thinker.internet.InternetHelper;
import cn.com.bjnews.thinker.utils.Mconstant;

/**
 * 
 * @author sunqm
 * @version 创建时间：2014-8-7 下午3:35:41
 * TODO TestMainAct  逻辑处理
 */
public class TestMainService {

	public void requestRemote(Context context,IRequestCallBack requestCallBack,String url){
		RequestEntity requestEntity = new RequestEntity(url);
		new InternetHelper(context).requestThread(requestEntity, requestCallBack,Mconstant.TIME_OUT);
	}
	
	
	
	
}
