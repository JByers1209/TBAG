package edu.ycp.cs320.tbag.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


import edu.ycp.cs320.tbag.dataBase.DerbyDatabase;
import edu.ycp.cs320.tbag.model.Room;
import edu.ycp.cs320.tbag.model.Actor;

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
	public void testFindActorByID() {
		
		System.out.println("Find Actor Test");
		Actor player = db.findActorByID(1);
		assertEquals(5, player.getRoomID());
		assertEquals("player", player.getName());
		assertEquals(5, player.getLevel());
		assertEquals(1000, player.getXP());
		assertEquals(80, player.getCurrentHealth());
		assertEquals(100, player.getMaxHealth());
	}
	
}