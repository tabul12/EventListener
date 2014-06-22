<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en"> <!--<![endif]-->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Add event</title>
  <link rel="stylesheet" href="eventRegisterCSS.css">
  <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body>
  <section class="container">
    <div class="login">
      <h1>Add event </h1>
      <form action="EventRegistrationServlet" method="post">
        <p><input id="txt" type="text" name="EventName"  placeholder="Event Name" required></p>
        <p><input id="txt" type="text" name="Place"  placeholder="Place" required></p>
		
        <p>
        <input id="day" type="text" name="Day"  placeholder="Day" required>
        <input id="month" type="text" name="Month"  placeholder="Month" required>
        <input id="year" type="text" name="Year"  placeholder="Year" required>
        </p>
        <p><input id="txt" type="text" name="Price"  placeholder="Price" required></p>
        <p><input id="txt"type="text" name="Image"  placeholder="Image" required></p>
        <p><textarea  name="About"  placeholder="About" rows="4" cols="26"></textarea></p>
        <p class="submit"><input type="submit" name="commit" value="Add"></p>
      </form>
    </div>
  </section>

   
</body>
</html>