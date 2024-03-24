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
            <!-- This is where the game text will appear -->
        </div>
        <form id="game-form" action="game" method="post"> <!-- Changed method to "post" -->
            <input type="text" id="command-line" name="userInput" placeholder="Type your command..."> <!-- Added name attribute -->
        </form>
    </div>
    <div> <!-- Moved form inside a container -->
        <form action="GameServlet" method="get"> <!-- Changed "function" to "action" -->
            <input type="submit" name="function" value="Pause" class="submit">
        </form>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            // Display initial prompt
            var gameText = document.getElementById("game-text");
            gameText.innerHTML += "<p>Welcome to Spooky York! Type your commands in the box below.</p>";

            var form = document.getElementById("game-form");
            var commandLine = document.getElementById("command-line");

            form.addEventListener("submit", function(event) {
                event.preventDefault(); // Prevent default form submission

                var userInput = commandLine.value.trim(); // Extract user input
                commandLine.value = ""; // Clear input field

                // Send AJAX request to server
                var xhr = new XMLHttpRequest();
                xhr.open("POST", form.action, true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function() {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        // Append new text to existing content
                        gameText.innerHTML += "<p><strong> >  "  + userInput +  "</p>";                                             
                        gameText.innerHTML += "<p>" + xhr.responseText + "</p>";
                    }
                };
                xhr.send("userInput=" + encodeURIComponent(userInput));
            });
        });
    </script>
</body>
</html>
