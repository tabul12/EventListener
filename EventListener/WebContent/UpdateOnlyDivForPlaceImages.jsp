<%@page import="errors.ConstantValues"%>
<%@page import="java.util.ArrayList"%>
<%@page import="baseConnection.PlaceManager"%>
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
	<div id="PlaceImages">
		<%
			String tmp = (String) request.getParameter("PlacePageNum");
			Integer pageNum = 1;
			if (tmp != null) {
				pageNum = Integer.parseInt(tmp);
			}
			String placeID = (String) request.getParameter("PlaceID");
			int PlaceID = Integer.parseInt(placeID);
			Integer st = (Integer)session.getAttribute("UserID");

			int userID = 0;
			if (st != null)
				userID = st;

			UserManager userManager = (UserManager) application.getAttribute("UserManager");
			PlaceManager placeManager = (PlaceManager)application.getAttribute("PlaceManager");
			boolean hasAddedThis = userManager.hasAddedPlace(userID, PlaceID);

			out.println("<p><div style=\"width:250%\";>");

			out.println("<div class =\"column\" style=\"width:40%;Text-align:center;float:left;\">");

			ArrayList<String> imageList = placeManager.getAllImages(PlaceID,pageNum);
			for (int i = 0; i < imageList.size(); i++) {
				out.println("<div class =\"column\" style=\"width:20%;Text-align:center;float:left;\">");
				out.println("<p><img src='ImageLoader?FileName="
						+ imageList.get(i)
						+ "' height=\"100\" width=\"80\" /></p></b>");
				if (hasAddedThis)
					out.println("<a href=\"updatePlaceProfileServlet?name="
							+ imageList.get(i) + "&PlacePageNum=" + pageNum
							+ "\"> Set Prof </a>");
				out.println("<a href=\"DeleteImageForPlace?Path="+ConstantValues.PATH_TO_IMAGES+"&FileName="+imageList.get(i)+
					 	"&PlaceID=" + placeID +"\"> DEL </a>");
				out.println("</div>");
			}

			out.println("</div></p> ");
			out.println("</div>");

			int numPlaceImages = placeManager.getPlaceImagesNum(PlaceID);
			int numPages = numPlaceImages / ConstantValues.PLACE_IMAGES_ON_PER_PAGE;
			if (numPlaceImages % ConstantValues.PLACE_IMAGES_ON_PER_PAGE > 0)
				numPages++;

			int startPage = Math.max(1, pageNum
					- ConstantValues.NUM_LEFT_RIGHT_PAGES);
			int endPage = Math.min(numPages, pageNum
					+ ConstantValues.NUM_LEFT_RIGHT_PAGES);

			out.println("<p>");
			out.println("<div>");
			out.println("<h1 align=\"center\" > ");
			for (int i = startPage; i <= endPage; i++) {
				out.println("<a href=# onclick=loadXMLDocForPlaces(" + i + ","+ PlaceID + ")>" + i + " </a>");
			}
			out.println("</h1>");
			out.println("</div>");
			out.println("</p>");
		%>
	</div>
</body>
</html>