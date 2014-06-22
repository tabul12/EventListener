<%@page import="objects.Place"%>
<%@page import="baseConnection.PlaceManager"%>
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
	PlaceManager placeManager = (PlaceManager) application.getAttribute("PlaceManager");
	Integer st = (Integer) session.getAttribute("PlaceID");
	int placeID = st;
	Place place = placeManager.getPlace(placeID);
	
	String pageN = request.getParameter("PlacePageNum");
	Integer pageNum = 1;
	if(pageN != null) pageNum = Integer.parseInt(pageN);
	request.setAttribute("PlacePageNum", pageNum);
	
	System.out.println(pageNum + " mdmakm ");
%>
<body>
	<section class="container">
		<div class="login">
			<h1>Update info</h1>
			<form action="updatePlaceInfoServlet" method="post">
				<p>
					<% out.println("<input type=\"text\" name=\"Name\" value=\"" + place.getName() + "\">"); %> Name
				</p>
				<p>
					<% out.println("<input type=\"text\" name=\"About\" value=\"" + place.getAbout() + "\">"); %>
					About
				</p>
				 
				<p>
					<% out.println("<input type=\"text\" name=\"Adress\" value=\"" + place.getAdress() + "\">"); %>
					Adress
				</p>
				
				<p>
					<% out.println("<input type=\"hidden\" name=\"PlacePageNum\" value=\"" + pageNum + "\">"); %>
					 
				</p>
				 
				  
				<p class="submit">
					<input type="submit" name="commit" value="Save" >
				</p>
			</form>
		</div>
	</section>


</body>
</html>