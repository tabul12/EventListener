<%@page import="errors.ConstantValues"%>
<%@page import="objects.Event"%>
<%@page import="baseConnection.EventManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="baseConnection.UserManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="post" id="BeenEvents">
		<div>
			<h1 align="center">Been Events</h1>
			<%
				int userID = Integer.parseInt(request.getParameter("UserID"));
            	UserManager userManager =(UserManager) application.getAttribute("UserManager");
            	Integer beenListPageNum = Integer.parseInt(request.getParameter("page"));
		   
				ArrayList<Integer> beenList = new ArrayList<Integer>();
				beenList = userManager.beenList(userID, beenListPageNum);
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

					int startBeenListPageNum = Math.max(1, beenListPageNum - ConstantValues.NUM_LEFT_RIGHT_PAGES);
					
					int endBeenListPageNum = Math.min(numBeenListPages,beenListPageNum
							+ ConstantValues.NUM_LEFT_RIGHT_PAGES);

					for (int i = startBeenListPageNum; i <= endBeenListPageNum; i++) {
						out.println("<a href=# onclick=loadXMLDocForUser(" + i
								+ ",'BeenEvents'," + userID + ")>" + i + " </a>");
					}
				%>

			</h1>
		</ul>
	</div>
</body>
</html>