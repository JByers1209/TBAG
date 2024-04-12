package edu.ycp.cs320.tbag.model;



public abstract class Actor {

	
	
	private Room location;
	private Inventory inventory;
	private int currentHealth, maxHealth;
	private int level, roomID, xp, actorID;
	
	public Actor() {
		inventory = new Inventory();
		currentHealth = maxHealth;
	}
	
	public Actor(Room room) {
		location = room;
		inventory = new Inventory();
		currentHealth = maxHealth;
		
	}
	
	public Actor(int maxHealth, Room room) {
		location = room;
		inventory = new Inventory();
		this.maxHealth = maxHealth;
		currentHealth = maxHealth;
	}
	

		
	public int getActorID() {
		return actorID;
	}

	public void setActorID(int actorID) {
		this.actorID = actorID;
	}

	public int getXP() {
		return xp;
	}
	
	public void setXP(int xp) {
		this.xp = xp;
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
	
	public int getRoomID() {
		return roomID;
	}
	
	public void setRoomID(int ID) {
		roomID = ID;
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
