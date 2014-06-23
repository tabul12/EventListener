<%@page import="errors.BaseErrors"%>
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
<link href="BandProfile.css" rel="stylesheet" type="text/css"
	media="screen" />
<script>
	function loadXMLDoc(p, type, BandID) {
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
		xmlhttp.open("GET", "UpdateOnlyDivFor" + type + ".jsp?page=" + p
				+ "&BandID=" + BandID, true);
		xmlhttp.send();
	}
</script>
</head>
<body>
	<!-- start header -->
	<div id="header">
		<div id="menu">
			<ul id="main">
				<li class="current_page_item"><a href="homePage.jsp?HomePageNum=1">Home
						Page</a></li>
			</ul>
		</div>
	</div>
	<!-- end header -->
	<div id="wrapper">
		<!-- start page -->
		<div id="page">
			<%
				Integer UserID =(Integer) session.getAttribute("UserID");
				int BandID = Integer.parseInt(request.getParameter("BandID"));
				BandManager bandManager = (BandManager) application
						.getAttribute("BandManager");
				Band band = bandManager.getBand(BandID);
			%>
			<div id="sidebar1" class="sidebar">
				<ul>
					<li>
						<h2>
							<%
								out.println(band.getName());
							%>
						</h2> <%
								 	String BandImageName = "default.jpg";
								 	if (bandManager.getProfileImage(BandID) != null)
								 		BandImageName = bandManager.getProfileImage(BandID);
								 	out.println("<img src=images/" + BandImageName
								 			+ " height='200' width='220'" + ">");
 								%>
					</li>
					<li>
						<h2>Raiting</h2>
						<ul>
							<li>
								<%
									out.println(bandManager.getRating(BandID));
								%>
							</li>
						</ul>
					</li>
					<li>
						 
						 
								<%
								if(UserID != null){
									out.println("<div>"); 
									out.println(" <h2> Add To Wishlist tralala  </h2> </br>");
									out.println("<form action=\"addWishlistServlet\" method=\"post\">");
									out.println("<ul><li>"); 			
									 
									out.println("<input type=\"hidden\" name=\"BandID\" value=\"" + band.getID() + "\">");
									out.println("<input type=\"hidden\" name=\"UserID\" value=\"" + UserID + "\">");
									
									out.println(" <input type=\"submit\" value=\"Add\"> </form>");
									Integer stAdded = (Integer)request.getAttribute("AlreadyAddedToWishlist");
									int added = 0;
									if(stAdded != null) added = stAdded;
									
									if(added == BaseErrors.ALREADY_ADDED_TO_WISHLIST){
										out.println("<h4> You have already added this band to wishlis</h4>");
									}
									out.println("</div>");
									
								}
								%>
							 
					</li>
					<li>
						<h2>about</h2>
						<ul>
							<li>
								<%
									out.println(band.getAbout());
								%>
							</li>
						</ul>
					</li>
					<li>
						<h2>Mail</h2>
						<ul>
							<li>
								<%
									out.println(band.getMail());
								%>
							</li>
						</ul>
					</li>
					<%
					
						 
						Integer curBandUserID=bandManager.getBand(BandID).getUserID();
						boolean hasAdded = (UserID == curBandUserID);
						if(UserID==curBandUserID)
						{
							out.println("<li>");
							out.println("<h2>Upload Images</h2>");
							out.println("<form method='POST' action='upload' enctype='multipart/form-data' >");
							out.println("File:");
							out.println("<input type='file' name='file' id='file' /> <br/>");
							String destIM =ConstantValues.PATH_TO_IMAGES;
							out.println("<input type='hidden' value="+destIM+
									" name='destination'"+"/>");
							out.println("<input type='hidden' value="+BandID+" name='BandID'/>");
							out.println("<input type='hidden' value='bandImages' name='typeFile'/>");
							out.println("<input type='submit' value='Upload' name='upload' id='upload' "+"/>");
							out.println("</form>");
							out.println("</li>");
						}
					    
					%>
					<%
					if(UserID==curBandUserID)
					{
						out.println("<li>");
						out.println("<h2>Upload Music</h2>");
						out.println("<form method='POST' action='upload' enctype='multipart/form-data' >");
						out.println("File:");
						out.println("<input type='file' name='file' id='file' /> <br/>");
						String destMU = ConstantValues.PATH_TO_MUSICS;
						out.println("<input type='hidden' value="+destMU+
								" name='destination'"+"/>");
						out.println("<input type='hidden' value="+BandID+" name='BandID'/>");
						out.println("<input type='hidden' value='bandMusics' name='typeFile'/>");
						
						out.println("<input type='submit' value='Upload' name='upload' id='upload' "+"/>");
						out.println("</form>");
						out.println("</li>");
					}
					%>
					<%
					if(UserID==curBandUserID)
					{
						out.println("<li>");
						out.println("<h2>Upload Videos</h2>");
						out.println("<form method='POST' action='upload' enctype='multipart/form-data' >");
						out.println("File:");
						out.println("<input type='file' name='file' id='file' /> <br/>");
						String destVI = ConstantValues.PATH_TO_VIDEOS;
						out.println("<input type='hidden' value="+destVI+
								" name='destination'"+"/>");
						out.println("<input type='hidden' value="+BandID+" name='BandID'/>");
						out.println("<input type='hidden' value='bandVideos' name='typeFile'/>");
						
						out.println("<input type='submit' value='Upload' name='upload' id='upload' "+"/>");
						out.println("</form>");
						out.println("</li>");
					}
					%>
				</ul>
			</div>
			<!-- start content -->
			<div id="content">
				<div class="post" id="Images">
					<h2 class="title">
						<a href="#">Images</a>
					</h2>
					<div class="entry">
						<!-- aq daiwereba image------------------------------------------------------>
						<p>
						<%
							int numImages = bandManager.getImagesNumberForBand(BandID);
							int imagePageNum = 1;
							
							ArrayList<String> imagesArray = bandManager.getImages(BandID,
									imagePageNum);
							
							for (int i = 0; i < imagesArray.size(); i++) {								
								 
								
								out.println("<a href =images/" + imagesArray.get(i) + ">  ");
								out.println("<img src=images/" + imagesArray.get(i)
										+ " height='100' width='100'/>");
								
								out.println("</a>");
								if(hasAdded)
									out.println("<a href=\"updateBandProfileServlet?name=" + imagesArray.get(i) + 
										 	"&BandPageNum=" + 1 + "&BandID=" + BandID +"\"> Set Prof </a>"); 
								 
								 
							}
							
						%>
						</p>
					</div>
					<ul>
						<h1 align="center">

							<%
								int imagesPerPage = ConstantValues.NUMBER_OF_IMAGES_PER_PAGE_FOR_BAND_PROFILE;
								int numImagePages = numImages / imagesPerPage;

								if (numImages % imagesPerPage > 0)
									numImagePages++;

								int startImagePageNum = Math.max(1, imagePageNum
										- ConstantValues.NUM_LEFT_RIGHT_PAGES);
								int endImagePageNum = Math.min(numImagePages, imagePageNum
										+ ConstantValues.NUM_LEFT_RIGHT_PAGES);

								for (int i = startImagePageNum; i <= endImagePageNum; i++) {
									out.println("<a href=# onclick=loadXMLDoc(" + i + ",'Images',"
											+ BandID + ")>" + i + " </a>");
									//out.println("<a href=# onclick=loadXMLDoc("+i+")>"+i+" </a>");
								}
							%>

						</h1>
					</ul>
				</div>
				<div class="post" id="Musics">
					<h2 class="title">
						<a href="#">Music</a>
					</h2>
					<div class="entry">
						<!-- aq daiwereba music ------------------------------------------------------>
						<%
							int numMusics = bandManager.getMusicsNumberForBand(BandID);
							int musicPageNum = 1;
							ArrayList<String> musicsArray = bandManager.getMusics(BandID,
									musicPageNum);
							for (int i = 0; i < musicsArray.size(); i++) {
								out.println("<strong><p class='colRed' >" + musicsArray.get(i)
										+ "</p></strong>");
								out.println("<audio controls>");
								out.println("<source src='musics/" + musicsArray.get(i)
										+ "' type='video/ogg'>");
								out.println("<source src='musics/" + musicsArray.get(i)
										+ "' type='video/mp4'>");
								out.println("</audio>");
							}
						%>

					</div>
					<ul>
						<h1 align="center">
							<%
								int musicsPerPage = ConstantValues.NUMBER_OF_MUSICS_PER_PAGE_FOR_BAND_PROFILE;
								int numMusicPages = numMusics / musicsPerPage;

								if (numMusics % musicsPerPage > 0)
									numMusicPages++;

								int startMusicPageNum = Math.max(1, musicPageNum
										- ConstantValues.NUM_LEFT_RIGHT_PAGES);
								int endMusicPageNum = Math.min(numMusicPages, musicPageNum
										+ ConstantValues.NUM_LEFT_RIGHT_PAGES);

								for (int i = startMusicPageNum; i <= endMusicPageNum; i++) {
									out.println("<a href=# onclick=loadXMLDoc(" + i + ",'Musics',"
											+ BandID + ")>" + i + " </a>");
								}
							%>
						</h1>
					</ul>
				</div>
				<div class="post" id="Videos">
					<h2 class="title">
						<a href="#">Video</a>
					</h2>
					<div class="entry">
						<!-- aq daiwereba video -------------------------------------------------->
						<%
							int numVideos = bandManager.getVideosNumberForBand(BandID);
							int videoPageNum = 1;
							ArrayList<String> videosArray = bandManager.getVideos(BandID,
									videoPageNum);
							for (int i = 0; i < videosArray.size(); i++) {
								out.println("<video  width='240' height='200' controls='controls' >");
								out.println("<source src=videos/" + videosArray.get(i)
										+ " type='video/ogg'>");
								out.println("<source src=videos/" + videosArray.get(i)
										+ " type='video/mp4'>");
								out.println("</video>");
							}
						%>
					</div>
					<ul>
						<h1 align="center">

							<%
								int videosPerPage = ConstantValues.NUMBER_OF_VIDEOS_PER_PAGE_FOR_BAND_PROFILE;
								int numVideoPages = numVideos / videosPerPage;

								if (numVideos % videosPerPage > 0)
									numVideoPages++;
								int startVideoPageNum = Math.max(1, videoPageNum
										- ConstantValues.NUM_LEFT_RIGHT_PAGES);
								int endVideoPageNum = Math.min(numVideoPages, videoPageNum
										+ ConstantValues.NUM_LEFT_RIGHT_PAGES);
								for (int i = startVideoPageNum; i <= endVideoPageNum; i++) {
									out.println("<a href=# onclick=loadXMLDoc(" + i + ",'Videos',"
											+ BandID + ")>" + i + " </a>");
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
						<h2>Top Bands</h2>
						<ol>

							<%
								ArrayList<Integer> bandsList = bandManager
										.getTopBands(ConstantValues.NUM_TOP_BANDS);

								for (int i = 0; i < bandsList.size(); i++) {
									Band currBand = bandManager.getBand(bandsList.get(i));
									out.println("<li><a href=\"BandProfile.jsp?BandID="
											+ currBand.getID() + "\"><h3>" + currBand.getName()
											+ "</h3></a>" + bandManager.getRating(currBand.getID())
											+ " </li>");
								}
							%>
						</ol>
					</li>
					<li>
						<h2>Top Places</h2>
						<ol>
							<%
								PlaceManager placeManager = (PlaceManager) application
										.getAttribute("PlaceManager");
								ArrayList<Integer> topPlaces = placeManager
										.getTopPlaces(ConstantValues.NUM_TOP_PLACES);

								for (int i = 0; i < topPlaces.size(); i++) {
									Place place = placeManager.getPlace(topPlaces.get(i));
									out.println("<li><a href=\"place.jsp?id=" + place.getID()
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
	<div align=center>
		<a href='https://www.facebook.com/'>This is EventListener Produced
			by TMM</a>
	</div>
</body>
</html>