<%@page import="errors.ConstantValues"%>
<%@page import="objects.Band"%>
<%@page import="baseConnection.BandManager"%>
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
	<div class="post" id="WishList">
		<div>
			<h1 align="center">WishList</h1>
			<%
			    int userID = Integer.parseInt(request.getParameter("UserID"));
	            UserManager userManager =(UserManager) application.getAttribute("UserManager");
	            Integer wishListPageNum = Integer.parseInt(request.getParameter("page"));
		
				ArrayList<Integer> wishList = new ArrayList<Integer>();
				wishList = userManager.getWishlist(userID, wishListPageNum);
				for (int i = 0; i < wishList.size(); i++) {
					int k = wishList.get(i);
					BandManager bandManager = (BandManager) application.getAttribute("BandManager");
					Band band = bandManager.getBand(k);
					if (bandManager.getProfileImage(k) != null) {
						out.println("<img src=\"" + bandManager.getProfileImage(k)
								+ "\" height=\"120\" width=\"120\" />");
					} else {
						out.println("<img src=\"images/default.jpg\" height=\"120\" width=\"120\" />");
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
					int numWishList = userManager.getWhishlistNum(userID);
					int wishListPerPage = ConstantValues.NUM_WISHLIST_ON_PER_PAGE;
					int numWishListPages = numWishList / wishListPerPage;

					if (numWishList % wishListPerPage > 0)
						numWishListPages++;

					int startWishListPageNum = Math.max(1, wishListPageNum
							- ConstantValues.NUM_LEFT_RIGHT_PAGES);
					int endWishListPageNum = Math.min(numWishListPages, wishListPageNum
							+ ConstantValues.NUM_LEFT_RIGHT_PAGES);

					for (int i = startWishListPageNum; i <= endWishListPageNum; i++) {
						out.println("<a href=# onclick=loadXMLDocForUser(" + i
								+ ",'WishList'," + userID + ")>" + i + " </a>");
					}
				%>

			</h1>
		</ul>
	</div>
</body>
</html>