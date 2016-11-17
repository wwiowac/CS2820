import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by kyle on 11/10/16.
 */
public class InventoryManagementTest {
    Master master = new Master();
    Floor floor = new Floor(master);
    InventoryManagement inventory = new InventoryManagement(floor);

    @org.junit.Test
    public void addShelf() throws Exception {

    }

    @org.junit.Test
    public void addItem() throws Exception {

    }

    /**
     * @throws Exception should print out the list of inventory items
     */
    @Test
    public void getCurrentInventory() throws Exception {
        inventory.addShelf(new Point(17, 24));

        //seeds the inventory with a random quantity
        for (InventoryItem item : InventoryList.catalog) {
            inventory.addItem(item);
        }

        HashMap currentInventory = inventory.getCurrentInventory();
        assertTrue("The Inventory list is populating", currentInventory.size() > 1);
    }

}