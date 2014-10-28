package cn.com.bjnews.thinker.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import cn.com.bjnews.thinker.entity.AdIntroEntity;
import cn.com.bjnews.thinker.entity.MediaEntity;
import cn.com.bjnews.thinker.entity.NewsEntity;
import cn.com.bjnews.thinker.entity.NewsListEntity;
import cn.com.bjnews.thinker.entity.RelatedEntity;
import cn.com.bjnews.thinker.utils.FileDown;

/**
 * 数据库操作
 * 
 * @author sunqm Create at: 2014-5-15 下午3:02:09 TODO
 */
public class DbHandler extends SQLiteOpenHelper implements IDbDao{

	
	
	public static final String DB_NAME = "bjnews.db";

	public static final String TABLE_AD = "ad_table";

	public static final String TABLE_NEWS = "news_table";

	public static final String TABLE_RELATED = "related_table";

	public static final String TABLE_LIST = "newslist_table";

	public static final String TABLE_MEDIA = "media_table";

	public static final String AD_CHANNEL_ID = "channel_id";

	public static final String AD_ID = "id";

	public static final String AD_PIC = "pic";

	public static final String AD_URL = "url";
	
	public static final String AD_CAPTION = "caption";
	// 新闻
	public static final String NEWS_CHANNEL_ID = "channel_id";

	public static final String NEWS_ID = "id";

	public static final String NEWS_DATE = "pubDate";

	public static final String NEWS_TITLE = "title";

	public static final String NEWS_DESCRIPTION = "description";

	public static final String NEWS_THUMBNAIL = "thumbnail";
	/**新--来源*/
	public static final String NEWS_SOURCE = "source";

	public static final String NEWS_CONTENT = "content";

	public static final String NEWS_WEBURL = "weburl";
	
	public static final String NEWS_STATE = "state";
	
	public static final String NEWS_ORDER = "input_order";

	public static final String RELATED_ID = "id";

	public static final String RELATED_NEWS_ID = "news_id";

	public static final String RELATED_CHANNEL_ID = "channel_id";

	public static final String RELATED_TITLE = "title";

	public static final String RELATED_URL = "url";

	public static final String MEDIA_ID = "id";
	
	public static final String MEDIA_CHANNELID = "channel_id";

	public static final String MEDIA_NEWS_ID = "news_id";

	public static final String MEDIA_PIC = "pic";

	public static final String MEDIA_CAPTION = "caption";

	/** 是否为多图模式下的 多个图片 ：0 是视频   1是上部图片  ，2是下部图片*/
	public static final String MEDIA_IS_PICS_MODEL = "flag";

	public static final String MEDIA_VIDEO = "video";

	public static final String LIST_CHANNEL_ID = "channelId";

	public static final String LIST_TITLE = "title";

	public static final String LIST_PUBDATE = "pubDate";
	
	/**是否已经请求过0：没有，1.请求过了*/
	public static final String LIST_REQUEST_STATE = "state";

	/****/
	public static final int VIDEO = 0;
	
	public static final int TOP_MEDIA =1;
	
	public static final int BOTTOM_MEDIA = 2;
	private static SQLiteDatabase db = null;

	private static DbHandler dbHandler = null;
	
	private final static int version = 3;
	
	/**
	 * 初始化构造函数
	 * 
	 * @param context
	 *            上下文容器
	 */
	private DbHandler(Context context) {
		super(context, DB_NAME, null, version);
	}

	private DbHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	public static DbHandler getInstance(Context context){
		if(dbHandler == null){
			dbHandler = new DbHandler(context);
		}
		return dbHandler;
	}

	private void open() {
		if(db==null || !db.isOpen())
			db = this.getWritableDatabase();
	}
	
	private void closeDb(){
		if(db!=null&&db.isOpen())
			db.close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// 创建表
		String adSql = "create table " + TABLE_AD + "( " + AD_ID
				+ " integer primary key AUTOINCREMENT," + AD_CHANNEL_ID + " INTEGER,"
				+ AD_URL + " TEXT, " + AD_PIC + " TEXT, " + AD_CAPTION
				+ " TEXT " + " )";
		db.execSQL(adSql);
		String newsSql = "create table " + TABLE_NEWS + " ( " + NEWS_ID
				+ " integer primary key," + NEWS_CHANNEL_ID + " INTEGER,"
				+ NEWS_CONTENT + " TEXT," + NEWS_DATE + "  TEXT,"
				+ NEWS_THUMBNAIL + " TEXT, "+NEWS_SOURCE+"  TEXT,"
				+ NEWS_DESCRIPTION + " TEXT," + NEWS_TITLE + " TEXT,"
				+ NEWS_WEBURL + " TEXT,"+ NEWS_STATE +" integer, "
				+NEWS_ORDER+" integer "
				+ " )";

		db.execSQL(newsSql);

		String relatedSql = "create table " + TABLE_RELATED + "( " + RELATED_ID
				+ " integer primary key AUTOINCREMENT," + RELATED_CHANNEL_ID
				+ " INTEGER," + RELATED_TITLE + " TEXT," + RELATED_NEWS_ID
				+ " INTEGER," + RELATED_URL + " TEXT" + ")";
		db.execSQL(relatedSql);
		String listSql = "create table " + TABLE_LIST + " ( " + LIST_CHANNEL_ID
				+ " integer primary key," + LIST_TITLE + " TEXT, "
				+ LIST_REQUEST_STATE+ " integer,"
				+ LIST_PUBDATE + "  TEXT " + " )";

		db.execSQL(listSql);
		String mediaSql = "create table " + TABLE_MEDIA + " ( " + MEDIA_ID
				+ " integer primary key AUTOINCREMENT," + MEDIA_CAPTION + " TEXT, "
				+ MEDIA_NEWS_ID + "  integer," + MEDIA_PIC + " TEXT,"
				+ MEDIA_IS_PICS_MODEL + " integer," + MEDIA_VIDEO + " TEXT, "
				+ MEDIA_CHANNELID + " integer "
				+ " )";

		db.execSQL(mediaSql);

	}

	/**更新数据库*/
	private String CREATE_BOOK = "create table book(bookId integer primarykey,bookName text);";
	private String CREATE_TEMP_BOOK = "alter table book rename to _temp_book";
	private String INSERT_DATA = "insert into book select *,'' from _temp_book";
	private String DROP_TABLE = "drop table _temp_book";
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		  switch (newVersion) {
//		  case 1:
		  case version:
			  String sql =" ALTER TABLE "+TABLE_LIST+" ADD COLUMN "+LIST_REQUEST_STATE+" integer";
//			  String listSql = "update table " + TABLE_LIST + " ( " + LIST_CHANNEL_ID
//				+ " integer primary key," + LIST_TITLE + " TEXT, "
//				+ LIST_REQUEST_STATE+ " integer,"
//				+ LIST_PUBDATE + "  TEXT " + " )";
//			  open();
			  db.execSQL(sql);
//			  close();
		   break;
		  }

	}

	public synchronized void update(final NewsListEntity content){
		new Thread(){

			@Override
			public void run() {
				open();
				try{
					updateAll(content);
				}catch(java.lang.IllegalStateException e){
					e.printStackTrace();
					closeDb();
				}
				closeDb();
			}
		}.start();
		
	}
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 1 && msg.obj instanceof IDbCallBack){
				((IDbCallBack)msg.obj).ReadSuccess((NewsListEntity)msg.getData().getParcelable("object"));
			}
				
		}
		
	};
	
//	private IDbCallBack dbCallBack;
	
	public synchronized void readNewsList(final IDbCallBack dbCallBack,final int channelId) {
//		this.dbCallBack = dbCallBack;
		new Thread(){

			@Override
			public void run() {
				Log.d("tag","message-start>"+channelId);
				open();
				NewsListEntity entity = new NewsListEntity();
				try{
				entity.setChannelId(channelId);
				entity.setPubDate(readNewsList(channelId).pubDate);
				entity.setTitle(readNewsList(channelId).title);
				entity.requestState = readNewsList(channelId).requestState;
				entity.setAds(readAds(channelId));
				entity.setNewsList(readNews(channelId));
				}catch(Exception e){
					e.printStackTrace();
					closeDb();
				}
				Log.d("tag","request--state->"+entity.requestState);
				closeDb();
				Message msg = handler.obtainMessage();
				msg.what=1;
				msg.obj = dbCallBack;
				Bundle b = new Bundle();
				b.putParcelable("object", entity);
				msg.setData(b);
				handler.sendMessage(msg);
				Log.d("tag","message->"+channelId);
			}
			
		}.start();
		
	}
	
	/**
	 * 推送 后 查询本地 数据库
	 * @param channelId
	 * @param newsId
	 * @return
	 */
	public synchronized NewsEntity selectNews(int channelId,int newsId){
		NewsEntity entity;
		open();
		Cursor c = db.query(TABLE_NEWS, new String[] { NEWS_CONTENT, NEWS_DATE,
				NEWS_DESCRIPTION, NEWS_ID,  NEWS_TITLE,NEWS_THUMBNAIL,NEWS_SOURCE,
				NEWS_WEBURL ,NEWS_STATE}, NEWS_CHANNEL_ID + "=? and "+NEWS_ID +"=?", new String[] { channelId+""
				 ,newsId+""}, null, null, NEWS_ORDER);
		Log.d("tag","news-count-11>"+c.getCount());
		if (c.moveToNext()) {
			entity = new NewsEntity();
			entity.id = Integer.valueOf(newsId);
			entity.content = (c.getString(c.getColumnIndex(NEWS_CONTENT)));
			entity.description = (c.getString(c
					.getColumnIndex(NEWS_DESCRIPTION)));
			entity.title = (c.getString(c.getColumnIndex(NEWS_TITLE)));
			entity.thumbnail = (c.getString(c.getColumnIndex(NEWS_THUMBNAIL)));
			entity.source = c.getString(c.getColumnIndex(NEWS_SOURCE));
			entity.pubDate = (c.getString(c.getColumnIndex(NEWS_DATE)));
			entity.weburl = (c.getString(c.getColumnIndex(NEWS_WEBURL)));
			entity.medias = (readMedia(c.getInt(c.getColumnIndex(NEWS_ID))));
			entity.relateds = (readRelateds(c.getInt(c.getColumnIndex(NEWS_ID))));
			entity.images = (readImage(c.getInt(c.getColumnIndex(NEWS_ID))));
			entity.state = (c.getInt(c.getColumnIndex(NEWS_STATE)));
		}else{
			return null;
		}
		c.close();
		closeDb();
		return entity;
	}
	
	/**
	 * 标志某一篇文章为 已读
	 * @param NewsId
	 * @param hasRead
	 * @return
	 */
	public synchronized void update(final int NewsId,final int hasRead){
		new Thread(){

			@Override
			public void run() {
				open();
				ContentValues values = new ContentValues();
				values.put(NEWS_STATE,hasRead);
				try{
					int result = db.update(TABLE_NEWS, values, NEWS_ID+"=?", new String[]{""+NewsId});
				}catch(Exception e){
					
				}
				
				closeDb();
			}
			
		}.start();
	}
	
	/**
	 * 所有已读 标志位未读
	 * @return
	 */
	public synchronized void AllNotRead(){
		open();
//		String sql = "update "+TABLE_NEWS +" set "+NEWS_STATE+" = '0' where "+NEWS_ID +" = '*'";
//		try{
//			db.execSQL(sql);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		
		ContentValues values = new ContentValues();
		values.put(NEWS_STATE,0);
		int result = db.update(TABLE_NEWS, values, NEWS_STATE+"=?", new String[]{String.valueOf(1)});
		Log.d("tag","notread->"+result);
		closeDb();
	}
	
	private synchronized long insertNewsList(NewsListEntity entity) {

		ContentValues values = new ContentValues();
		values.put(LIST_CHANNEL_ID, entity.getChannelId());
		values.put(LIST_PUBDATE, entity.getPubDate().toString());
		values.put(LIST_REQUEST_STATE, 1);
		values.put(LIST_TITLE, entity.getTitle());
		return db.insert(TABLE_LIST, null, values);
	}

	private synchronized long insertAd(AdIntroEntity entity, int channelId) {
		ContentValues values = new ContentValues();
		values.put(AD_ID, entity.id);
		values.put(AD_CHANNEL_ID, channelId);
		values.put(AD_URL, entity.url);
		values.put(AD_CAPTION, entity.caption);
		return db.insert(TABLE_AD, null, values);
	}

	private synchronized long insertNews(NewsEntity entity, int channelId,int order) {
		ContentValues values = new ContentValues();
		values.put(NEWS_CHANNEL_ID, channelId);
		values.put(NEWS_CONTENT, entity.content);
		values.put(NEWS_DATE, entity.pubDate);
		values.put(NEWS_DESCRIPTION, entity.description);
		values.put(NEWS_ID, entity.id);
		values.put(NEWS_THUMBNAIL, entity.thumbnail);
		values.put(NEWS_SOURCE, entity.source);
		values.put(NEWS_TITLE, entity.title);
		values.put(NEWS_WEBURL, entity.weburl);
		values.put(NEWS_STATE,0);
		values.put(NEWS_ORDER, order);
		return db.insert(TABLE_NEWS, null, values);
	}

	private synchronized long insertRelated(RelatedEntity entity,
			int channelId, int newsId) {
		ContentValues values = new ContentValues();
		values.put(RELATED_CHANNEL_ID, channelId);
		values.put(RELATED_NEWS_ID, newsId);
		values.put(RELATED_TITLE, entity.getTitle());
		values.put(RELATED_URL, entity.getUrl());
		return db.insert(TABLE_RELATED, null, values);
	}

	private synchronized long insertMedia(MediaEntity entity,int channelId, int newsId) {
		ContentValues values = new ContentValues();
		values.put(MEDIA_CAPTION, entity.caption);
		values.put(MEDIA_NEWS_ID, newsId);
		values.put(MEDIA_PIC, entity.pic);
		values.put(MEDIA_VIDEO, entity.pic);
		values.put(MEDIA_CHANNELID,channelId);
		values.put(MEDIA_IS_PICS_MODEL, "1");
		return db.insert(TABLE_MEDIA, null, values);
	}

	private synchronized long insertImage(MediaEntity entity,int channelId, int newsId) {
		ContentValues values = new ContentValues();
		values.put(MEDIA_CAPTION, entity.caption );
		values.put(MEDIA_NEWS_ID, newsId);
		values.put(MEDIA_PIC, entity.pic);
		values.put(MEDIA_VIDEO, entity.pic);
		values.put(MEDIA_CHANNELID,channelId);
		values.put(MEDIA_IS_PICS_MODEL, "2");
		return db.insert(TABLE_MEDIA, null, values);
	}

	private synchronized void deleteAd(int channelId) {
		db.delete(TABLE_AD, AD_CHANNEL_ID + "=?",
				new String[] { "" + channelId });
	}

	private synchronized void deleteNews(int channelId) {
		int result = db.delete(TABLE_NEWS, NEWS_CHANNEL_ID + "=?", new String[] {String.valueOf(channelId) });
		Log.d("tag","deleteNews-->"+result);
	}

	private synchronized void deleteRelated(int channelId) {
		db.delete(TABLE_RELATED, RELATED_CHANNEL_ID+"=?", new String[] { String.valueOf(channelId)});
	}

	private synchronized void deleteNewsList(int channelId) {
		db.delete(TABLE_LIST, LIST_CHANNEL_ID+"=?", new String[] { String.valueOf(channelId)  });
	}

	private synchronized void deleteMedia(int channelId) {
		db.delete(TABLE_MEDIA, MEDIA_CHANNELID+"=?", new String[] { String.valueOf(channelId) });
	}

	private NewsListEntity readNewsList(int channelId){
		NewsListEntity entity = new NewsListEntity();
//		String[] result =new String[2];
		open();
		Cursor c = db.query(TABLE_LIST, new String[] { LIST_PUBDATE, LIST_TITLE,LIST_REQUEST_STATE
			 }, LIST_CHANNEL_ID + "=?", new String[] { String.valueOf(channelId)
				}, null, null, null);
		if(c.moveToNext()){
			entity.pubDate = c.getString(c.getColumnIndex(LIST_PUBDATE));
			entity.title = c.getString(c.getColumnIndex(LIST_TITLE));
			entity.requestState = c.getInt(c.getColumnIndex(LIST_REQUEST_STATE));
		}
		c.close();
		return entity;
	}

	/**
	 * 读取广告
	 * 
	 * @param channelId
	 * @return
	 */
	private synchronized ArrayList<AdIntroEntity> readAds(int channelId) {
		ArrayList<AdIntroEntity> entitys = new ArrayList<AdIntroEntity>();
		AdIntroEntity entity;
		open();
		Cursor c = db.query(TABLE_AD, new String[] { AD_ID, AD_URL ,AD_PIC,AD_CAPTION},
				AD_CHANNEL_ID + "=?", new String[] { channelId + "" }, null,
				null, null);
		while (c.moveToNext()) {
			entity = new AdIntroEntity();
			entity.id = (c.getString(c.getColumnIndex(AD_ID)));
			entity.picUrl = (c.getString(c.getColumnIndex(AD_PIC)));
			entity.url = (c.getString(c.getColumnIndex(AD_URL)));
			entity.caption = c.getString(c.getColumnIndex(AD_CAPTION));
			entitys.add(entity);
		}
		c.close();
		Log.d("tag","data-db-ads"+entitys.size());
		return entitys;
	}

	private synchronized ArrayList<NewsEntity> readNews(int channelId) {
		ArrayList<NewsEntity> entitys = new ArrayList<NewsEntity>();
		NewsEntity entity;
		open();
		Cursor c = db.query(TABLE_NEWS, new String[] { NEWS_CONTENT, NEWS_DATE,
				NEWS_DESCRIPTION, NEWS_ID,  NEWS_TITLE,NEWS_THUMBNAIL,NEWS_SOURCE,
				NEWS_WEBURL ,NEWS_STATE}, NEWS_CHANNEL_ID + "=?", new String[] { channelId
				+ "" }, null, null, NEWS_ORDER);
		Log.d("tag","news-count->"+c.getCount());
		while (c.moveToNext()) {
			entity = new NewsEntity();
			entity.id = (c.getInt(c.getColumnIndex(NEWS_ID)));
			entity.content = (c.getString(c.getColumnIndex(NEWS_CONTENT)));
			entity.description = (c.getString(c
					.getColumnIndex(NEWS_DESCRIPTION)));
			entity.title = (c.getString(c.getColumnIndex(NEWS_TITLE)));
			entity.thumbnail = (c.getString(c.getColumnIndex(NEWS_THUMBNAIL)));
			entity.source = c.getString(c.getColumnIndex(NEWS_SOURCE));
			entity.pubDate = (c.getString(c.getColumnIndex(NEWS_DATE)));
			entity.weburl = (c.getString(c.getColumnIndex(NEWS_WEBURL)));
			entity.medias = (readMedia(c.getInt(c.getColumnIndex(NEWS_ID))));
			entity.relateds = (readRelateds(c.getInt(c.getColumnIndex(NEWS_ID))));
			entity.images = (readImage(c.getInt(c.getColumnIndex(NEWS_ID))));
			entity.state = (c.getInt(c.getColumnIndex(NEWS_STATE)));
			entitys.add(entity);
		}
		for(int i =0;i<entitys.size();i++){
			Log.d("tag","insert-read-newsId>"+entitys.get(i).id);
		}
		c.close();
		Log.d("tag","news-size->"+entitys.size());
		return entitys;
	}

	private synchronized ArrayList<RelatedEntity> readRelateds(int newsId) {
		ArrayList<RelatedEntity> entitys = new ArrayList<RelatedEntity>();
		RelatedEntity entity;
		open();
		Cursor c = db.query(TABLE_RELATED, new String[] { RELATED_ID,
				RELATED_TITLE, RELATED_URL }, RELATED_NEWS_ID + "=?",
				new String[] { newsId + "" }, null, null, null);
		while (c.moveToNext()) {
			entity = new RelatedEntity();
			entity.setUrl(c.getString(c.getColumnIndex(RELATED_URL)));
			entity.setTitle(c.getString(c.getColumnIndex(RELATED_TITLE)));
			entitys.add(entity);
		}
		c.close();
		return entitys;
	}

	private synchronized ArrayList<MediaEntity> readMedia(int newsId) {
		ArrayList<MediaEntity> entitys = new ArrayList<MediaEntity>();
		MediaEntity entity;
		open();
		Cursor c = db.query(TABLE_MEDIA, new String[] { MEDIA_ID,
				MEDIA_CAPTION, MEDIA_PIC, MEDIA_VIDEO,MEDIA_IS_PICS_MODEL }, MEDIA_NEWS_ID
				+ "=? and " + MEDIA_IS_PICS_MODEL + "=1 or "+MEDIA_IS_PICS_MODEL +"=0", new String[] { newsId
				+ "" }, null, null, null);
		while (c.moveToNext()) {
			entity = new MediaEntity();
			entity.caption = (c.getString(c.getColumnIndex(MEDIA_CAPTION)));
			entity.pic = (c.getString(c.getColumnIndex(MEDIA_PIC)));
			entity.video = (c.getString(c.getColumnIndex(MEDIA_VIDEO)));
			entity.flag = (c.getInt(c.getColumnIndex(MEDIA_IS_PICS_MODEL)));
			entitys.add(entity);
		}
		c.close();
		return entitys;
	}

	private  synchronized ArrayList<MediaEntity> readImage(int newsId) {
		ArrayList<MediaEntity> entitys = new ArrayList<MediaEntity>();
		MediaEntity entity;
		open();
		Cursor c = db.query(TABLE_MEDIA, new String[] { MEDIA_ID,
				MEDIA_CAPTION, MEDIA_PIC, MEDIA_VIDEO }, MEDIA_NEWS_ID
				+ "=? and " + MEDIA_IS_PICS_MODEL + "=2", new String[] { newsId
				+ "" }, null, null, null);
		while (c.moveToNext()) {
			entity = new MediaEntity();
			entity.caption = (c.getString(c.getColumnIndex(MEDIA_CAPTION)));
			entity.pic = (c.getString(c.getColumnIndex(MEDIA_PIC)));
			entity.video = (c.getString(c.getColumnIndex(MEDIA_VIDEO)));
			entity.flag = (BOTTOM_MEDIA);
			entitys.add(entity);
		}
		c.close();
		return entitys;
	}

	/**
	 * 更新新闻列表，1.删除2.插入（此时需要操作5张表）
	 */
	private synchronized void deleteAndInsert(NewsListEntity content, int channelId) {
		deleteAd(channelId);
		deleteNewsList(channelId);
		for (int i = 0; i < content.getNewsList().size(); i++) {
			deleteMedia(content.getNewsList().get(i).id);
			deleteNews(content.getNewsList().get(i).id);
			deleteRelated(content.getNewsList().get(i).id);
		}
		// 插入广告
		for (int i = 0; i < content.getAds().size(); i++) {
			insertAd(content.getAds().get(i), channelId);
		}
		// newsList
		insertNewsList(content);
		NewsEntity newsEntity = null;
		// news
		for (int i = 0; i < content.getNewsList().size(); i++) {
			newsEntity = content.getNewsList().get(i);
			insertNews(newsEntity, channelId,i);
			// media
			for (int j = 0; j < newsEntity.medias.size(); j++) {
				insertMedia(newsEntity.medias.get(j),channelId,
						newsEntity.id);
			}
			// image
			for (int m = 0; m < newsEntity.images.size(); m++) {
				insertImage(newsEntity.images.get(m),channelId,
						newsEntity.id);
			}
			// related
			for (int k = 0; k < newsEntity.relateds
					.size(); k++) {
				insertRelated(
						newsEntity.relateds.get(k),
						channelId, newsEntity.id);
			}
		}

	}
	
	private synchronized void updateAll(NewsListEntity content){
//		for(int i =0;i<content.newsList.size();i++){
//			Log.d("tag","insert-newsId>"+content.newsList.get(i).id);
//		}
//		Log.d("tag","insertads-->"+content.ads.size());
		deleteAd(content.getChannelId());
		//广告
		for(AdIntroEntity entity:content.ads){
			updateAd(entity, content.getChannelId());
//			Log.d("tag","insertads--result>");
		}
		//newslist
		updateNewsList(content);
		deleteMedia(content.getChannelId());
		deleteNews(content.getChannelId());
		deleteRelated(content.getChannelId());
		
		NewsEntity newsEntity = null;
		// news
		for (int i = 0; i < content.getNewsList().size(); i++) {
			newsEntity = content.getNewsList().get(i);
			insertNews(newsEntity, content.channelId,i);
			// media
			for (int j = 0; j < newsEntity.medias.size(); j++) {
				insertMedia(newsEntity.medias.get(j),content.getChannelId(),
						newsEntity.id);
			}
			// image
			for (int m = 0; m < newsEntity.images.size(); m++) {
				insertImage(newsEntity.images.get(m),content.getChannelId(),
						newsEntity.id);
			}
			// related
			for (int k = 0; k < newsEntity.relateds
					.size(); k++) {
				insertRelated(
						newsEntity.relateds.get(k),
						content.channelId, newsEntity.id);
			}
		}
		
	}

	private void updateAd(AdIntroEntity entity, int channelId) {
		// String sql = "REPLACE INTO " + TABLE_AD + " (" + AD_CAPTION + ","
		// + AD_PIC + "," + AD_URL + ") values ('" + entity.caption
		// + "','" + entity.picUrl + "','" + entity.url + "') where "
		// + AD_CHANNEL_ID + "='" + channelId+"'";
		
		ContentValues values = new ContentValues();
		values.put(AD_CAPTION, entity.caption);
		values.put(AD_PIC, entity.picUrl);
		values.put(AD_URL, entity.url);
		values.put(AD_CHANNEL_ID, channelId);
		db.insert(TABLE_AD, null, values);
//		db.update(TABLE_AD, values, AD_CHANNEL_ID + "=?",
//				new String[] { channelId + "" });
	}

	private void updateNewsList(NewsListEntity entity) {
		db.delete(TABLE_LIST, LIST_CHANNEL_ID+"=?", new String[] { String.valueOf(entity.getChannelId()) });
		ContentValues values = new ContentValues();
		values.put(LIST_CHANNEL_ID, entity.getChannelId());
		values.put(LIST_PUBDATE, entity.getPubDate());
		values.put(LIST_TITLE, entity.getTitle());
		values.put(LIST_REQUEST_STATE, 1);
		db.insert(TABLE_LIST, null, values);
//		db.update(TABLE_LIST, values, LIST_CHANNEL_ID+"=?", new String[]{entity.getChannelId()+""});
	}
	
	private void updateNews(NewsEntity entity,int channelId){
		ContentValues values = new ContentValues();
		values.put(NEWS_CHANNEL_ID, channelId);
		values.put(NEWS_CONTENT, entity.content);
		values.put(NEWS_DATE, entity.pubDate);
		values.put(NEWS_DESCRIPTION, entity.description);
		values.put(NEWS_ID, entity.id);
		values.put(NEWS_THUMBNAIL, entity.thumbnail);
		values.put(NEWS_TITLE, entity.title);
		values.put(NEWS_STATE,0);
		db.update(TABLE_NEWS, values, NEWS_CHANNEL_ID+"=? and "+NEWS_ID+"=?", new String[]{channelId+"",entity.id+""});
	}

	public synchronized void updateMedias(MediaEntity entity,int newsId){
		//delete   insert 
	}

	@Override
	public List<String> queryAllImgName() {
		open();
		Cursor c = db.query(TABLE_MEDIA, new String[] { MEDIA_PIC },
				null, null, null, null, null);
		List<String> strs = new ArrayList<String>();
		while (c.moveToNext()) {
			strs.add(FileDown.getFileName(c.getString(c.getColumnIndex(MEDIA_PIC))));
		}
		c.close();
		return strs;
	}

	@Override
	public boolean updateChannelUpdate(int channelId, int state) {
		open();
		ContentValues values = new ContentValues();
		values.put(LIST_REQUEST_STATE, state);
		int result = db.update(TABLE_LIST, values, LIST_CHANNEL_ID+"=?", new String[]{""+channelId});
		Log.d("tag", channelId+"update--state--"+state+result);
		closeDb();
		return result>0;
	}
	
	/**
	 * SimpleDateFormat fm=new SimpleDateFormat("EEE,d MMM yyyy hh:mm:ss Z",
	 * Locale.ENGLISH); System.out.println( fm.format(new Date())+" GMT" );
	 */
}
