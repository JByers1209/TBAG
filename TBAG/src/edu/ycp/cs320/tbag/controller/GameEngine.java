package edu.ycp.cs320.tbag.controller;

import java.util.List;

import edu.ycp.cs320.tbag.dataBase.DerbyDatabase;
import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.Item;
import edu.ycp.cs320.tbag.model.Room;
import edu.ycp.cs320.tbag.model.RoomConnection;

public class GameEngine {

    String response;
    boolean hasStarted = false;
    boolean canMove = true;
    boolean inFight = false;
    boolean decision = false;
    Room currentRoom;
    Room lastroom;
    Actor player;
    String gameLog = "Welcome to Spooky York! Type 'start' to begin.";
    List<Item> items = null;
    List<RoomConnection> connection = null;
    int nextRoomId;

    DerbyDatabase db = new DerbyDatabase();

    public void setup() {
        player = db.findActorByID(1);
        currentRoom = db.findRoomByRoomID(1);
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

        if (inFight) {
            // Player is in a fight, so only process fight-related commands
            response = processFightCommands(input);
        } else if (decision) {
        	response = processDecision(input);
        } else {
            if (input.equals("start")) {
                response = start();
            } else if (!hasStarted) {
                response = "Type start to begin";
            } else {
                response = processCommand(input);
            }
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
        	lastroom = currentRoom;
            player.moveTo(nextRoom);
            currentRoom = player.getCurrentRoom();
            String moveResult;
            if (nextRoom.getVisited().equals("false")) {
                moveResult = "You move " + direction + ". New Location: " + currentRoom.getName() + ". " + currentRoom.getLongDescription();
            } else {
                moveResult = "You move " + direction + " to " + currentRoom.getName() + ". " + currentRoom.getShortDescription();
            }

            // Check if there is an NPC in the next room after the player has been moved
            Actor npcInNextRoom = db.findActorByRoomID(currentRoom.getRoomID());
            if (npcInNextRoom != null) {
                moveResult += "\n" + startFight(npcInNextRoom);
            }
            return moveResult;
        }
    }


    private String startFight(Actor npc) {
    	decision = true;
        return "A " + npc.getName() + "stands in front of you! You can either fight or run.";
    }
    
    private String processDecision(String input) {
        if (input.equals("fight")) {
            inFight = true;
            decision = false; // Reset decision flag
            return "You engage in combat!";
        } else if (input.equals("run")) {
            // Move the player back to the previous room
            player.moveTo(db.findRoomByRoomID(lastroom.getRoomID()));
            currentRoom = player.getCurrentRoom();
            decision = false; // Reset decision flag
            return "You run back to the location you came from. Current location: " + currentRoom.getName() + ".";
        } else {
            // Invalid command
            return "Invalid decision. You can either 'fight' or 'run'.";
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
    
    private String processFightCommands(String input) {
        if (input.startsWith("use ")) {
            String itemName = input.substring(4);
            return useItem(itemName);
        } else if (input.startsWith("throw ")) {
            String itemName = input.substring(6);
            return throwItem(itemName);
        } else {
            switch (input) {
                case "run":
                    return runAway();
                case "kick":
                    return kick();
                case "punch":
                    return punch();
                default:
                    return "Invalid fight command.";
            }
        }
    }

    private String useItem(String itemName) {
        // Implement logic to use the specified item in combat
        return "You use " + itemName + "!";
    }

    private String throwItem(String itemName) {
        // Implement logic to throw the specified item in combat
        return "You throw " + itemName + "!";
    }

    private String runAway() {
        // Implement logic to allow the player to run away from the fight
        return "You try to run away!";
    }

    private String kick() {
        // Implement logic for the player to kick during the fight
        return "You kick the opponent!";
    }

    private String punch() {
        // Implement logic for the player to punch during the fight
        return "You punch the opponent!";
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
}

