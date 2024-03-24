package edu.ycp.cs320.tbag.controller;

import edu.ycp.cs320.tbag.model.Game;
import edu.ycp.cs320.tbag.model.Player;
import edu.ycp.cs320.tbag.model.Room;

public class GameEngine {
    private Game game;
    private String response;
	
	Room start = new Room("Start", "You are in the starter room!");
	Room north = new Room("North", "The north room");
	Room west = new Room("West", "The west room");
	Room east = new Room("East", "The east room");
	Room south = new Room("South", "The south room");
	
	Player player = new Player(start);
	
	Room currentRoom;
	
	boolean hasStarted = false;

	//Activates initial starting data
	public String start() {
		
		Room room = player.getCurrentRoom();
		room.setExit("west", west);
		room.setExit("east", east);
		room.setExit("north", north);
		room.setExit("south", south);
		
		if(hasStarted == true) {
			return "Game has already started";
		}else {
			String startingText = room.getDescription();
			hasStarted = true;
			return startingText;
		}
	}
   
    public String processUserInput(String userInput) {
        // Convert user input to lowercase for case-insensitive comparison
        String input = userInput.toLowerCase();

        // Process user input and return game response
        if (input.equals("start")) {
        	response = start();
        }else if (input.equals("north")) {
        	player.moveTo(north);
        	response = "You move north.";
        } else if (input.equals("south")) {
        	player.moveTo(south);
        	response = "You move south.";
        } else if (input.equals("west")) {
        	player.moveTo(west);
        	response = "You move west.";
        } else if (input.equals("east")) {
        	player.moveTo(east);
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
