package edu.ycp.cs320.tbag.model;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.tbag.dataBase.DerbyDatabase;

public class Game {
	
	String response;
	boolean hasStarted = false;
	boolean canMove = true;
	Room currentRoom;
	Player player;
	List<Item> items = null;
	
	DerbyDatabase db = new DerbyDatabase();
	
	public void setup() {
	        player = new Player();
	        currentRoom = db.findRoomByRoomID(5);
	}
	
	//Activates initial starting data
		public String start() {
			if(hasStarted == true) {
				return "Game has already started";
			}else {
				setup();
				String startingText = currentRoom.getLongDescription();
				currentRoom.setVisited("true");
				db.updateRoomByRoom(currentRoom);
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
		                if (nextRoom_id != 0 && nextRoom.getNeedsKey().equals("true") && !nextRoom.getKeyName().equals("none")) {
		                   String keyName = nextRoom.getKeyName();
		                   items = db.findItemsByOwnerID(1);
		                   for(int i=0; i<items.size(); i++) {
		                	   if (items.get(i).getName().equals(keyName)) {
			                        player.moveTo(nextRoom);
			                        currentRoom = player.getCurrentRoom();
			                        response = "You use the " + keyName + " to unlock the door. " + currentRoom.getLongDescription();
			                        currentRoom.setVisited("true");
			                        db.updateRoomByRoom(currentRoom);
			                        db.updateItem(items.get(i).getItemID(), currentRoom.getRoomID(), i);
			                        nextRoom.setNeedsKey("false");
			                        db.updateRoomByRoom(nextRoom);
			                    } else {
			                        response = "The following location is locked.";
			                    }
		                   }
		                }else if (nextRoom_id != 0) {
		                	player.moveTo(nextRoom);
		                    currentRoom = player.getCurrentRoom();
		                    
		                	if(nextRoom.getVisited().equals("false")) {
		                    	response = "You move " + input + ". New Location: " + currentRoom.getName() + ". " + currentRoom.getLongDescription();
		                    	currentRoom.setVisited("true");
		                    	db.updateRoomByRoom(currentRoom);
		                	} else if (nextRoom.getVisited().equals("true")) {	
		                		response = "You move " + input + " to " + currentRoom.getName() + ". " + currentRoom.getShortDescription();
		                	}
		                } else {
		                    response = "You cannot move that way.";
		                }
		                break;
		            case "health":
		                response = "Health: " + player.getCurrentHealth();
		                break;
		            case "location":
		                currentRoom = player.getCurrentRoom();
		                response = "Location: " + currentRoom.getName();
		                break;
		            case "short description":
		                currentRoom = player.getCurrentRoom();
		                response = currentRoom.getShortDescription();
		                break;
		            case "long description":
		                currentRoom = player.getCurrentRoom();
		                response = currentRoom.getLongDescription();
		                break;
		            case "search":
		            	items = db.findItemsByRoomID(currentRoom.getRoomID());
		            	String name = items.get(0).getName();
		                if (items.size() == 0) {
		                    response = "You search the room but find nothing.";
		                } else {
		                    response = "You search the area and find the following items: " + name + "." ;
		                }
		                break;
		            case "inventory":
		            	items = db.findItemsByOwnerID(1);
		            	if (items.size() == 0) {
		                    response = "Your inventory is empty :(";
		                } else {
		                    response = "Your inventory:\n";
		                    for(int i = 0; i< items.size(); i++) {
		                    	response += items.get(i).getName();
		                    	if(i < items.size()-1) {
		                    		response += ", ";
		                    	}
		                    }
		                    response += ".";
		                }
		                break;
		            default:
		                if (input.startsWith("take ")) {
		                    String itemName = input.substring(5); // Remove "take " from the input to get the item name
		                    List<Item> itemToTake = db.findItemsByNameAndRoomID(itemName, currentRoom.getRoomID());
		                    if (itemToTake.size() != 0) {
		                    	db.updateItem(itemToTake.get(0).getItemID(), currentRoom.getRoomID(), 1);
		                        response = "You take the " + itemName + ".";
		                    } else {
		                        response = "There is no " + itemName + " in this room.";
		                    }
		                } else {
		                    response = "Invalid command.";
		                }
		                break;
		        }
		        items = null;
		    }
		    return response;
		}

}

