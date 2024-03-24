package edu.ycp.cs320.tbag.model;

import java.util.ArrayList;
import edu.ycp.cs320.tbag.model.Player;
import edu.ycp.cs320.tbag.model.Room;

public class Game {
	
	String response;
	boolean hasStarted = false;
	boolean canMove = true;
	Room currentRoom;
	Player player;
	ArrayList<Room> rooms;

	public void setup() {
	    // Create an ArrayList to hold Room objects
		rooms = new ArrayList<>();
	    
	    // Create rooms and add them to the rooms ArrayList
	    Room startRoom = new Room("Start", "You wake up in a strange place. The sky is cloudy and there is nobody else in sight."+
	    		" You are all alone. Where will you go next?");
	    rooms.add(startRoom);
	    
	    Room campusRoom = new Room("York College Campus", "You arrive at the york college campus. Screaming is heard in the distance.");
	    rooms.add(campusRoom);
	    
	    Room neRoom = new Room("North East", "The northeast room");
	    rooms.add(neRoom);
	    
	    Room eastRoom = new Room("East", "The east room");
	    rooms.add(eastRoom);
	    
	    Room seRoom = new Room("South East", "The southeast room");
	    rooms.add(seRoom);
	    
	    Room southRoom = new Room("south", "The south room");
	    rooms.add(southRoom);
	    
	    Room swRoom = new Room("South West", "The soutwest room");
	    rooms.add(swRoom);
	    
	    Room westRoom = new Room("East", "The west room");
	    rooms.add(westRoom);
	    
	    Room nwRoom = new Room("North West", "The northwest room");
	    rooms.add(nwRoom);
	    
	  //Set room exits for the starter room
	  		startRoom.setExit("north", campusRoom);
	  		startRoom.setExit("east", eastRoom);
	  		startRoom.setExit("south", southRoom);
	  		startRoom.setExit("west", westRoom);
	  		
	  	//Set room exits for the campus room
	  		campusRoom.setExit("north", null);
	  		campusRoom.setExit("east", neRoom);
	  		campusRoom.setExit("south", startRoom);
	  		campusRoom.setExit("west", nwRoom);
	  		
	  	//Set room exits for the northeast room
	  		neRoom.setExit("north", null);
	  		neRoom.setExit("east", null);
	  		neRoom.setExit("south", eastRoom);
	  		neRoom.setExit("west", campusRoom);
	  		
	  	//Set room exits for the east room
	  		eastRoom.setExit("north", neRoom);
	  		eastRoom.setExit("east", null);
	  		eastRoom.setExit("south", seRoom);
	  		eastRoom.setExit("west", startRoom);
	  	
	  	//Set room exits for the southeast room
	  		seRoom.setExit("north", eastRoom);
	  		seRoom.setExit("east", null);
	  		seRoom.setExit("south", null);
	  		seRoom.setExit("west", southRoom);
	  		
	  	//Set room exits for the south room
	  		southRoom.setExit("north", startRoom);
	  		southRoom.setExit("east", seRoom);
	  		southRoom.setExit("south", null);
	  		southRoom.setExit("west", swRoom);
	  		
	  	//Set room exits for the southwest room
	  		swRoom.setExit("north", westRoom);
	  		swRoom.setExit("east", southRoom);
	  		swRoom.setExit("south", null);
	  		swRoom.setExit("west", null);
	  		
	  	//Set room exits for the west room
	  		westRoom.setExit("north", nwRoom);
	  		westRoom.setExit("east", startRoom);
	  		westRoom.setExit("south", swRoom);
	  		westRoom.setExit("west", null);
	    
	  	//Set room exits for the northwest room
	  		nwRoom.setExit("north", null);
	  		nwRoom.setExit("east", campusRoom);
	  		nwRoom.setExit("south", westRoom);
	  		nwRoom.setExit("west", null);
	  	
	        player = new Player(startRoom);
	        currentRoom = startRoom;
	}
	
	//Activates initial starting data
		public String start() {
			if(hasStarted == true) {
				return "Game has already started";
			}else {
				setup();
				String startingText = currentRoom.getDescription();
				hasStarted = true;
				return startingText;
			}
		}
		public String processUserInput(String userInput) {
		    // Convert user input to lowercase for case-insensitive comparison
		    String input = userInput.toLowerCase();

		    if (input.equals("start")) {
		        response = start();
		    } else if (!hasStarted) {
		        response = "Type start to begin";
		    } else {
		        switch (input) {
		            case "north":
		            case "south":
		            case "west":
		            case "east":
		                Room nextRoom = currentRoom.getExit(input);
		                if (nextRoom != null) {
		                    player.moveTo(nextRoom);
		                    currentRoom = player.getCurrentRoom();
		                    response = "You move " + input + ". New Location: " + currentRoom.getName();
		                } else {
		                    response = "You cannot move that way.";
		                }
		                break;
		            case "location":
		                currentRoom = player.getCurrentRoom();
		                response = "Current Location: " + currentRoom.getName();
		                break;
		            case "save game":
		                response = "Game Saved.";
		                break;
		            default:
		                response = "Invalid command.";
		                break;
		        }
		    }
		    return response;
		}

}

