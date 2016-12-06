package production;

import java.awt.*;
import java.util.*;

public class InventoryManagement {

    private ArrayList<Shelf> shelves;
    private HashMap<String, InventoryItem> skulookup;
    private HashMap<InventoryItem, Shelf> currentInventory;
    private Floor floor;

    /**
     * constructor - declares a null inventory list and and shelves list to be used to manage inventory.
     */
    public InventoryManagement(Floor floor) {
        this.floor = floor;
        shelves = new ArrayList<>();
        currentInventory = new HashMap<>();
        skulookup = new HashMap<>();

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 6*4; j += 4) {
                this.addShelf(new Point(20 + i, 5 + j));
                this.addShelf(new Point(20 + i, 5 + j + 1));
            }
        }

        // Load the inventory with everything on the list
        // Later, we might want to only load a subset so that new items can
        // arrive by truck.
        Arrays.stream(InventoryList.catalog).forEach(this::addItem);
    }

    /**
     * @param location - takes the location of where the shelf will be placed
     *                 description: creates a shelf at the given location defined by the parameter location and adds it to the list of
     *                 all shelves.
     */
    public void addShelf(Point location) {
        Shelf shelf = new Shelf(UUID.randomUUID(), location);
        shelves.add(shelf);
        floor.updateItemAt(location, Cell.Type.SHELF);
    }

    /**
     * @param item - item to be added to a shelf
     *             description: takes an item given as a parameter and selects a shelf for it to be placed on.
     */
    public void addItem(InventoryItem item) {
        skulookup.put(item.getId(), item);
        int idx = new Random().nextInt(shelves.size());
        addItem(item, shelves.get(idx));
    }

    /**
     * @param item  - item to be added to a shelf
     * @param shelf - specific shelf that the given item should be added to
     *              description: adds an item to a specific shelf and indicates this addition in the currentInventory HashMap.
     */
    public void addItem(InventoryItem item, Shelf shelf) {
        shelf.addItem(item);
        currentInventory.put(item, shelf);
    }

    public HashMap<InventoryItem, Shelf> getCurrentInventory() {
        return currentInventory;
    }


    /**
     * @param item - item to be found
     * @return the shelf object the item is on
     * description: takes an item object and finds the current shelf it is on and returns it.
     */
    public Shelf getItemShelf(InventoryItem item) {
        return currentInventory.get(item);
    }

    public InventoryItem getItembySku(String sku) {
        return skulookup.get(sku);
    }


    public Shelf getShelfByLocation(Point location) {
        for (Shelf s : shelves) {
            if (s.getLocation().equals(location)) {
                return s;
            }
        }
        return null;
    }


}

