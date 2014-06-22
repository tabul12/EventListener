<%@page import="objects.Event"%>
<%@page import="baseConnection.EventManager"%>
<%@page import="baseConnection.UserManager"%>
<%@page import="objects.User"%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Update info</title>
<link rel="stylesheet" href="updateEventInfo.css">
<!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<%
	EventManager eventManager = (EventManager) application.getAttribute("EventManager");
	Integer st = (Integer) session.getAttribute("EventID");
	int eventID = st;
	Event event = eventManager.getEvent(eventID);
	String str = request.getParameter("EventPageNum");
	Integer pageNum = 1;
	if(str != null) pageNum = Integer.parseInt(str); 
%>
<body>
	<section class="container">
		<div class="login">
			<h1>Update info</h1>
			<form action="updateEventInfoServlet" method="post">
				<p>
					<% out.println("<input type=\"text\" name=\"Name\" value=\"" + event.getName() + "\">"); %> Name
				</p>
				<p>
					<% out.println("<input type=\"text\" name=\"About\" value=\"" + event.getAbout() + "\">"); %>
					About
				</p>
				<p>
					<% out.println("<input type=\"text\" name=\"Price\" value=\"" + event.getPrice() + "\">"); %>
					Price
				</p>
				<p>
					<% out.println("<input type=\"text\" name=\"Time\" value=\"" + event.getTime() + "\">"); %>
					Time
				</p>
				<p>
					<% out.println("<input type=\"hidden\" name=\"EventPageNum\" value=\"" + pageNum + "\">"); %>
					 
				</p>
				 
				 
				<p class="submit">
					<input type="submit" name="commit" value="Save">
				</p>
			</form>
		</div>
	</section>


</body>
</html>