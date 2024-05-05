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
        </div>
    </div>
    <div id="authentication">
<<<<<<< HEAD
=======
        <h2>Login</h2>
>>>>>>> master
        <form id="loginForm" action="/tbag/login" method="post">
            <input type="text" id="username" name="username" placeholder="Username" required>
            <input type="password" id="password" name="password" placeholder="Password" required>
            <button type="submit">Login Now</button>
        </form>
        <p id="error" style="color: red;"></p>
        <div id="registrationBox">
            <h3>Create an account</h3>
            <form id="registerForm" action="/tbag/register" method="post">
                <input type="text" id="newUsername" name="newUsername" placeholder="New Username" required>
                <input type="password" id="newPassword" name="newPassword" placeholder="New Password" required>
                <button type="submit">Register</button>
            </form>
        </div>
    </div>
<<<<<<< HEAD
</body>
</html>
=======

    <script>
        // Get the error paragraph element
        var errorParagraph = document.getElementById("error");
    
        // Check if the error parameter is present in the URL
        var urlParams = new URLSearchParams(window.location.search);
        var errorParam = urlParams.get('error');
        if (errorParam === "1") {
            // If error parameter is present and equals 1, display the error message
            errorParagraph.textContent = "Incorrect username or password";
        } else if (errorParam === "2") {
            // If error parameter is present and equals 2, display the message for account created
            errorParagraph.textContent = "Account created!";
        } else if (errorParam === "3") {
            // If error parameter is present and equals 3, display the message for username taken
            errorParagraph.textContent = "Username taken";
        }
    </script>
    
</body>
</html>

>>>>>>> master
