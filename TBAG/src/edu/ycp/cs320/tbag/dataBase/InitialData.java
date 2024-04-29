package edu.ycp.cs320.tbag.dataBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.tbag.dataBase.ReadCSV;
import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.Consumable;
import edu.ycp.cs320.tbag.model.Item;
import edu.ycp.cs320.tbag.model.KeyItem;
import edu.ycp.cs320.tbag.model.NPC;
import edu.ycp.cs320.tbag.model.Player;
import edu.ycp.cs320.tbag.model.Room;
import edu.ycp.cs320.tbag.model.RoomConnection;
import edu.ycp.cs320.tbag.model.Weapon;



public class InitialData {
	
	public static List<Room> getRooms() throws IOException {
		List<Room> roomList = new ArrayList<Room>();
		ReadCSV readRooms = new ReadCSV("Rooms.csv");
		try {
			// auto-generated primary key for rooms table
			Integer roomId = 1;
			while (true) {
				List<String> tuple = readRooms.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Room room = new Room();
				room.setRoomID(roomId++);
				room.setName(i.next());
				room.setLongDescription(i.next());
				room.setShortDescription(i.next());
				room.setVisited(i.next());
				room.setNeedsKey(i.next());
				room.setKeyName(i.next());
				roomList.add(room);
				
			}
			return roomList;
		} finally {
			readRooms.close();
		}
	}
	
	public static List<RoomConnection> getConnections() throws IOException {
		List<RoomConnection> connectionList = new ArrayList<RoomConnection>();
		ReadCSV readConnections = new ReadCSV("RoomConnections.csv");
		try {
			// auto-generated primary key for room connections table
			Integer connectionID = 1;
			while (true) {
				List<String> tuple = readConnections.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				RoomConnection connection = new RoomConnection();
				connection.setConnectionID(connectionID++);
				connection.setRoomID(Integer.parseInt(i.next()));
				connection.setMove(i.next());
				connection.setDestId(Integer.parseInt(i.next()));
				connectionList.add(connection);
				
			}
			return connectionList;
		} finally {
			readConnections.close();
		}
	}
	
	public static List<Actor> getActors() throws IOException {
	    List<Actor> actorList = new ArrayList<Actor>();
	    ReadCSV readActors = new ReadCSV("Actors.csv");
	    try {
	    	int actorID = 1;
	        while (true) {
	            List<String> tuple = readActors.next();
	            if (tuple == null) {
	                break;
	            }

	            Iterator<String> i = tuple.iterator();

	            Actor actor;
	            if (actorID == 1) {
	                actor = new Player();
	            } else {
	                actor = new NPC();
	            }
	            actor.setActorID(actorID);
	            actor.setRoomID(Integer.parseInt(i.next()));
	            actor.setName((i.next()));
	            actor.setLevel(Integer.parseInt(i.next()));
	            actor.setXP(Integer.parseInt(i.next()));
	            actor.setCurrentHealth(Integer.parseInt(i.next()));
	            actor.setMaxHealth(Integer.parseInt(i.next()));
	            actorList.add(actor);
	            actorID++;
	        }

	        return actorList;
	    } finally {
	        readActors.close();
	    }
	}
	
	public static List<Item> getItems() throws IOException {
	    List<Item> itemList = new ArrayList<Item>();
	    ReadCSV readItems = new ReadCSV("Items.csv");
	    try {
	        // auto-generated primary key for rooms table
	        Integer itemId = 1;
	        while (true) {
	            List<String> tuple = readItems.next();
	            if (tuple == null) {
	                break;
	            }
	            Iterator<String> i = tuple.iterator();
	            
	            // Parse item type from the second element of the tuple
	            int itemType = Integer.parseInt(i.next());

	            Item item;
	            switch (itemType) {
	                case 1:
	                    item = new Weapon();
	                    break;
	                case 2:
	                    item = new Consumable();
	                    break;
	                case 3:
	                    item = new KeyItem();
	                    break;
	                default:
	                    throw new IllegalArgumentException("Invalid item type: " + itemType);
	            }

	            // Set item properties
	            item.setItemID(itemId++);
	            item.setType(itemType);
	            item.setName(i.next());
	            item.setDescription(i.next());
	            item.setThrowable(i.next());
	            item.setDamage(Integer.parseInt(i.next()));
	            item.setEffect(i.next());
	            item.setRoomID(Integer.parseInt(i.next()));
	            item.setOwnerID(Integer.parseInt(i.next()));
	            itemList.add(item);
	        }
	        return itemList;
	    } finally {
	        readItems.close();
	    }
	}


}