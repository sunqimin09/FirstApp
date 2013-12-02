package com.tellout.util;

/**
 * 废弃
 * @author sunqm
 *
 */
public class TouchHelper {
	
	private float oldX = 0;
	
	private float oldY = 0;
	
	private int pointCount = 0;
	
	public TouchHelper(float downX,float downY){
		oldX = downX;
		oldY = downY;
	}
	
	/**
	 * 返回滑动的方向
	 * @param x 
	 * @param y
	 * @return	0:不左右滑动,1 左侧滑动,2 右侧滑动
	 */
	public int Scroll(float x,float y){
		pointCount++;
		if(pointCount%3==0){
			float tempY = y-oldY;//y的偏移量
			float tempX = x-oldX;//x的偏移量
			if(Math.abs(tempX)>Math.abs(tempY)){//x轴偏移量更大
				if(tempX>0){//右侧滑动
					return 2;
				}else{//左侧滑动
					return 1;
				}
			}
			
		}
		return 0;
	}
	
}
