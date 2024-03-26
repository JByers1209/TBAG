package edu.ycp.cs320.tbag.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.tbag.model.Consumable;
import edu.ycp.cs320.tbag.model.Inventory;
import edu.ycp.cs320.tbag.model.Item;
import edu.ycp.cs320.tbag.model.KeyItem;
import edu.ycp.cs320.tbag.model.Weapon;

public class InventoryTest {
    private Inventory inventory;
    
    @Before
    public void setUp() {
        inventory = new Inventory();
        inventory.initializeItems(); // Make sure to initialize items before testing
    }
    
    @Test
    public void testAddItem() {
        int initialSize = inventory.getItems().size();
        inventory.addItem(new KeyItem("TestKey", true));
        assertEquals(initialSize + 1, inventory.getItems().size());
    }
    
    @Test
    public void testRemoveItem() {
        int initialSize = inventory.getItems().size();
        Item itemToRemove = inventory.getItems().get(0); // Get the first item for testing
        inventory.removeItem(itemToRemove);
        assertEquals(initialSize - 1, inventory.getItems().size());
    }
    
    public void testGetItems() {
        List<Item> items = inventory.getItems();
        
        assertNotNull(items);
        
        assertEquals(3, items.size()); 
        
        assertTrue(items.get(0) instanceof KeyItem); 
        assertTrue(items.get(1) instanceof Weapon);  
        assertTrue(items.get(2) instanceof Consumable); 
    }
    
}

