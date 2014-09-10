package com.sun.appdianpinghair.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.sun.appdianpinghair.entity.BusinessEntity;

/**
 * 主页面的 逻辑处理
 * @author sunqm
 * @version 创建时间：2014-9-10 下午3:05:32
 * TODO
 */
public class MainActService {
	

	/**
	 * 按照距离 由近到远
	 * @param list
	 */
	public List<BusinessEntity> sortByDistance(List<BusinessEntity> list){
        Collections.sort(list, comparatorDis);
        return list;
	}
	
	/**
	 * 评论好坏
	 */
	public List<BusinessEntity> sortByComment(List<BusinessEntity> list){
		Collections.sort(list, comparatorCom);
        return list;
	}
	
	/**
	 * 花费 由高到低
	 * @param list
	 */
	public List<BusinessEntity> sortByMoneyExpensice(List<BusinessEntity> list){
		Collections.sort(list, comparatorMoney1);
        return list;
	}
	
	/**
	 *  花费由低到高
	 * @param list
	 * @return
	 */
	public List<BusinessEntity> sortByMoneyCheap(List<BusinessEntity> list){
		Collections.sort(list, comparatorMoney2);
        return list;
	}
	
	/***
	 * 距离 比较
	 */
	Comparator<BusinessEntity> comparatorDis = new Comparator<BusinessEntity>() {

		public int compare(BusinessEntity arg0, BusinessEntity arg1) {
			// 先排年龄
			if (arg0.distance > arg1.distance) {
				return 1;
			}
			return -1;

		}
	};
	
	/***
	 * 评价比较 高-》低
	 */
	Comparator<BusinessEntity> comparatorCom = new Comparator<BusinessEntity>() {

		public int compare(BusinessEntity arg0, BusinessEntity arg1) {
			// 先排年龄
			if (arg0.avg_rating > arg1.distance) {
				return -1;
			}
			return 1;

		}
	};
	
	/***
	 * 价格比较 高-》低
	 */
	Comparator<BusinessEntity> comparatorMoney1 = new Comparator<BusinessEntity>() {

		public int compare(BusinessEntity arg0, BusinessEntity arg1) {
			// 先排年龄
			if (arg0.avg_price > arg1.avg_price) {
				return -1;
			}
			return 1;

		}
	};
	
	/***
	 * 价格比较 低-》高
	 */
	Comparator<BusinessEntity> comparatorMoney2 = new Comparator<BusinessEntity>() {

		public int compare(BusinessEntity arg0, BusinessEntity arg1) {
			// 先排年龄
			if (arg0.avg_price > arg1.avg_price) {
				return 1;
			}
			return -1;

		}
	};
	
	
}
