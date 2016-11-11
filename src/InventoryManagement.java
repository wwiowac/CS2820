import java.util.*;

public class InventoryManagement {

    private ArrayList<MockShelf> shelves;
    private HashMap<String, InventoryItem> skulookup;
    private HashMap<InventoryItem, MockShelf> currentInventory;

    /**
     * constructor - declares a null inventory list and and shelves list to be used to manage inventory.
     */
    public InventoryManagement() {
        shelves = new ArrayList<>();
        currentInventory = new HashMap<>();
        skulookup = new HashMap<>();
    }

    /**
     *
     * @param location - takes the location of where the shelf will be placed
     * description: creates a shelf at the given location defined by the parameter location and adds it to the list of
     *              all shelves.
     *
     */
    public void addShelf(Integer[] location) {
        MockShelf shelf = new MockShelf(UUID.randomUUID(), location);
        shelves.add(shelf);
    }

    /**
     *
     * @param item - item to be added to a shelf
     * description: takes an item given as a parameter and selects a shelf for it to be placed on.
     */
    public void addItem(InventoryItem item) {
            skulookup.put(item.getId(), item);
            int idx = new Random().nextInt(shelves.size());
            addItem(item, shelves.get(idx));
    }

    /**
     *
     * @param item - item to be added to a shelf
     * @param shelf - specific shelf that the given item should be added to
     * description: adds an item to a specific shelf and indicates this addition in the currentInventory HashMap.
     */
    public void addItem(InventoryItem item, MockShelf shelf) {
        currentInventory.put(item, shelf);
    }

    public HashMap<InventoryItem, MockShelf> getCurrentInventory() {
        return currentInventory;
    }


    /**
     *
     * @param item - single item to be used in generating an order
     *
     * Description: This is used to call the robot with the specific items that need to be taken
     *              to the belt by the robot.
     */
    public void generateOrder(InventoryItem item){
        //TODO:Figure out order process and implement inventory accordingly
    }


    /**
     *
     * @param item - item to be found
     * @return the shelf object the item is on
     * description: takes an item object and finds the current shelf it is on and returns it.
     */
    public MockShelf getItemShelf(InventoryItem item) {
            return currentInventory.get(item);
    }

    public InventoryItem getItembySku(String sku) {
        return skulookup.get(sku);
    }






}

