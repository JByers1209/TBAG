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
	List<RoomConnection> connection = null;
	int nextRoomId;
	
	DerbyDatabase db = new DerbyDatabase();
	
	public void setup() {
	        player = new Player();
	        currentRoom = db.findRoomByRoomID(5);
	}
	
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
	    String input = userInput.toLowerCase().trim();

	    if (input.equals("start")) {
	        response = start();
	    } else if (!hasStarted) {
	        response = "Type start to begin";
	    } else {
	        boolean isDirectionCommand = false;
	        switch (input) {
	            case "north":
	            case "south":
	            case "east":
	            case "west":
	                isDirectionCommand = true;
	                break;
	        }

	        if (isDirectionCommand) {
	            connection = db.findConnectionsByRoomID(currentRoom.getRoomID());
	            boolean foundConnection = false;
	            for (RoomConnection conn : connection) {
	                if (conn.getMove().equalsIgnoreCase(input)) {
	                    foundConnection = true;
	                    nextRoomId = conn.getDestId();
	                    break;
	                }
	            }
	            
	            if (foundConnection && nextRoomId != 0) {
	                Room nextRoom = db.findRoomByRoomID(nextRoomId);
	                player.moveTo(nextRoom);
	                currentRoom = player.getCurrentRoom();
	                response = currentRoom.getVisited().equals("false") ?
	                           "You move " + input + ". New Location: " + currentRoom.getName() + ". " + currentRoom.getLongDescription() :
	                           "You move " + input + " to " + currentRoom.getName() + ". " + currentRoom.getShortDescription();
	                currentRoom.setVisited("true");
	                db.updateRoomByRoom(currentRoom);
	            } else {
	                response = "You cannot move that way.";
	            }
	        } else {
	            // Handle other commands like health, inventory, etc.
	            switch (input) {
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
	                    if (items != null) {
	                        response = "You search the area and find the following items: " + name + "." ;
	                    } else {
	                        response = "You search the room but find nothing.";
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
	        }
	    }
	    return response;
	}
}


