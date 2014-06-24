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
			<li class="current_page_item"><a href="homePage.jsp?HomePageNum=1">Home Page</a></li>
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
								String tmp = (String)request.getParameter("EventPageNum");
								System.out.println(tmp + " esaa romelsac xatavs");
								Integer currentPage = 1;
								if(tmp != null) { 
									currentPage = Integer.parseInt(tmp);
								}
								
								int pageNum = currentPage;
							
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
							 	out.println("<a href=\"PlaceAddRatingServlet?name=" + place.getID() + "&value="
							 			+ j + "&EventPageNum=" + pageNum +"\">" + j +"</a>");
								
							}
							 
							if(request.getAttribute("Rated") != null && 
									(Integer)request.getAttribute("Rated") == BaseErrors.USER_ALREADY_RATED_PLACE) {
								out.println("<h4> You have aldeary rated this place </h4>");
								request.setAttribute("Rated", null);
							}
							
							if(request.getAttribute("UserCantRatePlace") != null && 
									(Integer)request.getAttribute("UserCantRatePlace") == BaseErrors.USER_DOES_NOT_ATTEND_EVENT){
								out.println("<h4> You should attend event to rate place </h4>");
								request.setAttribute("UserCantRatePlace", null);
							}
								
						}
						
					%>			 
					
					 
				 </li>
				
				

				
			</ul>
			
		</div>
		<!-- start content -->
		<div id="content">
			
			<div class="dj">
			  <%
			  	 
			  	
				 
				event = manager.getEvent(eventID);
				out.println("<h1 class=\"title\" align=\"center\"> Welcome to Event " + event.getName() + "</h1> </br>");
				out.println("<img src='ImageLoader?FileName="+ event.getImage() + "' height=250 width=510"+">");
				 
				System.out.println(event.getImage());
			 	
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
							out.println("<h4 >" + event.getAbout() + "</h4>");
							out.println("</div>");				
						
							 
							
							 
							UserManager userManager = (UserManager)application.getAttribute("UserManager");
							
							out.println("<li id = \"topPlaces\">");
							out.println("<div >");
							out.println(" <h2> Going Users List </h2> </br>");					 
							out.println("<p><div style=\"width:250%\";>");
							
							 
							
							int numGoingUSers = manager.getGoingUsersNum(event.getID());
							int numPages = numGoingUSers / ConstantValues.NUM_GOING_USERS_PER_PAGE;
							if(numGoingUSers % ConstantValues.NUM_GOING_USERS_PER_PAGE > 0) numPages++;
							 
							int startPage = Math.max(1, currentPage - ConstantValues.NUM_LEFT_RIGHT_PAGES);
							int endPage = Math.min(numPages, currentPage + ConstantValues.NUM_LEFT_RIGHT_PAGES);
							
							ArrayList<Integer> userList = manager.getGoingUsers(eventID, currentPage);
							
							for(int i = 0; i < userList.size(); i++){
								User user = userManager.getUser(userList.get(i));
								out.println("<div class =\"column\" style=\"width:20%;Text-align:center;float:left;\">");
								out.println("<b> <p> " + user.getUserName() +" </p> <p>");
								out.println("<img src='ImageLoader?FileName="+ user.getImage() + "' height=\"100\" width=\"80\" /></p></b></div> ");	
								
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
					  	
						int userID = 0;
						if(st != null) userID = st;
						
						if(manager.hasAdded(userID, eventID)){
							out.println("<div>");
							out.println("<h2> Update Information   </h2> </br>");
							out.println("<ul><li>");
							
							out.println(" <a href=\"updateEventInfo.jsp?EventPageNum=" + pageNum + "\" align=\"right\"/> Update Info  </a>");							
							out.println("<li><form action='ChangeProfilePicture' method='post' enctype='multipart/form-data' >");
							out.println("<label for='file'><p>Update Picture</p></label>");
							out.println("<input type='file' name='file' id='file' size='14' "+"/>"+"<br>");		
							out.println("<input type='hidden' value='EventProfile' name='typeProfile'>");	
							out.println("<input type='hidden' value="+eventID+" name='EventID'>");
							out.println("<input type='submit' name='submit' value='Submit'>");		
							out.println("</form></li>");				
								
							
							
							//out.println(" <a href=\"#\" align=\"right\"/> Change Profile</a>");
							out.println("</li></ul>");
							out.println("</div> </br>");
						}
						
						if(st != null){
							out.println("<div>");
							out.println(" <h2> Attend Event   </h2> </br>");
							out.println("<form action=\"UserAttendsEventServlet?value="+pageNum +"\" method=\"post\"> <br/>");
							out.println("<ul><li>");
							Integer attends = (Integer)request.getAttribute("UserAlreadyAttendsEvent");
							if( attends != null && attends  == BaseErrors.USER_ALREADY_ATTENDS_EVENT ){
								out.println("<h1> You Have Already Clicked To Attend Event</h1>");
							}
							out.println("<ul><li>");
							out.println("<input type=\"hidden\" name=\"EventPageNum\" value=\"" + pageNum + "\">");
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
						
						
						boolean hasAdded = userManager.hasAddedEvent(userID, eventID);
						
						if(hasAdded){
							out.println("<form action=\"addBandOnEvent\" method=\"post\">");
							out.println("<div align=center>");
							out.println("<h2> Add Band On Event</h2>");
							out.println("<p><input  name=\"BandName\"  placeholder=\"BandName\" rows=\"3\" cols=\"24\">");
        					out.println("<input type=\"submit\" name=\"commit\" value=\"Add\"></p>");
							out.println("</div>");
							out.println("</form>");
							if(request.getAttribute("NoSuchBand") != null && 
									(Integer)request.getAttribute("NoSuchBand") == BaseErrors.NO_SUCH_BAND)
								out.println("<h4> No such Band in DataBase</h4>");
							if(request.getAttribute("ThisBandIsAlreadyAdded") != null
								&& (Integer)request.getAttribute("ThisBandIsAlreadyAdded") == BaseErrors.THIS_BAND_IS_ALREADY_ADDED)
								out.println("<h4> This band is already added to this event</h4>");
						}
						
						
						out.println("<div>");
						out.println(" <h1> Check And Rate Bands  </h1>");
				
						 
						ArrayList<Integer> bands = manager.getBandsOnEvent(event.getID());
						
						if(request.getAttribute("Rated") != null && (Integer)request.getAttribute("Rated") == BaseErrors.USER_ALREADY_RATED_BAND) {
							out.println("<h4> You have aldeary rated this band </h4>");
							request.setAttribute("Rated", null);
						}
						
						if(request.getAttribute("UserCantRateBand") != null && 
								(Integer)request.getAttribute("UserCantRateBand") == BaseErrors.USER_DOES_NOT_ATTEND_EVENT){
							out.println("<h4> You should attend event to rate place </h4>");
							System.out.println("watafaa");
							request.setAttribute("UserCantRateBand", null);
						}
						
						for(int i = 0; i < bands.size(); i++){
							Band band = bandManager.getBand(bands.get(i));
							
							out.println("<li><a href=\"BandProfile.jsp?BandID=" + band.getID() + "\"><h2>" + band.getName()  +" " +
									bandManager.getRating(band.getID()) +  "</h2></a> </li>");
							if(session.getAttribute("UserID") != null){
								out.println("<p align=center size:30px>");						
								for(int j = 1; j <= ConstantValues.MAX_SCORE; j++){
								 	out.println("<a href=\"BandAddRatingServlet?name=" + band.getID() + "&value="
								 			+ j + "&EventPageNum=" + pageNum + "\">" + j +"</a>");									
								}
								 
								
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