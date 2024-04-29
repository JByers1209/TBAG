package edu.ycp.cs320.tbag.model;

public class NPC extends Actor{
	
	
	public NPC(int actorID, int roomID, String name, int level, int xp, int currentHealth, int maxHealth) {
    	super(actorID, roomID, name, level, xp, currentHealth, maxHealth);
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