package edu.ycp.cs320.tbag.model;

public abstract class Actor {

	
	
	private Room location;
	private int currentHealth, maxHealth;
	private int level;
	Inventory inventory = new Inventory();
	
	public Actor(Room room) {
		location = room;
	}
	
	public void moveTo(Room room) {
		this.location = room;
	}
	
	public Room getCurrentRoom() {
		return location;
	}
	
	public void setCurrentRoom(Room room) {
		this.location = room;
	}
	
	public void pickupItem(Item item) {
		inventory.addItem(item);
	}
	
	public void dropItem(Item item) {
		inventory.removeItem(item);
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
