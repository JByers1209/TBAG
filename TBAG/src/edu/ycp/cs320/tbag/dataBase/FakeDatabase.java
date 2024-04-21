package edu.ycp.cs320.tbag.dataBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import edu.ycp.cs320.tbag.model.Actor;

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
	public Actor findActorByRoomID(int roomID) {
	
		for(Actor actor: actorList) {
			if(actor.getRoomID() == roomID) {
				return actor;
			}
		}
		return null;
	}
	
	@Override
	public void updateActor(int actorID, Actor actor) {
		
	}

}
