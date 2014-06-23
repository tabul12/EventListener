<%@page import="errors.ConstantValues"%>
<%@page import="objects.Place"%>
<%@page import="baseConnection.PlaceManager"%>
<%@page import="baseConnection.UserManager"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="post" id="MyPlaces">
		<div>
			<h1 align="center">My Places</h1>
			<%
			    int userID = Integer.parseInt(request.getParameter("UserID"));
	            UserManager userManager =(UserManager) application.getAttribute("UserManager");
				Integer	myPlacePageNum = Integer.parseInt(request.getParameter("page"));
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
</body>
</html>