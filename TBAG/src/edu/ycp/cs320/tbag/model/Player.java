package edu.ycp.cs320.tbag.model;

public class Player extends Actor {
	
	
	
	public Player(Room room) {
		super(room);
	}

	private int xp;
	
	
	
	
	
	
	
	public int getXP() {
		return xp;
	}
	
	public void setXP(int xp) {
		this.xp = xp;
	}
	
	

}
