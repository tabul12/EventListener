<%@page import="errors.BaseErrors"%>
<%@page import="baseConnection.UserManager"%>
<%@page import="objects.Place"%>
<%@page import="baseConnection.PlaceManager"%>
<%@page import="objects.Band"%>
<%@page import="baseConnection.BandManager"%>
<%@page import="objects.Event"%>
<%@page import="objects.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="errors.ConstantValues"%>
<%@page import="baseConnection.EventManager"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--
Design by Free CSS Templates
http://www.freecsstemplates.org
Released for free under a Creative Commons Attribution 2.5 License

Name       : Premium Series
Description: A three-column, fixed-width blog design.
Version    : 1.0
Released   : 20090303

-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>User page</title>
<meta name="keywords" content="" />
<meta name="Premium Series" content="" />
<link href="userPageCSS.css" rel="stylesheet" type="text/css"
	media="screen" />
<script>
	function loadXMLDocForUser(p, type, UserID) {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById(type).innerHTML = xmlhttp.responseText;
			}
		}
		xmlhttp.open("GET", "UpdateOnlyDivForUser" + type + ".jsp?page=" + p
				+ "&UserID=" + UserID, true);
		xmlhttp.send();
	}
</script>

</head>
<body>
	<!-- start header -->
	<div id="header">

		<div id="menu">
			<ul id="main">
				<li class="current_page_item"><a href="homePage.jsp">Homepage</a></li>
			</ul>
		</div>

	</div>
	<%
		UserManager userManager = (UserManager)application.getAttribute("UserManager");
			HttpSession sesion = request.getSession();
			int userID = (Integer)session.getAttribute("UserID");
			User user = userManager.getUser(userID);
	%>
	<!-- end header -->
	<div id="wrapper">
		<!-- start page -->
		<div id="page">
			<div id="sidebar1" class="sidebar">
				<ul>
					<li>
						<%
							out.println( "<h2>" + user.getUserName() + "</h2>");
						%>
						<ul>
							<%
								if(user.getImage() != null){
						        	out.println("<img src=\"images/"+user.getImage()+ "\" width=\"220\" height=\"220\">");
								}else{
									out.println("<img src=\"images/default.jpg\" height=\"120\" width=\"120\" />");
								}
							%>
						</ul>
					</li>
					<li>
						<h2>About</h2>
						<ul>
							<%
								out.println("<li>Name: " + user.getName() + "</li>");
							%>
							<%
								out.println("<li>LastName: " + user.getLastName() + "</li>");
							%>
							<%
								out.println("<li>MobileNumber: " + user.getMobileNumber() + "</li>");
							%>
							<%
								out.println("<li>Mail: " + user.getMail() + "</li>");
							%>
						</ul>
					</li>
					<li>
						<h2>Settings</h2>
						<ul>
							<li><a
								href="http://localhost:8080/EventListener/updateUserInfo.jsp">Update
									info</a></li>
						</ul>
					</li>
					<li>
						<h2>Add new</h2>
						<ul>
							<li><a
								href="http://localhost:8080/EventListener/bandRegister.jsp">Band</a></li>
							<li><a
								href="http://localhost:8080/EventListener/placeRegister.jsp">Place</a></li>
							<li><a
								href="http://localhost:8080/EventListener/eventRegister.jsp">Event</a></li>
						</ul>
						<h2>Change profile picture</h2>
						<ul>

							<li><form action="upload_file.php" method="post"
									enctype="multipart/form-data">
									<label for="file">Filename:</label>
									<input type="file" name="file" id="file" size="14"><br>
											<input type="submit" name="submit" value="Submit">
								</form></li>
						</ul>
						<h2>Calendar</h2>
						<div id="calendar_wrap">
							<br />
							<center> <script language="javascript"
								type="text/javascript">
								var day_of_week = new Array('Sun', 'Mon',
										'Tue', 'Wed', 'Thu', 'Fri', 'Sat');
								var month_of_year = new Array('January',
										'February', 'March', 'April', 'May',
										'June', 'July', 'August', 'September',
										'October', 'November', 'December');

								//  DECLARE AND INITIALIZE VARIABLES
								var Calendar = new Date();

								var year = Calendar.getFullYear(); // Returns year
								var month = Calendar.getMonth(); // Returns month (0-11)
								var today = Calendar.getDate(); // Returns day (1-31)
								var weekday = Calendar.getDay(); // Returns day (1-31)

								var DAYS_OF_WEEK = 7; // "constant" for number of days in a week
								var DAYS_OF_MONTH = 31; // "constant" for number of days in a month
								var cal; // Used for printing

								Calendar.setDate(1); // Start the calendar day at '1'
								Calendar.setMonth(month); // Start the calendar month at now

								/* VARIABLES FOR FORMATTING
								NOTE: You can format the 'BORDER', 'BGCOLOR', 'CELLPADDING', 'BORDERCOLOR'
								      tags to customize your caledanr's look. */

								var TR_start = '<TR>';
								var TR_end = '</TR>';
								var highlight_start = '<TD WIDTH="30"><TABLE CELLSPACING=0 BORDER=1 BGCOLOR=DEDEFF BORDERCOLOR=8B0000><TR><TD WIDTH=20><B><CENTER>';
								var highlight_end = '</CENTER></TD></TR></TABLE></B>';
								var TD_start = '<TD WIDTH="30"><CENTER>';
								var TD_end = '</CENTER></TD>';

								/* BEGIN CODE FOR CALENDAR
								NOTE: You can format the 'BORDER', 'BGCOLOR', 'CELLPADDING', 'BORDERCOLOR'
								tags to customize your calendar's look.*/

								cal = '<TABLE BORDER=1 CELLSPACING=0 CELLPADDING=0 BORDERCOLOR=8B0000><TR><TD>';
								cal += '<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=2>'
										+ TR_start;
								cal += '<TD COLSPAN="' + DAYS_OF_WEEK + '" BGCOLOR="#EFEFEF"><CENTER><B>';
								cal += month_of_year[month] + '   ' + year
										+ '</B>' + TD_end + TR_end;
								cal += TR_start;

								//   DO NOT EDIT BELOW THIS POINT  //

								// LOOPS FOR EACH DAY OF WEEK
								for (index = 0; index < DAYS_OF_WEEK; index++) {

									// BOLD TODAY'S DAY OF WEEK
									if (weekday == index)
										cal += TD_start + '<B>'
												+ day_of_week[index] + '</B>'
												+ TD_end;

									// PRINTS DAY
									else
										cal += TD_start + day_of_week[index]
												+ TD_end;
								}

								cal += TD_end + TR_end;
								cal += TR_start;

								// FILL IN BLANK GAPS UNTIL TODAY'S DAY
								for (index = 0; index < Calendar.getDay(); index++)
									cal += TD_start + '  ' + TD_end;

								// LOOPS FOR EACH DAY IN CALENDAR
								for (index = 0; index < DAYS_OF_MONTH; index++) {
									if (Calendar.getDate() > index) {
										// RETURNS THE NEXT DAY TO PRINT
										week_day = Calendar.getDay();

										// START NEW ROW FOR FIRST DAY OF WEEK
										if (week_day == 0)
											cal += TR_start;

										if (week_day != DAYS_OF_WEEK) {

											// SET VARIABLE INSIDE LOOP FOR INCREMENTING PURPOSES
											var day = Calendar.getDate();

											// HIGHLIGHT TODAY'S DATE
											if (today == Calendar.getDate())
												cal += highlight_start + day
														+ highlight_end
														+ TD_end;

											// PRINTS DAY
											else
												cal += TD_start + day + TD_end;
										}

										// END ROW FOR LAST DAY OF WEEK
										if (week_day == DAYS_OF_WEEK)
											cal += TR_end;
									}

									// INCREMENTS UNTIL END OF THE MONTH
									Calendar.setDate(Calendar.getDate() + 1);

								}// end for loop

								cal += '</TD></TR></TABLE></TABLE>';

								//  PRINT CALENDAR
								document.write(cal);
							//  End -->
							</script> </center>
						</div>
					</li>

				</ul>
			</div>
			<!-- start content -->
			<div id="content">
				<div class="post" id="MyBands">
					<div>
						<h1 align="center">My Bands</h1>
						<%
							int numMyBands = userManager.getMyBandsNum(userID);
							int myBandsPerPage = ConstantValues.NUM_MY_BAND_ON_PER_PAGE;
							int numMyBandPages = numMyBands / myBandsPerPage;
							String myBandPageNumStr = (String) request
									.getParameter("MyBandPageID");
							Integer myBandPageNum;
							if (myBandPageNumStr == null) {
								myBandPageNum = 1;
							} else {
								myBandPageNum = Integer.parseInt(myBandPageNumStr);
							}
							ArrayList<Integer> addedBands = new ArrayList<Integer>();
							addedBands = userManager.addedBands(userID, myBandPageNum);
							for (int i = 0; i < addedBands.size(); i++) {
								int k = addedBands.get(i);
								BandManager bandManager = (BandManager) application
										.getAttribute("BandManager");
								Band band = bandManager.getBand(k);
								if (bandManager.getProfileImage(k) != null) {
									out.println("<img src=\"" + bandManager.getProfileImage(k)
											+ "\" height=\"120\" width=\"120\" />");
								} else {
									out.println("<img src=\"images/default.jpg\" height=\"120\" width=\"120\" />");
								}
								out.println("<h2 id=\"textImage\" style=\"position:absolute;margin-top: -35px; margin-left:"
										+ (i * 123) + "px;\">");
								out.println("<span> <a href=\"BandProfile.jsp?BandID="
										+ band.getID() + "\">" + band.getName() + "</a>");
								out.println("</span> </h2> ");
							}
						%>
					</div>
					<ul>
						<h1 align="center">

							<%
								if (numMyBands % myBandsPerPage > 0)
									numMyBandPages++;
								int startMyBandsPageNum = Math.max(1, myBandPageNum
										- ConstantValues.NUM_LEFT_RIGHT_PAGES);
								int endMyBandsPageNum = Math.min(numMyBandPages, myBandPageNum
										+ ConstantValues.NUM_LEFT_RIGHT_PAGES);
								System.out.println(userID + "   user id");

								for (int i = startMyBandsPageNum; i <= endMyBandsPageNum; i++) {
									out.println("<a href=# onclick=loadXMLDocForUser(" + i
											+ ",'MyBands'," + userID + ")>" + i + " </a>");
									//out.println("<a href=\"userPage.jsp?MyBandPageID=" + i + "\">"
									//	+ i + "</a>");
								}
							%>

						</h1>
					</ul>
				</div>
				<div class="post" id="MyPlaces">
					<div>
						<h1 align="center">My Places</h1>
						<%
							String myPlacePageNumstr = (String) request
									.getParameter("MyPlacePageID");
							Integer myPlacePageNum;
							if (myPlacePageNumstr == null) {
								myPlacePageNum = 1;
							} else {
								myPlacePageNum = Integer.parseInt(myPlacePageNumstr);
							}
							ArrayList<Integer> addedPlaces = new ArrayList<Integer>();
							addedPlaces = userManager.addedPlaces(userID, myPlacePageNum);
							for (int i = 0; i < addedPlaces.size(); i++) {
								int k = addedPlaces.get(i);
								PlaceManager placeManager = (PlaceManager) application
										.getAttribute("PlaceManager");
								Place place = placeManager.getPlace(k);
								if (place.getProfileImage() != "") {
									out.println("<img src=\"images/" + place.getProfileImage()
											+ "\" height=\"120\" width=\"120\" />");
								} else {
									out.println("<img src=\"images/default.jpg\" height=\"120\" width=\"120\" />");
								}
								out.println("<h2 id=\"textImage\" style=\"position:absolute;margin-top: -35px; margin-left:"
										+ (i * 123) + "px;\">");
								out.println("<span> <a href=\"place.jsp?PlaceID="
										+ place.getID() + "\">" + place.getName() + "</a>");
								out.println("</span> </h2> ");
							}
						%>
					</div>
					<ul>
						<h1 align="center">

							<%
								int numMyPlaces = userManager.getMyPlacesNum(userID);
								int myPlacesPerPage = ConstantValues.NUM_MY_PLACE_ON_PER_PAGE;
								int numMyPlacePages = numMyPlaces / myPlacesPerPage;

								if (numMyPlaces % myPlacesPerPage > 0)
									numMyPlacePages++;

								int startMyPlacesPageNum = Math.max(1, myPlacePageNum
										- ConstantValues.NUM_LEFT_RIGHT_PAGES);
								int endMyPlacesPageNum = Math.min(numMyPlacePages, myPlacePageNum
										+ ConstantValues.NUM_LEFT_RIGHT_PAGES);

								for (int i = startMyPlacesPageNum; i <= endMyPlacesPageNum; i++) {
									out.println("<a href=# onclick=loadXMLDocForUser(" + i
											+ ",'MyPlaces'," + userID + ")>" + i + " </a>");
								}
							%>

						</h1>
					</ul>
				</div>
				<div class="post" id="MyEvents">
					<div>
						<h1 align="center">My Events</h1>
						<%
							String myEventPageNumstr = (String) request
									.getParameter("MyEventPageID");
							Integer myEventPageNum;
							if (myEventPageNumstr == null) {
								myEventPageNum = 1;
							} else {
								myEventPageNum = Integer.parseInt(myEventPageNumstr);
							}
							ArrayList<Integer> addedEvents = new ArrayList<Integer>();
							addedEvents = userManager.addedEvents(userID, myEventPageNum);
							for (int i = 0; i < addedEvents.size(); i++) {
								int k = addedEvents.get(i);
								EventManager eventManager = (EventManager) application
										.getAttribute("EventManager");
								Event event = eventManager.getEvent(k);
								if (event.getImage() != "") {
									out.println("<img src=images/" + event.getImage()
										+  " height='120' width='120' >");
									System.out.println(event.getImage()+ "swkejiej");
								} else {
									out.println("<img src=images/default.jpg  height='120' width='120' />");
									}
								System.out.println(event.getImage());
								out.println("<h2 id=\"textImage\" style=\"position:absolute;margin-top: -35px; margin-left:"
										+ (i * 123) + "px;\">");
								out.println("<span> <a href=\"event.jsp?EventID="
										+ event.getID() + "\">" + event.getName() + "</a>");
								out.println("</span> </h2> ");
							}
						%>
					</div>
					<ul>
						<h1 align="center">

							<%
								int numMyEvents = userManager.getMyEventsNum(userID);
								int myEventsPerPage = ConstantValues.NUM_MY_EVENT_ON_PER_PAGE;
								int numMyEventPages = numMyEvents / myEventsPerPage;

								if (numMyEvents % myEventsPerPage > 0)
									numMyEventPages++;

								int startMyEventsPageNum = Math.max(1, myEventPageNum
										- ConstantValues.NUM_LEFT_RIGHT_PAGES);
								int endMyEventsPageNum = Math.min(numMyEventPages, myEventPageNum
										+ ConstantValues.NUM_LEFT_RIGHT_PAGES);

								for (int i = startMyEventsPageNum; i <= endMyEventsPageNum; i++) {
									out.println("<a href=# onclick=loadXMLDocForUser(" + i
											+ ",'MyEvents'," + userID + ")>" + i + " </a>");
								}
							%>

						</h1>
					</ul>
				</div>
				<div class="post" id="WishList">
					<div>
						<h1 align="center">WishList</h1>
						<%
							String wishListPageNumstr = (String) request
									.getParameter("WishListPageID");
							Integer wishListPageNum;
							if (wishListPageNumstr == null) {
								wishListPageNum = 1;
							} else {
								wishListPageNum = Integer.parseInt(wishListPageNumstr);
							}
							ArrayList<Integer> wishList = new ArrayList<Integer>();
							wishList = userManager.getWishlist(userID, wishListPageNum);
							for (int i = 0; i < wishList.size(); i++) {
								int k = wishList.get(i);
								BandManager bandManager = (BandManager) application
										.getAttribute("BandManager");
								Band band = bandManager.getBand(k);
								if (bandManager.getProfileImage(k) != null) {
									out.println("<img src=\"" + bandManager.getProfileImage(k)
											+ "\" height=\"120\" width=\"120\" />");
								} else {
									out.println("<img src=\"images/default.jpg\" height=\"120\" width=\"120\" />");
								}
								out.println("<h2 id=\"textImage\" style=\"position:absolute;margin-top: -35px; margin-left:"
										+ (i * 123) + "px;\">");
								out.println("<span> <a href=\"BandProfile.jsp?BandID="
										+ band.getID() + "\">" + band.getName() + "</a>");
								out.println("</span> </h2> ");
							}
						%>
					</div>
					<ul>
						<h1 align="center">

							<%
								int numWishList = userManager.getWhishlistNum(userID);
								int wishListPerPage = ConstantValues.NUM_WISHLIST_ON_PER_PAGE;
								int numWishListPages = numWishList / wishListPerPage;

								if (numWishList % wishListPerPage > 0)
									numWishListPages++;

								int startWishListPageNum = Math.max(1, wishListPageNum
										- ConstantValues.NUM_LEFT_RIGHT_PAGES);
								int endWishListPageNum = Math.min(numWishListPages, wishListPageNum
										+ ConstantValues.NUM_LEFT_RIGHT_PAGES);

								for (int i = startWishListPageNum; i <= endWishListPageNum; i++) {
									out.println("<a href=# onclick=loadXMLDocForUser(" + i
											+ ",'WishList'," + userID + ")>" + i + " </a>");
								}
							%>

						</h1>
					</ul>
				</div>
				<div class="post" id="BeenEvents">
					<div>
						<h1 align="center">Been Events</h1>
						<%
							String beenListPageNumstr = (String) request
									.getParameter("BeenListPageID");
							System.out.println(beenListPageNumstr + "  been");
							Integer beenListPageNum;
							if (beenListPageNumstr == null) {
								beenListPageNum = 1;
							} else {
								beenListPageNum = Integer.parseInt(beenListPageNumstr);
							}
							ArrayList<Integer> beenList = new ArrayList<Integer>();
							beenList = userManager.beenList(userID, beenListPageNum);
							System.out.println(beenList.size() + "    esaaa zoma masivis");
							for (int i = 0; i < beenList.size(); i++) {
								int k = beenList.get(i);
								EventManager eventManager = (EventManager) application
										.getAttribute("EventManager");
								Event event = eventManager.getEvent(k);
								if (event.getImage() != null) {
									out.println("<img src=images/" + event.getImage()
											+ " height='120'   width='120' " + "/>");
								} else {
									out.println("<img src=images/default.jpg height='120'   width='120' />");
								}
								out.println("<h2 id=\"textImage\" style=\"position:absolute;margin-top: -35px; margin-left:"
										+ (i * 123) + "px;\">");
								out.println("<span> <a href=\"event.jsp?EventID="
										+ event.getID() + "\">" + event.getName() + "</a>");
								out.println("</span> </h2> ");
							}
						%>
					</div>
					<ul>
						<h1 align="center">

							<%
								int numBeenList = userManager.getBeenOnEventsNum(userID);
								int beenListPerPage = ConstantValues.NUM_BEEN_PLACE_ON_PER_PAGE;
								int numBeenListPages = numBeenList / beenListPerPage;

								if (numBeenList % beenListPerPage > 0)
									numBeenListPages++;

								int startBeenListPageNum = Math.max(1, beenListPageNum
										- ConstantValues.NUM_LEFT_RIGHT_PAGES);
								int endBeenListPageNum = Math.min(numBeenListPages, beenListPageNum
										+ ConstantValues.NUM_LEFT_RIGHT_PAGES);

								for (int i = startBeenListPageNum; i <= endBeenListPageNum; i++) {
									out.println("<a href=# onclick=loadXMLDocForUser(" + i
											+ ",'BeenEvents'," + userID + ")>" + i + " </a>");
								}
							%>

						</h1>
					</ul>
				</div>
			</div>

			<!-- end content -->
			<!-- start sidebars -->
			<div id="sidebar2" class="sidebar">
				<ul>
					<li>
						<form id="searchform" method="get" action="#">
							<div id="bands">
								<h2>Top Bands</h2>
								</br>
								<ol>

									<%
										BandManager bandManager = (BandManager) application
												.getAttribute("BandManager");
										ArrayList<Integer> bandsList = bandManager
												.getTopBands(ConstantValues.NUM_TOP_BANDS);

										for (int i = 0; i < bandsList.size(); i++) {
											Band band = bandManager.getBand(bandsList.get(i));
											out.println("<li><a href=\"BandProfile.jsp?BandID="
													+ band.getID() + "\"><h3>" + band.getName()
													+ "</h3></a>" + bandManager.getRating(band.getID())
													+ " </li>");
										}
									%>
								</ol>
							</div>
						</form>
					</li>
					<li id="topPlaces">

						<h2>Top Places</h2>
						<ol>
							<%
								PlaceManager placeManager = (PlaceManager) application
										.getAttribute("PlaceManager");
								ArrayList<Integer> topPlaces = placeManager
										.getTopPlaces(ConstantValues.NUM_TOP_PLACES);

								for (int i = 0; i < topPlaces.size(); i++) {
									Place place = placeManager.getPlace(topPlaces.get(i));
									out.println("<li><a href=\"place.jsp?PlaceID=" + place.getID()
											+ "\"><h3>" + place.getName() + "</h3></a>"
											+ placeManager.getRating(place.getID()) + " </li>");
								}
							%>

						</ol>
						
					</li>
				</ul>
			</div>

			<!-- end sidebars -->
			<div style="clear: both;">&nbsp;</div>
		</div>
		<!-- end page -->
	</div>

	<div id="footer">
		<p class="copyright">Design by TMM</p>
	</div>
</body>
</html>
