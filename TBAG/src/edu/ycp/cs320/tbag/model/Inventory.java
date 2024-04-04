package edu.ycp.cs320.tbag.model;

import java.util.ArrayList;
import java.util.List;

// Define the Inventory class--------------------------------------------------------------------------
public class Inventory {
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
    
    public List<Item> getItems(){
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
    

}

