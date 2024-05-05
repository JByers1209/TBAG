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
		assertEquals(4, room1.size());
		assertEquals("north", room1.get(0).getMove());
		assertEquals(3, room1.get(0).getDestId());
		
		room5 = db.findConnectionsByRoomID(5);
		assertEquals(4, room5.size());
		assertEquals("north", room1.get(0).getMove());
		assertEquals(2, room5.get(0).getDestId());
		
	}
	
	
	@Test
	public void testFindRoomByRoomID() {
		
		System.out.println("Find Room Test");
		//Tests room1
		Room one = db.findRoomByRoomID(1);
		assertEquals(1, one.getRoomID());
		assertEquals("Start", one.getName());
		assertEquals("false", one.getVisited());
		assertEquals("false", one.getNeedsKey());
		assertEquals("none", one.getKeyName());
		
		//Tests room2
		Room two = db.findRoomByRoomID(2);
		assertEquals(2, two.getRoomID());
		assertEquals("Josh's House", two.getName());
		assertEquals("false", two.getVisited());
		assertEquals("true", two.getNeedsKey());
		assertEquals("blue key", two.getKeyName());
	}
	
	@Test
	public void testUpdateRoomByRoom() {
		
		System.out.println("Update Room Test");
		Room room = db.findRoomByRoomID(1);
		room.setVisited("false");
		room.setNeedsKey("false");
		db.updateRoomByRoom(room);
		assertEquals("false", room.getVisited());
		assertEquals("false", room.getNeedsKey());
		
		room.setVisited("true");
		room.setNeedsKey("true");
		db.updateRoomByRoom(room);
		
		room = db.findRoomByRoomID(1);
		assertEquals("true", room.getVisited());
		assertEquals("true", room.getNeedsKey());
		
		//Sets database back to default
		room.setVisited("false");
		room.setNeedsKey("false");
		db.updateRoomByRoom(room);
	}
	
	@Test
    public void testFindItemsByRoomID() {
        List<Item> items;
        
        items = db.findItemsByRoomID(2);
        assertEquals(1, items.size());
        assertEquals("sword", items.get(0).getName());
        assertEquals(1, items.get(0).getItemID());
        
        items = db.findItemsByRoomID(6);
        assertEquals(1, items.size());
        assertEquals("bandage", items.get(0).getName());
        assertEquals(2, items.get(0).getItemID());
        
        items = db.findItemsByRoomID(8);
        assertEquals(1, items.size());
        assertEquals("blue key", items.get(0).getName());
        assertEquals(3, items.get(0).getItemID());
        
    }
	
	@Test
    public void testFindItemsByOwnerID() {	
        List<Item> items;
        
        items = db.findItemsByOwnerID(1);
        assertEquals(2, items.size());
        assertEquals("knife", items.get(0).getName());
        assertEquals("lightsaber", items.get(1).getName());
        
        items = db.findItemsByOwnerID(3);
        assertEquals(0, items.size());
    }
	
	@Test
	public void testUpdateItem() {
		List<Item> items;
		
		//Ensures that Actor 1 starts with the Knife on every test
		db.updateItem(4, 0, 1);
		
		//Checks that Actor 1 has a Knife.
		items = db.findItemsByOwnerID(1);
        assertEquals(2, items.size());
        assertEquals("knife", items.get(0).getName());
        
        //Checks that Actor 2 has no item.
        items = db.findItemsByOwnerID(2);
        assertEquals(0, items.size());
        
        //Sets Knife to be owned by actor 2.
        db.updateItem(4, 0, 2);
		
		//Checks that Actor 2 has a Knife.
		items = db.findItemsByOwnerID(2);
		assertEquals(1, items.size());
		assertEquals("knife", items.get(0).getName());
		    
		//Checks that Actor 1 has 1 item(lightsaber).
		items = db.findItemsByOwnerID(1);
		assertEquals(1, items.size());
		
		//Resets the database to its original value
		db.updateItem(4, 0, 1);
		
	}
	
	@Test
    public void testFindItemsByNameAndRoomID() {
	
        List<Item> items;
        
        items = db.findItemsByNameAndRoomID("sword", 2);
        assertEquals("sword", items.get(0).getName());


    }
	
	@Test
	public void testFindActorByID() {
		Actor NPC = db.findActorByID(4);
		
		assertEquals(4, NPC.getActorID());
		assertEquals(12, NPC.getRoomID());
		assertEquals("silly specter", NPC.getName());
		assertEquals(4, NPC.getLevel());
		assertEquals(100, NPC.getXP());
		assertEquals(69, NPC.getCurrentHealth());
		assertEquals(420, NPC.getMaxHealth());
	}
	
	@Test
	public void testFindActorByRoomID() {
		Actor NPC = db.findActorByRoomID(12);
		
		assertEquals(4, NPC.getActorID());
		assertEquals(12, NPC.getRoomID());
		assertEquals("silly specter", NPC.getName());
		assertEquals(4, NPC.getLevel());
		assertEquals(100, NPC.getXP());
		assertEquals(69, NPC.getCurrentHealth());
		assertEquals(420, NPC.getMaxHealth());
	}
	
	@Test
	public void testUpdateActor() {
		Actor npc = new NPC(2, 7, "Less Friendly Ghost", 4, 600, 6, 64);
		db.updateActor(2, npc);
		Actor retrievedNPC = db.findActorByID(2);
		
		assertEquals(npc.getActorID(), retrievedNPC.getActorID());
		assertEquals(npc.getRoomID(), retrievedNPC.getRoomID());
		assertEquals(npc.getName(), retrievedNPC.getName());
		assertEquals(npc.getLevel(), retrievedNPC.getLevel());
		assertEquals(npc.getXP(), retrievedNPC.getXP());
		assertEquals(npc.getCurrentHealth(), retrievedNPC.getCurrentHealth());
		assertEquals(npc.getMaxHealth(), retrievedNPC.getMaxHealth());
		
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
		assertEquals("False", item.get(0).getThrowable());
		assertEquals("lightsaber", item.get(0).getName());
		assertEquals(1, item.get(0).getType());
		assertEquals("damage", item.get(0).getEffect());
	}
	
}