package edu.ycp.cs320.tbag.model;

public class Player extends Actor {
	
	public Player(int actorID, int roomID, String name, int level, int xp, int currentHealth, int maxHealth) {
    	super(actorID, roomID, name, level, xp, currentHealth, maxHealth);
    }
	
	
	public Player(int maxHealth, Room room) {
		super(maxHealth, room);
	}
	
	public Player(Room room) {
		super(room);
	}
	
	public Player() {
		super();
	}


	

}
