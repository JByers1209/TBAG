package edu.ycp.cs320.tbag.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


import edu.ycp.cs320.tbag.dataBase.DerbyDatabase;
import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.Item;
import edu.ycp.cs320.tbag.model.NPC;
import edu.ycp.cs320.tbag.model.Room;
import edu.ycp.cs320.tbag.model.RoomConnection;
import edu.ycp.cs320.tbag.model.User;

public class DataBaseTest {
	private DerbyDatabase db;
	
	@Before
	public void setUp() {
		db = new DerbyDatabase();
	}
	
	@Test
	public void testFindConnectionByRoomID() {
		
		System.out.println("Find Connection Test");
		
		List<RoomConnection> room1;
		List<RoomConnection> room5;
		room1 = db.findConnectionsByRoomID(1);
		assertEquals(5, room1.size());
		assertEquals("west", room1.get(0).getMove());
		assertEquals(4, room1.get(0).getDestId());
		
		room5 = db.findConnectionsByRoomID(5);
		assertEquals(2, room5.size());
		assertEquals("walk", room5.get(0).getMove());
		assertEquals(21, room5.get(0).getDestId());
		
	}
	
	
	@Test
	public void testFindRoomByRoomID() {
		
		System.out.println("Find Room Test");
		//Tests room1
		Room one = db.findRoomByRoomID(1);
		assertEquals(1, one.getRoomID());
		assertEquals("Start", one.getName());
		assertEquals("true", one.getVisited());
		assertEquals("false", one.getNeedsKey());
		assertEquals("none", one.getKeyName());
		
		//Tests room2
		Room two = db.findRoomByRoomID(2);
		assertEquals(2, two.getRoomID());
		assertEquals("Josh's House", two.getName());
		assertEquals("false", two.getVisited());
		assertEquals("true", two.getNeedsKey());
		assertEquals("rusty key", two.getKeyName());
	}
	
	@Test
	public void testUpdateRoomByRoom() {
		
		System.out.println("Update Room Test");
		Room room = db.findRoomByRoomID(1);
		room.setVisited("true");
		room.setNeedsKey("false");
		db.updateRoomByRoom(room);
		assertEquals("true", room.getVisited());
		assertEquals("false", room.getNeedsKey());
		
		room.setVisited("false");
		room.setNeedsKey("true");
		db.updateRoomByRoom(room);
		
		room = db.findRoomByRoomID(1);
		assertEquals("false", room.getVisited());
		assertEquals("true", room.getNeedsKey());
		
		//Sets database back to default
		room.setVisited("true");
		room.setNeedsKey("false");
		db.updateRoomByRoom(room);
	}
	
	@Test
    public void testFindItemsByRoomID() {
        List<Item> items;
        
        items = db.findItemsByRoomID(2);
        assertEquals(1, items.size());
        assertEquals("lightsaber", items.get(0).getName());
        assertEquals(4, items.get(0).getItemID());
        
        items = db.findItemsByRoomID(6);
        assertEquals(1, items.size());
        assertEquals("bandages", items.get(0).getName());
        assertEquals(8, items.get(0).getItemID());
        
        items = db.findItemsByRoomID(8);
        assertEquals(1, items.size());
        assertEquals("rusty key", items.get(0).getName());
        assertEquals(2, items.get(0).getItemID());
        
    }
	
	@Test
    public void testFindItemsByOwnerID() {	
        List<Item> items;
        
        db.updateItem(1, 0, 1);
        
        items = db.findItemsByOwnerID(1);
        assertEquals(1, items.size());
        assertEquals("knife", items.get(0).getName());
        
        items = db.findItemsByOwnerID(3);
        assertEquals(0, items.size());
        
        db.updateItem(1, 1, 0);
    }
	
	@Test
	public void testUpdateItem() {
		List<Item> items;
		
		//Ensures that Actor 1 starts with the lightsaber on every test
		db.updateItem(4, 0, 1);
		
		//Checks that Actor 1 has a lightsaber.
		items = db.findItemsByOwnerID(1);
        assertEquals(1, items.size());
        assertEquals("lightsaber", items.get(0).getName());
        
        //Checks that Actor 2 has no item.
        items = db.findItemsByOwnerID(2);
        assertEquals(0, items.size());
        
        //Sets Knife to be owned by actor 2.
        db.updateItem(4, 0, 2);
		
		//Checks that Actor 2 has a lightsaber.
		items = db.findItemsByOwnerID(2);
		assertEquals(1, items.size());
		assertEquals("lightsaber", items.get(0).getName());
		    
		//Checks that Actor 1 has no item.
		items = db.findItemsByOwnerID(1);
		assertEquals(0, items.size());
		
		//Resets the database to its original value
		db.updateItem(4, 2, 0);
		
	}
	
	@Test
    public void testFindItemsByNameAndRoomID() {
	
        List<Item> items;
        
        items = db.findItemsByNameAndRoomID("lightsaber", 2);
        assertEquals("lightsaber", items.get(0).getName());


    }
	
	@Test
	public void testFindActorByID() {
		Actor NPC = db.findActorByID(3);
		
		assertEquals(3, NPC.getActorID());
		assertEquals(8, NPC.getRoomID());
		assertEquals("null", NPC.getName());
		assertEquals(6, NPC.getLevel());
		assertEquals(100, NPC.getXP());
		assertEquals(200, NPC.getCurrentHealth());
		assertEquals(200, NPC.getMaxHealth());
	}
	
	@Test
	public void testFindActorByRoomID() {
		Actor NPC = db.findActorByRoomID(12);
		
		assertEquals(2, NPC.getActorID());
		assertEquals(12, NPC.getRoomID());
		assertEquals("gray man", NPC.getName());
		assertEquals(4, NPC.getLevel());
		assertEquals(100, NPC.getXP());
		assertEquals(75, NPC.getCurrentHealth());
		assertEquals(100, NPC.getMaxHealth());
	}
	
	@Test
	public void testUpdateActor() {
		Actor NPC = db.findActorByID(3);
		db.updateActor(NPC);
		
		NPC.setCurrentHealth(150);
		db.updateActor(NPC);
		
		assertEquals(150, NPC.getCurrentHealth());
		
		NPC.setCurrentHealth(200);
		db.updateActor(NPC);
	}
	
	@Test
	public void testFindUser() {
		User user = db.findUser("kdealva");
		assertEquals(2, user.getUserID());
		assertEquals("kdealva", user.getUsername());
		assertEquals("wwof1153", user.getPassword());
		
	}
	
	@Test
	public void testFindItemByName() {
		List<Item> item = db.findItemsByName("lightsaber");
		assertEquals("true", item.get(0).getThrowable());
		assertEquals("lightsaber", item.get(0).getName());
		assertEquals(1, item.get(0).getType());
		assertEquals("damage", item.get(0).getEffect());
	}
	
}