package cn.com.bjnews.thinker.actlogical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.com.bjnews.thinker.db.DbHandler;
import cn.com.bjnews.thinker.entity.AdIntroEntity;
import cn.com.bjnews.thinker.entity.NewsEntity;
import cn.com.bjnews.thinker.entity.NewsListEntity;
import cn.com.bjnews.thinker.json.JsonNewsList;

public class FragmentFirstLogical {
	
	private NewsListEntity locaListEntity = null;
	
	private DbHandler dbHandler = null;
	
	/**列表显示的数据*/
	private List<NewsEntity> list = new ArrayList<NewsEntity>();
	/**新闻*/
	private List<AdIntroEntity> adsNew = new ArrayList<AdIntroEntity>();
	
	public FragmentFirstLogical(Context context){
		if(context!=null)
			dbHandler = DbHandler.getInstance(context);
	}
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 0:
				HashMap m= (HashMap) msg.obj;
				((IFragmentFirst) m.get("interface")).showData( m.get("news"),
						 m.get("ads"),m.get("headEntity"),m.get("localListEntity"));
				break;
			}
			
		}
		
	};
	
	/**
	 * 处理更新到的数据
	 * @param locaListEntity
	 * @param result
	 */
	public void doData(NewsListEntity locaListEntity,String result,IFragmentFirst iFragmentFirst){
		NewsListEntity remoteListEntity = JsonNewsList
				.parse(result);
		this.locaListEntity = locaListEntity;
		if (locaListEntity != null && remoteListEntity.equal(locaListEntity)) {// 不更新
			iFragmentFirst.noUpdate();
			notUpdate();																// &&
			//
		} else {// 更新 本地没数据，更新，本地有数据，但是时间不一致，更新
			doUpdate(remoteListEntity,iFragmentFirst);
			
		}
	}
	
	/**
	 * 更新本地数据库，并更新页面
	 */
	public void doUpdate(final NewsListEntity remoteListEntity,final IFragmentFirst iFragmentFirst){
		
		new Thread(){

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				locaListEntity = copy(remoteListEntity);
				updateLocalData(locaListEntity);
				Message msg = new Message();
				msg.what = 0;
				HashMap map = new HashMap<String, List<?>>();
				map.put("news", getNews(locaListEntity));
				map.put("ads", getAds(locaListEntity));
				map.put("headEntity", getHeadEntity(locaListEntity));
				map.put("localListEntity", locaListEntity);
				map.put("interface", iFragmentFirst);
				msg.obj = map;
				handler.sendMessage(msg);
			}

			private NewsEntity getHeadEntity(NewsListEntity locaListEntity) {
				if (locaListEntity.getNewsList() != null
						&& locaListEntity.getNewsList().size() > 0)
					return locaListEntity.getNewsList().get(0);
				return null;
			}
			
		}.start();
		
//		iFragmentFirst.showData(getNews(locaListEntity), getAds(locaListEntity));
//		doPush();
	}
	
	
	
	private List<AdIntroEntity> getAds(NewsListEntity locaListEntity){
		adsNew.clear();
		
		if (locaListEntity.getNewsList().size() > 0) {
			
			AdIntroEntity ad = new AdIntroEntity();
			ad.id = (locaListEntity.getNewsList().get(0).id + "");
			ad.picUrl = locaListEntity.getNewsList().get(0).medias.size() > 0 ? locaListEntity
					.getNewsList().get(0).medias.get(0).pic : locaListEntity
					.getNewsList().get(0).thumbnail;
			ad.caption = locaListEntity.getNewsList().get(0).title;
			adsNew.add(ad);
			
		}
		adsNew.addAll(locaListEntity.getAds());
		return adsNew;
	}
	
	
	private List<NewsEntity> getNews(NewsListEntity locaListEntity){
		if (locaListEntity.getNewsList() != null
				&& locaListEntity.getNewsList().size() > 0)
			list = (locaListEntity.getNewsList()).subList(1, locaListEntity
					.getNewsList().size());
		return list;
	}
	
	
	private void updateLocalData(NewsListEntity locaListEntity2) {
		if(dbHandler!=null)
			dbHandler.update(locaListEntity2);
	}

	/**
	 * 不做任何更新
	 */
	private void notUpdate(){
		
	}
	
	private NewsListEntity copy(NewsListEntity newsList) {
		if (locaListEntity == null) {
			return newsList;
		}
		if (!locaListEntity.equal(newsList)) {
			for (NewsEntity entity : newsList.newsList) {
				for (NewsEntity item : locaListEntity.newsList) {
					if (entity.equal(item)) {
						entity.state = item.state;
						break;
					}
				}
			}
		}
		for(int i = 0;i<newsList.newsList.size();i++){
			Log.d("tag","news===state==>"+newsList.newsList.get(i).state);
		}
		return newsList;
	}
	
	
	
}
