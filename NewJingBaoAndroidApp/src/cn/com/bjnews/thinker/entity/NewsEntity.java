package cn.com.bjnews.thinker.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 新闻 信息
 * @author sunqm
 * Create at:   2014-5-16 下午1:12:55 
 * TODO
 */
public class NewsEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 两者是否相同
	 * @param entity
	 * @return
	 */
	public boolean equal(NewsEntity entity){
		return this.id == entity.id  && this.pubDate.equals(entity.pubDate);
	}
	
	public int id;
	//	
	public String title;
//	
	/**信息来源*/
	public String source ;
	
	
	public String description;
	/**缩略图*/
	public String thumbnail;
	
	public String pubDate;
	
	public String picUrl = null;
	
	public String weburl = null;
//	
	public String content = null;
	
	public int state =  0 ;
	
	/**上部图片或者视频*/
	public ArrayList<MediaEntity> medias = new ArrayList<MediaEntity>();
	/**多图模式，下部图片*/
	public ArrayList<MediaEntity> images = new ArrayList<MediaEntity>();

	public ArrayList<RelatedEntity> relateds = new ArrayList<RelatedEntity>();

	public String toString(){
		return "id:"+id+"title:"+title;
	}
	
	
}
