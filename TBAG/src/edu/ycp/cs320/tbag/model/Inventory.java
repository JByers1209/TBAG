package edu.ycp.cs320.tbag.model;

import java.util.ArrayList;
import java.util.List;

// Superclass for all items
class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Subclass for key items-------------------------------------------------------------------------
class KeyItem extends Item {
	private boolean throwable;
	
    public KeyItem(String name, boolean throwable) {
        super(name);
        this.throwable = throwable;
    }
    
    public boolean getThrowable() {
    	return throwable;
    }
}

// Subclass for weapons----------------------------------------------------------------------------
class Weapon extends Item {
    private int damage;

    public Weapon(String name, boolean throwable, int damage) {
        super(name);
        this.damage = damage;
    }
    
    public int getDamage() {
        return damage;
    }
}

// Subclass for consumables----------------------------------------------------------------
class Consumable extends Item {
    private String effect;

    public Consumable(String name, String effect) {
        super(name);
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
        items.add(new Consumable("Bandages", "Restores health"));
        // Add more initial items as needed
    }

    // Method to add item to the inventory
    public void addItem(Item item) {
        items.add(item);
    }
}

