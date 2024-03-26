package edu.ycp.cs320.tbag.model;



public abstract class Actor {

	
	
	private Room location;
	private Inventory inventory;
	private int currentHealth, maxHealth;
	private int level;
	
	public Actor(int maxHealth, Room room) {
		location = room;
		inventory = new Inventory();
		this.maxHealth = maxHealth;
		currentHealth = maxHealth;
	}
	
	public void moveTo(Room room) {
		this.location = room;
	}
	
	public Room getCurrentRoom() {
		return location;
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
		if(health > maxHealth) {
			currentHealth = maxHealth;
		}else {
			currentHealth = health;
		}
			
			
	}
	
	public void setMaxHealth(int health) {
		if(health < maxHealth) {
			currentHealth = health;
		}
		maxHealth = health;
		
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
    public boolean hasItem(Item item) {
    	for(Item i: inventory.getItems()) {
    		if (i.equals(item)) {
    			return true;
    		}
    	}
    	return false;
    }
}
