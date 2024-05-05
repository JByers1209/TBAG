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
	/*
	@Test
	public void testFindConnectionByRoomID() {
		
		System.out.println("Find Connection Test");
		
		List<RoomConnection> room1;
		List<RoomConnection> room5;
		room1 = db.findConnectionsByRoomID(1);
		assertEquals(4, room1.size());
		assertEquals("north", room1.get(0).getMove());
		assertEquals(0, room1.get(0).getDestId());
		
		room5 = db.findConnectionsByRoomID(5);
		assertEquals(4, room5.size());
		assertEquals(2, room5.get(0).getDestId());
		
	}
	
	@Test
	public void testFindRoomByRoomID() {
		
		System.out.println("Find Room Test");
		//Tests room1
		Room one = db.findRoomByRoomID(1);
		assertEquals(1, one.getRoomID());
		assertEquals("Josh's House", one.getName());
		assertEquals("false", one.getVisited());
		assertEquals("true", one.getNeedsKey());
		assertEquals("blue key", one.getKeyName());
		
		//Tests room2
		Room two = db.findRoomByRoomID(2);
		assertEquals(2, two.getRoomID());
		assertEquals("York college campus", two.getName());
		assertEquals("false", two.getVisited());
		assertEquals("none", two.getKeyName());
	}
	
	
	@Test
	public void testUpdateRoomByRoom() {
		
		System.out.println("Update Room Test");
		Room room = db.findRoomByRoomID(1);
		assertEquals("false", room.getVisited());
		assertEquals("true", room.getNeedsKey());
		
		room.setVisited("true");
		room.setNeedsKey("false");
		db.updateRoomByRoom(room);
		
		room = db.findRoomByRoomID(1);
		assertEquals("true", room.getVisited());
		assertEquals("false", room.getNeedsKey());
		
		//Sets database back to default
		room.setVisited("false");
		room.setNeedsKey("true");
		db.updateRoomByRoom(room);
	}
	
	@Test
    public void testFindItemsByRoomID() {
	
        List<Item> items;
        
        items = db.findItemsByRoomID(1);
        assertEquals(1, items.size());
        assertEquals("sword", items.get(0).getName());
        assertEquals(1, items.get(0).getItemID());
        
        items = db.findItemsByRoomID(6);
        assertEquals(1, items.size());
        
        items = db.findItemsByRoomID(8);
        assertEquals(1, items.size());
        
        items = db.findItemsByRoomID(2);
        assertEquals(0, items.size());
    }
	
	@Test
    public void testFindItemsByOwnerID() {	
        List<Item> items;
        
        items = db.findItemsByOwnerID(1);
        assertEquals(0, items.size());
        
        items = db.findItemsByOwnerID(3);
        assertEquals(1, items.size());
        assertEquals("knife", items.get(0).getName());
    }
	*/
	@Test
	public void testUpdateItem() {
		List<Item> items;
		
		//Checks that Actor 3 has a Knife.
		items = db.findItemsByOwnerID(3);
        assertEquals(1, items.size());
        assertEquals("knife", items.get(0).getName());
        
        //Checks that Actor 1 has no item.
        items = db.findItemsByOwnerID(1);
        assertEquals(0, items.size());
        
        //Sets Knife to be owned by actor 1.
        db.updateItem(4, 0, 1);
		
		//Checks that Actor 1 has a Knife.
		items = db.findItemsByOwnerID(1);
		assertEquals(1, items.size());
		assertEquals("knife", items.get(0).getName());
		    
		//Checks that Actor 3 has no item.
		items = db.findItemsByOwnerID(3);
		assertEquals(0, items.size());
		
		//Resets the database to its original value
		db.updateItem(4, 0, 3);
		
	}
	
	@Test
    public void testFindItemsByNameAndRoomID() {
	
        List<Item> items;
        
        items = db.findItemsByNameAndRoomID("sword", 1);
        assertEquals("sword", items.get(0).getName());


    }
	
	@Test
	public void testFindActorByID() {
		NPC npc = new NPC(4, 12, "silly specter", 4, 800, 69, 420);
		Actor retrievedNPC = db.findActorByID(4);
		
		assertEquals(npc.getActorID(), retrievedNPC.getActorID());
		assertEquals(npc.getRoomID(), retrievedNPC.getRoomID());
		assertEquals(npc.getName(), retrievedNPC.getName());
		assertEquals(npc.getLevel(), retrievedNPC.getLevel());
		assertEquals(npc.getXP(), retrievedNPC.getXP());
		assertEquals(npc.getCurrentHealth(), retrievedNPC.getCurrentHealth());
		assertEquals(npc.getMaxHealth(), retrievedNPC.getMaxHealth());
	}
	
	@Test
	public void testFindActorByRoomID() {
		NPC npc = new NPC(4, 12, "silly specter", 4, 800, 69, 420);
		Actor retrievedNPC = db.findActorByRoomID(12);
		
		assertEquals(npc.getActorID(), retrievedNPC.getActorID());
		assertEquals(npc.getRoomID(), retrievedNPC.getRoomID());
		assertEquals(npc.getName(), retrievedNPC.getName());
		assertEquals(npc.getLevel(), retrievedNPC.getLevel());
		assertEquals(npc.getXP(), retrievedNPC.getXP());
		assertEquals(npc.getCurrentHealth(), retrievedNPC.getCurrentHealth());
		assertEquals(npc.getMaxHealth(), retrievedNPC.getMaxHealth());
	}
	
	@Test
	public void testUpdateActor() {
		Actor npc = new NPC(2, 7, "Less Friendly Ghost", 4, 600, 6, 64);
		db.updateActor(npc);
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