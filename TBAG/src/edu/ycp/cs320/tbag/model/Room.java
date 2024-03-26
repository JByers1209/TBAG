package edu.ycp.cs320.tbag.model;

import java.util.HashMap;

public class Room {
	private String name;
	private String description;
	private HashMap<String, Room> exits;
	private boolean hasVisited;
	private boolean needsKey;
	private String keyName;

	Inventory roomInventory = new Inventory();
	
	public Room(String name, String description, boolean needsKey) {
		this.name = name;
	    this.description = description;
	    this.hasVisited = false;
	    this.setNeedsKey(needsKey);
	    exits = new HashMap<>();
	}

	public String getName() {
	    return name;
	}

	public String getDescription() {
	    return description;
	}

	public void setDescription(String description) {
	    this.description = description;
	}

	public void setExit(String direction, Room neighbor) {
	    exits.put(direction, neighbor);
	}

	public Room getExit(String direction) {
	    return exits.get(direction);
	}
	
	public void setVisited(boolean hasVisited) {
		this.hasVisited = hasVisited;
	}
	
	public boolean getVisited() {
		return hasVisited;
	}

	public String getExitString() {
	    StringBuilder exitString = new StringBuilder("Exits: ");
	    for (String exit : exits.keySet()) {
	        exitString.append(exit).append(", ");
	    }
	    return exitString.substring(0, exitString.length() - 2); // Remove the last comma and space
	}

	public boolean getNeedsKey() {
		return needsKey;
	}

	public void setNeedsKey(boolean needsKey) {
		this.needsKey = needsKey;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
}
