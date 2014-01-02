package com.example.msalary.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.msalary.entity.JobEntity;
import com.example.msalary.entity.ResponseResult;
import com.example.msalary.entity.ShowResult;
import com.example.msalary.internet.IRequestCallBack;
import com.example.msalary.util.ErrorCodeUtils;

/**
 * 工资排名--公司
 * @author sunqm
 * Create at:   2013-12-29 下午2:51:18 
 * TODO
 */
public class JsonSalaryRank {
	
	public static ShowResult parse(ResponseResult responseResult,IRequestCallBack requestCallBack){
		ShowResult showResult = new ShowResult();
		try {
			JSONObject object = new JSONObject(responseResult.resultStr);
			int code = object.getInt("code");
			JSONArray array = object.getJSONArray("list");
			List<JobEntity> list = new ArrayList<JobEntity>();
			JobEntity entity =  null;
			JSONObject item = null;
			for(int i =0;i<array.length();i++){
				item = array.getJSONObject(i);
				entity = new JobEntity();
				entity.setId(item.getInt("id"));
				entity.setSalary(item.getInt("salary"));
				entity.setName(item.getString("name"));
//				entity.setCompanyCount(item.getInt("companyCounts"));
				list.add(entity);
			}
			showResult.list = list;
			showResult.resultCode =code;
		} catch (JSONException e) {
			requestCallBack.requestFailedStr(ErrorCodeUtils.changeCodeToStr(-101));
			e.printStackTrace();
			return null;
		}
		return showResult;
	}
}
