package edu.ycp.cs320.tbag.controller;

import java.util.List;
import java.util.Random;

import edu.ycp.cs320.tbag.dataBase.DerbyDatabase;
import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.Item;
import edu.ycp.cs320.tbag.model.Room;
import edu.ycp.cs320.tbag.model.RoomConnection;

public class GameEngine {
	
	DerbyDatabase db = new DerbyDatabase();
	
	int userId = 0;
	String username = null;
    String response;
    boolean hasStarted = false;
    boolean canMove = true;
    boolean inFight = false;
    boolean decision = false;
    boolean returnInput;
    
    Room currentRoom = db.findRoomByRoomID(1);
    Room lastroom;
    Actor player = db.findActorByID(1);
    Actor enemy;
    String actionResult;
    String gameLog = currentRoom.getLongDescription();
    List<Item> items = null;
    List<RoomConnection> connection = null;
    int nextRoomId;
    
	public void reset() {
		userId = 0;
		username = null;
		response = null;
	    hasStarted = false;
	    canMove = true;
	    inFight = false;
	    decision = false;
	    
	    currentRoom = db.findRoomByRoomID(1);
	    player = db.findActorByID(1);
	    gameLog = currentRoom.getLongDescription();
	    items = null;
	    connection = null;
	}

    public String processUserInput(String userInput) {
        String input = userInput.toLowerCase().trim();

        if (input.equals("prestart") && !hasStarted) {
            // Handle the "prestart" command if the game hasn't started yet
            db.dropTables();
            db.reCreateTables();
            db.reLoadInitialData();
            reset();
            response = gameLog;
            returnInput = false;
        } else if (input.equals("prestart")) {
            // Handle the "prestart" command if the game has already started
            db.dropTables();
            db.reCreateTables();
            db.reLoadInitialData();
            reset();
            returnInput = false;
            response = gameLog;
        } else if (inFight) {
            // Player is in a fight, so only process fight-related commands
            response = processFightCommands(input);
        } else if (decision) {
            response = processDecision(input);
        } else {
            // Process other commands
            response = processCommand(input);
            returnInput = true;
        }


        if (!hasStarted) {
            hasStarted = true;
        }

        // Append user input and response to gameLog if returnInput is true
        if (returnInput && !input.equals("prestart")) {
            db.updateActor(1, player);
            gameLog += "\n >" + userInput;
            gameLog += "\n" + response;
        }

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
        return input.equals("north") || input.equals("south") || input.equals("east") || input.equals("west") || 
        		input.equals("climb") || input.equals("crawl") || input.equals("up") || input.equals("down") || input.equals("left")
        || input.equals("right");
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
            player.setRoomID(currentRoom.getRoomID());
            db.updateActor(1, player);
            String moveResult;
            if (nextRoom.getVisited().equals("false")) {
            	nextRoom.setVisited("true");
            	db.updateRoomByRoom(nextRoom);
                moveResult = "You move " + direction + ". " + currentRoom.getLongDescription();
            } else {
                moveResult = "You move " + direction + " to " + currentRoom.getName() + ". " + currentRoom.getShortDescription();
            }

            // Check if there is an NPC in the next room after the player has been moved
            Actor npcInNextRoom = db.findActorByRoomID(currentRoom.getRoomID());
            if (npcInNextRoom != null && npcInNextRoom.getActorID() != 1) {
            	enemy = npcInNextRoom;
                moveResult += "\n" + startFight(enemy);
            }
            return moveResult;
        }
    }


    private String startFight(Actor enemy) {
    	decision = true;
        return "The " + enemy.getName() + " stands in front of you! You can either fight or run.";
    }
    
    //Function used to process the fight decision
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
            return "You run back to the location you came from.";
        } else {
            // Invalid command
            return "Invalid decision. You can either 'fight' or 'run'.";
        }
    }

//Function used for unlocking rooms
    private String unlockDoor(Room nextRoom) {
        String keyName = nextRoom.getKeyName();
        items = db.findItemsByOwnerID(1);
        for (Item item : items) {
            if (item.getName().equals(keyName)) {
                player.moveTo(nextRoom);
                currentRoom = player.getCurrentRoom();
                nextRoom.setNeedsKey("false");
                db.updateRoomByRoom(nextRoom);
                db.updateItem(item.getItemID(), 0, 0);
                return "You use the " + keyName + " to unlock the door. " + currentRoom.getLongDescription();
            }
        }
        return "The following location is locked.";
    }

    //Function used to process non fight commands
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
                } else if (input.startsWith("save ")) {
                    return "saved " + input.substring(5);
                } else if (input.startsWith("load ")) {
                    return "Loaded " + input.substring(5);
                } else if (input.startsWith("use ")) {
                    String itemName = input.substring(4);
                    actionResult = useItem(itemName);
                    return checkFightStatus(actionResult);
                } else if (input.startsWith("throw ")) {
                    String itemName = input.substring(6);
                    actionResult = throwItem(itemName);
                    return checkFightStatus(actionResult);
                } else {
                    return "Invalid command.";
                }
        }
    }
    
    //Function used to process fighting commands
    private String processFightCommands(String input) {
        if (input.startsWith("use ")) {
            String itemName = input.substring(4);
            actionResult = useItem(itemName);
            return checkFightStatus(actionResult);
        } else if (input.startsWith("throw ")) {
            String itemName = input.substring(6);
            actionResult = throwItem(itemName);
            return checkFightStatus(actionResult);
        } else {
            switch (input) {
                case "kick":
                	actionResult = kick();
                    return checkFightStatus(actionResult);
                case "punch":
                	actionResult = punch();
                    return checkFightStatus(actionResult);
                default:
                    return "Invalid fight command.";
            }
        }
    }
    
    //Function used to check the health of both fighters and end the fight
    private String checkFightStatus(String actionResult) {
        if (enemy.getCurrentHealth() <= 0) {
            // NPC's health is 0 or less, handle victory
        	inFight = false;
        	List<Item> item = db.findItemsByOwnerID(enemy.getActorID());
        	
        	for (Item items : item) {
                // Update each item's information
                items.setDescription("New description");
                // Update other fields as needed
                db.updateItem(items.getItemID(), enemy.getRoomID(), 0);
            }
        	
        	enemy.setRoomID(0);
        	db.updateActor(enemy.getActorID(), enemy);
            return "You defeated the " + enemy.getName() + "!";
        } else if (player.getCurrentHealth() <= 0) {
            // Player's health is 0 or less, handle defeat
        	inFight = false;
            return "You were defeated by " + enemy.getName() + ".";
        } else {
            // Neither player nor NPC's health is 0 or less, return action result
            return actionResult;
        }
    }

    private String useItem(String itemName) {
        // Get the items owned by the player
        items = db.findItemsByOwnerID(1);
        
        // Iterate through the player's items
        for (Item item : items) {
            // Check if the item's name matches the provided itemName
            if (item.getName().equals(itemName)) {
                // Check the type of the item
                String itemType = item.getEffect();
                
                // Perform actions based on the item type
                switch (itemType) {
                    case "damage":
                    	if(!inFight) {
                    		return "You cannot use that item now";
                    	} else {
                    		Random random = new Random();
                            int randomNumber = random.nextInt(100); // Generate a random number between 0 and 99

                            // Determine the outcome based on the random number
                            if (randomNumber < 66) {
                                // 66% chance of outcome 1
                                return processDamage1(item);
                            } else if (randomNumber > 66) {
                                // 33% chance of outcome 2
                                return processDamage2(item);
                            }
                    	}
                    case "health":
                    	player.setCurrentHealth(player.getCurrentHealth() + item.getDamage());
                        return " You use the " + item.getName();
                    case "key":
                        return " You can't use a key here.";
                    default:
                        // Handle unknown item types
                        return "Unknown item!";
                }
            }
        }
        
        // If the itemName does not match any of the player's items
        return "You do not have an item by that name.";
    }

// Functions used to process the damage the user makes to the enemy
    private String processDamage1(Item item) {
        // Process outcome 1
        enemy.setCurrentHealth(enemy.getCurrentHealth() - item.getDamage());
        db.updateActor(enemy.getActorID(), enemy);
        String outcome = "You use the " + item.getName() + " against the " + enemy.getName() + ".";
        return enemyFightsBack(outcome);
    }

    private String processDamage2(Item item) {
        // Process outcome 2
    	String outcome = "You attempt to use the " + item.getName() + " against the " + enemy.getName() + " but miss.";
    	return enemyFightsBack(outcome);
    }

    // Function used to determine if the user takes damage from the enemy
    private String enemyFightsBack(String outcome) {
    	Random random = new Random();
        int randomNumber = random.nextInt(100); // Generate a random number between 0 and 99
        String result = null;
        // Determine the outcome based on the random number
        if (randomNumber < 33) {
        	player.setCurrentHealth(player.getCurrentHealth() - 10);
        	result = "\n The " + enemy.getName() + " fights back and hits you. ";
        } else if (randomNumber < 66) {
        	player.setCurrentHealth(player.getCurrentHealth() - 15);
        	result = "\n The " + enemy.getName() + " fights back and hits you. ";
        } else {
        	result = "\n The " + enemy.getName() + " fights back and misses you. ";
        }
    	result = outcome + result + 
    			"\n " + enemy.getName() + " health: " + enemy.getCurrentHealth();
    	return result;
    }
    
    private String throwItem(String itemName) {
    	Random random = new Random();
    	String result = null;
        int randomNumber = random.nextInt(100); // Generate a random number between 0 and 99
        List<Item> item = db.findItemsByName(itemName);
        if(!inFight) {
        	db.updateItem(item.get(0).getItemID(), player.getRoomID(), 0);
            result = "You throw the " + itemName + ".";
        }else {
        	if(item.get(0).getThrowable().equals("True")) {
            	// Determine the outcome based on the random number
                if (randomNumber < 66) {
                    
                	db.updateItem(item.get(0).getItemID(), player.getRoomID(), 0);
                    result = "You throw the " + itemName + " and hit the enemy!";
                    return enemyFightsBack(result);
                } else if( randomNumber < 90){
                    
                	db.updateItem(item.get(0).getItemID(), player.getRoomID(), 0);
                    result = "You throw the " + itemName + " and miss the enemy.";
                    return enemyFightsBack(result);
                }else {
                	db.updateItem(item.get(0).getItemID(), 0, enemy.getActorID());
                	result = "You throw the " + itemName + " and the " + enemy.getName() + " catches it!";
                	return enemyFightsBack(result);
                }
        	}
        }
        return "You cannot throw that item!";
      
    }

    private String kick() {
    	Random random = new Random();
    	String result = null;
        int randomNumber = random.nextInt(100); // Generate a random number between 0 and 99
            if (randomNumber < 66) {
                enemy.setCurrentHealth(enemy.getCurrentHealth() - 5);
                result = "You kick the " + enemy.getName() + ".";
                return enemyFightsBack(result);
            } else if( randomNumber < 85){
            	enemy.setCurrentHealth(enemy.getCurrentHealth() - 8);
                result = "You kick the " + enemy.getName() + " really hard. ";
                return enemyFightsBack(result);
            }else {
            	result = "You attempt to kick the " + enemy.getName() + " but miss.";
            	return enemyFightsBack(result);
            }
    }

    private String punch() {
    	Random random = new Random();
    	String result = null;
        int randomNumber = random.nextInt(100); // Generate a random number between 0 and 99
            if (randomNumber < 66) {
                enemy.setCurrentHealth(enemy.getCurrentHealth() - 8);
                result = "You punch the " + enemy.getName() + ".";
                return enemyFightsBack(result);
            } else if( randomNumber < 85){
            	enemy.setCurrentHealth(enemy.getCurrentHealth() - 12);
                result = "You punch the " + enemy.getName() + " really hard. ";
                return enemyFightsBack(result);
            }else {
            	result = "You swing a punch at the " + enemy.getName() + " but miss.";
            	return enemyFightsBack(result);
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

	public void setUserId(int userId) {
		this.userId=(userId);
	}
	
	public void setUsername(String username) {
		this.username=(username);
	}
	
	public int getPlayerHealth() {
		return player.getCurrentHealth();
	}
	
	public String getPlayerMaxHealth() {
		Actor playerInfo = db.findActorByID(1);
		return "$" + playerInfo.getMaxHealth();
	}
}

