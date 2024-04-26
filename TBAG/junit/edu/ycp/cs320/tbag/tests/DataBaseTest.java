package edu.ycp.cs320.tbag.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


import edu.ycp.cs320.tbag.dataBase.DerbyDatabase;
import edu.ycp.cs320.tbag.model.Item;
import edu.ycp.cs320.tbag.model.Room;

public class DataBaseTest {
	private DerbyDatabase db;
	
	@Before
	public void setUp() {
		db = new DerbyDatabase();
	}
	
	@Test
	public void testFindConnectionByRoomIDandDirection() {
		
		System.out.println("Find Connection Test");
		
		//Tests room1
		assertEquals(0, db.findConnectionByRoomIDandDirection(1, "north"));
		assertEquals(4, db.findConnectionByRoomIDandDirection(1, "south"));
		assertEquals(2, db.findConnectionByRoomIDandDirection(1, "east"));
		assertEquals(0, db.findConnectionByRoomIDandDirection(1, "west"));
		
		//Tests room2
		assertEquals(0, db.findConnectionByRoomIDandDirection(2, "north"));
		assertEquals(5, db.findConnectionByRoomIDandDirection(2, "south"));
		assertEquals(3, db.findConnectionByRoomIDandDirection(2, "east"));
		assertEquals(1, db.findConnectionByRoomIDandDirection(2, "west"));
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
	public void testUpdateRoomByRoomID() {
		
		System.out.println("Update Room Test");
		Room room = db.findRoomByRoomID(1);
		assertEquals("false", room.getVisited());
		assertEquals("true", room.getNeedsKey());
		
		room.setVisited("true");
		room.setNeedsKey("false");
		db.updateRoomByRoomID(room);
		
		room = db.findRoomByRoomID(1);
		assertEquals("true", room.getVisited());
		assertEquals("false", room.getNeedsKey());
		
		//Sets database back to default
		room.setVisited("false");
		room.setNeedsKey("true");
		db.updateRoomByRoomID(room);
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
	
}