package edu.ycp.cs320.tbag.tests;

import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.tbag.model.Item;
import edu.ycp.cs320.tbag.model.KeyItem;
import edu.ycp.cs320.tbag.model.NPC;
import edu.ycp.cs320.tbag.model.Player;
import edu.ycp.cs320.tbag.model.Room;
import edu.ycp.cs320.tbag.model.Weapon;



public class ActorTest {
	
	Room room1, room2;
	Player player;
	NPC npc;
	Item item1, item2, item3;
	
	@Before
	public void setUp() {
		
		room1 = new Room("Test Room 1", "a rubber room with rats");
		room2 = new Room("Test Room 2", "a boring old empty room");
		item1 = new Weapon("A Rusty Sword", false, 10);
		item2 = new KeyItem("A Strange Key", false);
		player = new Player(20, room1);
		npc = new NPC(20, room1);
		
		
	}
	
	
	@Test
	public void testMoveTo() {
		player.moveTo(room2);
		assertEquals(player.getCurrentRoom(), room2);
	}
	
	@Test
	public void testPickupItem() {
		player.pickupItem(item1);
		assertEquals(player.hasItem(item1), true);
	}
	
	@Test
	public void testDropItem() {
		player.pickupItem(item1);
		player.dropItem(item1);
		assertEquals(player.hasItem(item1), false);
	}
	
	@Test
	public void testSetCurrentHealth() {
		//test setting health
		player.setCurrentHealth(10);
		assertEquals(player.getCurrentHealth(), 10);
		//test setting health to value over maxhealth
		player.setCurrentHealth(30);
		assertEquals(player.getCurrentHealth(), 20);
	}
	
	@Test
	public void testSetMaxHealth() {
		
		//test setting max health
		player.setMaxHealth(30);
		assertEquals(player.getMaxHealth(), 30);
		//test setting max health when max health is less than previous max health
		player.setMaxHealth(10);
		assertEquals(player.getCurrentHealth(), 10);
		assertEquals(player.getMaxHealth(), 10);
	}
	
	
	

}
