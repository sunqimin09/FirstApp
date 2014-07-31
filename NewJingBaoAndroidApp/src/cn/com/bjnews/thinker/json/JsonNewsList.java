package cn.com.bjnews.thinker.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import cn.com.bjnews.thinker.db.DbHandler;
import cn.com.bjnews.thinker.entity.AdIntroEntity;
import cn.com.bjnews.thinker.entity.MediaEntity;
import cn.com.bjnews.thinker.entity.NewsEntity;
import cn.com.bjnews.thinker.entity.NewsListEntity;
import cn.com.bjnews.thinker.entity.RelatedEntity;


public class JsonNewsList {

	public static NewsListEntity parse(String content) {
		NewsListEntity entity = new NewsListEntity();
		try {
			JSONObject object = new JSONObject(content);
			entity.setChannelId(object.getInt("channelId"));
			entity.setTitle(object.getString("title"));
			entity.setPubDate(object.getString("pubDate"));
			// 几个横向滑动的图片，以及说明

			entity.setAds(parseAd(object));
			// 每一项
			entity.setNewsList(parseNews(object.getJSONArray("itemList")));
			Log.d("tag","pubDate-->"+object.getString("pubDate"));
		} catch (JSONException e) {
			Log.e("tag", "error" + e);
			e.printStackTrace();
		}
		return entity;

	}

	private static ArrayList<AdIntroEntity> parseAd(JSONObject  object)
			throws JSONException {
		ArrayList<AdIntroEntity> ads = new ArrayList<AdIntroEntity>();
		if(!object.has("adIntro")){
			return ads;
		}
		JSONArray adIntroJson = object.getJSONArray("adIntro");
		AdIntroEntity adIntroEntity;
		for (int i = 0; i < adIntroJson.length(); i++) {
			JSONObject adintroTempJson = adIntroJson.getJSONObject(i);

			adIntroEntity = new AdIntroEntity();
			adIntroEntity.id = (adintroTempJson.getString("id"));
			adIntroEntity.picUrl = (adintroTempJson.getString("pic"));
			adIntroEntity.url = (adintroTempJson.getString("url"));
			if(adintroTempJson.has("caption"))
				adIntroEntity.caption = adintroTempJson.getString("caption");
			ads.add(adIntroEntity);
		}
		return ads;
	}

	private static ArrayList<NewsEntity> parseNews(JSONArray newsListJson)
			throws JSONException {
		ArrayList<NewsEntity> newsEntitys = new ArrayList<NewsEntity>();
		NewsEntity newsEntity = null;
		for (int j = 0; j < newsListJson.length(); j++) {
			JSONObject TempJson = newsListJson.getJSONObject(j);
			newsEntity = new NewsEntity();
			newsEntity.id = (TempJson.getInt("id"));
			newsEntity.pubDate = (TempJson.getString("pubDate"));
			newsEntity.weburl = (TempJson.getString("weburl"));
			newsEntity.source = TempJson.getString("source");
			newsEntity
					.content = (parseContent(TempJson.getJSONArray("content")));
			newsEntity.thumbnail = (TempJson.getString("thumbnail"));
			newsEntity.description = (TempJson.getString("description"));
			newsEntity.title = (TempJson.getString("title"));
			// media
			newsEntity.medias = (parseMedia(TempJson));
			/**多图模式*/
			newsEntity.images = (parseImages(TempJson));

			// 相关文件
			newsEntity.relateds = (parseRelated(TempJson));
			newsEntitys.add(newsEntity);
		}
		return newsEntitys;
	}

	private static ArrayList<MediaEntity> parseMedia(JSONObject object)
			throws JSONException {
		ArrayList<MediaEntity> mediaList = new ArrayList<MediaEntity>();
		if(object.has("stdImage")){
			JSONArray mediasTempJson = object.getJSONArray("stdImage");
			
			MediaEntity mediaEntity;
			
			for (int m = 0; m < mediasTempJson.length(); m++) {
				JSONObject mediaTempJson = mediasTempJson.getJSONObject(m);
				mediaEntity = new MediaEntity();
				mediaEntity.caption = (mediaTempJson.getString("caption"));
				mediaEntity.pic = (mediaTempJson.getString("pic"));
				mediaEntity.flag = (DbHandler.TOP_MEDIA);
				mediaList.add(mediaEntity);
			}
			if(object.has("video")){//加入视频部分
				JSONArray videoJson = object.getJSONArray("video");
				for(int j =0;j<videoJson.length();j++){
					JSONObject videoTempJson = videoJson.getJSONObject(j);
					mediaEntity = new MediaEntity();
					mediaEntity.caption = (videoTempJson.getString("caption"));
					mediaEntity.pic = (videoTempJson.getString("pic"));
					mediaEntity.video = (videoTempJson.getString("url"));
					mediaEntity.flag =(DbHandler.VIDEO);
					mediaList.add(mediaEntity);
				}
			}
			
		}
		
		return mediaList;
	}
	
	/**
	 * 多图模式--下部图片
	 * @param TempJson
	 * @return
	 * @throws JSONException
	 */
	private static ArrayList<MediaEntity> parseImages(JSONObject TempJson) throws JSONException{
		ArrayList<MediaEntity> mediaList = new ArrayList<MediaEntity>();
		if (TempJson.has("extImage")) {// 更多图片
			JSONArray extImageTemp = TempJson.getJSONArray("extImage");
			JSONObject imgTempJson;
			MediaEntity mediaEntity;
			for (int i = 0; i < extImageTemp.length(); i++) {
				imgTempJson = extImageTemp.getJSONObject(i);
				mediaEntity = new MediaEntity();
				mediaEntity.caption = (imgTempJson.getString("caption"));
				mediaEntity.pic = (imgTempJson.getString("url"));
				mediaEntity.flag = (DbHandler.BOTTOM_MEDIA);
				mediaList.add(mediaEntity);
			}
		}
		return mediaList;
	}

	private static ArrayList<RelatedEntity> parseRelated(JSONObject object)
			throws JSONException {
		ArrayList<RelatedEntity> relateds = new ArrayList<RelatedEntity>();
		if(object.has("related")){
			JSONArray relatedJson = object.getJSONArray("related");
			JSONObject tempJson = null;
			RelatedEntity relatedEntity = null;
			for (int i = 0; i < relatedJson.length(); i++) {
				tempJson = relatedJson.getJSONObject(i);
				relatedEntity = new RelatedEntity();
				relatedEntity.setTitle(tempJson.getString("title"));
				relatedEntity.setUrl(tempJson.getString("url"));
				relateds.add(relatedEntity);
			}
		}
		
		return relateds;
	}

	private static String parseContent(JSONArray contentJson)
			throws JSONException {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < contentJson.length(); i++) {
			
			sb.append(contentJson.get(i).toString());
			sb.append("\r\n\r\n");
		}
		return sb.toString();
	}
	
	

}
