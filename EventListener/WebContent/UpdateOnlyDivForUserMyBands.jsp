<%@page import="objects.Band"%>
<%@page import="baseConnection.BandManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="errors.ConstantValues"%>
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
	<div class="post" id="MyBands">
		<div>
			<h1 align="center">My Bands</h1>
			<%
			    int userID = Integer.parseInt(request.getParameter("UserID"));
  	            UserManager userManager =(UserManager) application.getAttribute("UserManager");
  	            Integer myBandPageNum = Integer.parseInt(request.getParameter("page"));
  	            
  	            int numMyBands = userManager.getMyBandsNum(userID);
				int myBandsPerPage = ConstantValues.NUM_MY_BAND_ON_PER_PAGE;
				int numMyBandPages = numMyBands / myBandsPerPage;
				ArrayList<Integer> addedBands = new ArrayList<Integer>();
				addedBands = userManager.addedBands(userID, myBandPageNum);
				for (int i = 0; i < addedBands.size(); i++) {
					int k = addedBands.get(i);
					BandManager bandManager = (BandManager) application
							.getAttribute("BandManager");
					Band band = bandManager.getBand(k);
					if (bandManager.getProfileImage(k) != null) {
						out.println("<img src='ImageLoader?FileName=" + bandManager.getProfileImage(k)
								+ "' height=\"120\" width=\"120\" />");
					} else {
						out.println("<img src='ImageLoader?FileName=default.jpg' height=\"120\" width=\"120\" />");
					}
					out.println("<h2 id=\"textImage\" style=\"position:absolute;margin-top: -35px; margin-left:"
							+ (i * 123) + "px;\">");
					out.println("<span> <a href=\"BandProfile.jsp?BandID="
							+ band.getID() + "\">" + band.getName() + "</a>");
					out.println("</span> </h2> ");
				}
			%>
		</div>
		<ul>
			<h1 align="center">

				<%
					if (numMyBands % myBandsPerPage > 0)
						numMyBandPages++;
					int startMyBandsPageNum = Math.max(1, myBandPageNum
							- ConstantValues.NUM_LEFT_RIGHT_PAGES);
					int endMyBandsPageNum = Math.min(numMyBandPages, myBandPageNum
							+ ConstantValues.NUM_LEFT_RIGHT_PAGES);

					for (int i = startMyBandsPageNum; i <= endMyBandsPageNum; i++) {
						out.println("<a href=# onclick=loadXMLDocForUser(" + i
								+ ",'MyBands'," + userID + ")>" + i + " </a>");
						//out.println("<a href=\"userPage.jsp?MyBandPageID=" + i + "\">"
						//	+ i + "</a>");
					}
				%>

			</h1>
		</ul>
	</div>
</body>
</html>