package edu.ycp.cs320.tbag.model;

import java.util.ArrayList;
import java.util.List;

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
    
    public List<Item> getItems(){
    	return items;
    }
    

}

