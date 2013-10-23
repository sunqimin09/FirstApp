package com.sun.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

import com.sun.db.DbManager;
import com.sun.entity.RequestEntity;
import com.sun.entity.ResponseEntity;
import com.sun.utils.MConstant;

/**
 * requestCode ：1 查询是否存在，2 创建该公司
 * 
 * @author sunqm
 * 
 */
public class NewCompanyServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public NewCompanyServlet() {
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
//		RequestEntity requestEntity = new RequestEntity();
//		requestEntity.setTypeId(MConstant.NEW_COMPANY);
//		requestEntity.setRequest(request);
//
//		ResponseEntity responseEntity = null;
//		if (null != request.getParameter(MConstant.REQUEST_CODE)) {
//			// 查询的结果
//			responseEntity = new DbManager().doRequest(requestEntity);
//		} else {
//			responseEntity = new ResponseEntity();
//			responseEntity.setCode(MConstant.FAILED);
//		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(request(request));
		out.flush();
		out.close();
	}

	private JSONObject request(HttpServletRequest request) {
		JSONObject object = new JSONObject();
		String requestCode = request.getParameter(MConstant.REQUEST_CODE);
		String companyName = request.getParameter("companyName");
		String sql = "";
		try {
			try {
				if (null!=request&&requestCode.equals("1")) {// 查询
					sql = "select * from company where name like '"
							+ companyName + "%'";
					ResultSet rs = new DbManager().doQuery(sql);

					JSONObject result = new JSONObject();
					JSONArray companys = new JSONArray();
					result.put("company", companys);
					JSONObject item = null;
					while (rs.next()) {
						item = new JSONObject();
						item.put("name", rs.getString("name"));
						item.put("id", rs.getString("id"));

						companys.put(item);
					}
					result.put("list_size", rs.getFetchSize());
					object.put("code", MConstant.SUCCESS);
					object.put("result", result);
				} else if (null!=request&&requestCode.equals("2")) {// 创建新的
					sql = "insert into company (name) values ('" + companyName
							+ "')";
					boolean resultBool = new DbManager().doInsert(sql);
					JSONObject result = new JSONObject();
					if (resultBool) {
						object.put("code", MConstant.FAILED);
					} else {
						object.put("code", MConstant.SUCCESS);
					}

					object.put("result", result);

				}
			} catch (SQLException e) {
				object.put("code", MConstant.SQL_EXCEPTION);
				e.printStackTrace();
			}
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
