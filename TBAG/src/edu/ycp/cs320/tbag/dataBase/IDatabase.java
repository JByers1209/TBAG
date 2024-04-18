package edu.ycp.cs320.tbag.dataBase;

import java.util.List;
import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.Room;


public interface IDatabase {
	
	public Actor findActorByID(int actorID);
	public Room findCurrentLocationByActorID(int actorId);
	public Room findRoomByRoomID(int room_id);
	public int findConnectionByRoomIDandDirection(int roomId, String move);
	public void updateNeedsKeyByRoomID(int room_id);
	public void updateHasVisitedByRoomID(int room_id);
	
}
