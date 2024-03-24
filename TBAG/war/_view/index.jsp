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
        <div class="logo-container">
            <img src="https://blog.flamingtext.com/blog/2024/03/13/flamingtext_com_1710299194_29928696.png" border="0" alt="Game title" title="title">
            <img src="https://blog.flamingtext.com/blog/2024/03/13/flamingtext_com_1710299127_29928695.png" border="0" alt="Game subtitle" title="subtitle">
        </div>
        
       <!-- login button--> 
        <div id ="login-form">
        	<form action="IndexServlet" method="get">
                <input type="submit" value="Login" class="submit">
            </form>
        </div>
        
       <!-- play button -->  
        <div id ="play-form">
            <form action="IndexServlet" method="get">
                <input type="submit" value="Play" class="submit"> 
            </form>
        </div>
       
    </div>
</body>
</html>