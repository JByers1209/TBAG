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
	public void testFindActorByID() {
		
		System.out.println("Find Actor Test");
		Actor player = db.findActorByID(1);
		assertEquals(5, player.getRoomID());
		assertEquals("player", player.getName());
		assertEquals(5, player.getLevel());
		assertEquals(1000, player.getXP());
		assertEquals(100, player.getCurrentHealth());
		assertEquals(100, player.getMaxHealth());
	}
	
}