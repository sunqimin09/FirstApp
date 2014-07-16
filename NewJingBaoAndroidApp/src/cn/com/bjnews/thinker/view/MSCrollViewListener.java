package cn.com.bjnews.thinker.view;


public interface MSCrollViewListener {

	public void onScrollChanged(int x, int y, int oldx, int oldy);
	
	/**
	 * 
	 * @param deltaX <0 向右滑动
	 * @param scrollX 最大值380，已经在最右边
	 * @param isTouchable 是否在触摸
	 */
	public void overScrollBy(int deltaX,int scrollX,boolean isTouchable);
	
	
}
