package production;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;

public class Shelf {

    private Master master;
    private Floor floor;

    private UUID shelfId;
    private Point location;
    private boolean isAvailable;
    private ArrayList<InventoryItem> items = new ArrayList<>();

    public Shelf(UUID shelfId, Point location){
        this.shelfId = shelfId;
        this.location = location;
        this.isAvailable = true;
    }

    public UUID getShelfId() {
        return shelfId;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    /**
     * Get if the shelf is being moved
     *
     * @author Jacob Roschen
     *
     * @return true if the shelf is not being moved
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Set if the shelf is being moved
     *
     * @author Jacob Roschen
     *
     * @param available
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void addItem(InventoryItem item) {
        items.add(item);
    }

    public void removeItem(InventoryItem item) {
        items.remove(item);
    }
}
