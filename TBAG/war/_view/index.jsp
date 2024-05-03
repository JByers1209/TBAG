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
        <div id="login-form">
            <!-- Check if the user is logged in and has a session with user ID -->
            <% if (session.getAttribute("user_id") != null) { %>
                <!-- User box -->
                <div class="user-box">
                    <% if (session.getAttribute("user_id") != null) { %>
                        <!-- Display username -->
                        <p>Welcome, <%= session.getAttribute("username") %>!</p>
                    <% } %>
                </div>
                <form function="IndexServlet" method="get">
                    <input type="submit" name="function" value="Play" class="submit">
                </form>
                <!-- Display the "Resume Game" button -->
                <form function="ResumeServlet" method="get">
                    <input type="submit" name="function" value="Resume" class="submit">
                </form>
                <!-- Logout button -->
                <form action="LogoutServlet" method="get">
                    <input type="submit" value="Logout" class="submit">
                </form>                
            <% } else { %>
                <!-- Render only Play and Login buttons if the user is not logged in -->
                <form function="IndexServlet" method="get">
                    <input type="submit" name="function" value="Play" class="submit">
                </form>
                <form function="IndexServlet" method="get">
                    <input type="submit" name="function" value="Login" class="submit">
                </form>
            <% } %>
        </div>
    </div>
</body>
</html>
