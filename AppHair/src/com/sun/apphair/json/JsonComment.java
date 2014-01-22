/**
 * 
 */
package com.sun.apphair.json;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.apphair.entity.CommentEntity;
import com.sun.apphair.entity.ResponseResult;
import com.sun.apphair.entity.ShowResult;
import com.sun.apphair.internet.IRequestCallBack;
import com.sun.apphair.utils.Mconstant;

/**
 * 项目名称：Hair
 * 文件名：JsonComment.java  
 * 作者：@sunqm    
 * 创建时间：2014-1-22 下午2:35:52
 * 功能描述:  
 * 版本 V 1.0               
 */
public class JsonComment {
	
	/**
	 * 评论
	 * @param responseResult
	 * @param requestCallBack
	 * @return
	 */
	public ShowResult parse(ResponseResult responseResult,IRequestCallBack requestCallBack){
		ShowResult showResult = new ShowResult();
		ArrayList<CommentEntity> list = new ArrayList<CommentEntity>();
		try {
			JSONObject object = new JSONObject(responseResult.resultStr);
			
			JSONArray array = object.getJSONArray("list");
			CommentEntity entity =  null;
			JSONObject item = null;
			for(int i =0;i<array.length();i++){
				item = array.getJSONObject(i);
				entity = new CommentEntity();
				entity.content = (item.getString("content"));
				entity.id = (item.getInt("id"));
				entity.createTime = item.getString("createTime");
				entity.ratingbarScore = (float) item.getDouble("ratingbarScore");
				list.add(entity);
			}
			showResult.list = list;
		} catch (JSONException e) {
			requestCallBack.requestFailedStr(Mconstant.ERROR_JSON);
			e.printStackTrace();
			return null;
		}
		return showResult;
	}
	
}
