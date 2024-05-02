package edu.ycp.cs320.tbag.model;



public abstract class Actor {

	
	
	private Room location;
	private Inventory inventory;
	private int currentHealth, maxHealth;
	private int level, roomID, xp, actorID;
	private String name;
	
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
	
    public Actor(int actorID, int roomID, String name, int level, int xp, int currentHealth, int maxHealth) {
    	this.actorID = actorID;
    	this.roomID = roomID;
    	this.name = name;
    	this.level = level;
    	this.xp = xp;
    	this.maxHealth = maxHealth;
    	this.currentHealth = currentHealth;
    }
		
    public void update(SaveData saveData) {
    	this.actorID = saveData.getIdSlot1();
    	this.roomID = saveData.getIdSlot2();
    	this.level = saveData.getLevel();
    	this.xp = saveData.getXp();
    	this.currentHealth = saveData.getCurrentHealth();
    	this.maxHealth = saveData.getMaxHealth();
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		if(health > maxHealth && maxHealth != 0) {
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
    
    public String toString() {
    	String str = "";
    	str += "actorID: " + actorID + "\n";
    	str += "name: " + name + "\n";
    	str += "roomID: " + roomID + "\n";
    	str += "level: " + level + "\n";
    	str += "xp: " + xp + "\n";
    	str += "current_health: " + currentHealth + "\n";
    	str += "max_health: " + maxHealth + "\n";
		return str;
    	
    }
}
