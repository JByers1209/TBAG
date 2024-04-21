package edu.ycp.cs320.tbag.model;

public class NPC extends Actor{
	
	
	public NPC(int actorID, int roomID, String name, int level, int xp, int maxHealth, int currentHealth) {
    	super(actorID, roomID, name, level, xp, maxHealth, currentHealth);
    }

	public NPC(int maxHealth, Room room) {
		super(maxHealth, room);
		
	}
	
	public NPC(Room room) {
		super(room);
	}
	
	public NPC() {
		super();
	}
	
	
	
	
	
	
	
	

}
