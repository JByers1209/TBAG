package edu.ycp.cs320.tbag.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


import edu.ycp.cs320.tbag.dataBase.DerbyDatabase;
import edu.ycp.cs320.tbag.model.Room;

public class DataBaseTest {
	private DerbyDatabase db;
	
	@Before
	public void setUp() {
		db = new DerbyDatabase();
	}
	
//	@Test
//	public void testFindConnectionByRoomIDandDirection() {
//		
//		//Tests room1
//		assertEquals(0, db.findConnectionByRoomIDandDirection(1, "north"));
//		assertEquals(4, db.findConnectionByRoomIDandDirection(1, "south"));
//		assertEquals(2, db.findConnectionByRoomIDandDirection(1, "east"));
//		assertEquals(0, db.findConnectionByRoomIDandDirection(1, "west"));
//		
//		//Tests room2
//		assertEquals(0, db.findConnectionByRoomIDandDirection(2, "north"));
//		assertEquals(5, db.findConnectionByRoomIDandDirection(2, "south"));
//		assertEquals(3, db.findConnectionByRoomIDandDirection(2, "east"));
//		assertEquals(1, db.findConnectionByRoomIDandDirection(2, "west"));
//	}
//	
//	@Test
//	public void testFindRoomByRoomID() {
//		
//		Room one = db.findRoomByRoomID(1);
//		assertEquals(1, one.getRoomID());
//		/*assertEquals("Josh's House" , one.getName());
//		assertEquals(false, one.getVisited());
//		assertEquals("blue key", one.getKeyName());*/
//	}
	
	@Test
	public void testFindUserByUserID(){
		assertEquals(0, db.findUserByUserID (1, "user"));
		assertEquals(4, db.findUserByUserID (1, "kdealva"));
		assertEquals(2, db.findUserByUserID (1, "jbyers"));

	}
}
