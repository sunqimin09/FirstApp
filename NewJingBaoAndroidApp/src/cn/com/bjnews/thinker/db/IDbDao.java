package cn.com.bjnews.thinker.db;

import java.util.List;

public interface IDbDao {
	
	/**查询所有的图片的地址*/
	public List<String> queryAllImgName();
	
	/**
	 * 更新是否已经请求数据，增加标示位
	 * @param channelId
	 * @param 
	 * @return
	 */
	public boolean updateChannelUpdate(int channelId,int state);
	
}
