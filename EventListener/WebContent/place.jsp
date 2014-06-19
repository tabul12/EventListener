<%@page import="objects.Place"%>
<%@page import="baseConnection.PlaceManager"%>
<%@ page language="java" contentType="text/html;  charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

	<%
		int placeID = Integer.parseInt(request.getParameter("id"));
		PlaceManager manager = (PlaceManager)application.getAttribute("PlaceManager");
		Place place = manager.getPlace(placeID);
		
		out.println("<p> you chose this bandId=" + placeID + "name is '" + place.getName() + "' </p>");
	%>
</body>
</html>