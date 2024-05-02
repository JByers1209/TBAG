package edu.ycp.cs320.tbag.model;

import java.util.HashMap;

public class Room {
	private String name;
	private String shortDescription;
	private String longDescription;
	private HashMap<String, Room> exits;
	private String hasVisited;
	private String needsKey;
	private String keyName;
	private int roomId;

	Inventory roomInventory = new Inventory();
	
	public Room() {
		
	}

	public Room(String name, String shortDescription, String longDescription, String hasVisited, String needsKey, String keyName ) {
		this.name = name;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.hasVisited = hasVisited;
		this.needsKey = needsKey;
		this.keyName = keyName;
	}
	
	public Room(String name, String longDescription, String hasVisited) {
		this.name = name;
		this.longDescription = longDescription;
		this.hasVisited = hasVisited;
	}
	
	public void update(SaveData saveData) {
		this.roomId = saveData.getIdSlot1();
		this.hasVisited = saveData.getHasVisited();
		this.needsKey = saveData.getNeedsKey();
	}
	
	public void setRoomID(int roomId) {
		this.roomId = roomId;
	}
	
	public int getRoomID() {
		return roomId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
	    return name;
	}

	public String getShortDescription() {
	    return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
	    this.shortDescription = shortDescription;
	}
	
	public String getLongDescription() {
	    return longDescription;
	}

	public void setLongDescription(String longDescription) {
	    this.longDescription = longDescription;
	}

	public void setExit(String direction, Room neighbor) {
	    exits.put(direction, neighbor);
	}

	public Room getExit(String direction) {
	    return exits.get(direction);
	}
	
	public void setVisited(String hasVisited) {
		this.hasVisited = hasVisited;
	}
	
	public String getVisited() {
		return hasVisited;
	}

	public String getExitString() {
	    StringBuilder exitString = new StringBuilder("Exits: ");
	    for (String exit : exits.keySet()) {
	        exitString.append(exit).append(", ");
	    }
	    return exitString.substring(0, exitString.length() - 2); // Remove the last comma and space
	}

	public String getNeedsKey() {
		return needsKey;
	}

	public void setNeedsKey(String needsKey) {
		this.needsKey = needsKey;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
}
