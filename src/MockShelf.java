import java.awt.*;
import java.util.*;

public class MockShelf {

    private UUID shelfId;
    private Point location;

    public MockShelf(UUID shelfId, Point location){
        this.shelfId = shelfId;
        this.location = location;
    }

    public UUID getShelfId() {
        return shelfId;
    }

    public Point getLocation() {
        return location;
    }

}
