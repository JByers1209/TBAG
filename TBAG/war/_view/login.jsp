<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Spooky York</title>
    <link rel="stylesheet" type="text/css" href="styleSheet.css">
</head>
<body>
    <div class= "logo-container">
      <img src="https://blog.flamingtext.com/blog/2024/03/13/flamingtext_com_1710299194_29928696.png" style="width:300px;height:300px;">
    </div>
    <div class="login">
        <div>
            <img src="https://blog.flamingtext.com/blog/2024/03/14/flamingtext_com_1710447290_29928759.png" border="0" style="width:300px;height:300px;">
        </div>
    </div>
    <div id="authentication">
        <form function="LoginServlet" method="get">
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <input type="submit" value="Login Now">
        </form>
    </div>
</body>
</html>
