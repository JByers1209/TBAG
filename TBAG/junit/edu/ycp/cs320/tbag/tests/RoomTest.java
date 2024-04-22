package edu.ycp.cs320.tbag.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.tbag.model.Room;

public class RoomTest {
	Room room1, room2;
    
    @Before
    public void setUp() {
        
        room1 = new Room("Test Room 1", "a rubber room with rats", "false");
        room2 = new Room("Test Room 2", "a boring old empty room", "false");
        
    }
    
    @Test
    public void testNeedsKey() {
        room1.setNeedsKey("true");
        assertEquals(room1.getNeedsKey(), "true");
    }
    
    @Test
    public void testRoomShortDescription() {
        room2.setShortDescription("Rats drive me crazy");
        assertEquals(room2.getShortDescription(), "Rats drive me crazy");
    }
}
