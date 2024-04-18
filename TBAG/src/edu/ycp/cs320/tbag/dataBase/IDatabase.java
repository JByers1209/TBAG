package edu.ycp.cs320.tbag.dataBase;

import java.util.List;
import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.Room;


public interface IDatabase {
	
	public Actor findActorByID(int actorID);
	public Room findCurrentLocationByActorID(int actorId);
	public int findConnectionByRoomIDandDirection(String roomId, String move);
	public void updateCurrentRoomByRoomAndActorID(int newRoomId, int actorId);
	
	
}
