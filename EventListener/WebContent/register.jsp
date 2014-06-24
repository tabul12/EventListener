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

					<!-- Place this asynchronous JavaScript just before your </body> tag -->
			<script type="text/javascript">
				(function() {
					var po = document.createElement('script');
					po.type = 'text/javascript';
					po.async = true;
					po.src = 'https://apis.google.com/js/client:plusone.js';
					var s = document.getElementsByTagName('script')[0];
					s.parentNode.insertBefore(po, s);
				})();
			</script>
			
			<script type="text/javascript">
				scopes = [ "https://www.googleapis.com/auth/plus.me",
						"https://www.googleapis.com/auth/userinfo.email" ];
				
				function signinCallback(authResult) {
					if (authResult['status']['signed_in']) {
						gapi.client.load('plus', 'v1');
						var request = gapi.client.plus.people.get({
							'userId' : 'me'
						});
						
						
						var name = 'semi';
						//name = resp.name.givenName;
						 
						var mail;
						request.execute(function(resp) {			 
							console.log('name ' + resp.name.givenName);
							console.log('Image URL: ' + resp.image.url);
							console.log('Profile URL: ' + resp.url);
							 
							
							gapi.client.load('oauth2', 'v2', function() {
								gapi.client.oauth2.userinfo.get().execute(function(response) {
									console.log('user email :'+ response.email);
									window.location.replace("fbLogin?email=" + response.email + "&name=" + resp.name.givenName +"&lastname=" + 
										resp.name.familyName + "&id=" + resp.id);
								})
							});
						});
						
						 
						
						
						 
						
						document.getElementById('signinButton').setAttribute('style',
								'display: none');
					} else {
						// Update the app to reflect a signed out user
						// Possible error values:
						//   "user_signed_out" - User is signed-out
						//   "access_denied" - User denied access to your app
						//   "immediate_failed" - Could not automatically log in the user
						console.log('Sign-in state: ' + authResult['error']);
					}
				}
			
			</script>

 
<body >
 
 <script>
  // This is called with the results from from FB.getLoginStatus().
  function statusChangeCallback(response) {
    console.log('statusChangeCallback');
    console.log(response);
    // The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') {
      // Logged into your app and Facebook.
      testAPI();
    } else if (response.status === 'not_authorized') {
      // The person is logged into Facebook, but not your app.
      document.getElementById('status').innerHTML = 'Please log ' +
        'into this app.';
    } else {
      // The person is not logged into Facebook, so we're not sure if
      // they are logged into this app or not.
      document.getElementById('status').innerHTML = '';
    }
  }

  // This function is called when someone finishes with the Login
  // Button.  See the onlogin handler attached to it in the sample
  // code below.
  function checkLoginState() {
    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });
  }

  window.fbAsyncInit = function() {
  FB.init({
    appId      : '709906935736081',
    cookie     : true,  // enable cookies to allow the server to access 
                        // the session
    xfbml      : true,  // parse social plugins on this page
    version    : 'v2.0' // use version 2.0
  });

  // Now that we've initialized the JavaScript SDK, we call 
  // FB.getLoginStatus().  This function gets the state of the
  // person visiting this page and can return one of three states to
  // the callback you provide.  They can be:
  //
  // 1. Logged into your app ('connected')
  // 2. Logged into Facebook, but not your app ('not_authorized')
  // 3. Not logged into Facebook and can't tell if they are logged into
  //    your app or not.
  //
  // These three cases are handled in the callback function.

  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });

  };

  // Load the SDK asynchronously
  (function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));

  // Here we run a very simple test of the Graph API after login is
  // successful.  See statusChangeCallback() for when this call is made.
  function testAPI() {
    console.log('Welcome!  Fetching your information.... ');
    FB.api('/me', function(response) {
    	window.location.replace("fbLogin?name=" + response.first_name +"&lastname=" + 
				response.last_name + "&email=" + response.email + "&id=" + response.id);
      console.log('Successful login for: ' + response.name);
      document.getElementById('status').innerHTML =
        'Thanks for logging in, ' + response.name + '!';
    });
  }
</script>


				     						 
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
     		 	
     		 	 <p>
				<span id="signinButton"> <span class="g-signin"
					data-callback="signinCallback"
					data-clientid="451986216825-dp9tb8b54iotm832n20h0smqaidtr1ld.apps.googleusercontent.com"
					data-cookiepolicy="single_host_origin"
					data-requestvisibleactions="http://schemas.google.com/AddActivity"
					data-scope="https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email"> </span>
				</span>  
				</p>
				
     		 	<p>
					<fb:login-button size="large" scope="public_profile,email" onlogin="checkLoginState();">
					</fb:login-button>
				</p>
			 
				 
    </div>
  </section>

   
</body>
</html>