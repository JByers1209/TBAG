npackage edu.ycp.cs320.tbag.model;

import java.util.ArrayList;

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
	    		" You are all alone. Where will you go next?", false);
	    rooms.add(startRoom);
	    startRoom.setVisited(true);
	    
	    Room campusRoom = new Room("York College Campus", "York college campus. Screaming is heard in the distance.", false);
	    rooms.add(campusRoom);
	    
	    Room neRoom = new Room("Yorktowne Hotel", "Towering hotel in downtown York.", false);
	    rooms.add(neRoom);
	    
	    Room eastRoom = new Room("Reymeyers Hollow", "A winding road leads you into the forest.", false);
	    rooms.add(eastRoom);
	    
	    Room seRoom = new Room("Roosevelt Tavern", "A fancy lounge. The smell of crab soup is in the air.", false);
	    rooms.add(seRoom);
	    
	    Room southRoom = new Room("Acomac Inn", "You hear the roaring Susquehanna behind you as you stare at the brick exterior.", false);
	    rooms.add(southRoom);
	    
	    Room swRoom = new Room("Elmwood Mansion", "In front of you stands a large pristine mansion", false);
	    rooms.add(swRoom);
	    
	    Room westRoom = new Room("Seven Gates of Hell", "The horrors that can be found if you follow Toad Rd.", false);
	    rooms.add(westRoom);
	    
	    Room nwRoom = new Room("Josh's House", "Josh's house. A dog is heard barking inside.", true);
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
	  	
	  	//Create items for rooms/player
	  		Item blue_key = new KeyItem("Blue Key", false);
	  		Item sword = new Weapon("Sword", true, 10);
	  		Item bandage = new Consumable("Bandage", true, "Health");
	  
	  	//Add items to rooms
	  		nwRoom.setKeyName("Blue Key");
	  		southRoom.roomInventory.addItem(blue_key);
	  		nwRoom.roomInventory.addItem(sword);
	  		eastRoom.roomInventory.addItem(bandage);
	  		
	        player = new Player(startRoom);
	        player.setCurrentHealth(100);
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
		                if (nextRoom != null && nextRoom.getNeedsKey()) {
		                    String keyName = nextRoom.getKeyName();
		                    Item keyItem = player.inventory.getItemByName(keyName);
		                    if (keyItem != null) {
		                        player.moveTo(nextRoom);
		                        currentRoom = player.getCurrentRoom();
		                        response = "You use the " + keyName + " to unlock the door.";
		                        currentRoom.setVisited(true);
		                        player.inventory.removeItem(keyItem);
		                        nextRoom.setNeedsKey(false);
		                    } else {
		                        response = currentRoom.getName() + ": You don't have the required key (" + keyName + ") to enter this room.";
		                    }
		                } else if (nextRoom != null && !nextRoom.getVisited()) {
		                    player.moveTo(nextRoom);
		                    currentRoom = player.getCurrentRoom();
		                    response = "You move " + input + ". New Location: " + currentRoom.getName();
		                    currentRoom.setVisited(true);
		                } else if (nextRoom != null && nextRoom.getVisited()) {
		                    player.moveTo(nextRoom);
		                    currentRoom = player.getCurrentRoom();
		                    response = "You move " + input;
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
		            case "description":
		                currentRoom = player.getCurrentRoom();
		                response = currentRoom.getDescription();
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
		            	if (player.inventory.getItems().isEmpty()) {
		                    response = "Your inventory is empty :(";
		                } else {
		                    response = "Your inventory:\n" + player.inventory.getItemNames();
		                }
		                break;
		            default:
		                if (input.startsWith("take ")) {
		                    String itemName = input.substring(5); // Remove "take " from the input to get the item name
		                    Item itemToTake = currentRoom.roomInventory.getItemByName(itemName);
		                    if (itemToTake != null) {
		                        player.inventory.addItem(itemToTake);
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

