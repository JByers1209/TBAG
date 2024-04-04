<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Spooky York</title>
<link rel="stylesheet" type="text/css" href="gameStyles.css">
</head>
<body>

  <h1>
    <div>
      <img src="https://blog.flamingtext.com/blog/2024/03/13/flamingtext_com_1710299194_29928696.png" style="width:300px;height:300px;">   
    </div>
  </h1>

  <h2>
    <div>
      <img src="https://blog.flamingtext.com/blog/2024/03/14/flamingtext_com_1710445150_29928758.png" style="width:300px;height:300px;">   
    </div>
  </h2>

    <div id = "Resume">
        <form function="GameServlet" method="get">
            <input type="submit" name="function" value="Resume" class="submit">
        </form>
    </div>
    <!-- <input type="submit" name= "function" value="Restart Game" class="submit">  -->
   <div id = Quit-form>
    <form function="IndexServlet" method="get">
        <input type="submit" name="function" value="Quit Game" class="submit">
    </form>
   </div> 
</body>
</html>
