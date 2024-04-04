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
        <form id="loginForm" function="LoginServlet" method="get">
            <input type="text" id="username" name="username" placeholder="Username" required>
            <input type="password" id="password" name="password" placeholder="Password" required>
            <input type="submit" value="Login Now">
        </form>
        <p id="error" style="color: red; display: none;">Incorrect username or password</p>
    </div>

    <script>
        document.getElementById("loginForm").addEventListener("submit", function(event) {
            event.preventDefault(); // Prevent the form from submitting normally
            
            // Get username and password values
            var username = document.getElementById("username").value;
            var password = document.getElementById("password").value;

            // Check if username and password are correct
            if (username === "admin" && password === "password") {
                // If correct, submit the form
                this.submit();
            } else {
                // If incorrect, display error message
                document.getElementById("error").style.display = "block";
            }
        });
    </script>
</body>
</html>
