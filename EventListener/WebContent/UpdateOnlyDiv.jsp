<%@page import="errors.ConstantValues"%>
<%@page import="objects.Event"%>
<%@page import="java.util.ArrayList"%>
<%@page import="baseConnection.EventManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<!-- start content -->
		<div id="content">
			<div class="dj"><img src="ImageLoader?FileName=dj-3.jpg" alt="" width="510" height="250" /></div>
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
				<div class="entry">



							<%
								EventManager eventManager =(EventManager) application.getAttribute("EventManager");
								int numEvents = eventManager.getEventsNum();
							 	String currPage = request.getParameter("page");
								session.setAttribute("HomePageNum", currPage);
								
								 
							 	int pageNum = 0;
							  	pageNum = Integer.parseInt(currPage);
							   
							 	 ArrayList<Integer> eventsList = eventManager.getEventsForNthPage(pageNum);
								//	System.out.println(eventsList.size() + "eventebis raodenobaa update");
							 	 for(int i = 0; i < eventsList.size(); i++){
							 		 Event event = eventManager.getEvent(eventsList.get(i));
							 		
							 		out.println("<li><a href=\"event.jsp?EventID=" + event.getID() + "\"><h3>" + event.getName() + "</h3></a>" +
													 " </li>");
							 		if(!event.getImage().equals("default.jpg"))
							 		{
							 			out.println("<div style=\"height: 250px\">" +
					     						"<img src='ImageLoader?FileName="+event.getImage()+"' alt=\" \" style=\"width: 100%;max-height: 100%\" />"+
											"</div>");
							 		}
							 		else{
							 		out.println("<div style=\"height: 250px\">" +
				     						"<img src='ImageLoader?FileName=dj-3.jpg' alt=\" \" style=\"width: 100%;max-height: 100%\" />"+
										"</div>");
							 		}
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
				
			  System.out.println(numPages  + " " + pageNum + ConstantValues.NUM_LEFT_RIGHT_PAGES);


			  for(int i = startPageNum; i <= endPageNum; i++){
				  out.println("<a href=# onclick=loadXMLDoc("+i+")>"+i+" </a>");
			  }		   

			%>  

			 </h1>
			</ul>

		</div>
		<!-- end content -->
</body>
</html>