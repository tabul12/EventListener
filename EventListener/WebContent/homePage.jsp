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
<script>
function loadXMLDoc(p)
{
var xmlhttp;
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("content").innerHTML=xmlhttp.responseText;
    document.getElementById("logIncor").innerHTML="";
    }
  }
xmlhttp.open("GET","UpdateOnlyDiv.jsp?page="+p,true);
xmlhttp.send();
}
</script>
</head>
<body>
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
								BandManager bandManager = (BandManager)application.getAttribute("BandManager");
								ArrayList<Integer> bandsList = bandManager.getTopBands(ConstantValues.NUM_TOP_BANDS);

								for(int i = 0; i < bandsList.size(); i++){
									Band band = bandManager.getBand(bandsList.get(i));
									out.println("<li><a href=\"BandProfile.jsp?BandID=" + band.getID() + "\"><h3>" + band.getName() + "</h3></a>" +
											bandManager.getRating(band.getID()) + " </li>");
								}

							%>
						</ol>
					</div>
				</li>
			</ul>
		</div>
		<!-- start content -->
		<div id="content">
			<div class="dj"><img src="images/dj-3.jpg" alt="" width="510" height="250" /></div>
			<div class="post">
				<h1 class="title"><a href="#">Welcome to Our Website!</a></h1>

				<div class="entry">
					<p><strong>Event Listener</strong> is designed by MACS students <a href="images/webcam-toy-photo3.jpg">TMM</a>.
				</div>
			</div>
			<div class="post">
				<h2 class="title"><a href="#">Event List</a></h2>
				<ul>
					<li> <p class="byline"><strong>Latest Event List. Enjoy Your Life Man!</strong></p></li>
				</ul>
				<div class="entry" >
							<% 
							EventManager eventManager =(EventManager) application.getAttribute("EventManager");
							int numEvents = eventManager.getEventsNum();
						 	String id = (String)session.getAttribute("HomePageNum");

						 	 int pageNum = 0;
						 	 if(id!= null)
						  		  pageNum = Integer.parseInt(id);
						 	 else pageNum = 1;

						 	 ArrayList<Integer> eventsList = eventManager.getEventsForNthPage(pageNum);

						 	// System.out.println(eventsList.size() + " eventebis raodenoba");
						 	 
						 	 for(int i = 0; i < eventsList.size(); i++){
						 		 Event event = eventManager.getEvent(eventsList.get(i));
						 		out.println("<li><a href=\"event.jsp?EventID=" + event.getID() + "\"><h3>" + event.getName() + "</h3></a>" +
												 " </li>");

						 		 out.println("<div style=\"height: 250px\">" +
			     						"<img src=\"images/dj-3.jpg\" alt=\" \" style=\"width: 100%;max-height: 100%\" />"+
									"</div>");
						 	 }

							%>


				</div>
			</div>
		  	<ul>
		  	 <h1 align="center" > 

			<%		 			



			  int eventsPerPage = ConstantValues.NUM_EVENT_ON_PER_PAGE;
			  int numPages = numEvents / eventsPerPage;

			  if(numEvents % eventsPerPage > 0) numPages++;



			  int startPageNum = Math.max(1,pageNum- ConstantValues.NUM_LEFT_RIGHT_PAGES);
			  int endPageNum = Math.min(numPages,pageNum + ConstantValues.NUM_LEFT_RIGHT_PAGES);
				
			  System.out.println(numPages  + " " + pageNum + " " + ConstantValues.NUM_LEFT_RIGHT_PAGES);

			  for(int i = startPageNum; i <= endPageNum; i++){
				  out.println("<a href=# onclick=loadXMLDoc("+i+")>"+i+" </a>");
				  //out.println("<a href=\"homePage.jsp?id=" + i + "\">" + i + "</a>");
			  }		

			%>  

			 </h1>
			</ul>

		</div>
		<!-- end content -->
		<!-- start sidebars -->
		<div id="sidebar2" class="sidebar">
			<ul>
				<li>					 
						<div>
						<%
							UserManager userManager = (UserManager)application.getAttribute("UserManager");
							String name = request.getParameter("FirstName");
							boolean weShouldDrawLogOut = false;
							if(name == null && session.getAttribute("UserID") == null) {
								out.println("<h2>Sign In</h2>");
								out.println("<ul><li>");
								out.println("<form action=\"LoginServlet\" method=\"post\"> <br/>");
								
								
								Integer number = (Integer)session.getAttribute("UserExists");
								System.out.println(number);
						      	int num  = -1;
						      	if(number != null) num = number;
								
						      	if(num == BaseErrors.IN_CORRECT_USERNAME_OR_PASSWORD){
						      		out.println("<div id='logIncor'>");
						      		out.println("<p> incorrect username or password </p>");
						      		session.setAttribute("UserExists", 0);
						      		out.println("</div>");
						      	} 
								
								out.println("User Name: <br/>");
								out.println("<input type=\"text\" name=\"UserName\"><br />");
								out.println("Password: <br/>");
								out.println("<input type=\"text\" name =\"Password\"> <br/>");
								out.println("<input type=\"submit\" value=\"Log in\">");
								out.println("<a href=\"register.jsp\" > Sign Up</a>");
								out.println("</form></li></ul>");
							}
							else {
								weShouldDrawLogOut = true;
								String userName = request.getParameter("UserName"); 
								String password = request.getParameter("Password");
								int userID = 0;
								if(userName == null) userID = (Integer)session.getAttribute("UserID");
								else {
									userID = userManager.getUserID(userName, password); 
									session.setAttribute("UserID", userID); 
									userName = userManager.getUser(userID).getName();
								}
								out.println("<div id='welcome'>");
								out.println("<p><h2> Welcome " + userName + "</h2></p>");
								out.println("</div>");
							}
						
						%> 

							</div>
					
				</li>

				<%
					if(weShouldDrawLogOut){
						out.println("<li>");
						out.println("<form action=\"logout\" method=\"post\">");
						out.println("<ul><li> <input type=\"submit\" value=\"Log Out\">	</li></ul>");
						out.println("</form></li>");
					}
				%>		 



				<li id = "topPlaces">

					<h2>Top Places</h2>
					<ol>
						<%
							PlaceManager placeManager = (PlaceManager)application.getAttribute("PlaceManager");
							ArrayList<Integer> topPlaces = placeManager.getTopPlaces(ConstantValues.NUM_TOP_PLACES);

							for(int i = 0; i < topPlaces.size(); i++){
								Place place = placeManager.getPlace(topPlaces.get(i));
								out.println("<li><a href=\"place.jsp?PlaceID=" + place.getID() + "\"><h3>" + place.getName() + "</h3></a>" +
										placeManager.getRating(place.getID()) + " </li>");
							}
						%>
					</ol>



					<h2>Calendar</h2>
					<div id="calendar_wrap">
					<br/>
						<center>
							<script language="javascript" type="text/javascript">
							var day_of_week = new Array('Sun','Mon','Tue','Wed','Thu','Fri','Sat');
							var month_of_year = new Array('January','February','March','April','May','June','July','August','September','October','November','December');

							//  DECLARE AND INITIALIZE VARIABLES
							var Calendar = new Date();

							var year = Calendar.getFullYear();     // Returns year
							var month = Calendar.getMonth();    // Returns month (0-11)
							var today = Calendar.getDate();    // Returns day (1-31)
							var weekday = Calendar.getDay();    // Returns day (1-31)

							var DAYS_OF_WEEK = 7;    // "constant" for number of days in a week
							var DAYS_OF_MONTH = 31;    // "constant" for number of days in a month
							var cal;    // Used for printing

							Calendar.setDate(1);    // Start the calendar day at '1'
							Calendar.setMonth(month);    // Start the calendar month at now


							/* VARIABLES FOR FORMATTING
							NOTE: You can format the 'BORDER', 'BGCOLOR', 'CELLPADDING', 'BORDERCOLOR'
							      tags to customize your caledanr's look. */

							var TR_start = '<TR>';
							var TR_end = '</TR>';
							var highlight_start = '<TD WIDTH="30"><TABLE CELLSPACING=0 BORDER=1 BGCOLOR=DEDEFF BORDERCOLOR=8B0000><TR><TD WIDTH=20><B><CENTER>';
							var highlight_end   = '</CENTER></TD></TR></TABLE></B>';
							var TD_start = '<TD WIDTH="30"><CENTER>';
							var TD_end = '</CENTER></TD>';

							/* BEGIN CODE FOR CALENDAR
							NOTE: You can format the 'BORDER', 'BGCOLOR', 'CELLPADDING', 'BORDERCOLOR'
							tags to customize your calendar's look.*/

							cal =  '<TABLE BORDER=1 CELLSPACING=0 CELLPADDING=0 BORDERCOLOR=8B0000><TR><TD>';
							cal += '<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=2>' + TR_start;
							cal += '<TD COLSPAN="' + DAYS_OF_WEEK + '" BGCOLOR="#EFEFEF"><CENTER><B>';
							cal += month_of_year[month]  + '   ' + year + '</B>' + TD_end + TR_end;
							cal += TR_start;

							//   DO NOT EDIT BELOW THIS POINT  //

							// LOOPS FOR EACH DAY OF WEEK
							for(index=0; index < DAYS_OF_WEEK; index++)
							{

							// BOLD TODAY'S DAY OF WEEK
							if(weekday == index)
							cal += TD_start + '<B>' + day_of_week[index] + '</B>' + TD_end;

							// PRINTS DAY
							else
							cal += TD_start + day_of_week[index] + TD_end;
							}

							cal += TD_end + TR_end;
							cal += TR_start;

							// FILL IN BLANK GAPS UNTIL TODAY'S DAY
							for(index=0; index < Calendar.getDay(); index++)
							cal += TD_start + '  ' + TD_end;

							// LOOPS FOR EACH DAY IN CALENDAR
							for(index=0; index < DAYS_OF_MONTH; index++)
							{
							if( Calendar.getDate() > index )
							{
							  // RETURNS THE NEXT DAY TO PRINT
							  week_day =Calendar.getDay();

							  // START NEW ROW FOR FIRST DAY OF WEEK
							  if(week_day == 0)
							  cal += TR_start;

							  if(week_day != DAYS_OF_WEEK)
							  {

							  // SET VARIABLE INSIDE LOOP FOR INCREMENTING PURPOSES
							  var day  = Calendar.getDate();

							  // HIGHLIGHT TODAY'S DATE
							  if( today==Calendar.getDate() )
							  cal += highlight_start + day + highlight_end + TD_end;

							  // PRINTS DAY
							  else
							  cal += TD_start + day + TD_end;
							  }

							  // END ROW FOR LAST DAY OF WEEK
							  if(week_day == DAYS_OF_WEEK)
							  cal += TR_end;
							  }

							  // INCREMENTS UNTIL END OF THE MONTH
							  Calendar.setDate(Calendar.getDate()+1);

							}// end for loop

							cal += '</TD></TR></TABLE></TABLE>';

							//  PRINT CALENDAR
							document.write(cal);

							//  End -->
							</script>
						</center>
					</div>
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