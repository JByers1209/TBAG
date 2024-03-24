package edu.ycp.cs320.tbag.controller;

import edu.ycp.cs320.tbag.model.Game;
import edu.ycp.cs320.tbag.model.Player;
import edu.ycp.cs320.tbag.model.Room;

public class GameEngine {
    private Game game;
    private String response;
	
	Room start = new Room("Start", "You are in the starter room!");
	Room up = new Room("Above", "The above room");
	Room left = new Room("Left", "The left room");
	Room right = new Room("Right", "The right room");
	Room down = new Room("Below", "The below room");
	
	Player player = new Player(start);
	
	Room currentRoom;

	//Activates initial starting data
	public String start() {
		Room room = player.getCurrentRoom();
		room.setExit("left", left);
		room.setExit("right", right);
		room.setExit("up", up);
		room.setExit("down", down);
		
		String startingText = room.getDescription();
		return startingText;
	}
   
    public String processUserInput(String userInput) {
        // Convert user input to lowercase for case-insensitive comparison
        String input = userInput.toLowerCase();

        // Process user input and return game response
        if (input.equals("start")) {
        	response = start();
        }else if (input.equals("up")) {
        	player.moveTo(up);
        	response = "You move north.";
        } else if (input.equals("down")) {
        	player.moveTo(down);
        	response = "You move south.";
        } else if (input.equals("left")) {
        	player.moveTo(left);
        	response = "You move west.";
        } else if (input.equals("right")) {
        	player.moveTo(right);
        	response = "You move east.";
        } else if (input.equals("location")){
        	currentRoom = player.getCurrentRoom();
        	response = "Current Location: " + currentRoom.getName();
        }else {
            return "Invalid command.";
        }
        return response;
    }
    
}
