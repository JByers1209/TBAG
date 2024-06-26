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
            <div id="search-button" style="display: none;">
                <button id="searchButton" class="extra-button" type="button">Search</button>
            </div>
            <div id="punch-button" style="display: none;">
                <button id="punchButton" class="extra-button" type="button">Punch</button>
            </div>
            <div id="kick-button" style="display: none;">
                <button id="kickButton" class="extra-button" type="button">Kick</button>
            </div>
            
            <!-- pause button -->
            <div id="pause">
                <form function="PauseServlet" method="get">
                    <input type="submit" name="function" value="Quit" class="pause">
                </form>
            </div>
            <!-- HEALTH STATUS -->
            <div class="health-bar-container">
                <div class="health-text">Health Level</div>
                <div class="health-bar">
                    <div class="bar"></div>
                </div>
                
            </div>
        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            var gameText = document.getElementById("game-text");
            var form = document.getElementById("game-form");
            var commandLine = document.getElementById("command-line");
            var buttonsContainer = document.getElementById("buttons-container");
            var directions = []; // Array to store entered directions
    
            // Function to send command to servlet
            function sendCommand(userInput) {
                var xhr = new XMLHttpRequest();
                xhr.open("POST", form.action, true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function() {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        handleResponse(xhr.responseText);
                    }
                };
                xhr.send("userInput=" + encodeURIComponent(userInput));
            }
    
            // Function to handle response from servlet
            function handleResponse(responseText) {
                // Clear previous game text
                gameText.innerHTML = "";
                
                // Split response text into lines
                var responseLines = responseText.split('\n');
                
                // Extract health information (assuming it's the first part of the response)
                var healthInfo = responseLines[0];
                
                // Call the updateHealthBar function to update the health bar
                updateHealthBar(healthInfo);
                
                // Display each line of response except the health information
                for (var i = 1; i < responseLines.length; i++) {
                    gameText.innerHTML += "<p>" + responseLines[i] + "</p>";
                }
                gameText.scrollTop = gameText.scrollHeight;
            }
    
            // Function to update health bar width
            function updateHealthBar(responseText) {
                // Split the response text by the delimiter
                var responseParts = responseText.split('$');
    
                // Extract the health percentage from the response
                var healthPercentage = parseInt(responseParts[0]);
    
                // Update width of health bar
                var healthBar = document.querySelector(".health-bar .bar");
                healthBar.style.width = healthPercentage + "%";
            }
    
            // Function to send initial GET request
            function sendGetRequest() {
                var xhr = new XMLHttpRequest();
                xhr.open("GET", "game", true);
                xhr.onreadystatechange = function() {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        // Handle response if needed
                    }
                };
                xhr.send();
            }
    
            // Event listener for form submission
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
    
    // Show corresponding buttons based on user input
    showButtons(userInput);
});

// Function to show corresponding buttons based on user input
function showButtons(userInput) {
    var searchButton = document.getElementById("search-button");
    var punchButton = document.getElementById("punch-button");
    var kickButton = document.getElementById("kick-button");

    switch (userInput) {
        case "search":
            searchButton.style.display = "block";
            break;
        case "punch":
            punchButton.style.display = "block";
            break;
        case "kick":
            kickButton.style.display = "block";
            break;
        default:
            break;
    }
}
            // Event listeners for direction buttons
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
            document.getElementById("searchButton").addEventListener("click", function() {
                sendCommand("search"); 
            });
            document.getElementById("kickButton").addEventListener("click", function() {
                sendCommand("kick"); 
            });
            document.getElementById("punchButton").addEventListener("click", function() {
                sendCommand("punch"); 
            });
    
            // Initial GET request
            sendGetRequest();
            sendCommand("preStart");
        });
    </script>

</body>
</html>