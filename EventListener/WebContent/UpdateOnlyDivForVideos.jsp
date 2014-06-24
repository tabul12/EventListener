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
<div class="post" id="Videos">
				<h2 class="title"><a href="#">Video</a></h2>
				<div class="entry">
					<!-- aq daiwereba video -------------------------------------------------->
					<%
					     int BandID = Integer.parseInt(request.getParameter("BandID"));
			       	     BandManager bandManager =(BandManager) application.getAttribute("BandManager");
						 int numVideos = bandManager.getVideosNumberForBand(BandID); 
						 int videoPageNum = Integer.parseInt(request.getParameter("page"));
					 	 ArrayList<String> videosArray = bandManager.getVideos(BandID,videoPageNum);
					 	 for(int i = 0; i<videosArray.size(); i++)
					 	 {
					 		out.println("<video  width='240' height='200' controls='controls' >");
					 		out.println("<source src='VideoLoader?FileName="+videosArray.get(i)+ "' type='video/ogg'>");
					 		out.println("<source src='VideoLoader?FileName="+videosArray.get(i)+ "' type='video/mp4'>");
					 		out.println("</video>");
					 	 }
					%>
				</div>
				<ul>
		  	 <h1 align="center" > 

			<%		 			
			  int videosPerPage = ConstantValues.NUMBER_OF_VIDEOS_PER_PAGE_FOR_BAND_PROFILE;
			  int numVideoPages = numVideos / videosPerPage;

			  if(numVideos % videosPerPage > 0) numVideoPages++;



			  int startVideoPageNum = Math.max(1,videoPageNum- ConstantValues.NUM_LEFT_RIGHT_PAGES);
			  int endVideoPageNum = Math.min(numVideoPages,videoPageNum + ConstantValues.NUM_LEFT_RIGHT_PAGES);
			

			  for(int i = startVideoPageNum; i <= endVideoPageNum; i++){
				  out.println("<a href=# onclick=loadXMLDoc("+i+",'Videos',"+BandID+")>"+i+" </a>");
			  }		

			%>  

			 </h1>
			</ul>
		  </div> 

</body>
</html>