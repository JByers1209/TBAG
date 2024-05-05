package edu.ycp.cs320.tbag.dataBase;

import java.util.List;
import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.Item;
import edu.ycp.cs320.tbag.model.Room;
import edu.ycp.cs320.tbag.model.RoomConnection;
import edu.ycp.cs320.tbag.model.SaveData;
import edu.ycp.cs320.tbag.model.SaveGame;
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
	public List<Item> findItemsByName(String name);
	
	//user
	public User findUser(String username);
	public void insertUser(String username, String password);
	
	//save data
	public void saveActor(int saveID, Actor actor);
	public void saveRoom(int saveID, Room room);
	public void saveItem(int saveID, Item item);
	public void saveLog(int saveID, String log);
	public List<SaveData> getSaveData(int saveID);
	
	//save game
	public List<SaveGame> getSaveGames(int userID);
	public SaveGame getSaveGameByName(String saveName);
	public int getNextSaveID();
	public void addSaveGame(SaveGame saveGame);
}
