package edu.ycp.cs320.tbag.dataBase;

import java.util.List;
import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.Room;


public interface IDatabase {
	
	public Room findCurrentLocationByActorID(int actorId);
	public Room findRoomByRoomID(int room_id);
	public int findConnectionByRoomIDandDirection(int roomId, String move);
	public void updateRoomByRoomID(Room room);
	public Actor findActorByRoomID(int roomID);
	public void updateActor(int actorID, Actor actor);
	
}
