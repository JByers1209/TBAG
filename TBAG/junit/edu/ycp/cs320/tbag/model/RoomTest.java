package edu.ycp.cs320.tbag.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class RoomTest {
	Room room1, room2;
    
    @Before
    public void setUp() {
        
        room1 = new Room("Test Room 1", "a rubber room with rats", false);
        room2 = new Room("Test Room 2", "a boring old empty room", false);
        
    }
    
    
    @Test
    public void testSetExit() {
        room1.setExit("north", room2);
        assertEquals(room1.getExit("north"), room2);
    }
    
    @Test
    public void testNeedsKey() {
        room1.setNeedsKey(true);
        assertEquals(room1.getNeedsKey(), true);
    }
    
    @Test
    public void testRoomDescription() {
        room2.setDescription("Rats drive me crazy");
        assertEquals(room2.getDescription(), "Rats drive me crazy");
    }
}
