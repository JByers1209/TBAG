package edu.ycp.cs320.tbag.model;

public abstract class Actor {

	
	
	private Room location;
	private Inventory inventory;
	private int currentHealth, maxHealth;
	private int level;
	
	
	
	public Actor(Room room) {
		location = room;
		inventory = new Inventory();
	}
	
	
	
	public void moveTo(RoomConnection roomConnection) {
		
	}
	
	public Room getCurrentRoom() {
		return location;
	}
	
	public void pickupItem(Item item) {
		
	}
	
	public void dropItem(Item item) {
		
	}
	
	
	public int getCurrentHealth() {
		return currentHealth;
	}
	
	
	public int getMaxHealth() {
		return maxHealth;
	}
		
	
	public int getLevel() {
		return level;
	}
	
	public void setCurrentHealth(int health) {
		currentHealth = health;
	}
	
	public void setMaxHealth(int health) {
		maxHealth = health;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
