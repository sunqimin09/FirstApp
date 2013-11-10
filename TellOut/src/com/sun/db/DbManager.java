package com.sun.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sun.entity.RequestEntity;
import com.sun.entity.ResponseEntity;
import com.sun.utils.MConstant;

public class DbManager {

	private static String url = "jdbc:mysql://localhost:3306/tucao";

	private static String DRIVER = "com.mysql.jdbc.Driver";

	private static Connection conn = null;

	private Statement stmt = null;

	private String sql = null;

	/** 数据库用户名、秘密 */
	private static String user_name = "sun";

	private final static String pwd = "sun123";
	/** 数据库各字段 */
	private final int USER = 0;

	public DbManager() {
		if (conn == null)
			conn = getConn();
	}

	public static Connection getConn() {
		try {
			Class.forName(DRIVER);
			System.out.println("----conne-->class");
			conn = DriverManager.getConnection(url, user_name, pwd);
			System.out.println("----conne-->success");
			return conn;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 初始化数据库
	 */
	// public void dbInit() {
	// sql = "select * from user;";
	// try {
	// Class.forName(DRIVER);
	// System.out.println("----conne-->class");
	// conn = DriverManager.getConnection(url, user_name, pwd);
	// System.out.println("----conne-->success");
	// stmt = conn.createStatement();
	// ResultSet rst = stmt.executeQuery(sql);
	// System.out.println("----conne-->result-->" + rst.getRow());
	// } catch (ClassNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// }

	public ResponseEntity doRequest(RequestEntity request) {
		switch (request.getTypeId()) {
		case MConstant.REQUEST_REGIST:// 注册
			return doRegist(request.getRequest());
		case MConstant.REQUEST_LOGIN:// 登录
			return doLogin(request.getParams());
		case MConstant.WORLD_RANK:// 世界排名
			return doWorldRank(request.getRequest());
		case MConstant.INDUSTRY_RANK:// 行业排名
			return doIndustryRank(request.getRequest());
		case MConstant.COMPANY_RANK:// 企业排名
			return doCompany(request.getRequest());
		case MConstant.MY_INFOR:// 个人排名信息,
			return doMyInfor(request.getRequest());
		case MConstant.EDIT_MY_INFOR:
			return doEditInfor(request.getRequest());
		case MConstant.GET_MY_DETAIL://输入的详细信息
			return doGetMyDetail(request.getRequest());
		case MConstant.NEW_INDUSTRY:// 新建行业

			break;
		case MConstant.NEW_COMPANY:// 新建公司

			break;
		case MConstant.CHANGE_PASSWORLD:// 修改密码
			return changePassWord(request.getRequest());
		case MConstant.REQUEST_TELLOUTS:// 吐槽列表
			return doGetTellouts(request.getRequest());
		case MConstant.REQUEST_COMMENTS:
			return doGetComments(request.getRequest());
		case MConstant.REQUEST_NEW_TELLOUTS:// 新吐槽
			return doNewTellout(request.getRequest());
		case MConstant.REQUEST_NEW_COMMENTS:// 新建吐槽
			return doNewComment(request.getRequest());
		case MConstant.REQUEST_OK://赞
			doOk(request.getRequest());
			break;
		}

		ResponseEntity result = new ResponseEntity();
		return result;
	}
	/**
	 * 点击赞，取消赞
	 * @param request
	 */
	private ResponseEntity doOk(HttpServletRequest request) {
		// TODO Auto-generated method stub
		ResponseEntity result = new ResponseEntity();
		String telloutIdStr = request
				.getParameter(DbConstant.DB_COMMENT_TELLOUT_ID) != null ? request
				.getParameter(DbConstant.DB_COMMENT_TELLOUT_ID)
				: "1";
		String okOrNo = request.getParameter("ok") != null ? request
				.getParameter("ok") : "1";
		String sql = "update " + DbConstant.DB_TABLE_TELLOUTS + " set "
				+ DbConstant.DB_TELLOUT_OK + " ='" + DbConstant.DB_TELLOUT_OK
				+ okOrNo;
		int temp = doUpdate(sql);
		if (temp == 1)
			result.setCode(MConstant.ERROR_SUCCESS);
		else
			result.setCode(MConstant.ERROR_OTHER);
		return result;
	}

	/**
	 * 新建一个评论
	 * 
	 * @param request
	 */
	private ResponseEntity doNewComment(HttpServletRequest request) {
		ResponseEntity result = new ResponseEntity();
		String contentStr = request.getParameter(DbConstant.DB_COMMENT_CONTENT);
		String telloutIdStr = request
				.getParameter(DbConstant.DB_COMMENT_TELLOUT_ID)!=null?request
				.getParameter(DbConstant.DB_COMMENT_TELLOUT_ID):"1";
		String telloutAuthorId = request
				.getParameter(DbConstant.DB_COMMENT_AUTHOR_ID)!=null?request
						.getParameter(DbConstant.DB_COMMENT_AUTHOR_ID):"1";

		String sql = "insert into " + DbConstant.DB_TABLE_COMMENTS + " ("
				+ DbConstant.DB_COMMENT_CONTENT + ","
				+ DbConstant.DB_COMMENT_TELLOUT_ID + ","
				+ DbConstant.DB_COMMENT_AUTHOR_ID + ")" + " values('"
				+ contentStr + "','" + telloutIdStr + "','" + telloutAuthorId
				+ "')";
		try {
			if (doInsert(sql)) {// 失败
				result.setCode(MConstant.ERROR_OTHER);
			} else {// 成功
				result.setCode(MConstant.ERROR_SUCCESS);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 新建一个吐槽
	 * 
	 * @param request
	 * @return
	 */
	private ResponseEntity doNewTellout(HttpServletRequest request) {
		ResponseEntity result = new ResponseEntity();
		String sql = "insert into " + DbConstant.DB_TABLE_TELLOUTS;
		String authorId = request.getParameter(DbConstant.DB_TELLOUT_USER_ID)!=null?request.getParameter(DbConstant.DB_TELLOUT_USER_ID):"1";
		String content = request.getParameter(DbConstant.DB_COMMENT_CONTENT)!=null?request.getParameter(DbConstant.DB_COMMENT_CONTENT):"null";
		String ok = request.getParameter(DbConstant.DB_TELLOUT_OK)!=null?request.getParameter(DbConstant.DB_TELLOUT_OK):"0";
		sql = sql + " (" + DbConstant.DB_TELLOUT_USER_ID + ","
				+ DbConstant.DB_TELLOUT_CONTENT + ","
				+ DbConstant.DB_TELLOUT_OK + ") values " + "('" + authorId
				+ "','" + content + "','" + ok + "')";

		try {
			if (doInsert(sql)) {// 失败
				result.setCode(MConstant.ERROR_OTHER);
			} else {// 成功
				result.setCode(MConstant.ERROR_SUCCESS);
			}
			return result;
		} catch (SQLException e) {
			result.setCode(MConstant.ERROR_SQL_EXCEPTION);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获得评论列表
	 * 
	 * @param request
	 */
	private ResponseEntity doGetComments(HttpServletRequest request) {
		ResponseEntity result = new ResponseEntity();
		String telloutId = request.getParameter(DbConstant.DB_TELLOUT_ID)!=null? request.getParameter(DbConstant.DB_TELLOUT_ID):"1";
		String sql = "select * from user," + DbConstant.DB_TABLE_COMMENTS
				+ " where user." + DbConstant.DB_USER_ID + "="
				+ DbConstant.DB_TABLE_COMMENTS + "."
				+ DbConstant.DB_COMMENT_AUTHOR_ID + " and "
				+ DbConstant.DB_COMMENT_TELLOUT_ID + " = '" + telloutId+"'";
		try {
			ResultSet rs1 = doQuery(sql);
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Map<String, String> map = null;
			while (rs1.next()) {	
				map = new HashMap<String, String>();
				map.put(DbConstant.DB_COMMENT_CONTENT, rs1
						.getString(DbConstant.DB_COMMENT_CONTENT));
				map.put(DbConstant.DB_USER_NICK_NAME, rs1
						.getString(DbConstant.DB_USER_NICK_NAME));
				list.add(map);
			}
			result.setList(list);
			result.setCode(MConstant.ERROR_SUCCESS);
		} catch (SQLException e) {
			result.setCode(MConstant.ERROR_SQL_EXCEPTION);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 请求吐槽列表，请求参数有，当前显示的页码，
	 * 
	 * @param request
	 * @return 总数，显示的页码，20条数据:作者名，内容，赞的数量，评论的数量
	 */
	private ResponseEntity doGetTellouts(HttpServletRequest request) {
		ResponseEntity result = new ResponseEntity();
		String currentIndext = request
				.getParameter(DbConstant.OTHER_PAGE_INDEXT);
		int currentIndextInt = 0;
		if (currentIndext != null)
			currentIndextInt = Integer.parseInt(currentIndext);
		String sql1 = "select count(*) from " + DbConstant.DB_TABLE_TELLOUTS
				+ "";
		String sql2 = "select * from user," + DbConstant.DB_TABLE_TELLOUTS
				+ " where user." + DbConstant.DB_USER_ID + " ="
				+ DbConstant.DB_TABLE_TELLOUTS + "."
				+ DbConstant.DB_TELLOUT_USER_ID + "  limit "
				+ (currentIndextInt * 20) + ",20";
		/** 评论数量 */
		String sql3 = "select count(*) from " + DbConstant.DB_TABLE_TELLOUTS
				+ "," + DbConstant.DB_TABLE_COMMENTS + " where "
				+ DbConstant.DB_TABLE_TELLOUTS + "." + DbConstant.DB_TELLOUT_ID
				+ " = " + DbConstant.DB_TABLE_COMMENTS + "."
				+ DbConstant.DB_COMMENT_TELLOUT_ID + ","
				+ DbConstant.DB_TABLE_TELLOUTS + "." + DbConstant.DB_TELLOUT_ID
				+ "=" + "";
		try {
			ResultSet a = doQuery(sql1);
			ResultSet b = doQuery(sql2);
			System.out.println("tellouts-++++++++++++>start");
			Map<String, String> map = null;
			if (a.next()) {
				map = new HashMap<String, String>();
				int total = a.getRow() + 1;
				map.put(DbConstant.OTHER_TOTAL_SIZE, "" + total);
				result.setParams(map);
			}
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();

			while (b.next()) {
				map = new HashMap<String, String>();
				map.put(DbConstant.DB_TELLOUT_ID, b.getString(DbConstant.DB_TELLOUT_ID));
				map.put(DbConstant.DB_TELLOUT_CONTENT, b
						.getString(DbConstant.DB_TELLOUT_CONTENT));
				map.put(DbConstant.DB_USER_NICK_NAME, b
						.getString(DbConstant.DB_USER_NICK_NAME));
				map.put(DbConstant.DB_TELLOUT_OK, ""
						+ b.getInt(DbConstant.DB_TELLOUT_OK));
				list.add(map);
			}
			System.out.println("tellouts-++++++++++++>" + list.size());
			result.setList(list);
			result.setCode(MConstant.ERROR_SUCCESS);
			return result;
		} catch (SQLException e) {
			result.setCode(MConstant.ERROR_SQL_EXCEPTION);
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings("finally")
	private ResponseEntity doRegist(HttpServletRequest httpServletRequest) {
		System.out.println("sql-regist11223->" );
		String name = httpServletRequest.getParameter(DbConstant.DB_USER_NICK_NAME);
		String email = httpServletRequest.getParameter(DbConstant.DB_USER_EMAIL);
		String pwd = httpServletRequest.getParameter(DbConstant.DB_USER_PWD);
		
		ResponseEntity response = new ResponseEntity();
		String sql = "select * from user where "+DbConstant.DB_USER_NICK_NAME+" ='" + name + "'";
		String sql1 = "select * from user where "+DbConstant.DB_USER_EMAIL+" ='" + email
				+ "'";
		try {
			ResultSet a = doQuery(sql);
			ResultSet b = doQuery(sql1);
			if (a.next()) {
				response.setCode(MConstant.ERROR_NAME_EXIT);

				// return "用户名已存在";
			} else if (b.next()) {
				response.setCode(MConstant.ERROR_EMAIL_EXIT);
			} else {
				System.out.println("sql-regist11->" );
				sql = "insert into user  (" + DbConstant.DB_USER_NICK_NAME + ","
						+ DbConstant.DB_USER_PWD + ","
						+ DbConstant.DB_USER_EMAIL + ") values ('" + name + "','"
						+ pwd + "','" + email + "');";
				boolean aa = doInsert(sql);
				System.out.println("sql-regist->" + aa);
				if (aa) {
					response.setCode(MConstant.ERROR_OTHER);
					response.setError_str("注册失败");
				} else {
					String sqlTemp = "select " + DbConstant.DB_USER_ID
							+ " from user where "
							+ DbConstant.DB_USER_NICK_NAME + " ='" + name + "'";
					ResultSet aTemp = doQuery(sqlTemp);
					if (aTemp.next()) {
						int id = aTemp.getInt(DbConstant.DB_USER_ID);
						System.out.println("注册--登录成功-->" + id);
						Map<String, String> hashmap = new HashMap<String, String>();
						hashmap.put(DbConstant.DB_USER_ID, id + "");
						response.setParams(hashmap);
					}
					response.setCode(MConstant.ERROR_SUCCESS);
				}
			}
			a.close();
			b.close();
		} catch (SQLException e) {
			response.setCode(MConstant.ERROR_SQL_EXCEPTION);
			e.printStackTrace();
		} finally {
			return response;
		}
	}

	@SuppressWarnings("finally")
	private ResponseEntity doLogin(Map<String, String> map) {
		ResponseEntity response = new ResponseEntity();
		String sql = "select "+DbConstant.DB_USER_ID+" from user where email = '" + map.get("email")
				+ "' and pwd = '" + map.get("pwd") + "'";
		try {
			ResultSet rs = doQuery(sql);
			if (rs.next()) {
				response.setCode(MConstant.ERROR_SUCCESS);
				int id = rs.getInt(DbConstant.DB_USER_ID);
				System.out.println("登录成功-->");
				Map<String, String> hashmap = new HashMap<String, String>();
				hashmap.put(DbConstant.DB_USER_ID, id + "");
				response.setParams(hashmap);
			} else {
				response.setCode(MConstant.ERROR_OTHER);
				response.setError_str("");
			}
		} catch (SQLException e) {
			response.setCode(MConstant.ERROR_SQL_EXCEPTION);
			e.printStackTrace();
		} finally {
			return response;
		}

	}

	private ResponseEntity doWorldRank(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		String sql = "select * from user,_industry where user.industry_id=_industry.id order by score desc limit 10";
		String sql1 = "select * from user where id ='"
				+ request.getParameter(DbConstant.DB_USER_ID) + "'";
		try {
			ResultSet rs = doQuery(sql);
			ResultSet rs1 = doQuery(sql1);
			if (rs1.next()) {
				String userName = rs1.getString(DbConstant.DB_USER_NICK_NAME);
				String score = rs1.getString(MConstant.SCORE);
				String industry = rs1.getString(MConstant.INDUSTRY_ID);

				Map<String, String> map = new HashMap<String, String>();
				map.put(DbConstant.DB_USER_NICK_NAME, userName);
				map.put(MConstant.SCORE, score);
				map.put(MConstant.INDUSTRY_ID, industry);
				response.setParams(map);
			}
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Map<String, String> map = null;
			while (rs.next()) {
				System.out.println("while--company>");
				map = new HashMap<String, String>();
				map.put("name", rs.getString("name"));
				map.put(MConstant.INDUSTRY_ID, rs
						.getString(MConstant.INDUSTRY_ID));
				map.put("score", rs.getString("score"));
				map.put("industry_name", rs.getString("_industry.name"));
				list.add(map);
			}
			System.out.print("list-size-->" + list.size());
			rs.close();
			rs1.close();
			response.setList(list);
			response.setCode(MConstant.ERROR_SUCCESS);
		} catch (SQLException e) {
			response.setCode(MConstant.ERROR_SQL_EXCEPTION);
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 行业排名
	 * 
	 * @param request
	 * @return
	 */
	private ResponseEntity doIndustryRank(HttpServletRequest request) {
		String userId = request.getParameter(DbConstant.DB_USER_ID);
		ResponseEntity response = new ResponseEntity();
		// String
		// sql="select * from user,company where user.company_id=company.id group by user.company_id order by user.score desc limit 10";//" where "+MConstant.REGION_ID
		// +" ='"+region_id+

		String sql = "select * from user,_industry where user.industry_id=_industry.id group by user."
				+ MConstant.INDUSTRY_ID
				+ " order by user.score "
				+ " desc limit 10";

		try {
			ResultSet rs = doQuery(sql);
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Map<String, String> map = null;
			while (rs.next()) {
				System.out.println("while--company>");
				map = new HashMap<String, String>();
				map.put("name", rs.getString("_industry.name"));
				map.put("score", rs.getString("user.score"));
				list.add(map);
			}
			response.setList(list);
			response.setCode(MConstant.ERROR_SUCCESS);
		} catch (SQLException e) {
			response.setCode(MConstant.ERROR_SQL_EXCEPTION);
			e.printStackTrace();
		}
		return response;
	}

	private ResponseEntity doCompany(HttpServletRequest request) {
		// map.put("industry_id", request.getParameter(MConstant.INDUSTRY_ID));
		// map.put("region_id", request.getParameter(MConstant.REGION_ID));
		// map.put("top_bottom", request.getParameter(MConstant.));
		// map.put("company_id", request.getParameter(MConstant.COMPANY_ID));
		String region_id = request.getParameter("region_id") == null ? "0"
				: request.getParameter("region_id");
		// String company_id = request.getParameter("company_id");
		ResponseEntity response = new ResponseEntity();
		// where user.industry_id=_industry.id order by score desc limit 10";
		String sql = "select * from user,company where user.company_id=company.id group by user.company_id order by user.score desc limit 10";// " where "+MConstant.REGION_ID
																																				// +" ='"+region_id+
		String sql1 = "select * from ";
		try {
			ResultSet rs = doQuery(sql);
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Map<String, String> map = null;
			while (rs.next()) {
				System.out.println("while--company>");
				map = new HashMap<String, String>();
				map.put("name", rs.getString("name"));
				map.put("score", rs.getString("score"));
				map.put("company_name", rs.getString("company.name"));
				list.add(map);
			}
			response.setList(list);
			response.setCode(MConstant.ERROR_SUCCESS);
		} catch (SQLException e) {
			response.setCode(MConstant.ERROR_SQL_EXCEPTION);
			e.printStackTrace();
		}
		return response;

	}

	// private ResponseEntity doIndustryRank(Map<String, String> params){
	// ResponseEntity response = new ResponseEntity();
	// String sql="select top 10 * from user group by "+MConstant.INDUSTRY_ID;
	// return response;
	// }

	/**
	 * 1.请求参数 userid 2.返回 我的昵称、我的得分、我的世界排名、我的地区排名、我所在行业排名
	 */
	private ResponseEntity doMyInfor(HttpServletRequest request) {
		/**
		 * 1.查询 score 的排名 sql = select count(*) from user where score>(select
		 * score from id where id = user_id) 2.查询score 的排名 地区 where region =
		 * region_id 3.查询score 的排名 行业 sql where industry = industry_id
		 */

		ResponseEntity response = new ResponseEntity();
		/** 查询用户名，得分 */
		String sql = "select * from user where id = '"
				+ request.getParameter(DbConstant.DB_USER_ID) + "'";
		// 世界排名
		String sql1 = "select count(*) from user where score > (select score from user where id = '"
				+ request.getParameter(DbConstant.DB_USER_ID) + "')";
		String sql2 = sql1 + " and " + MConstant.REGION_ID + "=" + "(select "
				+ MConstant.REGION_ID + " from user where id = '"
				+ request.getParameter(DbConstant.DB_USER_ID) + "')";
		String sql3 = sql1 + " and " + MConstant.INDUSTRY_ID + "=" + "(select "
				+ MConstant.INDUSTRY_ID + " from user where id = '"
				+ request.getParameter(DbConstant.DB_USER_ID) + "')";
		try {
			ResultSet rs = doQuery(sql);
			ResultSet rs1 = doQuery(sql1);
			ResultSet rs2 = doQuery(sql2);
			ResultSet rs3 = doQuery(sql3);
			Map<String, String> result = new HashMap<String, String>();
			if (rs.next()) {// 我的昵称，得分
				result.put(DbConstant.DB_USER_NICK_NAME, rs
						.getString(DbConstant.DB_USER_NICK_NAME));
				result.put(MConstant.SCORE, rs.getString(MConstant.SCORE));
			}
			if (rs1.next()) {// 我的世界排名
				result.put("worldRank", "" + (rs1.getRow() + 1));
			}
			if (rs2.next()) {// 我的地区排名
				result.put("regionRank", "" + (rs2.getRow() + 1));
			}
			if (rs3.next()) {// 行业排名
				result.put("industryRank", "" + (rs3.getRow() + 1));
			}
			response.setParams(result);
			rs.close();
			rs1.close();
			rs2.close();
			rs3.close();
			response.setCode(MConstant.ERROR_SUCCESS);
		} catch (SQLException e) {
			response.setCode(MConstant.ERROR_SQL_EXCEPTION);
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 修改我的个人信息
	 * 
	 * @param request
	 * @return
	 */
	private ResponseEntity doEditInfor(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		String salary = request.getParameter(MConstant.SALARY);
		String salaryPer = request.getParameter(MConstant.SALARY_PER);
		String environment = request.getParameter(MConstant.ENVIRONMENT);
		String environmentPer = request.getParameter(MConstant.ENVIRONMENT_PER);
		String future = request.getParameter(MConstant.FUTURE);
		String futurePer = request.getParameter(MConstant.FUTURE_PER);
		String other = request.getParameter(MConstant.OTHER);
		String otherPer = request.getParameter(MConstant.OTHER_PER);
		String regionId = request.getParameter(MConstant.REGION_ID);
		String industryId = request.getParameter(MConstant.INDUSTRY_ID);
		String companyId = request.getParameter(MConstant.COMPANY_ID);

		int salaryInt = Integer.parseInt(salary);
		int salaryPerInt = Integer.parseInt(salaryPer);
		int environmentInt = Integer.parseInt(environment);
		int environmentPerInt = Integer.parseInt(environmentPer);
		int futureInt = Integer.parseInt(future);
		int futurePerInt = Integer.parseInt(futurePer);
		int otherInt = Integer.parseInt(other);
		int otherPerInt = Integer.parseInt(otherPer);

		int score = salaryInt
				+ 100
				/ salaryPerInt
				* (environmentInt * environmentPerInt + futureInt
						* futurePerInt + otherInt * otherPerInt);

		String sql = "update user SET " + DbConstant.DB_USER_NICK_NAME + "='"
				+ request.getParameter(DbConstant.DB_USER_NICK_NAME) + "',"
				+ MConstant.SALARY + "='" + salary + "',"
				+ MConstant.SALARY_PER + "='" + salaryPer + "',"
				+ MConstant.ENVIRONMENT + "='" + environment + "',"
				+ MConstant.ENVIRONMENT_PER + "='" + environmentPer + "',"
				+ MConstant.FUTURE + "='" + future + "',"
				+ MConstant.FUTURE_PER + "='" + futurePer + "',"
				+ MConstant.OTHER + "='" + other + "'," + MConstant.OTHER_PER
				+ "='" + otherPer + "', " + MConstant.REGION_ID + "='"
				+ regionId + "'," + MConstant.INDUSTRY_ID + "='" + industryId
				+ "', " + MConstant.COMPANY_ID + "='" + companyId + "', "
				+ MConstant.SCORE + "='" + score + "' " + "where id ='"
				+ request.getParameter(DbConstant.DB_USER_ID) + "'";
		int result = doUpdate(sql);
		if (result == 1)
			response.setCode(MConstant.ERROR_SUCCESS);
		else
			response.setCode(MConstant.ERROR_OTHER);
		return response;
	}

	/**
	 * 获取我的详细信息
	 * 
	 * @param request
	 * @return
	 */
	private ResponseEntity doGetMyDetail(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		String sql = "select * from user where " + DbConstant.DB_USER_ID + " ='"
				+ request.getParameter(DbConstant.DB_USER_ID) + "'";
		try {
			ResultSet rs = doQuery(sql);
			// response.setRs(rs);
			if (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put(DbConstant.DB_USER_NICK_NAME, rs.getString(DbConstant.DB_USER_NICK_NAME));
				map.put(DbConstant.DB_USER_SALARY, rs.getString(DbConstant.DB_USER_SALARY));
				map.put(DbConstant.DB_USER_SALARY_PER, rs
						.getString(DbConstant.DB_USER_SALARY_PER));
//				map.put(MConstant.ENVIRONMENT, rs
//						.getString(MConstant.ENVIRONMENT));
//				map.put(MConstant.ENVIRONMENT_PER, rs
//						.getString(MConstant.ENVIRONMENT_PER));
				map.put(MConstant.FUTURE, rs.getString(MConstant.FUTURE));
				map.put(MConstant.FUTURE_PER, rs
						.getString(MConstant.FUTURE_PER));
				map.put(MConstant.OTHER, rs.getString(MConstant.OTHER));
				map.put(MConstant.OTHER_PER, rs.getString(MConstant.OTHER_PER));
				map.put(MConstant.INDUSTRY_ID, rs
						.getString(MConstant.INDUSTRY_ID));
				map.put(MConstant.REGION_ID, rs.getString(MConstant.REGION_ID));
				map.put(MConstant.COMPANY_ID, rs
						.getString(MConstant.COMPANY_ID));
				response.setParams(map);
			}

			response.setCode(MConstant.ERROR_SUCCESS);
		} catch (SQLException e) {
			response.setCode(MConstant.ERROR_SQL_EXCEPTION);
			e.printStackTrace();
		}
		return response;
	}

	private ResponseEntity doNewINdustry(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		String sql = null;
		if (request.getParameter(MConstant.REQUEST_CODE) == "0") {// 是否存在
			String industryName = request.getParameter("industryName");
			sql = "select * from user where " + DbConstant.DB_USER_ID + " ='"
					+ industryName + "'";
		} else if (request.getParameter(MConstant.REQUEST_CODE) == "1") {// 新建
			String industryName = request.getParameter("industryName");
			sql = "select * from user where " + DbConstant.DB_USER_ID + " ='"
					+ request.getParameter(DbConstant.DB_USER_ID) + "'";
		}
		// String sql
		// ="select * from user where "+DbConstant.DB_USER_ID+" ='"+request.getParameter(DbConstant.DB_USER_ID)+"'";

		// ResultSet rs = doQuery(sql);

		return response;
	}

	private ResponseEntity changePassWord(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		String sql1 = "select * from user where id ='"
				+ request.getParameter(DbConstant.DB_USER_ID) + "' and pwd ='"
				+ request.getParameter(DbConstant.DB_USER_PWD) + "'";

		String sql = "update user set pwd ='" + request.getParameter("newPwd")
				+ "' where " + DbConstant.DB_USER_ID + "=' "
				+ request.getParameter(DbConstant.DB_USER_ID) + "'";

		try {
			ResultSet rs = doQuery(sql1);
			if (rs.next()) {
				int result = doUpdate(sql);
				if (result == 1) {
					response.setCode(MConstant.ERROR_SUCCESS);
				} else {
					response.setCode(MConstant.ERROR_OTHER);
				}
			} else {
				response.setCode(MConstant.ERROR_OTHER);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	public ResultSet doQuery(String sql) throws SQLException {

		if (conn == null) {
			conn = getConn();
		}
		stmt = conn.createStatement();
		System.out.println("sql-Query->" + sql);
		ResultSet rs = stmt.executeQuery(sql);
		return rs;

	}

	public boolean doInsert(String sql) throws SQLException {
		if (conn == null) {
			conn = getConn();
		}
		stmt = conn.createStatement();
		System.out.println("sql-insert->" + sql);
		boolean rs = stmt.execute(sql);
		stmt.close();
		return rs;

	}

	public int doUpdate(String sql) {
		try {
			if (conn == null) {
				conn = DriverManager.getConnection(url, user_name, pwd);
			}
			if (stmt == null) {
				stmt = conn.createStatement();
			}
			System.out.println("sql--update>" + sql);
			int rs = stmt.executeUpdate(sql);
			// if(rs==1)//更新一条记录成功
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
