package edu.ycp.cs320.tbag.model;

import java.util.ArrayList;
import java.util.List;

// Subclass for key items-------------------------------------------------------------------------
class KeyItem extends Item {
	
    public KeyItem(String name, boolean throwable) {
        super(name, throwable);
    }
}

// Subclass for weapons----------------------------------------------------------------------------
class Weapon extends Item {
    private int damage;

    public Weapon(String name, boolean throwable, int damage) {
        super(name, throwable);
        this.damage = damage;
    }
    
    public int getDamage() {
        return damage;
    }
}

// Subclass for consumables----------------------------------------------------------------
class Consumable extends Item {
    private String effect;

    public Consumable(String name, boolean throwable, String effect) {
        super(name, throwable);
        this.effect = effect;
    }

    public String getEffect() {
        return effect;
    }
}

// Define the Inventory class--------------------------------------------------------------------------
class Inventory {
    private List<Item> items;

    // Constructor to initialize inventory and add initial items
    public Inventory() {
        items = new ArrayList<>();
    }

    // Method to initialize items in the inventory
    private void initializeItems() {
        items.add(new KeyItem("GreenKey", false));
        items.add(new Weapon("Sword", true, 10));
        items.add(new Consumable("Bandages", false, "Restores health"));
    }

    public void addItem(Item item) {
        items.add(item);
    }
    
    public void removeItem(Item item) {
    	items.remove(item);
    }
}

