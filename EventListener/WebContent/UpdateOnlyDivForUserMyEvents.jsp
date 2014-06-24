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
	<div class="post" id="MyEvents">
		<div>
			<h1 align="center">My Events</h1>
			<%
				int userID = Integer.parseInt(request.getParameter("UserID"));
				UserManager userManager = (UserManager) application.getAttribute("UserManager");
				Integer myEventPageNum = Integer.parseInt(request.getParameter("page"));
				String myEventPageNumstr = (String) request.getParameter("MyEventPageID");
				ArrayList<Integer> addedEvents = new ArrayList<Integer>();
				addedEvents = userManager.addedEvents(userID, myEventPageNum);
				for (int i = 0; i < addedEvents.size(); i++) {
					int k = addedEvents.get(i);
					EventManager eventManager = (EventManager) application
							.getAttribute("EventManager");
					Event event = eventManager.getEvent(k);
					if (event.getImage() != "") {
						out.println("<img src='ImageLoader?FileName=" + event.getImage()
								+ "' height='120' width='120' />");
					} else {
						out.println("<img src='ImageLoader?FileName=default.jpg'  height='120' width='120' />");
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

</body>
</html>