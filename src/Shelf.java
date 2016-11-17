import java.awt.*;
import java.util.*;

public class Shelf {

    private Master master;
    private Floor floor;

    private UUID shelfId;
    private Point location;

    public Shelf(UUID shelfId, Point location){
        this.shelfId = shelfId;
        this.location = location;
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

}
