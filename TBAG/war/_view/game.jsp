<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Spooky York</title>
    <link rel="stylesheet" type="text/css" href="gameStylesheet.css">

</head>
<body>
    <div id="logo-container">
        <img src="https://blog.flamingtext.com/blog/2024/03/13/flamingtext_com_1710299194_29928696.png" border="0" alt="Game title" title="title">
    </div>
    <div id="wrapper">
        <div id="content-container">
            <div id="game-container">
                <div id="game-text">
                    <!-- This is where the game text will appear -->
                </div>
                <form id="game-form" action="game" method="post">
                    <input type="text" id="command-line" name="userInput" placeholder="Type your command...">
                </form>
            </div>
            <!-- arrow buttons -->
            <div id="buttons-container" style="display: none;">
                <span id="button-span">
                    <button id="button1" class="game-button" type="button"><</button>
                    <button id="button2" class="game-button" type="button">></button>
                    <button id="button3" class="game-button" type="button">^</button>
                    <button id="button4" class="game-button" type="button">v</button>
                </span>
            </div>
            <!-- pause button -->
            <div id="pause">
                <form function="PauseServlet" method="get">
                    <input type="submit" name="function" value="Pause" class="pause">
                </form>
            </div>
            <!-- HEALTH STATUS -->
            <div class="health-bar-container">
                <div class="health-text">Health Level</div>
                <div class="health-bar">
                    <div class="bar" style="width: 100%;"></div>
                </div>
            </div>
        </div>
    </div>

    <script>
    document.addEventListener("DOMContentLoaded", function() {
        // Display initial prompt
        var gameText = document.getElementById("game-text");

        var form = document.getElementById("game-form");
        var commandLine = document.getElementById("command-line");
        var buttonsContainer = document.getElementById("buttons-container");
        var directions = []; // Array to store entered directions

        form.addEventListener("submit", function(event) {
            event.preventDefault(); // Prevent default form submission
            var userInput = commandLine.value.trim().toLowerCase();
            if (userInput === "north" || userInput === "south" || userInput === "east" || userInput === "west") {
                if (!directions.includes(userInput)) {
                    directions.push(userInput); // Add direction to array if not already present
                }
            }
            // Check if all directions have been entered
            if (directions.length === 4) {
                buttonsContainer.style.display = "block"; // Show buttons
            }
            sendCommand(userInput);
            commandLine.value = ""; // Clear input field
        });

        function sendCommand(userInput) {
            var xhr = new XMLHttpRequest();
            xhr.open("POST", form.action, true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    // Clear previous game text
                    gameText.innerHTML = "";
                    // Split response text into lines
                    var responseLines = xhr.responseText.split('\n');
                    // Display each line of response
                    responseLines.forEach(function(line) {
                        gameText.innerHTML += "<p>" + line + "</p>";
                    });
                    gameText.scrollTop = gameText.scrollHeight;
                }
            };
            xhr.send("userInput=" + encodeURIComponent(userInput));
        }

        document.getElementById("button1").addEventListener("click", function() {
            sendCommand("west"); 
        });
        document.getElementById("button2").addEventListener("click", function() {
            sendCommand("east"); 
        });
        document.getElementById("button3").addEventListener("click", function() {
            sendCommand("north"); 
        });
        document.getElementById("button4").addEventListener("click", function() {
            sendCommand("south"); 
        });
    });
</script>

</body>
</html>
