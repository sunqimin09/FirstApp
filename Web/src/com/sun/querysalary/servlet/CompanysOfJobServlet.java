package com.sun.querysalary.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

import com.sun.querysalary.bean.CompanyBean;
import com.sun.querysalary.db.DbManager;
/**
 * 返回公司列表，查询哪些公司有该岗位
 * @author sunqm
 *
 */
public class CompanysOfJobServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CompanysOfJobServlet() {
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

		request.setCharacterEncoding("utf-8");
		JSONObject object = null;
		try {
			object = Json(queryComapanys(request));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
	
		if(null==object){
			out.println("{code:-1}");
		}else{
			out.println(object);
		}
		out.flush();
		out.close();
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
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
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
		dbManager = new DbManager();
	}

	private DbManager dbManager = null;
	
	private List<CompanyBean> queryComapanys(HttpServletRequest request) throws SQLException{
		String jobId = request.getParameter("jobId");
		String sql = "select companytable.id,companytable.name,count(job.id) from companytable " +
				"left join job on  companytable.id=job.companyId where job.id=  '"+jobId+"' order by avg(job.salary) desc";
		System.out.print(sql);
		ResultSet rs = dbManager.doQuery(sql);
		List<CompanyBean> list = new ArrayList<CompanyBean>();
		CompanyBean company = null;
		while(rs.next()){
			company = new CompanyBean();
			company.setId(rs.getInt("companytable.id"));
			company.setName(rs.getString("companytable.name"));
			company.setJobCount(rs.getInt(1));
			list.add(company);
		}
		return list;
	}
	
	private JSONObject Json(List<CompanyBean> list) throws JSONException{
		JSONObject object = new JSONObject();
		object.put("code", 0);
		object.put("size", list.size());
		JSONArray array = new JSONArray();
		JSONObject item = null;
		for(int i = 0;i<list.size();i++){
			item = new JSONObject();
			item.put("id",list.get(i).getId());
			item.put("name",list.get(i).getName()+"");
			System.out.println("name->"+list.get(i).getName());
			item.put("jobCount",list.get(i).getJobCount());
			array.put(item);
		}
		object.put("list", array);
		return object;
	}

}
