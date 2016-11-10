package Inventory;

import java.util.*;

public class InventoryManagement {

    private ArrayList<MockShelf> shelves;
    private HashMap<String, InventoryItem> skulookup;
    private HashMap<InventoryItem, MockShelf> currentInventory;

    public InventoryManagement() {
        shelves = new ArrayList<>();
        currentInventory = new HashMap<>();
        skulookup = new HashMap<>();
    }

    public void addShelf(Integer[] location) {
        MockShelf shelf = new MockShelf(UUID.randomUUID(), location);
        shelves.add(shelf);
    }

    public InventoryItem getItembySku(String sku) {
        return skulookup.get(sku);
    }

    public void addItem(InventoryItem item) {
        skulookup.put(item.getId(), item);
        int idx = new Random().nextInt(shelves.size());
        addItem(item,  shelves.get(idx));
    }

    public void addItem(InventoryItem item, MockShelf shelf) {
        currentInventory.put(item, shelf);
    }

    public HashMap<InventoryItem, MockShelf> getCurrentInventory() {
        return currentInventory;
    }

    public MockShelf getItemShelf(InventoryItem item) {
        return currentInventory.get(item);
    }
}

