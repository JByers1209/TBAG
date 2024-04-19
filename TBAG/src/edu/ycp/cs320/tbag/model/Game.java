package edu.ycp.cs320.tbag.model;

import java.util.ArrayList;

import edu.ycp.cs320.tbag.dataBase.DerbyDatabase;

public class Game {
	
	String response;
	boolean hasStarted = false;
	boolean canMove = true;
	Room currentRoom;
	Player player;

	DerbyDatabase db = new DerbyDatabase();
	
	public void setup() {
	  
	  	
	  	//Create items for rooms/player
	  		Item blue_key = new KeyItem("Blue Key", false);
	  		Item sword = new Weapon("Sword", true, 10);
	  		Item bandage = new Consumable("Bandage", true, "Health");
	  		
	        player = new Player(100, 5);
	        currentRoom = db.findRoomByRoomID(5);
	        
	}
	
	//Activates initial starting data
		public String start() {
			if(hasStarted == true) {
				return "Game has already started";
			}else {
				setup();
				String startingText = currentRoom.getLongDescription();
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
		                int nextRoom_id = db.findConnectionByRoomIDandDirection(currentRoom.getRoomID(), input);
		                Room nextRoom = db.findRoomByRoomID(nextRoom_id);
		                /*if (nextRoom != null && nextRoom.getNeedsKey()) {
		                    String keyName = nextRoom.getKeyName();
		                    Item keyItem = player.getInventory().getItemByName(keyName);
		                    if (keyItem != null) {
		                if (nextRoom_id != 0) {
		                        player.moveTo(nextRoom);
		                        currentRoom = nextRoom;
		                        response = "You use the " + keyName + " to unlock the door.";
		                        currentRoom.setVisited("true");
		                        player.getInventory().removeItem(keyItem);
		                        nextRoom.setNeedsKey("false");
		                    } else {
		                        response = currentRoom.getName() + ": You don't have the required key (" + keyName + ") to enter this room.";
		                    }*/
		                if (nextRoom_id != 0) {
		                    player.moveTo(nextRoom);
		                    currentRoom = player.getCurrentRoom();
		                    response = "You move " + input + ". New Location: " + currentRoom.getName();
		                    //currentRoom.setVisited(true);
		                /*} else if (nextRoom_id != 0 && nextRoom.getVisited()) {
		                    player.moveTo(nextRoom);
		                    currentRoom = player.getCurrentRoom();
		                    response = "You move " + input;*/
		                } else {
		                    response = "You cannot move that way.";
		                }
		                break;
		            case "health":
		                response = "Current Health: " + player.getCurrentHealth();
		                break;
		            case "location":
		                currentRoom = player.getCurrentRoom();
		                response = "Current Location: " + currentRoom.getName();
		                break;
		            case "short description":
		                currentRoom = player.getCurrentRoom();
		                response = currentRoom.getShortDescription();
		                break;
		            case "search":
		                if (currentRoom.roomInventory.getItems().isEmpty()) {
		                    response = "You search the room but find nothing.";
		                } else {
		                    response = "You search the area and find the following items: " + currentRoom.roomInventory.getItemNames() +
		                               ". Do you want to take any of these items? If yes, type 'take' plus the item name.";
		                }
		                break;
		            case "inventory":
		            	if (player.getInventory().getItems().isEmpty()) {
		                    response = "Your inventory is empty :(";
		                } else {
		                    response = "Your inventory:\n" + player.getInventory().getItemNames();
		                }
		                break;
		            default:
		                if (input.startsWith("take ")) {
		                    String itemName = input.substring(5); // Remove "take " from the input to get the item name
		                    Item itemToTake = currentRoom.roomInventory.getItemByName(itemName);
		                    if (itemToTake != null) {
		                        player.getInventory().addItem(itemToTake);
		                        currentRoom.roomInventory.removeItem(itemToTake);
		                        response = "You take the " + itemName + ".";
		                    } else {
		                        response = "There is no " + itemName + " in this room.";
		                    }
		                } else {
		                    response = "Invalid command.";
		                }
		                break;
		        }
		    }
		    return response;
		}

}

