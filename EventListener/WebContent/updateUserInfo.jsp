<%@page import="baseConnection.UserManager"%>
<%@page import="objects.User"%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Update info</title>
<link rel="stylesheet" href="updateUserInfoCSS.css">
<!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<%
	UserManager userManager = (UserManager) application
			.getAttribute("UserManager");
	User user = userManager.getUser(83);
%>
<body>
	<section class="container">
		<div class="login">
			<h1>Update info</h1>
			<form action="UpdateUserInfoServlet" method="post">
				<p>
					<%
						out.println("<input type=\"text\" name=\"FirstName\" value=\""
								+ user.getName() + "\">");
					%>
					FirsName
				</p>
				<p>
					<%
						out.println("<input type=\"text\" name=\"LastName\" value=\""
								+ user.getLastName() + "\">");
					%>
					LastName
				</p>
				<p>
					<%
						out.println("<input type=\"text\" name=\"Mail\" value=\""
								+ user.getMail() + "\">");
					%>
					Mail
				</p>
				<p>
					<%
						out.println("<input type=\"text\" name=\"MobileNumber\" value=\""
								+ user.getMobileNumber() + "\">");
					%>
					Mobile
				</p>
				<p>
				
				<p>
					<%
						out.println("<input type=\"text\" name=\"Password\" value=\""
								+ user.getPassword() + "\">");
					%>
					Password
				</p>
				<p class="submit">
					<input type="submit" name="commit" value="Save">
				</p>
			</form>
		</div>
	</section>


</body>
</html>