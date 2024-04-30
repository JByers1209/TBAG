package edu.ycp.cs320.tbag.model;

public abstract class Item {
	private int itemID;
	private int type;
    private String name;
	private String description;
	private String throwable;
	
	private int damage;
	private String effect;
	
	private int roomID;
	private int ownerID;

	public Item() {
	}
	
    public Item(String name, String throwable) {
        this.name = name;
        this.throwable = "false";
    }
    
    public void setItemID(int itemID) {
    	this.itemID = itemID;
    }

    public int getItemID() {
        return itemID;
    }
    
    public void setType(int type) {
    	this.type = type;
    }
    
    public int getType() {
        return type;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }

    
    public void setThrowable(String throwable) {
    	this.throwable = throwable;
    }
    
    public String getThrowable() {
    	return throwable;
    }
    
    public void setDamage(int damage) {
    	this.damage = damage;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public void setEffect(String effect) {
    	this.effect = effect;
    }
    
    public String getEffect() {
        return effect;
    }
    
    public void setRoomID(int roomID) {
    	this.roomID = roomID;
    }
    
    public int getRoomID() {
        return roomID;
    }
    
    public void setOwnerID(int ownerID) {
    	this.ownerID = ownerID;
    }
    
    public int getOwnerID() {
        return ownerID;
    }
}
