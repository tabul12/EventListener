<%@page import="objects.Place"%>
<%@page import="baseConnection.PlaceManager"%>
<%@page import="errors.ConstantValues"%>
<%@page import="java.util.ArrayList"%>
<%@page import="objects.Band"%>
<%@page import="baseConnection.BandManager"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>EventListener</title>
<link href="BandProfile.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<!-- start header -->
<div id="header">
</div>
<!-- end header -->
<div id="wrapper">
	<!-- start page -->
	<div id="page">
	<%
      int BandID = Integer.parseInt(request.getParameter("BandID"));	
      BandManager bandManager = (BandManager)application.getAttribute("BandManager");
      Band band = bandManager.getBand(BandID);
	%>
		<div id="sidebar1" class="sidebar">
			<ul>
				<li>
					<h2>
						<%out.println(band.getName());%>
					</h2>
					<%
						String BandImageName ="default.jpg";
					    if(bandManager.getProfileImage(BandID)!=null)
						  BandImageName = bandManager.getProfileImage(BandID);
					    out.println("<img src=images/"+BandImageName+
							  " height=200 width=220"+">");
					    System.out.print(BandImageName);
					%>
				</li>
				<li>
					<h2>Raiting</h2>
					<ul>
					   <li>
					    <%out.println(bandManager.getRating(BandID)); %>
						</li>
					</ul>
				</li>
				<li>
					<h2>about</h2>
					<ul>
						<li>
						  <% out.println(band.getAbout());%>
						</li>
					</ul>
				</li>
				<li>
					<h2>Mail</h2>
					<ul>
						<li>
							<%out.println(band.getMail());%>
						</li>
					</ul>
				</li>
			</ul>
		</div>
		<!-- start content -->
		<div id="content">
			<div class="post">
				<h2 class="title"><a href="#">Images</a></h2>
				<div class="entry">
					<!-- aq daiwereba eventebi------------------------------------------------------>
				</div>
			</div>
			<div class="post">
				<h2 class="title"><a href="#">Music</a></h2>
				<div class="entry">
				<!-- aq daiwereba vidoebi ------------------------------------------------------>
				</div>
			</div>
			<div class="post">
				<h2 class="title"><a href="#">Video</a></h2>
				<div class="entry">
					<!-- aq daiwereba suratebi -------------------------------------------------->
				</div>
			</div>
		</div>
		<!-- end content -->
		<!-- start sidebars -->
		<div id="sidebar2" class="sidebar">
			<ul>
				<li>
				    <h2>Top Bands</h2>
				    <ol>

							<% 
								ArrayList<Integer> bandsList = bandManager.getTopBands(ConstantValues.NUM_TOP_BANDS);

								for(int i = 0; i < bandsList.size(); i++){
									Band currBand = bandManager.getBand(bandsList.get(i));
									out.println("<li><a href=\"BandProfile.jsp?BandID=" + currBand.getID() + "\"><h3>" + currBand.getName() + "</h3></a>" +
											bandManager.getRating(currBand.getID()) + " </li>");
								}

							%>
						</ol>
				</li>
				<li>
					<h2>Top Places</h2>
					<ol>
						<%
							PlaceManager placeManager = (PlaceManager)application.getAttribute("PlaceManager");
							ArrayList<Integer> topPlaces = placeManager.getTopPlaces(ConstantValues.NUM_TOP_PLACES);

							for(int i = 0; i < topPlaces.size(); i++){
								Place place = placeManager.getPlace(topPlaces.get(i));
								out.println("<li><a href=\"place.jsp?id=" + place.getID() + "\"><h3>" + place.getName() + "</h3></a>" +
										placeManager.getRating(place.getID()) + " </li>");
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
<div align=center> <a href='https://www.facebook.com/'>This is EventListener Produced by TMM</a></div></body>
</html>