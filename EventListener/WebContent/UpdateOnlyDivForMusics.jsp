<%@page import="errors.ConstantValues"%>
<%@page import="java.util.ArrayList"%>
<%@page import="baseConnection.BandManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="post" id='Musics'>
		<h2 class="title">
			<a href="#">Music</a>
		</h2>
		<div class="entry">
			<!-- aq daiwereba music ------------------------------------------------------>
			<%
				int BandID = Integer.parseInt(request.getParameter("BandID"));
				BandManager bandManager = (BandManager) application.getAttribute("BandManager");
				int numMusics = bandManager.getMusicsNumberForBand(BandID); 
				int musicPageNum = Integer.parseInt(request.getParameter("page"));
				ArrayList<String> musicsArray = bandManager.getMusics(BandID,musicPageNum);
				for (int i = 0; i < musicsArray.size(); i++) {
					out.println("<strong><p class='colRed' >" + musicsArray.get(i)
							+ "</p></strong>");
					out.println("<audio controls>");
					out.println("<source src='MusicLoader?FileName=" + musicsArray.get(i)
							+ "' type='video/ogg'>");
					out.println("<source src='MusicLoader?FileName=" + musicsArray.get(i)
							+ "'  type='video/mp4'>");
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
</body>
</html>