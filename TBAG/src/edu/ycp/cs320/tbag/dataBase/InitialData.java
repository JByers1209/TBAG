package edu.ycp.cs320.tbag.dataBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


import edu.ycp.cs320.tbag.dataBase.ReadCSV;
import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.NPC;
import edu.ycp.cs320.tbag.model.Player;
import edu.ycp.cs320.tbag.model.Room;
import edu.ycp.cs320.tbag.model.RoomConnection;
import edu.ycp.cs320.tbag.model.User;



public class InitialData {

	public static List<Actor> getActors() throws IOException {
		List<Actor> actorList = new ArrayList<Actor>();
		ReadCSV readActors = new ReadCSV("Actors.csv");
		try {
			while (true) {
				List<String> tuple = readActors.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				int actorID = Integer.parseInt(i.next());
				if(actorID == 1) {
					Player actor = new Player();
					actor.setActorID(actorID++);
					actor.setRoomID(Integer.parseInt(i.next()));
					actor.setLevel(Integer.parseInt(i.next()));
					actor.setXP(Integer.parseInt(i.next()));
					actor.setCurrentHealth(Integer.parseInt(i.next()));
					actorList.add(actor);
					
				}else {
					NPC actor = new NPC();
					actor.setActorID(actorID++);
					actor.setRoomID(Integer.parseInt(i.next()));
					actor.setLevel(Integer.parseInt(i.next()));
					actor.setXP(Integer.parseInt(i.next()));
					actor.setCurrentHealth(Integer.parseInt(i.next()));
					actorList.add(actor);
				}
				
			}
			return actorList;
		} finally {
			readActors.close();
		}
	}
	
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
			Integer roomId = 1;
			while (true) {
				List<String> tuple = readConnections.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				RoomConnection connection = new RoomConnection();
				connection.setRoomID(roomId++);
				connection.setMove1(i.next());
				connection.setDest1(Integer.parseInt(i.next()));
				connection.setMove2(i.next());
				connection.setDest2(Integer.parseInt(i.next()));
				connection.setMove3(i.next());
				connection.setDest3(Integer.parseInt(i.next()));
				connection.setMove4(i.next());
				connection.setDest4(Integer.parseInt(i.next()));
				connectionList.add(connection);
				
			}
			return connectionList;
		} finally {
			readConnections.close();
		}
	}
	
	public static List<User> getUser() throws IOException {
		List<User> userList = new ArrayList<User>();
		ReadCSV readUser = new ReadCSV("users.csv");
		try {
		// auto-generated primary key for room connections table
			Integer userId = 1;
			while (true) {
				List<String> tuple = readUser.next();
				if (tuple == null) {
					break;
				}
			Iterator<String> i = tuple.iterator();
			User user = new User();
			user.setUserID(userId++);
			user.setUsername(i.next(), null);
			user.setPassword(i.next());
			userList.add(user);
			
		}
		return userList;
	} finally {
		readUser.close();
	}
}

}


