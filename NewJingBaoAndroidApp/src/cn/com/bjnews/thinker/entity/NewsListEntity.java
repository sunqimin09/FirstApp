package cn.com.bjnews.thinker.entity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * 新闻列表信息
 * @author sunqm
 * Create at:   2014-5-16 下午1:55:55 
 * TODO
 */
@SuppressLint("ParcelCreator")
public class NewsListEntity implements Parcelable {
	
	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public ArrayList<AdIntroEntity> getAds() {
		return ads;
	}

	public void setAds(ArrayList<AdIntroEntity> ads) {
		this.ads = ads;
	}

	public ArrayList<NewsEntity> getNewsList() {
		return newsList;
	}

	public void setNewsList(ArrayList<NewsEntity> newsList) {
		this.newsList = newsList;
	}

//	public ArrayList<RelatedEntity> getRelateds() {
//		return relateds;
//	}
//
//	public void setRelateds(ArrayList<RelatedEntity> relateds) {
//		this.relateds = relateds;
//	}

	/**
	 * 
	 * @param entity
	 * @return true:两个数据一样，false:两个数据不一样
	 */
	public boolean equal(NewsListEntity entity){
		Log.d("tag","equal->"+entity.pubDate+"<this>"+pubDate);
		if(pubDate==null||entity.pubDate == null){
			return false;
		}
		return this.pubDate.equals(entity.pubDate)&& this.channelId == entity.channelId;
	}
	
	
	public int channelId;
	
	public String title;
	
	public String pubDate;
	
	public ArrayList<AdIntroEntity>  ads = new ArrayList<AdIntroEntity>();
	
	public ArrayList<NewsEntity> newsList = new ArrayList<NewsEntity>();

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
//	private ArrayList<RelatedEntity> relateds= new ArrayList<RelatedEntity>();
	
}
