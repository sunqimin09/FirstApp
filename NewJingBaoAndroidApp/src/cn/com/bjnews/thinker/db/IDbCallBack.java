package cn.com.bjnews.thinker.db;

import cn.com.bjnews.thinker.entity.NewsListEntity;
/**
 * 数据库 读取毁掉函数
 * @author sunqm
 * Create at:   2014-5-28 下午2:41:35 
 * TODO
 */
public interface IDbCallBack {
	
	public void ReadSuccess(NewsListEntity entity);
	
}
