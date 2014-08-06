package cn.com.bjnews.thinker.actlogical;

import java.util.List;

import cn.com.bjnews.thinker.entity.AdIntroEntity;
import cn.com.bjnews.thinker.entity.NewsEntity;

public interface IFragmentFirst {
	
	/**
	 * 显示数据
	 * @param list
	 * @param ads
	 */
//	public void showData(List<NewsEntity> list,List<AdIntroEntity> ads);
	
	void showData(Object ...params);
	
	/**没有更新*/
	void noUpdate();
	
}
