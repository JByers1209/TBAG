package edu.ycp.cs320.tbag.dataBase;

import java.util.List;
import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.Room;


public interface IDatabase {
	
	public Actor findActorByID(int actorID);
	public Room findRoomByActorID(int actorId);
	public Room findConnectionByRoomID(int roomId);
	public void updateCurrentRoomByRoomAndActorID(int newRoomId, int actorId);
	
	
}
