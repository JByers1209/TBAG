<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Spooky York</title>
    <link rel="stylesheet" type="text/css" href="styleSheet.css">
</head>

<body>
    <div class="container">
        <div class= "logo-container">
            <img src="https://blog.flamingtext.com/blog/2024/03/13/flamingtext_com_1710299194_29928696.png">
            <img src="https://blog.flamingtext.com/blog/2024/03/27/flamingtext_com_1711512717_626226192.png">
        </div>
    </div>
    <div id="authentication">
        <form id="loginForm" action="/tbag/login" method="post">
            <input type="text" id="username" name="username" placeholder="Username" required>
            <input type="password" id="password" name="password" placeholder="Password" required>
            <button type="submit">Login Now</button>
        </form>
        <p id="error" style="color: red; display: none;">Incorrect username or password</p>
    </div>
</body>
</html>