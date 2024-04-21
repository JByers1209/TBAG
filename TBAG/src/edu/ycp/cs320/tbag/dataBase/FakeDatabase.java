package edu.ycp.cs320.tbag.dataBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.Room;

public class FakeDatabase implements IDatabase {
	

	private List<Actor> actorList;

	//constructor
	public FakeDatabase() {

		actorList = new ArrayList<Actor>();
		// Add initial data
		readInitialData();
		
		System.out.println(actorList.size() + " actor");
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
		return null;
	}


	@Override
	public Room findCurrentLocationByActorID(int actorId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Room findRoomByRoomID(int room_id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int findConnectionByRoomIDandDirection(int roomId, String move) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void updateRoomByRoomID(Room room) {
		// TODO Auto-generated method stub
		
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

}
