package edu.ycp.cs320.tbag.controller;

public class GameEngine {
    // Method to process user input commands and return game responses
    public String processUserInput(String userInput) {
        // Process user input and generate game response
        switch (userInput.toLowerCase()) {
            case "up":
                return "You moved up!";
            case "down":
                return "You moved down!";
            case "left":
                return "You moved left!";
            case "right":
                return "You moved right!";
            default:
                return "Invalid command.";
        }
    }
    
