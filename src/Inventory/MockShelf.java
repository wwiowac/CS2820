package Inventory;

import java.util.*;

public class MockShelf {

    private UUID shelfId;
    private Integer[] location;

    public MockShelf(UUID shelfId, Integer[] location){
        this.shelfId = shelfId;
        this.location = location;
    }

    public UUID getShelfId() {
        return shelfId;
    }

    public Integer[] getLocation() {
        return location;
    }

}
