<%@page import="errors.BaseErrors"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en"> <!--<![endif]-->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Login Form</title>
  <link rel="stylesheet" href="registerCSS.css">
  <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body >
 
				     						 
  <section class="container">
    <div class="login">
     
      <h1>Sign Up </h1>
      <%  
      	Integer number = (Integer)session.getAttribute("UserNameAlreadyUsed");
      	int num  = -1;
      	if(number != null) num = number;
      	
    	if(num == BaseErrors.USER_NAME_ALREADY_USED) {
    		out.println("<h5> This UserName already used. Choose another</h5>");
    	}
    %> 
      <form method="post" action="registrationServlet" type="text/css">
        <p><input type="text" name="FirstName"   placeholder="FirstName"></p>
        <p><input type="text" name="LastName"  placeholder="LastName"></p>
        <p><input type="text" name="UserName"  placeholder="UserName"></p>
        <p><input type="password" name="Password"  placeholder="Password"></p>
        <p><input type="text" name="Mail"  placeholder="Mail"></p>
        <p><input type="text" name="MobileNumber"  placeholder="MobileNumber"></p>
       
        <p class="submit"><input type="submit" name="commit" value="Sign Up"></p>
      </form>
    </div>
  </section>

   
</body>
</html>