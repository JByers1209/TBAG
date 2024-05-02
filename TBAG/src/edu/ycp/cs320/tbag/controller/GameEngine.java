package edu.ycp.cs320.tbag.controller;

import java.util.List;

import edu.ycp.cs320.tbag.dataBase.DerbyDatabase;
import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.Item;
import edu.ycp.cs320.tbag.model.Room;
import edu.ycp.cs320.tbag.model.RoomConnection;
import edu.ycp.cs320.tbag.model.SaveData;

public class GameEngine {

    String response;
    boolean hasStarted = false;
    boolean canMove = true;
    Room currentRoom;
    Actor player;
    String gameLog = "Welcome to Spooky York! Type 'start' to begin.";
    List<Item> items = null;
    List<RoomConnection> connection = null;
    int nextRoomId;

    DerbyDatabase db = new DerbyDatabase();

    public void setup() {
        player = db.findActorByID(1);
        currentRoom = db.findRoomByRoomID(5);
    }

    public String start() {
        if (hasStarted) {
            return "Game has already started";
        } else {
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
            response = processCommand(input);
        }

        gameLog += "\n >" + userInput;
        gameLog += "\n" + response;
        return gameLog;
    }

    private String processCommand(String input) {
        if (isDirectionCommand(input)) {
            return processDirectionCommand(input);
        } else {
            return processOtherCommands(input);
        }
    }

    private boolean isDirectionCommand(String input) {
        return input.equals("north") || input.equals("south") || input.equals("east") || input.equals("west");
    }

    private String processDirectionCommand(String direction) {
        connection = db.findConnectionsByRoomID(currentRoom.getRoomID());
        boolean foundConnection = false;
        for (RoomConnection conn : connection) {
            if (conn.getMove().equalsIgnoreCase(direction)) {
                foundConnection = true;
                nextRoomId = conn.getDestId();
                break;
            }
        }

        if (foundConnection && nextRoomId != 0) {
            return moveDirection(nextRoomId, direction);
        } else {
            return "You cannot move that way.";
        }
    }

    private String moveDirection(int nextRoomId, String direction) {
        Room nextRoom = db.findRoomByRoomID(nextRoomId);
        if (nextRoom.getNeedsKey().equals("true") && !nextRoom.getKeyName().equals("none")) {
            return unlockDoor(nextRoom);
        } else {
            player.moveTo(nextRoom);
            currentRoom = player.getCurrentRoom();
            if (nextRoom.getVisited().equals("false")) {
                return "You move " + direction + ". New Location: " + currentRoom.getName() + ". " + currentRoom.getLongDescription();
            } else {
                return "You move " + direction + " to " + currentRoom.getName() + ". " + currentRoom.getShortDescription();
            }
        }
    }

    private String unlockDoor(Room nextRoom) {
        String keyName = nextRoom.getKeyName();
        items = db.findItemsByOwnerID(1);
        for (Item item : items) {
            if (item.getName().equals(keyName)) {
                player.moveTo(nextRoom);
                currentRoom = player.getCurrentRoom();
                nextRoom.setNeedsKey("false");
                db.updateRoomByRoom(nextRoom);
                db.updateItem(item.getItemID(), currentRoom.getRoomID(), 1);
                return "You use the " + keyName + " to unlock the door. " + currentRoom.getLongDescription();
            }
        }
        return "The following location is locked.";
    }

    private String processOtherCommands(String input) {
        switch (input) {
            case "health":
                return "Health: " + player.getCurrentHealth();
            case "location":
                currentRoom = player.getCurrentRoom();
                return "Location: " + currentRoom.getName();
            case "short description":
                currentRoom = player.getCurrentRoom();
                return currentRoom.getShortDescription();
            case "long description":
                currentRoom = player.getCurrentRoom();
                return currentRoom.getLongDescription();
            case "search":
                return searchRoom();
            case "inventory":
                return displayInventory();
            default:
                if (input.startsWith("take ")) {
                    return takeItem(input.substring(5));
                } else {
                    return "Invalid command.";
                }
        }
    }

    private String searchRoom() {
        items = db.findItemsByRoomID(currentRoom.getRoomID());
        if (items != null && !items.isEmpty()) {
            return "You search the area and find the following items: " + items.get(0).getName() + ".";
        } else {
            return "You search the room but find nothing.";
        }
    }

    private String displayInventory() {
        items = db.findItemsByOwnerID(1);
        if (items.isEmpty()) {
            return "Your inventory is empty :(";
        } else {
            StringBuilder inventory = new StringBuilder("Your inventory:\n");
            for (int i = 0; i < items.size(); i++) {
                inventory.append(items.get(i).getName());
                if (i < items.size() - 1) {
                    inventory.append(", ");
                }
            }
            inventory.append(".");
            return inventory.toString();
        }
    }

    private String takeItem(String itemName) {
        List<Item> itemToTake = db.findItemsByNameAndRoomID(itemName, currentRoom.getRoomID());
        if (!itemToTake.isEmpty()) {
            db.updateItem(itemToTake.get(0).getItemID(), currentRoom.getRoomID(), 1);
            return "You take the " + itemName + ".";
        } else {
            return "There is no " + itemName + " in this room.";
        }
    }
    
    
    public void saveGame(int saveID) {
    	
    	int actorCount = db.getActorCount() , roomCount = db.getRoomCount(), itemCount = db.getItemCount();
    	
    	//save actors
    	for(int i = 1; i <= actorCount; i++) {
    		Actor actor = db.findActorByID(i);
    		db.saveActor(saveID, actor);
    	}
    	
    	//save rooms
    	for(int i = 1; i <= roomCount; i++ ) {
    		Room room = db.findRoomByRoomID(i);
    		db.saveRoom(saveID, room);
    	}
    	
    	//save items
    	for(int i = 1; i <= itemCount; i++ ) {
    		Item item = db.findItemByID(i);
    		db.saveItem(saveID, item);
    	}  
    	
    	
    	//save log
    	db.saveLog(saveID, gameLog);
    }
    
    public void loadGame(int saveID) {
    	
    	List<SaveData> saveDataList = db.getSaveData(saveID);
    	
    	for(SaveData saveData: saveDataList) {
    		if(saveData.getSaveType().equals("actor")) {
    			Actor actorToUpdate = db.findActorByID(saveData.getIdSlot1());
    			actorToUpdate.update(saveData);
    			db.updateActor(actorToUpdate);
    			
    		}else if(saveData.getSaveType().equals("room")) {
    			Room roomToUpdate = db.findRoomByRoomID(saveData.getIdSlot1());
    			roomToUpdate.update(saveData);
    			db.updateRoomByRoom(roomToUpdate);
    			
    		}else if(saveData.getSaveType().equals("item")) {
    			Item itemToUpdate = db.findItemByID(saveData.getIdSlot1());
    			itemToUpdate.update(saveData);
    			db.updateItem(itemToUpdate.getItemID(), itemToUpdate.getRoomID(), itemToUpdate.getOwnerID());
    		}else {
    			gameLog = saveData.getLog();
    		}
    	}
    	
    	
    	
    	
    	
    	
    }//end loadGame
    
    
    
    
    
}//end gameEngine

