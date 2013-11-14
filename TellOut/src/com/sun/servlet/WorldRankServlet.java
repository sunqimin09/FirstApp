package com.sun.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

import com.sun.db.DbConstant;
import com.sun.db.DbManager;
import com.sun.entity.RequestEntity;
import com.sun.entity.ResponseEntity;
import com.sun.utils.MConstant;
/**
 * 世界排名
 * @author sunqm
 *
 */
public class WorldRankServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public WorldRankServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setTypeId(MConstant.WORLD_RANK);
		requestEntity.setRequest(request);

		ResponseEntity responseEntity = null;
//		if (null != request.getParameter(DbConstant.DB_USER_ID)) {
			// 查询的结果
			responseEntity = new DbManager().doRequest(requestEntity);
//		}else{
//			responseEntity =new ResponseEntity();
//			responseEntity.setCode(MConstant.ERROR_OTHER);
//		}
		
		response.setCharacterEncoding("utf8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(change(responseEntity));
		out.flush();
		out.close();
	}

	private JSONObject change(ResponseEntity responseEntity){
		JSONObject object = new JSONObject();
		try {
			object.put("code", responseEntity.getCode());
			JSONObject result = new JSONObject();
			object.put("result", result);
			JSONArray jsonArray = new JSONArray();
			Map<String,String> map = responseEntity.getParams();
			result.put("worldRank", jsonArray);
			/**我的信息*/
			result.put(DbConstant.DB_USER_NICK_NAME, map.get(DbConstant.DB_USER_NICK_NAME));
			result.put(MConstant.SCORE, map.get(MConstant.SCORE));
			result.put(DbConstant.DB_INDUSTRY_ID, map.get(DbConstant.DB_INDUSTRY_ID));
			
			List<Map<String,String>> list =responseEntity.getList();
			result.put(DbConstant.OTHER_TOTAL_SIZE, list.size());
			JSONObject item = null;
			for(int i = 0;i<list.size();i++){
				item = new JSONObject();
				item.put(DbConstant.DB_USER_NICK_NAME, list.get(i).get(DbConstant.DB_USER_NICK_NAME));
				item.put("score", list.get(i).get("score"));
				item.put(DbConstant.DB_USER_SALARY, list.get(i).get(DbConstant.DB_USER_SALARY));
				item.put(DbConstant.DB_INDUSTRY_ID, list.get(i).get(DbConstant.DB_INDUSTRY_ID));
				item.put(DbConstant.DB_INDUSTRY_NAME, list.get(i).get(DbConstant.DB_INDUSTRY_NAME));
				jsonArray.put(item);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
