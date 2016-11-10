package Inventory;

import java.util.*;

public class InventoryManagement {

    private ArrayList<MockShelf> shelves;
    private HashMap<InventoryItem, MockShelf> currentInventory;

    public InventoryManagement() {
        shelves = new ArrayList<>();
        currentInventory = new HashMap<>();
    }

    public void addShelf(Integer[] location) {
        MockShelf shelf = new MockShelf(UUID.randomUUID(), location);
    }

    public void addItem(InventoryItem item) {
        int idx = new Random().nextInt(shelves.size());
        addItem(item,  shelves.get(idx));
    }

    public void addItem(InventoryItem item, MockShelf shelf) {
        currentInventory.put(item, shelf);
    }

    public HashMap<InventoryItem, MockShelf> getCurrentInventory() {
        return currentInventory;
    }
}

