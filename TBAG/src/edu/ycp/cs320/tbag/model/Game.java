package edu.ycp.cs320.tbag.model;

public class Game {
    public String processUserInput(String userInput) {
        // Convert user input to lowercase for case-insensitive comparison
        String input = userInput.toLowerCase();

        // Process user input and return game response
        if (input.equals("up")) {
            return "You moved up!";
        } else if (input.equals("down")) {
            return "You moved down!";
        } else if (input.equals("left")) {
            return "You moved left!";
        } else if (input.equals("right")) {
            return "You moved right!";
        } else {
            return "Invalid command.";
        }
    }
}

