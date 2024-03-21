public class GameEngine {
    private String testText;

    public GameEngine() {
        testText = "";
    }

    public String processInput(String userInput) {
        if (userInput != null) {
            System.out.println("Received command: " + userInput); // Add this line to log the received command
            switch (userInput.toLowerCase()) {
                case "up":
                    testText = "You move up.";
                    break;
                case "down":
                    testText = "You move down.";
                    break;
                // Add cases for other directions or actions
                default:
                    testText = "Invalid command!";
            }
            System.out.println("Result: " + testText); // Add this line to log the result
        }
        // Return output to display to the user
        return testText;
    }
}
