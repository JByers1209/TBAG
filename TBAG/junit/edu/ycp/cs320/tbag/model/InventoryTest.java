<<<<<<<< HEAD:TBAG/junit/edu/ycp/cs320/tbag/model/InventoryTest.java
package edu.ycp.cs320.tbag.model;
========
package edu.ycp.cs320.tbag.tests;
>>>>>>>> Ethan:TBAG/junit/edu/ycp/cs320/tbag/tests/InventoryTest.java
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
    
    @Test
    public void testGetItems() {
        List<Item> items = inventory.getItems();
        
        assertNotNull(items);
        assertEquals(3, items.size()); 
        assertTrue(items.get(0) instanceof KeyItem); 
        assertTrue(items.get(1) instanceof Weapon);  
        assertTrue(items.get(2) instanceof Consumable); 
    }
    
    @Test
    public void testGetItemNames() {
        String expectedNames = "GreenKey, Sword, Bandages";
        assertEquals(expectedNames, inventory.getItemNames());
    }
    
    @Test
    public void testGetItemByName() {
        Item foundItem = inventory.getItemByName("Sword");
        assertNotNull(foundItem);
        assertEquals("Sword", foundItem.getName());
    }
}


