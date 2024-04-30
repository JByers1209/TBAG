package edu.ycp.cs320.tbag.dataBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.Item;
import edu.ycp.cs320.tbag.model.Room;
import edu.ycp.cs320.tbag.model.RoomConnection;
import edu.ycp.cs320.tbag.model.User;

public class FakeDatabase implements IDatabase {
	

	private List<Actor> actorList;

	//constructor
	public FakeDatabase() {

		actorList = new ArrayList<Actor>();
		// Add initial data
		readInitialData();
		
		System.out.println(actorList.size() + " actors");
	}

	
	public void readInitialData() {
		try {
			actorList.addAll(InitialData.getActors());
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't read initial data", e);
		}
	}
	

	@Override
	public Actor findActorByID(int actorID) {
	
		for(Actor actor: actorList) {
			if(actor.getActorID() == actorID) {
				return actor;
			}
		}
		System.out.println("Couldnt find actor");
		return null;
	}
	




	@Override
	public Room findRoomByRoomID(int room_id) {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public Actor findActorByRoomID(int roomID) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void updateActor(int actorID, Actor actor) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<RoomConnection> findConnectionsByRoomID(int roomId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void updateRoomByRoom(Room room) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Item> findItemsByOwnerID(int ownerID) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Item> findItemsByNameAndRoomID(String name, int room_id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void updateItem(int itemID, int roomID, int ownerID) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<User> findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
