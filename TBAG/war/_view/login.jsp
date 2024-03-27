<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Spooky York</title>
    <link rel="stylesheet" type="text/css" href="styleSheet.css">
    <style>
        body {
            background-color: var(--gray);
            color: white;
            font-family: Arial, sans-serif;
            display: flex;
            flex-direction: column; /* Align items in a column */
            align-items: center; /* Center horizontally */
            justify-content: center; /* Center vertically */
            height: 100vh; /* Set height to viewport height */
            margin: 0; /* Remove default margin */
            padding: 0; /* Remove default padding */
        }
        .container {
            text-align: center; /* Center the content horizontally */
        }
        .logo-container {
            margin-bottom: 20px; /* Adjust the margin between the logo and the button */
        }
        img {
            max-width: 100%; /* Prevent image squishing */
            max-height: 100%; /* Prevent image squishing */
            margin-bottom: -80px; /* Adjust the margin between the two images */
        }
        .login-container {
            margin-top: 50px; /* Adjust margin for the login container */
        }
        #login-form {
            margin-top: 20px; /* Adjust margin for the login form */
        }
        #login-form input[type="text"],
        #login-form input[type="password"],
        #login-form input[type="submit"] {
            margin: 5px auto; /* Center inputs horizontally */
            display: block; /* Make inputs appear on separate lines */
            width: 200px; /* Set width for inputs */
            padding: 10px; /* Add padding to inputs */
            border-radius: 5px; /* Add border radius to inputs */
            border: 1px solid #ccc; /* Add border to inputs */
        }
        #login-form input[type="submit"] {
            background-color: #511717;
            border: none;
            color: white;
            padding: 15px 32px;
            font-size: 16px;
            transition-duration: 0.4s;
            cursor: pointer;
            border-radius: 10px;
        }
        #login-form input[type="submit"]:hover {
            background-color: #ff8080;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="logo-container">
            <img src="https://blog.flamingtext.com/blog/2024/03/13/flamingtext_com_1710299194_29928696.png" style="max-width:300px; max-height:300px;">
        </div>
        <h2>
            <div>
                <img src="https://blog.flamingtext.com/blog/2024/03/14/flamingtext_com_1710447290_29928759.png" border="0" style="max-width:300px; max-height:300px;">
            </div>
        </h2>
        <div class="login-container">
            <form id="login-form" action="login" method="post"> <!-- corrected form attributes -->
                <input type="text" name="username" placeholder="Username" required>
                <input type="password" name="password" placeholder="Password" required>
                <input type="submit" value="Login">
            </form>
        </div>
    </div>
</body>
</html>
