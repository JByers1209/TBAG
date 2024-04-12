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
    public void initializeItems() {
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
    
    public List<Item> getItems() {
        return items;
    }

	public Object getItemNames() {
		String result = "";
		int i = 0;
		for(Item item: items) {
			result += item.getName();
			if(i < items.size()-1){result+=" ,";}
		}
		return result;
	}

	public Item getItemByName(String itemName) {
		for(Item item: items) {
			if(item.getName().equalsIgnoreCase(itemName)) {
				return item;
			}
		}
		return null;
	}
    
 // Method to return a string of item names separated by commas
    public String getItemNames() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            result.append(item.getName());
            if (i < items.size() - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }
    
    public Item getItemByName(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null; // Item not found
    }

}

