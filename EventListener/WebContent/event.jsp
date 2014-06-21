<%@page import="objects.User"%>
<%@page import="errors.BaseErrors"%>
<%@page import="baseConnection.UserManager"%>
<%@page import="objects.Place"%>
<%@page import="baseConnection.PlaceManager"%>
<%@page import="objects.Band"%>
<%@page import="baseConnection.BandManager"%>
<%@page import="objects.Event"%>
<%@page import="java.util.ArrayList"%>
<%@page import="errors.ConstantValues"%>
<%@page import="baseConnection.EventManager"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Event Listener</title>
<link href="homePageCSS.css" rel="stylesheet" type="text/css" media="screen" /> 
</head>
<body>
<p>
	 
<!-- start header -->
<div id="header">
	<div id="menu">
		<ul id="main">
			<li class="current_page_item"><a href="homePage.jsp">Home Page</a></li>
		</ul>
	</div>
</div>
<!-- end header -->
<div id="wrapper">
	<!-- start page -->
	<div id="page">
		<div id="sidebar1" class="sidebar">
			<ul>
				<li>
				 	<div id = "bands"> 
						<h2 >Top Bands</h2>
						</br>
						<ol>

							<% 
							
								String stID = request.getParameter("EventID");
							  
							 	 
							  	int eventID = 0;
							  	if(stID != null) {
							  		eventID = Integer.parseInt(stID);
							  		session.setAttribute("EventID", eventID);
							  	} else eventID = (Integer)session.getAttribute("EventID");
							  	
							  	
								BandManager bandManager = (BandManager)application.getAttribute("BandManager");
								ArrayList<Integer> bandsList = bandManager.getTopBands(ConstantValues.NUM_TOP_BANDS);

								for(int i = 0; i < bandsList.size(); i++){
									Band band = bandManager.getBand(bandsList.get(i));
									out.println("<li><a href=\"BandProfile.jsp?BandID=" + band.getID() + "\"><h3>" + band.getName() + "</h3></a>" +
											bandManager.getRating(band.getID()) + " </li>");
								}

							%>
						</ol>
						
						</br>
						<h2>Top Places</h2>
						<ol>
						<%
							PlaceManager placeManager = (PlaceManager)application.getAttribute("PlaceManager");
							ArrayList<Integer> topPlaces = placeManager.getTopPlaces(ConstantValues.NUM_TOP_PLACES);
							Place place;
							for(int i = 0; i < topPlaces.size(); i++){
								place = placeManager.getPlace(topPlaces.get(i));
								out.println("<li><a href=\"place.jsp?PlaceID=" + place.getID() + "\"><h3>" + place.getName() + "</h3></a>" +
										placeManager.getRating(place.getID()) + " </li>");
							}
							
							EventManager manager = (EventManager)application.getAttribute("EventManager");
							Event event = manager.getEvent(eventID);
							int placeID = event.getPlaceID();
							place = placeManager.getPlace(placeID);
							
							 
						%>
					</ol>
					
					 
						
					</div>
						
					<%
						out.println("<br/> <h1> Check And Rate Place  </h1>");
						out.println("<li><a href=\"place.jsp?PlaceID=" + place.getID() + "\"><h2>" + place.getName()  +" " +
								placeManager.getRating(place.getID()) +  "</h2></a> </li>");
						if(session.getAttribute("UserID") != null){
							out.println("<p align=center size:30px>");
							for(int j = 1; j <= ConstantValues.MAX_SCORE; j++){
								String str =""; 
								
								System.out.println("<b align=center><a href=\"PlaceAddRatingServlet\" id=" + j + ">" + j +"</a></b>");
							}
							out.println("<p>");
						}
						
					%>			 
					
					<a href="PlaceAddRatingServlet?PlaceID=1?value=5"> kamdkm</a>
				 </li>
				
			</ul>
			
		</div>
		<!-- start content -->
		<div id="content">
			
			<div class="dj">
			  <%
			  	 
			  	
				 
				event = manager.getEvent(eventID);
				out.println("<h1 class=\"title\" align=\"center\"> Welcome to Event " + event.getName() + "</h1> </br>");
				out.println("<img src=images/"+ event.getImage() + " height=250 width=510"+">");
				 
			 	
				%>
			   
			    
			 
			
			</div>
			<div class="post">
				 

				<div class="entry">
					<p><strong>Event Listener</strong> is designed by MACS students <a href="images/webcam-toy-photo3.jpg">TMM</a>.
				</div>
			</div>
			<div class="post">
				 
			</div>
		   
		  	
		  	
		  	<div id="sidebar1" class="sidebar">
				<ul>
					<li>				 	 
						 
						<%
							
							out.println("<div >");
							out.println(" <h1> Event About </h1> </br>");
							out.println("<div class=\"column\">");
							out.println("<h4 >" +"mdkamdkamdkamdkadnakdnakdnkadkandkandkandkadnkandakn"+
									"kdnakdnakdnakndkanddk"+ "</h4>");
							out.println("</div>");				
						
							 
							
							 
							UserManager userManager = (UserManager)application.getAttribute("UserManager");
							
							out.println("<li id = \"topPlaces\">");
							out.println("<div >");
							out.println(" <h2> Going Users List </h2> </br>");					 
							out.println("<p><div style=\"width:250%\";>");
							
							String tmp = (String)request.getParameter("EventPageNum");
							Integer currentPage = 1;
							if(tmp != null) {
								currentPage = Integer.parseInt(tmp);
								session.setAttribute("EventPageNum", currentPage);
							}
							
							Integer pg = (Integer) session.getAttribute("EventPageNum");
							if(pg != null){
								currentPage = pg;
							}
							
							
							int numGoingUSers = manager.getGoingUsersNum(event.getID());
							int numPages = numGoingUSers / ConstantValues.NUM_GOING_USERS_PER_PAGE;
							if(numGoingUSers % ConstantValues.NUM_GOING_USERS_PER_PAGE > 0) numPages++;
							
							System.out.println(currentPage);
							int startPage = Math.max(1, currentPage - ConstantValues.NUM_LEFT_RIGHT_PAGES);
							int endPage = Math.min(numPages, currentPage + ConstantValues.NUM_LEFT_RIGHT_PAGES);
							
							ArrayList<Integer> userList = manager.getGoingUsers(eventID, currentPage);
							
							for(int i = 0; i < userList.size(); i++){
								User user = userManager.getUser(userList.get(i));
								out.println("<div class =\"column\" style=\"width:20%;Text-align:center;float:left;\">");
								out.println("<b> <p> " + user.getUserName() +" </p> <p>");
								out.println("<img src=\"images/"+ user.getImage() + "\" height=\"100\" width=\"80\" /></p></b></div> ");	
								
							}
							out.println("</div></p> ");
							out.println("</div>");
							
							
							out.println("<p>");
							out.println("<div>");
							out.println("<h1 align=\"center\" > ");
							for(int i = startPage; i <= endPage; i++){
								  out.println("<a href=\"event.jsp?EventPageNum=" + i + "\">" + i + "</a>");
							}	
							out.println("</h1>");
						 
							out.println("</div>");
							out.println("</p>");
						%>
					</li>
				</ul>
			</div>
			
			   
			 
			 

		</div>
		<!-- end content -->
		<!-- start sidebars -->
		<div id="sidebar2" class="sidebar">
			<ul>
				<li id = "topPlaces">
				
				<%
				
					  	Integer st = (Integer)session.getAttribute("UserID");
					  	session.setAttribute("UserID", st);
						
						int userID = 0;
						if(st != null) userID = st;
						
						if(manager.hasAdded(userID, eventID)){
							out.println("<div>");
							out.println("<h2> Update Information   </h2> </br>");
							out.println("<ul><li>");
							// out.println(" <a href=\"UpdateEventInfo.jsp\" align=\"right\"/> Update Information     </a>");							
							
							out.println(" <a href=\"#\" align=\"right\"/> Update Information     </a>");							
							out.println("</li></ul>");
							out.println("</div> </br>");
						}
						
						if(st != null){
							out.println("<div>");
							out.println(" <h2> Attend Event   </h2> </br>");
							out.println("<form action=\"UserAttendsEventServlet\" method=\"post\"> <br/>");
							out.println("<ul><li>");
							Integer attends = (Integer)request.getAttribute("UserAlreadyAttendsEvent");
							if( attends != null && attends  == BaseErrors.USER_ALREADY_ATTENDS_EVENT ){
								out.println("<h1> You Have Already Clicked To Attend Event</h1>");
							}
							out.println("<ul><li>");
							out.println(" <input type=\"submit\" value=\"Attend\"> </form>");
							out.println("</li></ul>");
							out.println("</div> </br>");
						}
						
						out.println("<div>");
						out.println(" <h2> Event Time  </h2> </br>");
						out.println("<p > <h4 align=\"center\">" + event.getTime() + "<h4></p>" );
						out.println("</div> </br>");
						
						out.println("<div>");
						out.println(" <h2> Event Price  </h2> </br>");
						out.println("<p > <h4 align=\"center\">" + event.getPrice() + "<h4></p>" );
						out.println("</div> </br>");
						
						out.println("<div>");
						out.println(" <h2> Num Going Users  </h2> </br>");
						out.println("<p > <h4 align=\"center\">" + manager.getGoingUsersNum(event.getID()) + "<h4></p>" );
						out.println("</div> </br>");
						
						
						PlaceManager placemanag = (PlaceManager)application.getAttribute("PlaceManager");
						place = placemanag.getPlace(event.getPlaceID());
						out.println("<div>");
						out.println(" <h2> Event Address  </h2> </br>");
						out.println("<p > <h4 align=\"center\">" + place.getAdress() + "<h4></p>" );
						out.println("</div> </br>");
						
						
						out.println("<div>");
						out.println(" <h1> Check And Rate Bands  </h1>");
				
						 
						ArrayList<Integer> bands = manager.getBandsOnEvent(event.getID());
						
						for(int i = 0; i < bands.size(); i++){
							Band band = bandManager.getBand(bands.get(i));
							
							out.println("<li><a href=\"BandProfile.jsp?BandID=" + band.getID() + "\"><h2>" + band.getName()  +" " +
									bandManager.getRating(band.getID()) +  "</h2></a> </li>");
							if(session.getAttribute("UserID") != null){
								out.println("<p align=center size:30px>");
								for(int j = 1; j <= ConstantValues.MAX_SCORE; j++){
									out.println("<b align=center><a href=\"#\" value=" + j + "/>" + j +"</b>");
								}
								out.println("<p>");
							}
						}
						
						out.println("<div>");
						 
						out.println("<ul>");
						 
						 out.println("</ul></div>");
						
				%>
				
					
					 
					 
				</li>
			</ul>
		</div>
		<!-- end sidebars -->
		<div style="clear: both;">&nbsp;</div>
	</div>
	<!-- end page -->
</div>
<div id="footer">
	<p class="copyright">  Design by <a href="images/webcam-toy-photo3.jpg">TMM</a>.</p>
</div>
</html>