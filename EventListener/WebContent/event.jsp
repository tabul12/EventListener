<%@page import="objects.Event"%>
<%@page import="baseConnection.EventManager"%>
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
		int eventID = Integer.parseInt(request.getParameter("id"));
		EventManager manager = (EventManager)application.getAttribute("EventManager");
		Event event = manager.getEvent(eventID);
		
		out.println("<p> you chose this eventID=" + eventID + "name is '" + event.getAbout() + "' </p>");
	%>

</body>
</html>