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
	function loadXMLDocForPlaces(p,PlaceID) {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById("PlaceImages").innerHTML = xmlhttp.responseText;
			}
		}
		xmlhttp.open("GET", "UpdateOnlyDivForPlaceImages.jsp?PlacePageNum=" + p
				+ "&PlaceID=" + PlaceID, true);
		xmlhttp.send();
	}
</script>
</head>
<body>
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
			<%
				String stID = request.getParameter("PlaceID");
				  
			  	int placeID = 0;
			  	if(stID != null) {
			  		placeID = Integer.parseInt(stID);
			  		session.setAttribute("PlaceID", placeID);
			  	} else placeID = (Integer)session.getAttribute("PlaceID");
			  	
			  	Place place = placeManager.getPlace(placeID);
			  	
			  	out.println("<h2 align=center>Welcome To " + place.getName() + " </h2>" );
			  	
			  	System.out.print(place.getProfileImage() + "es ixateba ");
			  	
			  	out.println("<div class=\"dj\"><img src='ImageLoader?FileName=" + placeManager.getProfileImage(placeID) + "' alt=\"\" width=\"510\" height=\"250\" /></div>");
			
			  	
			%>
			<div class="post">
				<h1 class="title"><a href="#">Welcome to Our Website!</a></h1>

				<div class="entry">
					<p><strong>Event Listener</strong> is designed by MACS students <a href="images/webcam-toy-photo3.jpg">TMM</a>.
				</div>
			</div>
			<div id="sidebar1" class="sidebar">
				<ul>
					<li>	
				<%
						out.println("<div >");
						out.println(" <h1> Event About </h1> </br>");
						out.println("<div class=\"column\">");
						out.println("<h4 >" + place.getAbout() + "</h4>");
						out.println("</div>");
						out.println("</div>");
				%>
				</li>
				<%
					out.println("<li id = \"topPlaces\">");
					out.println("<div >");
					out.println(" <h2> " + place.getName() +"'s Images </h2> </br>");					 
					out.println("<p><div style=\"width:250%\";>");
					
				%>
					<div id ="PlaceImages">
				<%
					String tmp = (String)request.getParameter("PlacePageNum");
					Integer pageNum = 1;
					
					if(tmp != null) {
						pageNum = Integer.parseInt(tmp);
					}
					
					Integer st = (Integer)session.getAttribute("UserID");
					
					int userID = 0;
					if(st != null) userID = st;
					
					UserManager userManager = (UserManager)application.getAttribute("UserManager");
					boolean hasAddedThis = userManager.hasAddedPlace(userID, placeID);
					
					out.println("<p><div style=\"width:250%\";>");
					
					out.println("<div class =\"column\" style=\"width:40%;Text-align:center;float:left;\">");
					
					 
					ArrayList<String> imageList = placeManager.getAllImages(placeID, pageNum);
					 
					
					for(int i = 0; i < imageList.size(); i++){
						out.println("<div class =\"column\" style=\"width:20%;Text-align:center;float:left;\">");
						out.println("<p><img src='ImageLoader?FileName="+ imageList.get(i) + "' height=\"100\" width=\"80\" /></p></b>");
						if(hasAddedThis)
						out.println("<a href=\"updatePlaceProfileServlet?name=" + imageList.get(i) + 
						"&PlacePageNum=" + pageNum + "\"> Set Prof </a>");
						out.println("</div>");						 
					} 
					 
					out.println("</div></p> ");
					out.println("</div>");
					 
					
					int numPlaceImages = placeManager.getPlaceImagesNum(placeID);
					int numPages = numPlaceImages / ConstantValues.PLACE_IMAGES_ON_PER_PAGE;
					if(numPlaceImages % ConstantValues.PLACE_IMAGES_ON_PER_PAGE > 0) numPages++;
					
					int startPage = Math.max(1, pageNum - ConstantValues.NUM_LEFT_RIGHT_PAGES);
					int endPage = Math.min(numPages, pageNum + ConstantValues.NUM_LEFT_RIGHT_PAGES);
					
					out.println("<p>");
					out.println("<div>");
					out.println("<h1 align=\"center\" > ");
					for(int i = startPage; i <= endPage; i++){
						  out.println("<a href=# onclick=loadXMLDocForPlaces(" + i + ","
									+ placeID + ")>" + i + " </a>");
					}	
					out.println("</h1>");
					out.println("</div>");
					out.println("</p>");
				
				%>
				</div>
				</ul>
			</div>
		  	 

		</div>
		<!-- end content -->
		<!-- start sidebars -->
		<div id="sidebar2" class="sidebar">
			<ul>			
				<li id = "topPlaces">
				<%

						if(hasAddedThis){
							hasAddedThis = true;
							out.println("<div>");
							out.println("<h2> Update Information   </h2> </br>");
							out.println("<ul><li>"); // <a href=\"updatePlaceProfileServlet?name=" + imageList.get(i) + "&PlacePageNum=" + pageNum + "\">
							 
							out.println(" <a href=\"placeUpdateInfo.jsp?PlacePageNum="+ pageNum + "\" align=\"right\"/> Update Information    </a>");							
							out.println("</li></ul>");
							out.println("</div> </br>");
							
							 
							
		
							out.println("<h2> Upload Image   </h2> </br>"); 
							out.println("<form method='POST' action='upload' enctype='multipart/form-data' >");
							out.println("File:");
							out.println("<input type='file' name='file' id='file' /> <br/>");
							String destIM =ConstantValues.PATH_TO_IMAGES;
							out.println("<input type='hidden' value="+destIM+" name='destination'"+"/>");
							out.println("<input type='hidden' value="+placeID+" name='PlaceID'/>");
							out.println("<input type='hidden' value='placeImages' name='typeFile'/>");
							out.println("<input type='submit' value='Upload' name='upload' id='upload' "+"/>");
							out.println("</form>");
							out.println("</li>");
						}
				
						if(userID != 0 && userManager.isAdmin(userID)){
							out.println("<div>");
							out.println("<h2> Delete&Punish This User   </h2> </br>");	
							out.println("<p align=center> <a href=\"deletePlace?PlaceID=" + placeID + 
									"\" > Punish&Delete </a></p>");
							out.println("</div>");
						}
						
						place = placeManager.getPlace(placeID);
						out.println("<h2> Place Rating </h2>");
						out.println("<ul><li>");
						out.println("<h4>" + placeManager.getRating(placeID) + " </h4>");
						out.println("</li><ul>");
						
						out.println("<div>");
						out.println("<h2> Place Adress   </h2> </br>");
						out.println("<h3 align=center> " + place.getAdress() + "</h3>");
						out.println("</div>");
						 
						
						
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