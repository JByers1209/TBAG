<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Spooky York</title>
    <link rel="stylesheet" type="text/css" href="styleSheet2.css">
</head>

<body>
    <div id="logo-container">
        <img src="https://blog.flamingtext.com/blog/2024/03/13/flamingtext_com_1710299194_29928696.png" border="0" alt="Game title" title="title">
    </div>

    <div id="game-container">
        <div id="game-text">
            
        </div>
        <form id="command-form" action="GameServlet" method="post">
            <input type="text" id="command-line" name="userInput" placeholder="Type your command...">
            <input type="submit" value="Submit">
        </form>        
    </div>

    <script>
        // Get the form
        var form = document.getElementById("command-form");
        
        // Add event listener for form submission
        form.addEventListener("submit", function(event) {
            // Prevent the default form submission behavior
            event.preventDefault();
            
            // Submit the form
            form.submit();
        });
    </script>
    
    
</body>
</html>
