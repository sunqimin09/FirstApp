package com.sun.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

import com.sun.db.DbConstant;
import com.sun.db.DbManager;
import com.sun.entity.RequestEntity;
import com.sun.entity.ResponseEntity;
import com.sun.utils.MConstant;

/**
 * 我的排名
 * 
 * @author sunqm
 * 
 */
public class MyRankInforServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MyRankInforServlet() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1.请求参数 userid 
		 * 2.返回 我的昵称、我的得分、我的世界排名、我的地区排名、我所在行业排名
		 */

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setTypeId(MConstant.GET_MY_RANK_INFOR);
		requestEntity.setRequest(request);

		ResponseEntity responseEntity = null;
//		if (null != request.getParameter(DbConstant.DB_USER_ID)) {
			// 查询的结果
			responseEntity = new DbManager().doRequest(requestEntity);
//		} else {
//			responseEntity = new ResponseEntity();
//			responseEntity.setCode(MConstant.FAILED);
//		}

		// 返回我的信息，我的世界排名
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(change(responseEntity));
		out.flush();
		out.close();
	}

	private JSONObject change(ResponseEntity responseEntity) {
		JSONObject object = new JSONObject();
		try {
			object.put("code", responseEntity.getCode());
			JSONObject result = new JSONObject();
			object.put("result", result);
			Map<String, String> map = responseEntity.getParams();
			result.put("worldRank", map.get("worldRank"));
			result.put("regionRank", map.get("regionRank"));
			result.put(DbConstant.DB_USER_NICK_NAME, map.get(DbConstant.DB_USER_NICK_NAME));
			result.put(DbConstant.DB_USER_SCORE, map.get(DbConstant.DB_USER_SCORE));
			result.put("industryRank", map.get("industryRank"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
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
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
