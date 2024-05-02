package edu.ycp.cs320.tbag.dataBase;

import java.util.List;
import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.Item;
import edu.ycp.cs320.tbag.model.Room;
import edu.ycp.cs320.tbag.model.RoomConnection;
import edu.ycp.cs320.tbag.model.SaveData;
import edu.ycp.cs320.tbag.model.User;


public interface IDatabase {

	//room
	public Room findRoomByRoomID(int room_id);
	public List<RoomConnection> findConnectionsByRoomID(int roomId);
	public void updateRoomByRoom(Room room);
	
	//actor
	public Actor findActorByRoomID(int roomID);
	public void updateActor(Actor actor);
	public Actor findActorByID(int actorID);
	
	//item
	public List<Item> findItemsByOwnerID(int ownerID);
	public List<Item> findItemsByNameAndRoomID(String name, int room_id);
	public void updateItem(int itemID, int roomID, int ownerID);
	public Item findItemByID(int itemID);
	
	//user
	public List<User> findUserByUsername(String username);
	
	//save game
	public void saveActor(int saveID, Actor actor);
	public void saveRoom(int saveID, Room room);
	public void saveItem(int saveID, Item item);
	public void saveLog(int saveID, String log);
	public List<SaveData> getSaveData(int saveID);
	
}
