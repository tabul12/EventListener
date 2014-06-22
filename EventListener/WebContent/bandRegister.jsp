<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en"> <!--<![endif]-->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Add band</title>
  <link rel="stylesheet" href="bandRegisterCSS.css">
  <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body background="images/band.png">
  <section class="container">
    <div class="login">
      <h1>Add band </h1>
      <form  action="BandRegistrationServlet"  method="post" >
        <p><input type="text" name="BandName"   placeholder="Band Name" required></p>
        <p><input type="text" name="Mail"  placeholder="Mail" required></p>
        <p><textarea  name="About"  placeholder="About" rows="4" cols="26"></textarea></p>
        <p class="submit"><input type="submit" name="commit" value="Add"></p>
      </form>
    </div>
  </section>

   
</body>
</html>