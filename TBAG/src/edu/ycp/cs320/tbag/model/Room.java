package edu.ycp.cs320.tbag.model;

import java.util.HashMap;

public class Room {
	private String name;
	private String description;
	private HashMap<String, Room> exits;

	public Room(String name, String description) {
		this.name = name;
	    this.description = description;
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

	public String getExitString() {
	    StringBuilder exitString = new StringBuilder("Exits: ");
	    for (String exit : exits.keySet()) {
	        exitString.append(exit).append(", ");
	    }
	    return exitString.substring(0, exitString.length() - 2); // Remove the last comma and space
	}
}
