<%@page import="objects.Band"%>
<%@page import="baseConnection.BandManager"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

	<%
		int bandID = Integer.parseInt(request.getParameter("id"));
		BandManager manager = (BandManager)application.getAttribute("BandManager");
		Band band = manager.getBand(bandID);
		
		out.println("<p> you chose this bandId=" + bandID + "name is '" + band.getName() + "' </p>");
	%>
</body>
</html>