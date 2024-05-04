package edu.ycp.cs320.tbag.dataBase;

import java.util.List;
import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.Item;
import edu.ycp.cs320.tbag.model.Room;
import edu.ycp.cs320.tbag.model.RoomConnection;
import edu.ycp.cs320.tbag.model.User;


public interface IDatabase {

	public Room findRoomByRoomID(int room_id);
	public List<RoomConnection> findConnectionsByRoomID(int roomId);
	public void updateRoomByRoom(Room room);
	public Actor findActorByRoomID(int roomID);
	public void updateActor(int actorID, Actor actor);
	public Actor findActorByID(int actorID);
	public List<Item> findItemsByOwnerID(int ownerID);
	public List<Item> findItemsByNameAndRoomID(String name, int room_id);
	public void updateItem(int itemID, int roomID, int ownerID);
	public User findUserByUsername(String username);
}
