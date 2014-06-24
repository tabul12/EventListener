<%@page import="java.util.ArrayList"%>
<%@page import="baseConnection.BandManager"%>
<%@page import="errors.ConstantValues"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="post" id="Images">
				<h2 class="title"><a href="#">Images</a></h2>
				<div class="entry">
					<!-- aq daiwereba image------------------------------------------------------>
					<%
						 BandManager bandManager = (BandManager)application.getAttribute("BandManager");
						 Integer UserID =(Integer) session.getAttribute("UserID");
						 int BandID = Integer.parseInt(request.getParameter("BandID"));
						 Integer curBandUserID=bandManager.getBand(BandID).getUserID();
						 boolean hasAdded = (UserID == curBandUserID);
					     
						 int numImages = bandManager.getImagesNumberForBand(BandID); 
						 int imagePageNum = Integer.parseInt(request.getParameter("page"));
					 	 ArrayList<String> imagesArray = bandManager.getImages(BandID, imagePageNum);
					 	 for(int i = 0; i<imagesArray.size(); i++)
					 	 {
					 		out.println("<a href ='ImageLoader?FileName=" + imagesArray.get(i) + "'>  ");
							out.println("<img src='ImageLoader?FileName=" + imagesArray.get(i)
									+ "' height='100' width='100'" + "/>");
							
							out.println("</a>");
							if(hasAdded)
								out.println("<a href=\"updateBandProfileServlet?name=" + imagesArray.get(i) + 
										"&BandPageNum=" + imagePageNum + "&BandID=" + BandID +"\"> Set Prof </a>");
							 
						}
					%>
				</div>
			<ul>
		  	 <h1 align="center" > 

			<%		 			
			  int imagesPerPage = ConstantValues.NUMBER_OF_IMAGES_PER_PAGE_FOR_BAND_PROFILE;
			  int numImagePages = numImages / imagesPerPage;

			  if(numImages % imagesPerPage > 0) numImagePages++;



			  int startImagePageNum = Math.max(1,imagePageNum- ConstantValues.NUM_LEFT_RIGHT_PAGES);
			  int endImagePageNum = Math.min(numImagePages,imagePageNum + ConstantValues.NUM_LEFT_RIGHT_PAGES);
			  
			  for(int i = startImagePageNum; i <= endImagePageNum; i++){
				  out.println("<a href=# onclick=loadXMLDoc("+i+",'Images',"+BandID+")>"+i+" </a>");
				  //out.println("<a href=# onclick=loadXMLDoc("+i+")>"+i+" </a>");
			  }		

			%>  

			 </h1>
			</ul>
		</div>

</body>
</html>