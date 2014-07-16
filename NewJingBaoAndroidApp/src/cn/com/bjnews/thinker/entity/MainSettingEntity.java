package cn.com.bjnews.thinker.entity;

import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @author sunqm
 * Create at:   2014-5-16 上午8:31:49 
 * TODO 全局参数配置
 */
public class MainSettingEntity {
	
	/**上次更新日期*/
	public String ChannelLastUpdate;
	
	public ArrayList<ChannelEntity> channelList = new ArrayList<ChannelEntity>();
	
	/**启动页上次更新日期*/
	public String StartPicLastUpdate;
	
	/**启动页图片地址*/
	public String StartPicUrl;
	
	public String AboutUsLastUpdate;
	
	public String AboutUsEmail;
	
	public String AboutUsUrl;
	
	// 菜单
	public String MenuLastUpdate;

	public ArrayList<MenuEntity> menus = new ArrayList<MenuEntity>();

//	public String getChannelLastUpdate() {
//		return ChannelLastUpdate;
//	}
//
//	public void setChannelLastUpdate(String channelLastUpdate) {
//		ChannelLastUpdate = channelLastUpdate;
//	}
//
//	public ArrayList<ChannelEntity> getChannelList() {
//		return channelList;
//	}
//
//	public void setChannelList(ArrayList<ChannelEntity> channelList) {
//		this.channelList = channelList;
//	}
//
//	public String getStartPicLastUpdate() {
//		return StartPicLastUpdate;
//	}
//
//	public void setStartPicLastUpdate(String startPicLastUpdate) {
//		StartPicLastUpdate = startPicLastUpdate;
//	}
//
//	public String getStartPicUrl() {
//		return StartPicUrl;
//	}
//
//	public void setStartPicUrl(String startPicUrl) {
//		StartPicUrl = startPicUrl;
//	}
//
//	public String getAboutUsLastUpdate() {
//		return AboutUsLastUpdate;
//	}
//
//	public void setAboutUsLastUpdate(String aboutUsLastUpdate) {
//		AboutUsLastUpdate = aboutUsLastUpdate;
//	}
//
//	public String getAboutUsEmail() {
//		return AboutUsEmail;
//	}
//
//	public void setAboutUsEmail(String aboutUsEmail) {
//		AboutUsEmail = aboutUsEmail;
//	}
//
//	public String getAboutUsUrl() {
//		return AboutUsUrl;
//	}
//
//	public void setAboutUsUrl(String aboutUsUrl) {
//		AboutUsUrl = aboutUsUrl;
//	}
//
//	public String getMenuLastUpdate() {
//		return MenuLastUpdate;
//	}
//
//	public void setMenuLastUpdate(String menuLastUpdate) {
//		MenuLastUpdate = menuLastUpdate;
//	}
//
//	public ArrayList<MenuEntity> getMenus() {
//		return menus;
//	}
//
//	public void setMenus(ArrayList<MenuEntity> menus) {
//		this.menus = menus;
//	}

	/**
	 * 是否一致
	 * @return true: 一致； false  不一致
	 */
	public boolean compare(MainSettingEntity entity){
		if(this.ChannelLastUpdate==null||entity.ChannelLastUpdate==null){//一个没有更新数据库
			return false;
		}
		return this.ChannelLastUpdate.equals(entity.ChannelLastUpdate);
	}
	
	public String toString(){
		return ChannelLastUpdate+""+"";
	}
	
}
