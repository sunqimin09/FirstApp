package cn.com.bjnews.thinker.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import cn.com.bjnews.thinker.entity.ChannelEntity;
import cn.com.bjnews.thinker.entity.MainSettingEntity;
import cn.com.bjnews.thinker.entity.MenuEntity;


public class JsonSettings {
	
	public static MainSettingEntity parse(String settings){
		MainSettingEntity settingsEntity = new MainSettingEntity();
		ArrayList<ChannelEntity> channelListEntity = new ArrayList<ChannelEntity>();
		ChannelEntity channelEntity = null;
		ArrayList<MenuEntity> menuListEntity = new ArrayList<MenuEntity>();
		MenuEntity menuEntity = null;
		try {
			JSONObject object = new JSONObject(settings);
			JSONObject channelJson = object.getJSONObject("channel");
			//channel:
			settingsEntity.ChannelLastUpdate = (channelJson.getString("lastUpdate"));
			JSONArray channelListJson = channelJson.getJSONArray("channelList");
			for(int i =0;i<channelListJson.length();i++){
				JSONObject channelTempJson = channelListJson.getJSONObject(i);
				channelEntity = new ChannelEntity();
				channelEntity.id = (channelTempJson.getInt("id"));
				channelEntity.name = (channelTempJson.getString("name"));
				channelEntity.url = (channelTempJson.getString("url"));
				channelListEntity.add(channelEntity);
			}
			settingsEntity.channelList = (channelListEntity);
			Log.d("tag","channellist-->size"+channelListEntity.size());
			//channel end;startPic
			JSONObject startPicJson = object.getJSONObject("startPic");
			settingsEntity.StartPicLastUpdate = (startPicJson.getString("lastUpdate"));
			//? url???
			
			//about us
			JSONObject aboutUsJson = object.getJSONObject("aboutUs");
			settingsEntity.AboutUsLastUpdate = ( aboutUsJson.getString("lastUpdate"));
			settingsEntity.AboutUsEmail = (aboutUsJson.getString("email"));
			settingsEntity.AboutUsUrl = (aboutUsJson.getString("url"));
			// menu
			JSONObject menuJson = object.getJSONObject("customMenu");
			settingsEntity.MenuLastUpdate = ( menuJson.getString("lastUpdate"));
			JSONArray menusJson = menuJson.getJSONArray("menu");
			for(int j=0;j<menusJson.length();j++){
				JSONObject menuTempJson = menusJson.getJSONObject(j);
				menuEntity = new MenuEntity();
				menuEntity.setName(menuTempJson.getString("name"));
				menuEntity.setUrl(menuTempJson.getString("url"));
				menuListEntity.add(menuEntity);
			}
			settingsEntity.menus = (menuListEntity);
		} catch (JSONException e) {
			e.printStackTrace();
			return settingsEntity;
		}
		return settingsEntity;
	}
}
