package com.sun.hair.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.sun.hair.entity.DistrictsEntity;

/**
 * h获取地区
 * @author sunqm
 *
 */
public class RegionsService extends BaseService{

	/**
	 *{"status":"OK","cities":[{"city_name":"北京","districts":[{"district_name":"朝阳区","neighborhoods":["朝阳其它","安贞","劲松/潘家园","建外大街","朝外大街","朝阳公园/团结湖","左家庄","亮马桥/三元桥","亚运村","望京","大望路","对外经贸","三里屯","酒仙桥","双井","国贸","首都机场","管庄","十八里店","十里堡","北苑家园","东坝","马泉营","孙河","定福庄","北沙滩","大屯","小庄/红庙","常营","798/大山子","北京东站","霄云路","蓝色港湾","燕莎/农业展览馆","姚家园","十里河","立水桥","小营","百子湾","工人体育场","慈云寺/八里庄","甜水园","高碑店","北京欢乐谷","双桥","传媒大学/二外","石佛营","青年路","太阳宫","四惠"]},{"district_name":"东城区","neighborhoods":["朝阳门","安定门","东四","建国门/北京站","王府井/东单","天坛","崇文门","东直门","和平里","广渠门","沙子口","左安门","东四十条","雍和宫/地坛","南锣鼓巷/鼓楼东大街","光明楼/龙潭湖","北新桥/簋街","沙滩/美术馆灯市口"]},{"district_name":"海淀区","neighborhoods":["紫竹桥","航天桥","北下关","公主坟/万寿路","北太平庄","苏州桥","中关村","五道口","海淀其它","上地","颐和园","魏公村","清河","五棵松","双榆树","远大路","香山","农业大学西区","军博","人民大学","四季青","学院桥","万柳","大钟寺","西三旗","知春路"]},{"district_name":"西城区","neighborhoods":["前门","广外大街","宣武门","广内大街","地安门","新街口","西直门/动物园","阜成门","复兴门","西单","牛街","虎坊桥","月坛","什刹海","西四","菜市口","德外大街","陶然亭","南菜园/白纸坊"]},{"district_name":"近郊","neighborhoods":["怀柔","近郊其它","门头沟","平谷","密云县","延庆县"]},{"district_name":"丰台区","neighborhoods":["丰台其它","六里桥/丽泽桥","方庄","洋桥/木樨园","右安门","北大地","刘家窑","开阳里","青塔","草桥","看丹桥","花乡","大红门","卢沟桥","公益西桥","云岗","分钟寺/成寿寺","北京西站/六里桥","马家堡/角门","夏家胡同/纪家庙","丽泽桥/丰管路","总部基地"]},{"district_name":"石景山区","neighborhoods":["石景山其它","鲁谷","古城/八角","苹果园","模式口"]},{"district_name":"大兴区","neighborhoods":["黄村","旧宫","亦庄","西红门"]},{"district_name":"昌平区","neighborhoods":["昌平镇","天通苑","回龙观","小汤山","北七家","南口镇","沙河"]},{"district_name":"通州区","neighborhoods":["新华大街","梨园","果园","九棵树","武夷花园","通州北苑"]},{"district_name":"房山区","neighborhoods":["良乡"]},{"district_name":"顺义区","neighborhoods":["国展","顺义"]}]}]}

	 */
	@Override
	public Object parse(String result) {
		
		List<DistrictsEntity> list = new ArrayList<DistrictsEntity>();
		String status = null;
		try {
			JSONObject object = new JSONObject(result);
			status = object.getString("status");
			if(status.equals("OK")){
				JSONObject city = object.getJSONArray("cities").getJSONObject(0);
				list = parseDistricts(city.getJSONArray("districts"));
			}else{
				
			}
			Log.d("tag","parse-->"+status);
		} catch (JSONException e) {
			status = "数据错误";
			e.printStackTrace();
		}
		return list;
	}

	private List<DistrictsEntity> parseDistricts(JSONArray jsonArray) throws JSONException {
		List<DistrictsEntity> list = new ArrayList<DistrictsEntity>();
		DistrictsEntity entity = null;
		JSONObject item = null;
		for(int i=0;i<jsonArray.length();i++){
			entity = new DistrictsEntity();
			item = jsonArray.getJSONObject(i);
			entity.name = item.getString("district_name");
			entity.list = parseNeigh(item.getJSONArray("neighborhoods"));
			list.add(entity);
		}
		return list;
	}
	
	private List<String> parseNeigh(JSONArray array) throws JSONException{
		List<String> list = new ArrayList<String>();
		for(int i=0;i<array.length();i++){
			list.add(array.getString(i));
		}
		return list;
	}
	
	
}
