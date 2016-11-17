package production;

import java.util.*;

/**
 * @Author Kyle Den hartog
 */
public class InventoryItem{
    private String id;
    private String description;
    private double price = 0.0;
    private Shelf shelfID;
    private int quantity;

    public InventoryItem(int id, String description){
        this.id = Integer.toString(id);
        this.description = description;
        price = generatePrice();
        quantity = new Random().nextInt(25) + 5;
    }

    public InventoryItem(String id, String description, double price){
        this.id = id;
        this.description = description;
        this.price = price;
        quantity = new Random().nextInt(25) + 5;
    }

    /**
     *
     * Getter and Setter methods to access data from this class
     */

    public void setId(String id) { this.id = id;}

    public String getId() { return id;}

    public String getName() {
        return description;
    }

    public void setName(String name) {
        this.description = name;
    }

    public void setShelfID(Shelf shelfID) {
        this.shelfID = shelfID;
    }

    public Shelf getShelfID() {
        return shelfID;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    /**
     *
     * @param quantity - number of additional items to be added
     * description: adds a number of items to the quantity. This is used when a new shipment of products comes in.
     */
    public void increaseQuantity(int quantity){
        this.quantity += quantity;
    }

    /**
     *
     * @param quantity - number of additional items to be added
     * description: removes a number of items to the quantity. This is used when a new order is placed and the quantity
     *              in the inventory needs to be updated.
     */
    public void decreaseQuantity(int quantity){
        this.quantity -= quantity;
    }

    /**
     *
     * @return a random price
     *
     * description: used to assign a random price to the inventoryItem until a price system is figured out.
     *              I'll likely assign a specific price to each item in production.InventoryList.
     */
    private double generatePrice(){
        int min = 10;
        int max = 125;
        return price = min + (max - min) * new Random().nextDouble();
    }
}