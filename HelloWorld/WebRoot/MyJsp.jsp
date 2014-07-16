<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.sun.developerdown.bean.DownLoadEntity"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'MyJsp.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		
		<br>
		<table width="755" height="189">
			<tr>
				<td>
					Android 
					<br>
				</td>
				<td>
				
				</td>
			</tr>
			<%
				List<DownLoadEntity> list = (List<DownLoadEntity>) request.getAttribute("data");
				Iterator<DownLoadEntity> it = list.iterator();
				while (it.hasNext()) {
					DownLoadEntity team = it.next();
			%>
			<tr>
				<td><%=team.name%></td>
				<td><%=team.url%></td>
			</tr>
			<%
				}
			%>

		</table>
	</body>
</html>
