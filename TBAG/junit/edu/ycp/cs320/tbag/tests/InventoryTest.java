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
        inventory.addItem(new KeyItem());
    }
    
    @Test
    public void testAddItem() {
        int initialSize = inventory.getItems().size();
        inventory.addItem(new KeyItem());
        assertEquals(initialSize + 1, inventory.getItems().size());
    }
    
    @Test
    public void testGetItems() {
        List<Item> items = inventory.getItems();
        
        assertNotNull(items);
        assertEquals(1, items.size()); 
        assertTrue(items.get(0) instanceof KeyItem); 
    }

}


